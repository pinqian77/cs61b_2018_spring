package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private double[] res;
    private double mean;
    private double stddev;
    private final double alpha = 1.96;

    /** perform T independent experiments on an N-by-N grid*/
    public PercolationStats(int N, int T, PercolationFactory pf){
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        res = new double[T];
        for (int i = 0; i < T; i += 1){
            Percolation percolation = pf.make(N);
            while (!percolation.percolates()){
                percolation.open(StdRandom.uniform(N), StdRandom.uniform(N));
            }
            res[i] = ((double)percolation.numberOfOpenSites()) / (double)(N * N);
        }

        mean = StdStats.mean(res);
        stddev = StdStats.stddev(res);
    }

    /** sample mean of percolation threshold*/
    public double mean(){
        return mean;
    }

    /** sample standard deviation of percolation threshold*/
    public double stddev(){
        return stddev;
    }

    /** low endpoint of 95% confidence interval*/
    public double confidenceLow(){
        return mean - alpha * stddev / Math.sqrt(res.length);
    }

    /** high endpoint of 95% confidence interval*/
    public double confidenceHigh(){
        return mean + alpha * stddev / Math.sqrt(res.length);
    }

    public static void main(String[] args) {
        PercolationFactory pf = new PercolationFactory();
        PercolationStats system = new PercolationStats(20,30, pf);
        System.out.println("The mean probability is: "+ system.mean());
        System.out.println("The standard deviation is: "+ system.stddev());
        System.out.println("The confidence interval is : ( " + system.confidenceLow() + " , " + system.confidenceHigh() + " )");
    }
}
