import sys
import os 
from PyQt5.QtCore import Qt
from PyQt5 import QtWidgets, QtCore, QtGui, QtChart
from PyQt5.QtWidgets import QWidget, QVBoxLayout
from PyQt5 import uic
from PyQt5.QtChart import QChart, QChartView, QLineSeries, QValueAxis
from PyQt5.QtCore import QPointF
from PyQt5.QtGui import QPainter
from typing import List
from auxiliarGrafico import AuxiliarGrafico
from candidato import Candidato
from PyQt5.QtChart import QChart, QLineSeries, QValueAxis, QAbstractAxis



class graficolinhaWidget(QWidget):
    def __init__(self):
        super().__init__()
        self.resize(1000, 600)
        self.setWindowTitle("Gráfico de Notas de Corte")

        # Layout principal
        self.layout = QVBoxLayout(self)
        self.setLayout(self.layout)

    def setDados(self, dados: List[Candidato], curso: List[str]):
        self.dados = dados
        self.curso_selecionado = curso[0]

        # Agrupar notas por campus e ano
        dados_agrupados = {}
        for candidato in dados:
            campus = candidato.campus
            ano = int(candidato.ano)
            media = float(candidato.media)

            if campus not in dados_agrupados:
                dados_agrupados[campus] = {}
            if ano not in dados_agrupados[campus]:
                dados_agrupados[campus][ano] = media
            else:
                # pegar a menor nota (nota de corte)
                dados_agrupados[campus][ano] = min(dados_agrupados[campus][ano], media)

        # Criar gráfico
        line_chart = QChart()
        line_chart.setTitle(
            f"Evolução das Notas de Corte de {self.curso_selecionado} em cada Campus"
        )
        line_chart.setAnimationOptions(QChart.SeriesAnimations)

        # Eixos
        eixoX = QValueAxis()
        eixoX.setTitleText("Ano")
        eixoX.setLabelFormat("%d")

        eixoY = QValueAxis()
        eixoY.setTitleText("Nota de Corte")
        eixoY.setRange(0, 1000)
        eixoY.setTickCount(11)
        eixoY.setLabelFormat("%d")

        line_chart.addAxis(eixoX, Qt.AlignBottom)
        line_chart.addAxis(eixoY, Qt.AlignLeft)

        anos_global = set()

        # Criar séries para cada campus
        for campus, nota_de_corte in dados_agrupados.items():
            serie = QLineSeries()
            serie.setName(campus)

            anos = sorted(nota_de_corte.keys())
            for ano in anos:
                nota = nota_de_corte[ano]
                serie.append(QPointF(float(ano), float(nota)))
                anos_global.add(ano)

            line_chart.addSeries(serie)
            serie.attachAxis(eixoX)
            serie.attachAxis(eixoY)

        # Configurar faixa dos eixos
        if anos_global:
            eixoX.setRange(min(anos_global), max(anos_global))

        line_chart.addAxis(eixoX, QtCore.Qt.AlignBottom)
        line_chart.addAxis(eixoY, QtCore.Qt.AlignLeft)

        # Colocar gráfico na tela
        chart_view = QChartView(line_chart)
        chart_view.setRenderHint(QPainter.Antialiasing)

        # Limpar layout antes de adicionar (caso troque de gráfico)
        for i in reversed(range(self.layout.count())):
            self.layout.itemAt(i).widget().setParent(None)

        self.layout.addWidget(chart_view)

