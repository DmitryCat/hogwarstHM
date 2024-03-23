package ru.hogwarts.school.service;

import org.springframework.web.bind.annotation.GetMapping;
import ru.hogwarts.school.repository.model.Faculty;
import ru.hogwarts.school.repository.model.Student;

import java.util.List;

public interface StudentService {
    Student add(Student student);

    Student get(Long id);

    Student update(Long id, Student student);

    void delete(Long id);

    List<Student> getByAge(int age);

    List<Student> getByAgeBetween(int ageFrom, int ageTo);

    Faculty getFaculty(Long id);

    int getStudentCount();

    int getAverageAge();

    List<Student> getLastFive();

    List<String> getBeginsWithA();

    Double getsAverageAge();

    void printParallel();

    void printSynchronized();
    }
