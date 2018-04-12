package com.example.test.persistance;


import com.example.test.model.Application;
import com.example.test.model.Employee;
import com.example.test.model.Role;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class PersistanceMock {
    public List<Role> roles = new ArrayList<>();
    public List<Employee> employees = new ArrayList<>();

    public List<Application> applications = new ArrayList<>();
    public int turnOvers;

    public PersistanceMock(){
        initRoles();
        initEmployees();
        initApplications();



        turnOvers=0;
    }

    private void initRoles(){
        Role role1 = new Role();
        role1.id=1L;
        role1.title="CEO";

        Role role2 = new Role();
        role2.id=2L;
        role2.title="Manager";

        Role role3 = new Role();
        role3.id=3L;
        role3.title="Project Manager";

        Role role4 = new Role();
        role4.id=4L;
        role4.title="Employee";

        roles.add(role1);
        roles.add(role2);
        roles.add(role3);
        roles.add(role4);
    }

    private void initEmployees(){
        Employee employee1 = new Employee();
        employee1.id=0L;
        employee1.name="Jovan Davchev";
        employee1.departmentId=1;
        employee1.role=roles.get(0);


        Application app1=new Application();
        app1.id=0L;
        app1.employeeManager=null;
        app1.personData="Aleksandar";
        app1.rating=2;
        app1.personSkill="Programming";
        app1.title="App1";
        app1.profit=340;
        applications.add(app1);


        employee1.age="22";
        employee1.gender="M";
        employee1.dateEmployed="24/03/2018";
        employee1.tasksCompleted=4;
        employee1.salary=25000;
        employee1.ratingPerformance=4.3;
        employee1.assignedHoursADay=8;
        employee1.checkInOut.add("08:00/25/03/2018");
        employee1.checkInOut.add("16:00/25/03/2018");
        employee1.applications.add(applications.get(0));


        Employee employee2 = new Employee();
        employee2.id=1L;
        employee2.name="Malchev";
        employee1.departmentId=2;
        employee2.role=roles.get(1);

        employee2.age="27";
        employee2.gender="M";
        employee2.dateEmployed="28/06/2017";
        employee2.tasksCompleted=11;
        employee2.salary=27000;
        employee2.ratingPerformance=5.1;
        employee2.assignedHoursADay=7;
        employee2.checkInOut.add("08:00/29/06/2017");
        employee2.checkInOut.add("12:20/29/06/2017");
        employee2.applications.add(applications.get(0));



        employees.add(employee1);
        employees.add(employee2);

    }


    private void initApplications(){



    }

}
