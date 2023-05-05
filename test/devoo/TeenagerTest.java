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
    Teenager t5= new Teenager("t5", LocalDate.of(2000, 1, 1), Country.SPAIN);
    Teenager t6= new Teenager("t6", LocalDate.of(2003, 12, 20), Country.SPAIN);
    Teenager t7= new Teenager("t7", LocalDate.of(2000, 10, 11), Country.GERMANY);
    Teenager t8= new Teenager("t8", LocalDate.of(2000, 4, 9), Country.GERMANY);


    t1.addCriterion(new Criterion("GUEST_HAS_ALLERGY", "yes"));
    t2.addCriterion(new Criterion("HOST_HAS_ANIMAL", "no"));

    @Test
    public void compatibleWithGuesTest() {
        assertTrue(t1.compatibleWithGuest(t2));
    }
}
