package com.canama.studentsystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String name;
    private String description;

    @ManyToMany(mappedBy = "courses")
    @JsonBackReference // 🔥 Verhindert Endlosschleife
    private List<Student> students = new ArrayList<>(); // ✅ Initialisierte Liste

    public Course() {}

    public Course(String name, String description) {
        this.name = name;
        this.description = description;
        this.students = new ArrayList<>();
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public List<Student> getStudents() { return students; }
    public void setStudents(List<Student> students) { this.students = students; }
}
