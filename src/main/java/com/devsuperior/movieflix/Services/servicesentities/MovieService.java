package com.devsuperior.movieflix.Services.servicesentities;

import com.devsuperior.movieflix.Services.exceptionservice.DataBaseException;
import com.devsuperior.movieflix.Services.exceptionservice.ResourceNotFoundException;
import com.devsuperior.movieflix.dto.MovieDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.repositories.GenreRepository;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;
    private final GenreRepository genreRepository;
    public MovieService(MovieRepository movieRepository, ReviewRepository reviewRepository, GenreRepository genreRepository) {
        this.movieRepository = movieRepository;
        this.reviewRepository = reviewRepository;
        this.genreRepository = genreRepository;
    }


    @Transactional(readOnly = true)
    public Page<MovieDTO> findAllPaged(Pageable pageable){
        Page<Movie> list=movieRepository.findAll(pageable);
        return list.map(x -> new MovieDTO(x));
    }
    @Transactional(readOnly = true)
    public MovieDTO findById(Long id){
        Optional<Movie> obj=movieRepository.findById(id);
        Movie entity=obj.orElseThrow(
                ()-> new ResourceNotFoundException("Id "+id+" not found"));
        return new MovieDTO(entity);
    }

    @Transactional
    public MovieDTO insert(MovieDTO dto) {
        var movie=new Movie();
        copyDtoToEntity(dto,movie);
        movie=movieRepository.save(movie);
        return new MovieDTO(movie);
    }

    @Transactional
    public MovieDTO upDate(Long id, MovieDTO dto){
        try {
            var movie= movieRepository.getOne(id);
            copyDtoToEntity(dto,movie);
            movie=movieRepository.save(movie);
            return new MovieDTO(movie);
        }
        catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Id " + id + " not found :(");
        }

    }
    @Transactional
    public void deleteById(Long id){

        try {
            movieRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Id "+id+" not found!");
        }
        catch (DataIntegrityViolationException e){
            throw new DataBaseException("Integrity violation");
        }

    }

    private void copyDtoToEntity(MovieDTO movieDTO,Movie movie){
        movie.setImgUrl(movieDTO.getImgUrl());
        movie.setSubTitle(movieDTO.getTitle());
        movie.setSubTitle(movieDTO.getSubTitle());
        movie.setSynopsis(movieDTO.getSynopsis());
        movie.setYear(movieDTO.getYear());

        var genre=new Genre();
        genre.setId(movieDTO.getGenre().getId());
        movie.setGenre(genre);

        movie.getReviews().clear();
        for (ReviewDTO reviewDTO :movieDTO.getReviews()){
            Review review = reviewRepository.getOne(movieDTO.getId());
            movie.getReviews().add(review);

        }

    }



}


