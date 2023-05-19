package controller;



import model.Chessboard;

import java.util.Random;

public class ChessAI {
    private static final int MODE_EASY = 0;
    private static final int MODE_MEDIUM = 1;
    private static final int MODE_HARD = 2;

    private int difficultyMode;
    private Random random;

    public ChessAI(int difficultyMode) {
        this.difficultyMode = difficultyMode;
        this.random = new Random();
    }

    public void makeMove(Chessboard chessboard) {
        switch (difficultyMode) {
            case MODE_EASY:
                makeRandomMove(chessboard);
                break;
            case MODE_MEDIUM:
                makeMediumMove(chessboard);
                break;
            case MODE_HARD:
                makeHardMove(chessboard);
                break;
            default:
                makeRandomMove(chessboard);
                break;
        }
    }

    private void makeRandomMove(Chessboard chessboard) {
        // Get all valid moves for the AI player
        // Select a random move from the valid moves
        // Make the selected move on the chessboard
    }

    private void makeMediumMove(Chessboard chessboard) {
        // Implement medium difficulty move logic
    }

    private void makeHardMove(Chessboard chessboard) {
        // Implement hard difficulty move logic
    }
}
