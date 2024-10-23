package com.israelopeters.rtireviews.service;

import com.israelopeters.rtireviews.model.Role;

public interface RoleService  {
    Role findByName(String name);
}
