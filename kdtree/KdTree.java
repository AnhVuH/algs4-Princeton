/******************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;


public class KdTree {
    private int size;
    private Node root;
    private static class Node{
        private Point2D point;
        private RectHV rect;
        private  Node leftBottom;
        private  Node rightTop;
        public Node(Point2D p){
            this.point=p;
        }
    }


    public KdTree(){
        size =0;
    }

    public boolean isEmpty(){
        return size==0;
    }

    public int size(){
        return size;
    }

    public void insert(Point2D p){
        if(p==null) throw new IllegalArgumentException();
        double[] rectPos = {0,0,1,1}; // {xmin, ymin,xmax,ymax}
        root = insert(root, p, rectPos, 'x');

    }

    private Node insert(Node node, Point2D p, double[] rectPos, char coordinate){
        if(node ==null){
            if(size!=0){
                if(contains(p)){
                    // StdOut.println("contain");
                    return null;
                }
            }
            node = new Node(p);
            node.rect = new RectHV(rectPos[0],rectPos[1] ,rectPos[2] , rectPos[3]);
            size+=1;
            return node;
        }

        double xmin = node.rect.xmin();
        double xmax = node.rect.xmax();
        double ymin = node.rect.ymin();
        double ymax = node.rect.ymax();

        if(coordinate=='x'){
            if(node.point.x()<= p.x()){
                node.rightTop = insert(node.rightTop, p ,
                                       new double[] { node.point.x(), ymin, xmax, ymax }, 'y' );
            }
            else if(node.point.x()> p.x()){
                node.leftBottom = insert(node.leftBottom,p ,new double[]{xmin, ymin, node.point.x(), ymax},'y' );
            }
        }
        if(coordinate=='y'){
            if(node.point.y()<= p.y()){
                node.rightTop = insert(node.rightTop, p, new double[]{xmin, node.point.y(),xmax,ymax}, 'x');
            }
            else if(node.point.y()> p.y()){
                node.leftBottom = insert(node.leftBottom, p, new double[]{xmin,ymin,xmax,node.point.y()}, 'x');
            }

        }

        return node;
    }
    public boolean contains(Point2D p){
        if(p==null) throw new IllegalArgumentException();
        if(isEmpty()) return false;
        return contains(root, p, 'x');
    }

    private boolean contains(Node node, Point2D p, char coordinate){
        if(node.point.equals(p)) {
            return true;
        }
        if(coordinate=='x'){
            if(node.point.x() <= p.x()){
                if(node.rightTop!=null) return contains(node.rightTop ,p, 'y');
            }
            else if(node.point.x() > p.x()){
                if(node.leftBottom!=null) return contains(node.leftBottom,p,'y');
            }
        }

        if(coordinate=='y'){
            if(node.point.y() <= p.y()){
                if(node.rightTop!=null) return contains(node.rightTop,p,'x');
            }
            else if(node.point.y() > p.y()){
                if(node.leftBottom!=null) return contains(node.leftBottom,p,'x');
            }
        }
        return false;
    }

    public void draw(){
        Node currentNode = root;
        char coordinate = 'x';
        draw(currentNode, coordinate);
    }

    private void draw(Node currentNode, char coordinate){
        if(currentNode!= null){
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            currentNode.point.draw();
            if(coordinate=='x'){
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.setPenRadius();
                StdDraw.line(currentNode.point.x(), currentNode.rect.ymin(), currentNode.point.x(), currentNode.rect.ymax());
                draw(currentNode.leftBottom,'y' );
                draw(currentNode.rightTop,'y' );
            }
            if(coordinate=='y'){
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.setPenRadius();
                StdDraw.line(currentNode.rect.xmin(), currentNode.point.y(),currentNode.rect.xmax(),currentNode.point.y());
                draw(currentNode.leftBottom,'x' );
                draw(currentNode.rightTop,'x' );
            }
        }
        return ;
    }

    public Iterable<Point2D> range(RectHV rect){
        if(rect== null) throw new IllegalArgumentException();
        ArrayList<Point2D> point2DArrayList = new ArrayList<Point2D>();
        if(root !=null) rangeSearch(root,rect,point2DArrayList );
        return point2DArrayList;
    }

    private void rangeSearch(Node node, RectHV rect,ArrayList<Point2D> arrayPoint){
        if(rect.intersects(node.rect)){
            if(rect.contains(node.point)){
                arrayPoint.add(node.point);
            }
            if(node.leftBottom!=null){
                if(rect.intersects(node.leftBottom.rect)){
                    rangeSearch(node.leftBottom,rect ,arrayPoint );
                }
            }
            if(node.rightTop!=null){
                if(rect.intersects(node.rightTop.rect)){
                    rangeSearch(node.rightTop,rect ,arrayPoint );
                }
            }
        }
        return ;

    }

    public Point2D nearest(Point2D p){
        if(p==null) throw new IllegalArgumentException();
        if(root== null) return null;
        double minDistanceSquare = p.distanceSquaredTo(root.point);
        Point2D minPoint = root.point;
        return pointSearch(root,p, minPoint,minDistanceSquare);
    }

    private Point2D pointSearch(Node node, Point2D p,Point2D minPoint, double minDistanceSquare){
        // StdOut.println(node.point);
        // StdOut.println("min point before" + minPoint);
        // StdOut.println(node.rect);
        // StdOut.println("dis before" + minDistanceSquare);
        // if(node.rect.distanceSquaredTo(p)> minDistanceSquare){
        //     return minPoint;
        // }
        double distanceSquare = node.point.distanceSquaredTo(p);
        if(distanceSquare<=minDistanceSquare){
            minDistanceSquare = distanceSquare;
            minPoint = node.point;
        }
        if(node.rightTop!=null && node.leftBottom!=null){
            double distanceToLeftBot = node.leftBottom.rect.distanceSquaredTo(p);
            double distanceToRightTop = node.rightTop.rect.distanceSquaredTo(p);
            if(distanceToLeftBot <distanceToRightTop){
                minPoint=pointSearch(node.leftBottom,p,minPoint,minDistanceSquare);
                minDistanceSquare = minPoint.distanceSquaredTo(p);
                if(distanceToRightTop <=minDistanceSquare){
                    minPoint=pointSearch(node.rightTop,p,minPoint,minDistanceSquare);
                }

            }
            else{
                minPoint=pointSearch(node.rightTop,p,minPoint,minDistanceSquare);
                minDistanceSquare = minPoint.distanceSquaredTo(p);
                if(distanceToLeftBot <=minDistanceSquare){
                    minPoint=pointSearch(node.leftBottom,p,minPoint,minDistanceSquare);
                }

            }
        }
        else{
            if(node.leftBottom!=null){
                if(node.leftBottom.rect.distanceSquaredTo(p)<=minDistanceSquare){
                    minPoint=pointSearch(node.leftBottom,p,minPoint,minDistanceSquare);
                    minDistanceSquare = minPoint.distanceSquaredTo(p); // ham de quy chi tra ve minpoint nen phai tinh lai min distance
                }

            }
            if(node.rightTop!=null){
                if(node.rightTop.rect.distanceSquaredTo(p)<=minDistanceSquare){
                    minPoint=pointSearch(node.rightTop,p,minPoint,minDistanceSquare);
                    minDistanceSquare = minPoint.distanceSquaredTo(p);
                }

            }
        }


        // StdOut.println("min point after" + minPoint);
        // StdOut.println("after" + minDistanceSquare);
        return minPoint;
    }



}
