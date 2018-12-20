
import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Comparator;

public class FastCollinearPoints {
    private LineSegment[] segments;
    private int numOfLine=0;
    public FastCollinearPoints(Point[] points){
		if(points==null){
			throw new IllegalArgumentException();
		}
        int length=points.length;
		for(int i=0;i<length;i++){
			if(points[i]==null) throw new IllegalArgumentException();
			for(int j=i+1;j<length;j++){
				if(points[j]==null||points[i].compareTo(points[j])==0){
					throw new IllegalArgumentException();
				}
			}
		}
        Point[] medium1=new Point[length];
        Point[] medium2=new Point[length];
		Point[] medium=new Point[length];
        Point[] array= new Point[length];
        Point max;
        Point min;
        int n=0;
		for(int i=0;i<length;i++){
			medium[i]=points[i];
			array[i]=points[i];
		}
		
        for(int i=0;i<length;i++){
			
			Arrays.sort(array,medium[i].slopeOrder());
            int j=0;
            while(j<length-1){
                int t=1;
                max=medium[i];
                min=medium[i];
                while(j<length-1&&medium[i].slopeTo(array[j])==medium[i].slopeTo(array[j+1])){
                    if(max.compareTo(array[j])<0) max=array[j];
                    if(min.compareTo(array[j])>0) min=array[j];
			        if(max.compareTo(array[j+1])<0) max=array[j+1];
                    if(min.compareTo(array[j+1])>0) min=array[j+1];
                    t++;
                    j++;
                }
                j++;
                if(t>2){
                    medium1[n]=max;
                    medium2[n]=min;
					n++;
			        if(isFull(medium1)){
                        medium1=resize(medium1);
                        medium2=resize(medium2);
                    }
                }
            }
        }
		segments=new LineSegment[medium1.length];
        for(int i=0;i<n;i++){
            if(medium1[i]!=null){
                 segments[numOfLine++]=new LineSegment(medium1[i],medium2[i]);
            
                 for(int j=i+1;j<n;j++){
                    if(medium1[j]!=null&&medium1[i].compareTo(medium1[j])==0&&medium2[i].compareTo(medium2[j])==0){
                        medium1[j]=null;
                        medium2[j]=null;
                    }
                }
            }
        }
    }
	private Point[] resize(Point[] max){
        int length=max.length;
        Point[] medium=new Point[2*length];
        for(int i=0;i<length;i++){
            medium[i]=max[i];
        }
        return medium;
    }
    private boolean isFull(Point[] max){
        int length=max.length;
        return max[length-1]!=null;
    }
    public int numberOfSegments(){
        return numOfLine;
    }
    public LineSegment[] segments(){
        LineSegment[] medium=new LineSegment[numOfLine];
        for(int i=0;i<numOfLine;i++){
            medium[i]=segments[i];
        }
        return medium;
    }
    public static void main(String[] args) {

    // read the n points from a file
    int n = StdIn.readInt();
    Point[] points = new Point[n];
    for (int i = 0; i < n; i++) {
        int x = StdIn.readInt();
        int y = StdIn.readInt();
        points[i] = new Point(x, y);
    }
    // draw the points
    StdDraw.enableDoubleBuffering();
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    for (Point p : points) {
        p.draw();
    }
    StdDraw.show();

    // print and draw the line segments
    FastCollinearPoints collinear = new FastCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
    StdDraw.show();
}
    
}
