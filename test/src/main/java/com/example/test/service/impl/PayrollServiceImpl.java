package com.example.test.service.impl;



import com.example.test.model.Application;
import com.example.test.model.Employee;
import com.example.test.model.Role;
import com.example.test.persistance.PersistanceMock;
import com.example.test.service.PayrollService;
import com.example.test.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

@Service
public class PayrollServiceImpl implements PayrollService {

    PayrollServiceImpl(){

    }
    @Autowired
    private PersistanceMock persistanceMock;
    @Autowired
    StatsService statsService;

    @Override
    public Employee EmployeeDetailsById(Long id) {
        Employee eTarget=null;
        for(int i=0;i<persistanceMock.employees.size();i++){
            Employee e = persistanceMock.employees.get(i);
            if(e.id==id){
                eTarget = persistanceMock.employees.get(i);
            }
        }
        if(eTarget!=null){
            statsService.EmployeeTotalTimeSpentWorkingInCompanyById(eTarget.id);
            return eTarget;
        }else{
            Employee err = new Employee();
            err.name="/";
            return err;
        }
    }

    @Override
    public List<Employee> AllEmployees() {
        for(int i=0;i<persistanceMock.employees.size();i++){
            Long id = persistanceMock.employees.get(i).id;
            statsService.EmployeeTotalTimeSpentWorkingInCompanyById(id);
        }
        return persistanceMock.employees;
    }

    @Override
    public List<Application> AllApplications() {
        List<Application> apps = new ArrayList<Application>();
        for(int i=0;i<persistanceMock.employees.size();i++){
            Employee e = persistanceMock.employees.get(i);
            List<Application> eApps= e.applications;
            for(int j=0;j<eApps.size();j++){
                if(apps.contains(eApps.get(j))==false){
                    apps.add(eApps.get(j));
                }
            }
        }
        return apps;
    }

    @Override
    public void EnterEmployee(Employee employee) {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Employee e = new Employee();

        e.name=employee.name;
        e.departmentId=employee.departmentId;
        e.age=employee.age;
        e.gender= employee.gender;
        e.dateEmployed=now.format(formatter);
        e.tasksCompleted=0;
        e.minutesWorked=0;
        e.salary=20000;
        e.ratingPerformance=0;
        e.assignedHoursADay=employee.assignedHoursADay;
        e.role=employee.role;

        persistanceMock.employees.add(e);
    }

    @Override
    public String RemoveEmployeeById(Long id) {
        Employee eTarget=null;
        for(int i=0;i<persistanceMock.employees.size();i++){
            Employee e = persistanceMock.employees.get(i);
            if(e.id==id){
                persistanceMock.employees.remove(e);
                persistanceMock.turnOvers++;
                return "Successfully removed employee with id: "+id;
            }
        }
        return "Employee with id: "+id+" not found";
    }


    public String ChangeSalaryById(Long id, String change){
        Employee eTarget=null;
        for(int i=0;i<persistanceMock.employees.size();i++){
            Employee e = persistanceMock.employees.get(i);
            if(e.id==id){
                eTarget = persistanceMock.employees.get(i);
            }
        }

        try{
            if(eTarget!=null){
                int currSalary = eTarget.salary;
                eTarget.salary=eTarget.salary+Integer.parseInt(change);
                return "Salary for employee with id "+id+" change from "+ currSalary+" to "+eTarget.salary;
            }
            else{
                return "Employee with ID: "+id+" not found";
            }
        }catch (Exception e){
            return "invalid value for change!";
        }
    }


    @Override
    public String ChangeRole(Long id,  String role) {
        Employee eTarget=null;
        for(int i=0;i<persistanceMock.employees.size();i++){
            Employee e = persistanceMock.employees.get(i);
            if(e.id==id){
                eTarget = persistanceMock.employees.get(i);
            }
        }
        if(eTarget!=null){
            if(role.equals("Employee") || role.equals("Manager") || role.equals("Project Manager") ||  role.equals("CEO") ){
                String tmp = eTarget.role.title;
                eTarget.role.title=role;
                return "Successfully changed role to employee with id: "+id +" from "+tmp+" to "+eTarget.role.title;
            }
            else{
                return "role not valid.";
            }

        }else{
            return "Employee with id: "+id+" not found";
        }
    }

    @Override
    public String CheckInOut(Long id,  String checkInOut) {
        //checkInOut value should be like  "10:00/25/04/2018-16:00/25/04/2018"
        Employee eTarget=null;
        for(int i=0;i<persistanceMock.employees.size();i++){
            Employee e = persistanceMock.employees.get(i);
            if(e.id==id){
                eTarget = persistanceMock.employees.get(i);
            }
        }
        if(eTarget!=null){
            String checkIn = checkInOut.split("-")[0];//10:00/25/04/2018
            String checkOut = checkInOut.split("-")[1];//16:00/25/04/2018

            //to print LocalDateTime checkOutL.format(formatter).toString() ->10:00/25/04/2018
            // with formatter  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm/dd/MM/yyyy");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm/dd/MM/yyyy");
            LocalDateTime checkInL = LocalDateTime.parse(checkIn,formatter);
            LocalDateTime checkOutL = LocalDateTime.parse(checkOut,formatter);

            if(checkInL.isBefore(checkOutL)){
                eTarget.checkInOut.add(checkIn);
                eTarget.checkInOut.add(checkOut);

                statsService.EmployeeTotalTimeSpentWorkingInCompanyById(eTarget.id);
                return "Successfully added \ncheckIn "+checkInL.format(formatter).toString()+
                        "\ncheckOut "+checkOutL.format(formatter).toString()+
                        "\nto employee with id: "+id;
            }else{
                return "invalid checkInOut";
            }
        }else{
            return "Employee with id: "+id+" not found";
        }
    }


}
