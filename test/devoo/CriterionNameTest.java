package devoo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CriterionNameTest {

    @Test
    void getTypeTest() {
        assertEquals('B', CriterionName.GUEST_HAS_ALLERGY.getType());
        assertEquals('B', CriterionName.HOST_HAS_ANIMAL.getType());
        assertEquals('T', CriterionName.GUEST_FOOD.getType());
        assertEquals('T', CriterionName.HOST_FOOD.getType());
        assertEquals('T', CriterionName.HOBBIES.getType());
        assertEquals('T', CriterionName.GENDER.getType());
        assertEquals('T', CriterionName.PAIR_GENDER.getType());
        assertEquals('T', CriterionName.HISTORY.getType());
    }

}