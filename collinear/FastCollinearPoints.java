import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] segments;
    private int numberOfSegments;


    public FastCollinearPoints(Point[] points){
        if(points== null){
            throw new IllegalArgumentException();
        }
        int numberOfPoints = points.length;
        numberOfSegments =0;
        segments = new LineSegment[0];


        for(int i =0; i<numberOfPoints; i++){
            if(points[i]== null) throw new IllegalArgumentException();
        }

        Point[] arrayPoints = new Point[numberOfPoints-1];


        for(int i = 0; i <numberOfPoints; i++){
            int index =0;
            for(int j = 0; j<numberOfPoints; j++){
                if(i!=j){
                    double slope = points[i].slopeTo(points[j]);
                    if(slope == Double.NEGATIVE_INFINITY){
                        throw new IllegalArgumentException();
                    }
                    arrayPoints[index++]= points[j];
                }
            }
            Arrays.sort(arrayPoints,points[i].slopeOrder());


            Point firstPoint= points[i], lastPoint = points[i];
            int countPoints=0;
            double slope = Double.NEGATIVE_INFINITY;

            for(int k=0; k < arrayPoints.length; k++) {
                Point currentPoint = arrayPoints[k];
                double currentSlope = points[i].slopeTo(currentPoint);

                if(slope ==Double.NEGATIVE_INFINITY){
                    slope = currentSlope;
                    countPoints = 1;
                    if(firstPoint.compareTo(currentPoint)>0){
                        firstPoint = currentPoint;
                    }
                    else if(lastPoint.compareTo(currentPoint)<0){
                        lastPoint = currentPoint;
                    }
                }
                else if(slope == currentSlope){
                    countPoints +=1;
                    if(firstPoint.compareTo(currentPoint)>0){
                        firstPoint = currentPoint;
                    }
                    else if(lastPoint.compareTo(currentPoint)<0){
                        lastPoint = currentPoint;
                    }
                    if(k == arrayPoints.length-1 && countPoints>=3 && firstPoint.compareTo(points[i])==0){
                        addSegment(new LineSegment(firstPoint,lastPoint ));
                    }
                }
                else{
                    if(countPoints>=3 && firstPoint.compareTo(points[i])==0){
                        addSegment(new LineSegment(firstPoint,lastPoint ));
                    }
                    if(currentPoint.compareTo(points[i])>0){
                        firstPoint = points[i];
                        lastPoint = currentPoint;
                    }
                    else if(currentPoint.compareTo(points[i])<0){
                        firstPoint = currentPoint;
                        lastPoint = points[i];
                    }
                    slope = currentSlope;
                    countPoints = 1;
                }

            }
        }




    }

    private  void addSegment(LineSegment segment){

        if(numberOfSegments == segments.length){
            LineSegment[] copy;
            if(numberOfSegments==0){
                copy = new LineSegment[segments.length+1];
            }
            else{
                copy = new LineSegment[segments.length*2];
            }

            for(int i =0; i< segments.length;i++){
                copy[i] = segments[i];
            }
            segments = copy;
        }
        segments[numberOfSegments++] = segment;
    }


    public int numberOfSegments(){
        return numberOfSegments;
    }

    public LineSegment[] segments(){
        LineSegment[] copyOfSegments = new LineSegment[numberOfSegments];
        for(int i = 0; i<numberOfSegments; i++ ){
            copyOfSegments[i] = segments[i];
        }

        return copyOfSegments;
    }
}
