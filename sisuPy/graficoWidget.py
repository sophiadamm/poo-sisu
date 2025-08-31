import sys
from PyQt5.QtWidgets import QWidget, QVBoxLayout
from PyQt5 import uic
from PyQt5.QtChart import QChart, QChartView, QLineSeries, QValueAxis
from PyQt5.QtCore import QPointF
from PyQt5.QtGui import QPainter

class graficoWidget():
    def __init__ (self, dados, curso_selecionado):
        super().__init__()
        # Carrega a interface do arquivo .ui
        uic.loadUi("visual/Grafico1.ui", self)
        
        # Atributos equivalentes aos campos privados da classe Java
        self.dados = dados
        self.curso_selecionado = curso_selecionado
        
        # Acessa o widget que serve de contêiner para o gráfico
        self.widget_grafico = self.findChild(QWidget, 'widgetGrafico')
        
        # Chama a função para gerar o gráfico
        self.gerar_grafico(curso_selecionado, dados)

    def gerar_grafico(self, curso, dados):
        dados_agrupados = {}

        for candidato in dados:
            campus = candidato.campus()
            ano = candidato.ano()
            media = candidato.media()

            if campus not in dados_agrupados:
                dados_agrupados[campus] = {}
            nota_de_corte = dados_agrupados[campus]

            if ano not in nota_de_corte:
                nota_de_corte[ano] = media
            else:
                if media < nota_de_corte[ano]:
                    nota_de_corte[ano] = media

        dados_agrupados = self.agrupar_dados_por_campus(self.dados, self.curso, self.curso_selecionado)

        if self.widget_grafico.layout():
            layout = self.widget_grafico.layout()
            while layout.count():
                child = layout.takeAt(0)
                if child.widget():
                    child.widget().deleteLater()
        else:
            layout = QVBoxLayout(self.widget_grafico)
        
        line_chart = QChart()
        line_chart.setTitle(f"Gráfico da Evolução das Notas de Corte de {curso} em cada Campus Disponível")
        line_chart.setAnimationOptions(QChart.SeriesAnimations)

        eixoX = QValueAxis()
        eixoY = QValueAxis()
        line_chart.addAxis(eixoX, QChart.AxisLocationBottom)
        line_chart.addAxis(eixoY, QChart.AxisLocationLeft)

        for campus, nota_de_corte in dados_agrupados.items():
            serie = QLineSeries()
            serie.setName(campus)
            
            anos = sorted(nota_de_corte.keys())
            for ano in anos:
                nota = nota_de_corte[ano]
                serie.append(ano, nota)
            
            line_chart.addSeries(serie)
            serie.attachAxis(eixoX)
            serie.attachAxis(eixoY)
        
        eixoX.setRange(min(anos), max(anos))
        chart_view = QChartView(line_chart)
        chart_view.setRenderHint(QPainter.Antialiasing)

        layout.addWidget(chart_view)

