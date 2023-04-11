package com.likebookapp.service;

import com.likebookapp.model.entity.Mood;
import com.likebookapp.model.enums.MoodName;
import com.likebookapp.repository.MoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Service
public class MoodService {

    private final MoodRepository moodRepository;

    @Autowired
    public MoodService(MoodRepository moodRepository) {
        this.moodRepository = moodRepository;
    }

    @PostConstruct
    public void initMoods() {
        if (this.moodRepository.count() == 0) {
            Arrays.stream(MoodName.values())
                    .map(mood -> new Mood().setMoodName(mood))
                    .forEach(this.moodRepository::save);
        }
    }

    public Mood getMoodByName(MoodName moodName) {
        return this.moodRepository.findByMoodName(moodName).orElse(null);
    }
}
