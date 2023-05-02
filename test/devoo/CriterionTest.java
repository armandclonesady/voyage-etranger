package devoo;


import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class CriterionTest {
    Criterion c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13;

    @BeforeEach
    public void initialization() {
        c1 = new Criterion("yes","GUEST_HAS_ALLERGY");
        c2 = new Criterion("no", "HOST_HAS_ANIMAL");

        c3 = new Criterion("yes", "GUEST_HAS_ALLERGY");
        c4 = new Criterion("non non non", "HOST_HAS_ANIMAL");

        c5 = new Criterion("female", "GENDER");
        c6 = new Criterion("male", "GENDER");
        c7 = new Criterion("other", "GENDER");

        c8 = new Criterion("other", "PAIR_GENDER");
        c9 = new Criterion("mafeme", "PAIR_GENDER");
        c10 = new Criterion("autre", "PAIR_GENDER");

        c11 = new Criterion("same", "HISTORY");
        c12 = new Criterion("other", "HISTORY");
        c13 = new Criterion("AUTRE", "HISTORY");
    }

    @Test
    public void isValidTest() {
        assertEquals(true, c1.isValid());
    }
}
