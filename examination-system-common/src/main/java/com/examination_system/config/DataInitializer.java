package com.examination_system.config;

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

import com.examination_system.model.entity.exam.Chapter;
import com.examination_system.model.entity.exam.Exam;
import com.examination_system.model.entity.exam.Major;
import com.examination_system.model.entity.exam.Question;
import com.examination_system.model.entity.exam.QuestionOption;
import com.examination_system.model.entity.exam.Subject;
import com.examination_system.model.entity.user.AuthInfo;
import com.examination_system.model.entity.user.User;
import com.examination_system.repository.exam.ChapterDao;
import com.examination_system.repository.exam.ExamDao;
import com.examination_system.repository.exam.MajorDao;
import com.examination_system.repository.exam.QuestionDao;
import com.examination_system.repository.exam.QuestionOptionDao;
import com.examination_system.repository.exam.SubjectDao;
import com.examination_system.repository.user.AuthInfoDao;
import com.examination_system.repository.user.UserDao;

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
        private UserDao userDao;

        @Autowired
        AuthInfoDao authInfoDao;

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

        @Override
        public void run(String... args) throws Exception {
                // Check if data already exists
                if (majorDao.count() > 0) {
                        System.out.println("Sample data already exists. Skipping initialization.");
                        return;
                }

                System.out.println("Initializing sample data...");

                // Create Major
                Major computerScience = Major.builder()
                                .majorCode("CS")
                                .majorName("Computer Science")
                                .build();
                majorDao.save(computerScience);

                // Create Subject
                Subject javaProgramming = Subject.builder()
                                .subjectCode("JAVA101")
                                .subjectName("Java Programming")
                                .majors(Arrays.asList(computerScience))
                                .build();
                subjectDao.save(javaProgramming);

                // Create Chapters
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

                // Create User (Exam Creator)
                User examCreator = new User("John", "Doe", "john.doe@university.edu");

                // Create AuthInfo
                AuthInfo authInfo = AuthInfo.builder()
                                .userName("johndoe")
                                .password(passwordEncoder.encode("123456")) // In a real application, passwords
                                                                            // should be hashed
                                .role(AuthInfo.ROLE_USER)
                                .user(examCreator)
                                .build();

                authInfoDao.save(authInfo);

                // Create Questions with Options
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

                // Save all questions and their options
                for (Question question : questions) {
                        questionDao.save(question);

                        // Save options for this question
                        for (QuestionOption option : question.getOptions()) {
                                questionOptionDao.save(option);
                        }
                }

                // Create Exam
                Exam sampleExam = Exam.builder()
                                .duration(60) // 60 minutes
                                .examDate(LocalDateTime.now().plusDays(7)) // 7 days from now
                                .deadline(LocalDate.now().plusDays(14)) // 14 days from now
                                .examCode("JAVA101-EXAM-001")
                                .examName("Java Programming Midterm Exam")
                                .questions(questions)
                                .user(examCreator)
                                .build();
                examDao.save(sampleExam);

                System.out.println("Sample data initialized successfully!");
                System.out.println("Created:");
                System.out.println("- 1 Major: Computer Science");
                System.out.println("- 1 Subject: Java Programming");
                System.out.println("- 3 Chapters: Introduction to Java, Object-Oriented Programming, Java Collections");
                System.out.println("- 10 Questions with 4 options each");
                System.out.println("- 1 Exam: Java Programming Midterm Exam");
                System.out.println("- 1 User: John Doe (Exam Creator)");
        }

        private Question createQuestion(String content, int difficulty, Chapter chapter, List<Object> optionsData) {
                Question question = Question.builder()
                                .questionContent(content)
                                .difficulty(difficulty)
                                .chapter(chapter)
                                .options(new ArrayList<QuestionOption>())
                                .build();

                // Create options for the question
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
