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
        t1.addCriterion(new Criterion("GUEST_HAS_ALLERGY", "yes"));
        t1.addCriterion(new Criterion("GUEST_FOOD", "vegetarian"));
        t1.addCriterion(new Criterion("GENDER", "male"));
        t1.addCriterion(new Criterion("HISTORY", "same"));
        t1.addCriterion(new Criterion("HOST_FOOD", "vegetarian"));

        
        t3.addCriterion(new Criterion("HOST_HAS_ANIMAL", "no"));
        t3.addCriterion(new Criterion("HOST_FOOD", "vegetarian"));
        t3.addCriterion(new Criterion("PAIR_GENDER", "female"));

        t2.addCriterion(new Criterion("GUEST_HAS_ALLERGY", "yes"));
        t2.addCriterion(new Criterion("GUEST_FOOD", "nonuts"));
        t2.addCriterion(new Criterion("HISTORY", "same"));

        t4.addCriterion(new Criterion("HISTORY", "OTHER"));

    }

    @Test
    public void compatibleWithGuesTest() {
        assertTrue(t3.compatibleWithGuest(t1));
        assertTrue(t1.compatibleWithGuest(t2)); //devrait pas passer
        assertFalse(t3.compatibleWithGuest(t2));//devrait pas passer
         
    }
}
