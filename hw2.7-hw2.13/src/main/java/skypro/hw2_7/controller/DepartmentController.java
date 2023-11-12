package skypro.hw2_7.controller;

import org.springframework.web.bind.annotation.*;
import skypro.hw2_7.sevice.DepartmentService;
import skypro.hw2_7.sevice.Employee;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    private final DepartmentService departmentService;


    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/{id}/employees")
    public Map<Integer, List<Employee>> getAllEmployeesByDepartmentWithPathVariable(@PathVariable int id) {
        return departmentService.getAllEmployeesByDepartment(id);
    }

    @GetMapping("/{id}/salary/sum")
    public int sumSalaryByDepartment(@PathVariable int id) {
        return departmentService.getTotalSalaryCostByDepartment(id);
    }

    @GetMapping("/{id}/salary/max")
    public Employee maxSalary(@PathVariable int id) {
        return departmentService.findMaxSalaryByDepartment(id);
    }

    @GetMapping("/{id}/salary/min")
    public Employee minSalary(@PathVariable int id) {
        return departmentService.findMinSalaryByDepartment(id);
    }

    @GetMapping("employees")
    public Map<Integer, List<Employee>> getAllEmployeesByDepartment() {
        return departmentService.getAllEmployees();
    }
}
