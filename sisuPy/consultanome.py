from visual.ui_consultanome import Ui_Form
from PyQt5.QtWidgets import QWidget, QMessageBox
from typing import List
from candidato import Candidato

class ConsultaNomeController(QWidget):
    def __init__(self):
        super().__init__()
        self.ui = Ui_Form()
        self.ui.setupUi(self)
        self.ui.botaoBuscar.clicked.connect(self.exibir_resultado)
    
    def setDados(self, dados: List[Candidato], filtros: List[str]):
        self._dados = dados if dados is not None else []
        self._filtros = filtros if filtros is not None else []
        filtros_str = ", ".join(self._filtros)
        titulo_texto = f"Filtros Selecionados: ({filtros_str})"
        self.ui.label_filtros.setText(titulo_texto)

    def exibir_resultado(self):
            nome = self.ui.txtNome.toPlainText().strip().upper()
            encontrados = []

            for c in self._dados:
                if nome in c.nome:
                    encontrados.append(c)

            if not encontrados:
                self.ui.areaTxt.setText("Nome não encontrado.")
                return

            LIMITE = 50
            if len(encontrados) > LIMITE:
                QMessageBox.warning(
                    self,
                    "Muitos Resultados",
                    f"Foram encontrados {len(encontrados)} candidatos.\n"
                    "Refine sua busca. "
                )
                return

            # mostra todos os resultados encontrados
            self.ui.areaTxt.clear()
            for c in encontrados:
                self.ui.areaTxt.append(str(c))