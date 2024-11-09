import org.example.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeAddTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private Employee employee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employee = new Employee("Zeyad", LocalDate.of(1990, 12, 21), "Male", LocalDate.of(2012, 6, 1), "Engineering", Set.of("Java", "Spring", "SQL"), null);
    }



    @Test
    void testAddEmployee_Success() {
        when(employeeService.addEmployee(any(Employee.class))).thenReturn(employee);

        ResponseEntity<Employee> response = employeeController.addEmployee(employee);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(employee.getFirstName(), response.getBody().getFirstName());
    }

    @Test
    void testAddEmployee_Failure() {
        when(employeeService.addEmployee(any(Employee.class))).thenThrow(new RuntimeException("Employee cannot be added"));

        try {
            employeeController.addEmployee(employee);
        } catch (Exception e) {
            assertTrue(e instanceof RuntimeException);
            assertEquals("Employee cannot be added", e.getMessage());
        }
    }

    @Test
    void testAddEmployee_InvalidData() {
        Employee invalidEmployee = new Employee(0, "", "", "");

        when(employeeService.addEmployee(any(Employee.class))).thenThrow(new IllegalArgumentException("Invalid employee data"));

        try {
            employeeController.addEmployee(invalidEmployee);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("Invalid employee data", e.getMessage());
        }
    }
}
