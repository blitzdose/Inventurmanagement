package de.dhbw.christian.EAN;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EAN13Test {

    private final String eanCode1 = "1358456248705";
    private final String eanCode2 = "1358456248705";
    private final String eanCode3 = "652458723";
    private final String eanCode4 = "6524587236909";

    @Test
    public void create() {
        EAN ean = new EAN(eanCode1);
        assertEquals(eanCode1, ean.toString());
    }

    @Test
    public void testEquals() {
        EAN ean1 = new EAN(eanCode1);
        EAN ean2 = new EAN(eanCode2);
        assertEquals(ean1, ean2);
    }

    @Test
    public void testLength() {
        assertThrows(RuntimeException.class, () -> new EAN(eanCode3));
    }
    @Test
    public void testChecksum() {
        assertThrows(RuntimeException.class, () -> new EAN(eanCode4));
    }
}
