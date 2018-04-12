package com.example.test.web;


import com.example.test.model.Application;
import com.example.test.model.Employee;
import com.example.test.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/payroll")
public class PayrollController {
    @Autowired
    PayrollService payrollService;


    @RequestMapping(value="/query/EmployeeDetailsById", method= RequestMethod.GET)
    @ResponseBody
    public Employee EmployeeDetailsById(@RequestParam(name="id", required=false, defaultValue="defId") Long id){
        return payrollService.EmployeeDetailsById(id);
    }

    @RequestMapping(value="/query/AllEmployees", method=RequestMethod.GET)
    @ResponseBody
    public List<Employee> AllEmployees() {
        return payrollService.AllEmployees();
    }

    @RequestMapping(value="/query/AllApplications", method=RequestMethod.GET)
    @ResponseBody
    public List<Application> AllApplications(){
        return payrollService.AllApplications();
    }

    @RequestMapping(value="/query/EnterEmployee", method=RequestMethod.POST )
    public void EnterEmployee(@RequestBody  Employee employee) {
        payrollService.EnterEmployee(employee);
    }

    @RequestMapping(value="/query/RemoveEmployeeById", method=RequestMethod.DELETE)
    @ResponseBody
    public String RemoveEmployeeById(@RequestParam(name="id", required=true, defaultValue="defName") Long id) {
        return payrollService.RemoveEmployeeById(id);
    }

    @RequestMapping(value="/query/ChangeSalaryById", method=RequestMethod.PUT)
    @ResponseBody
    public String ChangeSalaryById(@RequestParam(name="id", required=true, defaultValue="defName") Long id,
                                   @RequestParam(name="change", required=true, defaultValue="defName") String change){
        return payrollService.ChangeSalaryById(id,change);
    }

    @RequestMapping(value="/query/ChangeRole", method=RequestMethod.PUT)
    @ResponseBody
    public String ChangeRole(@RequestParam(name="id", required=true, defaultValue="defId") Long id,
                                    @RequestParam(name="role", required=true, defaultValue="defrole") String role) {
        return payrollService.ChangeRole(id,role);
    }

    @RequestMapping(value="/query/CheckInOut", method=RequestMethod.PUT)
    @ResponseBody
    public String CheckInOut(@RequestParam(name="id", required=true, defaultValue="defId") Long id,
                             @RequestParam(name="checkInOut", required=true, defaultValue="/") String checkInOut) {
        return payrollService.CheckInOut(id,checkInOut);
    }




}
