package view;

import controller.GameController;
import model.Chessboard;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class ChessGameFrame extends JFrame {


    private String[] backgroundImages = {
            "resource/img/background1.png",
            "resource/img/background2.png"
    };
    private int currentBackgroundIndex = 0;
    private JLabel backgroundLabel;
    private JButton startTimerButton;
    private JButton stopTimerButton;
    private boolean isTimer1Running;
    private boolean isTimer2Running;
    private JLabel roundCountLabel;

    private Chessboard chessboard;
    private JLabel currentPlayerLabel;
    private boolean isPlayer1Turn;
    private int roundCount;
    private JButton restartButton;


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
        chessboard = new Chessboard();
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


        startTimerButton = new JButton("Blue Turn");
        startTimerButton.setLocation(WIDTH / 2 + 120, HEIGHT / 2 - 20);
        startTimerButton.setSize(120, 30);
        startTimerButton.setFont(new Font("Arial", Font.BOLD, 14));
        startTimerButton.addActionListener(e -> startTimer());
        add(startTimerButton);

        stopTimerButton = new JButton("Red Turn");
        stopTimerButton.setLocation(WIDTH / 2 + 120, HEIGHT / 2 - 60);
        stopTimerButton.setSize(120, 30);
        stopTimerButton.setFont(new Font("Arial", Font.BOLD, 14));
        stopTimerButton.addActionListener(e -> stopTimer());
        add(stopTimerButton);
        try {
            Image backgroundImage = ImageIO.read(new File("resource/img/background1.png"));
            backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
            backgroundLabel.setBounds(0, 0, WIDTH, HEIGHT);
            add(backgroundLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JButton themeButton = new JButton("Theme");
        themeButton.setLocation(WIDTH - 220, 50);
        themeButton.setSize(200, 30);
        themeButton.setFont(new Font("Arial", Font.BOLD, 14));
        themeButton.addActionListener(e -> changeBackground());
        add(themeButton);

        restartButton = new JButton("Restart");
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });
        restartButton.setLocation(HEIGHT, HEIGHT/10 + 180);
        restartButton.setSize(200, 50);
        restartButton.setFont(new Font("Arial", Font.BOLD, 20));
        add(restartButton);


    }
    private void startGame() {
        addChessboard();
//        Random random = new Random();
        isPlayer1Turn = true;
        roundCount = 0;


        timerPlayer1.start();

        roundCountLabel = new JLabel("Round: " + roundCount);
        roundCountLabel.setFont(new Font("Arial", Font.BOLD, 24));
        roundCountLabel.setSize(100, 30);
        roundCountLabel.setLocation(WIDTH / 2 - 50, HEIGHT/20);
        add(roundCountLabel);



        currentPlayerLabel = new JLabel("Current Player: " + (isPlayer1Turn ? "Player 1" : "Player 2"));
        currentPlayerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        currentPlayerLabel.setSize(200, 30);
        currentPlayerLabel.setLocation(WIDTH / 2 - 100, HEIGHT / 20 + 40);
        add(currentPlayerLabel);

    }
    private void restartGame(){
        dispose();
        SwingUtilities.invokeLater(() -> {
            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
            GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(),new Chessboard());
            mainFrame.setVisible(true);
        });

    }
    private void changeBackground() {
        try {
            currentBackgroundIndex = (currentBackgroundIndex + 1) % backgroundImages.length;
            String newBackgroundImagePath = backgroundImages[currentBackgroundIndex];
            Image newBackgroundImage = ImageIO.read(new File(newBackgroundImagePath));
            backgroundLabel.setIcon(new ImageIcon(newBackgroundImage));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startTimer(){
        if(!isTimer1Running){
            timerPlayer1.start();
            isTimer1Running = true;
            isTimer2Running = false;
            increaseRoundCount(1);
        }
    }
    private void stopTimer(){
        if(!isTimer2Running){
            timerPlayer2.start();
            isTimer2Running = true;
            isTimer1Running = false;
            increaseRoundCount(1);
        }
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
    public int increaseRoundCount( int roundCount) {
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
        return roundCount;
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
        private int minutes;
        private int seconds;

        public TimerActionListener(JLabel timerLabel) {
            this.timerLabel = timerLabel;
            this.minutes = 10;
            this.seconds = 0;
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
                if (isTimer1Running && minutes == 0 && seconds == 0) {
                    // Time is up, show negative message and stop the timer
                    JOptionPane.showMessageDialog(null, "Time's up! Player 2 wins.");
                    timerPlayer1.stop();
                } else if (isTimer2Running && minutes == 0 && seconds == 0) {
                    // Time is up, show negative message and stop the timer
                    JOptionPane.showMessageDialog(null, "Time's up! Player 1 wins.");
                    timerPlayer2.stop();
                } else {
                    // Update the timer label with the new time
                    timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
                }

            }
        }
    }



}
