package com.devsuperior.movieflix.Services.servicesentities;

import com.devsuperior.movieflix.Services.exceptionservice.DataBaseException;
import com.devsuperior.movieflix.Services.exceptionservice.ResourceNotFoundException;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.repositories.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository repository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    public ReviewService(ReviewRepository repository, UserRepository userRepository, MovieRepository movieRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    @Transactional(readOnly = true)
    public Page<ReviewDTO> findAllPaged(Pageable pageable){
        Page<Review> list= repository.findAll(pageable);
        return list.map(x -> new ReviewDTO(x));
    }
    @Transactional(readOnly = true)
    public ReviewDTO findById(Long id){
        Optional<Review> obj=repository.findById(id);
        Review entity=obj.orElseThrow(
                ()-> new ResourceNotFoundException("Id "+id+" not found"));
        return new ReviewDTO(entity);
    }

    @Transactional
    public ReviewDTO insert(ReviewDTO dto) {
        var review=new Review();
        copyDtoToEntity(dto,review);
        review=repository.save(review);
        return new ReviewDTO(review);
    }

    @Transactional
    public ReviewDTO upDate(Long id, ReviewDTO dto){
        try {
            var review= repository.getOne(id);
            copyDtoToEntity(dto,review);
            review=repository.save(review);
            return new ReviewDTO(review);
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

    private void copyDtoToEntity(ReviewDTO reviewDTO,Review review){
        review.setText(reviewDTO.getText());

        var movie=new Movie();
        movie.setId(reviewDTO.getMovie().getId());
        review.setMovie(movie);
        var user=new User();
        user.setId(reviewDTO.getUser().getId());
        review.setUser(user);

        //var user=userRepository.getOne(reviewDTO.getUserId());
       // review.setUser(user);
      //  var movie=movieRepository.getOne(reviewDTO.getMovieId());
       // review.setMovie(movie);


    }

}






