import org.example.model.Employee;
import org.example.repository.EmployeeRepository;  // Corrected the typo from Repositry to Repository
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeeControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employeeRepository.deleteAll();  // Cleanup before each test
        employee = new Employee("Zeyad", LocalDate.of(1990, 12, 21), "Male", LocalDate.of(2012, 6, 1), "Engineering", Set.of("Java", "Spring", "SQL"), 1);
    }

    @Test
    void testAddEmployee_Success() {
        // Make sure the database is initially empty
        assertEquals(0, employeeRepository.count());

        // Perform POST request to add employee
        ResponseEntity<Employee> response = restTemplate.postForEntity("/employees", employee, Employee.class);

        // Validate response status and body
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(employee.getName(), response.getBody().getName());

        // Verify that the employee was added to the database
        assertTrue(employeeRepository.count() > 0);
    }

    @Test
    void testAddEmployee_Failure() {
        // Simulate an invalid employee (empty name)
        Employee invalidEmployee = new Employee("", LocalDate.of(1990, 12, 21), "Male", LocalDate.of(2012, 6, 1), "Engineering", Set.of("Java", "Spring", "SQL"), 1);

        // Perform POST request and expect a validation failure
        ResponseEntity<String> response = restTemplate.postForEntity("/employees", invalidEmployee, String.class);

        // Assert that the response status is BAD_REQUEST and contains the validation error message
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("must not be empty"));
    }

    @Test
    void testGetAllEmployees_Success() {
        // Add an employee to the repository
        employeeRepository.save(employee);

        // Perform GET request to retrieve all employees
        ResponseEntity<Employee[]> response = restTemplate.getForEntity("/employees", Employee[].class);

        // Assert that the response contains the employee
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    void testGetEmployeeById_Success() {
        // Save employee to the repository
        Employee savedEmployee = employeeRepository.save(employee);

        // Perform GET request to retrieve the employee by ID
        ResponseEntity<Employee> response = restTemplate.getForEntity("/employees/" + savedEmployee.getId(), Employee.class);

        // Assert the employee is returned correctly
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(savedEmployee.getName(), response.getBody().getName());
    }

    @Test
    void testGetEmployeeById_NotFound() {
        // Perform GET request for a non-existing employee
        ResponseEntity<Employee> response = restTemplate.getForEntity("/employees/999", Employee.class);

        // Assert that the response is 404 NOT FOUND
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
