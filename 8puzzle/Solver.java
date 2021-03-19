/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

// Check for this input
// 3
//         0 1 3
//         4 2 5
//         7 8 6
public class Solver {
    private int moves = 0;
    private boolean solvable;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        // TODO solve puzzle

        MinPQ<GameNode> initialBoardPQ = new MinPQ<>(new ManhattanPriorityBoardComparator());
        initialBoardPQ.insert(new GameNode(initial, null, moves()));

        MinPQ<GameNode> twinBoardPQ = new MinPQ<>(new ManhattanPriorityBoardComparator());
        Board twin = initial.twin();
        twinBoardPQ.insert(new GameNode(twin, null, moves()));


        while (true) {
            // TODO optimize
            // initial
            GameNode currentGameNode = initialBoardPQ.delMin();
            if (currentGameNode.board.isGoal()) {
                solvable = true;
                break;
            }

            moves++;
            for (Board b : currentGameNode.board.neighbors()) {
                if (currentGameNode.prevBoard.equals(b)) initialBoardPQ
                        .insert(new GameNode(b, currentGameNode.board, moves));
            }


            // TODO make twin
            // twin
            // if (twin.isGoal()) {
            //     solvable = false;
            //     break;
            // }

        }

        StdOut.println("end " + moves);
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        // -1 if unsolvable
        if (isSolvable()) return moves;
        else return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        // return null if unsolvable
        return null;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }


    private class GameNode {
        private final Board board;
        private final Board prevBoard;
        private final int moves;
        // private final int moves;

        GameNode(Board board, Board prevBoard, int moves) {
            this.board = board;
            this.prevBoard = board;
            this.moves = moves;
        }
    }


    private class ManhattanPriorityBoardComparator implements Comparator<GameNode> {
        public int compare(GameNode gameNode1, GameNode gameNode2) {
            return gameNode1.moves + gameNode1.board.manhattan() - gameNode2.moves + gameNode2.board
                    .manhattan();
        }
    }

    private class HammingPriorityBoardComparator implements Comparator<GameNode> {
        public int compare(GameNode gameNode1, GameNode gameNode2) {
            return gameNode1.moves + gameNode1.board.hamming() - gameNode2.moves + gameNode2.board
                    .hamming();
        }
    }

}


