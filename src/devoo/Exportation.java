package devoo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Exportation {

    final static String sourcePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "src" + System.getProperty("file.separator") + "devoo" + System.getProperty("file.separator");
    final static String victimPath = "Output.txt";

    static String extractValuesHost(Teenager teenager) {
        return ""+ teenager + ", " + teenager.getForename() +", " + teenager.getId() + ", " +
        teenager.getCriterion(CriterionName.GENDER)+ ", " + teenager.getCriterion(CriterionName.HOST_FOOD) + ", " +
        teenager.getCriterion(CriterionName.HOST_HAS_ANIMAL) + ", " + teenager.getCriterion(CriterionName.HOBBIES) + ", " +
        teenager.getCriterion(CriterionName.HISTORY) + ", " + teenager.getCriterion(CriterionName.PAIR_GENDER);
    }

    static String extractValuesGuest(Teenager teenager) {
        return ""+ teenager + ", " + teenager.getForename() + ", " + teenager.getId() + ", " + 
        teenager.getCriterion(CriterionName.GENDER)+ ", " + teenager.getCriterion(CriterionName.GUEST_FOOD) + ", " + 
        teenager.getCriterion(CriterionName.GUEST_HAS_ALLERGY) +", " + teenager.getCriterion(CriterionName.HOBBIES) + ", " +
        teenager.getCriterion(CriterionName.HISTORY) + ", " + teenager.getCriterion(CriterionName.PAIR_GENDER);
    }

    public static void main(String[] args) {
        System.out.println("\n"+sourcePath+victimPath);
        
        Map<Teenager,Teenager> teenagerMap = new HashMap<Teenager,Teenager>();
        Teenager armand = new Teenager("Armand", LocalDate.now(), Country.FRANCE);
        Teenager noa = new Teenager("Noa", LocalDate.of(2004, 1, 1), Country.GERMANY);
        Teenager antoine = new Teenager("Antoine", LocalDate.of(2008, 11, 30), Country.ITALY);
        Teenager raphale = new Teenager("Raphael", LocalDate.of(2000, 12, 31), Country.SPAIN);
        Teenager[] list = new Teenager[] {armand, noa, antoine, raphale};
        for (Teenager teenager : list) {
            for (int i = 0 ; i < list.length; i++) {
                if (!teenager.equals(list[i])) {
                    System.out.println(teenager+"/"+list[i]);
                    teenagerMap.put(teenager, list[i]);
                }
            }
        }
        System.out.println("here's the map");
        try (BufferedWriter br = new BufferedWriter(new FileWriter(sourcePath+victimPath))) {
            br.write("host_name, host_forename, host_id, host_gender, host_diet, host_animal, host_hobbies, host_history, host_same_gender, guest_name, guest_forename, guest_id, guest_gender, guest_diet, guest_allergy, guest_history, guest_same_gender");
            br.newLine();
            for (Teenager teenager : teenagerMap.keySet()) {
                br.write(""+ extractValuesHost(teenager) + "," + extractValuesGuest(teenagerMap.get(teenager)));
                br.newLine();
            }
        } catch (IOException e) {
            System.out.println("IOEXCEPTION ;\n"+e.getStackTrace());
        } catch (Exception e) {
            System.out.println("Exception\n" + e.getMessage());
        }
        
    }
    
}
