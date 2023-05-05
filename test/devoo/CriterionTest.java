package devoo;


import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class CriterionTest {
    Criterion c1; Criterion c2; Criterion c3; 
    Criterion c4; Criterion c5; Criterion c6;
    Criterion c7; Criterion c8; Criterion c9; 
    Criterion c10; Criterion c11; Criterion c12;
    Criterion c13; Criterion c14; Criterion c15; 
    Criterion c16; Criterion c17; Criterion c18;
    Criterion c19; Criterion c20;


/**
 * Y'a des champs qu'on peut laisser vide mais on les as pas prit en compte pour le moment je crois (genre History ou Hobbies)
 */

    @BeforeEach
    public void initTest() {
        c1 = new Criterion("GUEST_HAS_ALLERGY", "yes");
        c2 = new Criterion( "HOST_HAS_ANIMAL", "no");
        c3 = new Criterion( "GUEST_HAS_ALLERGY" ,"yes");
        c4 = new Criterion( "HOST_HAS_ANIMAL", "non");
        c5 = new Criterion( "GUEST_HAS_ALLERGY", "oui");
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
        c18 = new Criterion( "PAIR_GENDER", "HOMME");
        c19 = new Criterion( "GENDER", "other");
        c20 = new Criterion( "GENDER", "FEMME");
    }
         
    @Test
    public void isValidTest() {
        assertEquals(true, c1.isValid());
        assertEquals(true, c2.isValid());
        assertEquals(true, c3.isValid());
        assertEquals(false, c4.isValid());
        assertEquals(false, c5.isValid());
        assertEquals(false, c6.isValid());

        assertEquals(true, c7.isValid());
        assertEquals(false, c8.isValid());
        assertEquals(true, c9.isValid());
        assertEquals(false, c10.isValid());
        
        assertEquals(true, c11.isValid());
        assertEquals(true, c12.isValid());
        assertEquals(false, c13.isValid());
        assertEquals(false, c14.isValid());

        assertEquals(true, c15.isValid());
        assertEquals(false, c16.isValid());
        assertEquals(true, c17.isValid());
        assertEquals(false, c18.isValid());
    }
}