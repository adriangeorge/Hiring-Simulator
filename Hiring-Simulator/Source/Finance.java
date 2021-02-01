public class Finance extends Department{

    public Finance(){
        name = "Finance";
    }

    @Override
    public double getTotalSalaryBudget() {
        Double salaryBudget = 0.0;
        Double taxesPercentage;
        for(Employee employee : departmentEmployees){
            if(employee.getExperienceYears() < 1){
                taxesPercentage = 0.1;
            } else
                taxesPercentage = 0.16;

            salaryBudget += employee.getSalary()/(1-taxesPercentage);
        }

        return salaryBudget;
    }
}
