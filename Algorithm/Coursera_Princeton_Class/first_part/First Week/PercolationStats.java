import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
/**
 *
 * @author zhaoq
 */
public class PercolationStats {
    private int numOfTrials;
    private double[] overal;
    private int gridSize;
	private double average;
	private double deviation;
    public PercolationStats(int n, int trials){
        if(n<=0||trials<=0){
            throw new IllegalArgumentException();
        }
        gridSize=n;
        numOfTrials=trials;
        overal=new double[trials];
        for(int i=0;i<numOfTrials;i++){
			Percolation grid=new Percolation(n);
            while(!grid.percolates()){
                int row=StdRandom.uniform(1,gridSize+1);
                int col=StdRandom.uniform(1,gridSize+1);
                grid.open(row,col);
            }
            overal[i]=(double)grid.numberOfOpenSites()/(double)(n*n);            
        }
        average=StdStats.mean(overal);
		deviation=StdStats.stddev(overal);
    }
    public double mean(){
        return average;
    }
    public double stddev(){
        return deviation;
    }
    public double confidenceLo(){
        return(average-1.96*deviation/(Math.sqrt(numOfTrials)));
    }
    public double confidenceHi(){
        return(average+1.96*deviation/(Math.sqrt(numOfTrials)));
    }
         
    public static void main(String[] args) {
        int n=Integer.parseInt(args[0]);
        int trials=Integer.parseInt(args[1]);
        PercolationStats stat=new PercolationStats(n,trials);
        System.out.println("the mean is"+stat.mean());
        System.out.println("the standard deviation is"+stat.stddev());
        System.out.println("the confidence interval is["+stat.confidenceLo()+","+stat.confidenceHi()+"]");
    }
    
}

