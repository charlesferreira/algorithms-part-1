import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/******************************************************************************
 *  Compilation:  javac Percolation.java
 *  Execution:    java Percolation
 *
 ******************************************************************************/

public class Percolation {

    private int gridSize;
    private boolean[] openSite;
    private int numberOfOpenSites;
    private WeightedQuickUnionUF uf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        gridSize = n;
        openSite = new boolean[n * n];
        uf = new WeightedQuickUnionUF(n * n + 2);
        setupVirtualSites();
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        int i = xyTo1D(col, row);
        openSite[i] = true;
        numberOfOpenSites++;

        // connect to top neighbour
        if (row > 1 && isOpen(col, row - 1))
            uf.union(i, xyTo1D(col, row - 1));

        // connect to bottom neighbour
        if (row < gridSize && isOpen(col, row + 1))
            uf.union(i, xyTo1D(col, row + 1));

        // connect to left neighbour
        if (col > 1 && isOpen(col - 1, row))
            uf.union(i, xyTo1D(col - 1, row));

        // connect to right neighbour
        if (col < gridSize && isOpen(col + 1, row))
            uf.union(i, xyTo1D(col + 1, row));
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        int i = xyTo1D(col, row);
        return openSite[i];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        int i = xyTo1D(col, row);
        return uf.find(i) == uf.find(virtualTop());
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(virtualTop()) == uf.find(virtualBottom());
    }

    private void setupVirtualSites() {
        int virtualTop = this.virtualTop();
        int virtualBot = this.virtualBottom();

        for (int i = 1; i <= gridSize; i++) {
            int topSite = xyTo1D(i, 1);
            int botSite = xyTo1D(i, gridSize);
            uf.union(topSite, virtualTop);
            uf.union(botSite, virtualBot);
        }
    }

    private int virtualTop() {
        return gridSize * gridSize;
    }

    private int virtualBottom() {
        return gridSize * gridSize + 1;
    }

    private int xyTo1D(int x, int y) {
        return (x - 1) + (y - 1) * gridSize;
    }

    private void validate(int row, int col) {
        int p = xyTo1D(col, row);
        if (p < 0 || p >= gridSize * gridSize)
            throw new IllegalArgumentException("Row and col must be between [1, n]");
    }

    public static void main(String[] args) throws Exception {
        Percolation percolation = new Percolation(StdIn.readInt());

        while (!StdIn.isEmpty()) {
            int row = StdIn.readInt();
            int col = StdIn.readInt();
            if (percolation.isOpen(row, col))
                continue;
            percolation.open(row, col);
        }

        StdOut.println(percolation.uf.count());
        StdOut.println(percolation.percolates());

    }
}
