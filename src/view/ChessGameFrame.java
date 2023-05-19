package view;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
/**
 * This class represents the entire game interface during the game, and is the carrier of everything
 * 这个类是设计游戏界面的主要区域，包括棋盘，棋子，按钮等等
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    //    public final int WIDTH = 1100;
    private final int HEIGHT;
    //    public final int HEIGHT = 810;

    private final int ONE_CHESS_SIZE;
    //    public final int ONE_CHESS_SIZE = (HEIGHT * 4 / 5) / 9;

    private ChessboardComponent chessboardComponent;
    //    private ChessboardComponent chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE);

   //
    public ChessGameFrame(int width, int height) {
        setTitle("这就是自信队-斗兽棋"); //设置标题
        this.WIDTH = width; //设置窗体大小
        this.HEIGHT = height; //设置窗体大小
        this.ONE_CHESS_SIZE = (HEIGHT * 4 / 5) / 9;  //设置棋子大小

        setSize(WIDTH, HEIGHT);  //设置窗体大小
        setLocationRelativeTo(null); // Center the window.  //设置窗体居中
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);  //设置布局管理器为空


        addChessboard();  //在游戏面板中添加棋盘
        addLabel();  //在游戏面板中添加标签
        addHelloButton();  //在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
        addSingInButton();  //按下弹出登录界面
        playMusic();  //播放音乐
        addToggleMusicButton();  // 添加停止音乐的按钮
    }
    //

    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }

    public void setChessboardComponent(ChessboardComponent chessboardComponent) {
        this.chessboardComponent = chessboardComponent;

    }

    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE);
        chessboardComponent.setLocation(HEIGHT / 5, HEIGHT / 10);
        add(chessboardComponent);

    }

    /**
     * 在游戏面板中添加标签
     */
    private void addLabel() {
        JLabel statusLabel = new JLabel("try");
        statusLabel.setLocation(HEIGHT, HEIGHT / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addHelloButton() {
        JButton button = new JButton("Back");
        button.addActionListener((e) -> {
            System.out.println("Click Back");
            new GameUI().setVisible(true);
            dispose();
        });
        button.setLocation(HEIGHT, HEIGHT / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }
    // 创建返回按钮


    // 在ChessGameFrame类中添加以下方法
    private Clip clip;  // 用于控制音乐播放
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
        JButton button = new JButton("Toggle Music");
        button.addActionListener((e) -> {
            toggleMusic();
        });
        button.setLocation(WIDTH-220, 10);
        button.setSize(200, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 14));
        add(button);
    }









    private void addSingInButton() {
        JButton button = new JButton("Sign In");
        button.setLocation(HEIGHT, HEIGHT / 10 + 180);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click Sign In");
//            new SignInFrame();
        });
    }
    JLabel timerLabel = new JLabel("00:00");

    Timer timer = new Timer(1000, new ActionListener() {
        int minutes = 10;
        int seconds = 30;
        @Override
        public void actionPerformed(ActionEvent e) {
            seconds--;
            if (seconds == 0) {
                minutes--;
                seconds = 59;
            }
            if (minutes == 0 && seconds == 0) {
                timer.stop();
            }
            timerLabel.setText(String.format("%02d:%02d", minutes, seconds));

        }
    });

    public void startTimer() {
        timer.start();
    }
//    add(timerLabel, BorderLayout.NORTH);


}
