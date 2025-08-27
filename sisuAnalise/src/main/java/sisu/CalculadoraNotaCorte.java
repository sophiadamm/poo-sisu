/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sisu;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author pc
 */
public class CalculadoraNotaCorte {
    public static Map<String, Map<Integer, Double>> calcularNotaCorte(List<Candidato> candidatos, String curso){
        Map<String, Map<Integer, Double>> resultado = new HashMap<>();
        
        for(Candidato c : candidatos){
            if(!c.getCurso().equalsIgnoreCase(curso)) continue;
            
            String campus = c.getCampus();
            int ano = c.getAno();
            double media = c.getAno();
            
            resultado.putIfAbsent(campus, new HashMap<>());
            Map<Integer, Double> porAno = resultado.get(campus);
            
            if(!porAno.containsKey(ano) || media < porAno.get(ano)){
                porAno.put(ano, media);
            }
        }
        return resultado;
    }
    
    public static Map<String, Double> calcularNotaCorte(List<Candidato> candidatos, boolean fcampus){
        Map<String, Double> resultado = new HashMap<>();
        
        for(Candidato c : candidatos){
            String curr = c.getCurso();
            if(!fcampus) curr += (" - " + c.getCampus());
            
            if(resultado.containsKey(curr)){
                resultado.put(curr, Math.min(c.getMedia(), resultado.get(curr)));
            }else resultado.put(curr, c.getMedia());
        }
        return resultado;
    }
}
