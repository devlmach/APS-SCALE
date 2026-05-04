package aps.trabalho.SCALE_Software.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Recurso {

    @NotBlank
    public String nome;

    @NotBlank
    public Tipo tipo;

    @NotNull
    public double quantidade;

    @NotNull
    public double uev;

    public double calcularEmergia() {
        return quantidade * uev;
    }
}

