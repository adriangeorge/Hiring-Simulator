import java.time.DateTimeException;
import java.time.LocalDate;

public class Education implements Comparable<Education>{
    private LocalDate endDate;
    private LocalDate startDate;
    private String name;
    private String level;
    private double grade;

    // As requested in the homework requirements
    @Override
    public int compareTo(Education o) throws DateTimeException {
        try{
            int result = o.endDate.compareTo(endDate);
            if(result == 0){
                if((grade - o.grade) < 0){
                    return -1;
                } else if ((grade - o.grade) > 0){
                    return 1;
                }
            }
        } catch (Exception e){
            throw new DateTimeException("Invalid Date");
        }
        return 0;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public String toString(){
        String eduInfo;
        eduInfo = "---\nEducation\n---\n\t" + name + " " + level + " " + grade + "\n";
        if(endDate != null)
            eduInfo += "\tPeriod : " + startDate.toString() + " - " + endDate.toString();
        else
            eduInfo += "\tPeriod : " + startDate.toString() + " - " + "Present day";
        return eduInfo;
    }
}
