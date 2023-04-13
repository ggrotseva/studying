package com.plannerapp.service;

import com.plannerapp.model.entity.Priority;
import com.plannerapp.model.enums.PriorityEnum;
import com.plannerapp.repo.PriorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PriorityService {

    private final PriorityRepository priorityRepository;

    @Autowired
    public PriorityService(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    @PostConstruct
    public void initPostConstruct() {
        if (this.priorityRepository.count() == 0) {
            List<Priority> conditions = Arrays.stream(PriorityEnum.values())
                    .map(conditionEnum -> new Priority()
                            .setName(conditionEnum)
                            .setDescription(conditionEnum.getDescription()))
                    .collect(Collectors.toList());

            this.priorityRepository.saveAllAndFlush(conditions);
        }
    }

    public Priority findByName(PriorityEnum name) {
        return this.priorityRepository.findByName(name).orElse(null);
    }

}
