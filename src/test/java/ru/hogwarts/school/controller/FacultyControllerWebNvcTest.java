package ru.hogwarts.school.controller;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.student.FacultyController;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@WebMvcTest(FacultyController.class)
public class FacultyControllerWebNvcTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AvatarService avatarService;
    @MockBean
    private FacultyService facultyService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldGetFacultyById() throws Exception {
        Long facultyId = 1L;
        Faculty faculty = new Faculty("Slytherin", "Green");
        when(facultyService.get(facultyId)).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders.get("/faculties/{id}", facultyId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(faculty.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color").value(faculty.getColor()));
    }
    @Test
    void shouldUpdateStudent() throws Exception {
        Long id = 1L;
        Faculty faculty = new Faculty("S", "green");
        when(facultyService.update(id, faculty)).thenReturn(faculty);
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.put("/faculties/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(faculty)));
        perform.andExpect(MockMvcResultMatchers.jsonPath("$.name").value(faculty.getName()));
        perform.andExpect(MockMvcResultMatchers.jsonPath("$.color").value(faculty.getColor()));
    }
    @Test
    void shouldDeleteFaculty() throws Exception {
        Long facultyId = 1L;
        Mockito.doNothing().when(facultyService).delete(facultyId);
        mockMvc.perform(delete("/faculties/{id}", facultyId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(facultyService, Mockito.times(1)).delete(facultyId);
    }

}
