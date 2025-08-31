from collections import Counter
from typing import List, Set
from PyQt5 import QtWidgets
from PyQt5 import QtChart
from PyQt5 import QtCore
from PyQt5 import QtGui
from PyQt5.QtCore import Qt
from PyQt5.QtGui import QPainter
from PyQt5.QtWidgets import QWidget
from candidato import Candidato

class GraficoBarra(QWidget):
    def __init__(self):
        super().__init__()
        self.resize(1000, 600)
        # self.widget = QtWidgets.QWidget(self)
        # self.widget.setStyleSheet('background-color: linegreen')
        # self.vertical_layout = QtWidgets.QVBoxLayout(self.widget)

        self.setStyleSheet('background-color: lightgreen')  
        self.vertical_layout = QtWidgets.QVBoxLayout(self) 
        self.set0 = QtChart.QBarSet('menor_nota')
        self.set1 = QtChart.QBarSet('valor_medio')
        self.set2 = QtChart.QBarSet('maior_nota')

    def setDados(self, dados: List[Candidato]):
        self.dados = dados
        maior_nota = {}
        menor_nota = {}
        valor_medio = {}
        soma_notas = {}
        contagem_candidatos = {}

        for c in dados:
            ano = c.ano
            media = c.media
            if ano not in maior_nota or media > maior_nota[ano]:
                maior_nota[ano] = media
            
            if ano not in menor_nota or media < menor_nota[ano]:
                menor_nota[ano] = media
            
            soma_notas[ano] = soma_notas.get(ano, 0.0) + media
            contagem_candidatos[ano] = contagem_candidatos.get(ano, 0) + 1
        
        for ano in soma_notas:
            if contagem_candidatos[ano] > 0:
                media_calculada = soma_notas[ano] / contagem_candidatos[ano]
                valor_medio[ano] = media_calculada
        
        self.set0 = QtChart.QBarSet('menor_nota')
        self.set1 = QtChart.QBarSet('valor_medio')
        self.set2 = QtChart.QBarSet('maior_nota')

        self.set0.append(menor_nota.values())
        self.set1.append(valor_medio.values())
        self.set2.append(maior_nota.values())

        self.bar_series = QtChart.QBarSeries()
        self.bar_series.append(self.set0)
        self.bar_series.append(self.set1)
        self.bar_series.append(self.set2)

        self.chart = QtChart.QChart()
        self.chart.addSeries(self.bar_series)
        self.chart.setTitle("Gráfico com a nota de corte, nota média e nota máximo do curso no decorrer dos anos")

        self.categories = ['2019', '2020', '2021', '2022', '2023', '2024', '2025']
        self.x_axis = QtChart.QBarCategoryAxis()
        self.x_axis.append(self.categories)
        self.chart.setAxisX(self.x_axis, self.bar_series)

        self.y_axis = QtChart.QValueAxis()
        self.chart.setAxisY(self.y_axis, self.bar_series)
        self.y_axis.setRange(0, 1000)

        self.chart.legend().setVisible(True)
        self.chart.legend().setAlignment(QtCore.Qt.AlignBottom)
        self.chart.axisX().setTitleText('Ano')
        self.chart.axisY().setTitleText('Nota')
        self.chart.setAnimationOptions(QtChart.QChart.SeriesAnimations)

        self.chart_view = QtChart.QChartView(self.chart)
        self.chart_view.setRenderHint(QtGui.QPainter.Antialiasing)

        self.vertical_layout.addWidget(self.chart_view)
