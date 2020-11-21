package com.fzw.chche.service;

import com.fzw.chche.config.RedisUtil;
import com.fzw.chche.entity.Employee;
import com.fzw.chche.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author Fzw
 * @Date 11:48 2020/7/1
 */
@Service
@CacheConfig(cacheNames = "emp")
public class EmployeeService {

    Employee employee;
    List<Employee> all;
    //配置过期时间
//    private Duration timeToLive=Duration.ofMillis(20);

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private RedisUtil redisUtil;

    //  key = "#root.method.name+'['+#id+']'"
    //,unless = "#a0==5"
    //,keyGenerator = "keyGenerator",condition = "#a0>3",sync = true

    @Cacheable(/*cacheNames = "emp",*/key = "#a0")
    public Employee findById(@PathVariable int id){
        System.out.println("编号为:"+id);
        Employee employee = employeeMapper.findById(id);
        return employee;
    }


    public void insert(Employee employee){
        employeeMapper.insert(employee);
        System.out.println("插入一名员工");
    }

    //allEntries把所有缓存删除
    //beforeInvocation = true 方法执行之前清空缓存
    @CacheEvict(/*cacheNames = "emp",*/beforeInvocation = false,allEntries = true)
    public void delete(int id){
        System.out.println("删除成功");
        int x=10/0;
        //employeeMapper.delete(id);
    }

    @CachePut(/*cacheNames = "emp",*/key = "#result.id")
    public Employee update(Employee employee){
       employeeMapper.update(employee);
       return employee;
    }


  /*  @Caching(
            cacheable = {
                    @Cacheable(*//*cacheNames = "emp",*//*key="#name")
            },
            put = {
                    @CachePut(*//*cacheNames = "emp",*//*key = "#result.id"),
                    @CachePut(*//*cacheNames = "emp",*//*key = "#result.email")
            }
    )
    public Employee findByName(String name){
        Employee byName = employeeMapper.findByName(name);
        return byName;
    }*/


  public Employee findByName(String name){
      String key = "user"+name;
      if(redisUtil.hasKey(key)) {
          employee= (Employee)redisUtil.get(key);
          System.out.println("查询的是缓存");
      }else{
          employee = employeeMapper.findByName(name);
          System.out.println("查询的是数据库");
          System.out.println(redisUtil.set(key,employee) ? "插入成功" : "插入失败");
      }
      return employee;
     /* Employee byName = employeeMapper.findByName(name);
      return byName;*/
  }

    public List<Employee> findAll(){

        String key="AllEmployee";
        if (redisUtil.hasKey(key)){
            System.out.println("查询的是缓存");
//             List<Employee> employees = (List<Employee>)redisTemplate.opsForList().leftPop(key);
            all = redisTemplate.opsForList().range(key, 0, -1);
//            List<Employee> employees = redisUtil.lGet(key, 0, -1);

            for (int i = 0; i < all.size(); i++) {
                System.out.println(all.get(i));
            }

        }else {
            System.out.println("查询的是数据库");
           all = employeeMapper.findAll();
//           redisTemplate.opsForValue().set("k2", "wwwww");
//            redisTemplate.expire(key,timeToLive);
            redisTemplate.opsForList().leftPush(key, this.all);
            redisTemplate.expire(key, 10, TimeUnit.SECONDS);

        }

        return all;
    }


}
