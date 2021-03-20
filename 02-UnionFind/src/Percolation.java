import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/******************************************************************************
 *  Compilation:  javac Percolation.java
 *  Execution:    java Percolation
 *
 ******************************************************************************/

public class Percolation {

    private final int n;
    private final WeightedQuickUnionUF uf;
    private boolean[] openSite;
    private int numberOfOpenSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("n must >= 1");

        this.n = n;
        openSite = new boolean[n * n];
        uf = new WeightedQuickUnionUF(n * n + 2);
        setupVirtualSites();
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        if (isOpen(row, col))
            return;

        int i = xyTo1D(col, row);
        openSite[i] = true;
        numberOfOpenSites++;

        // connect to top neighbour
        if (row > 1 && isOpen(row - 1, col))
            uf.union(i, xyTo1D(col, row - 1));

        // connect to bottom neighbour
        if (row < n && isOpen(row + 1, col))
            uf.union(i, xyTo1D(col, row + 1));

        // connect to left neighbour
        if (col > 1 && isOpen(row, col - 1))
            uf.union(i, xyTo1D(col - 1, row));

        // connect to right neighbour
        if (col < n && isOpen(row, col + 1))
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
        return isOpen(row, col) && uf.find(i) == uf.find(virtualTop());
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

        for (int i = 1; i <= n; i++) {
            int topSite = xyTo1D(i, 1);
            int botSite = xyTo1D(i, n);
            uf.union(topSite, virtualTop);
            uf.union(botSite, virtualBot);
        }
    }

    private int virtualTop() {
        return n * n;
    }

    private int virtualBottom() {
        return n * n + 1;
    }

    private int xyTo1D(int x, int y) {
        return (x - 1) + (y - 1) * n;
    }

    private void validate(int row, int col) {
        if (row <= 0 || row > n || col <= 0 || col > n)
            throw new IllegalArgumentException("row and col values must be between [1, n]");
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        if (n <= 0)
            throw new IllegalArgumentException("n must >= 1");

        Percolation percolation = new Percolation(n);
        // while (!StdIn.isEmpty()) {
        //     int row = StdIn.readInt();
        //     int col = StdIn.readInt();
        //     if (percolation.isOpen(row, col))
        //         continue;
        //     percolation.open(row, col);
        //     percolation.isOpen(row, col);
        //     percolation.isFull(row, col);
        // }

        // StdOut.println(percolation.percolates());

        StdOut.println(percolation.percolates());
        StdOut.println(percolation.percolates());
        StdOut.println(percolation.percolates());
        StdOut.println(percolation.percolates());
        StdOut.println(percolation.percolates());
        percolation.open(1, 3);
        StdOut.println(percolation.percolates());
        StdOut.println(percolation.percolates());
        percolation.open(2, 1);
        percolation.open(3, 3);
        StdOut.println(percolation.percolates());
        percolation.open(3, 2);
        percolation.open(2, 2);
        percolation.open(1, 2);
        StdOut.println(percolation.percolates());
        // [ xx]
        // [xx ]
        // [ xx]
    }
}
