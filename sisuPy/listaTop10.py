import sys
import os 
from PyQt5.QtCore import Qt
from PyQt5 import QtWidgets, QtCore, QtGui, QtChart
from PyQt5.QtWidgets import QWidget, QVBoxLayout
from candidato import Candidato
from typing import List,  Dict, Tuple
from visual.ui_listatop10 import Ui_Form 

class ListaTop10(QWidget, Ui_Form):
    def __init__(self):
        super().__init__()
        self.ui = Ui_Form()
        self.ui.setupUi(self)
        self.listWidget = self.ui.listWidget

        

    def setDados(self, dados: List[Candidato], ano: List[str]):
        self.dados = dados
        self.ano_selecionado = int(ano[1])
        
        self.ui.label.setText(f"Top 10 Cursos com Maior Nota de Corte em {self.ano_selecionado}")
        
        maiores_notas: Dict[str, float] = {}

        for c in dados:
            if c.ano == self.ano_selecionado:
                maior_nota_atual = maiores_notas.get(c.curso, 0.0)
                maiores_notas[c.curso] = max(maior_nota_atual, c.media)
        
        lista_de_notas: List[Tuple[float, str]] = []
        for curso, nota in maiores_notas.items():
            lista_de_notas.append((nota, curso))
        
        lista_de_notas.sort(reverse=True)

        top_10 = lista_de_notas[:10]

        self.listWidget.clear()
        for nota, curso in top_10:
            item_string = f"{curso}  -  {nota:.2f}"
            self.listWidget.addItem(item_string)
        

        