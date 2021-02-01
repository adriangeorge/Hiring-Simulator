import java.io.IOException;
import java.util.ArrayList;


public class Test {

    public static void main(String[] argv) throws IOException {

        // Parser class used for reading JSON input files
        ParserJSON parser;
        Application application = Application.getInstance();

        // Loading parser with the company input file
        // Generating the list of available companies
        parser = new ParserJSON(".\\Source\\company.json");
        ArrayList<Company> companies = new ArrayList<Company>(parser.generateCompanies(parser.jsonInformation.getJSONArray("companies")));

        // Adding all companies to the application's memory
        for (Company company : companies) {
            application.add(company);
        }

        // Loading parser with the consumer input file
        // Generating lists for all types of consumers
        parser = new ParserJSON(".\\Source\\consumers.json");
        ArrayList<Employee> employeesList = new ArrayList<Employee>(parser.generateConsumer("employees"));
        application.registerAllConsumers(employeesList);

        ArrayList<Recruiter> recruitersList = new ArrayList<Recruiter>(parser.generateConsumer("recruiters"));
        application.registerAllConsumers(recruitersList);

        ArrayList<User> usersList = new ArrayList<User>(parser.generateConsumer("users"));
        application.registerAllConsumers(usersList);
        application.addUsers(usersList);
        ArrayList<Manager> managersList = new ArrayList<Manager>(parser.generateConsumer("managers"));
        application.registerAllConsumers(managersList);

        // Load parser with friends input file
        // Generate list of friends for each consumer
        parser = new ParserJSON(".\\Source\\social.json");
        for (Consumer cons : application.getRegisteredConsumers()) {
            parser.getFriends(cons);
//            for(Consumer friend :cons.socialNetwork )
//                System.out.println(friend.resume.getInformation().getPrenume() + " " + friend.resume.getInformation().getNume());
//            System.out.println("+----+");
        }

        // Load parser with available jobs input file
        // Generate list of jobs
        parser = new ParserJSON(".\\Source\\available_jobs.json");
        ArrayList<Job> availableJobs = new ArrayList<>(parser.getJobs());
        for (Job job : availableJobs) {
            Company companyOfJob = application.getCompany(job.getCompanyName());
            for (Department department : companyOfJob.getDepartments()) {
                if (department.name.equals(job.getDepartment().name)) {
                    department.add(job);
                    break;
                }
            }
        }

//        DISPLAY USER INFORMATION
        for (User user : usersList) {
            System.out.println(user.toString());
            for (Education education : user.resume.getEducation()) {
                System.out.println(education);
            }
            for (Experience experience : user.resume.getExperience()) {
                System.out.println(experience);
            }
            System.out.println("\n\n");
        }

        // Users apply to their wanted jobs
        for (User user : application.getUserList()) {
            for (Job searchedJob : application.getJobs(user.targetCompanies)) {
                System.out.println("Se aplica la " + searchedJob.getCompanyName() + " pozitia " + searchedJob.getName());
                searchedJob.apply(user);
            }
        }
        // Processing employment requests
        for (Company company : application.getCompanies()) {
            Manager manager = company.getManager();
            for (Request<Job, Consumer> request : manager.getPendingRequests()) {
                Job checkedJob = request.getKey();
                company.getManager().process(checkedJob);
            }
        }
    }
}
