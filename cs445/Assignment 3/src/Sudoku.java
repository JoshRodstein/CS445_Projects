package cs445.a3;

import java.util.List;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Sudoku {
    static boolean isFullSolution(int[][] board) {
        for(int i = 8; i >= 0; i--){
            for(int j = 8; j >= 0; j--){
                if(board[i][j] == 0){
                    return false;
                }
            }
        }
        
        System.out.println("is Full Solution");
        return true;
    }

    static boolean reject(int[][] board, int [][] reference) throws NumberFormatException {
        for(int i = 8; i >= 0; i--){
            for(int j = 8; j >= 0; j--){
                if(board[i][j] > 0 && board[i][j] < 10) {
                    if(checkRow(board, i , j) || checkColumn(board, i, j) || checkRegion(board, i, j)){
                        System.out.println("rejected");
                        return true;
                    }
                } else if(board[i][j] != 0) {
                    throw new NumberFormatException("Invalid Board: Element at index ["+i+"]["+j+"] out of range.");
                }
            }
        }
    return false;
    }

    static int[][] extend(int[][] board, int[][] reference) {
        System.out.println("REACHED extend");
        int[][] extSoln = new int[board[0].length][];
        for(int i = 0; i < extSoln.length; i++){
            extSoln[i] = Arrays.copyOf(board[i], board[i].length);
        }
        for (int i = 0; i <extSoln[i].length; i++) {
            for (int j = 0; j < extSoln.length; j++) {
                if ((extSoln[i][j] == 0 )) {
                    extSoln[i][j] += 1;
                    reference[i][j] = -1;
                    return extSoln;
                }
            }
        }
        return null;
    }

    static int[][] next(int[][] board, int[][] reference) {
        System.out.println("REACHED NEXT");
        for (int i = 8; i >= 0; i--) {
            for (int j = 8; j >= 0; j--) {
                if(board[i][j] == 9 && reference[i][j] == -1){
                    reference[i][j] = 0;
                    System.out.println("nullin out");
                    return null;
                }
                if ((reference[i][j] == -1) && (board[i][j] > 0)) {
                    board[i][j] += 1;
                    return board;
                } 
            }
        }
        return null;
    }

    static void testIsFullSolution() {
        // TODO: Complete this method
    }

    static void testReject() {
        // TODO: Complete this method
    }

    static void testExtend() {
        
    }

    static void testNext() {
        // TODO: Complete this method
    }

    static void printBoard(int[][] board) {
        if (board == null) {
            System.out.println("No assignment");
            return;
        }
        for (int i = 0; i < 9; i++) {
            if (i == 3 || i == 6) {
                System.out.println("----+-----+----");
            }
            for (int j = 0; j < 9; j++) {
                if (j == 2 || j == 5) {
                    System.out.print(board[i][j] + " | ");
                } else {
                    System.out.print(board[i][j]);
                }
            }
            System.out.print("\n");
        }
    }

    static int[][] readBoard(String filename) {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(filename), Charset.defaultCharset());
        } catch (IOException e) {
            return null;
        }
        int[][] board = new int[9][9];
        int val = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                try {
                    val = Integer.parseInt(Character.toString(lines.get(i).charAt(j)));
                } catch (Exception e) {
                    val = 0;
                }
                board[i][j] = val;
            }
        }
        return board;
    }

    static int[][] solve(int[][] board, int[][] reference) {
        if (reject(board, reference)) 
            return null;
        if (isFullSolution(board)) 
            return board;
        int[][] attempt = extend(board, reference);
        while (attempt != null) {
            int[][] solution = solve(attempt, reference);
            if (solution != null) return solution;
            attempt = next(attempt, reference);
        }
        return null;
    }

    public static void main(String[] args) {
        if (args[0].equals("-t")) {
            testIsFullSolution();
            testReject();
            testExtend();
            testNext();
        } else {
            int[][] board = readBoard(args[0]);
            int[][] reference = readBoard(args[0]);
            long startTime = System.currentTimeMillis();
            printBoard(solve(board, reference));
            long endTime = System.currentTimeMillis();
            System.out.println("That took " + (endTime - startTime)/1000 + " seconds");
            System.out.println("Final Reference Board");
            printBoard(reference);
            printBoard(readBoard(args[0]));
        }
    }

    private static boolean checkRow(int[][] board, int x, int y) {
        for (int i = 0; i < 9; i++) {
            if ((i != y) && (board[x][i] != 0) && (board[x][y] == board[x][i])){
                return true; // true == reject
            }
        }
        return false;
    }

    private static boolean checkColumn(int[][] board, int x, int y) {
        for (int i = 0; i < 9; i++) {
            if ((i != x) && (board[i][y] == board[x][y]) && (board[i][y] != 0)) {
                return true; //true == reject
            }
        }
        return false;
    }

    private static boolean checkRegion(int[][] board, int x, int y) {
        int row = (x/3)*3;
        int column = (y/3)*3;

        for(int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if ((board[i+row][j+column] != 0) && (board[i+row][j+column] == board[x][y]) && 
                    (x != (i+row) && y != (j+column))){
                    return true;
                }
            }
        }
        return false;
    }
}
