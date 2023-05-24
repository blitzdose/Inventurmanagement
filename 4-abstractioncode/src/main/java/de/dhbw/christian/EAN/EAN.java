package de.dhbw.christian.EAN;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class EAN implements Serializable {
    private String ean;

    protected EAN() {}

    public EAN(String ean) throws RuntimeException {
        validateEAN(ean);
        this.ean = ean;
    }

    public void validateEAN(String ean) throws RuntimeException {
        if (!ean.matches("^[0-9]{13}$|^[0-9]{8}$")) {
            throw new RuntimeException("Ungültiges EAN-Format");
        }
        if (!checkChecksum(ean)) {
            throw new RuntimeException("Ungültige Prüfsumme");
        }
    }

    public boolean checkChecksum(String ean) {
        int factor = 3;
        int sum = 0;
        char[] charArray = ean.substring(0, ean.length() - 1).toCharArray();
        for (int i = charArray.length-1; i >= 0; i--) {
            char c = charArray[i];
            int digit = Integer.parseInt(String.valueOf(c));
            sum += digit * factor;
            factor = (factor + 2) % 4;
        }

        int checksum = (10 - (sum % 10)) % 10;

        return checksum == Integer.parseInt(ean.substring(ean.length()-1));
    }

    @Override
    public int hashCode() {
        return ean.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EAN ean1) {
            return ean1.ean.equals(this.ean);
        }
        return false;
    }

    @Override
    public String toString() {
        return ean;
    }
}
