package com.example.test.service;



import com.example.test.model.Application;
import com.example.test.model.Employee;

import java.util.List;

public interface PayrollService {
    public Employee EmployeeDetailsById(Long id);
    public List<Employee> AllEmployees();
    public List<Application> AllApplications();
    public void EnterEmployee(Employee employee);
    public String RemoveEmployeeById(Long id);
    public String ChangeSalaryById(Long id,  String change);
    public String ChangeRole(Long id,  String role);
    public String CheckInOut(Long id,  String checkInOut);
}
