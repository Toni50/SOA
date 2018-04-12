package com.example.test.service.impl;



import com.example.test.model.Application;
import com.example.test.model.Employee;
import com.example.test.persistance.PersistanceMock;
import com.example.test.service.PayrollService;
import com.example.test.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class StatsServiceImpl implements StatsService {


@Autowired
    private PersistanceMock persistanceMock;
    StatsServiceImpl() {
    }
    @Autowired
    PayrollService payrollService;

    @Override
    public String EmployeeTotalTimeSpentWorkingInCompanyById(Long id) {
        Employee eTarget=null;
        for(int i=0;i<persistanceMock.employees.size();i++){
            Employee e = persistanceMock.employees.get(i);
            if(e.id==id){
                eTarget = persistanceMock.employees.get(i);
            }
        }

        if(eTarget!=null){
            List<String> checkInOut = eTarget.getCheckInOut();
            eTarget.setMinutesWorked(0);
            long totalMinutes=0;

            for(int i=0;i<checkInOut.size();i+=2){
                String checkIn = eTarget.getCheckInOut().get(i);//10:00/25/04/2018
                String checkOut = eTarget.getCheckInOut().get(i+1);//16:00/25/04/2018

                //to print LocalDateTime checkOutL.format(formatter).toString() ->10:00/25/04/2018
                // with formatter  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm/dd/MM/yyyy");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm/dd/MM/yyyy");
                LocalDateTime checkInL = LocalDateTime.parse(checkIn,formatter);
                LocalDateTime checkOutL = LocalDateTime.parse(checkOut,formatter);

                LocalTime initialTime = checkInL.toLocalTime();
                LocalTime finalTime = checkOutL.toLocalTime();

                totalMinutes += ChronoUnit.MINUTES.between(initialTime, finalTime);
            }
            eTarget.setMinutesWorked(totalMinutes);

            return "Total time spent working in company: "+totalMinutes/60+"h "+totalMinutes%60+"m";
        }
        else return "Employee with ID: "+id+" not found";
    }

    @Override
    public String TotalEmployees() {
        return "TotalEmployees= "+persistanceMock.employees.size();
    }

    @Override
    public  String TurnOvers() {
        return "TurnOvers= "+this.persistanceMock.turnOvers;
    }

    @Override
    public  String  revenuePerEmployee() {
        List<Application> apps = payrollService.AllApplications();

        StringBuilder sb = new StringBuilder("");
        for(int i=0;i<apps.size();i++){
            int numOfDevelopers=0;
            for(int j=0;j<persistanceMock.employees.size();j++){
                Employee e = persistanceMock.employees.get(j);
                List<Application> eApps= e.getApplications();
                for(int k=0;k<eApps.size();k++){
                    if(eApps.get(k).getId()==apps.get(i).getId())
                        numOfDevelopers+=1;
                }
            }

            sb.append("App name: "+apps.get(i).getTitle()+" Profit: " + apps.get(i).getProfit() +" Num of developers: "+numOfDevelopers
                    +" Revenue per employee "+apps.get(i).getProfit()/numOfDevelopers);

            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public String changeRatingPerformance(Long id, String changeBy) {

        Employee eTarget=null;
        for(int i=0;i<persistanceMock.employees.size();i++){
            Employee e = persistanceMock.employees.get(i);
            if(e.getId()==id){
                eTarget = persistanceMock.employees.get(i);
            }
        }

        try{
            if(eTarget!=null){
                double tmp = eTarget.getRatingPerformance();
                eTarget.setRatingPerformance(eTarget.getRatingPerformance()+Double.parseDouble(changeBy));
                return "RatingPerformance for employee with id "+id+" changed from "+tmp+" to "+eTarget.getRatingPerformance();
            }
            else{
                return "Employee with ID: "+id+" not found";
            }
        }catch (Exception e){
            return "invalid value for changeBy!";
        }
    }


    @Override
    public String TotalProfit() {
        List<Application> apps = payrollService.AllApplications();
        int total=0;
        StringBuilder sb = new StringBuilder("");

        for(int i=0;i<apps.size();i++){
            Application a = apps.get(i);
            total+=a.getProfit();
            sb.append(apps.get(i).getTitle()+" "+apps.get(i).getProfit()+"\n");
        }

        return sb.toString()+"TOTAL PROFIT: "+total;
    }

    @Override

    public String TotalHoursADay(Long id) {
        Employee eTarget=null;
        for(int i=0;i<persistanceMock.employees.size();i++){
            Employee e = persistanceMock.employees.get(i);
            if(e.getId()==id){
                eTarget = persistanceMock.employees.get(i);
            }
        }
        StringBuilder sb = new StringBuilder("");
        if(eTarget!=null){
            for(int i=0;i<eTarget.getCheckInOut().size();i=i+2){
                String checkIn = eTarget.getCheckInOut().get(i);//10:00/25/04/2018
                String checkOut = eTarget.getCheckInOut().get(i+1);//16:00/25/04/2018
                long totalHours=0,totalMinutes=0;

                //to print LocalDateTime checkOutL.format(formatter).toString() ->10:00/25/04/2018
                // with formatter  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm/dd/MM/yyyy");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm/dd/MM/yyyy");
                LocalDateTime checkInL = LocalDateTime.parse(checkIn,formatter);
                LocalDateTime checkOutL = LocalDateTime.parse(checkOut,formatter);

                LocalTime initialTime = checkInL.toLocalTime();
                LocalTime finalTime = checkOutL.toLocalTime();

                totalHours = ChronoUnit.MINUTES.between(initialTime, finalTime)/60;
                totalMinutes = ChronoUnit.MINUTES.between(initialTime, finalTime)%60;

                if(totalHours>=eTarget.getAssignedHoursADay()){
                    sb.append( "Obraboteni se dodelenite casovi za den "+checkInL.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+"\n");
                }else{
                    sb.append( "Ne se obraboteni "+eTarget.getAssignedHoursADay()+"h na den "+checkInL.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    long remainingMinutes = (eTarget.getAssignedHoursADay()*60)-(totalHours*60)-totalMinutes;
                    sb.append( " Vi ostanuvaat uste "+remainingMinutes/60+"h "+remainingMinutes%60+"m"+"\n");
                }
            }
            return sb.toString();

        }else{
            return "Employee with id: "+id+" not found";
        }
    }

    @Override
    public String AgeStatistics() {
        List<Employee> allEmployees = payrollService.AllEmployees();
        StringBuilder sb = new StringBuilder("");
        int age_20_29=0,age_30_39=0,age_40_49=0,age_50_59=0;
        int under20=0,above59=0;


        for(int i=0;i<allEmployees.size();i++){
            Employee e = allEmployees.get(i);
            int curAge = Integer.parseInt(e.getAge());

            if(curAge<20)
                under20++;
            else if(curAge>=20 && curAge<=29)
                age_20_29++;
            else if(curAge>=30 && curAge<=39)
                age_30_39++;
            else if(curAge>=40 && curAge<=49)
                age_40_49++;
            else if(curAge>=50 && curAge<=59)
                age_50_59++;
            else if(curAge>=60)
                above59++;
        }
        sb.append("under 20: "+under20+"\n");
        sb.append("from 20 to 29: "+age_20_29+"\n");
        sb.append("from 30 to 39: "+age_30_39+"\n");
        sb.append("from 40 to 49: "+age_40_49+"\n");
        sb.append("from 50 to 59: "+age_50_59+"\n");
        sb.append("above 59: "+above59+"\n");

        return sb.toString();
    }

    @Override
    public String GenderStatistics() {
        List<Employee> allEmployees = payrollService.AllEmployees();
        int males=0,females=0;
        for(int i=0;i<allEmployees.size();i++){
            Employee e = allEmployees.get(i);
            if(e.getGender().equals("M"))
                males++;
            else if(e.getGender().equals("F"))
                females++;
        }
        return "Males: "+males+"\nFemales: "+females;
    }


    @Override
    public String AverageTasksCompletedPerEmployeeStatistics() {
        List<Employee> allEmployees = payrollService.AllEmployees();
        int totalTaskCompleted=0;
        for(int i=0;i<allEmployees.size();i++) {
            Employee e = allEmployees.get(i);
            totalTaskCompleted+=e.getTasksCompleted();
        }
        return "Average Tasks Completed Per Employee: "+totalTaskCompleted/allEmployees.size();
    }

    @Override
    public String AverageTimeSpentWorkingInCompanyPerEmployeeStatistics() {
        List<Employee> allEmployees = payrollService.AllEmployees();
        long totalMinutesWorked=0;
        for(int i=0;i<allEmployees.size();i++) {
            Employee e = allEmployees.get(i);
            totalMinutesWorked+=e.getMinutesWorked();
        }

        totalMinutesWorked=totalMinutesWorked/allEmployees.size();
        long hours = totalMinutesWorked/60;
        long minutes= totalMinutesWorked%60;
        return "Average Time Spent Working In Company Per Employee: "+hours+"h "+minutes+"m";
    }

    @Override
    public String AverageSalaryPerEmployeeStatistics() {
        List<Employee> allEmployees = payrollService.AllEmployees();
        int totalSalary=0;
        for(int i=0;i<allEmployees.size();i++) {
            Employee e = allEmployees.get(i);
            totalSalary+=e.getSalary();
        }
        return "Average Salary Per Employee: "+totalSalary/allEmployees.size();
    }

    @Override
    public String RolesStatistics() {
        //possible values CEO Manager Project Manager Employee
        List<Employee> allEmployees = payrollService.AllEmployees();
        int ceo=0,manager=0,project_manager=0,employee=0;

        StringBuilder sb = new StringBuilder("");
        for(int i=0;i<allEmployees.size();i++) {
            Employee e = allEmployees.get(i);
            if(e.getRole().getTitle().equals("CEO"))
                ceo++;
            else if(e.getRole().getTitle().equals("Manager"))
                manager++;
            else if(e.getRole().getTitle().equals("Project Manager"))
                project_manager++;
            else if(e.getRole().getTitle().equals("Employee"))
                employee++;
        }
        sb.append("Employees: "+employee+"\n");
        sb.append("Project Managers: "+project_manager+"\n");
        sb.append("Managers: "+manager+"\n");
        sb.append("CEOs: "+ceo+"\n");

        return sb.toString();
    }


    @Override
    public String AverageRatingPerformancePerEmployeeStatistics() {
        List<Employee> allEmployees = payrollService.AllEmployees();
        double totalRating=0;
        StringBuilder sb = new StringBuilder("");
        for(int i=0;i<allEmployees.size();i++) {
            Employee e = allEmployees.get(i);
            totalRating+=e.getRatingPerformance();
        }
        return "Average Rating Performance Per Employee: "+totalRating/allEmployees.size();
    }

    @Override
    public String AverageAssignedHoursADayPerEmployeeStatistics() {
        List<Employee> allEmployees = payrollService.AllEmployees();
        double totalAssignedHoursADay=0;
        StringBuilder sb = new StringBuilder("");
        for(int i=0;i<allEmployees.size();i++) {
            Employee e = allEmployees.get(i);
            totalAssignedHoursADay+=e.getAssignedHoursADay();
        }
        return "Average Assigned Hours A Day PerEmployee:  "+totalAssignedHoursADay/allEmployees.size();
    }





}
