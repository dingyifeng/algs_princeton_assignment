import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by up_ding on 19/12/2016.
 */
public class FastCollinearPoints {
    //private LineSegment[] lineSeg;
    private ArrayList<LineSegment> foundSeg = new ArrayList<LineSegment>();
    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException();
        checkPoints(points);



        Point[] copyPoints = Arrays.copyOf(points,points.length);

        //Arrays.sort(copyPoints); // the order would be changed

        for (int i = 0; i < points.length; i++) {
            Arrays.sort(copyPoints); // 保证排序的稳定性，如果没有这步排序就不能保证每个collinear只出现一次
            Arrays.sort(copyPoints,copyPoints[i].slopeOrder()); // points[0] is the p
            for (int p = 0, first = 1, last = 2; last < points.length; last++) {
                while (last < points.length && copyPoints[p].slopeTo(copyPoints[first]) == copyPoints[p].slopeTo(copyPoints[last]))
                    last++;
                if (last - first >= 3 && copyPoints[p].compareTo(copyPoints[first]) < 0)
                    foundSeg.add(new LineSegment(copyPoints[p],copyPoints[last - 1])); // the reason of >= 3 and last should minus 1 is
                // when compare the two slop when they equal last++, for example when last == 5 the slope is not equal anymore, but last
                // is 5, so should minus 1
                first = last;
                // while 的退出条件之一是slope不相等，而此时的last已经不是相等的那个类里面的last，所以减1，>=3
            }
        }

        //lineSeg = foundSeg.toArray(new LineSegment[foundSeg.size()]);

    }

    // the number of line segments
    public           int numberOfSegments() {
        return foundSeg.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return foundSeg.toArray(new LineSegment[numberOfSegments()]);
    }

    private void checkPoints(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new NullPointerException();
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException("repeated points");
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }

    }
}
