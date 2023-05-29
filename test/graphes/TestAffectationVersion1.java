package graphes;

import org.junit.jupiter.api.Test;

import devoo.Country;
import devoo.Criterion;
import devoo.Platform;
import devoo.Teenager;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Map;

public class TestAffectationVersion1 {

    Teenager A, B, C, X, Y, Z;
    Platform p1;
    Map<Teenager, Teenager> trueValuep1;
    
    @BeforeEach
    public void init() {
        p1 = new Platform();
        
        A = new Teenager("t", "A", Country.FRANCE, LocalDate.of(2000, 1, 1));
        B = new Teenager("t", "B", Country.FRANCE, LocalDate.of(2000, 1, 1));
        C = new Teenager("t", "C", Country.FRANCE, LocalDate.of(2000, 1, 1));
        X = new Teenager("t", "X", Country.ITALY, LocalDate.of(2000, 1, 1));
        Y = new Teenager("t", "Y", Country.ITALY, LocalDate.of(2000, 1, 1));
        Z = new Teenager("t", "Z", Country.ITALY, LocalDate.of(2000, 1, 1));
        
        A.updateCriterion(new Criterion("HOST_HAS_ANIMAL", "no"));
        A.updateCriterion(new Criterion("GUEST_ANIMAL_ALLERGY", "no"));
        A.updateCriterion(new Criterion("HOBBIES", "sports,technology"));

        B.updateCriterion(new Criterion("HOST_HAS_ANIMAL", "yes"));
        B.updateCriterion(new Criterion("GUEST_ANIMAL_ALLERGY", "yes"));
        B.updateCriterion(new Criterion("HOBBIES", "culture,science"));

        C.updateCriterion(new Criterion("HOST_HAS_ANIMAL", "no"));
        C.updateCriterion(new Criterion("GUEST_ANIMAL_ALLERGY", "no"));
        C.updateCriterion(new Criterion("HOBBIES", "science,reading"));

        X.updateCriterion(new Criterion("HOST_HAS_ANIMAL", "no"));
        X.updateCriterion(new Criterion("GUEST_ANIMAL_ALLERGY", "no"));
        X.updateCriterion(new Criterion("HOBBIES", "culture,technology"));

        Y.updateCriterion(new Criterion("HOST_HAS_ANIMAL", "yes"));
        Y.updateCriterion(new Criterion("GUEST_ANIMAL_ALLERGY", "yes"));
        Y.updateCriterion(new Criterion("HOBBIES", "science,reading"));

        Z.updateCriterion(new Criterion("HOST_HAS_ANIMAL", "no"));
        Z.updateCriterion(new Criterion("GUEST_ANIMAL_ALLERGY", "no"));
        Z.updateCriterion(new Criterion("HOBBIES", "technology"));

        p1.addStudents(A);
        p1.addStudents(B);
        p1.addStudents(C);
        p1.addStudents(X);
        p1.addStudents(Y);
        p1.addStudents(Z);
        
        p1.affectation(Country.FRANCE, Country.ITALY);

        trueValuep1 = Map.of(
            B, X,
            A, Z,
            C, Y);
    }   

    @Test
    public void affectationExempleTest(){
        assertEquals(trueValuep1, p1.getAffectation());
    }
}
