/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdIn;
import java.util.Comparator;
import java.util.Iterator;
import java.lang.IllegalArgumentException;
/**
 *
 * @author zhaoq
 */

public class Solver{
    private searchNode end;
	private Iterable<Board> iterable;
    public Solver(Board initial){
       Board start=initial;
       end=solve(start); 
	   iterable=new list();
    }
    private searchNode solve(Board initial){
		if(initial==null) throw new IllegalArgumentException();
        searchNode begin1=new searchNode(initial,0,null);
        searchNode begin2=new searchNode(initial.twin(),0,null);
        Comparator comparator1=begin1.compare();
        Comparator comparator2=begin2.compare();
        MinPQ<searchNode> pq1=new MinPQ<searchNode>(comparator1);
        MinPQ<searchNode> pq2=new MinPQ<searchNode>(comparator2);
        pq1.insert(begin1);
        pq2.insert(begin2);
        while(true){
            searchNode current1=pq1.delMin();
            searchNode current2=pq2.delMin();
			Board medium1=current1.getBoard();
			Board medium2=current2.getBoard();
            if(medium1.isGoal()) {
                return current1;
            }
            if(medium2.isGoal()){
                return null;
            }
            for(Board neighbor1:medium1.neighbors()){
				if(current1.preSearchNode!=null&&neighbor1.equals(current1.preSearchNode.getBoard())) continue;
                int numOfmove=current1.numOfMove+1;
                searchNode insertNode=new searchNode(neighbor1,numOfmove,current1);
                pq1.insert(insertNode);
			}
            for(Board neighbor2:medium2.neighbors()){ 
				if(current2.preSearchNode!=null&&neighbor2.equals(current2.preSearchNode.getBoard())) continue;
                int numOfmove=current2.numOfMove+1;
                searchNode insertNode=new searchNode(neighbor2,numOfmove,current2);
                pq2.insert(insertNode);
			}
         
        }
    }
    public boolean isSolvable(){
        if(end==null) return false;
        else return true;
    }
    public int moves(){
        if(end==null) return -1;
        else return end.getnumOfMove();
    }
    public Iterable<Board> solution(){
		if(end==null) return null;
        else return iterable;

    }
    /**
     * @param args the command line arguments
     */

    private class list implements Iterable<Board>{
		private int m;
		private Board[] outPut;
		public Iterator<Board> iterator(){
			m=0;
			searchNode nowNode=end;
            outPut=new Board[end.getnumOfMove()+1];
			while(nowNode!=null){
                Board nowBoard=nowNode.getBoard();
			    nowNode=nowNode.preSearchNode;
				outPut[m++]=nowBoard;
			}
			return new boardList();
		}
        private class boardList implements Iterator<Board>{
			 private int n=m;
             public boolean hasNext(){
                 return n!=0;
             }
             public Board next(){
				 if(n==0) return null;
                 return outPut[--n];
             }
             public void remove(){
          
             }
      
        }
       
    }
    

    private class searchNode{
        private Board board;
        private int numOfMove;
        private searchNode preSearchNode;
		private int Manhattan;
		private int Hamming;
        searchNode(Board board,int numOfMove,searchNode preSearchNode){
            this.board=board;
            this.numOfMove=numOfMove;
            this.preSearchNode=preSearchNode;
			this.Manhattan=board.manhattan()+numOfMove;
			this.Hamming=board.hamming()+numOfMove;
        }
        public Board getBoard(){
            return board;
        }
        public boolean isGoal(){
            return board.isGoal();
        }
        public int getnumOfMove(){
            return numOfMove;
        }
        public searchNode getPreSearchNode(){
            return preSearchNode;
        }
        public int getHamming(){
            return Hamming;
        }
        public int getManhattan(){
            return Manhattan;
        }
        public Comparator<searchNode> compare(){
            return new list();
        }
        private class list implements Comparator<searchNode>{
            public int compare(searchNode node1,searchNode node2){
                if(node1.getManhattan()>node2.getManhattan()) return 1;
                if(node1.getManhattan()<node2.getManhattan()) return -1;       
                else return 0;
            }
        }
    }
    
    public static void main(String[] args) {
         int n=StdIn.readInt();
		 int[][] array=new int[n][n];
		 for(int i=0;i<n;i++){
			 for(int j=0;j<n;j++){
				 array[i][j]=StdIn.readInt();
		     }
		 }
		 Board initial=new Board(array);
		 Solver sol=new Solver(initial);
		 if(!sol.isSolvable()){
			 System.out.println("No possible solutions");
	     }
		 else{
			 Board[] outPut=new Board[sol.moves()+1];
			 int i=0;
			 System.out.println("Minimum number of movers = "+sol.moves());
			 for(Board board: sol.solution()) System.out.println(board);
			 for(Board board: sol.solution()) System.out.println(board);
			 for(Board board: sol.solution()) System.out.println(board);
		 }
		 

    }
    
}
