package com.karthi.tictactoe;

import android.util.Log;

public class Board {

    public static final int PLAYER_1 = 1;
    public static final int PLAYER_2 = 2;

    private final String xResult, oResult;
    private final int maxAction;
    private final String value[][];
    private int actionCount = 0;

    public Board(int row, int column) {
        this.value = new String[row][column];
        this.xResult = prepareResultTxt("X", row);
        this.oResult = prepareResultTxt("O", row);
        maxAction = row * column;
    }

    public String[][] getValue() {
        return value;
    }

    public int getActionCount() {
        return actionCount;
    }

    public void incrementActionCount() {
        this.actionCount++;
    }

    public String getCurrentPlayer() {
        return (actionCount % 2 > 0) ? "X" : "O";
    }

    public void setValueAt(int rowIndex, int columnIndex, String input) {
        this.value[rowIndex][columnIndex] = input;
    }

    public int isSolved() {
        int row = checkRow();

        if (row > 0) {
            return row;
        }

        int col = checkColumn();

        if (col > 0) {
            return col;
        }

        int diag = checkDiagonalLeft2Right();

        if (diag > 0) {
            return diag;
        }

        int diag2 = checkDiagonalRight2Left();

        if (diag2 > 0) {
            return diag2;
        }

        if (actionCount == maxAction) {
            return 3;
        }

        return 4;
    }

    private int checkDiagonalRight2Left() {
        Log.d("Board", "checkDiagonalRight2Left..");
        String row = "";
        int result = -1;

        for (int i = 0; i < value.length; i++) {
            for (int j = 0; j < value.length; j++) {
                int reqPosition = value.length - i - 1;
                if (reqPosition == j) {
                    row += value[i][j] != null ? value[i][j] : "";
                }
            }
        }
        result = hasMatch(row);
        return result;
    }

    private int checkDiagonalLeft2Right() {
        Log.d("Board", "checkDiagonalLeft2Right..");

        String row = "";
        int result = -1;

        for (int i = 0; i < value.length; i++) {
            for (int j = 0; j < value.length; j++) {
                if (i == j) {
                    row += value[i][j] != null ? value[i][j] : "";
                }
            }
            result = hasMatch(row);
            if (result > 0) {
                break;
            }
        }

        return result;
    }

    private int checkRow() {

        Log.d("Board", "checkRow..");

        int result = -1;
        for (int i = 0; i < value.length; i++) {
            String row = "";
            for (int j = 0; j < value.length; j++) {
                row += value[i][j] != null ? value[i][j] : "";
            }
            result = hasMatch(row);
            if (result > 0) {
                break;
            }
        }
        return result;
    }

    private int checkColumn() {

        Log.d("Board", "checkColumn..");

        int result = -1;
        for (int i = 0; i < value.length; i++) {
            String column = "";
            for (int j = 0; j < value.length; j++) {
                column += value[j][i] == null ? "" : value[j][i];
            }
            result = hasMatch(column);
            if (result > 0) {
                break;
            }
        }
        return result;
    }

    private int hasMatch(String row) {
        Log.d("Board", "hasMatch: " + row);

        int player = -1;
        if (row.equals(xResult)) {
            player = PLAYER_1;
        } else if (row.equals(oResult)) {
            player = PLAYER_2;
        }
        return player;
    }

    private String prepareResultTxt(String key, int size) {
        String result = "";
        for (int i = 0; i < size; i++) {
            result += key;
        }
        return result;
    }


}
