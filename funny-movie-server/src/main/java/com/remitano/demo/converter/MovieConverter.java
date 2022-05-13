package com.remitano.demo.converter;

import com.remitano.demo.dto.MovieDTO;
import com.remitano.demo.dto.PageDTO;
import com.remitano.demo.entity.MovieEntity;
import com.remitano.demo.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class MovieConverter {

    @Autowired
    private Mapper mapper;

    public MovieDTO convert2DTO(MovieEntity entity) {
        return mapper.map(entity, MovieDTO.class);
    }

    public PageDTO<MovieDTO> convertToPageDTO(Page<MovieEntity> entities)  {
        List<MovieEntity> content = entities.getContent();
        List<MovieDTO> dtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(content)) {
            for (MovieEntity entity: content) {
                dtos.add(convert2DTO(entity));
            }
        }
        return new PageDTO<>(entities.getNumber(), dtos.size(), entities.getTotalPages(), dtos);
    }
}
