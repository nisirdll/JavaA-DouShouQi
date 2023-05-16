package controller;
import java.util.Timer;
import java.util.TimerTask;

public class ChessTimer {
    private Timer timer;
    private int timeLeft;
    private boolean isPlayer1;

    public ChessTimer() {
        timer = new Timer();
        timeLeft = 600; // 10 minutes
        isPlayer1 = true;
    }

    public void start() {
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (timeLeft == 0) {
                    System.out.println("Time's up!");
                    System.exit(0);
                }
                if (timeLeft % 60 == 0) {
                    System.out.println(timeLeft / 60 + " minutes left");
                } else if (timeLeft == 30) {
                    System.out.println("30 seconds left");
                }
                timeLeft--;
            }
        }, 0, 1000);
    }

    public void switchPlayer() {
        isPlayer1 = !isPlayer1;
        timeLeft = 600; // reset the timer
    }

    public boolean isPlayer1() {
        return isPlayer1;
    }
}


