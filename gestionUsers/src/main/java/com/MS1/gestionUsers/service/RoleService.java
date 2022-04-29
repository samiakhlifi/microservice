package com.bac.tn.gestionusers.service;

import com.MS1.gestionUsers.models.Role;
import com.MS1.gestionUsers.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;


    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    public Optional<Role> getById(long id) {
        return roleRepository.findById(id);
    }

    public Role addRole(Role activite) {

        return roleRepository.save(activite);
    }

    public void deleteRole(long id) throws Exception {
        Role activite = roleRepository.getOne(id);
        if (activite != null) {
            roleRepository.deleteById(id);
        } else {
            throw new RuntimeException("Role not found");
        }
    }

    public Role updateRole(long id, Role roleRequest) throws Exception {

        Role editedRole = roleRepository.getOne(id);

        if (editedRole != null) {
            return roleRepository.save(editedRole);
        } else {
            throw new RuntimeException("Role not found");
        }
    }
}
