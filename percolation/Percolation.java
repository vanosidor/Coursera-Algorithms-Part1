/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final WeightedQuickUnionUF weightedQuickUF;
    private final int[] openedSites;
    private final int n;
    private final int pTop;
    private final int pBottom;
    private int numOfOpenSites = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {

        if (n <= 0) throw new IllegalArgumentException();

        this.n = n;
        int sitesLength = n * n;

        pTop = sitesLength;
        pBottom = sitesLength + 1;
        openedSites = new int[sitesLength];

        weightedQuickUF = new WeightedQuickUnionUF(
                sitesLength + 2); // two virtual roots n+1 top and n+2 bottom

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isCoordinatesIncorrect(row, col)) throw new IllegalArgumentException();

        if (!isOpen(row, col)) {
            int siteIndex = getSite2D(row, col);
            openedSites[siteIndex] = 1;
            numOfOpenSites++;

            if (row == 1) {
                weightedQuickUF.union(pTop, siteIndex);
            }

            if (row == n) {
                weightedQuickUF.union(pBottom, siteIndex);
            }

            int topRow = row - 1;
            int bottomRow = row + 1;
            int leftCol = col - 1;
            int rightCol = col + 1;

            if (topRow >= 1 && isOpen(topRow, col)) {
                int topIndex = getSite2D(topRow, col);
                weightedQuickUF.union(siteIndex, topIndex);
            }
            if (bottomRow <= n && isOpen(bottomRow, col)) {
                int bottomIndex = getSite2D(bottomRow, col);
                weightedQuickUF.union(siteIndex, bottomIndex);
            }
            if (leftCol >= 1 && isOpen(row, leftCol)) {
                int leftIndex = getSite2D(row, leftCol);
                weightedQuickUF.union(siteIndex, leftIndex);
            }
            if (rightCol <= n && isOpen(row, rightCol)) {
                int rightIndex = getSite2D(row, rightCol);
                weightedQuickUF.union(siteIndex, rightIndex);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (isCoordinatesIncorrect(row, col)) throw new IllegalArgumentException();
        return openedSites[getSite2D(row, col)] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (isCoordinatesIncorrect(row, col)) throw new IllegalArgumentException();
        int site = getSite2D(row, col);
        return weightedQuickUF.find(site) == weightedQuickUF
                .find(pTop);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return weightedQuickUF.find(pTop) == weightedQuickUF.find(pBottom);
    }

    // test client (optional)
    public static void main(String[] args) {

        if (args == null || args.length == 0) return;

        In in = new In(args[0]);      // input file
        int n = in.readInt();         // n-by-n percolation system

        // repeatedly read in sites to open and draw resulting system
        Percolation perc = new Percolation(n);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
        }
    }

    private int getSite2D(int row, int col) {
        return (row * n - 1) - (n - col);
    }

    private boolean isCoordinatesIncorrect(int row, int col) {
        return row < 1 || row > n || col < 1 || col > n;
    }
}
