import java.util.ArrayList;

public abstract class Department {
    // Added a name field for easier identification
    String name;
    ArrayList<Job> departmentJobs;
    ArrayList<Employee> departmentEmployees;

    // I assumed the salaries given in the input file consumers.json were already in net form
    // Therefore I added all the salaries of the employees in the company
    // A salary is calculated using the following formula
    // salary = net/(1-percentage * 0.01)
    public abstract double getTotalSalaryBudget();

    public Department() {
        departmentEmployees = new ArrayList<>();
        departmentJobs = new ArrayList<>();
    }

    public ArrayList<Job> getJobs() {
        return departmentJobs;
    }

    public ArrayList<Employee> getDepartmentEmployees() {
        return departmentEmployees;
    }

    public void add(Employee employee) {
        departmentEmployees.add(employee);
    }

    public void remove(Employee employee) {
        departmentEmployees.remove(employee);
    }

    public void add(Job job) {
        departmentJobs.add(job);
    }
}

// Implementation of the Factory design pattern
class DepartmentFactory{
    public static Department CreateDepartment(String type){
        if(type.equals("Finance")){
            return new Finance();
        }
        if(type.equals("IT")){
            return new IT();
        }
        if(type.equals("Management")){
            return new Management();
        }
        if(type.equals("Marketing")){
            return new Marketing();
        }
        return null;
    }
}
