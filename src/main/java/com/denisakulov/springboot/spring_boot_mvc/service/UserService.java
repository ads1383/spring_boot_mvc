package com.denisakulov.springboot.spring_boot_mvc.service;


import com.denisakulov.springboot.spring_boot_mvc.model.Role;
import com.denisakulov.springboot.spring_boot_mvc.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {
    User findByUsername(String username);
    List<User> listUsers();
    List<Role> listRoles();
    void save(User user);
    User getById(Long id);
    void delete(Long id);
    Set<Role> hashSetRolesByListId(List<Long> rolesId);
}