package devoo;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestFrenchTeenager {
    FrenchTeenager ft1, ft2, ft3, ft4;
    Teenager t1, t2, t3, t4;

    @BeforeEach
    public void setUp() {
        ft1 = new FrenchTeenager("t1", "t1", Country.FRANCE, LocalDate.now(), t2);
        ft2 = new FrenchTeenager("t2", "t2", Country.FRANCE, LocalDate.now(), t1);
        ft3 = new FrenchTeenager("t3", "t3", Country.FRANCE, LocalDate.now());
        ft4 = new FrenchTeenager("t4", "t4", Country.FRANCE, LocalDate.now(), t3);
        
        ft1.updateCriterion(new Criterion("GUEST_ANIMAL_ALLERGY", "yes"));
        ft1.updateCriterion(new Criterion("GUEST_FOOD", "vegetarian"));
        ft1.updateCriterion(new Criterion("GENDER", "male"));
        ft1.updateCriterion(new Criterion("HISTORY", "same"));
        ft1.updateCriterion(new Criterion("HOST_FOOD", "vegetarian"));
        ft1.updateCriterion(new Criterion("HOST_HAS_ANIMAL", "vegetarian"));

        
        ft3.updateCriterion(new Criterion("HOST_HAS_ANIMAL", "no"));
        ft3.updateCriterion(new Criterion("HOST_FOOD", "vegetarian"));
        ft3.updateCriterion(new Criterion("PAIR_GENDER", "female"));

        ft2.updateCriterion(new Criterion("GUEST_ANIMAL_ALLERGY", "yes"));
        ft2.updateCriterion(new Criterion("GUEST_FOOD", "nonuts"));
        ft2.updateCriterion(new Criterion("HISTORY", "same"));

        ft4.updateCriterion(new Criterion("HISTORY", "OTHER"));
        
        t1 = new Teenager("t", "one", Country.ITALY, LocalDate.of(2000, 1, 1));
        t2 = new Teenager("t", "two", Country.ITALY, LocalDate.of(2002, 1, 1));
        t3 = new Teenager("t", "three", Country.ITALY, LocalDate.of(2004, 1, 1));
        t4 = new Teenager("t", "four", Country.ITALY, LocalDate.of(2000, 1, 1));

        t1.updateCriterion(new Criterion("GUEST_ANIMAL_ALLERGY", "yes"));
        t1.updateCriterion(new Criterion("GUEST_FOOD", "vegetarian"));
        t1.updateCriterion(new Criterion("GENDER", "male"));
        t1.updateCriterion(new Criterion("HISTORY", "same"));
        t1.updateCriterion(new Criterion("HOST_FOOD", "vegetarian"));
        t1.updateCriterion(new Criterion("HOST_HAS_ANIMAL", "vegetarian"));

        
        t3.updateCriterion(new Criterion("HOST_HAS_ANIMAL", "no"));
        t3.updateCriterion(new Criterion("HOST_FOOD", "vegetarian"));
        t3.updateCriterion(new Criterion("PAIR_GENDER", "female"));

        t2.updateCriterion(new Criterion("GUEST_ANIMAL_ALLERGY", "yes"));
        t2.updateCriterion(new Criterion("GUEST_FOOD", "nonuts"));
        t2.updateCriterion(new Criterion("HISTORY", "same"));

        t4.updateCriterion(new Criterion("HISTORY", "OTHER"));
    }

    @Test
    public void countryCompatibilityTest() {
        assertTrue(ft1.countryCompatibility(ft2));
        assertTrue(ft1.countryCompatibility(ft3));
        assertTrue(ft1.countryCompatibility(ft4));

        assertFalse(ft1.countryCompatibility(t2));
        assertFalse(ft1.countryCompatibility(t3));
        assertFalse(ft1.countryCompatibility(t3));
    }
}
