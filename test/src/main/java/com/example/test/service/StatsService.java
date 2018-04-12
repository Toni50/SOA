package com.example.test.service;

public interface StatsService {
    public String EmployeeTotalTimeSpentWorkingInCompanyById(Long id);
    public String TotalEmployees();
    public String TurnOvers();
    public String revenuePerEmployee();
    public String changeRatingPerformance(Long id, String changeBy);
    public String TotalProfit();
    public String TotalHoursADay(Long id);
    public String AgeStatistics();
    public String GenderStatistics();
    public String AverageTasksCompletedPerEmployeeStatistics();
    public String AverageTimeSpentWorkingInCompanyPerEmployeeStatistics();
    public String AverageSalaryPerEmployeeStatistics();
    public String RolesStatistics();
    public String AverageRatingPerformancePerEmployeeStatistics();
    public String AverageAssignedHoursADayPerEmployeeStatistics();
}
