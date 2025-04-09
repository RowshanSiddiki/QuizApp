import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class QuizApp extends JFrame implements ActionListener {
    JLabel questionLabel, timerLabel, resultLabel;
    JRadioButton[] options = new JRadioButton[4];
    ButtonGroup group;
    JButton nextButton;

    String[] questions = {
        "What is the size of int in Java?",
        "Which OOP principle is being used when a subclass inherits a superclass?",
        "Which diagram shows object interaction over time?"
    };

    String[][] choices = {
        {"2 bytes", "4 bytes", "8 bytes", "16 bytes"},
        {"Encapsulation", "Polymorphism", "Inheritance", "Abstraction"},
        {"Class Diagram", "Activity Diagram", "Sequence Diagram", "Use Case Diagram"}
    };

    int[] answers = {1, 2, 2};
    int currentQuestion = 0, score = 0;
    Timer timer;
    int timeLeft = 15;

    QuizApp() {
        setTitle("Java Quiz App");
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 1));

        questionLabel = new JLabel("", JLabel.CENTER);
        timerLabel = new JLabel("Time left: 15s", JLabel.CENTER);
        resultLabel = new JLabel("", JLabel.CENTER);

        group = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            group.add(options[i]);
            add(options[i]);
        }

        nextButton = new JButton("Next");
        nextButton.addActionListener(this);

        add(timerLabel);
        add(questionLabel);
        add(options[0]);
        add(options[1]);
        add(options[2]);
        add(options[3]);
        add(nextButton);
        add(resultLabel);

        loadQuestion();
        startTimer();

        setVisible(true);
    }

    void loadQuestion() {
        if (currentQuestion < questions.length) {
            group.clearSelection();
            questionLabel.setText("Q" + (currentQuestion + 1) + ": " + questions[currentQuestion]);
            for (int i = 0; i < 4; i++) {
                options[i].setText(choices[currentQuestion][i]);
            }
            timeLeft = 15;
        } else {
            showResult();
        }
    }

    void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                timerLabel.setText("Time left: " + timeLeft + "s");
                timeLeft--;
                if (timeLeft < 0) {
                    nextButton.doClick();
                }
            }
        }, 0, 1000);
    }

    void showResult() {
        timer.cancel();
        questionLabel.setText("");
        for (JRadioButton option : options) {
            option.setVisible(false);
        }
        nextButton.setVisible(false);
        resultLabel.setText("Final Score: " + score + "/" + questions.length);
    }

    public void actionPerformed(ActionEvent e) {
        if (group.getSelection() != null) {
            int selected = -1;
            for (int i = 0; i < 4; i++) {
                if (options[i].isSelected()) {
                    selected = i;
                    break;
                }
            }
            if (selected == answers[currentQuestion]) {
                score++;
            }
        }
        currentQuestion++;
        if (currentQuestion < questions.length) {
            loadQuestion();
        } else {
            showResult();
        }
    }

    public static void main(String[] args) {
        new QuizApp();
    }
}
