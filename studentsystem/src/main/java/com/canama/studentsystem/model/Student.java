package com.canama.studentsystem.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String address;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
        name = "student_course",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses = new ArrayList<>();
    

    // Constructors
    public Student() {
        this.courses = new ArrayList<>();  // 🔥 **Falls leer, keine NullPointerException**
    }

    public Student(String name, String address) {
        this.name = name;
        this.address = address;
        this.courses = new ArrayList<>();  // 🔥 **Falls leer, keine NullPointerException**
    }

    // Getter & Setter
    public int getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public List<Course> getCourses() { return courses == null ? new ArrayList<>() : courses; }
    public void setCourses(List<Course> courses) { this.courses = courses != null ? courses : new ArrayList<>(); } // ✅ **Fix: Falls null, wird neue Liste gesetzt**
}
