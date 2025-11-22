package com.quiz.Services.impl;

import com.quiz.Entity.Quiz;
import com.quiz.Repository.QuizRepository;
import com.quiz.Services.QuestionClient;
import com.quiz.Services.QuizService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final QuestionClient questionClient;

    public QuizServiceImpl(QuizRepository quizRepository, QuestionClient questionClient) {
        this.quizRepository = quizRepository;
        this.questionClient = questionClient;
    }

    @Override
    public Quiz add(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @Override
    public List<Quiz> get() {
        List<Quiz> quizzes = quizRepository.findAll();

        return quizzes.stream()
                .map(quiz -> {
                    quiz.setQuestions(
                            questionClient.getQuestionOfQuiz(quiz.getId())
                    );
                    return quiz;
                }).collect(Collectors.toList());
    }

    @Override
    public Quiz get(Long id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("quiz not found"));

        quiz.setQuestions(
                questionClient.getQuestionOfQuiz(quiz.getId())
        );

        return quiz;
    }
}
