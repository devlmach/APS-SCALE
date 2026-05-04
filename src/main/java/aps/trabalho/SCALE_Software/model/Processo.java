package aps.trabalho.SCALE_Software.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class Processo {

    @NotBlank
    public String nome;

    public List<Recurso> recursos = new ArrayList<>();

    public void adicionarRecurso(Recurso recurso){
        recursos.add(recurso);
    }

    public double calculaEmergiaTotal(){
        double total = 0;
        for (Recurso recurso : recursos){
            total += recurso.calcularEmergia();
        }

        return total;
    }
}
