package com.SpringExaminationSystem.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.SpringExaminationSystem.model.entity.exam.Chapter;
import com.SpringExaminationSystem.model.entity.exam.Exam;
import com.SpringExaminationSystem.model.entity.exam.Major;
import com.SpringExaminationSystem.model.entity.exam.Question;
import com.SpringExaminationSystem.model.entity.exam.QuestionOption;
import com.SpringExaminationSystem.model.entity.exam.Subject;
import com.SpringExaminationSystem.model.entity.user.AuthInfo;
import com.SpringExaminationSystem.model.entity.user.User;
import com.SpringExaminationSystem.repository.exam.ChapterDao;
import com.SpringExaminationSystem.repository.exam.ExamDao;
import com.SpringExaminationSystem.repository.exam.MajorDao;
import com.SpringExaminationSystem.repository.exam.QuestionDao;
import com.SpringExaminationSystem.repository.exam.QuestionOptionDao;
import com.SpringExaminationSystem.repository.exam.SubjectDao;
import com.SpringExaminationSystem.repository.user.AuthInfoDao;
import com.SpringExaminationSystem.repository.user.UserDao;

@Component
public class DataInitializer implements CommandLineRunner {

        @Autowired
        private MajorDao majorDao;

        @Autowired
        private SubjectDao subjectDao;

        @Autowired
        private ChapterDao chapterDao;

        @Autowired
        private QuestionDao questionDao;

        @Autowired
        private QuestionOptionDao questionOptionDao;

        @Autowired
        private ExamDao examDao;

        @Autowired
        AuthInfoDao authInfoDao;

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

        @Override
        public void run(String... args) throws Exception {
                if (majorDao.count() > 0) {
                        System.out.println("Sample data already exists. Skipping initialization.");
                        return;
                }

                System.out.println("Initializing sample data...");

                Major computerScience = Major.builder()
                                .majorCode("CS")
                                .majorName("Computer Science")
                                .build();
                majorDao.save(computerScience);

                Subject javaProgramming = Subject.builder()
                                .subjectCode("JAVA101")
                                .subjectName("Java Programming")
                                .majors(Arrays.asList(computerScience))
                                .build();
                subjectDao.save(javaProgramming);

                Chapter chapter1 = Chapter.builder()
                                .chapterName("Introduction to Java")
                                .subject(javaProgramming)
                                .build();
                chapterDao.save(chapter1);

                Chapter chapter2 = Chapter.builder()
                                .chapterName("Object-Oriented Programming")
                                .subject(javaProgramming)
                                .build();
                chapterDao.save(chapter2);

                Chapter chapter3 = Chapter.builder()
                                .chapterName("Java Collections")
                                .subject(javaProgramming)
                                .build();
                chapterDao.save(chapter3);

                User examCreator = new User("John", "Doe", "john.doe@university.edu");

                AuthInfo authInfo = AuthInfo.builder()
                                .userName("johndoe")
                                .password(passwordEncoder.encode("123456"))
                                .role(AuthInfo.ROLE_USER)
                                .user(examCreator)
                                .build();

                authInfoDao.save(authInfo);

                List<Question> questions = Arrays.asList(
                                createQuestion("What is the main method signature in Java?", 1, chapter1, Arrays.asList(
                                                "public static void main(String[] args)", true,
                                                "public void main(String[] args)", false,
                                                "static void main(String[] args)", false,
                                                "public static main(String[] args)", false)),
                                createQuestion("Which keyword is used to create an object in Java?", 1, chapter1,
                                                Arrays.asList(
                                                                "new", true,
                                                                "create", false,
                                                                "object", false,
                                                                "instance", false)),
                                createQuestion("What is the default value of an int variable in Java?", 1, chapter1,
                                                Arrays.asList(
                                                                "0", true,
                                                                "null", false,
                                                                "undefined", false,
                                                                "-1", false)),
                                createQuestion("Which of the following is a primitive data type in Java?", 1, chapter1,
                                                Arrays.asList(
                                                                "String", false,
                                                                "int", true,
                                                                "Integer", false,
                                                                "Object", false)),
                                createQuestion("What is inheritance in Java?", 2, chapter2, Arrays.asList(
                                                "A mechanism that allows a class to inherit properties and methods from another class",
                                                true,
                                                "A way to create multiple objects", false,
                                                "A method to access private variables", false,
                                                "A technique to improve performance", false)),
                                createQuestion("Which keyword is used to prevent inheritance in Java?", 2, chapter2,
                                                Arrays.asList(
                                                                "final", true,
                                                                "static", false,
                                                                "private", false,
                                                                "protected", false)),
                                createQuestion("What is polymorphism in Java?", 2, chapter2, Arrays.asList(
                                                "The ability of different objects to respond to the same method call in different ways",
                                                true,
                                                "The ability to create multiple constructors", false,
                                                "The ability to use multiple inheritance", false,
                                                "The ability to access private methods", false)),
                                createQuestion("Which collection interface extends Iterable?", 3, chapter3,
                                                Arrays.asList(
                                                                "Collection", true,
                                                                "List", false,
                                                                "Set", false,
                                                                "Map", false)),
                                createQuestion("What is the difference between ArrayList and LinkedList?", 3, chapter3,
                                                Arrays.asList(
                                                                "ArrayList uses dynamic array, LinkedList uses doubly linked list",
                                                                true,
                                                                "ArrayList is faster for insertion, LinkedList is faster for access",
                                                                false,
                                                                "ArrayList can store objects, LinkedList can only store primitives",
                                                                false,
                                                                "There is no difference between them", false)),
                                createQuestion("Which method is used to add an element to an ArrayList?", 3, chapter3,
                                                Arrays.asList(
                                                                "add()", true,
                                                                "insert()", false,
                                                                "append()", false,
                                                                "push()", false)));

                for (Question question : questions) {
                        questionDao.save(question);
                        for (QuestionOption option : question.getOptions()) {
                                questionOptionDao.save(option);
                        }
                }

                Exam sampleExam = Exam.builder()
                                .duration(60)
                                .examDate(LocalDateTime.now().plusDays(7))
                                .deadline(LocalDate.now().plusDays(14))
                                .examCode("JAVA101-EXAM-001")
                                .examName("Java Programming Midterm Exam")
                                .questions(questions)
                                .user(examCreator)
                                .build();
                examDao.save(sampleExam);

                System.out.println("Sample data initialized successfully!");
        }

        private Question createQuestion(String content, int difficulty, Chapter chapter, List<Object> optionsData) {
                Question question = Question.builder()
                                .questionContent(content)
                                .difficulty(difficulty)
                                .chapter(chapter)
                                .options(new ArrayList<QuestionOption>())
                                .build();

                for (int i = 0; i < optionsData.size(); i += 2) {
                        String optionContent = (String) optionsData.get(i);
                        boolean isCorrect = (Boolean) optionsData.get(i + 1);

                        QuestionOption option = QuestionOption.builder()
                                        .optionContent(optionContent)
                                        .isCorrect(isCorrect)
                                        .question(question)
                                        .build();

                        question.getOptions().add(option);
                }
                return question;
        }
}
