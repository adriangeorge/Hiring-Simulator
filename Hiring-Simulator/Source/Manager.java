import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Manager extends Employee {
    ArrayList<Request> pendingRequests;

    public Manager(){
        pendingRequests = new ArrayList<>();
    }

    public ArrayList<Request> getPendingRequests(){
        return pendingRequests;
    }
    public void process(Job job){

        ArrayList<Request> matchingReq = new ArrayList<>();
        Application instance = Application.getInstance();
        for (Request request: pendingRequests) {
            if(request.getKey().equals(job))
                matchingReq.add(request);
        }

        Collections.sort(matchingReq, new SortByScore());
        // Getting the first NoPositions candidates
        User currentUser;

        // Used iterator to avoid a concurrent modification exception
        Iterator<Request> iter = matchingReq.iterator();

        while (iter.hasNext()) {
        Request<Job,User> currentRequest = iter.next();

            if(job.isOpen()){
                currentUser = currentRequest.getValue1();

                //Check availability in application unemployed user list
                if(!instance.getUserList().contains(currentUser)){
                    // If user was not found in the applications user list
                    // It can only mean the user was already employed
                    // The current user's request is removed
                    iter.remove();
                    continue;
                }

                // Employment, the user is converted into an Employee
                Employee newEmployee;
                newEmployee = currentUser.convert();
                System.out.println("\t\t" + newEmployee.resume.getInformation().getNume() + " a fost angajat la " + job.getCompanyName() + " ca " + job.getName());
                // Job is closed if there are no more positions left
                job.setNoPositions(job.getNoPositions() - 1);
                if(job.getNoPositions() <= 0 ){
                    job.setOpen(false);
                }

                // Employment experience is added to their resume
                Experience newExperience = new Experience();
                newExperience.setStartDate(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth()));
                newExperience.setEndDate(null);
                newExperience.setPosition(job.getName());
                newExperience.setName(getEmployer());

                newEmployee.add(newExperience);
                newEmployee.setEmployer(job.getCompanyName());
                newEmployee.setSalary(job.getSalary());
                Company currentCompany = instance.getCompany(getEmployer());
                currentCompany.add(newEmployee, job.getDepartment());
            }

        }

    }
}
