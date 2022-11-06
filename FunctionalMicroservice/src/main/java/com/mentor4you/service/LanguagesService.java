package com.mentor4you.service;

import com.mentor4you.model.Languages;
import com.mentor4you.repository.LanguagesRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class LanguagesService {
    private final LanguagesRepository languagesRepository;

    public LanguagesService(LanguagesRepository languagesRepository) {
        this.languagesRepository = languagesRepository;
    }

    public List<Languages> getAllLanguages(){
        return languagesRepository.findAll();
    }

    public Set<Languages> getAllLanguages(Set<String> list){

        Set<Languages> languages =new HashSet<>();
        for (String name : list) {
            if(languagesRepository.findByName(name)!=null)
                languages.add(languagesRepository.findByName(name));
        }
        return languages;
    }


    public void addNewLanguages(String name) {
        Languages l = new Languages();
        l.setName(name);
        languagesRepository.save(l);
    }
}
