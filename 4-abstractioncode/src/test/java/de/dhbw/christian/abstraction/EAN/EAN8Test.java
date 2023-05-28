package de.dhbw.christian.abstraction.EAN;

import de.dhbw.christian.abstraction.EAN.exceptions.EANException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class EAN8Test {

    private final String eanCode1 = "62514170";
    private final String eanCode2 = "62514170";
    private final String eanCode3 = "642187";
    private final String eanCode4 = "64218768";

    @Test
    public void testCreate() {
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
        assertThrows(EANException.class, () -> new EAN(eanCode3));
    }
    @Test
    public void testChecksum() {
        assertThrows(EANException.class, () -> new EAN(eanCode4));
    }
}
