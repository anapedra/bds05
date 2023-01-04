package com.devsuperior.movieflix.Services.servicesentities;

import com.devsuperior.movieflix.Services.exceptionservice.DataBaseException;
import com.devsuperior.movieflix.Services.exceptionservice.ResourceNotFoundException;
import com.devsuperior.movieflix.dto.RoleDTO;
import com.devsuperior.movieflix.entities.Role;
import com.devsuperior.movieflix.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RoleService {
    @Autowired
    private RoleRepository repository;

    @Transactional(readOnly = true)
    public List<RoleDTO> findAll() {
        List<Role> list = repository.findAll(Sort.by("name"));
        return list.stream().map(x -> new RoleDTO(x)).collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public RoleDTO findById(Long id){
        Optional<Role> obj=repository.findById(id);
        Role entity=obj.orElseThrow(
                ()-> new ResourceNotFoundException("Id "+id+" not found"));
        return new RoleDTO(entity);
    }
    @Transactional
    public RoleDTO save(RoleDTO dto) {
        var role=new Role();
        role.setAuthority(dto.getAuthority());
        role=repository.save(role);
        return new RoleDTO(role);
    }
    @Transactional
    public RoleDTO upDate(Long id, RoleDTO dto){
        try {
            var role = repository.getOne(id);
            role.setAuthority(dto.getAuthority());
            role=repository.save(role);
            return new RoleDTO(role);
        }
        catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Id " + id + " not found :(");
        }

    }

    public void delet(Long id){

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
}


