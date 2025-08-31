from typing import List
from collections import OrderedDict

from PyQt5.QtWidgets import QWidget, QVBoxLayout, QListWidget
from PyQt5.QtGui import QPainter
from PyQt5.QtChart import (
    QChart, QChartView, QBarSeries, QBarSet, QBarCategoryAxis, QValueAxis
)

class HistogramaWidget(QWidget):

    def __init__(self):
        super().__init__()
        self._dados = []
        self._filtros = []
        self._range = 25 
        self.nota_min = 400
        self.nota_max = 900

        self._layout = QVBoxLayout(self)
        self.list_widget = QListWidget()
        self._layout.addWidget(self.list_widget, stretch=1)

        self.chart = QChart()
        self.chart.setTitle("Histograma de Notas")
        self.chart_view = QChartView(self.chart)
        self.chart_view.setRenderHint(QPainter.Antialiasing)
        self._layout.addWidget(self.chart_view, stretch=5)

    def setDados(self, dados: List, filtros: List[str]):
        self._dados = dados if dados is not None else []
        self._filtros = filtros if filtros is not None else []
        self.preencher_listview()
        self._criar_histograma()

    def preencher_listview(self):
        if not self._filtros:
            self.list_widget.addItem("Nenhum Filtro foi Selecionado.")
        else:
            self.list_widget.addItem("Filtros Selecionados:")
        if self._filtros:
            for f in self._filtros:
                self.list_widget.addItem(str(f))


    def _criar_histograma(self):
        freq = OrderedDict()
        r = self._range
        for i in range(self.nota_min, self.nota_max, r):
            label = f"{i} - {i + r - 1}"
            freq[label] = 0

        for c in self._dados:
            nota = c.media
            faixa_inicial = int((nota // self._range) * self._range)
            faixa_label = f"{faixa_inicial} - {faixa_inicial + self._range - 1}"
            if faixa_label in freq:
                freq[faixa_label] += 1

        faixas = list(freq.keys())
        contagem = list(freq.values())

        barset = QBarSet("Frequência de Notas")
        barset.append(contagem)
        series = QBarSeries()
        series.append(barset)
        self.chart.addSeries(series)

        eixo_x = QBarCategoryAxis()
        eixo_x.append(faixas)
        self.chart.setAxisX(eixo_x, series)

        limite_sup = max(contagem) if contagem else 0
        eixo_y = QValueAxis()
        eixo_y.setRange(0, limite_sup + max(1, int(limite_sup * 0.1))) 
        eixo_y.setLabelFormat("%d")

        self.chart.setAxisY(eixo_y, series)
        self.chart.legend().setVisible(False)
        self.chart_view.setChart(self.chart)