package com.softuni.mvc.repositories;

import com.softuni.mvc.models.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Optional<Project> findFirstByName(String name);

    Optional<List<Project>> findAllByIsFinished(boolean status);

}
