package graphes;

import org.junit.jupiter.api.Test;

import devoo.Country;
import devoo.Criterion;
import devoo.Teenager;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestAffectationVersion1 {

    Teenager A, B, C, X, Y, Z, t1, t2, t3, t4, t5, t6;
    Set<Teenager> promo;
    Map<Teenager, Teenager> dico, trueMap;
    
    @BeforeEach
    public void init() {
        promo = new HashSet<Teenager>();
        
        t1 = new Teenager("A", LocalDate.of(2000, 1, 1), Country.FRANCE);
        t2 = new Teenager("B", LocalDate.of(2000, 1, 1), Country.FRANCE);
        t3 = new Teenager("C", LocalDate.of(2000, 1, 1), Country.FRANCE);
        t4 = new Teenager("X", LocalDate.of(2000, 1, 1), Country.ITALY);
        t5 = new Teenager("Y", LocalDate.of(2000, 1, 1), Country.ITALY);
        t6 = new Teenager("Z", LocalDate.of(2000, 1, 1), Country.ITALY);


        A.updateCriterion(new Criterion("HOST_HAS_ANIMAL", "no"));
        A.updateCriterion(new Criterion("GUEST_HAS_ALLERGY", "no"));
        A.updateCriterion(new Criterion("HOBBIES", "sports,technology"));

        B.updateCriterion(new Criterion("HOST_HAS_ANIMAL", "yes"));
        B.updateCriterion(new Criterion("GUEST_HAS_ALLERGY", "yes"));
        B.updateCriterion(new Criterion("HOBBIES", "culture,science"));

        C.updateCriterion(new Criterion("HOST_HAS_ANIMAL", "no"));
        C.updateCriterion(new Criterion("GUEST_HAS_ALLERGY", "no"));
        C.updateCriterion(new Criterion("HOBBIES", "science,reading"));

        X.updateCriterion(new Criterion("HOST_HAS_ANIMAL", "no"));
        X.updateCriterion(new Criterion("GUEST_HAS_ALLERGY", "no"));
        X.updateCriterion(new Criterion("HOBBIES", "culture,technology"));

        Y.updateCriterion(new Criterion("HOST_HAS_ANIMAL", "yes"));
        Y.updateCriterion(new Criterion("GUEST_HAS_ALLERGY", "yes"));
        Y.updateCriterion(new Criterion("HOBBIES", "science,reading"));

        Z.updateCriterion(new Criterion("HOST_HAS_ANIMAL", "no"));
        Z.updateCriterion(new Criterion("GUEST_HAS_ALLERGY", "no"));
        Z.updateCriterion(new Criterion("HOBBIES", "technology"));

        t1 = new Teenager("Z", LocalDate.of(2000, 1, 1), Country.FRANCE,t2);
        t2 = new Teenager("Z", LocalDate.of(2000, 1, 1), Country.ITALY,t1);
        t3 = new Teenager("Z", LocalDate.of(2000, 1, 1), Country.FRANCE,t4);
        t4 = new Teenager("Z", LocalDate.of(2000, 1, 1), Country.ITALY,t3);
        t5 = new Teenager("Z", LocalDate.of(2000, 1, 1), Country.FRANCE,t6);
        t6 = new Teenager("Z", LocalDate.of(2000, 1, 1), Country.ITALY,t5);

        t1.updateCriterion(new Criterion("HISTORY", "same"));
        t2.updateCriterion(new Criterion("HISTORY", "same"));
        t3.updateCriterion(new Criterion("HISTORY", "same"));
        t5.updateCriterion(new Criterion("HISTORY", "other"));


        List<Teenager> host = new ArrayList<Teenager>();
        host.add(A);
        host.add(B);
        host.add(C);
        host.add(t1);
        host.add(t3);
        host.add(t5);

        List<Teenager> guest = new ArrayList<Teenager>();
        guest.add(X);
        guest.add(Y);
        guest.add(Z);
        guest.add(t2);
        guest.add(t4);
        guest.add(t6);
        

        dico = AffectationUtil.compatibilityMap(host,guest);

        trueMap = Map.of(
            t3, t5,
            t1, t4,
            t2, t6);
    }   

    @Test
    public void calculTest(){
        System.out.println(trueMap);
        assertEquals(trueMap, dico);
    }
}
