import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] segments;
    private int numberOfSegments;

    public BruteCollinearPoints(Point[] points){
        if(points== null){
            throw new IllegalArgumentException();
        }

        int numberOfPoints = points.length;
        for(int i =0; i<numberOfPoints; i++){
            if(points[i]== null) throw new IllegalArgumentException();
        }
        numberOfSegments =0;
        segments = new LineSegment[0];
        if(numberOfPoints >=4){
            for(int i =0; i<numberOfPoints-3; i++){
                for(int j =i+1; j<numberOfPoints-2; j++){
                    for(int k =j+1; k<numberOfPoints-1; k++){
                        for(int l =k+1; l<numberOfPoints; l++){
                            double slope1 = points[i].slopeTo(points[j]);
                            double slope2 = points[j].slopeTo(points[k]);
                            double slope3 = points[k].slopeTo(points[l]);
                            if(slope1 == Double.NEGATIVE_INFINITY || slope2==Double.NEGATIVE_INFINITY|| slope3== Double.NEGATIVE_INFINITY){
                                throw new IllegalArgumentException();
                            }
                            if(slope1 == slope2 && slope2 == slope3){
                                Point[] array4Point =  {points[i],points[j],points[k],points[l]};
                                Arrays.sort(array4Point);

                                addSegment(new LineSegment(array4Point[0],array4Point[3]));
                            }
                        }
                    }
                }
            }
        }

    }

    private  void addSegment(LineSegment segment){

        if(numberOfSegments == segments.length){
            LineSegment[] copy = new LineSegment[segments.length +1];
            for(int i =0; i< segments.length;i++){
                copy[i] = segments[i];
            }
            segments = copy;
        }
        segments[numberOfSegments++] = segment;
    }


    public int numberOfSegments() {
        return numberOfSegments;
    }

    public LineSegment[] segments(){
        return segments;
    }

}
