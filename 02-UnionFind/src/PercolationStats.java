import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double[] results;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("Both n and trials must be greater than 0");

        results = new double[trials];
        for (int i = 0; i < trials; i++) {
            results[i] = monteCarlo(n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(results);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - delta();
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + delta();
    }

    private double delta() {
        return 1.96 * stddev() / (Math.sqrt(results.length));
    }

    private double monteCarlo(int n) {
        Percolation percolation = new Percolation(n);
        while (!percolation.percolates()) {
            int row = 1 + StdRandom.uniform(n);
            int col = 1 + StdRandom.uniform(n);
            if (percolation.isOpen(row, col))
                continue;
            percolation.open(row, col);
        }
        return (double) percolation.numberOfOpenSites() / (n * n);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);
        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println("95% confidence interval = [" + stats.confidenceLo() + "," + stats.confidenceHi() + "]");
    }

}