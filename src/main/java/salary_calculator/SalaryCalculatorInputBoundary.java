package salary_calculator;

public interface SalaryCalculatorInputBoundary {

    SalaryResponseModel requestSalary(SalaryRequestModel salaryRequestModel) throws IllegalArgumentException;

}
