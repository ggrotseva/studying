package com.resellerapp.service;

import com.resellerapp.model.entity.Condition;
import com.resellerapp.model.enums.ConditionEnum;
import com.resellerapp.repository.ConditionRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConditionService {

    private final ConditionRepository conditionRepository;

    public ConditionService(ConditionRepository conditionRepository) {
        this.conditionRepository = conditionRepository;
    }

    @PostConstruct
    public void initPostConstruct() {
        if (this.conditionRepository.count() == 0) {
            List<Condition> conditions = Arrays.stream(ConditionEnum.values())
                    .map(conditionEnum -> new Condition()
                            .setName(conditionEnum)
                            .setDescription(conditionEnum.getDescription()))
                    .collect(Collectors.toList());

            this.conditionRepository.saveAllAndFlush(conditions);
        }
    }

    public Condition findByName(ConditionEnum name) {
        return this.conditionRepository.findByName(name).orElse(null);
    }
}
