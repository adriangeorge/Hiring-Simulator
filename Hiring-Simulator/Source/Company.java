import java.util.ArrayList;

public class Company implements Subject {

    private String name;
    private Manager manager;
    private ArrayList<Department> departments;
    private ArrayList<Recruiter> recruiters;

    // Observer design pattern, observers list
    private ArrayList<Observer> observers;

    public Company() {
        this("Default");
    }

    public Company(String name) {
        this.name = name;
        departments = new ArrayList<>();
        recruiters = new ArrayList<>();
        observers = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Department> getDepartments() {
        return departments;
    }

    public void add(Department department) {
        departments.add(department);
    }

    public void add(Recruiter recruiter) {
        recruiters.add(recruiter);
    }

    public void add(Employee employee, Department department) {
        department.departmentEmployees.add(employee);
    }

    public void remove(Employee employee) {
        for (Department department : departments) {
            if (department.departmentEmployees.contains(employee)) {
                department.departmentEmployees.remove(employee);
                break;
            }
        }
    }

    public void remove(Department department) {
        if (departments.contains(department))
            remove(department);
    }

    public void remove(Recruiter recruiter) {
        if (recruiters.contains(recruiter))
            remove(recruiter);
    }

    public void move(Department source, Department destination) {
        destination.departmentEmployees.addAll(source.departmentEmployees);
        departments.remove(source);
    }

    public void move(Employee employee, Department newDepartment) {
        // find employee in old department and remove them
        for (Department department : departments) {
            if (department.departmentEmployees.contains(employee))
                department.departmentEmployees.remove(employee);

        }
        newDepartment.departmentEmployees.add(employee);
    }

    public boolean contains(Department department) {
        return departments.contains(department);
    }

    public boolean contains(Employee employee) {
        return true;
    }

    // Using the getDegreeInFriendship the furthest recruiter from the user is selected and returned
    public Recruiter getRecruiter(User user) {
        ArrayList<Integer> recruiterDegrees = new ArrayList<>();
        ArrayList<Recruiter> recruiterList = new ArrayList<>();
        Integer max_degree_index = 0;
        for (int i = 0; i < recruiters.size(); i++) {
            recruiterDegrees.add(user.getDegreeInFriendship(recruiters.get(i)));
            recruiterList.add(recruiters.get(i));

            if (recruiterDegrees.get(i) > recruiterDegrees.get(max_degree_index))
                max_degree_index = i;
            else if (recruiterDegrees.get(i) == recruiterDegrees.get(max_degree_index)) {
                if (recruiterList.get(i).getRating_recruiter() >= recruiterList.get(max_degree_index).getRating_recruiter())
                    max_degree_index = i;
            }
        }
        System.out.println("  ");
        return recruiterList.get(max_degree_index);
    }

    public ArrayList<Job> getJobs() {
        ArrayList<Job> jobsList = new ArrayList<>();
        for (Department department : departments) {
            jobsList.addAll(department.getJobs());
        }
        return jobsList;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public String toString() {
        String companyInfo = "Company Name: " + getName();
        return companyInfo;
    }

    // Used by the observer dp
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyAllObservers() {
        Notification notification = new Notification();
        for (Observer obs : observers) {
            obs.update(notification);
        }
    }
}
