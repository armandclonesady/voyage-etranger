
package devoo;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;

public class TestCriterion {
    Criterion c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15, c16, c17, c18, c19, c20, c21, c22, c23;
    Criterion[] cList;

    @BeforeEach
    public void initTest() {
        c1 = new Criterion( "HOST_HAS_ANIMAL", "no");
        c2 = new Criterion( "HOST_HAS_ANIMAL", "non");
        c3 = new Criterion( "HOST_HAS_ANIMAL", "");
        c4 = new Criterion("GUEST_ANIMAL_ALLERGY", "yes");
        c5 = new Criterion( "GUEST_ANIMAL_ALLERGY", "oui");
        c6 = new Criterion( "GUEST_ANIMAL_ALLERGY", "");

        c7 = new Criterion("HOST_FOOD", "nonuts");
        c8 = new Criterion( "HOST_FOOD", "nonuts,vegetarian");
        c9 = new Criterion( "HOST_FOOD", "AUCUNENOIX");
        c10 = new Criterion( "HOST_FOOD", "");
        c11 = new Criterion( "GUEST_FOOD" ,"vegetarian");
        c12 = new Criterion( "GUEST_FOOD", "vegetarian,nonuts");
        c13 = new Criterion( "GUEST_FOOD", "AUCUNEVIANDE");
        c14 = new Criterion( "GUEST_FOOD", "");

        c15 = new Criterion( "HISTORY", "same");
        c16 = new Criterion( "HISTORY", "other");
        c17 = new Criterion( "HISTORY", "AUTRE");
        c18 = new Criterion( "HISTORY", "PAREIL");

        c19 = new Criterion( "PAIR_GENDER", "female");
        c20 = new Criterion( "PAIR_GENDER", "Homme");
        c21 = new Criterion("PAIR_GENDER", "");
        c22 = new Criterion( "GENDER", "other");
        c23 = new Criterion( "GENDER", "FEMME");

        cList = new Criterion[] {c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12,c13,c14,c15,c16,c17,c18,c19,c20,c21,c22,c23};
    }
    
    @Test
    public void isValidTest() {
        List<String> errors = new ArrayList<String>();
        for (Criterion c : cList) {
            try {
                c.isValid();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                errors.add(e.getMessage());
            }
        }
        assertEquals(10, errors.size());
    }

    @Test
    public void errorMsgTest() {
        assertEquals("Valeur \"non\" incorrect | Valeurs possibles (HOST_HAS_ANIMAL) : [yes, no]", c2.errorMsg());
    }
}