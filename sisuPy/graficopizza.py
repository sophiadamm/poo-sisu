from collections import Counter
from typing import List, Set
from PyQt5 import QtWidgets
from PyQt5.QtCore import Qt
from PyQt5.QtGui import QPainter
from PyQt5.QtChart import QChart, QChartView, QPieSeries, QPieSlice
from PyQt5.QtWidgets import QListWidget, QVBoxLayout, QWidget
from PyQt5.QtGui import QStandardItemModel, QStandardItem
from PyQt5.QtWidgets import QToolTip
from PyQt5.QtGui import QCursor


class PizzaDemandaWidget(QWidget):
    def __init__(self):
        super().__init__()
        self._layout = QVBoxLayout(self)
        self.chart_view = QChartView()
        self.chart_view.setRenderHint(QPainter.Antialiasing)
        self._layout.addWidget(self.chart_view, stretch=3)

        self.list_widget = QListWidget()
        self._layout.addWidget(self.list_widget, stretch=1)

    def setDados(self, dados: List, filtros: List[str], demandas: Set[str]):
        self._dados = dados or []
        self._demandas = set(demandas or [])
        self._filtros = list(filtros or [])

        freq = {d: 0 for d in self._demandas}
        for c in self._dados:
            key = c.demanda
            if key in freq:
                freq[key] += 1
            else:
                freq[key] = freq.get(key, 0) + 1

        total = len(self._dados)

        self.preencher_listview(self._filtros, freq, total)
        self.criar_grafico(freq, total)

    def preencher_listview(self, filtros: List[str], freq_map: dict, total: int):
        for f in filtros:
            self.list_widget.addItem(str(f))
        self.list_widget.addItem(f"Total: {total}")
        for chave, val in sorted(freq_map.items(), key=lambda kv: kv[1], reverse = True):
            self.list_widget.addItem(f"{chave} : {val}")

    def criar_grafico(self, frequencias: dict, total: int):
        series = QPieSeries()
        
        for chave, valor in sorted(frequencias.items(), key=lambda kv: kv[1]):
            slice = QPieSlice(chave, float(valor))

            percent = (valor / total * 100) if total > 0 else 0.0
            label_text = f"{chave} ({percent:.1f}%)"
            slice.setLabel(label_text)

            if percent >= 5.0:
                slice.setLabelVisible(True)
            else:
                slice.setLabelVisible(False)
            series.append(slice)
            slice.hovered.connect(lambda entered, text=label_text: QToolTip.showText(QCursor.pos(), text) if entered else QToolTip.hideText())


        chart = QChart()
        chart.addSeries(series)
        chart.setTitle("Participacao Percentual de cada Demanda no Total de Ingressantes")
        chart.legend().setAlignment(Qt.AlignRight)

        self.chart_view.setChart(chart)
        # self.chart_view.repaint()
