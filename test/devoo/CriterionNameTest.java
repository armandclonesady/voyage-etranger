package devoo;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class CriterionNameTest {
    /**
     * Il n'y a pas plus a mettre dans ce test car c'est un test sur des enum.
     */
    @Test
    void getTypeTest() {
        assertEquals('B', CriterionName.GUEST_HAS_ALLERGY.getType());
        assertEquals('B', CriterionName.HOST_HAS_ANIMAL.getType());
        assertEquals('T', CriterionName.GUEST_FOOD.getType());
        assertEquals('T', CriterionName.HOST_FOOD.getType());
        assertEquals('T', CriterionName.HOBBIES.getType());
        assertEquals('T', CriterionName.GENDER.getType());
        assertNotEquals('B', CriterionName.HISTORY.getType());
        assertNotEquals('B', CriterionName.PAIR_GENDER.getType());
    }

}