package com.turanbalayev;

import com.turanbalayev.dao.DAO;
import com.turanbalayev.model.Course;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class UltimateJdbcTemplateApplication {

    private static DAO<Course> dao;

    public UltimateJdbcTemplateApplication(DAO<Course> dao) {
        this.dao = dao;
    }

    public static void main(String[] args) {
        SpringApplication.run(UltimateJdbcTemplateApplication.class, args);

        System.out.println("\n-------------------- Newly Created Courses ------------------------\n");

        Course springVue  = new Course("Spring Boot + Vue Js","New Course","www.springvue.com");
        dao.create(springVue);

        System.out.println("\n-------------------- One Course ------------------------\n");
        Optional<Course> firstOne = dao.get(10);
        firstOne.ifPresent(System.out::println);

        System.out.println("\n-------------------- Updated 11th Course ------------------------\n");
        dao.update(new Course("Updated Eleventh course","Updated course description","updatedlink"),3);


        System.out.println("\n-------------------- Deleted 9th Course ------------------------\n");
        dao.delete(9);

        System.out.println("\n-------------------- All Courses ------------------------\n");
        List<Course> courses = dao.list();

        courses.forEach(System.out::println);
    }

}
