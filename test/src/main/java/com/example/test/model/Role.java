package com.example.test.model;



import java.util.List;




public class Role  {
    public Role(){}

    public Long id;

    public String title;

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public Long getId() {

        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }


    public List<Employee> employeeList;
}
