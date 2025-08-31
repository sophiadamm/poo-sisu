
from typing import List, Dict
from PyQt5.QtWidgets import QWidget, QTableWidgetItem, QHeaderView
from PyQt5.QtCore import Qt
from visual.ui_aumentoNota import Ui_Form
from calculadora_nota_corte import CalculadoraNotaCorte
from item_numerico import NumericItem

class AumentoNotaController(QWidget):
    def __init__(self):
        super().__init__()
        self.ui = Ui_Form()
        self.ui.setupUi(self)

        self._dados = []
        anos_completos = ["2019", "2020", "2021", "2022", "2023", "2024", "2025"]
        anos_iniciais = anos_completos.copy()
        anos_iniciais.remove("2025")
        anos_finais = anos_completos.copy()
        anos_finais.remove("2019")

        self.ui.anoInicial.addItems(anos_iniciais)
        self.ui.anoFinal.addItems(anos_finais)

        self.ui.anoInicial.currentIndexChanged.connect(self.atualizar)
        self.ui.anoFinal.currentIndexChanged.connect(self.atualizar)
        self.ui.botaoResultado.clicked.connect(self.exibir_resultado)

        self.tabela = self.ui.tabela
        self.tabela.resizeColumnsToContents()
        header = self.tabela.horizontalHeader()
        header.setSectionResizeMode(QHeaderView.Stretch)

    def atualizar(self):
        ano_i = self.ui.anoInicial.currentText()
        ano_f = self.ui.anoFinal.currentText()

        estao_selecionados = bool(ano_i) and bool(ano_f)
        ordem_correta = False
        if estao_selecionados:
            try:
                ordem_correta = int(ano_i) < int(ano_f)
            except Exception:
                print("Erro na conversão na função atualizar")
                return 
            
        self.ui.botaoResultado.setDisabled(not (estao_selecionados and ordem_correta))

    def setDados(self, dados: List, demandaSelecionada: str):
        self._dados = list(dados) if dados is not None else []
        self.ui.label.setText(f"Análise Comparativa da Nota de Corte (Tipo de Concorrência: {demandaSelecionada})")

    def exibir_resultado(self):
        ano_i = self.ui.anoInicial.currentText()
        ano_f = self.ui.anoFinal.currentText()

        try:
            ano_i_int = int(ano_i)
            ano_f_int = int(ano_f)
        except Exception:
            print("Erro na conversão na função exibir_resultado")
            return

        lista_ano_i = [c for c in self._dados if c.ano == ano_i_int]
        lista_ano_f = [c for c in self._dados if c.ano == ano_f_int]
        notas_i = CalculadoraNotaCorte.calcular(lista_ano_i, False)
        notas_f = CalculadoraNotaCorte.calcular(lista_ano_f, False)

        cursos_comuns = [c for c in notas_i.keys() if c in notas_f]

        self.tabela.setRowCount(0)
        self.tabela.setSortingEnabled(False)

        for curso in sorted(cursos_comuns):
            nota_i_val = float(notas_i.get(curso, 0.0))
            nota_f_val = float(notas_f.get(curso, 0.0))
            aumento_bruto = nota_f_val - nota_i_val
            aumento_percent = (aumento_bruto / nota_i_val) * 100.0

            row = self.tabela.rowCount()
            self.tabela.insertRow(row)

            # coluna 0: Curso
            item0 = QTableWidgetItem(str(curso))
            self.tabela.setItem(row, 0, item0)

            # coluna 1: Aumento % 
            item1 = NumericItem(f"{aumento_percent:.2f}", aumento_percent)
            item1.setTextAlignment(Qt.AlignCenter)
            self.tabela.setItem(row, 1, item1)

            # coluna 2: Aumento Bruto
            item2 = NumericItem(f"{aumento_bruto:.2f}", aumento_bruto)
            item2.setTextAlignment(Qt.AlignCenter)
            self.tabela.setItem(row, 2, item2)

            # coluna 3: Nota Inicial
            item3 = NumericItem(f"{nota_i_val:.2f}", nota_i_val)
            item3.setTextAlignment(Qt.AlignCenter)
            self.tabela.setItem(row, 3, item3)

            # coluna 4: Nota Final
            item4 = NumericItem(f"{nota_f_val:.2f}", nota_f_val)
            item4.setTextAlignment(Qt.AlignCenter)
            self.tabela.setItem(row, 4, item4)


        self.tabela.setSortingEnabled(True)  
        self.tabela.resizeColumnsToContents()
        header = self.tabela.horizontalHeader()
        header.setSectionResizeMode(QHeaderView.Stretch)
