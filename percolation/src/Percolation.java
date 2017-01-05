import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/*--------------------------------------------------------
 * Author : Up_Ding
 * Written : 4/12/2016
 * Last updated : 5/12/2016
 *
 *
 * Compilation : javac Percolation.java
 * Execution :  java Percolation
 *
 * Implement of Percolation which contains API as follow:
 *
 * public Percolation(int n)
 * public void open(int row, int col)
 * public boolean isOpen(int row, int col)
 * public boolean if Full(int row, int col)
 * public boolean percolates()
 *
 *
 *---------------------------------------------------------*/
public class Percolation {
    private int size; // the size of the n-by-n grid, namely the n
    private boolean[][] blocked; // to examine the site is open or block
    // due to the backwash problem caused by the bottom virtual site, use two UF,
    // one to test percolate, the one to test isFull
    private WeightedQuickUnionUF ufIsFull; // this UF is to examine the site isFull
    private WeightedQuickUnionUF ufPercolate; // this UF is to examine the percolate property
    private int top = 0; // this is a virtual site on the top of grid connects to the first line of the grid
    private int bottom; // this is a virtual site on the bottom of the grid connects to the last line of the grid


    // to initialization the Percolation
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("The input Argument is invalid");
        size = n;
        blocked = new boolean[size][size];
        top = 0;
        bottom = size*size + 1;
        ufIsFull = new WeightedQuickUnionUF(size*size + 2);
        ufPercolate = new WeightedQuickUnionUF(size*size +2);
        for (int j = 1; j <= size; j++) {
            ufPercolate.union(top, xyTo1D(1, j));
            ufIsFull.union(top, xyTo1D(1, j));
            ufPercolate.union(bottom, xyTo1D(size, j));
        }
    }


    // to examine the validation of the input row and column
    private void validate(int row, int col) {
        if (row < 1 || row > size) throw new IndexOutOfBoundsException("The index of row " + row + " is out of bound");
        if (col < 1 || col > size) throw new IndexOutOfBoundsException("The index of col " + col + " is out of bound");
    }

    // to transfer the xy coordinate to 1D value
    private int xyTo1D(int row, int col) {
        validate(row, col);
        int index;
        index = (row - 1)*size + col; // an two dimension array a[i][j] in the memory is i*n + j, while
                                    // row and col begin from 1, so i and j should both decrease 1, at the same time there
        // is a virtual site with the value 0, so the transformation should be (i-1)*n + (j-1) + 1;
        return index;
    }

    // open site(row,col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        if (isOpen(row, col)) return;
        blocked[row - 1][col - 1] = true;
        if (row > 1 && isOpen(row - 1, col)) {
            ufPercolate.union(xyTo1D(row, col), xyTo1D(row - 1, col));
            ufIsFull.union(xyTo1D(row, col), xyTo1D(row - 1, col));
        }
        if (row < size && isOpen(row + 1, col)) {
            ufPercolate.union(xyTo1D(row, col), xyTo1D(row + 1, col));
            ufIsFull.union(xyTo1D(row, col), xyTo1D(row + 1, col));
        }
        if (col > 1 && isOpen(row, col -1)) {
            ufPercolate.union(xyTo1D(row, col), xyTo1D(row, col - 1));
            ufIsFull.union(xyTo1D(row, col), xyTo1D(row, col - 1));
        }
        if (col < size && isOpen(row, col + 1)) {
            ufPercolate.union(xyTo1D(row, col), xyTo1D(row, col + 1));
            ufIsFull.union(xyTo1D(row, col), xyTo1D(row, col + 1));
        }
    }

    // to examine site(row,col) is open or not
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return blocked[row - 1][col - 1];
    }

    // is site(row,col) full
    public boolean isFull(int row, int col) {
        validate(row, col);
        return ufIsFull.connected(top, xyTo1D(row, col)) && isOpen(row, col);
    }

    // does the system percolate
    public boolean percolates() {
        if (size > 1)
            return ufPercolate.connected(top, bottom);
        else return isOpen(1, 1);
    }

    public static void main(String[] args) {

    }
}