package com.mentor4you.service;

import com.mentor4you.repository.TypeContactsRepository;
import org.springframework.stereotype.Service;

@Service
public class TypeContactsService {

    TypeContactsRepository typeContactsRepository;


    public TypeContactsService(TypeContactsRepository typeContactsRepository) {
        this.typeContactsRepository = typeContactsRepository;
    }

    public boolean isTypeContactsExist(String name){
        if(typeContactsRepository.findByName(name)==null){return  false;}
        else {return  true;}
    }


}
