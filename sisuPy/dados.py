import os
from typing import List
from candidato import Candidato
import importlib.resources as pkg_resources

class Dados:
    __instancia = None

    def __init__(self):
        self.__lista_candidatos: List[Candidato] = []
        self._load_csv()

    def _load_csv(self):
        caminho_arquivo = os.path.join("resources", "sisuDados.csv")
        try:
            with open(caminho_arquivo, 'r', encoding='utf-8') as f:
                for linha in f:
                        partes = linha.strip().split(",")
                        candidato = Candidato(partes)
                        self.__lista_candidatos.append(candidato)
        except FileNotFoundError:
            print(f"Erro: O arquivo não foi encontrado.")
            # Você pode levantar uma exceção aqui ou lidar com o erro
        except Exception as e:
            print(f"Ocorreu um erro ao ler o arquivo: {e}")

    @classmethod
    def get_instancia(cls):
        if cls.__instancia is None:
            cls.__instancia = cls()
        return cls.__instancia

    def get_lista_candidatos(self) -> List[Candidato]:
        return self.__lista_candidatos
