import java.util.ArrayList;

public class User extends Consumer implements Observer{
    ArrayList<String> targetCompanies;

    // Notifications used by the observer design pattern
    ArrayList<Notification> notifications;

    public User(){
        targetCompanies = new ArrayList<String>();
        notifications = new ArrayList<Notification>();
    }
    // A new employee is created and the resume and social network arraylists are transferred over
    public Employee convert(){
        Employee converted = new Employee();
        converted.resume = resume;
        converted.socialNetwork = socialNetwork;

        for(String targetCompany : targetCompanies){
            Application.getInstance().getCompany(targetCompany).removeObserver(this);
        }

        Application.getInstance().remove(this);
        return converted;
    }

    public Double getTotalScore(){
        // Checking experience years count
        // Will count the number of months, divide by 12, and round up
        return getExperienceYears() * 1.5 + meanGPA();
    }

    @Override
    public void update(Notification notification) {
        notifications.add(notification);
        System.out.println(notification);
    }
}
