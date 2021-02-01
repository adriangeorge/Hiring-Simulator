public class Employee extends Consumer {
    private String employer;
    private int salary;

    public Employee() {
        this("Default", 0);
    }

    public Employee(String employer, int salary) {
        this.employer = employer;
        this.salary = salary;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public void setEmployer() {
        this.employer = resume.getExperience().get(resume.getExperience().size() - 1).getName();
    }

    public String getEmployer() {
        return employer;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Department getDepartment() {
        Company company = Application.getInstance().getCompany(employer);
        for( Department department : company.getDepartments()){
            for(Employee employee : department.getDepartmentEmployees()){
                if(employee.equals(this))
                    return department;
            }
        }
        return null;
    }

    public String toString() {
        String employee_info = super.toString() + "---\nEMPLOYMENT INFO\n---";
        employee_info = employee_info
                + "\n\tSalary: " + getSalary()
                + "\n\tEmployed at: " + getEmployer()
                + "\n\tDepartment: " + resume.getExperience().get(resume.getExperience().size() - 1).getDepartment();

        return employee_info;
    }
}
