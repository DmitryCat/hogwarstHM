package ru.hogwarts.school.student;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.repository.model.Faculty;
import ru.hogwarts.school.repository.model.Student;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("students")
public class StudentController {
    private final StudentService studentService;
    private final AvatarService avatarService;

    public StudentController(StudentService studentService, AvatarService avatarService) {
        this.studentService = studentService;
        this.avatarService = avatarService;
    }

    @GetMapping("{id}")
    public Student get(@PathVariable Long id) {
        return studentService.get(id);
    }

    @PostMapping
    public Student add(@RequestBody Student student) {
        return studentService.add(student);
    }

    @PutMapping("{id}")
    public Student update(@PathVariable Long id, @RequestBody Student student) {
        return studentService.update(id, student);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> delete(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<Student> getByAge(@RequestParam int age) {
        return studentService.getByAge(age);
    }

    @GetMapping("getByAgeBetween")
    public List<Student> getByAgeBetween(
            @RequestParam
            int ageFrom,
            @RequestParam
            int ageTo
    ) {
        return studentService.getByAgeBetween(ageFrom, ageTo);

    }

    @GetMapping("{id}/getFaculty")
    public Faculty getFaculty(@PathVariable Long id) {
        return studentService.getFaculty(id);
    }

    @PostMapping(value = "/{studentId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(
            @PathVariable Long studentId,
            @RequestParam MultipartFile avatar) throws IOException {
        avatarService.uploadAvatar(studentId, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping("getStudentCount")
    public int getStudentCount() {
        return studentService.getStudentCount();
    }

    @GetMapping("getAverageAge")
    public int getAverageAge() {
        return studentService.getAverageAge();
    }

    @GetMapping("getLastFive")
    public List<Student> getLastFive() {
        return studentService.getLastFive();
    }

    @GetMapping("beginWithA")
    public List<String> getBeginsWithA() {
        return studentService.getBeginsWithA();
    }

    @GetMapping("averageAge")
    public Double getsAverageAge() {
        return studentService.getsAverageAge();
    }
}
