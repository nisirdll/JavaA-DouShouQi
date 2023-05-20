package view;


import controller.GameController;
import model.Chessboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

    public class GameUI {

            public static void main(String[] args) {
                SwingUtilities.invokeLater(() -> {
                    // 创建初始界面
                    JFrame initialFrame = createFrame("Jungle");
                    addTitle(initialFrame, "Jungle");
                    JPanel buttonPanel = createButtonPanel();

                    // 创建对弈按钮
                    JButton playButton = createButton("Play");
                    playButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            showPlayScreen(initialFrame);
                        }
                    });
                    buttonPanel.add(playButton);

                    // 创建观战按钮
                    JButton watchButton = createButton("Watch");
                    watchButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            showWatchScreen(initialFrame);
                        }
                    });
                    buttonPanel.add(watchButton);

                    // 创建个人数据按钮
                    JButton dataButton = createButton("Personal Data");
                    dataButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            showDataScreen(initialFrame);
                        }
                    });
                    buttonPanel.add(dataButton);

                    // 创建退出游戏按钮
                    JButton exitButton = createButton("Exit");
                    exitButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.exit(0); // 退出游戏
                        }
                    });
                    buttonPanel.add(exitButton);

                    // 将按钮面板添加到初始界面
                    initialFrame.add(buttonPanel, BorderLayout.CENTER);

                    // 显示初始界面
                    initialFrame.setVisible(true);
                });
            }

            private static JFrame createFrame(String title) {
                JFrame frame = new JFrame(title);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(600, 900);
                frame.setLocationRelativeTo(null); // 居中显示
                return frame;
            }

            private static void addTitle(JFrame frame, String title) {
                JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
                titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
                frame.add(titleLabel, BorderLayout.NORTH);
            }

            private static JPanel createButtonPanel() {
                JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 10, 10));
                buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                return buttonPanel;
            }

            private static JButton createButton(String text) {
                JButton button = new JButton(text);
                button.setPreferredSize(new Dimension(150, 40));
                button.setFont(new Font("Arial", Font.BOLD, 16));
                button.setFocusPainted(false);
                return button;
            }

            private static void showPlayScreen(JFrame previousFrame) {
                previousFrame.dispose(); // 关闭初始界面

                JFrame playFrame = createFrame("Play");
                JPanel buttonPanel = createButtonPanel();

                // 创建匹配按钮
                JButton matchButton = createButton("Match");
                buttonPanel.add(matchButton);
                // 创建个人对战按钮
                JButton personalMatchButton = createButton("Self");
                personalMatchButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        showChessboardScreen(previousFrame);
                    }
                });
                buttonPanel.add(personalMatchButton);
                matchButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        showChessboardScreen(previousFrame);
                    }
                });
                buttonPanel.add(matchButton);




                // 创建人机按钮
                JButton aiButton = createButton("AI");
                aiButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        showChessboardScreen(previousFrame);
                    }
                });

                buttonPanel.add(aiButton);

                // 创建返回按钮
                JButton backButton = createButton("Back");
                backButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        playFrame.dispose(); // 关闭对弈界面
                        previousFrame.setVisible(true); // 显示初始界面
                    }
                });
                buttonPanel.add(backButton);

                playFrame.add(buttonPanel, BorderLayout.CENTER);
                playFrame.setVisible(true);
            }

            private static void showWatchScreen(JFrame previousFrame) {
                previousFrame.dispose(); // 关闭初始界面

                JFrame watchFrame = createFrame("Watch");
                JPanel panel = new JPanel(new BorderLayout());

                // 创建列表
                JTextArea listArea = new JTextArea();
                listArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(listArea);
                panel.add(scrollPane, BorderLayout.CENTER);

                // 创建返回按钮
                JButton backButton = createButton("Back");
                backButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        watchFrame.dispose(); // 关闭观战界面
                        previousFrame.setVisible(true); // 显示初始界面
                    }
                });
                panel.add(backButton, BorderLayout.SOUTH);

                watchFrame.add(panel);
                watchFrame.setVisible(true);
            }

            private static void showDataScreen(JFrame previousFrame) {
                previousFrame.dispose(); // 关闭初始界面

                JFrame dataFrame = createFrame("Personal Data");
                JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
                panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

                // 创建头像和用户名区域
                JPanel profilePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                ImageIcon userIcon = new ImageIcon("/img/用户.png");
                JLabel userLabel = new JLabel("UserID: 312512");
                JLabel profileLabel = new JLabel(userIcon);
                profileLabel.setPreferredSize(new Dimension(50, 50));
                profilePanel.add(profileLabel);
                profilePanel.add(userLabel);
                panel.add(profilePanel);

                // 创建等级分区域
                JPanel levelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JLabel levelLabel = new JLabel("Rate：1000");
                levelPanel.add(levelLabel);
                panel.add(levelPanel);

                // 创建对局数和获胜局数区域
                JPanel gamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JLabel gameLabel = new JLabel("All games：12   Win：7   Winner present：58.33%");
                gamePanel.add(gameLabel);
                panel.add(gamePanel);

                // 创建牌谱按钮
                JButton recordButton = createButton("Chess Record");
                panel.add(recordButton);

                // 创建按钮区域
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                buttonPanel.setOpaque(false);

                // 创建排名按钮
                JButton rankingButton = createButton("Ranking");
                buttonPanel.add(rankingButton);

                // 创建帮助按钮
                JButton helpButton = createButton("Help");
                buttonPanel.add(helpButton);
                helpButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        showHelpScreen(dataFrame); // 显示帮助界面
                    }
                });






                // 创建设置按钮
                JButton settingsButton = createButton("Settings");
                buttonPanel.add(settingsButton);

                panel.add(buttonPanel);

                // 创建返回按钮
                JButton backButton = createButton("Back");
                backButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dataFrame.dispose(); // 关闭个人数据界面
                        previousFrame.setVisible(true); // 显示初始界面
                    }
                });
                panel.add(backButton);

                dataFrame.add(panel);
                dataFrame.setVisible(true);
            }
        private static void showHelpScreen(JFrame previousFrame) {
            previousFrame.dispose(); // 关闭初始界面

            JFrame helpFrame = createFrame("Help");
            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);

            // 设置斗兽棋规则文本内容
            String rules = "斗兽棋简介：\n\n斗兽棋是一种棋类游戏，源于中国古代的传统棋类游戏之一。在斗兽棋中，两位玩家扮演动物棋子，通过策略和运气来争夺胜利。\n" +
                    "\n" +
                    "斗兽棋的棋盘是一个9x7的方形网格，每个玩家有8种不同的动物棋子，包括象、狮、虎、豹、狼、狗、猫和鼠。每个棋子都有不同的移动能力和战斗能力。\n" +
                    "\n" +
                    "棋子的移动规则是在棋盘上的格子之间进行移动，每次可以移动到相邻的空格或跳过河流（中间有河流的格子）到对岸的空格。棋子不能后退，除非是狮子或老鼠。\n" +
                    "\n" +
                    "棋子的战斗规则是根据棋子的等级来判断胜负，等级高的棋子可以吃掉等级低的棋子，但老鼠可以吃掉象。如果狮子遇到对方的象，它可以选择继续战斗或者跳过象到对岸。\n" +
                    "\n" +
                    "游戏的目标是将对方的兽王（狮子）困在陷阱中，或者吃掉对方的兽王。\n" +
                    "\n" +
                    "斗兽棋是一款策略性和推理性很强的棋类游戏，需要玩家根据棋子的移动和战斗规则制定合理的策略来获得胜利。";
            textArea.setText(rules);

            JScrollPane scrollPane = new JScrollPane(textArea);

            JButton backButton = new JButton("Back");
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    helpFrame.dispose(); // 关闭帮助界面
                    previousFrame.setVisible(true); // 显示上一个界面
                }
            });

            helpFrame.setLayout(new BorderLayout());
            helpFrame.add(scrollPane, BorderLayout.CENTER);
            helpFrame.add(backButton, BorderLayout.SOUTH);

            helpFrame.setVisible(true);
        }
        private static void showChessboardScreen(JFrame prevoiusFrame){
                prevoiusFrame.dispose();

                ChessGameFrame chessGameFrame = new ChessGameFrame(1100,800);
                new GameController(chessGameFrame.getChessboardComponent(), new Chessboard(),new Chessboard());
                chessGameFrame.setVisible(true);
        }


        public void setVisible(boolean b) {

        }
    }
