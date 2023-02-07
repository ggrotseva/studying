package bg.softuni.pathfinder.service;

import bg.softuni.pathfinder.model.Role;
import bg.softuni.pathfinder.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByName(String name) {
        return this.roleRepository.findByName(name)
                .orElseThrow(NoSuchElementException::new);
    }
}
