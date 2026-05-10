package aps.trabalho.SCALE_Software.service;

import aps.trabalho.SCALE_Software.model.Recurso;

import java.util.List;

public class CalculadoraEmergia {

    public static double calcularTotal(List<Recurso> recursos){
        return recursos.stream()
                .mapToDouble(Recurso::calcularEmergia)
                .sum();
    }
}
