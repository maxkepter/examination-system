package com.examination_system.model.entity.exam.student;

import java.util.ArrayList;
import java.util.List;

import com.examination_system.model.entity.exam.QuestionOption;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Option {
    private long optionId;
    private String content;
    private boolean isCorrect;

    public static Option convertFromEntity(QuestionOption option) {
        return new Option(option.getOptionId(), option.getOptionContent(), option.isCorrect());
    }

    public static List<Option> convertFromEntities(List<QuestionOption> options) {

        return options.stream().map(Option::convertFromEntity).toList();
    }

    public static List<Option> randomOption(List<Option> options) {
        List<Option> cloneOptions = new ArrayList<>(options);
        List<Option> tempOptions = new ArrayList<>();
        while (!cloneOptions.isEmpty()) {
            int randomIndex = (int) (Math.random() * cloneOptions.size());
            Option option = cloneOptions.remove(randomIndex);
            option.setOptionId(tempOptions.size());
            tempOptions.add(option);
        }
        return tempOptions;
    }
}