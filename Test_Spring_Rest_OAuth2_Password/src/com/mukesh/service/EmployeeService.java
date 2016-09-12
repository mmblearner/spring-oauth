package com.mukesh.service;

/**
 * Created with IntelliJ IDEA.
 * User: vivianvanzyl
 * Date: 6/16/14
 * Time: 6:02 PM
 */

import com.psl.model.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Controller
@RequestMapping("/employee")
public class EmployeeService {
    static Set<Employee> Employees;

    static {
        Employees = new HashSet<Employee>();
        Employee foobar = null;
        for (int i = 0; i < 10; i++) {
            double sal = new SecureRandom().nextInt(400)*500;
            foobar = new Employee(i, "Employee " + i, sal );
            Employees.add(foobar);
        }
        System.out.println("inside employee");
    }

    @RequestMapping(value = "/{employeeId}", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public Employee getFoobar(@PathVariable int employeeId) {
        Iterator<Employee> X = Employees.iterator();
        while (X.hasNext()) {
            Employee f = (Employee) X.next();
            if (f.getId() == employeeId) return f;
        }
        return null;
    }

    @RequestMapping(value = "/htmllist", method = RequestMethod.GET, headers = "Accept=text/html", produces = {"text/html"})
    @ResponseBody
    public String getFoobarListHTML() {
    	System.out.println("htmllist");
        String retVal = "<html><body><table border=1>";
        Iterator<Employee> X = Employees.iterator();
        while (X.hasNext()) {
            Employee f = (Employee) X.next();
            retVal += "<tr><td>" + f.getId() + "</td><td>" + f.getName() + "</td><td>" + f.getSalary() + "</td></tr>";
        }
        retVal += "</table></body></html>";

        return retVal;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public Set<Employee> getFoobarList() {
        return Employees;
    }

    @RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public Set<Employee> getFoobars() {
        return Employees;
    }

    @RequestMapping(value = "/{employeeId}", method = RequestMethod.PUT, headers = "Accept=application/json", produces = {"application/json"}, consumes = {"application/json"})
    @ResponseBody
    public Employee editFoobar(@RequestBody Employee foobar, @PathVariable int employeeId) {
        Iterator<Employee> X = Employees.iterator();
        while (X.hasNext()) {
            Employee f = (Employee) X.next();
            if (employeeId == f.getId()) {
                f.setId(foobar.getId());
                f.setName(foobar.getName());
                return f;
            }
        }
        return null;
    }

    @RequestMapping(value = "/{employeeId}", method = RequestMethod.DELETE, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public boolean deleteFoobar(@PathVariable int employeeId) {
        System.out.println("Delete call.");
        Iterator<Employee> fooIterator = Employees.iterator();
        while (fooIterator.hasNext()) {
            Employee foobar = (Employee) fooIterator.next();
            System.out.println(foobar);
            if (foobar.getId() == employeeId) {
                fooIterator.remove();
                return true;
            }
        }
        return false;
    }

    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"}, consumes = {"application/json"})
    @ResponseBody
    public boolean createFoobar(@RequestBody Employee employee) {
        return Employees.add(employee);
    }
}
