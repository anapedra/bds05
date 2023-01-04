package com.devsuperior.movieflix.Services.servicesentities;

import com.devsuperior.movieflix.Services.exceptionservice.DataBaseException;
import com.devsuperior.movieflix.Services.exceptionservice.ResourceNotFoundException;
import com.devsuperior.movieflix.dto.GenreDTO;
import com.devsuperior.movieflix.dto.MovieDTO;
import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.repositories.GenreRepository;
import com.devsuperior.movieflix.repositories.MovieRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class GenreService {

    private final MovieRepository movieRepository;
    private final GenreRepository repository;
    public GenreService(MovieRepository movieRepository, GenreRepository repository) {
        this.movieRepository = movieRepository;
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Page<GenreDTO> findAllPaged(Pageable pageable){
        Page<Genre> list=repository.findAll(pageable);
        return list.map(x -> new GenreDTO(x));
    }
    @Transactional(readOnly = true)
    public GenreDTO findById(Long id){
        Optional<Genre> obj=repository.findById(id);
        Genre entity=obj.orElseThrow(
                ()-> new ResourceNotFoundException("Id "+id+" not found"));
        return new GenreDTO(entity);
    }

    @Transactional
    public GenreDTO insert(GenreDTO dto) {
        var genre=new Genre();
        copyDtoToEntity(dto,genre);
        genre=repository.save(genre);
        return new GenreDTO(genre);
    }

    @Transactional
    public GenreDTO upDate(Long id, GenreDTO dto){
        try {
            var genre= repository.getOne(id);
            copyDtoToEntity(dto,genre);
            genre=repository.save(genre);
            return new GenreDTO(genre);
        }
        catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Id " + id + " not found :(");
        }

    }
    @Transactional
    public void deleteById(Long id){

        try {
            repository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Id "+id+" not found!");
        }
        catch (DataIntegrityViolationException e){
            throw new DataBaseException("Integrity violation");
        }

    }

    private void copyDtoToEntity(GenreDTO genreDTO,Genre genre){
        genre.setName(genreDTO.getName());
        genre.getMovies().clear();
        for (MovieDTO movieDTO : genreDTO.getMovies()){
            Movie movie=movieRepository.getOne(movieDTO.getId());
            genre.getMovies().add(movie);
        }

    }





}


