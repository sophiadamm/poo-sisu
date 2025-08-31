import sys
import os 
from PyQt5 import QtWidgets, QtCore, QtGui, QtChart
from PyQt5.QtWidgets import QWidget, QVBoxLayout
from PyQt5 import uic
from PyQt5.QtChart import QChart, QChartView, QLineSeries, QValueAxis
from PyQt5.QtCore import QPointF
from PyQt5.QtGui import QPainter
from typing import List
from auxiliarGrafico import AuxiliarGrafico

class graficolinhaWidget(QWidget):
    def __init__ (self):
        super().__init__()
        self.resize(1000,600)
        self.main_widget = QtWidgets.QWidget(self)
        self.setCentralWidget(self.main_widget)
        self.vertical_layout = QtWidgets.QVBoxLayout(self.main_widget)
        self.series = QtChart.QLineSeries()
        self.series.setName('Gráfico de Linha')
        self.chart = QtChart.QChart()
        self.chart.addSeries(self.series)
        self.chart.legend().setVisible(True)
        self.chart.createDefaultAxes()
        self.chart.axisX().setTitleText('Ano')
        self.chart.axisY().setTitleText('Nota de Corte')
        self.chart.setTitle('Grafico')
        self.chart.setAnimationOptions(QtChart.QChart.AllAnimations)
        self.chart_view = QtChart.QChartView(self.chart)
        self.chart_view.setRenderHint(QtGui.QPainter.Antialiasing)
        self.vertical_layout.addWidget(self.chart_view)


    
    '''def agrupar_dados_por_campus(self, dados, curso_selecionado=None):
        mapas = {}
        for candidato in dados:
            # se quiser filtrar por curso:
            if curso_selecionado is not None and candidato.curso() != curso_selecionado:
                continue

            campus = candidato.campus()
            ano = int(candidato.ano())
            media = float(candidato.media())

            if campus not in mapas:
                mapas[campus] = AuxiliarGrafico(campus)
            mapas[campus].adicionar_nota(ano, media)

        return mapas
        

    def setDados(self, dados: List, curso: List[str]):
        self.dados = dados 
        self.curso_selecionado = curso
        dados_agrupados = {}

        for candidato in dados:
            campus = candidato.campus
            ano = candidato.ano
            media = candidato.media

            if campus not in dados_agrupados:
                dados_agrupados[campus] = {}
            nota_de_corte = dados_agrupados[campus]

            if ano not in nota_de_corte:
                nota_de_corte[ano] = media
            else:
                if media < nota_de_corte[ano]:
                    nota_de_corte[ano] = media

        dados_agrupados = self.agrupar_dados_por_campus(self.dados, self.curso_selecionado)

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
            serie: QLineSeries = QLineSeries()
            serie.setName(campus)
            
            anos = sorted(nota_de_corte.keys())
            for ano in anos:
                nota = nota_de_corte[ano]
                serie.append(QPointF(ano, nota))
            
            line_chart.addSeries(serie)
            serie.attachAxis(eixoX)
            serie.attachAxis(eixoY)
        
        eixoX.setRange(min(anos), max(anos))
        eixoX.setLabelFormat("%d")
        chart_view = QChartView(line_chart)
        chart_view.setRenderHint(QPainter.Antialiasing)

        layout.addWidget(chart_view)'''
     

