import java.io.*;

/**
 * Created by nandane on 22/02/17.
 */
public class Helper {

    public static Pizza loadPizza(File pizzaFile) {
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
}
