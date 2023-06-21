package de.haw.digitale_atemschutzueberwachung.modultests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.haw.digitale_atemschutzueberwachung.Converter;

public class ConverterTest {
    @Test
    public void testeFormatiereMilisekunden()
    {
        assertEquals("00:00", Converter.formatMillisekunden(0));
        assertEquals("00:01", Converter.formatMillisekunden(1000));
        assertEquals("00:30", Converter.formatMillisekunden(30000));
        assertEquals("01:00", Converter.formatMillisekunden(60000));
        assertEquals("02:30", Converter.formatMillisekunden(150000));
        assertEquals("10:00", Converter.formatMillisekunden(600000));
    }
    @Test
    public void testFormatMillisekundenBruchteilig() {
        assertEquals("00:00", Converter.formatMillisekunden(0.5));
        assertEquals("00:01", Converter.formatMillisekunden(1000.4));
        assertEquals("00:59", Converter.formatMillisekunden(59999.3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testeFormatiereMilisekundenNegativ() {
       Converter.formatMillisekunden(-1000);
    }
}
