from PyQt5.QtWidgets import QTableWidgetItem
from PyQt5.QtCore import Qt

"""
Classe (que herda o QTableWidgetItem tradicional) para adaptar a ordenação nas tabelas.
text - texto que é apresentado na célula
value - o valor numérico que eu quero usar pra comparar e ordenar dps
"""

class NumericItem(QTableWidgetItem):
    def __init__(self, text: str, value: float):
        super().__init__(text)
        self.setData(Qt.UserRole, float(value))

    def _numeric_value_of(self, item):
        v = item.data(Qt.UserRole)
        if v is not None:
            return float(v)
        else:
            return 0.0

    def __lt__(self, other):
        if not isinstance(other, QTableWidgetItem):
            return super().__lt__(other)

        a = self._numeric_value_of(self)
        b = self._numeric_value_of(other)

        if a is None or b is None:
            return super().__lt__(other)
        
        return a < b