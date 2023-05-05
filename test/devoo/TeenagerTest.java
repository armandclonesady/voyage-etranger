package devoo;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class TeenagerTest {

    Teenager t1= new Teenager("t1", LocalDate.of(2000, 1, 1), Country.FRANCE);
    Teenager t2= new Teenager("t2", LocalDate.of(2002, 1, 1), Country.FRANCE);
    Teenager t3= new Teenager("t3", LocalDate.of(2004, 1, 1), Country.ITALY);
    Teenager t4= new Teenager("t4", LocalDate.of(2000, 1, 1), Country.ITALY);

    @BeforeEach
    public void init() {
        t1.lastGuest = t2;
        t1.updateCriterion(new Criterion("GUEST_HAS_ALLERGY", "yes"));
        t1.updateCriterion(new Criterion("GUEST_FOOD", "vegetarian"));
        t1.updateCriterion(new Criterion("GENDER", "male"));
        t1.updateCriterion(new Criterion("HISTORY", "same"));
        t1.updateCriterion(new Criterion("HOST_FOOD", "vegetarian"));
        t1.updateCriterion(new Criterion("HOST_HAS_ANIMAL", "vegetarian"));

        
        t3.updateCriterion(new Criterion("HOST_HAS_ANIMAL", "no"));
        t3.updateCriterion(new Criterion("HOST_FOOD", "vegetarian"));
        t3.updateCriterion(new Criterion("PAIR_GENDER", "female"));

        t2.updateCriterion(new Criterion("GUEST_HAS_ALLERGY", "yes"));
        t2.updateCriterion(new Criterion("GUEST_FOOD", "nonuts"));
        t2.updateCriterion(new Criterion("HISTORY", "same"));

        t4.updateCriterion(new Criterion("HISTORY", "OTHER"));

    }

    
    @Test
    public void compatibleWithGuestTest() {
        // Allergie

        // Regime
         
    }
    /*
    @Test
    public void historyCompatibilityTest() {

    }
    */

    @Test
    public void animalCompatibilityTest() {

    }

    @Test
    public void foodCompatibility() {
        t1.updateCriterion(new Criterion("GUEST_FOOD", ""));

        t2.updateCriterion(new Criterion("HOST_FOOD", ""));
        assertTrue(t2.foodCompatibility(t1));
        t2.updateCriterion(new Criterion("HOST_FOOD", "nonuts"));
        assertTrue(t2.foodCompatibility(t1));
        t2.updateCriterion(new Criterion("HOST_FOOD", "vegetarian"));
        assertTrue(t2.foodCompatibility(t1));
        t2.updateCriterion(new Criterion("HOST_FOOD", "nonuts,vegetarian"));


        t1.updateCriterion(new Criterion("GUEST_FOOD", "nonuts"));

        t2.updateCriterion(new Criterion("HOST_FOOD", ""));
        assertTrue(t2.foodCompatibility(t1));
        t2.updateCriterion(new Criterion("HOST_FOOD", "nonuts"));
        assertFalse(t2.foodCompatibility(t1));
        t2.updateCriterion(new Criterion("HOST_FOOD", "vegetarian"));
        assertTrue(t2.foodCompatibility(t1));
        t2.updateCriterion(new Criterion("HOST_FOOD", "nonuts,vegetarian"));
        assertFalse(t2.foodCompatibility(t1));

        t1.updateCriterion(new Criterion("GUEST_FOOD", "vegetarian"));
        
        t2.updateCriterion(new Criterion("HOST_FOOD", ""));
        assertTrue(t2.foodCompatibility(t1));
        t2.updateCriterion(new Criterion("HOST_FOOD", "nonuts"));
        assertFalse(t2.foodCompatibility(t1));
        t2.updateCriterion(new Criterion("HOST_FOOD", "vegetarian"));
        assertTrue(t2.foodCompatibility(t1));
        t2.updateCriterion(new Criterion("HOST_FOOD", "nonuts,vegetarian"));
        assertFalse(t2.foodCompatibility(t1));

        t1.updateCriterion(new Criterion("GUEST_FOOD", "nonuts,vegetarian"));
        
        t2.updateCriterion(new Criterion("HOST_FOOD", ""));
        assertTrue(t2.foodCompatibility(t1));
        t2.updateCriterion(new Criterion("HOST_FOOD", "nonuts"));
        assertFalse(t2.foodCompatibility(t1));
        t2.updateCriterion(new Criterion("HOST_FOOD", "vegetarian"));
        assertFalse(t2.foodCompatibility(t1));
        t2.updateCriterion(new Criterion("HOST_FOOD", "nonuts,vegetarian"));
        assertFalse(t2.foodCompatibility(t1));
    }

    @Test
    public void purgeCriterionTest() {
        assertFalse(t1.getCriterion(CriterionName.HOST_HAS_ANIMAL)==null);
        t1.updateCriterion(new Criterion("HOST_HAS_ANIMAL", "azazaza"));
        t1.purgeCriterion();
        assertTrue(t1.getCriterion(CriterionName.HOST_HAS_ANIMAL)== null);

        assertTrue(t2.getCriterion(CriterionName.GENDER) == null);
        t2.updateCriterion(new Criterion("GENDER", "female"));
        t2.purgeCriterion();
        assertFalse(t2.getCriterion(CriterionName.GENDER)== null);

        assertTrue(t3.getCriterion(CriterionName.GENDER) == null);
        t3.updateCriterion(new Criterion("GENDER", "fefefefe"));
        t3.purgeCriterion();
        assertTrue(t3.getCriterion(CriterionName.GENDER)== null);

        assertFalse(t4.getCriterion(CriterionName.HISTORY) == null);
        t4.updateCriterion(new Criterion("HISTORY", "same"));
        t4.purgeCriterion();
        assertFalse(t4.getCriterion(CriterionName.HISTORY)== null);
    }
}
