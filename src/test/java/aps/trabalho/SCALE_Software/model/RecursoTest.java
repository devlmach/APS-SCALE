package aps.trabalho.SCALE_Software.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RecursoTest {

    @Test
    void TipoShouldBeUpperCase_ShouldFail(){
        Recurso r = new Recurso("Solar", Tipo.R, 100, 10^6);
        assertEquals(Tipo.R, r.tipo);
    }

    @Test
    void allParametersShouldNotBeNull(){
        Recurso r = new Recurso("Solar", Tipo.R, 100, 10^6);
        assertNotNull(r);
    }
}
