package com.example.fwd;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicTacToe {

    private char[][] board;
    private int size;
    private char currentPlayer;

    public TicTacToe(int size) {
        this.size = size;
        board = new char[size][size];
        currentPlayer = 'X';
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = ' ';
            }
        }
    }

    public char[][] getBoard() {
        return board;
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean makeMove(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size || board[row][col] != ' ') {
            return false; // Invalid move
        }

        board[row][col] = currentPlayer;
        if (checkWin(row, col)) {
            return true; // Player wins
        }

        if (checkDraw()) {
            // Handle a draw if the board is full
            initializeBoard();
        } else {
            switchPlayer();
        }

        return false; // Move successful
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    private boolean checkWin(int lastMoveRow, int lastMoveCol) {
        
        if (checkLine(board[lastMoveRow])) {
            return true;
        }

        if (checkLine(getColumn(lastMoveCol))) {
            return true;
        }

        if (lastMoveRow == lastMoveCol || lastMoveRow + lastMoveCol == size - 1) {
            if (lastMoveRow == lastMoveCol && checkLine(getDiagonal(true))) {
                return true;
            }

            if (lastMoveRow + lastMoveCol == size - 1 && checkLine(getDiagonal(false))) {
                return true;
            }
        }

        return false;
    }

    private char[] getColumn(int col) {
        char[] column = new char[size];
        for (int i = 0; i < size; i++) {
            column[i] = board[i][col];
        }
        return column;
    }

    private char[] getDiagonal(boolean main) {
        char[] diagonal = new char[size];
        for (int i = 0; i < size; i++) {
            diagonal[i] = main ? board[i][i] : board[i][size - 1 - i];
        }
        return diagonal;
    }

    private boolean checkLine(char[] line) {
        for (char c : line) {
            if (c != currentPlayer) {
                return false;
            }
        }
        return true;
    }

    private boolean checkDraw() {
        // Check if the board is full (draw)
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == ' ') {
                    return false; // There is an empty space, not a draw yet
                }
            }
        }
        // Board is full, it's a draw
        initializeBoard();
        return true;
    }
}