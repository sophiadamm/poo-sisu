

class auxiliarGrafico:
    def __init__(self, nome_campus):
        self.nome_campus = nome_campus
        self.notas_por_ano = {}
    
    def adicionar_nota(self, ano, nota):
        self.notas_por_ano[ano] = nota