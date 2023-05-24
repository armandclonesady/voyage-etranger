
package devoo;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class TestCriterion {
    Criterion c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15, c16, c17, c18, c19, c20, c21;

/**
 * Y'a des champs qu'on peut laisser vide mais on les as pas prit en compte pour le moment je crois (genre History ou Hobbies)
 */

    @BeforeEach
    public void initTest() {
        c1 = new Criterion("GUEST_ANIMAL_ALLERGY", "yes");
        c2 = new Criterion( "HOST_HAS_ANIMAL", "no");
        c3 = new Criterion( "GUEST_ANIMAL_ALLERGY" ,"yes");
        c4 = new Criterion( "HOST_HAS_ANIMAL", "non");
        c5 = new Criterion( "GUEST_ANIMAL_ALLERGY", "oui");
        c6 = new Criterion( "HOST_HAS_ANIMAL", "noN");

        c7 = new Criterion("HOST_FOOD", "nonuts");
        c8 = new Criterion( "HOST_FOOD", "AUCUNENOIX");
        c9 = new Criterion( "HOST_FOOD", "");
        c10 = new Criterion( "GUEST_FOOD" ,"vegetarian");
        c11 = new Criterion( "GUEST_FOOD", "AUCUNEVIANDE");
        c12 = new Criterion( "GUEST_FOOD", "");

        c13 = new Criterion( "HISTORY", "same");
        c14 = new Criterion( "HISTORY", "other");
        c15 = new Criterion( "HISTORY", "AUTRE");
        c16 = new Criterion( "HISTORY", "PAREIL");

        c17 = new Criterion( "PAIR_GENDER", "female");
        c18 = new Criterion( "PAIR_GENDER", "Homme");
        c19 = new Criterion("PAIR_GENDER", "");
        c20 = new Criterion( "GENDER", "other");
        c21 = new Criterion( "GENDER", "FEMME");
        
    }
    
    @Test
    public void isValidTest() {
        Criterion[] cList = new Criterion[] {c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12,c13,c14,c15,c16,c17,c18,c19,c20,c21};
        for (Criterion criterion : cList) {
            try {
                criterion.isValid();
            } catch (CriterionException e) {
                assertEquals(criterion.errorMsg(), e.getMessage());
            }   
        }
    }
}