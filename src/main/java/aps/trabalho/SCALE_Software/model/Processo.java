package aps.trabalho.SCALE_Software.model;

import java.util.ArrayList;
import java.util.List;

public class Processo {

    public String nome;

    public List<Recurso> recursos = new ArrayList<>();

    public Processo(String nome){
        this.nome = nome;
    }

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
