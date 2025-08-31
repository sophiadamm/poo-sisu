import sys
from PyQt5 import QtWidgets
from PyQt5.QtWidgets import QApplication, QMainWindow
from visual.ui_janelaprincipal import Ui_MainWindow
from dados import Dados

class JanelaInicial(QMainWindow):
    def __init__(self):
        super().__init__()

        self.ui = Ui_MainWindow()
        self.ui.setupUi(self) 

        self.campus = set()
        self.demandas = set()
        self.cursos = set()

        self.setWindowTitle("Análise dos dados do Sisu")
        self.setMinimumSize(600, 600)
        self.resize(600, 600) 
        self.show()
        self.initialize()

    def initialize(self):
        self.dados_sisu = Dados.get_instancia().get_lista_candidatos()
        self.ui.botao10.setDisabled(False)
        self.ui.botao3.setDisabled(False)
        self.ui.botao1.setDisabled(True)
        self.ui.botao2.setDisabled(True)
        self.ui.botao4.setDisabled(True)
        self.ui.botao5.setDisabled(True)
        self.ui.botao6.setDisabled(True)
        self.ui.botao7.setDisabled(True)
        self.ui.botao8.setDisabled(True)
        self.ui.botao9.setDisabled(True)


        self.preencher_filtros()
        self.adicionar_listeners()

        self.ui.limparAno.clicked.connect(self.limpar_ano)
        self.ui.limparCampus.clicked.connect(self.limpar_campus)
        self.ui.limparDemanda.clicked.connect(self.limpar_demanda)
        self.ui.limparCurso.clicked.connect(self.limpar_curso)
        self.ui.botao1.clicked.connect(self.abrirF1)
        self.ui.botao2.clicked.connect(self.abrirF2)
        self.ui.botao3.clicked.connect(self.abrirF3)
        self.ui.botao4.clicked.connect(self.abrirF4)
        self.ui.botao5.clicked.connect(self.abrirF5)
        self.ui.botao6.clicked.connect(self.abrirF6)
        self.ui.botao7.clicked.connect(self.abrirF7)
        self.ui.botao8.clicked.connect(self.abrirF8)
        self.ui.botao9.clicked.connect(self.abrirF9)
        self.ui.botao10.clicked.connect(self.abrirF10)

    
    def preencher_filtros(self):
        anos = ["Ano","2019", "2020", "2021", "2022", "2023", "2024", "2025"]
        self.ui.filtroAno.addItems(anos)
        
        if self.dados_sisu:
            for candidato in self.dados_sisu:
                self.campus.add(candidato.campus)
                self.demandas.add(candidato.demanda)
                self.cursos.add(candidato.curso)

            
            self.ui.filtroDemanda.addItem("Demanda")
            self.ui.filtroCampus.addItem("Campus")
            self.ui.filtroCurso.addItem("Curso")
            self.ui.filtroDemanda.addItems(sorted(list(self.demandas)))
            self.ui.filtroCampus.addItems(sorted(list(self.campus)))
            self.ui.filtroCurso.addItems(sorted(list(self.cursos)))
    
    def adicionar_listeners(self):

        self.ui.filtroAno.currentIndexChanged.connect(self.atualizar)
        self.ui.filtroCampus.currentIndexChanged.connect(self.atualizar)
        self.ui.filtroDemanda.currentIndexChanged.connect(self.atualizar)
        self.ui.filtroCurso.currentIndexChanged.connect(self.atualizar)

    def atualizar(self):
        cursoSelecionado = self.ui.filtroCurso.currentText()
        anoSelecionado = self.ui.filtroAno.currentText()
        campusSelecionado = self.ui.filtroCampus.currentText()
        demandaSelecionada = self.ui.filtroDemanda.currentText()

        validaAno = (anoSelecionado != "Ano")
        validaCampus = ( campusSelecionado != "Campus")
        validaDemanda = (demandaSelecionada != "Demanda")
        validaCurso = (cursoSelecionado != "Curso")

        self.ui.botao1.setDisabled(not validaCurso or not validaDemanda or validaAno)
        self.ui.botao2.setDisabled(not validaCampus or not validaCurso or not validaDemanda or validaAno)

        self.ui.botao4.setDisabled(not validaAno or not validaDemanda or validaCurso)
        self.ui.botao5.setDisabled(not validaAno)
        
        self.ui.botao6.setDisabled(not validaDemanda or validaAno)
        self.ui.botao7.setDisabled(not validaDemanda or not validaCurso or not validaCampus or not validaAno)

        self.ui.botao8.setDisabled(not validaAno or not validaDemanda or validaCurso)
        self.ui.botao9.setDisabled(not validaAno and validaDemanda)

    def limpar_ano(self):
        self.ui.filtroAno.setCurrentIndex(0)
        self.atualizar()

    def limpar_campus(self):
        self.ui.filtroCampus.setCurrentIndex(0)
        self.atualizar()

    def limpar_demanda(self):
        self.ui.filtroDemanda.setCurrentIndex(0)
        self.atualizar()
    
    def limpar_curso(self):
        self.ui.filtroCurso.setCurrentIndex(0)
        self.atualizar()

    def abrirF1(self):
        print("Abriu botao 1")
        pass

    def abrirF2(self):
        print("Abriu botao 2")
        pass

    def abrirF3(self):
        print("Abriu botao 3")
        pass

    def abrirF4(self):
        print("Abriu botao 4")
        pass

    def abrirF5(self):
        print("Abriu botao 5")
        pass

    def abrirF6(self):
        print("Abriu botao 6")
        pass

    def abrirF7(self):
        print("Abriu botao 7")
        pass

    def abrirF8(self):
        print("Abriu botao 8")
        pass

    def abrirF9(self):
        print("Abriu botao 9")
        pass

    def abrirF10(self):
        print("Abriu botao 10")
        pass

if __name__ == "__main__":
    app = QApplication(sys.argv)
    window = JanelaInicial()
    sys.exit(app.exec_())