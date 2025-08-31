
from typing import List, Dict
from candidato import Candidato

class CalculadoraNotaCorte:
    def calcular(candidatos: List[Candidato], fcampus: bool) -> Dict[str, float]:
        resultado = {}

        for c in candidatos or []:
            curso = c.curso
            campus = c.campus

            try:
                media_val = float(c.media)
            except Exception:
                continue

            chave = curso if fcampus else f"{curso} - {campus}"

            if chave in resultado:
                # mantém o menor valor (nota de corte = mínimo)
                resultado[chave] = min(media_val, resultado[chave])
            else:
                resultado[chave] = media_val
        return dict(sorted(resultado.items()))