package com.ua.foxminded.task_13.dao;

import com.ua.foxminded.task_13.model.Group;

public interface GroupEntity extends DaoEntity<Group> {
     int getLessonsById(Long id);
}
