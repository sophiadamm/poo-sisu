from typing import List, Optional, Any
from PyQt5.QtWidgets import QWidget, QMessageBox
from PyQt5.QtCore import Qt
from visual.ui_simulaçãoSisu import Ui_Form  
from candidato import Candidato

class SimulacaoSisuController(QWidget):
    def __init__(self):
        super().__init__()
        self.ui = Ui_Form()
        self.ui.setupUi(self)

        self.ui.botaoResultado.clicked.connect(self.exibir_resultado)

        self.ui.txtinfo.setDisabled(True)
        self.ui.txtDiff.setDisabled(True)
        self.ui.txtColoc.setDisabled(True)
        self.ui.txtF.setDisabled(True)


    def setDados(self, dados: List[Candidato]):
        self._dados = sorted(list(dados), key=lambda c: c.media, reverse=True)

    def exibir_resultado(self):
        self.ui.txtinfo.setDisabled(False)
        self.ui.txtDiff.setDisabled(False)
        self.ui.txtColoc.setDisabled(False)
        self.ui.txtF.setDisabled(False)

        tmp = self.ui.txtnota.toPlainText().strip()
        try:
            nota = float(tmp.replace(",", "."))
        except Exception:
            QMessageBox.critical(self, "Formato inválido", "Por favor insira uma nota válida (ex: 750.5).")
            return

        vagas = len(self._dados)
        nota_corte = self._dados[-1].media

        aprovado = (nota >= nota_corte)
        diferenca_bruta = nota - nota_corte
        diferenca_percentual = (diferenca_bruta / nota_corte * 100.0) if nota_corte != 0 else 0.0
        perc_do_corte = (100.0 * nota / nota_corte) if nota_corte != 0 else 0.0

        coloc = 1
        for c in self._dados:
            if c.media > nota:
                coloc += 1
            else:
                break

        percent_supera = 100.0 * (vagas - coloc) / vagas if vagas > 0 else 0.0

        curso = self._dados[0].curso
        campus = self._dados[0].campus
        demanda = self._dados[0].demanda
        ano = self._dados[0].ano

        info = (
            f"Curso: {curso}\n"
            f"Campus: {campus}\n"
            f"Tipo: {demanda}\n"
            f"Ano: {ano}\n"
            f"Vagas (aprovados): {vagas}\n"
        )
        self.ui.txtinfo.setPlainText(info)

        comparacao = (
            f"Sua nota: {nota:.2f}\n"
            f"Nota de corte (último aprovado): {nota_corte:.2f}\n"
            f"Diferença (sua - corte): {diferenca_bruta:.2f}\n"
            f"Diferença percentual: {diferenca_percentual:.2f}%"
        )
        self.ui.txtDiff.setPlainText(comparacao)

        if aprovado:
            situacao = (
                f"Parabéns — você estaria APROVADO(A)!\n"
                f"Posição entre aprovados: {coloc}ª de {vagas}.\n"
                f"Você supera {percent_supera:.1f}% dos aprovados."
            )
        else:
            faltam = (nota_corte - nota)
            situacao = (
                "Infelizmente, você não estaria aprovado(a).\n"
                f"Faltam {faltam:.2f} pontos para o corte.\n"
                f"Você já atingiu {perc_do_corte:.1f}% da nota de corte."
            )
        self.ui.txtColoc.setPlainText(situacao)

        # estatísticas gerais
        notas = [c.media for c in self._dados]
        nota_maxima = max(notas) if notas else 0.0
        nota_media = (sum(notas) / len(notas)) if notas else 0.0
        diff_media = nota - nota_media
        perc_media = (diff_media / nota_media * 100.0) if nota_media != 0 else 0.0

        estatisticas = (
            f"Média: {nota_media:.2f}\n"
            f"Máxima: {nota_maxima:.2f}\n"
            f"Sua nota é {abs(diff_media):.2f} pontos ({abs(perc_media):.2f}%) "
            f"{'ACIMA' if diff_media >= 0 else 'ABAIXO'} da média."
        )
        self.ui.txtF.setPlainText(estatisticas)