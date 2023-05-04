package devoo;


import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class CriterionTest {

    @Test
    public void isValidTest() {
        Criterion c1 = new Criterion("GUEST_HAS_ALLERGY", "yes"); 
        assertEquals(true, c1.isValid());
        Criterion c2 = new Criterion( "HOST_HAS_ANIMAL", "no");
        assertEquals(true, c2.isValid());
        Criterion c3 = new Criterion( "GUEST_HAS_ALLERGY" ,"yes");
        assertEquals(true, c3.isValid());
        Criterion c4 = new Criterion( "HOST_HAS_ANIMAL", "non");
        assertEquals(false, c4.isValid());
        Criterion c5 = new Criterion( "GUEST_HAS_ALLERGY", "oui");
        assertEquals(false, c5.isValid());
        Criterion c6 = new Criterion( "HOT_HAS_ANIMAL", "no");
        assertEquals(false, c6.isValid());
    }
}
