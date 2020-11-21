package com.fzw.chche;

import com.fzw.chche.entity.Employee;
import com.fzw.chche.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.TestExecutionListeners;

@SpringBootTest
class FzwApplicationTests {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate empRedisTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    public void testInsert(){
        stringRedisTemplate.opsForValue().set("a", "hello");
        stringRedisTemplate.opsForValue().append("a", "redis");
    }

    @Test
    public void testAddEmp(){
        Employee byId = employeeService.findById(5);

        redisTemplate.opsForValue().set("emp", byId);
    }

    @Test
    public void testAdd(){
        Employee byId = employeeService.findById(5);

        empRedisTemplate.opsForValue().set("emp", byId);
    }
}
