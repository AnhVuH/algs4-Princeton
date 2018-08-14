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

        Point[] arrayPoints = new Point[numberOfPoints];

        for(int i =0; i<numberOfPoints; i++){
            arrayPoints[i] = points[i];
        }

        Arrays.sort(arrayPoints);

        for(int i =0; i<numberOfPoints-1; i++){
            if(arrayPoints[i].compareTo(arrayPoints[i+1])==0){
                throw new IllegalArgumentException();
            }
        }

        numberOfSegments =0;
        segments = new LineSegment[0];

        if(numberOfPoints >=4){
            for(int i =0; i<numberOfPoints-3; i++){
                for(int j =i+1; j<numberOfPoints-2; j++){
                    for(int k =j+1; k<numberOfPoints-1; k++){
                        for(int l =k+1; l<numberOfPoints; l++){
                            double slope1 = arrayPoints[i].slopeTo(arrayPoints[j]);
                            double slope2 = arrayPoints[j].slopeTo(arrayPoints[k]);
                            double slope3 = arrayPoints[k].slopeTo(arrayPoints[l]);
                            if(slope1 == slope2 && slope2 == slope3){
                                Point[] array4Point =  {arrayPoints[i],arrayPoints[j],arrayPoints[k],arrayPoints[l]};
                                // Arrays.sort(array4Point);

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


    public int numberOfSegments() {
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
