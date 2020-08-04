package com.ua.foxminded.university.dao;

import com.ua.foxminded.university.model.Group;

public interface GroupEntity extends DaoEntity<Group> {
     int getLessonsById(Long id);
}
