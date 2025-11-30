package com.examination_system.exam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examination_system.exam.model.dto.common.QuestionDTO;
import com.examination_system.common.model.entity.exam.Chapter;
import com.examination_system.common.model.entity.exam.Question;
import com.examination_system.common.model.entity.exam.QuestionOption;
import com.examination_system.exam.model.mapper.QuestionMapper;
import com.examination_system.exam.model.mapper.QuestionOptionMapper;
import com.examination_system.common.repository.exam.QuestionRepository;
import com.examination_system.common.repository.exam.QuestionOptionRepository;

@Service
public class QuestionService {
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    QuestionOptionMapper questionOptionMapper;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    QuestionOptionRepository questionOptionRepository;

    public void createQuestion(QuestionDTO questionDTO) {
        Question question = Question.builder()
                .questionContent(questionDTO.getQuestionContent())
                .difficulty(questionDTO.getDifficulty())
                .build();

        if (questionDTO.getChapterId() != null) {
            Chapter chapter = new Chapter();
            chapter.setChapterId(questionDTO.getChapterId());
            question.setChapter(chapter);
        }

        if (questionDTO.getOptions() != null && !questionDTO.getOptions().isEmpty()) {

            List<QuestionOption> optionEntities = questionDTO.getOptions().stream()
                    .map(optionDTO -> {
                        QuestionOption option = questionOptionMapper.toNewEntity(optionDTO);
                        option.setQuestion(question);
                        option.setCorrect(optionDTO.isCorrect());
                        return option;
                    })
                    .toList();
            question.setOptions(optionEntities);
        }

        questionRepository.save(question);
    }

}
