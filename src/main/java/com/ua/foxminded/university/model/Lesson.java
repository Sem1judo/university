package com.ua.foxminded.university.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

public class Lesson {

    private long lessonId;

    @NotBlank
    @Size(min = 3, max = 50,
            message = "Lesson name must be between 3 and 20 characters long")
    @Pattern(regexp = "^[a-zA-Z0-9]+$",
            message = "Lesson name must be alphanumeric with no spaces")
    private String name;

    private long lectorId;

    public Lesson() {
    }

    public Lesson(String name, long lectorId) {
        this.name = name;
        this.lectorId = lectorId;
    }

    public Lesson(long lessonId, String name, long lectorId) {
        this.lessonId = lessonId;
        this.name = name;
        this.lectorId = lectorId;
    }

    public long getLessonId() {
        return lessonId;
    }

    public void setLessonId(long lessonId) {
        this.lessonId = lessonId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLectorId() {
        return lectorId;
    }

    public void setLectorId(long lectorId) {
        this.lectorId = lectorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return lessonId == lesson.lessonId &&
                Objects.equals(name, lesson.name) &&
                Objects.equals(lectorId, lesson.lectorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lessonId, name, lectorId);
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "lesson_id=" + lessonId +
                ", name='" + name + '\'' +
                ", lector=" + lectorId +
                '}';
    }
}
