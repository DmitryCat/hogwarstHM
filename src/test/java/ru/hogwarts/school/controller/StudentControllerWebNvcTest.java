package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.repository.model.Student;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;
import ru.hogwarts.school.student.StudentController;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(StudentController.class)
public class StudentControllerWebNvcTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private StudentService studentService;
    @MockBean
    private AvatarService avatarService;
    @InjectMocks
    private StudentController studentController;
    @Test
    void shouldGetStudent() throws Exception {
        Long studentId = 1L;
        Student student = new Student("Dmitry", 23);
        when(studentService.get(studentId)).thenReturn(student);
        mockMvc.perform(MockMvcRequestBuilders.get("/students/{id}", studentId))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()));

    }

    @Test
    void shouldCreateStudent() throws Exception {
        Long studentId = 1L;
        Student student = new Student("Dima", 23);
        Student savedStudent = new Student("Dima", 23);
        savedStudent.setId(studentId);
        when(studentService.add(student)).thenReturn(savedStudent);
        ResultActions perform = mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)));
        perform
                .andExpect(jsonPath("$.id").value(savedStudent.getId()))
                .andExpect(jsonPath("$.name").value(savedStudent.getName()))
                .andExpect(jsonPath("$.age").value(savedStudent.getAge()));
    }
    @Test
    void shouldUpdateStudent() throws Exception {
        Long studentId = 1L;
        Student student = new Student("Dima", 23);
        when(studentService.update(studentId, student)).thenReturn(student);
        ResultActions perform = mockMvc.perform(put("/students/{id}", studentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)));
        perform
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()));
    }

}
