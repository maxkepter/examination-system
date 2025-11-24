package com.SpringExaminationSystem.service.exam;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SpringExaminationSystem.model.dto.common.QuestionDTO;
import com.SpringExaminationSystem.model.dto.common.QuestionOptionDTO;
import com.SpringExaminationSystem.model.entity.exam.Chapter;
import com.SpringExaminationSystem.model.entity.exam.Question;
import com.SpringExaminationSystem.model.entity.exam.QuestionOption;
import com.SpringExaminationSystem.model.mapper.exam.QuestionMapper;
import com.SpringExaminationSystem.model.mapper.exam.QuestionOptionMapper;
import com.SpringExaminationSystem.repository.exam.QuestionDao;
import com.SpringExaminationSystem.repository.exam.QuestionOptionDao;

@Service
public class QuestionService {
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    QuestionOptionMapper questionOptionMapper;
    @Autowired
    QuestionDao questionDao;
    @Autowired
    QuestionOptionDao questionOptionDao;

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

        questionDao.save(question);
    }

}
