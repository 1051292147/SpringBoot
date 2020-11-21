package com.fzw.chche.controller;

import com.fzw.chche.entity.Employee;
import com.fzw.chche.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author Fzw
 * @Date 11:49 2020/7/1
 */

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("find/{id}")
    public Employee findById(@PathVariable int id){
        Employee employee = employeeService.findById(id);
        return employee;
    }

    @RequestMapping("update")
    public void update(Employee employee){
        Employee update = employeeService.update(employee);
        System.out.println("已更新");

    }

    @RequestMapping("delete")
    public void delete(int id){
        employeeService.delete(id);
    }

    @RequestMapping("insert")
    public void insert(Employee employee){
        employeeService.insert(employee);
    }


    @RequestMapping("find/lastname/{lastname}")
    public Employee findByName(@PathVariable String lastname){
        Employee byName = employeeService.findByName(lastname);
        return byName;
    }

    @RequestMapping("findAll")
    public List<Employee> findAll(){
        List<Employee> all = employeeService.findAll();
        return all;
    }
}
