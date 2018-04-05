package com.example.demo;




import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;


import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

//http://localhost:8080/params/query/EmployeeDetailsById?id=0


@RestController
@RequestMapping("/params")
public class Query {
    ArrayList<Employee> employeeList=new ArrayList<Employee>() ;
    int turnOvers = 0;

    public Query(){
        initEmployees(employeeList);
    }

    @RequestMapping(value="/query/EmployeeDetailsById", method=RequestMethod.GET)
    @ResponseBody
    public Employee EmployeeDetailsById(@RequestParam(name="id", required=false, defaultValue="defId") String id) {
        Employee eTarget=null;
        for(int i=0;i<employeeList.size();i++){
            Employee e = employeeList.get(i);
            if(e.getId().equals(id)){
                eTarget = employeeList.get(i);
            }
        }
        if(eTarget!=null){
            this.EmployeeTotalTimeSpentWorkingInCompanyById(eTarget.getId());
            return eTarget;
        }else{
            return new Employee("/", "/","/", "/", "/",
            0, 0, "/",
            0, 0);
        }
    }


    @RequestMapping(value="/query/EmployeeTotalTimeSpentWorkingInCompanyById", method=RequestMethod.GET)
    @ResponseBody
    public String EmployeeTotalTimeSpentWorkingInCompanyById(@RequestParam(name="id", required=false, defaultValue="defId") String id) {
        Employee eTarget=null;
        for(int i=0;i<employeeList.size();i++){
            Employee e = employeeList.get(i);
            if(e.getId().equals(id)){
                eTarget = employeeList.get(i);
            }
        }

        if(eTarget!=null){
            ArrayList<String> checkInOut = eTarget.getCheckInOut();
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


    @RequestMapping(value="/query/AllEmployees", method=RequestMethod.GET)
    @ResponseBody
    public  ArrayList<Employee> AllEmployees() {
       for(int i=0;i<employeeList.size();i++){
           this.EmployeeTotalTimeSpentWorkingInCompanyById(i+"");
       }
       return employeeList;
    }


    @RequestMapping(value="/query/AllApplications", method=RequestMethod.GET)
    @ResponseBody
    public  ArrayList<Application> AllApplications() {
        ArrayList<Application> apps = new ArrayList<Application>();
        for(int i=0;i<employeeList.size();i++){
            Employee e = employeeList.get(i);
            ArrayList<Application> eApps= e.getApplications();
            for(int j=0;j<eApps.size();j++){
                if(apps.contains(eApps.get(j))==false){
                    apps.add(eApps.get(j));
                }
            }
        }
       return apps;
    }


    @RequestMapping(value="/query/TotalEmployees", method=RequestMethod.GET)
    @ResponseBody
    public String TotalEmployees() {
        return "TotalEmployees= "+employeeList.size();
    }


    @RequestMapping(value="/query/TurnOvers", method=RequestMethod.GET)
    @ResponseBody
    public  String TurnOvers() {
        return "TurnOvers= "+this.turnOvers;
    }


    @RequestMapping(value="/query/revenuePerEmployee", method=RequestMethod.GET)
    @ResponseBody
    public  String  revenuePerEmployee() {
        ArrayList<Application> apps = this.AllApplications();

        StringBuilder sb = new StringBuilder("");
        for(int i=0;i<apps.size();i++){
            int numOfDevelopers=0;
            for(int j=0;j<employeeList.size();j++){
                Employee e = employeeList.get(j);
                ArrayList<Application> eApps= e.getApplications();
                for(int k=0;k<eApps.size();k++){
                    if(eApps.get(k).equals(apps.get(i)))
                        numOfDevelopers+=1;
                }
            }

            sb.append("App name: "+apps.get(i).getName()+" Profit: " + apps.get(i).getProfit() +" Num of developers: "+numOfDevelopers
            +" Revenue per employee "+apps.get(i).getProfit()/numOfDevelopers);

            sb.append("\n");
        }
        return sb.toString();
    }


    @RequestMapping(value="/query/EnterEmployee", method=RequestMethod.POST )
    public void EnterEmployee(@RequestBody  Employee employee) {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Employee e = new Employee(employeeList.size()+"",employee.getName(),employee.getAge(),
                employee.getGender(), now.format(formatter),0, employee.getSalary(),
                employee.getJobPosition(),0,employee.getAssignedHoursADay());
        employeeList.add(e);
    }


    @RequestMapping(value="/query/RemoveEmployeeById", method=RequestMethod.DELETE)
    @ResponseBody
    public String RemoveEmployeeById(@RequestParam(name="id", required=true, defaultValue="defName") String id) {
        Employee eTarget=null;
        for(int i=0;i<employeeList.size();i++){
            Employee e = employeeList.get(i);
            if(e.getId().equals(id)){
                employeeList.remove(e);
                turnOvers++;
                return "Successfully removed employee with id: "+id;
            }
        }
        return "Employee with id: "+id+" not found";
    }


    @RequestMapping(value="/query/changeRatingPerformance", method=RequestMethod.PUT)
    @ResponseBody
    public String changeRatingPerformance(@RequestParam(name="id", required=true, defaultValue="defId") String id,
                                          @RequestParam(name="changeBy", required=true, defaultValue="defId") String changeBy) {

        Employee eTarget=null;
        for(int i=0;i<employeeList.size();i++){
            Employee e = employeeList.get(i);
            if(e.getId().equals(id)){
                eTarget = employeeList.get(i);
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


    @RequestMapping(value="/query/ChangeSalaryById", method=RequestMethod.PUT)
    @ResponseBody
    public String ChangeSalaryById(@RequestParam(name="id", required=true, defaultValue="defName") String id,
                                     @RequestParam(name="change", required=true, defaultValue="defName") String change){
        Employee eTarget=null;
        for(int i=0;i<employeeList.size();i++){
            Employee e = employeeList.get(i);
            if(e.getId().equals(id)){
                eTarget = employeeList.get(i);
            }
        }

        try{
            if(eTarget!=null){
                int currSalary = eTarget.getSalary();
                eTarget.setSalary(eTarget.getSalary()+Integer.parseInt(change));
                return "Salary for employee with id "+id+" change from "+ currSalary+" to "+eTarget.getSalary();
            }
            else{
                return "Employee with ID: "+id+" not found";
            }
        }catch (Exception e){
            return "invalid value for change!";
        }
    }


    @RequestMapping(value="/query/TotalProfit", method=RequestMethod.GET)
    @ResponseBody
    public String TotalProfit() {
        ArrayList<Application> apps = this.AllApplications();
        int total=0;
        StringBuilder sb = new StringBuilder("");

        for(int i=0;i<apps.size();i++){
          Application a = apps.get(i);
          total+=a.getProfit();
          sb.append(apps.get(i).getName()+" "+apps.get(i).getProfit()+"\n");
        }

        return sb.toString()+"TOTAL PROFIT: "+total;
    }


    @RequestMapping(value="/query/ChangeJobPosition", method=RequestMethod.PUT)
    @ResponseBody
    public String ChangeJobPosition(@RequestParam(name="id", required=true, defaultValue="defId") String id,
                                    @RequestParam(name="jobPosition", required=true, defaultValue="defJobPosition") String jobPosition) {
        Employee eTarget=null;
        for(int i=0;i<employeeList.size();i++){
            Employee e = employeeList.get(i);
            if(e.getId().equals(id)){
                eTarget = employeeList.get(i);
            }
        }

        if(eTarget!=null){
            if(jobPosition.equals("employee") || jobPosition.equals("manager") || jobPosition.equals("director") ){
                String tmp = eTarget.getJobPosition();
                eTarget.setJobPosition(jobPosition);
                return "Successfully changed job position to employee with id: "+id +" from "+tmp+" to "+eTarget.getJobPosition();
            }
            else{
                return "job position not valid.";
            }

        }else{
            return "Employee with id: "+id+" not found";
        }
    }



    @RequestMapping(value="/query/CheckInOut", method=RequestMethod.PUT)
    @ResponseBody
    public String CheckInOut(@RequestParam(name="id", required=true, defaultValue="defId") String id,
                             @RequestParam(name="checkInOut", required=true, defaultValue="/") String checkInOut) {
        //checkInOut value should be like  "10:00/25/04/2018-16:00/25/04/2018"
        Employee eTarget=null;
        for(int i=0;i<employeeList.size();i++){
            Employee e = employeeList.get(i);
            if(e.getId().equals(id)){
                eTarget = employeeList.get(i);
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
                eTarget.getCheckInOut().add(checkIn);
                eTarget.getCheckInOut().add(checkOut);

                this.EmployeeTotalTimeSpentWorkingInCompanyById(eTarget.getId());
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


    @RequestMapping(value="/query/TotalHoursADay", method=RequestMethod.GET)
    @ResponseBody
    public String TotalHoursADay(@RequestParam(name="id", required=true, defaultValue="defId") String id) {
        Employee eTarget=null;
        for(int i=0;i<employeeList.size();i++){
            Employee e = employeeList.get(i);
            if(e.getId().equals(id)){
                eTarget = employeeList.get(i);
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



    @RequestMapping(value="/query/AgeStatistics", method=RequestMethod.GET)
    @ResponseBody
    public String AgeStatistics() {
        ArrayList<Employee> allEmployees = this.AllEmployees();
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

    @RequestMapping(value="/query/GenderStatistics", method=RequestMethod.GET)
    @ResponseBody
    public String GenderStatistics() {
        ArrayList<Employee> allEmployees = this.AllEmployees();
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

    @RequestMapping(value="/query/AverageTasksCompletedPerEmployeeStatistics", method=RequestMethod.GET)
    @ResponseBody
    public String AverageTasksCompletedPerEmployeeStatistics() {
        ArrayList<Employee> allEmployees = this.AllEmployees();
        int totalTaskCompleted=0;
        for(int i=0;i<allEmployees.size();i++) {
            Employee e = allEmployees.get(i);
            totalTaskCompleted+=e.getTasksCompleted();
        }
        return "Average Tasks Completed Per Employee: "+totalTaskCompleted/allEmployees.size();
    }


    @RequestMapping(value="/query/AverageTimeSpentWorkingInCompanyPerEmployeeStatistics", method=RequestMethod.GET)
    @ResponseBody
    public String AverageTimeSpentWorkingInCompanyPerEmployeeStatistics() {
        ArrayList<Employee> allEmployees = this.AllEmployees();
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

    @RequestMapping(value="/query/AverageSalaryPerEmployeeStatistics", method=RequestMethod.GET)
    @ResponseBody
    public String AverageSalaryPerEmployeeStatistics() {
        ArrayList<Employee> allEmployees = this.AllEmployees();
        int totalSalary=0;
        for(int i=0;i<allEmployees.size();i++) {
            Employee e = allEmployees.get(i);
            totalSalary+=e.getSalary();
        }
        return "Average Salary Per Employee: "+totalSalary/allEmployees.size();
    }
    @RequestMapping(value="/query/JobPositionStatistics", method=RequestMethod.GET)
    @ResponseBody
    public String JobPositionStatistics() {
        ArrayList<Employee> allEmployees = this.AllEmployees();
        int employee=0,manager=0,director=0;
        StringBuilder sb = new StringBuilder("");
        for(int i=0;i<allEmployees.size();i++) {
            Employee e = allEmployees.get(i);
            if(e.getJobPosition().equals("employee"))
                employee++;
            else if(e.getJobPosition().equals("manager"))
                manager++;
            else if(e.getJobPosition().equals("director"))
                director++;
        }
        sb.append("Employees: "+employee+"\n");
        sb.append("Managers: "+manager+"\n");
        sb.append("Directors: "+director+"\n");

        return sb.toString();
    }
    @RequestMapping(value="/query/AverageRatingPerformancePerEmployeeStatistics", method=RequestMethod.GET)
    @ResponseBody
    public String AverageRatingPerformancePerEmployeeStatistics() {
        ArrayList<Employee> allEmployees = this.AllEmployees();
        double totalRating=0;
        StringBuilder sb = new StringBuilder("");
        for(int i=0;i<allEmployees.size();i++) {
            Employee e = allEmployees.get(i);
            totalRating+=e.getRatingPerformance();
        }
        return "Average Rating Performance Per Employee: "+totalRating/allEmployees.size();
    }

    @RequestMapping(value="/query/AverageAssignedHoursADayPerEmployeeStatistics", method=RequestMethod.GET)
    @ResponseBody
    public String AverageAssignedHoursADayPerEmployeeStatistics() {
        ArrayList<Employee> allEmployees = this.AllEmployees();
        double totalAssignedHoursADay=0;
        StringBuilder sb = new StringBuilder("");
        for(int i=0;i<allEmployees.size();i++) {
            Employee e = allEmployees.get(i);
            totalAssignedHoursADay+=e.getAssignedHoursADay();
        }
        return "Average Assigned Hours A Day PerEmployee:  "+totalAssignedHoursADay/allEmployees.size();
    }





    public void initEmployees(ArrayList<Employee> employeeList){

        Application angryBirds = new Application("angryBirds",340);
        Application viber = new Application("viber",520);
        Application twitter = new Application("twitter",600);
        Application instagram = new Application("instagram",320);
        Application facebook = new Application("facebook",890);
        Application snapchat = new Application("snapchat",130);
        Application whatsUp = new Application("whatsUp",220);
        Application youtube = new Application("youtube",660);


        Employee e1 = new Employee("0", "John", "22", "M", "24/03/2018",
        20, 20000, "employee",
        4.3, 8);
        e1.getApplications().add(angryBirds);
        e1.getApplications().add(viber);
        e1.getCheckInOut().add("08:00/26/03/2017");
        e1.getCheckInOut().add("16:00/26/03/2017");

        Employee e2 = new Employee("1", "Ana", "26", "F", "22/06/2018",
                33, 24000, "manager",
                7.3, 6);
        e2.getApplications().add(facebook);
        e2.getApplications().add(viber);
        e2.getCheckInOut().add("08:00/26/03/2017");
        e2.getCheckInOut().add("12:00/26/03/2017");

        Employee e3 = new Employee("2", "Mark", "33", "M", "12/09/2018",
                4, 30000, "director",
                10.3, 5);
        e3.getApplications().add(facebook);
        e3.getApplications().add(viber);
        e3.getCheckInOut().add("08:00/26/03/2017");
        e3.getCheckInOut().add("09:20/26/03/2017");
        e3.getCheckInOut().add("10:20/27/03/2017");
        e3.getCheckInOut().add("15:20/27/03/2017");

        employeeList.add(e1);
        employeeList.add(e2);
        employeeList.add(e3);
    }
}