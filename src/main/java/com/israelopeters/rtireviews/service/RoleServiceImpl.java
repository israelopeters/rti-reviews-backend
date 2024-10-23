package com.israelopeters.rtireviews.service;

import com.israelopeters.rtireviews.model.Role;
import com.israelopeters.rtireviews.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository roleRepository;

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}
