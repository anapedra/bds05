package com.devsuperior.movieflix.dto;

import com.devsuperior.movieflix.entities.Review;

import java.io.Serializable;
import java.util.Objects;

public class ReviewDTO implements Serializable {
    private static final long serialVersionUID=1L;
        private Long id;
        private String text;
        private MovieDTO movie;
        private UserDTO user;

    public ReviewDTO(Long id, String text, MovieDTO movie, UserDTO user) {
        this.id = id;
        this.text = text;
        this.movie = movie;
        this.user = user;
    }

    public ReviewDTO() {

    }
    public ReviewDTO(Review entity) {
        id = entity.getId();
        text= entity.getText();
        entity.getMovie();
        entity.getUser();


    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public MovieDTO getMovie() {
        return movie;
    }

    public void setMovie(MovieDTO movie) {
        this.movie = movie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewDTO)) return false;
        ReviewDTO reviewDTO = (ReviewDTO) o;
        return Objects.equals(id, reviewDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
