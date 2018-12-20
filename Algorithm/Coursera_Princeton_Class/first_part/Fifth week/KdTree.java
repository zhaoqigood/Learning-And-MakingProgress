/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdDraw;
import java.util.NoSuchElementException;
/**
 *
 * @author zhaoq
 */
public class KdTree {
    private class Node{
        Point2D point;
        int level;
        Node leftNode;
        Node rightNode;
        RectHV rectangle;
        Node(Point2D point,int level,Node leftNode,Node rightNode,RectHV rectangle){
            this.point=point;
            this.level=level;
            this.leftNode=leftNode;
            this.rightNode=rightNode;
            this.rectangle=rectangle;
        }
    }
    private int N;
    private Node root;
    public KdTree(){
        N=0;
        root=null;
    }
    public boolean isEmpty(){
        return N==0;
    }
    public int size(){
        return N;
    }
    
    public void insert(Point2D p){
		if(p==null){
			throw new IllegalArgumentException();
		}
        root=insert(root,p);
        N++;
    }
    private Node insert(Node node,Point2D p){
        if(node==null){
            return new Node(p,0,null,null,new RectHV(0,0,1,1));
        }
        if(node.level%2==0){
            if(p.x()<node.point.x()){
                if(node.leftNode==null){
                    node.leftNode=new Node(p,node.level+1,null,null,new RectHV(node.rectangle.xmin(),node.rectangle.ymin(),node.point.x(),node.rectangle.ymax()));
                }
                else{
                    node.leftNode=insert(node.leftNode,p);
                } 
            }
            else if(p.x()>node.point.x()){
                if(node.rightNode==null){
                    node.rightNode=new Node(p,node.level+1,null,null,new RectHV(node.point.x(),node.rectangle.ymin(),node.rectangle.xmax(),node.rectangle.ymax()));
                }
                else{
                    node.rightNode=insert(node.rightNode,p);
                } 
            }
			else{
				if(p.y()==node.point.y()){
					N--;
					return node;
				}
                else if(node.rightNode==null){
                    node.rightNode=new Node(p,node.level+1,null,null,new RectHV(node.point.x(),node.rectangle.ymin(),node.rectangle.xmax(),node.rectangle.ymax()));
                }
                else{
                    node.rightNode=insert(node.rightNode,p);
                } 
            }
        }
        if(node.level%2==1){
             if(p.y()<node.point.y()){
                if(node.leftNode==null){
                    node.leftNode=new Node(p,node.level+1,null,null,new RectHV(node.rectangle.xmin(),node.rectangle.ymin(),node.rectangle.xmax(),node.point.y()));
                }
                else{
                    node.leftNode=insert(node.leftNode,p);
                } 
            }
            else if(p.y()>node.point.y()){
                if(node.rightNode==null){
                    node.rightNode=new Node(p,node.level+1,null,null,new RectHV(node.rectangle.xmin(),node.point.y(),node.rectangle.xmax(),node.rectangle.ymax()));
                }
                else{
                    node.rightNode=insert(node.rightNode,p);
                } 
            }
			else{
				if(p.x()==node.point.x()){
					N--;
					return node;
				}
				else if(node.rightNode==null){
                    node.rightNode=new Node(p,node.level+1,null,null,new RectHV(node.rectangle.xmin(),node.point.y(),node.rectangle.xmax(),node.rectangle.ymax()));
                }
                else{
                    node.rightNode=insert(node.rightNode,p);
                } 

			}
            
        }
        return node;
    }
    public boolean contains(Point2D p){
		if(p==null){
			throw new IllegalArgumentException();
		}
        return contains(root,p);
    }
    private boolean contains(Node node,Point2D p){
        if(node==null){
            return false;
        }
        if(node.level%2==0){
            if(p.x()<node.point.x()){
                return contains(node.leftNode,p);
            }
            else if(p.x()>node.point.x()){
                return contains(node.rightNode,p);
            }
            else{
                if(p.y()==node.point.y()){
                    return true;
                }
                else{
                    return contains(node.rightNode,p);
                }
            }
        }
        else{
            if(p.y()<node.point.y()){
                return contains(node.leftNode,p);
            }
            else if(p.y()>node.point.y()){
                return contains(node.rightNode,p);
            }
            else{
                if(p.x()==node.point.x()){
                    return true;
                }
                else{
                    return contains(node.rightNode,p);
                }
            }
        }
    }
    public void draw(){
        root=draw(root);
    }
    private Node draw(Node node){
        if(node==null){
            return null;
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledCircle(node.point.x(),node.point.y(),0.01);
        if(node.level%2==0){
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.point.x(),node.rectangle.ymin(),node.point.x(),node.rectangle.ymax());
        }
        if(node.level%2==1){
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(node.rectangle.xmin(),node.point.y(),node.rectangle.xmax(),node.point.y());
        }
        node.leftNode=draw(node.leftNode);
        node.rightNode=draw(node.rightNode);
        return node;
    }
    public Iterable<Point2D> range(RectHV rect){
		if(rect==null){
			throw new IllegalArgumentException();
		}
        Queue<Point2D> queue=new Queue<Point2D>();
        queue=range(root,queue,rect);
        return queue;
    }
    private Queue<Point2D> range(Node node,Queue<Point2D> pointQueue,RectHV rect){
        if(node==null){
            return pointQueue; 
        }
        if(!rect.intersects(node.rectangle)){
            return pointQueue;
        }
        else{
            if(rect.contains(node.point)){
                pointQueue.enqueue(node.point);
            }
            pointQueue=range(node.leftNode,pointQueue,rect);
            pointQueue=range(node.rightNode,pointQueue,rect);
        }
        return pointQueue;
        
    }
    public Point2D nearest(Point2D p){
		if(p==null){
			throw new IllegalArgumentException();
		}
		if(root==null){
			return null;
        }
        Node nearestNode=nearest(root,root,p);
        return nearestNode.point;
    }
    private Node nearest(Node node,Node nearestNode,Point2D p){
		if(node==null){
			return nearestNode;
		}
        double distance1=node.rectangle.distanceTo(p);
        double distance2=nearestNode.point.distanceTo(p);
        if(distance1>distance2){
            return nearestNode;
        }
        else{
            double distance3=node.point.distanceTo(p);
            if(distance3<distance2){
                nearestNode=node;
            }
            
			if(node.rightNode!=null&&node.leftNode!=null&&node.rightNode.rectangle.distanceTo(p)>node.leftNode.rectangle.distanceTo(p)){
                nearestNode=nearest(node.leftNode,nearestNode,p);
                nearestNode=nearest(node.rightNode,nearestNode,p);
            }
		    else{
                nearestNode=nearest(node.rightNode,nearestNode,p);
                nearestNode=nearest(node.leftNode,nearestNode,p);
            }
            
        }
        return nearestNode;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
