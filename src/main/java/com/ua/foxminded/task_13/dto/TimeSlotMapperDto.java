package com.ua.foxminded.task_13.dto;


import com.ua.foxminded.task_13.model.TimeSlot;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TimeSlotMapperDto {

    @Autowired
    private ModelMapper mapper;

    public TimeSlot toEntity(TimeSlotDto dto) {
        return Objects.isNull(dto) ? null : mapper.map(dto, TimeSlot.class);
    }

    public TimeSlotDto toDto(TimeSlot entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, TimeSlotDto.class);
    }

}
