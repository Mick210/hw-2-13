package skypro.hw2_7.sevice;

import skypro.hw2_7.exceptions.NotValidCharacterException;

import java.util.Collection;

public interface EmployeeService {
    public Employee addEmployee(String name, String surname, int salary, int department);

    public Employee removeEmployee(String name, String surname);

    public Employee findEmployee(String name, String surname);

    public Collection<Employee> getEmployeeMap();
}
