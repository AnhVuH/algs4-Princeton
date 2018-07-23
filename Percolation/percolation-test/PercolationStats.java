import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] thresholds;
    private int trials;
    private double mean;
    private double stddev;
    public PercolationStats (int n, int trials){
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        thresholds = new double[trials];
        this.trials = trials;
        for(int i =0; i<trials; i++){
            Percolation uf = new Percolation(n);

            while(!uf.percolates()){
                int row = StdRandom.uniform(1,n+1 );
                int col =  StdRandom.uniform(1,n+1 );
                if(!uf.isOpen(row,col)){
                    uf.open(row,col);
                }
            }
            int numberOfOpenSites = uf.numberOfOpenSites();
            thresholds[i] = (double)numberOfOpenSites/(double)(n*n);
        }
        mean = StdStats.mean(thresholds);
        stddev = StdStats.stddev(thresholds);
    }

    public double mean(){return mean;}

    public double stddev(){return stddev;}
    public double confidenceLo(){return mean - 1.96*stddev/Math.sqrt((trials));}
    public double confidenceHi(){return mean + 1.96*stddev/Math.sqrt((trials));}


    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(n, trials);
        StdOut.println("mean             = " + percolationStats.mean());
        StdOut.println("stddev           = " + percolationStats.stddev());
        StdOut.println("95% confidence interval= " +"[" + percolationStats.confidenceLo() +", " + percolationStats.confidenceHi() +"]");
    }
}
