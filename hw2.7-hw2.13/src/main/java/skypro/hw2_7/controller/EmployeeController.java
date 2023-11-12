package skypro.hw2_7.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import skypro.hw2_7.exceptions.NotValidCharacterException;
import skypro.hw2_7.sevice.Employee;
import skypro.hw2_7.sevice.EmployeeService;

import java.util.Collection;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;


    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(value = "/add", produces = "application/json")
    public Employee addEmployee(@RequestParam String name, @RequestParam String surname, @RequestParam int salary,
                                @RequestParam int department) {
        return employeeService.addEmployee(name, surname, salary, department);
    }

    @GetMapping("/remove")
    public Employee removeEmployee(@RequestParam String name, @RequestParam String surname) {
        return employeeService.removeEmployee(name, surname);
    }

    @GetMapping("/find")
    public Employee findEmployee(@RequestParam String name, @RequestParam String surname) {
        return employeeService.findEmployee(name, surname);
    }
    @GetMapping()
    public Collection showEmployee() {
        return employeeService.getEmployeeMap();
    }
}
