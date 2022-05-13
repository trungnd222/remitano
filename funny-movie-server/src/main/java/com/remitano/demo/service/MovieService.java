package com.remitano.demo.service;

import com.remitano.demo.entity.MovieEntity;
import com.remitano.demo.entity.UserEntity;
import com.remitano.demo.repository.MovieRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class MovieService {
    public static final Pattern YOUTUBE_PATTERN = Pattern.compile("^.*(youtu.be\\/|v\\/|u\\/\\w\\/|embed\\/|watch\\?v=|\\&v=|\\?v=)([^#\\&\\?]*).*");

    @Autowired
    private MovieRepository movieRepository;

    public Page<MovieEntity> find(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        return movieRepository.findAll(pageable);
    }

    public MovieEntity share(String link, UserEntity user) throws Exception {
        MovieEntity movieEntity = parseLink(link);
        movieEntity.setUser(user);
        movieRepository.saveAndFlush(movieEntity);
        return movieEntity;
    }

    public MovieEntity parseLink(String url) throws Exception {
        if (!YOUTUBE_PATTERN.matcher(url).matches()) {
            throw new Exception("link is invalid");
        }
        Document document = Jsoup.connect(url).get();
        String description = document.select("meta[property=og:description]").get(0).attr("content");
        String title = document.select("meta[property=og:title]").get(0).attr("content");
        String img = document.select("meta[property=og:image]").get(0).attr("content");
        String embed = document.select("meta[property=og:video:url]").get(0).attr("content");

        MovieEntity movie = new MovieEntity();
        movie.setDesc(description);
        movie.setImg(img);
        movie.setTitle(title);
        movie.setUrl(embed);
        return movie;
    }

}
