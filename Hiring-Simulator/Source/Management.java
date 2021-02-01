public class Management extends Department{

    public Management(){
        name = "Management";
    }

    @Override
    public double getTotalSalaryBudget() {
        Double salaryBudget = 0.0;
        Double taxesPercentage = 0.16;
        for(Employee employee : departmentEmployees){
            salaryBudget += employee.getSalary()/(1-taxesPercentage);
        }

        return salaryBudget;
    }
}
