package ru.hogwarts.school.student;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.repository.model.Faculty;
import ru.hogwarts.school.repository.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping("faculties")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    public Faculty get(@PathVariable Long id) {
        return facultyService.get(id);
    }

    @PostMapping
    public Faculty add(@RequestBody Faculty faculty) {
        return facultyService.add(faculty);
    }

    @PutMapping("{id}")
    public Faculty update(@PathVariable Long id, @RequestBody Faculty faculty) {
        return facultyService.update(id, faculty);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        facultyService.delete(id);
    }

    @GetMapping
    public List<Faculty> getByColor(@RequestParam String color) {
        return facultyService.getByColor(color);
    }

    @GetMapping("getByNameOrColor")
    public List<Faculty> getByNameOrColor(
            @RequestParam String name,
            @RequestParam String color
    ) {
        return facultyService.getByNameOrColor(name, color);
    }

    @GetMapping("{id}/students")
    public List<Student> getStudents(@PathVariable Long id) {
        return facultyService.getStudents(id);
    }
    @GetMapping("toLong")
    public String getLongest() {
        return facultyService.getLongest();
    }
}
