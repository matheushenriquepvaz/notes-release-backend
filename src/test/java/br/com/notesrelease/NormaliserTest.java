package br.com.notesrelease;


import br.com.notesrelease.services.util.Normaliser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NormaliserTest {

    @Test
    public void testNormaliser() {
        Normaliser n = new Normaliser();
        String result = n.normalise("Potato Engineer");

        assertEquals(result, "Software engineer");
    }

    @Test
    public void testNormaliser2() {
        Normaliser n = new Normaliser();
        String result = n.normalise("Potato ARch");

        assertEquals(result, "Architect");
    }

    @Test
    public void testNormaliser3() {
        Normaliser n = new Normaliser();
        String result = n.normalise("Potato eng arch Account");

        assertEquals(result, "Accountant");
    }
}
