package SYSC6.Project.employee;

import SYSC6.Project.sorting.Page;
import SYSC6.Project.sorting.PageArray;
import SYSC6.Project.sorting.PagingRequest;
import SYSC6.Project.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeRestController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public Page<Employee> list(@RequestBody PagingRequest pagingRequest) {
        return employeeService.getEmployees(pagingRequest);
    }

    @PostMapping("/array")
    public PageArray array(@RequestBody PagingRequest pagingRequest) {
        return employeeService.getEmployeesArray(pagingRequest);
    }

    @PostMapping("/users")
    public Page<UserPOJO> listUsers(@RequestBody PagingRequest pagingRequest) {
        return employeeService.getUsers(pagingRequest);
    }

}
