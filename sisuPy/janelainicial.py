import sys
import os
from typing import List
from PyQt5 import QtWidgets
from PyQt5.QtWidgets import QApplication, QMainWindow, QWidget
from visual.ui_janelaprincipal import Ui_MainWindow
from graficopizza import PizzaDemandaWidget
from dados import Dados
from PyQt5 import QtWidgets, uic
from PyQt5.QtWidgets import QApplication, QMainWindow, QTabWidget, QPushButton, QWidget
from histogramawidget import HistogramaWidget
from aumento_nota_cont import AumentoNotaController
from simulacao_sisu import SimulacaoSisuController
from simulacao_cursos import SimulacaoCursosController
from consultanome import ConsultaNomeController
from graficobarra import GraficoBarra
from graficolinha import graficolinhaWidget
from listaTop10 import ListaTop10
from tabelaestados import TabelaEstados, DadosEstado, DadosTemporarios
from PyQt5.QtWidgets import QMessageBox
from PyQt5.QtWidgets import QWidget
from visual.ui_ajuda import Ui_Form
from ajuda import AjudaWidget


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
        self.ui.tabWidget.setTabsClosable(True)
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
        self.ui.botaoAjuda.setDisabled(False)


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
        self.ui.botaoAjuda.clicked.connect(self.abrirAjuda)

    
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
        self.ui.tabWidget.tabCloseRequested.connect(self.fechar_aba)

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
        self.ui.botao5.setDisabled(not validaAno or anoSelecionado == "2025" or validaCampus)
        
        self.ui.botao6.setDisabled(not validaDemanda or validaAno)
        self.ui.botao7.setDisabled(not validaAno or not validaDemanda or validaCurso)

        self.ui.botao8.setDisabled(not validaDemanda or not validaCurso or not validaCampus or not validaAno)
        self.ui.botao9.setDisabled(not validaAno or validaDemanda)

    def filtrosSelecionados(self) -> List[str]:
        cursoSelecionado = self.ui.filtroCurso.currentText()
        anoSelecionado = self.ui.filtroAno.currentText()
        campusSelecionado = self.ui.filtroCampus.currentText()
        demandaSelecionada = self.ui.filtroDemanda.currentText()

        filtros: List[str] = []

        if cursoSelecionado != "Curso":
            filtros.append(cursoSelecionado)
        if demandaSelecionada != "Demanda":
            filtros.append(demandaSelecionada)
        if anoSelecionado != "Ano":
            filtros.append(anoSelecionado)
        if campusSelecionado != "Campus":
            filtros.append(campusSelecionado)

        return filtros

    def filtrar_dados(self):
        dados_filtrados = []

        cursoSelecionado = self.ui.filtroCurso.currentText()
        anoSelecionado = self.ui.filtroAno.currentText()
        campusSelecionado = self.ui.filtroCampus.currentText()
        demandaSelecionada = self.ui.filtroDemanda.currentText()

        for candidato in self.dados_sisu:
            valida_ano = (anoSelecionado == "Ano" or str(candidato.ano) == anoSelecionado)
            valida_curso = (cursoSelecionado == "Curso" or candidato.curso == cursoSelecionado)
            valida_campus = (campusSelecionado == "Campus" or candidato.campus == campusSelecionado)
            valida_demanda = (demandaSelecionada == "Demanda" or candidato.demanda == demandaSelecionada)

            if valida_ano and valida_curso and valida_campus and valida_demanda:
                dados_filtrados.append(candidato)
        
        return dados_filtrados
    
    def verificarLista(self) -> bool:
        tmp = self.filtrar_dados()
        if tmp is None or len(tmp) == 0:
            msg = QMessageBox(self)
            msg.setIcon(QMessageBox.Critical)  # Ícone de erro
            msg.setWindowTitle("Nenhum Aprovado Encontrado")
            msg.setText("Lista de aprovados vazia")
            msg.setInformativeText("Não foi possível encontrar aprovados com os filtros atuais.\nRedefina seus critérios de busca e tente novamente.")
            msg.exec_()  # mostra a caixa de diálogo e espera
            return False
        return True

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

    def abrirAjuda(self):
        print("Abriu botao ajuda")
        widget = AjudaWidget()
        self.ui.tabWidget.addTab(widget, "Ajuda")
        self.ui.tabWidget.setCurrentWidget(widget)
        pass

    def abrirF1(self):
        if not self.verificarLista():
            return 
        
        print("Abriu botao 1")
        widget = graficolinhaWidget()
        self.ui.tabWidget.addTab(widget, "Gráfico Linha")
        self.ui.tabWidget.setCurrentWidget(widget)
        widget.setDados(self.filtrar_dados(), self.filtrosSelecionados())
        pass

    def abrirF2(self):
        if not self.verificarLista():
            return
        
        print("Abriu botao 2")
        widget = GraficoBarra()
        self.ui.tabWidget.addTab(widget, "Grafico Barra")
        self.ui.tabWidget.setCurrentWidget(widget)
        widget.setDados(self.filtrar_dados())
        pass

    def abrirF3(self):
        if not self.verificarLista():
            return
        
        print("Abriu botao 3")
        widget = HistogramaWidget()
        self.ui.tabWidget.addTab(widget, "Histograma de Notas")
        self.ui.tabWidget.setCurrentWidget(widget)
        widget.setDados(self.filtrar_dados(), self.filtrosSelecionados())
        pass

    def abrirF4(self):
        if not self.verificarLista():
            return
        
        print("Abriu botao 4")
        widget = ListaTop10()
        self.ui.tabWidget.addTab(widget, "Lista Top 10")
        self.ui.tabWidget.setCurrentWidget(widget)
        widget.setDados(self.filtrar_dados(), self.filtrosSelecionados())
        pass

    def abrirF5(self):
        if not self.verificarLista():
            return
        
        print("Abriu botao 5")
        widget = TabelaEstados()
        self.ui.tabWidget.addTab(widget, "Análise por Estado")
        self.ui.tabWidget.setCurrentWidget(widget)
        widget.setDados(self.filtrar_dados(), self.filtrosSelecionados())
        pass

    def abrirF6(self):
        if not self.verificarLista():
            return
        
        print("Abriu botao 6")
        widget = AumentoNotaController()
        title = "Comp Nota"
        self.ui.tabWidget.addTab(widget, title)
        self.ui.tabWidget.setCurrentWidget(widget)
        widget.setDados(self.filtrar_dados(), self.ui.filtroDemanda.currentText())
        pass

    def abrirF7(self):
        if not self.verificarLista():
            return
        
        print("Abriu botao 7")
        widget = SimulacaoCursosController()
        title = "Simulacao Cursos"
        self.ui.tabWidget.addTab(widget, title)
        self.ui.tabWidget.setCurrentWidget(widget)

        campusSelecionado = self.ui.filtroCampus.currentText()
        validaCampus = ( campusSelecionado != "Campus")
        widget.setDados(self.filtrar_dados(), self.filtrosSelecionados(), validaCampus)
        pass

    def abrirF8(self):
        if not self.verificarLista():
            return
        print("Abriu botao 8")
        widget = SimulacaoSisuController()
        title = "Simulacao Sisu"
        self.ui.tabWidget.addTab(widget, title)
        self.ui.tabWidget.setCurrentWidget(widget)
        widget.setDados(self.filtrar_dados())
        pass

    def abrirF9(self):
        if not self.verificarLista():
            return
        
        print("Abriu botao 9")
        widget = PizzaDemandaWidget()
        title = "Percentual Demanda"
        self.ui.tabWidget.addTab(widget, title)
        self.ui.tabWidget.setCurrentWidget(widget)

        widget.setDados(self.filtrar_dados(), self.filtrosSelecionados(), self.demandas)
        pass

    def abrirF10(self):
        if not self.verificarLista():
            return
        
        print("Abriu botao 10")
        widget = ConsultaNomeController()
        title = "Consulta Nome"
        self.ui.tabWidget.addTab(widget, title)
        self.ui.tabWidget.setCurrentWidget(widget)

        widget.setDados(self.filtrar_dados(), self.filtrosSelecionados())
        pass

    def fechar_aba(self, index: int):
        if index == 0:
            return
        widget = self.ui.tabWidget.widget(index)
        self.ui.tabWidget.removeTab(index)
        widget.deleteLater()

if __name__ == "__main__":
    app = QApplication(sys.argv)
    window = JanelaInicial()
    sys.exit(app.exec_())