/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class Board {

    private int n;
    private int[][] tiles;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.length;
        this.tiles = copyTwoDimensional(tiles);
    }

    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] < 10) {
                    sb.append("  " + tiles[i][j]);
                }
                else {
                    sb.append(" " + tiles[i][j]);
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != 0
                        && tiles[i][j] != i * dimension() + j + 1) { // numeration starts with 1
                    hamming++;
                }
            }
        }
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != 0 && tiles[i][j] != i * dimension() + j + 1) {
                    int goalRow = (tiles[i][j] - 1) / n;
                    int goalCol = (tiles[i][j] - 1) % n;
                    int rowMoves = Math.abs(i - goalRow);
                    int colMoves = Math.abs(j - goalCol);
                    manhattan += colMoves + rowMoves;
                }
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // // does this board equal y?
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || this.getClass() != object.getClass())
            return false;
        Board that = (Board) object;
        if (this.n != that.n) return false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] != that.tiles[i][j]) return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<>();

        int zeroPosition = zeroPosition();

        int i = zeroPosition / dimension();
        int j = zeroPosition % dimension();

        if (i > 0) {
            neighbors.add(new Board(swap(i, j, i - 1, j)));
        }

        if (i < n - 1) {
            neighbors.add(new Board(swap(i, j, i + 1, j)));
        }

        if (j > 0) {
            neighbors.add(new Board(swap(i, j, i, j - 1)));
        }

        if (j < n - 1) {
            neighbors.add(new Board(swap(i, j, i, j + 1)));
        }

        return neighbors;
    }

    private int[][] swap(int i1, int j1, int i2, int j2) {
        int[][] copy = copyTwoDimensional(tiles);
        int tempValue = copy[i1][j1];
        copy[i1][j1] = copy[i2][j2];
        copy[i2][j2] = tempValue;
        return copy;
    }

    private int zeroPosition() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    return i * dimension() + j;
                }
            }
        }
        return -1;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        for (int i = 0; i < n; i++) {
            if (tiles[i][0] != 0 && tiles[i][1] != 0) {
                int[][] twinTiles = copyTwoDimensional(tiles);
                twinTiles[i][0] = tiles[i][1];
                twinTiles[i][1] = tiles[i][0];
                return new Board(twinTiles);
            }
        }
        throw new IllegalArgumentException("twin tiles not found");
    }

    private int[][] copyTwoDimensional(int[][] sourceArray) {
        int length = sourceArray.length;
        int[][] copy = new int[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                copy[i][j] = sourceArray[i][j];
            }
        }
        return copy;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board initial = new Board(tiles);

        StdOut.println("InitialBoard:");
        StdOut.print(initial);

        // Test equality
        // Board test1 = new Board(tiles);
        // Board test2 = new Board(new int[][] { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } });
        // StdOut.println("Are equals : " + test1.equals(test2));

        StdOut.println("hamming = " + String.valueOf(initial.hamming()));
        StdOut.println("manhattan = " + String.valueOf(initial.manhattan()));
        StdOut.print("is goal: " + initial.isGoal());

        // check neighbors
        StdOut.println("Neighbors check:");
        for (Board b : initial.neighbors()) {
            StdOut.print(b);
        }

        // check twin
        // StdOut.println("Twins check:");
        // StdOut.print(initial.twin());
        // StdOut.print(initial.twin());
        // StdOut.print(initial.twin());
        // StdOut.print(initial.twin());
        // StdOut.print(initial.twin());
        // StdOut.print(initial.twin());
        // StdOut.print(initial.twin());

        // StdOut.print(initial);
    }
}
