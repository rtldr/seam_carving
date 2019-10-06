import java.util.concurrent.RecursiveAction;

public class FastEnergyApplier extends RecursiveAction {
    private static final int SEQUENTIAL_CUTOFF = 196;

    private int row1, col1, row2, col2;

    public FastEnergyApplier(int row1, int col1, int row2, int col2) {
        this.row1 = row1;
        this.col1 = col1;
        this.row2 = row2;
        this.col2 = col2;
    }

    protected void compute() {
        if((row2 - row1) * (col2 - col1) <= SEQUENTIAL_CUTOFF) {
            for(int i = row1; i < row2; i++) {
                for(int j = col1; j < col2; j++) {
                    Main.image[i][j] = Main.energy(i, j);
                }
            }
        } else {
            int midRow = row1 + (row2 - row1) / 2;
            int midCol = col1 + (col2 - col1) / 2;
            FastEnergyApplier topLeft = new FastEnergyApplier(row1, col1, midRow, midCol);
            FastEnergyApplier topRight = new FastEnergyApplier(row1, midCol, midRow, col2);
            FastEnergyApplier botLeft = new FastEnergyApplier(midRow, col1, row2, midCol);
            FastEnergyApplier botRight = new FastEnergyApplier(midRow, midCol, row2, col2);
            topLeft.fork();
            topRight.fork();
            botLeft.fork();

            botRight.compute();

            topLeft.join();
            topRight.join();
            botLeft.join();
        }

    }
}
