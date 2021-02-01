import java.time.LocalDate;
import java.util.ArrayList;

public class Information {
    private String nume;
    private String prenume;
    private String email;
    private String telefon;
    private LocalDate dataNastere;
    private String sex;
    private ArrayList<String> languages;
    private ArrayList<String> languagesLevel;

    public Information(){
        languages = new ArrayList<>();
        languagesLevel = new ArrayList<>();
    }

    // Self explainatory getters and setters
    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public LocalDate getDataNastere() {
        return dataNastere;
    }

    public void setDataNastere(LocalDate dataNastere) {
        this.dataNastere = dataNastere;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


    public ArrayList<String> getLanguages() {
        return languages;
    }

    public void addLanguage(String language) {
        languages.add(language);
    }

    public ArrayList<String> getLanguagesLevel() {
        return languagesLevel;
    }

    public void addLanguagesLevel(String level) {
       languagesLevel.add(level);
    }
}
