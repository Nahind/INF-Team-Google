/**
 * Created by nandane on 20/02/17.
 */
public class Pizza {

    int rows;
    int cols;
    int minOfEach;
    int maxCellsPerSlice;
    int[][] cells;

    Pizza(int r, int c, int minOfEach, int maxCellsPerSlice, int[][] cells) {
        this.rows = r;
        this.cols = c;
        this.minOfEach = minOfEach;
        this.maxCellsPerSlice = maxCellsPerSlice;
        this.cells = cells;
    }

    public void printPizza() {
        String board = "";
        for (int row = 0; row < this.cells.length; row++) {
            for (int col = 0; col < this.cells[0].length; col++) {
                board += this.cells[row][col];
            }
            board += "\n";
        }
        System.out.println(board);
    }

    public String toString() {
        String board = "";
        for (int row = 0; row < this.cells.length; row++) {
            for (int col = 0; col < this.cells[0].length; col++) {
                board += this.cells[row][col];
            }
            board += "\n";
        }
        return board;
    }
}
