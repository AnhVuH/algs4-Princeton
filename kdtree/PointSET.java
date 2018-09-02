/******************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> pointSet;

   public PointSET(){
        pointSet = new TreeSet<Point2D>();
   }

    public boolean isEmpty(){
       return pointSet.isEmpty();
    }
    public int size(){
       return pointSet.size();
    }
    public void insert(Point2D p){
       if(p==null) throw new IllegalArgumentException();
       if(!contains(p)) pointSet.add(p);
    }
    public boolean contains(Point2D p){
        if(p==null) throw new IllegalArgumentException();
       return pointSet.contains(p);
    }
    public void draw(){
        for (Point2D point:pointSet) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            point.draw();
        }
    }
    public Iterable<Point2D> range(RectHV rect){
        if(rect==null) throw new IllegalArgumentException();
        ArrayList<Point2D> point2DArrayList = new ArrayList<Point2D>();
        for (Point2D point:pointSet) {
            if(rect.contains(point)){
                point2DArrayList.add(point);
            }
        }
        return point2DArrayList;
    }
    public Point2D nearest(Point2D p){
        if(p==null) throw new IllegalArgumentException();
       double minDistance=Double.POSITIVE_INFINITY;
       Point2D minPoint=null;
        for (Point2D point:pointSet) {
            double distance = p.distanceTo(point);
            if(distance < minDistance){
                minDistance = distance;
                minPoint = point;
            }
        }
        return minPoint;
    }

    public static void main(String[] args) {

    }

}
