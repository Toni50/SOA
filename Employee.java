package com.example.demo;

import java.util.ArrayList;

public class Employee {

    private String id;
    private String name;
    private String age;
    private String gender;
    private String dateEmployed;
    private int tasksCompleted;
    private long minutesWorked;
    private int salary;
    private String jobPosition;
    private double ratingPerformance;
    private int assignedHoursADay;
    private ArrayList<String> checkInOut;
    private ArrayList<Application> applications;

    public Employee() { }

    public Employee(String id, String name, String age, String gender, String dateEmployed,
                    int tasksCompleted,  int salary, String jobPosition,
                    double ratingPerformance, int assignedHoursADay) {
        this.id = id;
        this.name=name;
        this.age=age;
        this.gender=gender;
        this.dateEmployed=dateEmployed;
        this.tasksCompleted=tasksCompleted;
        this.salary=salary;
        this.jobPosition=jobPosition;
        this.ratingPerformance=ratingPerformance;
        this.assignedHoursADay=assignedHoursADay;
        this.checkInOut = new ArrayList<String>();
        this.applications = new ArrayList<Application>();
    }


    public String getId() {return this.id; }

    public String getName() {return this.name; }
    public void setName(String name) { this.name = name; }

    public String getAge() {return this.age; }
    public void setAge(String age) { this.age = age; }


    public String getGender(){return this.gender;}
    public void setGender(String gender) { this.gender = gender; }


    public String getDateEmployed(){return this.dateEmployed;}
    public void setDateEmployed(String dateEmployed) { this.dateEmployed = dateEmployed; }

    public int getTasksCompleted(){return this.tasksCompleted;}
    public void setTasksCompleted(int tasksCompleted) { this.tasksCompleted = tasksCompleted; }

    public ArrayList<String> getCheckInOut() { return this.checkInOut; }

    public ArrayList<Application> getApplications() { return this.applications; }

    public long getMinutesWorked(){return this.minutesWorked;}
    public void setMinutesWorked(long amount){this.minutesWorked=amount;}

    public int getSalary() { return salary; }
    public void setSalary(int salary) { this.salary = salary; }

    public String getJobPosition() { return jobPosition; }
    public void setJobPosition(String jobPosition) { this.jobPosition = jobPosition; }

    public double getRatingPerformance() { return ratingPerformance; }
    public void setRatingPerformance(double ratingPerformance) { this.ratingPerformance = ratingPerformance; }

    public int getAssignedHoursADay() { return assignedHoursADay; }
    public void setAssignedHoursADay(int assignedHoursADay) { this.assignedHoursADay = assignedHoursADay; }
}