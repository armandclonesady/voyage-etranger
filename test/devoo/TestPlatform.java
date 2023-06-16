package devoo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class TestPlatform {
    Platform p;

    @Test
    public void affectationTest() {
        p = new Platform();
        p.importCSV(new File("C:\\Users\\clone\\OneDrive\\Documents\\!PROG\\sae1.02\\D2\\rsc\\adosAléatoires.csv"));
        for (int i = 0; i < Country.values().length ; i++) {
            Country host = Country.values()[i];
            for (int j = 0 ; j < Country.values().length ; j++) {
                Country guest = Country.values()[j];
                if (host != guest) {
                    p.affectation(host, guest);
                    for (Teenager t : p.getAffectation().keySet()) {
                        assertEquals(host, t.getCountry());
                    }
                    for (Teenager t : p.getAffectation().keySet()) {
                        assertEquals(guest, p.getAffectation().get(t).getCountry());
                    }
                }
            }
        }
    }
}
