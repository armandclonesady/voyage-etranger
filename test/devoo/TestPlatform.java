package devoo;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

public class TestPlatform {
    Platform p;

    @Test
    public void affectationTest() {
        p = new Platform();
        p.importCSV(new File(Platform.ressourcesPath + "adosAléatoires.csv"));
        for (int i = 0; i < Country.values().length ; i++) {
            Country host = Country.values()[i];
            for (int j = 0 ; j < Country.values().length ; j++) {
                Country guest = Country.values()[j];
                if (host != guest) {
                    p.affectation(host, guest);
                    for (Teenager t : p.getCurrentAffectation().keySet()) {
                        assertEquals(host, t.getCountry());
                    }
                    for (Teenager t : p.getCurrentAffectation().keySet()) {
                        assertEquals(guest, p.getCurrentAffectation().get(t).getCountry());
                    }
                }
            }
        }
    }
}
