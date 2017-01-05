import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/*---------------------------------------------------------
 * Author : Up_ding
 * Written : 5/12/2016
 * Last updated: 5/12/2016
 *
 * Compilation: javac PercolationStats.java
 * Execution: java PercolationStats parameter1 parameter2
 *
 * The implementation of PercolationStats, which uses the Monte Carlo simulation to
 * evaluate the percolation probability
 * The API of the class as follow:
 *
 *
 * public PercolationStats(int n, int trials);
 * public double mean();
 * public double stddev();
 * public double confidenceLo();
 * public double confidenceHi();
 *
 *----------------------------------------------------------*/
public class PercolationStats {
    private int size; // the size of the n-by-n grid
    private int trials; // the time of the experiment
    private double[] mean; // the array to calculate mean and stddev

    // initialization of the PercolationStats
    public PercolationStats(int n, int times) {
        if (n <= 0 || times <= 0) throw new IllegalArgumentException("Invalid input argument");
        size = n;
        trials = times;
        mean = new double[trials];
        for (int i = 0; i < trials; i++) {
            int countOpensite = 0;
            double meanNum = 0.0;
            Percolation pc = new Percolation(size);
            while (!pc.percolates()) {
                int row = StdRandom.uniform(1, size + 1);
                int col = StdRandom.uniform(1, size + 1);
                if (!pc.isOpen(row, col)) {
                    pc.open(row, col);
                    countOpensite++;
                }
            }
            meanNum = (double) countOpensite / (size*size);
            mean[i] = meanNum;
        }

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(mean);
    }

    // sample standard deviation of percolation threhold
    public double stddev() {
        return StdStats.stddev(mean);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((1.96*stddev())/Math.sqrt(trials));
    }

    // high endpoint of 95% confience interval
    public double confidenceHi() {
        return mean() + ((1.96*stddev())/Math.sqrt(trials));
    }

    public static void main(String[] args) {
        if (args.length != 2) throw new IllegalArgumentException("Invalidate input");
        int n = Integer.parseInt(args[0]);
        int times = Integer.parseInt(args[1]);
        PercolationStats percos = new PercolationStats(n, times);
        StdOut.println("mean                    = " + percos.mean());
        StdOut.println("stddev                  = " + percos.stddev());
        StdOut.println("95% confidence interval = " + percos.confidenceLo() + ", " + percos.confidenceHi());
    }

}


