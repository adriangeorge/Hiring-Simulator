import java.util.ArrayList;
import java.util.List;

public class Application {
    private ArrayList<Company> companyList;
    private ArrayList<User> userList;

    // This list will contain every single user registered in the application
    private ArrayList<Consumer> consumers;
    // Singleton design pattern instance initially set to null
    private static Application instance = null;

    private Application() {
        companyList = new ArrayList<>();
        userList = new ArrayList<>();
        consumers = new ArrayList<>();
    }

    public void addUsers(ArrayList<User> users) {
        userList.addAll(users);
    }

    // Singleton design patter usage
    public static Application getInstance() {
        if (instance == null) {
            instance = new Application();
        }
        return instance;
    }

    public void registerAllConsumers(ArrayList consumers) {
        this.consumers.addAll(consumers);
    }

    public ArrayList<Consumer> getRegisteredConsumers() {
        return consumers;
    }

    // same as getRegisteredConsumers() but searches by a String
    public Consumer getRegisteredConsumer(String searchedName) {
        String name;
        for (Consumer consumer : consumers) {
            name = consumer.resume.getInformation().getNume() + " " + consumer.resume.getInformation().getPrenume();
            if (searchedName.equals(name))
                return consumer;
        }
        return null;
    }

    public ArrayList<Company> getCompanies() {
        return companyList;
    }

    public Company getCompany(String name) {
        for (Company searchedCompany : companyList) {
            if (searchedCompany.getName().equals(name)) {
                return searchedCompany;
            }
        }
        return null;
    }

    public void add(Company company) {
        companyList.add(company);
    }

    public void add(User user) {
        userList.add(user);
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public boolean remove(Company company) {
        return companyList.remove(company);
    }

    public boolean remove(User user) {
        return userList.remove(user);
    }

    // Get all the jobs of all the companies in the list
    public ArrayList<Job> getJobs(List<String> companies) {
        ArrayList<Job> jobArrayList = new ArrayList<Job>();
        ArrayList<Department> companyDepartments;
        for (String company : companies) {
            companyDepartments = getCompany(company).getDepartments();
            for (Department department : companyDepartments) {
                jobArrayList.addAll(department.getJobs());
            }
        }
        return jobArrayList;
    }

}
