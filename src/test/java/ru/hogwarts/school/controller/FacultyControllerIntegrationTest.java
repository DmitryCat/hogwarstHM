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
import ru.hogwarts.school.repository.FacultyRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class FacultyControllerIntegrationTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private FacultyRepository facultyRepository;

    @BeforeEach
    private void clearDatabase() {
        facultyRepository.deleteAll();
    }

    @Test
    void shouldCreateFaculty() {
        Faculty faculty = new Faculty("Slytherin", "Green");

        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.postForEntity
                ("http://localhost:" + port + "/faculties", faculty, Faculty.class);

        Assertions.assertNotNull(facultyResponseEntity);
        assertEquals(facultyResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));

        Faculty actualFaculty = facultyResponseEntity.getBody();
        Assertions.assertNotNull(actualFaculty.getId());
        Assertions.assertEquals(actualFaculty.getColor(), faculty.getColor());
        Assertions.assertEquals(actualFaculty.getName(), faculty.getName());

    }

    @Test
    void shouldUpdateFaculty() {
        Faculty faculty = new Faculty("Dima", "green");
        facultyRepository.save(faculty);
        Faculty facultyForUpdate = new Faculty("Demetrius", "green");
        HttpEntity<Faculty> entity = new HttpEntity<Faculty>(facultyForUpdate);
        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.exchange
                ("http://localhost:" + port + "/faculties/" + faculty.getId(), HttpMethod.PUT, entity
                , Faculty.class);
        Assertions.assertNotNull(facultyResponseEntity);
        assertEquals(facultyResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));

        Faculty actualFaculty = facultyResponseEntity.getBody();
        Assertions.assertEquals(actualFaculty.getId(), faculty.getId());
        Assertions.assertEquals(actualFaculty.getName(), facultyForUpdate.getName());
        Assertions.assertEquals(actualFaculty.getColor(), facultyForUpdate.getColor());

    }

    @Test
    void shouldGetFaculty() {
        Faculty faculty = new Faculty("Dima", "green");
        facultyRepository.save(faculty);

        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.getForEntity
                ("http://localhost:" + port + "/faculties/" + faculty.getId(), Faculty.class);
        Assertions.assertNotNull(facultyResponseEntity);
        assertEquals(facultyResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));

        Faculty actualFaculty = facultyResponseEntity.getBody();
        Assertions.assertEquals(actualFaculty.getId(), faculty.getId());
        Assertions.assertEquals(actualFaculty.getName(), faculty.getName());
        Assertions.assertEquals(actualFaculty.getColor(), faculty.getColor());

    }
    @Test
    public void shouldDeleteFaculty() {
        Faculty faculty = new Faculty("Dima", "green");
        facultyRepository.save(faculty);

        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.exchange
                ("http://localhost:" + port + "/faculties/" + faculty.getId(),HttpMethod.DELETE, null, Faculty.class);
        Assertions.assertNotNull(facultyResponseEntity);
        assertEquals(facultyResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));
        org.assertj.core.api.Assertions.assertThat(facultyRepository.findById(faculty.getId())).isNotPresent();

    }
}
