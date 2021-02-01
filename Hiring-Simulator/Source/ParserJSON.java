import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ParserJSON {

    JSONObject jsonInformation;

    // Loads the file into jsonInformation
    public ParserJSON(String jsonFile) throws IOException {
        FileInputStream fileReader = null;
        String data = null;
        // Reading Companies input file
        try {
            File inputFile;
            inputFile = new File(jsonFile);
            fileReader = new FileInputStream(inputFile);
            data = new String(fileReader.readAllBytes());
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
        assert data != null;
        jsonInformation = new JSONObject(data);
    }

    public ParserJSON(JSONObject jsonInformation) {
        this.jsonInformation = jsonInformation;
    }
    // Various getters defined for cleaner looking code when reading from input files
    public String getCompanyName() {
        return jsonInformation.getString("name");
    }

    public ArrayList<String> getCompanyDepartments() {
        ArrayList<String> departmentsList = new ArrayList<>();
        JSONArray departments = (JSONArray) jsonInformation.get("departments");
        for (int i = 0; i < departments.length(); i++) {
            departmentsList.add(departments.getString(i));
        }
        return departmentsList;
    }

    public String getFirstName() {
        String[] split = ((String) jsonInformation.get("name")).split(" ", 0);
        return split[1];
    }

    public String getLastName() {
        String[] split = ((String) jsonInformation.get("name")).split(" ", 0);
        return split[0];
    }

    public String getEmail() {
        return (String) jsonInformation.get("email");
    }

    public String getPhone() {
        return (String) jsonInformation.get("phone");
    }

    public LocalDate getBirthDate() {
        String[] birth = (((String) jsonInformation.get("date_of_birth")).split("\\."));
        return LocalDate.of(Integer.valueOf(birth[2]), Integer.valueOf(birth[1]), Integer.valueOf(birth[0]));
    }

    public String getSex() {
        return (String) jsonInformation.get("genre");
    }

    public ArrayList<String> getLang() {
        ArrayList<String> languages = new ArrayList<>();
        JSONArray lang = (JSONArray) jsonInformation.get("languages");
        for (int i = 0; i < lang.length(); i++) {
            languages.add(lang.getString(i));
        }
        return languages;
    }

    public ArrayList<String> getLangLevel() {
        ArrayList<String> languages = new ArrayList<>();
        JSONArray lang = (JSONArray) jsonInformation.get("languages_level");
        for (int i = 0; i < lang.length(); i++) {
            languages.add(lang.getString(i));
        }
        return languages;
    }

    public int getSalary() {
        return jsonInformation.getInt("salary");
    }

    public ArrayList<String> getTargetCompanies(){
        ArrayList<String> companies = new ArrayList<>();
        JSONArray comps = (JSONArray) jsonInformation.get("interested_companies");
        for (int i = 0; i < comps.length(); i++) {
            companies.add(comps.getString(i));
        }
        return companies;
    }

    // Returning an array list of all the educations of a given user
    public ArrayList<Education> getEducation() {
        ArrayList<Education> eduList = new ArrayList<>();
        // Add education
        JSONArray education = (JSONArray) jsonInformation.get("education");
        Education newEdu;
        JSONObject jsonEdu;
        String level;
        String name;
        Double grade;
        for (int i = 0; i < education.length(); i++) {
            jsonEdu = (JSONObject) education.get(i);
            newEdu = new Education();

            level = (String) jsonEdu.get("level");
            newEdu.setLevel(level);

            name = (String) jsonEdu.get("name");
            newEdu.setName(name);

            LocalDate start_date;
            LocalDate end_date;
            String[] startD;
            startD = (((String) jsonEdu.get("start_date")).split("\\."));
            start_date = LocalDate.of(Integer.valueOf(startD[2]), Integer.valueOf(startD[1]), Integer.valueOf(startD[0]));
            newEdu.setStartDate(start_date);
            String[] endD = null;
            if (!jsonEdu.get("end_date").equals(null)) {
                endD = (((String) jsonEdu.get("end_date")).split("\\."));
                end_date = LocalDate.of(Integer.valueOf(endD[2]), Integer.valueOf(endD[1]), Integer.valueOf(endD[0]));
            } else end_date = null;
            newEdu.setEndDate(end_date);

            grade = jsonEdu.getDouble("grade");
            newEdu.setGrade(grade);

            eduList.add(newEdu);
        }
        return eduList;
    }
    // Returning an array list of all the experiences of a given user
    public ArrayList<Experience> getExperience() {
        ArrayList<Experience> expList = new ArrayList<>();
        // Add Experience
        JSONArray experience = (JSONArray) jsonInformation.get("experience");
        Experience newExp;
        JSONObject jsonExp;
        String company;
        String position;
        String department;

        for (int i = 0; i < experience.length(); i++) {
            jsonExp = (JSONObject) experience.get(i);
            newExp = new Experience();

            // Get company name
            company = (String) jsonExp.get("company");
            newExp.setName(company);

            // Get position in company
            position = (String) jsonExp.get("position");
            newExp.setPosition(position);

            // Getting dates
            LocalDate start_date;
            LocalDate end_date;
            String[] startD;
            startD = (((String) jsonExp.get("start_date")).split("\\."));
            start_date = LocalDate.of(Integer.parseInt(startD[2]), Integer.parseInt(startD[1]), Integer.parseInt(startD[0]));
            newExp.setStartDate(start_date);
            String[] endD = null;

            if (jsonExp.isNull("end_date")) {
                end_date = null;
                if (!jsonExp.isNull("department")) {
                    // Get employee department string
                    department = jsonExp.getString("department");
                    newExp.setDepartment(department);
                }
            } else {
                endD = (((String) jsonExp.get("end_date")).split("\\."));
                end_date = LocalDate.of(Integer.parseInt(endD[2]), Integer.parseInt(endD[1]), Integer.parseInt(endD[0]));
            }
            newExp.setEndDate(end_date);
            expList.add(newExp);
        }
        return expList;
    }

    // Reading all the companies from company.json
    public ArrayList generateCompanies(JSONArray jsonInfo) {
        ArrayList<Company> returnArray = new ArrayList<Company>();
        Company newCompany;

        // Iterative approach to generating the list of inputted companies
        for (Object companyObj : jsonInfo) {
            newCompany = new Company();
            ParserJSON parserJSON = new ParserJSON((JSONObject) companyObj);
            // Define the parser class to be used while reading inputs
            newCompany.setName(parserJSON.getCompanyName());
            for (String departmentName : parserJSON.getCompanyDepartments()) {
                newCompany.add(DepartmentFactory.CreateDepartment(departmentName));
            }
            returnArray.add(newCompany);
        }
        return returnArray;
    }

    // Generating a consumer based on a role, usage of Builder design pattern
    public ArrayList generateConsumer(String role) {
        ArrayList<Consumer> returnArray = new ArrayList<Consumer>();
        Consumer newConsumer = null;

        // Iterative approach to generating the resume of inputted person
        for (Object resumeObj : jsonInformation.getJSONArray(role)) {
            // Construct consumer based
            switch (role) {
                case "employees":
                    newConsumer = new Employee();
                    break;
                case "recruiters":
                    newConsumer = new Recruiter();
                    break;
                case "users":
                    newConsumer = new User();
                    break;
                case "managers":
                    newConsumer = new Manager();
                    break;
            }
            if (newConsumer == null)
                break;

            ParserJSON parserJSON = new ParserJSON((JSONObject) resumeObj);
            // Add full name
            String first_name = parserJSON.getFirstName();
            String last_name = parserJSON.getLastName();
            // Add e-mail
            String email = parserJSON.getEmail();
            // Add phone #
            String phone = parserJSON.getPhone();
            // Add birth date
            LocalDate birthDate = parserJSON.getBirthDate();
            // Add sex
            String sex = parserJSON.getSex();
            // Add languages
            ArrayList<String> languages = parserJSON.getLang();
            ArrayList<String> languages_level = parserJSON.getLangLevel();
            // Add Experience
            ArrayList<Experience> experience = parserJSON.getExperience();
            // Add Education
            ArrayList<Education> education = parserJSON.getEducation();

            newConsumer.resume = new Consumer.Resume.ResumeBuilder(education, experience)
                    .firstName(first_name)
                    .lastName(last_name)
                    .email(email)
                    .phone(phone)
                    .birthDate(birthDate)
                    .gender(sex)
                    .languages(languages, languages_level)
                    .build();

            // If consumer is an employee, they will also be assigned to the current company they work at
            // Else they will receive a list of their interested companies
            if (Employee.class.isAssignableFrom(newConsumer.getClass())) {
                ((Employee) newConsumer).setSalary(parserJSON.getSalary());
                ((Employee) newConsumer).setEmployer();

                // Set the department of a customer
                // Get the company this employee works at
                Application app = Application.getInstance();
                Company currentCompany = app.getCompany(((Employee) newConsumer).getEmployer());

                // Get string containing the employee's department
                String empDepartment = newConsumer.resume.getExperience().get(newConsumer.resume.getExperience().size() - 1).getDepartment();

                if (role.equals("recruiters")) {
                    empDepartment = "IT";
                    currentCompany.add((Recruiter) newConsumer);
                } else if (role.equals("managers")){
                    empDepartment = "Manager";
                    currentCompany.setManager((Manager) newConsumer);
                }

                // Get department by name
                for (Department department : currentCompany.getDepartments()) {
                    if (department.name.equals(empDepartment))
                        currentCompany.add((Employee) newConsumer, department);
                }
            } else {
                // The consumer is a user, adding targeted companies
                ((User) newConsumer).targetCompanies = parserJSON.getTargetCompanies();
            }

            returnArray.add(newConsumer);
        }
        return returnArray;
    }

    // Read the friends list from the social.json file
    public ArrayList<Consumer> getFriends(Consumer consumer) {
        ArrayList<Consumer> returnList = new ArrayList<>();
        String nume = consumer.resume.getInformation().getNume() + " " + consumer.resume.getInformation().getPrenume();

        if (!jsonInformation.has(nume))
            return returnList;

        for (Object friendsJSON : jsonInformation.getJSONArray(nume)) {
            consumer.add(Application.getInstance().getRegisteredConsumer(friendsJSON.toString()));
        }
        return returnList;
    }

    // Getting jobs from available_jobs.json
    public ArrayList<Job> getJobs() {
        Application app = Application.getInstance();
        ArrayList<Job> returnList = new ArrayList<>();
        Job newJob;

        for (Object jobObj : jsonInformation.getJSONArray("jobs")) {
            newJob = new Job();
            newJob.setOpen(true);
            String companyName = ((JSONObject) jobObj).getString("company_name");
            newJob.setCompanyName(companyName);

            String jobName = ((JSONObject) jobObj).getString("name");
            newJob.setName(jobName);

            ArrayList<Department> companyDepts = app.getCompany(companyName).getDepartments();
            String jobDeptName = ((JSONObject) jobObj).getString("department");
            Department jobDept = null;
            for(Department department : companyDepts){
                if(department.name.equals(jobDeptName)){
                    jobDept = department;
                }
            }
            newJob.setDepartment(jobDept);

            Integer noPos = ((JSONObject) jobObj).getInt("noPositions");
            newJob.setNoPositions(noPos);

            // Setting constraints
            Double lower;
            Double upper;

            try {
                lower = ((JSONObject) jobObj).getDouble("grad_min");
            }
            catch (Exception e){
                lower = null;
            }
            try {
                upper = ((JSONObject) jobObj).getDouble("grad_max");
            }
            catch (Exception e){
                upper = null;
            }
            Constraint gradConstr = new Constraint(lower,upper);
            newJob.setGraduationConstr(gradConstr);

            try {
                lower = ((JSONObject) jobObj).getDouble("meanGPA_min");
            }
            catch (Exception e){
                lower = null;
            }
            try {
                upper = ((JSONObject) jobObj).getDouble("meanGPA_max");
            }
            catch (Exception e){
                upper = null;
            }
            Constraint gpaConstr = new Constraint(lower,upper);
            newJob.setGpaConstr(gpaConstr);

            try {
                lower = ((JSONObject) jobObj).getDouble("exp_min");
            }
            catch (Exception e){
                lower = null;
            }
            try {
                upper = ((JSONObject) jobObj).getDouble("exp_max");
            }
            catch (Exception e){
                upper = null;
            }
            Constraint expConstr = new Constraint(lower,upper);
            newJob.setExperienceConstr(expConstr);

            Integer salary = ((JSONObject) jobObj).getInt("salary");
            newJob.setSalary(salary);

            returnList.add(newJob);
        }

        return returnList;
    }
}
