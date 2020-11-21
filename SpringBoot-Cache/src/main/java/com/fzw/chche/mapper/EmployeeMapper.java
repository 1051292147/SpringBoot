package com.fzw.chche.mapper;

import com.fzw.chche.entity.Employee;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @Author Fzw
 * @Date 11:45 2020/7/1
 */

@MapperScan
public interface EmployeeMapper {

    @Select("select * from employee")
    public List<Employee> findAll();

    @Select("select * from employee where id=#{id}")
    public Employee findById(int id);

    @Insert("insert into employee values(default,#{lastName},#{email},#{gender},#{dId})")
    public void insert(Employee employee);

    @Delete("delete from employee where id=#{id}")
    public void delete(int id);

    @Update("update employee set lastName=#{lastName},email=#{email},gender=#{gender},d_id=#{dId} where id=#{id}")
    public void update(Employee employee);

    @Select("select * from employee where lastName=#{lastName}")
    public Employee findByName(String lastName);

}
