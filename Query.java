package com.example.demo;


import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
            this.EmployeeTimeSpentWorkingById(eTarget.getId());
            return eTarget;
        }else{
            return new Employee("/", "/", "/", "/", "/", "/",
            0, new ArrayList<String>(), 0,"/",0,0);
        }
    }


    @RequestMapping(value="/query/EmployeeTimeSpentWorkingById", method=RequestMethod.GET)
    @ResponseBody
    public String EmployeeTimeSpentWorkingById(@RequestParam(name="id", required=false, defaultValue="defId") String id) {
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

            for(int i=0;i<checkInOut.size();i+=2){
                int checkInMinutes = Integer.parseInt(checkInOut.get(i).split("/")[0].split(":")[1]);
                int checkInHour = Integer.parseInt(checkInOut.get(i).split("/")[0].split(":")[0]);

                int checkOutMinutes = Integer.parseInt(checkInOut.get(i+1).split("/")[0].split(":")[1]);
                int checkOutHour = Integer.parseInt( checkInOut.get(i+1).split("/")[0].split(":")[0]);

                if(checkInMinutes<checkOutMinutes){
                    //checkInOut.add("08:23/25/03/2017");
                    //checkInOut.add("16:46/25/03/2017");
                    int dif = checkOutMinutes - checkInMinutes;
                    dif+=(checkOutHour-checkInHour)*60;
                    eTarget.setMinutesWorked(eTarget.getMinutesWorked()+dif);

                }else if(checkInMinutes>checkOutMinutes){
                    //checkInOut.add("08:30/25/03/2017");
                    //checkInOut.add("09:10/25/03/2017");
                    int dif = checkOutHour - checkInHour;
                    dif=dif*60;
                    dif=dif-checkInMinutes+checkOutMinutes;
                    eTarget.setMinutesWorked(eTarget.getMinutesWorked()+dif);
                }else{
                    //checkInOut.add("08:00/25/03/2017");
                    //checkInOut.add("15:00/25/03/2017");
                    int dif = checkOutHour - checkInHour;
                    dif = dif *60;
                    eTarget.setMinutesWorked(eTarget.getMinutesWorked()+dif);
                }
            }

            return "Time spent working: "+eTarget.getMinutesWorked()/60 +"h "+eTarget.getMinutesWorked()%60+"m";
        }
        else return "Employee with ID: "+id+" not found";
    }


    @RequestMapping(value="/query/AllEmployees", method=RequestMethod.GET)
    @ResponseBody
    public  ArrayList<Employee> AllEmployees() {
       for(int i=0;i<employeeList.size();i++){
           this.EmployeeTimeSpentWorkingById(i+"");
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
        return "Total employees= "+employeeList.size();
    }


    @RequestMapping(value="/query/TurnOvers", method=RequestMethod.GET)
    @ResponseBody
    public  String TurnOvers() {
        return "TurnOvers= "+this.turnOvers;
    }


    @RequestMapping(value="/query/revenuePerEmployee", method=RequestMethod.GET)
    @ResponseBody
    public  String  revenuePerEmployee() {
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

            sb.append("App name: "+apps.get(i).getName()+" Profit: " + apps.get(i).getProfit() +"Num of developers: "+numOfDevelopers
            +"Revenue per employee"+apps.get(i).getProfit()/numOfDevelopers);

            sb.append("<br>");
        }
        return sb.toString();
    }


    @RequestMapping(value="/query/EnterEmployee", method=RequestMethod.POST )
    public void EnterEmployee(@RequestBody  Employee employee) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        String []sDate = dateFormat.format(date).split(" ");

        String year = sDate[0].split("/")[0];
        String month =  sDate[0].split("/")[1];
        String day = sDate[0].split("/")[2];

        Employee e = new Employee(employeeList.size()+"",employee.getName(),employee.getAge(),employee.getGender(),
                day+"/"+month+"/"+year ,"/",0,new ArrayList<String>(),
                20000,employee.getJobPosition(),0,employee.getAssignedHoursADay());

        employeeList.add(e);
    }


    @RequestMapping(value="/query/RemoveEmployeeById", method=RequestMethod.DELETE)
    @ResponseBody
    public String RemoveEmployeeById(@RequestParam(name="id", required=true, defaultValue="defName") String id) {

        Employee eTarget=null;
        for(int i=0;i<employeeList.size();i++){
            Employee e = employeeList.get(i);
            if(e.getId().equals(id)){
                eTarget = employeeList.get(i);
            }
        }

        if(eTarget!=null){
            employeeList.remove(Integer.parseInt(id));
            turnOvers++;
            return "Successfully removed employee with id: "+id;
        }else{
            return "Employee with id: "+id+" not found";
        }


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

                eTarget.setRatingPerformance(eTarget.getRatingPerformance()+Double.parseDouble(changeBy));
                return "RatingPerformance for employee with id "+id+" changed to "+eTarget.getRatingPerformance();
            }
            else{
                return "Employee with ID: "+id+" not found";
            }
        }catch (Exception e){
            return "invalid value for changeBy!";
        }

    }


    @RequestMapping(value="/query/IncreaseSalaryById", method=RequestMethod.PUT)
    @ResponseBody
    public String IncreaseSalaryById(@RequestParam(name="id", required=true, defaultValue="defName") String id,
                                     @RequestParam(name="increase", required=true, defaultValue="defName") String increase){
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
                eTarget.setSalary(eTarget.getSalary()+Integer.parseInt(increase));
                return "Salary for employee with id "+id+" increased from "+ currSalary+" to "+eTarget.getSalary();
            }
            else{
                return "Employee with ID: "+id+" not found";
            }
        }catch (Exception e){
            return "invalid value for increase!";
        }


    }


    @RequestMapping(value="/query/TotalProfit", method=RequestMethod.GET)
    @ResponseBody
    public String TotalProfit() {

        ArrayList<Application> apps = new ArrayList<Application>();
        int total=0;


        for(int i=0;i<employeeList.size();i++){
            Employee e = employeeList.get(i);
            ArrayList<Application> eApps= e.getApplications();
            for(int j=0;j<eApps.size();j++){
                if(apps.contains(eApps.get(j))==false){
                    apps.add(eApps.get(j));
                    total+=eApps.get(j).getProfit();
                }
            }
        }
        StringBuilder sb = new StringBuilder("");

        for(int k=0;k<apps.size();k++){
            sb.append(apps.get(k).getName()+" "+apps.get(k).getProfit()+"<br>");
        }

        return sb.toString()+" "+"TOTAL PROFIT: "+total;
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
                eTarget.setJobPosition(jobPosition);
                return "Successfully changed job position to employee with id: "+id;
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
            eTarget.getCheckInOut().add(checkInOut.split("-")[0]);
            eTarget.getCheckInOut().add(checkInOut.split("-")[1]);

            this.EmployeeTimeSpentWorkingById(eTarget.getId());
            return "Successfully added checkInOut to employee with id: "+id;
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
                String arrived = eTarget.getCheckInOut().get(i);
                String date = "";
                String left = eTarget.getCheckInOut().get(i+1);
                int hours=0,minutes=0;
                //arrived -> "10:00/02/01/2018";
                //left -> "13:40/02/01/2018";
                date = arrived.split("/")[1]+"/"+arrived.split("/")[2]+"/"+arrived.split("/")[3];

                arrived = arrived.split("/")[0]; //10:00
                left = left.split("/")[0]; // 13:40

                String arrivedH = arrived.split(":")[0]; //10
                String arrivedM = arrived.split(":")[1]; //00

                String leftH = left.split(":")[0];//13
                String leftM = left.split(":")[1];//40

                if(Integer.parseInt(arrivedH)<Integer.parseInt(leftH)){
                    //arrived -> "10:20/02/01/2018";
                    //left -> "13:40/02/01/2018";
                    if(Integer.parseInt(arrivedM)<Integer.parseInt(leftM)){
                        //arrived -> "10:20/02/01/2018";
                        //left -> "13:40/02/01/2018";
                        hours=Integer.parseInt(leftH)-Integer.parseInt(arrivedH);
                        minutes=Integer.parseInt(leftM)-Integer.parseInt(arrivedM);
                    }
                    else if(Integer.parseInt(arrivedM)>Integer.parseInt(leftM)){
                        //arrived -> "10:30/02/01/2018";
                        //left ->    "13:10/02/01/2018";
                        hours = Integer.parseInt(leftH)-Integer.parseInt(arrivedH)-1;
                        minutes = 60-Integer.parseInt(arrivedM)+Integer.parseInt(leftM);
                    }else{
                         //arrived -> "10:10/02/01/2018";
                         //left ->    "13:10/02/01/2018";
                        hours = Integer.parseInt(leftH)-Integer.parseInt(arrivedH);
                        minutes = 0;
                    }
                }else if(Integer.parseInt(arrivedH)==Integer.parseInt(leftH)){
                    //arrived -> "10:00/02/01/2018";
                    //left -> "10:40/02/01/2018";
                    if(Integer.parseInt(arrivedM)<Integer.parseInt(leftM)){
                        //arrived -> "10:20/02/01/2018";
                        //left -> "10:40/02/01/2018";
                        hours=0;
                        minutes=Integer.parseInt(leftM)-Integer.parseInt(arrivedM);
                    }
                    else if(Integer.parseInt(arrivedM)>Integer.parseInt(leftM)){
                        //ne e mozno scenariovo
                        //arrived -> "10:30/02/01/2018";
                        //left ->    "10:10/02/01/2018";
                        hours = -1;
                        minutes = -1;
                    }else{
                        //arrived -> "10:10/02/01/2018";
                        //left ->    "10:10/02/01/2018";
                        hours = 0;
                        minutes = 0;
                    }
                }
                if(hours*60+minutes>=eTarget.getAssignedHoursADay()*60){
                    sb.append( "Obraboteni se dodelenite casovi za den "+date+"\n");
                }else{
                    sb.append( "Ne se obraboteni "+eTarget.getAssignedHoursADay()+" na den "+date);
                    int remainingMinutes = (eTarget.getAssignedHoursADay()*60)-(hours*60)-minutes;
                    sb.append( "Vi ostanuvaat uste "+remainingMinutes/60+"h "+remainingMinutes%60+"m"+"\n");
                }
            }
            return sb.toString();
        }else{
             return "Employee with id: "+id+" not found";
        }
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

        ArrayList<String> checkInOut = new ArrayList<String>();
        checkInOut.add("08:20/25/03/2017");
        checkInOut.add("16:00/25/03/2017");

        checkInOut.add("08:00/26/03/2017");
        checkInOut.add("16:00/26/03/2017");

        Employee e1 = new Employee("0","User0","20","M",
                    "25/03/2017","/",88,checkInOut,20000,"employee",
                0,8);
        e1.addApplication(angryBirds);
        e1.addApplication(viber);

        checkInOut = new ArrayList<String>();
        checkInOut.add("10:00/01/01/2018");
        checkInOut.add("15:00/01/01/2018");

        checkInOut.add("10:00/02/01/2018");
        checkInOut.add("13:40/02/01/2018");

        Employee e2 = new Employee("1","User1","21","M",
                                "01/01/2018","/",212,checkInOut,20000,"manager",
                0,8);
        e2.addApplication(twitter);
        e2.addApplication(viber);


        checkInOut = new ArrayList<String>();
        checkInOut.add("08:50/25/03/2017");
        checkInOut.add("09:20/25/03/2017");

        checkInOut.add("08:20/26/03/2017");
        checkInOut.add("09:20/26/03/2017");

        Employee e3 = new Employee("2","User2","21","M",
                "01/01/2018","/",212,checkInOut,20000,"director",
                0,8);


        employeeList.add(e1);
        employeeList.add(e2);
        employeeList.add(e3);
    }
}