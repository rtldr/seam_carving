import java.util.concurrent.RecursiveAction;

public class EnergyApplier extends RecursiveAction {
    private static final int SEQUENTIAL_CUTOFF = 100;

    private int row, low, hi;

    public EnergyApplier(int row, int low, int hi) {
        this.row = row;
        this.low = low;
        this.hi = hi;
    }

    protected void compute() {
        if(hi - low <= SEQUENTIAL_CUTOFF) {
            for(int i = low; i < hi; i++) {
                Main.image[row][i] = Main.energy(row, i);
            }
        } else {
            int mid = low + (hi - low) / 2;
            EnergyApplier left = new EnergyApplier(row, low, mid);
            EnergyApplier right = new EnergyApplier(row, mid, hi);
            right.fork();
            left.compute();
            right.join();
        }

    }
}
