public class Recruiter extends Employee{

    private Double rating_recruiter;

    public Recruiter(){
        rating_recruiter = 5.0;
    }

    public Double getRating_recruiter() {
        return rating_recruiter;
    }

    public void setRating_recruiter(Double rating_recruiter) {
        this.rating_recruiter = rating_recruiter;
    }

    public Double evaluate(Job job, User user){
        Application app = Application.getInstance();
        Company company = app.getCompany(getEmployer());

        // Check if the user meets the requirements of the job before sending a request to the manager
        if (!job.meetsRequirements(user)){
            System.out.println(user.resume.getInformation().getNume() + " a fost respins de pe postul de " + job.getName() + " la " + job.getCompanyName());
            user.update(new Notification("Rejected!"));
            return 0.0;
        }

        // Compute score and generate a request to be sent to the manager
        Double score = 0.0;
        score = rating_recruiter + user.getTotalScore();
        rating_recruiter += 0.1;

        Request<Job, Consumer> newRequest = new Request<Job, Consumer>(job, user, this, score);
        company.getManager().pendingRequests.add(newRequest);
        return score;
    }
}