package api.oracle.poc.api.oracle.poc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;

import api.oracle.poc.api.oracle.entities.User;

@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/users")
    public void getUsers(){
        String sql = "SELECT * FROM api_user";
        List<User> users = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(User.class));
        users.forEach(e -> System.out.println(e.getEmail()));
    }

    @PostMapping("/signup")
    public String postUser(@RequestBody User requestUser){
        System.out.println(requestUser);
        String sql = "INSERT INTO api_user (email, password) VALUES (?, ?)";
        int dbQuery = jdbcTemplate.update(sql,requestUser.getEmail(),requestUser.getPassword());
        if(dbQuery == 1){
            return "New user created successfuly!";
        }else{
            return "Something went wrong.";
        }
    }
}