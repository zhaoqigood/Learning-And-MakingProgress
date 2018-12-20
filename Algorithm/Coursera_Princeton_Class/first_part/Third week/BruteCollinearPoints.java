import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdDraw;
/**
 *
 * @author zhaoq
 */
public class BruteCollinearPoints {
    private LineSegment[] segments;
    private int numOfLine=0;
    public BruteCollinearPoints(Point[] points){
        if(points==null){
            throw new IllegalArgumentException();
        }
        int length=points.length;
        for(int i=0;i<length;i++){
			if(points[i]==null){
				throw new IllegalArgumentException();
			}
            for(int j=i+1;j<length;j++){
                if(points[j]==null||points[i].compareTo(points[j])==0){
                    throw new IllegalArgumentException();
                }
            }
        }
        Point max;
        Point min;
        double slope1;
        double slope2;
        double slope3;
        segments=new LineSegment[length];
        for(int i=0;i<length;i++){
            for(int j=i+1;j<length;j++){
                for(int k=j+1;k<length;k++){
                    for(int m=k+1;m<length;m++){
                        slope1=points[i].slopeTo(points[j]);
                        slope2=points[i].slopeTo(points[k]);
                        slope3=points[i].slopeTo(points[m]);
                        if(slope1==slope2&&slope1==slope3){
                            max=points[i];
                            min=points[i];
                            if(max.compareTo(points[j])<0){
                                max=points[j];
                            }
                            if(max.compareTo(points[k])<0){
                                max=points[k];
                            }
                            if(max.compareTo(points[m])<0){
                                max=points[m];
                            }
                            if(min.compareTo(points[j])>0){
                                min=points[j];
                            }
                            if(min.compareTo(points[k])>0){
                                min=points[k];
                            }
                            if(min.compareTo(points[m])>0){
                                min=points[m];
                            }
                            segments[numOfLine++]=new LineSegment(min,max);
                            if(isFull(segments)){
                                segments=resize(segments);
                            }
                        }
                    }
                }
            }
        }
    }
    private LineSegment[] resize(LineSegment[] max){
        int length=max.length;
        LineSegment[] medium=new LineSegment[2*length];
        for(int i=0;i<length;i++){
            medium[i]=max[i];
        }
        return medium;
    }
    private boolean isFull(LineSegment[] max){
        int length=max.length;
        return max[length-1]!=null;
    }
    public  int numberOfSegments(){
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
        int size=StdIn.readInt();
        Point[] points=new Point[size];
        for(int i=0;i<size;i++){
            int x=StdIn.readInt();
            int y=StdIn.readInt();
            points[i]=new Point(x,y);
        }
		for(Point q:points){
			q.draw();
		}
		StdDraw.show();
        BruteCollinearPoints weCan=new BruteCollinearPoints(points);
        LineSegment[] lines=weCan.segments();
		StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
		
        for(LineSegment segment:lines){
            System.out.println(segment);
			segment.draw();
        }
		StdDraw.show();
        
    }
    
}

