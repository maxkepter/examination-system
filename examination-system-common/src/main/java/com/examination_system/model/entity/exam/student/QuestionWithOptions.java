package com.examination_system.model.entity.exam.student;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;

import com.examination_system.model.entity.exam.Question;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Scope("prototype")
public class QuestionWithOptions {
    private long questionId;
    private String content;
    private List<Option> options;

    public QuestionWithOptions(String content, List<Option> options) {
        this.content = content;
        this.options = options;
    }

    public static QuestionWithOptions convertFromEntity(Question question) {
        List<Option> options = Option.convertFromEntities(question.getOptions());

        return new QuestionWithOptions(question.getQuestionContent(), Option.randomOption(options));
    }

    public static List<QuestionWithOptions> convertFromEntities(List<Question> questions) {
        List<QuestionWithOptions> questionWithOptions = new ArrayList<>();
        for (Question question : questions) {
            questionWithOptions.add(convertFromEntity(question));
        }

        return questionWithOptions;
    }

    public static void randomQuestion(List<QuestionWithOptions> questions) {
        List<QuestionWithOptions> tempQuestions = new ArrayList<>();
        while (!questions.isEmpty()) {
            int randomIndex = (int) (Math.random() * questions.size());
            QuestionWithOptions question = questions.remove(randomIndex);
            question.setQuestionId(tempQuestions.size());
            tempQuestions.add(question);
        }
        questions.addAll(tempQuestions);
    }

}