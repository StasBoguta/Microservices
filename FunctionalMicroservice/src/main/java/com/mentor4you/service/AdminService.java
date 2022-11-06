package com.mentor4you.service;

import com.mentor4you.model.Categories;
import com.mentor4you.model.Role;
import com.mentor4you.model.User;
import com.mentor4you.repository.CategoriesRepository;
import com.mentor4you.repository.UserRepository;
import com.mentor4you.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    UserRepository userRepository;
    UserService userService;
    JwtProvider jwtProvider;
    CategoriesRepository categoriesRepository;


    @Autowired
    public AdminService(UserRepository userRepository, UserService userService, JwtProvider jwtProvider, CategoriesRepository categoriesRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.categoriesRepository = categoriesRepository;
    }


    public String appointModerator(String email) {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User is not found.");
        }
        user.setRole(Role.MODERATOR);
        userRepository.save(user);
        return "New moderator is appointed";
    }

    public String addCategory(String header, String newCategory) throws Exception {
        User user = userService.getUserByHeader(header);
        if (!user.getRole().equals(Role.ADMIN)) {
            throw new Exception("Required ADMINISTRATOR right");
        }
        Categories categories = new Categories();
        categories.setName(newCategory);
        categoriesRepository.save(categories);
        return "New Category was added";
    }

    public String deleteCategory(String header, String category) throws Exception {
        User user = userService.getUserByHeader(header);
        if (!user.getRole().equals(Role.ADMIN)) {
            throw new Exception("Required ADMINISTRATOR right");
        }
        categoriesRepository.deleteByName(category);
        return "Category was deleted";
    }
}
