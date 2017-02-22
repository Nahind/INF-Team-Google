import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nandane on 20/02/17.
 */
public class Solution {

    Pizza pizza;
    public  Map<Integer, List<Slice>> allAvailibleSlices;
    public int totalCellsInSlices = 0;
    public List<Slice> finalCut;
    int pizzaSize;
    int iterationCount = 0;

    Solution(Pizza p) {
        this.pizza = p;
        allAvailibleSlices = findAllAvailibleSlices(
                this.pizza.cells, this.pizza.minOfEach, this.pizza.maxCellsPerSlice);
        pizzaSize = p.cells[0].length * p.cells.length;
    }

    public void printCells(int[][] cells) {
        String board = "";
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[0].length; col++) {
                board += cells[row][col];
            }
            board += "\n";
        }
        System.out.println(board);
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
                        slices.add(new Slice(r1, c1, r2, c2));
                        count++;
                        // System.out.println("count = " + count);
                    }
                }
            }
        }

        // System.out.println("availible sclices count = " + slices.size());
        return slices;
    }

    public Map<Integer, List<Slice>> findAllAvailibleSlices(int[][] pizza, int minOfEach, int maxSize) {
        HashMap<Integer, List<Slice>> allAvailibleSlices = new HashMap<>();

        for (int i = minOfEach*2; i <= maxSize; i++) {
            List<Slice> slices = findAvailibleSlices(i, pizza);
            allAvailibleSlices.put(i, slices);
        }

        return allAvailibleSlices;
    }

    public List<Slice> findValidSlices(List<Slice> slices, int[][] cells) {
        List<Slice> validSlices = new ArrayList<>();

        for (int k = 0; k <slices.size(); k++) {
            Slice s = slices.get(k);
            boolean isCut = false;
            boolean hasT = false;
            boolean hasM = false;
            for (int i = s.r1; i <= s.r2; i++) {
                for (int j = s.c1; j <= s.c2; j++) {
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
            if (hasM && hasT && !isCut) {
                validSlices.add(s);
            }
        }

        return validSlices;
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

    public int calcTotalCutSlices(int[][] cells) {
        int total = 0;

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {

                if (cells[i][j] == 2) total++;
            }
        }

        return total;
    }

    public Boolean solveHashCodeProblem(int[][] currentPizza, int sliceSize, List<Slice> cutSlices)
    {
        this.iterationCount++;
        System.out.println("iteration count = " + this.iterationCount);
        // System.out.println("slice size checking = " + sliceSize);

        // Condition d'arrêt
        if (sliceSize < this.pizza.minOfEach*2) {
            return false;
        }

        if (totalCellsInSlices == pizzaSize) return true;

        // List availible Slices
        List<Slice> availibleSlices = this.allAvailibleSlices.get(sliceSize);

        // List valid Slices
        List<Slice> validSlices = findValidSlices(availibleSlices, currentPizza);

        // System.out.println("valid slices count = " + validSlices.size());
        // System.out.println("slices list len = " + cutSlices.size());

        // If no slices is valid, we might have not did the right cut
        if (validSlices.size() == 0) {

            int newTotal = calcTotalCutSlices(currentPizza);

            // If the newTotal is higher than the last one, we update it
            if (newTotal > totalCellsInSlices)
            {
                totalCellsInSlices = newTotal;
                ArrayList<Slice> helper = new ArrayList<>();
                helper.addAll(cutSlices);
                finalCut = helper;
            }

            solveHashCodeProblem(
                    currentPizza, sliceSize - 1, cutSlices
            );
        }

        // If there is some valid slices, we cut them out
        for (int k = 0; k < validSlices.size(); k++) {

            Slice s = validSlices.get(k);
            cutPizza(currentPizza, s);
            cutSlices.add(s);
            // System.out.println("cut ");

            if(!solveHashCodeProblem(currentPizza, sliceSize, cutSlices)) {
                cutBackPizza(currentPizza, s, this.pizza.cells);
                cutSlices.remove(s);
                // System.out.println("cut BACK");

            } else {
                return true;
            }
        }
        // System.out.println("slices array length = " + cutSlices.size());
        // System.out.println("Total = " + totalCellsInSlices);
        return false;
    }
}
