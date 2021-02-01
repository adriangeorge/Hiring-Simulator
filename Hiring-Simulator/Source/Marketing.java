public class Marketing extends Department{

    public Marketing(){
        name = "Marketing";
    }

    @Override
    public double getTotalSalaryBudget() {
        Double salaryBudget = 0.0;
        Double taxesPercentage;

        for(Employee employee : departmentEmployees){
            if(employee.getSalary() > 5000){
                taxesPercentage = 0.1;
            } else if(employee.getSalary() < 3000){
                taxesPercentage = 0.0;
            } else
                taxesPercentage = 0.16;

            salaryBudget += employee.getSalary()/(1-taxesPercentage);
        }

        return salaryBudget;
    }
}
