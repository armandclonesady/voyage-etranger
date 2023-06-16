package graphes;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.Map;

import org.junit.jupiter.api.Test;

import devoo.Country;
import devoo.Criterion;
import devoo.CriterionName;
import devoo.Platform;
import devoo.Teenager;

public class TestAffectationVersion2 {
    
    Teenager t1, t2, t3, t4, t5, t6, t7, t8;
    Platform p1;
    Map<Teenager, Teenager> trueValuep1;

    @Test
    public void testExemple1() {

        p1 = new Platform();

        t1 = new Teenager("ItaTeen", "A", Country.ITALY, LocalDate.of(2000, 1, 1));
        t2 = new Teenager("ItaTeen", "B", Country.ITALY, LocalDate.of(2000, 1, 1));
        t3 = new Teenager("ItaTeen", "C", Country.ITALY, LocalDate.of(2000, 1, 1));
        t4 = new Teenager("ItaTeen", "D", Country.ITALY, LocalDate.of(2000, 1, 1));
        t5 = new Teenager("GerTeen", "E", Country.GERMANY, LocalDate.of(2000, 1, 1));
        t6 = new Teenager("GerTeen", "F", Country.GERMANY, LocalDate.of(2000, 1, 1));
        t7 = new Teenager("GerTeen", "G", Country.GERMANY, LocalDate.of(2000, 1, 1));
        t8 = new Teenager("GerTeen", "H", Country.GERMANY, LocalDate.of(2000, 1, 1));
        
        t1.updateCriterion(new Criterion(CriterionName.HISTORY, "other"));
        t2.updateCriterion(new Criterion(CriterionName.HISTORY, ""));
        t3.updateCriterion(new Criterion(CriterionName.HISTORY, "same"));
        t4.updateCriterion(new Criterion(CriterionName.HISTORY, "same"));
        t5.updateCriterion(new Criterion(CriterionName.HISTORY, ""));
        t6.updateCriterion(new Criterion(CriterionName.HISTORY, "other"));
        t7.updateCriterion(new Criterion(CriterionName.HISTORY, "same"));
        t8.updateCriterion(new Criterion(CriterionName.HISTORY, "other"));

        p1.addStudents(t1);
        p1.addStudents(t2);
        p1.addStudents(t3);
        p1.addStudents(t4);
        p1.addStudents(t5);
        p1.addStudents(t6);
        p1.addStudents(t7);
        p1.addStudents(t8);

        trueValuep1 = Map.of (
            t1, t6,
            t2, t8,
            t3, t7,
            t4, t5
        );

        t1.setLastguest(t5);
        t2.setLastguest(t6);
        t3.setLastguest(t7);
        t4.setLastguest(t8);

        p1.affectation(Country.ITALY, Country.GERMANY);

        System.out.println("Exemple 1");
        for (Map.Entry<Teenager, Teenager> entry : p1.getCurrentAffectation().entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
            assertEquals(trueValuep1.get(entry.getKey()), entry.getValue());
        }
    }

    @Test
    public void testExemple2() {
            
        p1 = new Platform();

        t1 = new Teenager("ItaTeen", "A", Country.ITALY, LocalDate.of(2000, 1, 1));
        t2 = new Teenager("ItaTeen", "B", Country.ITALY, LocalDate.of(2000, 1, 1));
        t3 = new Teenager("ItaTeen", "C", Country.ITALY, LocalDate.of(2000, 1, 1));
        t4 = new Teenager("ItaTeen", "D", Country.ITALY, LocalDate.of(2000, 1, 1));
        t5 = new Teenager("GerTeen", "E", Country.GERMANY, LocalDate.of(2000, 1, 1));
        t6 = new Teenager("GerTeen", "F", Country.GERMANY, LocalDate.of(2000, 1, 1));
        t7 = new Teenager("GerTeen", "G", Country.GERMANY, LocalDate.of(2000, 1, 1));
        t8 = new Teenager("GerTeen", "H", Country.GERMANY, LocalDate.of(2000, 1, 1));
        
        t1.updateCriterion(new Criterion(CriterionName.HISTORY, "other"));
        t2.updateCriterion(new Criterion(CriterionName.HISTORY, ""));
        t3.updateCriterion(new Criterion(CriterionName.HISTORY, "same"));
        t4.updateCriterion(new Criterion(CriterionName.HISTORY, "same"));
        t5.updateCriterion(new Criterion(CriterionName.HISTORY, ""));
        t6.updateCriterion(new Criterion(CriterionName.HISTORY, "other"));
        t7.updateCriterion(new Criterion(CriterionName.HISTORY, "same"));
        t8.updateCriterion(new Criterion(CriterionName.HISTORY, "other"));
  
        t1.updateCriterion(new Criterion(CriterionName.HOBBIES, "science,culture,reading"));
        t2.updateCriterion(new Criterion(CriterionName.HOBBIES, ""));
        t3.updateCriterion(new Criterion(CriterionName.HOBBIES, "technology"));
        t4.updateCriterion(new Criterion(CriterionName.HOBBIES, "science,language"));
        t5.updateCriterion(new Criterion(CriterionName.HOBBIES, "technology,language"));
        t6.updateCriterion(new Criterion(CriterionName.HOBBIES, "culture,technology"));
        t7.updateCriterion(new Criterion(CriterionName.HOBBIES, "culture"));
        t8.updateCriterion(new Criterion(CriterionName.HOBBIES, "science,culture,reading"));


        p1.addStudents(t1);
        p1.addStudents(t2);
        p1.addStudents(t3);
        p1.addStudents(t4);
        p1.addStudents(t5);
        p1.addStudents(t6);
        p1.addStudents(t7);
        p1.addStudents(t8);

        trueValuep1 = Map.of (
            t1, t8,
            t2, t5,
            t3, t7,
            t4, t6
        );

        t1.setLastguest(t5);
        t2.setLastguest(t6);
        t3.setLastguest(t7);
        t4.setLastguest(t8);

        p1.affectation(Country.ITALY, Country.GERMANY);

        System.out.println("Exemple 2");
        for (Map.Entry<Teenager, Teenager> entry : p1.getCurrentAffectation().entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
            assertEquals(trueValuep1.get(entry.getKey()), entry.getValue());
        }
    }
}
