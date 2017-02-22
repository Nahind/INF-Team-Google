import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by nandane on 20/02/17.
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        File testFile = new File("src/small.in");
        Pizza pizza = Helper.loadPizza(testFile);
        Solution s = new Solution(pizza);

        int[][] cells = pizza.cells;
        int [][] newCells = new int[cells.length][];
        for(int i = 0; i < cells.length; i++)
            newCells[i] = cells[i].clone();

        s.solveHashCodeProblem(
                newCells,
                pizza.maxCellsPerSlice,
                new ArrayList<Slice>()
        );

        System.out.println("Finished");
        System.out.println("Total of cells = " + s.totalCellsInSlices);
        System.out.println("Best Slices combinaison : ");
        for (Slice slice : s.finalCut) {
            System.out.println(slice.r1 + " " + slice.c1 + " " + slice.r2 + " " + slice.c2);
        }
    }


}