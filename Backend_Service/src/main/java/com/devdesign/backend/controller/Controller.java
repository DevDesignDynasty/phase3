package com.devdesign.backend.controller;


import com.devdesign.backend.dto.QuestionsWrapper;
import com.devdesign.backend.dto.QuizResponse;
import com.devdesign.backend.entities.Question;
import com.devdesign.backend.entities.User;
import com.devdesign.backend.services.QuizService;
import com.devdesign.backend.services.UserService;
import com.devdesign.backend.services.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path="/")
public class Controller {
    @Autowired
    private QuizService quizService;
    @Autowired
    private ValidationService validation;
    @Autowired
    private UserService userService;

    //Add all questions
    @PostMapping(path="/addall")
    public ResponseEntity<String> addNewQuestions(@RequestBody QuestionsWrapper questionsWrapper){
        List<Question> questions = questionsWrapper.getQuestions();
        return ResponseEntity.ok(quizService.addNewQuestions(questions));
    }

    //Add a question
    @PostMapping(path="/add")
    public ResponseEntity<String> addNewQuestion(@RequestBody Question question){
        return ResponseEntity.ok(quizService.addNewQuestion(question));
    }

    //validate quiz taken by user or not
    @GetMapping(path="/validate/{token}")
    public ResponseEntity<String> getQuestion(@PathVariable("token") String token){
        if (userService.userExists(token)){
            String status = userService.quizStatus(token);
            return ResponseEntity.ok(status);
        }else {
            if (validation.validate(token)){
                userService.addUser(token);
                String status = userService.quizStatus(token);
                return ResponseEntity.ok(status);

            }else {
                return ResponseEntity.ok("Invalid Token");
            }

        }

    }

    //Get all questions
    @GetMapping(path="/get/{token}", produces = "application/json")
    public ResponseEntity<QuizResponse> getQuestions(@PathVariable("token") String token) {
        if (userService.userExists(token)) {
            if (userService.quizStatus(token).equals("true")) {
                // Return an empty JSON object instead of null
                return ResponseEntity.ok(new QuizResponse());
            } else {
                return ResponseEntity.ok(quizService.getQuestions());
            }
        } else {
            if (validation.validate(token)) {
                if (userService.addUser(token)) {
                    return ResponseEntity.ok(quizService.getQuestions());
                } else {
                    // Return a 204 No Content instead of null
                    return ResponseEntity.noContent().build();
                }
            } else {
                // Return a 400 Bad Request for invalid tokens
                return ResponseEntity.badRequest().build();
            }
        }
    }




    //Get quiz score
    @GetMapping(path="/score/{token}")
    public ResponseEntity<String> getScore(@PathVariable("token") String token){
        if (userService.userExists(token)){
            return ResponseEntity.ok(String.valueOf(userService.getScore(token)));
        }else {
            return ResponseEntity.ok("User not found");
        }
    }

    //Get collected coins
    @GetMapping(path="/coins/{token}")
    public ResponseEntity<String> getCoins(@PathVariable("token") String token){
        if (userService.userExists(token)){
            User user = userService.getUser(token);
            return ResponseEntity.ok(String.valueOf(user.getGameCoins()));
        }else {
            return ResponseEntity.ok("User not found");
        }
    }



    //Submit quiz
    @PutMapping(path="/submit/{token}")
    public ResponseEntity<String> submitQuiz(@PathVariable("token") String token, @RequestBody String score){
        if (userService.userExists(token)){
            Integer scoreInt = Integer.parseInt(score);
            if(userService.submitQuiz(token, scoreInt)) {
                return ResponseEntity.ok("Quiz Submitted");
            }else {
                return ResponseEntity.ok("Failed to submit quiz");
            }
        }else {
            return ResponseEntity.ok("User not found");
        }
    }

    //update collected coins
    @PutMapping(path="/coins/{token}")
    public ResponseEntity<String> addCoins(@PathVariable("token") String token, @RequestBody String coins){
        if (userService.userExists(token)){
            Integer coinsInt = Integer.parseInt(coins);
            if(userService.addCoins(token, coinsInt)) {
                return ResponseEntity.ok("Coins added");
            }else {
                return ResponseEntity.ok("Failed to add coins");
            }
        }else {
            return ResponseEntity.ok("User not found");
        }
    }
}
