package com.mentor4you.controller;

import com.mentor4you.model.Languages;
import com.mentor4you.service.LanguagesService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/languages")
public class LanguagesController {
    private final LanguagesService languagesService;

    public LanguagesController(LanguagesService languagesService) {
        this.languagesService = languagesService;
    }

    @GetMapping("/")
    public List<Languages> getAllLanguages(){
        return languagesService.getAllLanguages();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MODERATOR')")
    ResponseEntity<?> addNewLanguage(@RequestBody Languages languages){
        Map<String, String> res = new HashMap<>();
        if(!languages.getName().equals("") || languages.getName()==null){
            languagesService.addNewLanguages(languages.getName());
            res.put("message","New languages was added");
            return ResponseEntity.status(201).body(res);
        }else {
            res.put("message","Field name cannot be null or empty");
            return ResponseEntity.badRequest().body(res);
        }
    }

 }
