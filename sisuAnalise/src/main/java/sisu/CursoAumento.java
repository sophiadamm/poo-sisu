package sisu;
// Modelo: uma linha da tabela

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
//!Lembrete - Properties do JavaFX são o que tornam os valores observáveis para poder colocar na tabela.

public class CursoAumento {
    private final SimpleStringProperty curso;
    private final SimpleDoubleProperty aumentoPercent;
    private final SimpleDoubleProperty aumentoBruto;
    private final SimpleDoubleProperty notaInicial;
    private final SimpleDoubleProperty notaFinal;

    public CursoAumento(String curso, double notaInicial, double notaFinal) {
        this.curso = new SimpleStringProperty(curso);

        double bruto = notaFinal - notaInicial;
        double pct = (bruto / notaInicial) * 100.0;

        // Formata os valores para duas casas decimais antes de atribuir
        this.aumentoBruto = new SimpleDoubleProperty(Double.parseDouble(String.format("%.2f", bruto).replace(',', '.')));
        this.aumentoPercent = new SimpleDoubleProperty(Double.parseDouble(String.format("%.2f", pct).replace(',', '.')));
        this.notaInicial = new SimpleDoubleProperty(Double.parseDouble(String.format("%.2f", notaInicial).replace(',', '.')));
        this.notaFinal = new SimpleDoubleProperty(Double.parseDouble(String.format("%.2f", notaFinal).replace(',', '.')));
    }

    // getters convencionais (para PropertyValueFactory)
    public String getCurso() { return curso.get(); }
    public double getAumentoPercent() { return aumentoPercent.get(); }
    public double getAumentoBruto() { return aumentoBruto.get(); }
    public double getNotaInicial() { return notaInicial.get(); }
    public double getNotaFinal() { return notaFinal.get(); }
}
