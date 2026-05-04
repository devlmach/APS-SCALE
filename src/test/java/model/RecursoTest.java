package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RecursoTest {

    @Test
    void TipoShouldBeUpperCase_ShouldFail(){
        Recurso r = new Recurso("Solar", "r", 100, 10^6);
        assertEquals("R", r.getTipo());
    }

    @Test
    void allParametersShouldNotBeNull(){
        Recurso r = new Recurso("Solar", "r", 100, 10^6);
        assertNotNull(r);
    }
}
