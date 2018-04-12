package com.example.test.model;








public class Application {
    public Application(){}

    public Long id;

    public String personData;

    public String personSkill;

    public String title;

    public Integer rating;

    public int profit;

    public void setId(Long id) {
        this.id = id;
    }

    public void setPersonData(String personData) {
        this.personData = personData;
    }

    public void setPersonSkill(String personSkill) {
        this.personSkill = personSkill;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }

    public void setEmployeeManager(Employee employeeManager) {
        this.employeeManager = employeeManager;
    }

    public Long getId() {
        return id;
    }

    public String getPersonData() {
        return personData;
    }

    public String getPersonSkill() {
        return personSkill;
    }

    public String getTitle() {
        return title;
    }

    public Integer getRating() {
        return rating;
    }

    public int getProfit() {
        return profit;
    }

    public Employee getEmployeeManager() {
        return employeeManager;
    }

    public Employee employeeManager;
}
