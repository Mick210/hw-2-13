package skypro.hw2_7.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import skypro.hw2_7.exceptions.EmployeeAlreadyAddedException;
import skypro.hw2_7.exceptions.EmployeeNotFoundException;
import skypro.hw2_7.exceptions.MaximumEmployeesException;
import skypro.hw2_7.exceptions.NotValidCharacterException;
import skypro.hw2_7.sevice.Employee;
import skypro.hw2_7.sevice.EmployeeServiceImpl;

import java.util.Collection;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeServiceImplTest {
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    void setEmployeeService() {
        employeeService = new EmployeeServiceImpl();
    }

    static Stream<Arguments> arguments() {
        return Stream.of(Arguments.of("Klava", "Koka", 1000, 1),
                Arguments.of("John", "Doe", 2000, 2),
                Arguments.of("Jane", "Smith", 1500, 1));
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void testAddEmployee(String name, String surname, int salary, int department) {
        Employee addedEmployee = employeeService.addEmployee(name, surname, salary, department);
        assertNotNull(addedEmployee);
        assertEquals(name, addedEmployee.getName());
        assertEquals(surname, addedEmployee.getSurname());
        assertEquals(salary, addedEmployee.getSalary());
        assertEquals(department, addedEmployee.getDepartment());

        Collection<Employee> collections = employeeService.getEmployeeMap();
        assertTrue(collections.contains(addedEmployee));
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void testAddDuplicateEmployee(String name, String surname, int salary, int department) {

        Employee addedEmployee = employeeService.addEmployee(name, surname, salary, department);
        assertThrows(EmployeeAlreadyAddedException.class, () -> employeeService.addEmployee(name, surname, salary, department));
    }

    @Test
    void testAddMaxEmployees() {
        for (int i = 0; i < EmployeeServiceImpl.MAX_EMPLOYEES; i++) {
            String name = "Klava" + ((char) (97 + i));
            String surname = "Koka" + ((char) (97 + i));
            int salary = 1000 + i;
            int department = 1 + i;
            employeeService.addEmployee(name, surname, salary, department);
        }
        assertThrows(MaximumEmployeesException.class, () ->
                employeeService.addEmployee("Fiodor", "Koniuhov", 2, 1));
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void testRemoveEmployee(String name, String surname, int salary, int department) {

        Employee addedEmployee = employeeService.addEmployee(name, surname, salary, department);
        Employee deletedEmployee = employeeService.removeEmployee(name, surname);

        assertNotNull(deletedEmployee);
        assertEquals(name, deletedEmployee.getName());
        assertEquals(surname, deletedEmployee.getSurname());

        Collection<Employee> employeeCollection = employeeService.getEmployeeMap();
        assertFalse(employeeCollection.contains(addedEmployee));
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.findEmployee(name, surname));
        assertThrows(EmployeeNotFoundException.class, () ->
                employeeService.removeEmployee("James", "Hatfield"));
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void testFindEmployee(String name, String surname, int salary, int department) {

        Employee addedEmployee = employeeService.addEmployee(name, surname, salary, department);
        Employee foundedEmployee = employeeService.findEmployee(name, surname);

        assertNotNull(foundedEmployee);
        assertEquals(name, foundedEmployee.getName());
        assertEquals(surname, foundedEmployee.getSurname());

        Collection<Employee> employeeCollection = employeeService.getEmployeeMap();
        assertTrue(employeeCollection.contains(addedEmployee));

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.findEmployee("Frodo", "Baggins"));
    }

    @Test
    void inputValidationTest() {

        String invalidName = "4otkii$%^";
        String invalidSurname = "Ne ochen";

        assertThrows(NotValidCharacterException.class, () ->
                employeeService.findEmployee(invalidName, invalidSurname));
    }
}

