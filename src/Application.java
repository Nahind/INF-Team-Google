/*
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

*/
/**
 * Created by nandane on 21/02/17.
 *//*

public class Application {

    public static void main(String[] args) throws FileNotFoundException {
        Solution s = new Solution();
        File testFile = new File(args[0]);
        //System.setOut(new PrintStream(new File("src/out")));
        Pizza pizza = s.loadPizza(testFile);
        pizza.printPizza();
        int[][] cells = pizza.cells;
        int [][] newCells = new int[cells.length][];
        for(int i = 0; i < cells.length; i++)
            newCells[i] = cells[i].clone();
        List<Slice> availibleSclices = s.findAvailibleSlices(pizza.maxCellsPerSlice, cells);
        s.findValidSlices(availibleSclices, cells);
        System.out.println("find all availible slices combinaison");
        Map<Integer, List<Slice>> allAvailibleSlices =  s.findAllAvailibleSlices(pizza.cells, pizza.minOfEach, pizza.maxCellsPerSlice);


        s.solveHashCodeProblem(
                cells,
                pizza.maxCellsPerSlice,
                pizza.minOfEach,
                new ArrayList<Slice>(),
                newCells);

        System.out.println("Finished");
        System.out.println("Total of cells = " + s.totalCellsInSlices);
        System.out.println("Best Slices combinaison : ");
        for (Slice slice : s.finalCut) {
            System.out.println(slice.r1 + " " + slice.c1 + " " + slice.r2 + " " + slice.c2);
        }
    }
}
*/
