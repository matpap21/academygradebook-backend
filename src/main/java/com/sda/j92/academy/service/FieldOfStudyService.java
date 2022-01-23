package com.sda.j92.academy.service;

import com.sda.j92.academy.model.FieldOfStudy;
import com.sda.j92.academy.modelDto.FieldOfStudyDto;
import com.sda.j92.academy.repository.FieldOfStudyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FieldOfStudyService {
    private final FieldOfStudyRepository fieldOfStudyRepository;

    public List<FieldOfStudyDto> findAll(){
        List<FieldOfStudy> fieldOfStudies = fieldOfStudyRepository.findAll ();
        log.info("GetAll : " + fieldOfStudies);
        return fieldOfStudies.stream ().map (fieldOfStudy -> FieldOfStudyDto.builder ().fieldOfStudyEnum (fieldOfStudy.getFieldOfStudyEnum ()).id (fieldOfStudy.getId ()).build ()).collect(Collectors.toList());

    }
    public void add(FieldOfStudyDto fieldOfStudy){
        log.info ("Add : " + fieldOfStudy);
        fieldOfStudyRepository.save (FieldOfStudy.builder ( ).fieldOfStudyEnum (fieldOfStudy.getFieldOfStudyEnum ()).build ( ));
    }
    public void delete(Long id){
        log.info ("Remove: " + id);
        fieldOfStudyRepository.deleteById (id);
    }

}
