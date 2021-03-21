/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

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
        this.tiles = copy2DimensialArray(tiles);
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
        if (this == y) return true;
        if (y == null || this.getClass() != y.getClass())
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
        ArrayList<Board> neighbors = new ArrayList<>();

        int zeroRow = 0;
        int zeroColumn = 0;

        // find zero element
        outer:
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    zeroRow = i;
                    zeroColumn = j;
                    break outer;
                }
            }
        }

        // top
        if (zeroRow > 0) {
            int[][] tempTiles = copy2DimensialArray(tiles);
            tempTiles[zeroRow][zeroColumn] = tiles[zeroRow - 1][zeroColumn];
            tempTiles[zeroRow - 1][zeroColumn] = 0;
            neighbors.add(new Board(tempTiles));
        }

        // bottom
        if (zeroRow < n - 1) {
            int[][] tempTiles = copy2DimensialArray(tiles);
            tempTiles[zeroRow][zeroColumn] = tiles[zeroRow + 1][zeroColumn];
            tempTiles[zeroRow + 1][zeroColumn] = 0;
            neighbors.add(new Board(tempTiles));
        }

        // left
        if (zeroColumn > 0) {
            int[][] tempTiles = copy2DimensialArray(tiles);
            tempTiles[zeroRow][zeroColumn] = tiles[zeroRow][zeroColumn - 1];
            tempTiles[zeroRow][zeroColumn - 1] = 0;
            neighbors.add(new Board(tempTiles));
        }

        // right
        if (zeroColumn < n - 1) {
            int[][] tempTiles = copy2DimensialArray(tiles);
            tempTiles[zeroRow][zeroColumn] = tempTiles[zeroRow][zeroColumn + 1];
            tempTiles[zeroRow][zeroColumn + 1] = 0;
            neighbors.add(new Board(tempTiles));
        }

        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        // 1) get random index and check != 0
        // 2) get random nearest element and check != 0
        // 3) swap elements and return new board
        int randomTileValue = 0;
        int randomRow = 0;
        int randomColumn = 0;

        while (randomTileValue == 0) {
            randomRow = StdRandom.uniform(0, n);
            randomColumn = StdRandom.uniform(0, n);
            randomTileValue = tiles[randomRow][randomColumn];
        }

        //TODO rewrite this part
        ArrayList<TilePoint> neighborTilePoints = new ArrayList<>();

        // top
        if (randomRow > 0) {
            int topValue = tiles[randomRow - 1][randomColumn];
            if (topValue != 0) {
                TilePoint topTilePoint = new TilePoint(topValue, randomRow - 1, randomColumn);
                neighborTilePoints.add(topTilePoint);
            }
        }

        // bottom
        if (randomRow < n - 1) {
            int bottomValue = tiles[n - 1][randomColumn];
            if (bottomValue != 0) {
                TilePoint bottomTilePoint = new TilePoint(bottomValue, n - 1, randomColumn);
                neighborTilePoints.add(bottomTilePoint);
            }
        }

        // left
        if (randomColumn > 0) {
            int leftValue = tiles[randomRow][randomColumn - 1];
            if (leftValue != 0) {
                TilePoint leftTilePoint = new TilePoint(leftValue, randomRow, randomColumn - 1);
                neighborTilePoints.add(leftTilePoint);
            }
        }

        // right
        if (randomColumn < n - 1) {
            int rightValue = tiles[randomRow][n - 1];
            if (rightValue != 0) {
                TilePoint rightTilePoint = new TilePoint(rightValue, randomRow, n - 1);
                neighborTilePoints.add(rightTilePoint);
            }
        }

        int neighborIndex = StdRandom.uniform(0, neighborTilePoints.size());
        TilePoint neighborTilePoint = neighborTilePoints.get(neighborIndex);


        int[][] twinTiles = copy2DimensialArray(tiles);

        twinTiles[randomRow][randomColumn] = neighborTilePoint.value;
        twinTiles[neighborTilePoint.row][neighborTilePoint.column] = randomTileValue;

        return new Board(twinTiles);
    }

    private int getElementRow(int value) {
        return (value - 1) / n;
    }

    private int getElementColumn(int value) {
        return (value - 1) % n;
    }

    private int[][] copy2DimensialArray(int[][] sourceArray) {
        int length = sourceArray.length;
        int[][] copiedArray = new int[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                copiedArray[i][j] = sourceArray[i][j];
            }
        }
        return copiedArray;
    }

    private class TilePoint {
        private final int value;
        private final int row;
        private final int column;


        TilePoint(int value, int row, int column) {
            this.value = value;
            this.row = row;
            this.column = column;
        }
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
        StdOut.print(initial.goalBoard);

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
