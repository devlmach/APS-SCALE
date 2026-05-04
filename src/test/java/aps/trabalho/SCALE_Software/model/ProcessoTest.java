package aps.trabalho.SCALE_Software.model;

import aps.trabalho.SCALE_Software.model.Recurso;
import aps.trabalho.SCALE_Software.model.Tipo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProcessoTest {

    @Test
    void deveAdicionarUmRecurso() {
        Recurso r = new Recurso("Solar", Tipo.r, 100, 2.0);
        processo.adicionarRecurso(r);

        assertEquals(1, processo.entradas.size());
    }

    @Test
    void deveAdicionarMultiplosRecursos() {
        processo.adicionarRecurso(new Recurso("Solar",  Tipo.r,  100, 2.0));
        processo.adicionarRecurso(new Recurso("Carvão", Tipo.nr, 50,  4.0));
        processo.adicionarRecurso(new Recurso("Terra",  Tipo.n,  200, 1.0));

        assertEquals(3, processo.entradas.size());
    }

    @Test
    void emergiaDeveSerZeroSemRecursos() {
        assertEquals(0.0, processo.calcularEmergiaTotal(), 0.001);
    }

    @Test
    void deveCalcularEmergiaTotalComUmRecurso() {
        processo.adicionarRecurso(new Recurso("Solar", "R", 100, 2.0));
        // 100 * 2.0 = 200
        assertEquals(200.0, processo.calcularEmergiaTotal(), 0.001);
    }

    @Test
    void deveCalcularEmergiaTotalComMultiplosRecursos() {
        processo.adicionarRecurso(new Recurso("Solar",  "R",  100, 2.0)); // 200
        processo.adicionarRecurso(new Recurso("Carvão", "NR", 50,  4.0)); // 200
        processo.adicionarRecurso(new Recurso("Terra",  "N",  200, 1.0)); // 200
        // total = 600
        assertEquals(600.0, processo.calcularEmergiaTotal(), 0.001);
    }

    @Test
    void deveCalcularCorretamenteComValoresDecimais() {
        processo.adicionarRecurso(new Recurso("Vento", "R", 33.5, 1.5));
        // 33.5 * 1.5 = 50.25
        assertEquals(50.25, processo.calcularEmergiaTotal(), 0.001);
    }
}
