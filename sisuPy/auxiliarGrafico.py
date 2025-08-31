

class AuxiliarGrafico:
    def __init__(self, nome_campus):
        self.nome_campus = nome_campus
        self.notas_por_ano = {}   # {ano: menor_nota}

    def adicionar_nota(self, ano, nota):
        """Armazena a menor nota para o ano.
           Converte ano -> int e nota -> float por segurança."""
        ano = int(ano)
        nota = float(nota)
        atual = self.notas_por_ano.get(ano)
        if atual is None or nota < atual:
            self.notas_por_ano[ano] = nota

    def anos_ordenados(self):
        return sorted(self.notas_por_ano.keys())

    def itens_ordenados(self):
        """Retorna lista de tuplas (ano, nota) ordenadas por ano."""
        return [(ano, self.notas_por_ano[ano]) for ano in self.anos_ordenados()]

    def __repr__(self):
        return f"<AuxiliarGrafico {self.nome_campus} {self.notas_por_ano}>"
    
    @staticmethod
    def agrupar_dados_por_campus(dados, curso_selecionado=None):
        mapas = {}
        for candidato in dados:
            # se quiser filtrar por curso:
            if curso_selecionado is not None and candidato.curso() != curso_selecionado:
                continue

            campus = candidato.campus()
            ano = int(candidato.ano())
            media = float(candidato.media())

            if campus not in mapas:
                mapas[campus] = AuxiliarGrafico(campus)
            mapas[campus].adicionar_nota(ano, media)

        return mapas