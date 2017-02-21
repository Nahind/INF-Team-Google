import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandane on 20/02/17.
 */
public class Solution {

    Pizza pizza;

    public Pizza loadPizza(File pizzaFile) {
        int[][] cells = new int[0][0];
        int rows = 0;
        int cols = 0;
        int minOfEach = 0;
        int maxCells = 0;
        int counter = 0;

        try {
            FileInputStream fStream = new FileInputStream(pizzaFile);
            BufferedReader in = new BufferedReader(new InputStreamReader(fStream));
            boolean firstline = true;

            while (in.ready()) {
                String line = in.readLine();
                if (firstline) {
                    String[] data = line.split(" ");
                    rows = Integer.parseInt(data[0]);
                    cols = Integer.parseInt(data[1]);
                    minOfEach = Integer.parseInt(data[2]);
                    maxCells = Integer.parseInt(data[3]);
                    cells = new int[rows][cols];
                    firstline = false;
                } else {
                    char[] data = line.toCharArray();
                    for (int k = 0; k < data.length; k++) {
                        int val;
                        if (data[k] == 'T') val = 0;
                        else val = 1;
                        cells[counter][k] = val;
                    }
                    counter++;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Pizza(rows, cols, minOfEach, maxCells, cells);
    }

    public int computeTotalCells(List<Slice> slices) {
        int sum = 0;

        for (Slice s : slices) {
            int rows = Math.max(s.r2 - s.r1, 1);
            int cols = Math.max(s.c2 - s.c1, 1);
            sum += rows * cols;
        }

        return sum;
    }

    public List<Slice> findAvailibleSlices(int sliceSize, int[][] cells) {
        List<Slice> slices = new ArrayList<>();
        int sliceRows;
        int sliceCols;
        int count = 0;
        for (int div = sliceSize; div >= 1; div--) {

            // System.out.println("div = " + div);
            if (sliceSize % div == 0) {

                sliceCols = div;
                sliceRows = sliceSize / div;

                // System.out.println("rows = " + sliceRows + " - cols = " + sliceCols);

                for (int c1 = 0; c1 <= cells[0].length - sliceCols; c1++) {
                    int c2 = c1 + sliceCols - 1;
                    // System.out.println(c1 + " c " + c2);
                    for (int r1 = 0; r1 <= cells.length - sliceRows; r1++) {
                        int r2 = r1 + sliceRows - 1;
                        // System.out.println(r1 + " r " + r2);
                        slices.add(new Slice(r1, r2, r1, c2));
                        count++;
                        // System.out.println("count = " + count);
                    }
                }
            }
        }

        // System.out.println("availible sclices count = " + slices.size());
        return slices;
    }

    public List<Slice> findValidSlices(List<Slice> slices, int[][] cells) {

        for (int k = 0; k <slices.size(); k++) {
            Slice s = slices.get(k);
            boolean isCut = false;
            boolean hasT = false;
            boolean hasM = false;
            for (int i = s.r1; i <= s.r2; i++) {
                for (int j = s.c1; j <= s.c2; j++) {
                    if (hasM && hasT) break;
                    // 1 --> Mushroom
                    // 0 --> Tomato
                    // 2 --> Slice is cut
                    int ingredient = cells[i][j];
                    if (ingredient == 2) {
                        isCut = true;
                        break;
                    }
                    if (!hasM && ingredient == 1) hasM = true;
                    if (!hasT && ingredient == 0) hasT = true;
                }
                if (isCut) break;
            }
            if (!hasM || !hasT || isCut) slices.remove(k);
        }

        System.out.println("valid slices count = " + slices.size());
        return slices;
    }

    public int[][] cutPizza(int[][] cells, Slice slice) {

        for (int i = slice.r1; i <= slice.r2; i++) {
            for (int j = slice.c1; j <= slice.c2; j++) {
                cells[i][j] = 2;
            }
        }
        return cells;
    }

    public int[][] cutBackPizza(int[][] cells, Slice slice, int[][] originalCells) {

        for (int i = slice.r1; i <= slice.r2; i++) {
            for (int j = slice.c1; j <= slice.c2; j++) {
                cells[i][j] = originalCells[i][j];
            }
        }
        return cells;
    }

    public Boolean solveHashCodeProblem(
            int[][] pizza,
            int sliceSize,
            int totalCells,
            int minOfEach,
            List<Slice> cutSclices,
            int[][] originalPizza
    ) {

        String board = "";
        for (int row = 0; row < pizza.length; row++) {
            for (int col = 0; col < pizza[0].length; col++) {
                board += pizza[row][col];
            }
            board += "\n";
        }
        System.out.println(board);

        // Condition d'arrêt
        if (sliceSize < minOfEach*2) {
            return false;
        }

        // List availible Slices
        List<Slice> availibleSlices = findAvailibleSlices(sliceSize, pizza);

        // List valid Slices
        List<Slice> validSlices = findValidSlices(availibleSlices, pizza);

        // If no slices is valid, we might have not did the right cut
        if (validSlices.size() == 0) {

            int newTotal = computeTotalCells(cutSclices);

            // If the newTotal is higher than the last one, we update it
            if (newTotal > totalCells)
            {
                totalCells = newTotal;
            }

            solveHashCodeProblem(
                    pizza, sliceSize - 1, totalCells, minOfEach, cutSclices, originalPizza
            );
        }

        // If there is some valid slices, we cut them out
        for (Slice s : validSlices) {
            cutPizza(pizza, s);

            if(!solveHashCodeProblem(pizza, sliceSize, totalCells, minOfEach, cutSclices, originalPizza)) {
                cutBackPizza(pizza, s, originalPizza);

            } else {
                return true;
            }
        }

        System.out.println("Total = " + totalCells);
        return false;
    }
}
