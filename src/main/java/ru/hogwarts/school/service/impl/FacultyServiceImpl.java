package ru.hogwarts.school.service.impl;

import jakarta.annotation.PostConstruct;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.HashMap;
import java.util.Map;

public class FacultyServiceImpl implements FacultyService {
    private final Map<Long, Faculty> faculties = new HashMap<>();
    private static long currentId = 1;

    @PostConstruct
    public void initFaculties() {
        add(new Faculty("Slytherin", "green"));
        add(new Faculty("Gryffindor", "red"));
    }

    @Override
    public Faculty add(Faculty faculty) {
        faculty.setId(currentId);
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    @Override
    public Faculty get(Long id) {
        return faculties.get(id);
    }

    @Override
    public Faculty update(Long id, Faculty faculty) {
        Faculty facultyStorage = faculties.get(id);
        facultyStorage.setColor(faculty.getColor());
        facultyStorage.setName(faculty.getName());
        return facultyStorage;
    }

    @Override
    public Faculty delete(Long id) {
        return faculties.remove(id);
    }
}
