import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    private WeightedQuickUnionUF grid;
    private boolean[][] grid2;
    private boolean[] full;
    private int numOfOpen;
    private int numOfRow;
    public Percolation(int n){
        if(n<=0){
            throw new IllegalArgumentException();
        }
        grid=new WeightedQuickUnionUF(n*n+2);
        grid2=new boolean[n+1][n+1];
        full=new boolean[n*n];
        numOfOpen=0;
        numOfRow=n;
        for(int i=0;i<n+1;i++){
            for(int j=0;j<n+1;j++){
                grid2[i][j]=false;
            }
        }
        
    }
    public void open(int row, int col){
        if(row<1||row>numOfRow||col<1||col>numOfRow){
            throw new IllegalArgumentException();
        }
        int i;
        int j;
        if(grid2[row][col]==false){
            grid2[row][col]=true;
            if(row==1){
                full[col-1+numOfRow*(row-1)]=true;
				grid.union(col-1+numOfRow*(row-1),numOfRow*numOfRow);
            }
			if(row==numOfRow){
				grid.union(col-1+numOfRow*(row-1),numOfRow*numOfRow+1);
			}
            if(row-1>=1&&grid2[row-1][col]==true){
                i=grid.find(col-1+numOfRow*(row-1));
                j=grid.find(col-1+numOfRow*(row-2));
                if(full[i]==true||full[j]==true){
                full[i]=true;
                full[j]=true;
                }
                grid.union(col-1+numOfRow*(row-1),col-1+numOfRow*(row-2));
            }
            if(row+1<=numOfRow&&grid2[row+1][col]==true){
                i=grid.find(col-1+numOfRow*(row-1));
                j=grid.find(col-1+numOfRow*row);
                if(full[i]==true||full[j]==true){
                full[i]=true;
                full[j]=true;
                }
                grid.union(col-1+numOfRow*(row-1),col-1+numOfRow*row);
            }
            if(col+1<=numOfRow&&grid2[row][col+1]==true){
                i=grid.find(col-1+numOfRow*(row-1));
                j=grid.find(col+numOfRow*(row-1));
                if(full[i]==true||full[j]==true){
                full[i]=true;
                full[j]=true;
                }
                grid.union(col-1+numOfRow*(row-1),col+numOfRow*(row-1));
            }
            if(col-1>=1&&grid2[row][col-1]==true){
                i=grid.find(col-1+numOfRow*(row-1));
                j=grid.find(col-2+numOfRow*(row-1));
                if(full[i]==true||full[j]==true){
                full[i]=true;
                full[j]=true;
                }
                grid.union(col-1+numOfRow*(row-1),col-2+numOfRow*(row-1));
            }
            numOfOpen++;
        }
        
        else{
            return;
        }
    }
    public boolean isOpen(int row, int col){
        if(row<1||row>numOfRow||col<1||col>numOfRow){
            throw new IllegalArgumentException();
        }
        return grid2[row][col]==true;    
    }
    public boolean isFull(int row, int col){
        if(row<1||row>numOfRow||col<1||col>numOfRow){
            throw new IllegalArgumentException();
        }
        if(isOpen(row, col)){
            int i=grid.find(col-1+numOfRow*(row-1));
            if(full[i]==true){
                return true;
            }
        }
        return false;
        
    }
    public int numberOfOpenSites(){
        return numOfOpen;
    }
    public boolean percolates(){
        boolean judge=grid.connected(numOfRow*numOfRow,numOfRow*numOfRow+1);
        return judge;
    }
    public static void main(String[] args) {
    }
    
}
