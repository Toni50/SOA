package com.example.test.web;



import com.example.test.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stats")
public class StatsController {
    @Autowired
    StatsService statsService;

    @RequestMapping(value="/query/EmployeeTotalTimeSpentWorkingInCompanyById", method= RequestMethod.GET)
    @ResponseBody
    public String EmployeeTotalTimeSpentWorkingInCompanyById(@RequestParam(name="id", required=false, defaultValue="defId") Long id) {
        return statsService.EmployeeTotalTimeSpentWorkingInCompanyById(id);
    }

    @RequestMapping(value="/query/TotalEmployees", method=RequestMethod.GET)
    @ResponseBody
    public String TotalEmployees() {
        return statsService.TotalEmployees();
    }

    @RequestMapping(value="/query/TurnOvers", method=RequestMethod.GET)
    @ResponseBody
    public String TurnOvers() {
        return statsService.TurnOvers();
    }

    @RequestMapping(value="/query/revenuePerEmployee", method=RequestMethod.GET)
    @ResponseBody
    public String revenuePerEmployee() {
        return statsService.revenuePerEmployee();
    }

    @RequestMapping(value="/query/changeRatingPerformance", method=RequestMethod.PUT)
    @ResponseBody
    public String changeRatingPerformance(@RequestParam(name="id", required=true, defaultValue="defId") Long id,
                                          @RequestParam(name="changeBy", required=true, defaultValue="defId") String changeBy) {
        return statsService.changeRatingPerformance(id,changeBy);
    }

    @RequestMapping(value="/query/TotalProfit", method=RequestMethod.GET)
    @ResponseBody
    public String TotalProfit() {
        return statsService.TotalProfit();
    }

    @RequestMapping(value="/query/TotalHoursADay", method=RequestMethod.GET)
    @ResponseBody
    public String TotalHoursADay(@RequestParam(name="id", required=true, defaultValue="defId") Long id) {
        return statsService.TotalHoursADay(id);
    }


    @RequestMapping(value="/query/AgeStatistics", method=RequestMethod.GET)
    @ResponseBody
    public String AgeStatistics() {
        return statsService.AgeStatistics();
    }

    @RequestMapping(value="/query/GenderStatistics", method=RequestMethod.GET)
    @ResponseBody
    public String GenderStatistics() {
        return statsService.GenderStatistics();
    }

    @RequestMapping(value="/query/AverageTasksCompletedPerEmployeeStatistics", method=RequestMethod.GET)
    @ResponseBody
    public String AverageTasksCompletedPerEmployeeStatistics() {
        return statsService.AverageTasksCompletedPerEmployeeStatistics();
    }

    @RequestMapping(value="/query/AverageTimeSpentWorkingInCompanyPerEmployeeStatistics", method=RequestMethod.GET)
    @ResponseBody
    public String AverageTimeSpentWorkingInCompanyPerEmployeeStatistics() {
        return statsService.AverageTimeSpentWorkingInCompanyPerEmployeeStatistics();
    }


    @RequestMapping(value="/query/AverageSalaryPerEmployeeStatistics", method=RequestMethod.GET)
    @ResponseBody
    public String AverageSalaryPerEmployeeStatistics() {
        return statsService.AverageSalaryPerEmployeeStatistics();
    }

    @RequestMapping(value="/query/RolesStatistics", method=RequestMethod.GET)
    @ResponseBody
    public String RolesStatistics() {
        return statsService.RolesStatistics();
    }

    @RequestMapping(value="/query/AverageRatingPerformancePerEmployeeStatistics", method=RequestMethod.GET)
    @ResponseBody
    public String AverageRatingPerformancePerEmployeeStatistics() {
        return statsService.AverageRatingPerformancePerEmployeeStatistics();
    }

    @RequestMapping(value="/query/AverageAssignedHoursADayPerEmployeeStatistics", method=RequestMethod.GET)
    @ResponseBody
    public String AverageAssignedHoursADayPerEmployeeStatistics() {
        return statsService.AverageAssignedHoursADayPerEmployeeStatistics();
    }

}
