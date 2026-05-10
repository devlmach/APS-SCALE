package aps.trabalho.SCALE_Software.model;

public class Recurso {

    public String nome;

    public Tipo tipo;

    public double quantidade;

    public double uev;

    public Recurso(String nome, Tipo tipo, double quantidade, double uev){
        this.nome = nome;
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.uev = uev;
    }

    public double calcularEmergia() {
        return quantidade * uev;
    }
}

