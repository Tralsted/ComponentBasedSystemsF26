package dk.sdu.mmmi.cbse.scoreservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ScoreServiceApplication {

    private int score = 0;

    public static void main(String[] args) {
        SpringApplication.run(ScoreServiceApplication.class, args);
    }

    @PostMapping("/score/add")
    public String addScore(@RequestParam(value = "points", defaultValue = "1") int points) {
        score += points;
        return "Score updated: " + score;
    }

    @GetMapping("/score")
    public String getScore() {
        return "Current score: " + score;
    }
}
