package com.devsuperior.movieflix.dto;

import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import java.io.Serializable;
import java.util.Objects;

public class ReviewDTO implements Serializable {
    private static final long serialVersionUID=1L;
        private Long id;
         private String text;
        private User user;
        private Movie movie;


    public ReviewDTO(Long id, String text,User user, Movie movie) {
        this.id = id;
        this.text=text;
        this.user = user;
        this.movie = movie;
    }

    public ReviewDTO() {

    }
    public ReviewDTO(Review entity) {
        id = entity.getId();
        text= entity.getText();
        user = entity.getUser();
        movie = entity.getMovie();
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
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
