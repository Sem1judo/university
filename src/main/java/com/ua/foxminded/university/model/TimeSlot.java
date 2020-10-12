package com.ua.foxminded.university.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

public class TimeSlot {

    private long timeSlotId;

    @NotNull
    @Future
    @DateTimeFormat(pattern = "yyyy-MM-dd, HH:mm")
    private LocalDateTime startLesson;

    @NotNull
    @Future
    @DateTimeFormat(pattern = "yyyy-MM-dd, HH:mm")
    private LocalDateTime endLesson;

    private long groupId;
    private long lessonId;

    public TimeSlot() {
    }

    public TimeSlot(LocalDateTime startLesson, LocalDateTime endLesson) {
        this.startLesson = startLesson;
        this.endLesson = endLesson;
    }

    public TimeSlot(@NotNull @Future LocalDateTime startLesson, @NotNull @Future LocalDateTime endLesson, long groupId, long lessonId) {
        this.startLesson = startLesson;
        this.endLesson = endLesson;
        this.groupId = groupId;
        this.lessonId = lessonId;
    }

    public TimeSlot(long timeSlotId, @NotNull @Future LocalDateTime startLesson, @NotNull @Future LocalDateTime endLesson, long groupId, long lessonId) {
        this.timeSlotId = timeSlotId;
        this.startLesson = startLesson;
        this.endLesson = endLesson;
        this.groupId = groupId;
        this.lessonId = lessonId;
    }

    public long getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(long timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    public LocalDateTime getStartLesson() {
        return startLesson;
    }

    public void setStartLesson(LocalDateTime startLesson) {
        this.startLesson = startLesson;
    }

    public LocalDateTime getEndLesson() {
        return endLesson;
    }

    public void setEndLesson(LocalDateTime endLesson) {
        this.endLesson = endLesson;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public long getLessonId() {
        return lessonId;
    }

    public void setLessonId(long lessonId) {
        this.lessonId = lessonId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeSlot timeSlot = (TimeSlot) o;
        return timeSlotId == timeSlot.timeSlotId &&
                Objects.equals(startLesson, timeSlot.startLesson) &&
                Objects.equals(endLesson, timeSlot.endLesson) &&
                Objects.equals(groupId, timeSlot.groupId) &&
                Objects.equals(lessonId, timeSlot.lessonId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeSlotId, startLesson, endLesson, groupId, lessonId);
    }

    @Override
    public String toString() {
        return "TimeSlot{" +
                "timeSlotId=" + timeSlotId +
                ", startLesson=" + startLesson +
                ", endLesson=" + endLesson +
                ", groupId=" + groupId +
                ", lessonId=" + lessonId +
                '}';
    }
}
