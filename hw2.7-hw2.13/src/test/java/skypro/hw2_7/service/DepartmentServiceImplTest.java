package skypro.hw2_7.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import skypro.hw2_7.sevice.DepartmentServiceImpl;
import skypro.hw2_7.sevice.Employee;
import skypro.hw2_7.sevice.EmployeeService;
import skypro.hw2_7.sevice.EmployeeServiceImpl;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceImplTest {
    private final EmployeeService employeeServiceMock = mock(EmployeeService.class);
    private DepartmentServiceImpl out;

    @BeforeEach
    void initOut() {
        out = new DepartmentServiceImpl(employeeServiceMock);
    }

    static Stream<Arguments> createEmployees() {
        Employee employee1 = new Employee("Ivan", "Dorn", 200, 1);
        Employee employee2 = new Employee("Ivan", "Groznii", 300, 1);
        Employee employee3 = new Employee("Ivan", "Urgant", 400, 2);
        Employee employee4 = new Employee("Ivan", "Durak", 500, 3);
        return Stream.of(Arguments.of(employee1, employee2, employee3, employee4));
    }

    @ParameterizedTest
    @MethodSource("createEmployees")
    void willReturnEmployeeWithMinSalaryByDepartment(Employee employee1, Employee employee2, Employee employee3, Employee employee4) {
        List<Employee> employeeList = List.of(employee1, employee2, employee3, employee4);

        when(employeeServiceMock.getEmployeeMap()).thenReturn(employeeList);

        Employee result = out.findMinSalaryByDepartment(1);

        assertNotNull(result);

        assertEquals(200, result.getSalary());

        Employee result1 = out.findMinSalaryByDepartment(2);

        assertNotNull(result1);

        assertEquals(400, result1.getSalary());
        verify(employeeServiceMock, atLeast(1)).getEmployeeMap();//экспериментально проверка вызова метода мока(минимум 1 раз)
    }

    @Test
    void testFindMinSalaryByDepartmentNoEmployee() {
        List<Employee> employeeList = new ArrayList<>();

        when(employeeServiceMock.getEmployeeMap()).thenReturn(employeeList);

        assertThrows(NoSuchElementException.class, () -> out.findMinSalaryByDepartment(1));
    }

    @ParameterizedTest
    @MethodSource("createEmployees")
    void willReturnEmployeeWithMaxSalaryByDepartment(Employee employee1, Employee employee2, Employee employee3, Employee employee4) {
        List<Employee> employeeList = List.of(employee1, employee2, employee3, employee4);

        when(employeeServiceMock.getEmployeeMap()).thenReturn(employeeList);

        Employee result = out.findMaxSalaryByDepartment(1);

        assertNotNull(result);

        assertEquals(300, result.getSalary());
    }

    @Test
    void testFindMaxSalaryByDepartmentNoEmployee() {
        List<Employee> employeeList = new ArrayList<>();

        when(employeeServiceMock.getEmployeeMap()).thenReturn(employeeList);

        assertThrows(NoSuchElementException.class, () -> out.findMaxSalaryByDepartment(1));
    }

    @ParameterizedTest
    @MethodSource("createEmployees")
    void testFindSumSalaryByDepartment(Employee employee1, Employee employee2, Employee employee3, Employee employee4) {
        List<Employee> employeeList = List.of(employee1, employee2, employee3, employee4);

        when(employeeServiceMock.getEmployeeMap()).thenReturn(employeeList);

        int result = out.getTotalSalaryCostByDepartment(1);

        assertEquals(500, result);
    }

    @ParameterizedTest
    @MethodSource("createEmployees")
    void testGetAllEmployeesByDepartment(Employee employee1, Employee employee2, Employee employee3, Employee employee4) {
        List<Employee> employeeList = List.of(employee1, employee2);
        int department = 1;

        when(employeeServiceMock.getEmployeeMap()).thenReturn(employeeList);

        Map<Integer, List<Employee>> resultEmployeeList = out.getAllEmployeesByDepartment(1);

        assertEquals(1, resultEmployeeList.size());//проверка что у нас только один отдел
        assertEquals(employeeList, resultEmployeeList.get(department));//проверка, что список сотрудников совпадает с ожидаемым
    }

    @ParameterizedTest
    @MethodSource("createEmployees")
    void testGetAllEmployees(Employee employee1, Employee employee2, Employee employee3, Employee employee4) {

        when(employeeServiceMock.getEmployeeMap()).thenReturn(Arrays.asList(employee1, employee2, employee3, employee4));

        List<Employee> expectedList1 = Arrays.asList(employee1, employee2);
        List<Employee> expectedList2 = Arrays.asList(employee3);
        List<Employee> expectedList3 = Arrays.asList(employee4);


        Map<Integer, List<Employee>> resultList = out.getAllEmployees();
        Map<Integer, List<Employee>> expectedResultList = Map.of(1, expectedList1, 2, expectedList2, 3, expectedList3);

        assertEquals(3, expectedResultList.size());
        assertEquals(expectedResultList, resultList);
    }
}
