
from typing import List

class Candidato:

    def __init__(self, partes: List[str]):  
        self.__numero_enem = partes[0]
        self.__nome = partes[1]
        curso_tmp = partes[2]
        campus_tmp = partes[3]
        demanda_tmp = partes[4]
        media_tmp = partes[5]
        self.__colocacao = partes[6]
        self.__estado = partes[7]
        ano_tmp = partes[8]

        self.__curso = self.tratamentoCurso(curso_tmp)
        self.__campus = "CAMPUS DO SERTAO" if campus_tmp == "CAMPUS DO SER" else campus_tmp
        self.__demanda = self.tratamentoDemanda(demanda_tmp)

        try:
            self.__media = float(media_tmp)
        except (ValueError, IndexError):
            self.__media = 0.0
        
        try:
            self.__ano = int(ano_tmp)
        except (ValueError, IndexError):
            self.__ano = 0


    @staticmethod
    def tratamentoCurso(s: str) -> str:
        if not s:
            return ""

        s = s.strip()

        substituicoes = {
            " - ": " ",
            "BACHARELADO": "",
            "LICENCIATURA": "LIC",
            " BAC ": " ",
            "-": "",
            "BAC": "",
            "INTEGRAL": "INT",
            "NOTURNO": "NOT",
            "VESPERTINO": "VESP",
            "(VES)": "(VESP)",
            "MATUTINO": "MAT"
        }

        for antigo, novo in substituicoes.items():
            s = s.replace(antigo, novo)

        excecoes = {
            "MEDICINA VETERINARIA(INT)": "MEDICINA VETERINARIA (INT)",
            "MATEMATICALIC (VESP)": "MATEMATICA LIC (VESP)",
            "LETRAS PORTESPANHOLLIC(VESP)": "LETRAS PORTESPANHOL LIC (VESP)"
        }
        if s in excecoes:
            return excecoes[s]

        if s.startswith("C. D"):
            return "CIENCIA" + s[2:]
        if s.startswith("C."):
            return "CIENCIAS" + s[2:]

        return s

    @staticmethod
    def tratamentoDemanda(s: str) -> str:
        if not s:
            return "Demanda não Identificada"

        s = s.strip()

        mapa = {
            "L1": "Escola pública, baixa renda",
            "LB_EP": "Escola pública, baixa renda",
            "L2": "Escola pública, baixa renda, PPI",
            "LB_PPI": "Escola pública, baixa renda, PPI",
            "L5": "Escola pública",
            "LI_EP": "Escola pública",
            "L6": "Escola pública, PPI",
            "LI_PPI": "Escola pública, PPI",
            "L9": "Escola pública, baixa renda, PcD",
            "LB_PCD": "Escola pública, baixa renda, PcD",
            "L10": "Escola pública, baixa renda, PcD, PPI",
            "L13": "Escola pública, PcD",
            "LI_PCD": "Escola pública, PcD",
            "L14": "Escola pública, PcD, PPI",
            "LI_Q": "Escola pública, quilombola",
            "LB_Q": "Escola pública, baixa renda, quilombola"
        }

        if s.startswith("A"):
            return "Ampla Concorrencia"
        if s.startswith("V"):
            return "PcD"

        return mapa.get(s, "Demanda não Identificada")

    def __str__(self) -> str: #equivalente ao toString
        return (
            f"Numero Enem: {self.__numero_enem}\n"
            f"__nome: {self.__nome}\n"
            f"__curso: {self.__curso}\n"
            f"__campus: {self.__campus}\n"
            f"Colocação: {self.__colocacao}\n"
            f"Média: {self.__media}\n"
            f"__estado: {self.__estado}\n"
            f"__demanda: {self.__demanda}\n"
            f"__ano: {self.__ano}\n"
        )
    
    # getters -> mantem o dado seguro de modificações, mas com simplicidade de acessar com o @property
    # objeto.atributo da pra ler, n da pra mudar.
    @property
    def numero_enem(self) -> str:
        return self.__numero_enem

    @property
    def nome(self) -> str:
        return self.__nome

    @property
    def curso(self) -> str:
        return self.__curso

    @property
    def campus(self) -> str:
        return self.__campus

    @property
    def demanda(self) -> str:
        return self.__demanda

    @property
    def estado(self) -> str:
        return self.__estado

    @property
    def colocacao(self) -> str:
        return self.__colocacao

    @property
    def media(self) -> float:
        return self.__media

    @property
    def ano(self) -> int:
        return self.__ano
