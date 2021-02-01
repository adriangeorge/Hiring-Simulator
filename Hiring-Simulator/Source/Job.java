import java.util.ArrayList;

public class Job {
    private String name;
    private String companyName;
    private boolean open;
    private Department department;

    private Constraint graduationConstr;
    private Constraint experienceConstr;
    private Constraint gpaConstr;

    private ArrayList<User> candidates;
    private int noPositions;
    private int salary;

    public Job() {
        candidates = new ArrayList<>();
    }

    // Check whether the job is still open, find a suitable recruiter and evaluate the user
    public void apply(User user) {
        System.out.println(user.resume.getInformation().getNume() + " a aplicat la " + companyName);
        if (isOpen()) {
            System.out.println("Jobul este deschis, aplicatia a fost trimisa");
            Application app = Application.getInstance();
            Company company = app.getCompany(companyName);
            company.addObserver(user);
            Recruiter bestRecruiter = company.getRecruiter(user);
            bestRecruiter.evaluate(this, user);
        }
    }


    public boolean meetsRequirements(User user) {
        Consumer.Resume userResume = user.resume;

        // Checking graduation constraint
        if (graduationConstr.getUpperLimit() != null)
            if (user.getGraduationYear() != null)
                if (user.getGraduationYear() > graduationConstr.getUpperLimit())
                    return false;

        if (graduationConstr.getLowerLimit() != null) {
            if (user.getGraduationYear() != null)
                if (user.getGraduationYear() < graduationConstr.getLowerLimit())
                    return false;
        }

        // Checking experience constraint
        int years;
        years = user.getExperienceYears();
        if (experienceConstr.getLowerLimit() != null)
            if (years < experienceConstr.getLowerLimit())
                return false;

        if (experienceConstr.getUpperLimit() != null)
            if (years > experienceConstr.getUpperLimit())
                return false;

        // Check GPA constraint
        if (gpaConstr.getLowerLimit() != null)
            if (user.meanGPA() < gpaConstr.getLowerLimit())
                return false;

        if (gpaConstr.getUpperLimit() != null)
            if (user.meanGPA() > gpaConstr.getUpperLimit())
                return false;
        System.out.println(user.resume.getInformation().getNume() + " Passed checks for " + companyName + " : " + name);
        return true;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void setGraduationConstr(Constraint graduationConstr) {
        this.graduationConstr = graduationConstr;
    }

    public void setExperienceConstr(Constraint experienceConstr) {
        this.experienceConstr = experienceConstr;
    }

    public void setGpaConstr(Constraint gpaConstr) {
        this.gpaConstr = gpaConstr;
    }

    public ArrayList<User> getCandidates() {
        return candidates;
    }

    public void setCandidates(ArrayList<User> candidates) {
        this.candidates = candidates;
    }

    public int getNoPositions() {
        return noPositions;
    }

    public void setNoPositions(int noPositions) {
        this.noPositions = noPositions;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
