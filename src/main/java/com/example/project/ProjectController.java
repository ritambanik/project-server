package com.example.project;

import com.example.project.data.Project;
import com.example.project.data.ProjectForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController("/projects")
public class ProjectController {

    @Autowired
    private ProjectRepository repo;

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody ProjectForm projectForm) {
        Project project = repo.create(projectForm);
        return new ResponseEntity<>(project, HttpStatus.CREATED);
    }

    @GetMapping("/projects/{id}")
    public ResponseEntity<Project> getProject(@PathVariable("id") int id) {
        Optional<Project> result = repo.findById(id);
        if (result.isPresent()) {
            return new ResponseEntity<>(result.get(), HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/projects/{id}")
    public ResponseEntity<Void> updateProjectStatus(@PathVariable("id") int id, @RequestParam("status") boolean status) {
        repo.update(id, status);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
