public class IT extends Department{

    public IT(){
        name = "IT";
    }

    @Override
    public double getTotalSalaryBudget() {
        Double salaryBudget = 0.0;
        for(Employee employee : departmentEmployees){
            salaryBudget += employee.getSalary();
        }

        return salaryBudget;
    }

}
