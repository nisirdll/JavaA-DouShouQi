package view;

import model.Chessboard;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class ChessGameFrame extends JFrame {
    private Chessboard chessboard;
    private JLabel roundCountLabel;
    private JLabel currentPlayerLabel;
    private boolean isPlayer1Turn;
    private int roundCount;


    private  final int HEIGHT ;
    private  final int WIDTH  ;
    private final int ONE_CHESS_SIZE;
    private view.ChessboardComponent chessboardComponent;

    private Timer timerPlayer1;
    private Timer timerPlayer2;
    private JLabel timerLabelPlayer1;
    private JLabel timerLabelPlayer2;


    public ChessGameFrame(int width, int height) {
        setTitle("这就是自信队-斗兽棋");
        this.WIDTH = width;
        this.HEIGHT = height;
        this.ONE_CHESS_SIZE = (HEIGHT *4 /5)/9 ;
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
//        chessboard = new Chessboard(this);
        timerLabelPlayer1 = createTimerLabel(720, 720);
        add(timerLabelPlayer1);

        timerLabelPlayer2 = createTimerLabel(20, 20);
        add(timerLabelPlayer2);

        timerPlayer1 = createTimer(timerLabelPlayer1);
        timerPlayer2 = createTimer(timerLabelPlayer2);

        roundCountLabel = new JLabel("Round: " + roundCount);
        roundCountLabel.setFont(new Font("Arial", Font.BOLD, 24));
        roundCountLabel.setSize(100, 30);
        roundCountLabel.setLocation(WIDTH / 2 - 50, HEIGHT/20);
        add(roundCountLabel);
        startGame();
        addHelloButton();
        playMusic();
        addToggleMusicButton();
    }




    private void startGame() {
        addChessboard();
        Random random = new Random();
        isPlayer1Turn = random.nextBoolean();
        roundCount = 0;

        if (isPlayer1Turn) {
            timerPlayer1.start();
        } else {
            timerPlayer2.start();
        }
        currentPlayerLabel = new JLabel("Current Player: " + (isPlayer1Turn ? "Player 1" : "Player 2"));
        currentPlayerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        currentPlayerLabel.setSize(200, 30);
        currentPlayerLabel.setLocation(WIDTH / 2 - 100, HEIGHT / 20 + 40);
        add(currentPlayerLabel);

        // 添加行棋的逻辑




    }
    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }
    public void setChessboardComponent(ChessboardComponent chessboardComponent) {
        this.chessboardComponent = chessboardComponent;
    }
    private void addChessboard() {
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE);
        chessboardComponent.setLocation(HEIGHT/5, HEIGHT/10);
        add(chessboardComponent);
    }
    private void addHelloButton(){
        JButton button = new JButton("Back");
        button.addActionListener((e)->{
            System.out.println("Click Back");
            clip.stop();
            new GameUI().setVisible(true);
            dispose();
        });
        button.setLocation(HEIGHT, HEIGHT/10 + 120);
        button.setSize(200, 50);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        add(button);
    }
    private Clip clip;
    private boolean isPlaying = true;
    // 播放音乐
    private void playMusic() {
        try {
            // 加载音乐文件
            ClassLoader classLoader = getClass().getClassLoader();
            File musicFile = new File(classLoader.getResource("music/october.wav").getFile());
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicFile);

            // 创建音频剪辑
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            // 循环播放音乐
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // 停止音乐播放
    private void toggleMusic(){
        if(clip != null){
            if(isPlaying){
                clip.stop();

            }else{
                clip.start();

            }
            isPlaying = !isPlaying;
        }
    }
    private void addToggleMusicButton() {
        JButton button = new JButton("Music");
        button.addActionListener((e) -> {
            toggleMusic();
        });
        button.setLocation(WIDTH-220, 10);
        button.setSize(200, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 14));
        add(button);
    }
    public void increaseRoundCount() {
        roundCount++;
        roundCountLabel.setText("Round: " + roundCount);
        currentPlayerLabel.setText("Current Player: " + (isPlayer1Turn ? "Player 2" : "Player 1"));
        if (isPlayer1Turn) {
            timerPlayer2.stop();
            timerPlayer1.start();
        } else {
            timerPlayer1.stop();
            timerPlayer2.start();
        }
        isPlayer1Turn = !isPlayer1Turn;
    }
    public static void handleMoveChessPiece(){

    }


    private JLabel createTimerLabel(int x, int y) {
        JLabel timerLabel = new JLabel("10:00");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        timerLabel.setSize(100, 30);
        timerLabel.setLocation(x, y);
        return timerLabel;
    }

    private Timer createTimer(JLabel timerLabel) {
        Timer timer = new Timer(1000, new TimerActionListener(timerLabel));
        timer.setInitialDelay(0);
        return timer;
    }
    private class TimerActionListener implements ActionListener {
        private JLabel timerLabel;
        private int minutes = 10;
        private int seconds = 0;

        public TimerActionListener(JLabel timerLabel) {
            this.timerLabel = timerLabel;
        }
        public Timer getTimerPlayer1() {
            return timerPlayer1;
        }
        public Timer getTimerPlayer2() {
            return timerPlayer2;
        }


        @Override
        public void actionPerformed(ActionEvent e) {
            if (minutes == 0 && seconds == 0) {
                // Time is up, show negative message and stop the timer
                JOptionPane.showMessageDialog(null, "Time's up! The other player wins.");
                ((Timer) e.getSource()).stop();
            } else {
                if (seconds == 0) {
                    minutes--;
                    seconds = 59;
                } else {
                    seconds--;
                }

                // Update the timer label with the new time
                timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
            }
        }
    }



}
