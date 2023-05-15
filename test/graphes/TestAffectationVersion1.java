package graphes;

import org.junit.jupiter.api.Test;

import devoo.Country;
import devoo.Criterion;
import devoo.Teenager;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class TestAffectationVersion1 {

    Teenager t1, t2, t3, t4, t5, t6;
    Set<Teenager> promo;
    
    @BeforeEach
    public void init() {
        promo = new HashSet<Teenager>();
        
        t1 = new Teenager("A", LocalDate.of(2000, 1, 1), Country.FRANCE);
        t2 = new Teenager("B", LocalDate.of(2000, 1, 1), Country.FRANCE);
        t3 = new Teenager("C", LocalDate.of(2000, 1, 1), Country.FRANCE);
        t4 = new Teenager("X", LocalDate.of(2000, 1, 1), Country.ITALY);
        t5 = new Teenager("Y", LocalDate.of(2000, 1, 1), Country.ITALY);
        t6 = new Teenager("Z", LocalDate.of(2000, 1, 1), Country.ITALY);

        t1.updateCriterion(new Criterion("HOST_HAS_ANIMAL", "no"));
        t2.updateCriterion(new Criterion("HOST_HAS_ANIMAL", "yes"));
        t3.updateCriterion(new Criterion("HOST_HAS_ANIMAL", "no"));
        t1.updateCriterion(new Criterion("GUEST_HAS_ALLERGY", "no"));
        t2.updateCriterion(new Criterion("GUEST_HAS_ALLERGY", "yes"));
        t3.updateCriterion(new Criterion("GUEST_HAS_ALLERGY", "no"));
        t4.updateCriterion(new Criterion("HOST_HAS_ANIMAL", "no"));
        t5.updateCriterion(new Criterion("HOST_HAS_ANIMAL", "yes"));
        t6.updateCriterion(new Criterion("HOST_HAS_ANIMAL", "no"));
        t4.updateCriterion(new Criterion("GUEST_HAS_ALLERGY", "no"));
        t5.updateCriterion(new Criterion("GUEST_HAS_ALLERGY", "yes"));
        t6.updateCriterion(new Criterion("GUEST_HAS_ALLERGY", "no"));

        promo.add(t1);
        promo.add(t2);
        promo.add(t3);
        promo.add(t4);
        promo.add(t5);
        promo.add(t6);
    }   

    @Test
    public void calculTest() {
        
    }
}
