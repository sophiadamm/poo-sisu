import sys
import os 
from PyQt5.QtCore import Qt
from PyQt5 import QtWidgets, QtCore, QtGui, QtChart
from PyQt5.QtWidgets import QWidget, QVBoxLayout, QTableView
from candidato import Candidato
from typing import List,  Dict, Tuple
from visual.ui_tabelaestado import Ui_Form 
from PyQt5.QtGui import QStandardItemModel, QStandardItem
from collections import defaultdict

class DadosEstado:
    def __init__(self, estado: str, numero_alunos: int, media: float, porcentagem: float):
        self.estado = estado
        self.numero_alunos = numero_alunos
        self.media = media
        self.porcentagem = porcentagem

class DadosTemporarios:
    def __init__(self):
        self.contagem = 0
        self.somaMedias = 0.0


class TabelaEstados(QWidget, Ui_Form):
    def __init__(self):
        super().__init__()
        self.setupUi(self)
        
        self.tableModel = QStandardItemModel(0, 4)
        self.tableModel.setHorizontalHeaderLabels(["Estado", "Nº de Alunos", "Média", "Porcentagem"])
        self.tableView.setModel(self.tableModel)

    def setDados(self, dados: List['Candidato'], filtro: List[str]) -> List[DadosEstado]:
        self.tableModel.removeRows(0, self.tableModel.rowCount())
        self.tudo = filtro
        tamanho = len(filtro)
        self.filtro = int(filtro[-1])
        self.label.setText(f"Tabela com a Relação de Aprovados por Estado em {self.filtro}") 
        if tamanho == 2:
            self.label_2.setText(f"Filtros aplicados: {self.tudo[0]}")
        elif tamanho == 3:
            self.label_2.setText(f"Filtros aplicados: {self.tudo[0]}, {self.tudo[1]}")
        else:
            self.label_2.setText(f"Filtros aplicados: Ano")
            
        inicial = defaultdict(DadosTemporarios)
        total = len(dados)

        for c in dados:
            estado = c.estado
            data = inicial[estado]
            data.contagem += 1
            data.somaMedias += c.media

        listaParaTabela: List[DadosEstado] = []
        for estado, data in inicial.items():
            media = data.somaMedias / data.contagem
            porcentagem = (data.contagem / total) * 100
            listaParaTabela.append(DadosEstado(estado, data.contagem, media, porcentagem))
        
        listaParaTabela.sort(key=lambda d: d.numero_alunos, reverse=True)

        for d in listaParaTabela:
            row =[
                QStandardItem(str(d.estado)),
                QStandardItem(str(d.numero_alunos)),
                QStandardItem(f"{d.media:.2f}"),
                QStandardItem(f"{d.porcentagem:.2f}%"),
            ]
            self.tableModel.appendRow(row)