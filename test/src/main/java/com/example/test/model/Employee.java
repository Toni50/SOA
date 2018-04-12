package com.example.test.model;



import java.util.ArrayList;
import java.util.List;



public class Employee {

    public Employee(){
        checkInOut = new ArrayList<String>( );
        applications = new ArrayList<Application>( );
    }

    public Long id;

    public String name;

    public Integer departmentId;

    public String age;

    public String gender;

    public String dateEmployed;

    public int tasksCompleted;

    public long minutesWorked;

    public int salary;

    public double ratingPerformance;

    public int assignedHoursADay;

    public List<String> checkInOut;

    public List<Application> applications;//works on


    public Role role;


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getDateEmployed() {
        return dateEmployed;
    }

    public int getTasksCompleted() {
        return tasksCompleted;
    }

    public long getMinutesWorked() {
        return minutesWorked;
    }

    public int getSalary() {
        return salary;
    }

    public double getRatingPerformance() {
        return ratingPerformance;
    }

    public int getAssignedHoursADay() {
        return assignedHoursADay;
    }

    public List<String> getCheckInOut() {
        return checkInOut;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public Role getRole() {
        return role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDateEmployed(String dateEmployed) {
        this.dateEmployed = dateEmployed;
    }

    public void setTasksCompleted(int tasksCompleted) {
        this.tasksCompleted = tasksCompleted;
    }

    public void setMinutesWorked(long minutesWorked) {
        this.minutesWorked = minutesWorked;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setRatingPerformance(double ratingPerformance) {
        this.ratingPerformance = ratingPerformance;
    }

    public void setAssignedHoursADay(int assignedHoursADay) {
        this.assignedHoursADay = assignedHoursADay;
    }

    public void setCheckInOut(List<String> checkInOut) {
        this.checkInOut = checkInOut;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
