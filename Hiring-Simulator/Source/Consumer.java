import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public abstract class Consumer {
    public Resume resume;
    public ArrayList<Consumer> socialNetwork;

    public Consumer() {
        socialNetwork = new ArrayList<>();
    }

    public void add(Education education) {
        resume.education.add(education);
        Collections.sort(resume.education);
    }

    public void add(Experience experience) {
        resume.experience.add(experience);
        Collections.sort(resume.experience);
    }

    public void add(Consumer consumer) {
        socialNetwork.add(consumer);
    }

    // BFS traversal of the friends graph
    public int getDegreeInFriendship(Consumer consumer) {

        // Retain level (depth)
        Integer level = 0;
        Integer currentLevel;

        // The node queue and level queue will be synced all the time
        Queue<Consumer> queue = new LinkedList<>();
        Queue<Integer> levelQueue = new LinkedList<>();

        ArrayList<Consumer> explored = new ArrayList<>();
        Consumer current = this;
        queue.add(current);
        levelQueue.add(level);

        // Pushing current node (root) and current level into their respective queues
        while (!queue.isEmpty()) {
            current = queue.poll();
            currentLevel = levelQueue.poll();

            explored.add(current);

            // exit if desired user was found
            if(current.equals(consumer))
                return currentLevel;

            // Go through all of the neighbour nodes of current node
            for(Consumer cons : current.socialNetwork){
                if(!explored.contains(cons)){
                    queue.add(cons);
                    levelQueue.add(currentLevel + 1);
                }
            }
        }
        return level;
    }

    public void remove(Consumer consumer) {
        socialNetwork.remove(consumer);
    }

    // Determine whether the user is still in college else return graduation year
    public Integer getGraduationYear() {
        for (Education edu : resume.education) {
            if (edu.getLevel().equals("college"))
                if(edu.getEndDate() == null)
                    return null;
                else
                    return edu.getEndDate().getYear();
        }
        return null;
    }

    public Integer getExperienceYears() {
        // Checking experience years count
        // Will count the number of months, divide by 12, and round up
        Double months = 0.0;
        for (Experience prevExp : resume.experience) {
            months += Period.between(prevExp.getStartDate(), prevExp.getEndDate()).getMonths();
        }

        months /= 12;
        return (int) Math.ceil(months);
    }

    public Double meanGPA() {
        Double mGPA = 0.0;
        for (Education edu : resume.education) {
            mGPA += edu.getGrade();
        }
        mGPA /= resume.education.size();
        return mGPA;
    }

    public String toString() {
        String consumer_info = "---\nUSER INFO\n---"
                + "\n\tFirst Name: " + resume.information.getNume()
                + "\n\tLast Name: " + resume.information.getPrenume()
                + "\n\tEmail: " + resume.information.getEmail()
                + "\n\tPhone: " + resume.information.getTelefon()
                + "\n\tBirth date: " + resume.information.getDataNastere().toString()
                + "\n\tGender: " + resume.information.getSex()
                + "\n\tLanguages: " + resume.information.getLanguages().toString()
                + "\n\tLanguages level: " + resume.information.getLanguagesLevel().toString() + "\n";

        return consumer_info;
    }

    // Usage of builder design pattern to build a resume object
    public static class Resume {
        private Information information;
        private ArrayList<Education> education;
        private ArrayList<Experience> experience;

        private Resume(ResumeBuilder builder) {
            this.information = builder.information;
            this.education = builder.education;
            this.experience = builder.experience;
        }

        public static class ResumeBuilder {
            private Information information;
            private ArrayList<Education> education;
            private ArrayList<Experience> experience;

            public ResumeBuilder(ArrayList<Education> education, ArrayList<Experience> experience) {
                this.education = education;
                this.experience = experience;
                information = new Information();
            }

            public ResumeBuilder firstName(String firstName) {
                information.setPrenume(firstName);
                return this;
            }

            public ResumeBuilder lastName(String lastName) {
                information.setNume(lastName);
                return this;
            }

            public ResumeBuilder email(String email) {
                information.setEmail(email);
                return this;
            }

            public ResumeBuilder phone(String phone) {
                information.setTelefon(phone);
                return this;
            }

            public ResumeBuilder birthDate(LocalDate date) {
                information.setDataNastere(date);
                return this;
            }

            public ResumeBuilder gender(String sex) {
                information.setSex(sex);
                return this;
            }

            public ResumeBuilder languages(ArrayList<String> languages, ArrayList<String> language_level) {
                for (int i = 0; i < languages.size(); i++) {
                    information.addLanguage(languages.get(i));
                    information.addLanguagesLevel(language_level.get(i));
                }
                return this;
            }

            public Resume build() {
                return new Resume(this);
            }
        }

        public Information getInformation() {
            return information;
        }

        public ArrayList<Education> getEducation() {
            return education;
        }

        public ArrayList<Experience> getExperience() {
            return experience;
        }

    }
}
