from PyQt5.QtWidgets import QWidget
from visual.ui_ajuda import Ui_Form

class AjudaWidget(QWidget):
    def __init__(self):
        super().__init__()
        self.ui = Ui_Form()  # ou Ui_MainWindow
        self.ui.setupUi(self)