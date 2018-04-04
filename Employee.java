package com.example.demo;

import java.util.ArrayList;

public class Employee {

    private String id;
    private String name;
    private String age;
    private String gender;
    private String dateEmployed;
    private String dateFired;
    private int tasksCompleted;
    private ArrayList<String> checkInOut;
    private ArrayList<Application> applications;
    private int minutesWorked;
    private int salary;
    private String jobPosition;
    private double ratingPerformance;
    private int assignedHoursADay;


    public Employee() { }

    public Employee(String id, String name, String age, String gender, String dateEmployed, String dateFired,
                    int tasksCompleted, ArrayList<String> checkInOut, int salary, String jobPosition, double ratingPerformance,
                    int assignedHoursADay) {
        this.id = id;
        this.name=name;
        this.age=age;
        this.gender=gender;
        this.dateEmployed=dateEmployed;
        this.dateFired=dateFired;
        this.tasksCompleted=tasksCompleted;
        this.checkInOut = checkInOut;
        this.applications = new ArrayList<Application>();
        this.minutesWorked=0;
        this.salary=salary;
        this.jobPosition=jobPosition;
        this.ratingPerformance=ratingPerformance;
        this.assignedHoursADay=assignedHoursADay;
    }


    public String getId() {return this.id; }
    public String getName() {return this.name; }
    public String getAge() {return this.age; }
    public String getGender(){return this.gender;}
    public String getDateEmployed(){return this.dateEmployed;}
    public String getDateFired(){return this.dateFired;}
    public int getTasksCompleted(){return this.tasksCompleted;}
    public ArrayList<Application> getApplications() { return this.applications; }
    public ArrayList<String> getCheckInOut() { return this.checkInOut; }
    public int getMinutesWorked(){return this.minutesWorked;}
    public void setMinutesWorked(int amount){this.minutesWorked=amount;}
    public void addApplication(Application app){ this.applications.add(app); }
    public int getSalary() { return salary; }
    public void setSalary(int salary) { this.salary = salary; }
    public String getJobPosition() { return jobPosition; }
    public void setJobPosition(String jobPosition) { this.jobPosition = jobPosition; }
    public double getRatingPerformance() { return ratingPerformance; }
    public void setRatingPerformance(double ratingPerformance) { this.ratingPerformance = ratingPerformance; }
    public void setAssignedHoursADay(int assignedHoursADay) { this.assignedHoursADay = assignedHoursADay; }
    public int getAssignedHoursADay() { return assignedHoursADay; }
}