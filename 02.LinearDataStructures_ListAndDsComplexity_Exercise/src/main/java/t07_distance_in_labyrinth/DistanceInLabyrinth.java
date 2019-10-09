package t07_distance_in_labyrinth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class DistanceInLabyrinth {
    public static void main(String[] args) throws IOException {
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

        int numberOfRows = Integer.parseInt(consoleReader.readLine());
        String[] stringRows = new String[numberOfRows];
        for (int i = 0; i < numberOfRows; i++)
            stringRows[i] = consoleReader.readLine();

        consoleReader.close();

        String[][] labyrinth = buildLabyrinth(stringRows, "");
        int[] starCoordinates = getCoordinates(labyrinth, "*");

        addShortestDistancesToCells(labyrinth, starCoordinates);

        System.out.println(getMatrixString(labyrinth, ""));
    }

    public static String[][] buildLabyrinth(String[] stringRows, String regexDelimiter) {
        if (stringRows == null)
            return null;

        if (stringRows.length == 0)
            return new String[0][0];

        int cols = stringRows[0].split(regexDelimiter).length;

        String[][] result = new String[stringRows.length][cols];

        for (int row = 0; row < stringRows.length; row++) {
            String[] rowTokens = stringRows[row].split(regexDelimiter);

            for (int col = 0; col < cols; col++)
                result[row][col] = rowTokens[col];
        }

        return result;
    }

    private static int[] getCoordinates(Object[][] matrix, Object item) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                if (matrix[row][col].equals(item)) {
                    return new int[]{row, col};
                }
            }
        }

        return null;
    }

    public static String getMatrixString(Object[][] matrix, String separator) {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length - 1; col++)
                sb.append(matrix[row][col].toString()).append(separator);

            sb.append(matrix[row][matrix[0].length - 1]).append(System.lineSeparator());
        }

        return sb.toString().trim();
    }

    private static boolean outOfBounds(Object[][] matrix, int row, int col) {
        return row < 0 || row >= matrix.length || col < 0 || col >= matrix[0].length;
    }

    private static boolean validCell(Object[][] matrix, int row, int col, Object indicator) {
        if (!outOfBounds(matrix, row, col) && matrix[row][col].equals(indicator))
            return true;
         else
            return false;
    }

    private static void replaceValuesInMatrix(Object[][] matrix, Object value, Object replacement) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j].equals(value)) {
                    matrix[i][j] = replacement;
                }
            }
        }
    }

    private static class Cell {
        private int row;
        private int col;
        private int distance;

        public Cell(int row, int col, int distance) {
            this.row = row;
            this.col = col;
            this.distance = distance;
        }
    }

    /**
     * Replaces all possible zeros with shortest distance from starting coordinates. If a zero
     * can't be reached it is replaced with a "u".
     *
     * @param labyrinth (N x N) matrix
     * @param startingCoordinates array with first element - row and second element - column
     */
    public static void addShortestDistancesToCells(Object[][] labyrinth, int[] startingCoordinates) {
        Queue<Cell> cellsQueue = new ArrayDeque<>();
        cellsQueue.add(new Cell(startingCoordinates[0], startingCoordinates[1], 0));

        while (!cellsQueue.isEmpty()) {
            Cell currentCell = cellsQueue.poll();

            if (validCell(labyrinth, currentCell.row + 1, currentCell.col, "0")) {
                cellsQueue.add(new Cell(
                        currentCell.row + 1,
                        currentCell.col,
                        currentCell.distance + 1));
                labyrinth[currentCell.row + 1][currentCell.col] = currentCell.distance + 1 + "";
            }

            if (validCell(labyrinth, currentCell.row - 1, currentCell.col, "0")) {
                cellsQueue.add(new Cell(
                        currentCell.row - 1,
                        currentCell.col,
                        currentCell.distance + 1
                ));
                labyrinth[currentCell.row - 1][currentCell.col] = currentCell.distance + 1 + "";
            }

            if (validCell(labyrinth, currentCell.row, currentCell.col + 1, "0")) {
                cellsQueue.add(new Cell(
                        currentCell.row,
                        currentCell.col + 1,
                        currentCell.distance + 1
                ));
                labyrinth[currentCell.row][currentCell.col + 1] = currentCell.distance + 1 + "";
            }

            if (validCell(labyrinth, currentCell.row, currentCell.col - 1, "0")) {
                cellsQueue.add(new Cell(
                        currentCell.row,
                        currentCell.col - 1,
                        currentCell.distance + 1
                ));
                labyrinth[currentCell.row][currentCell.col - 1] = currentCell.distance + 1 + "";
            }
        }

        replaceValuesInMatrix(labyrinth, "0", "u");
    }
}
