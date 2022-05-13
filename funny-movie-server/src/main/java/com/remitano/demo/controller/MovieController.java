package com.remitano.demo.controller;

import com.remitano.demo.common.BaseResponse;
import com.remitano.demo.common.ErrorCode;
import com.remitano.demo.converter.MovieConverter;
import com.remitano.demo.dto.MovieDTO;
import com.remitano.demo.dto.PageDTO;
import com.remitano.demo.entity.MovieEntity;
import com.remitano.demo.entity.UserEntity;
import com.remitano.demo.service.MovieService;
import com.remitano.demo.util.AuthenticationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieConverter converter;

    @GetMapping(path = "/find")
    public ResponseEntity<BaseResponse> find( @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int pageSize) {
        BaseResponse response = new BaseResponse();
        try{
            Page<MovieEntity> entities = movieService.find(page, pageSize);
            PageDTO<MovieDTO> dtoPageDTO = converter.convertToPageDTO(entities);
            response.setData(dtoPageDTO);
        }catch (Exception e) {
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }


    @GetMapping(path = "/share")
    public ResponseEntity<BaseResponse> share(@RequestParam(required = true, defaultValue = "0") String link, Authentication auth) {
        BaseResponse response = new BaseResponse();
        try{
            UserEntity userEntity = AuthenticationUtils.getAuthUser(auth);
            if (userEntity == null) {
                throw new Exception("not permission");
            }
            MovieEntity movieEntity = movieService.share(link, userEntity);
            response.setData(converter.convert2DTO(movieEntity));
        }catch (Exception e) {
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }


}
