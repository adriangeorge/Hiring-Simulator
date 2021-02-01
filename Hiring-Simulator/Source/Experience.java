import java.time.DateTimeException;
import java.time.LocalDate;

public class Experience implements Comparable<Experience> {
    private LocalDate endDate;
    private LocalDate startDate;
    private String name;
    private String position;
    private String department;

    @Override
    public int compareTo(Experience o) throws DateTimeException {
        try {
            int result;
            if (endDate == null)
                return getName().compareTo(o.getName());
            else
            result = o.endDate.compareTo(endDate);
            if (result == 0) {
                result = o.name.compareTo(name);
            }
        } catch (Exception e) {
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

    public LocalDate getStartDate() {
        return startDate;
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

    public String getPosition() {
        return position;
    }

    public String getDepartment() {
        if(department == null)
            return "No Department";

        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String toString(){
        String expInfo;
        expInfo = "---\nExperience\n---\n\t" + name + " " + position + "\n";
        if(endDate != null)
            expInfo += "\tPeriod : " + startDate.toString() + " - " + endDate.toString();
        else
            expInfo += "\tPeriod : " + startDate.toString() + " - " + "Present day";
        return expInfo;
    }
}
