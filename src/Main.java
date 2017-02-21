import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandane on 20/02/17.
 */
public class Main {

    public static void main(String[] args) {
        Solution s = new Solution();
        File testFile = new File("src/small.in");

        Pizza pizza = s.loadPizza(testFile);
        pizza.printPizza();
        int[][] cells = pizza.cells;

        List<Slice> availibleSclices = s.findAvailibleSlices(pizza.maxCellsPerSlice, cells);
        s.findValidSlices(availibleSclices, cells);

        s.solveHashCodeProblem(cells,
                pizza.maxCellsPerSlice,
                0,
                pizza.minOfEach,
                new ArrayList<Slice>(),
                cells.clone());
    }


}
