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
    private Board goalBoard;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles[0].length != tiles[1].length) throw new IllegalArgumentException();
        n = tiles.length;
        this.tiles = tiles.clone();
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
        int res = 0;
        int trueValue = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                trueValue++;
                if (tiles[i][j] != 0 && tiles[i][j] != trueValue) {
                    res++;
                }
            }
        }
        return res;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != 0) {
                    int trueRow = getElementRow(tiles[i][j]);
                    int trueCol = getElementColumn(tiles[i][j]);
                    int rowMoves = Math.abs(i - trueRow);
                    int colMoves = Math.abs(j - trueCol);
                    res += colMoves + rowMoves;
                }
            }
        }
        return res;
    }

    // is this board the goal board?
    public boolean isGoal() {
        if (goalBoard == null) {
            int[][] goalTiles = new int[n][n];
            int value = 0;
            for (int i = 0; i < n; i++) {

                for (int j = 0; j < n; j++) {
                    value++;
                    if (i == n - 1 && j == n - 1) {
                        goalTiles[i][j] = 0;
                    }
                    else {
                        goalTiles[i][j] = value;
                    }

                }
            }
            goalBoard = new Board(goalTiles);
        }
        return this.equals(goalBoard);
    }

    // // does this board equal y?
    public boolean equals(Object y) {
        if (y == null || getClass() != y.getClass())
            return false;
        Board that = (Board) y;
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
        // TODO restrict cloning

        ArrayList<Board> neighbors = new ArrayList<>();

        int zeroRow = 0;
        int zeroColumn = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    zeroRow = i;
                    zeroColumn = j;
                }
            }
        }

        // top
        if (zeroRow > 0) {
            int[][] tempTiles = tiles.clone();
            tempTiles[zeroRow][zeroColumn] = tiles[zeroRow - 1][zeroColumn];
            tempTiles[zeroRow - 1][zeroColumn] = 0;
            neighbors.add(new Board(tempTiles));
        }

        if (zeroRow < n - 1) {
            int[][] tempTiles = tiles.clone();
            tempTiles[zeroRow][zeroColumn] = tiles[n - 1][zeroColumn];
            tempTiles[n - 1][zeroColumn] = 0;
            neighbors.add(new Board(tempTiles));
        }

        if (zeroColumn > 0) {
            int[][] tempTiles = tiles.clone();
            tempTiles[zeroRow][zeroColumn] = tiles[zeroRow][zeroColumn - 1];
            tempTiles[zeroRow][zeroColumn - 1] = 0;
            neighbors.add(new Board(tempTiles));
        }

        if (zeroColumn < n - 1) {
            int[][] tempTiles = tiles.clone();
            tempTiles[zeroRow][zeroColumn] = tempTiles[zeroRow][n - 1];
            tempTiles[zeroRow][n - 1] = 0;
            neighbors.add(new Board(tempTiles));
        }

        return neighbors;

    }

    // TODO make function
    // // a board that is obtained by exchanging any pair of tiles
    // public Board twin()

    private int getElementRow(int value) {
        return (value - 1) / n;
    }

    private int getElementColumn(int value) {
        return (value - 1) % n;
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

        // Test equals
        Board test1 = new Board(tiles);
        Board test2 = new Board(new int[][] { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } });
        StdOut.println("Are equals : " + test1.equals(test2));

        StdOut.println("hamming = " + String.valueOf(initial.hamming()));
        StdOut.println("manhattan = " + String.valueOf(initial.manhattan()));
        StdOut.print("is goal: " + initial.isGoal());
        StdOut.print(initial.goalBoard);

        initial.neighbors();

        StdOut.print(initial);
    }
}