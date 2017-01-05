import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.ArrayList;

/**
 * Created by up_ding on 19/12/2016.
 */
public class BruteCollinearPoints {
    // private LineSegment[] lineSeg;

    private ArrayList<LineSegment> foundSegment = new ArrayList<>();
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new NullPointerException();
        checkPoints(points);



        Point[] copyPoints = Arrays.copyOf(points, points.length);
         Arrays.sort(copyPoints);

        for (int p = 0; p < copyPoints.length - 3; p++) {
            for (int q = p + 1; q < copyPoints.length - 2; q++) {
                for (int r = q + 1; r < copyPoints.length - 1; r++) {
                    for (int s = r + 1; s < copyPoints.length; s++) {
                        if (copyPoints[p].slopeTo(copyPoints[q]) == copyPoints[p].slopeTo(copyPoints[r]) &&
                                copyPoints[p].slopeTo(copyPoints[q]) == copyPoints[p].slopeTo(copyPoints[s]))
                            foundSegment.add(new LineSegment(copyPoints[p], copyPoints[s]));
                    }
                }
            }
        }

        //lineSeg = foundSegment.toArray(new LineSegment[foundSegment.size()]);
    }

    // the number of line segments
    public int numberOfSegments() {
        return foundSegment.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return foundSegment.toArray(new LineSegment[numberOfSegments()]);
    }

    private void checkPoints(Point[] points) {
        // for (int i = 0; i < points.length -1; i++) {
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new NullPointerException();
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("repeated point");
                }
            }
        }
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }

    }
}