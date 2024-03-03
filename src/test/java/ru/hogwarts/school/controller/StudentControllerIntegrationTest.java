package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import ru.hogwarts.school.model.Faculty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.io.IOException;
import java.util.List;


@SpringBootTest(webEnvironment = RANDOM_PORT)
public class StudentControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    private void clearDatabase() {
        studentRepository.deleteAll();
    }

    @Test
    void shouldCreateStudent() {
        Student student = new Student("John Doe", 55);

        ResponseEntity<Student> studentResponseEntity = restTemplate.postForEntity
                ("http://localhost:" + port + "/students", student, Student.class);

        Assertions.assertNotNull(studentResponseEntity);
        assertEquals(studentResponseEntity.getStatusCode(), HttpStatus.OK);

        Student actualStudent = studentResponseEntity.getBody();
        Assertions.assertNotNull(actualStudent.getId());
        Assertions.assertEquals(actualStudent.getName(), student.getName());
        Assertions.assertEquals(actualStudent.getAge(), student.getAge());
    }

    @Test
    void shouldUpdateStudent() {
        Student student = new Student("Dima", 55);
        studentRepository.save(student);

        Student studentForUpdate = new Student("Dima", 55);
        HttpEntity<Student> entity = new HttpEntity<>(studentForUpdate);
        ResponseEntity<Student> studentResponseEntity = restTemplate.exchange
                ("http://localhost:" + port + "/students/" + student.getId(), HttpMethod.PUT, entity, Student.class);

        Assertions.assertNotNull(studentResponseEntity);
        assertEquals(studentResponseEntity.getStatusCode(), HttpStatus.OK);

        Student actualStudent = studentResponseEntity.getBody();
        Assertions.assertEquals(actualStudent.getId(), student.getId());
        Assertions.assertEquals(actualStudent.getName(), studentForUpdate.getName());
        Assertions.assertEquals(actualStudent.getAge(), studentForUpdate.getAge());
    }

    @Test
    void shouldGetStudent() {
        Student student = new Student("Dima", 55);
        studentRepository.save(student);

        ResponseEntity<Student> studentResponseEntity = restTemplate.getForEntity
                ("http://localhost:" + port + "/students/" + student.getId(), Student.class);

        Assertions.assertNotNull(studentResponseEntity);
        assertEquals(studentResponseEntity.getStatusCode(), HttpStatus.OK);

        Student actualStudent = studentResponseEntity.getBody();
        Assertions.assertEquals(actualStudent.getId(), student.getId());
        Assertions.assertEquals(actualStudent.getName(), student.getName());
        Assertions.assertEquals(actualStudent.getAge(), student.getAge());
    }

    @Test
    public void shouldDeleteStudent() {
        Student student = new Student("Dima", 55);
        studentRepository.save(student);

        restTemplate.delete("http://localhost:" + port + "/students/" + student.getId());

        assertFalse(studentRepository.existsById(student.getId()));
    }
}
