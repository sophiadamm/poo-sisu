import sys
from PyQt5 import QtWidgets
from PyQt5.QtWidgets import QApplication, QMainWindow
from visual.ui_janelaprincipal import Ui_MainWindow

class App(QMainWindow):
    def __init__(self):
        super().__init__()

        # Instancia a UI gerada
        self.ui = Ui_MainWindow()
        self.ui.setupUi(self)  # monta a interface dentro deste QMainWindow

        # Agora você pode acessar widgets via self.ui.nome_do_widget
        self.setWindowTitle("Análise dos dados do Sisu")
        self.setMinimumSize(600, 600)
        self.resize(600, 600)  # Define o tamanho inicial da janela
        self.show()

# Para iniciar a aplicação
if __name__ == "__main__":
    app = QApplication(sys.argv)
    
    # Cria a instância da nossa classe App
    window = App()

    # Inicia o loop de eventos da aplicação
    sys.exit(app.exec_())