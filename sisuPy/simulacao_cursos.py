from typing import List
from PyQt5.QtWidgets import QWidget, QMessageBox
from PyQt5.QtCore import Qt
from visual.ui_simulacaoCursos import Ui_Form  
from candidato import Candidato
from calculadora_nota_corte import CalculadoraNotaCorte

class SimulacaoCursosController(QWidget):
    def __init__(self):
        super().__init__()
        self.ui = Ui_Form()
        self.ui.setupUi(self)
        self.ui.botaoResultado.clicked.connect(self.exibir_resultado)


    def setDados(self, dados: List[Candidato], filtros: List[str], markCampus: bool):
        self._dados = dados if dados is not None else []
        self._filtros = filtros if filtros is not None else []
        self.notas_corte = CalculadoraNotaCorte.calcular(self._dados, markCampus)
        filtros_str = ", ".join(self._filtros)
        titulo_texto = f"Simulação de Cursos referente a ({filtros_str})"
        self.ui.titulo.setText(titulo_texto)

    def exibir_resultado(self):
        tmp = self.ui.txtnota.toPlainText().strip()
        try:
            nota = float(tmp.replace(",", "."))
        except Exception:
            QMessageBox.critical(self, "Formato inválido", "Por favor insira uma nota válida (ex: 750.5).")
            return
        
        lista_c = []
        quant = 0
        for chave, val in self.notas_corte.items():
            if val < nota:
                lista_c.append(chave)
                quant += 1

        self.ui.listWidget.clear()
        if lista_c:
            self.ui.listWidget.addItems(lista_c)
        else:
            self.ui.listWidget.addItem("Nenhum curso encontrado para a nota informada.")

        total_cursos = len(self.notas_corte)
        percentual = (quant / total_cursos * 100.0) if total_cursos > 0 else 0.0

        mensagem_formatada = f"Você passaria em {quant} cursos do total de {total_cursos}.\nIsso representa {percentual:.2f}%."
        self.ui.infoadd.setText(mensagem_formatada)