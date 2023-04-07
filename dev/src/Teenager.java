import java.time.LocalDate;
import java.util.HashMap;

public class Teenager {
    private int id;
    private String name;
    private String firstname;
    private LocalDate birthDate;
    private Country country;

    public Teenager(int id, String name, String firstname, LocalDate birthDate, Country country) {
        this.id = id;
        this.name = name;
        this.firstname = firstname;
        this.birthDate = birthDate;
        this.country = country;
    }

    public Teenager(int id, String name, String firstname, LocalDate birthDate, String country) {
        this(id, name, firstname, birthDate, Country.valueOf(country));
    }

    
}
