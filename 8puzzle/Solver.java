/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

// TODO puzzle3x3-13.txt
// TODO puzzle3x3-27.txt (long time)
public class Solver {
    private boolean solvable;
    private GameNode currentGameNode;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        MinPQ<GameNode> initialBoardPQ = new MinPQ<>(new ManhattanPriorityBoardComparator());
        int moves = 0;
        initialBoardPQ.insert(new GameNode(initial, null, moves));

        MinPQ<GameNode> twinBoardPQ = new MinPQ<>(new ManhattanPriorityBoardComparator());
        Board twin = initial.twin();
        twinBoardPQ.insert(new GameNode(twin, null, moves));
        GameNode twinGameNode;

        while (true) {
            currentGameNode = initialBoardPQ.delMin();
            twinGameNode = twinBoardPQ.delMin();

            if (currentGameNode.board.isGoal()) {
                solvable = true;
                break;
            }

            if (twinGameNode.board.isGoal()) {
                solvable = false;
                break;
            }

            moves++;

            for (Board b : currentGameNode.board.neighbors()) {
                if (currentGameNode.prevNode == null || !b.equals(currentGameNode.prevNode.board))
                    initialBoardPQ
                            .insert(new GameNode(b, currentGameNode, moves));
            }

            for (Board b : twinGameNode.board.neighbors()) {
                if (twinGameNode.prevNode == null || !b.equals(twinGameNode.prevNode.board))
                    twinBoardPQ
                            .insert(new GameNode(b, twinGameNode, moves));
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        // TODO correct number of moves (not all program moves)
        if (isSolvable() && currentGameNode != null) {
            int minMoves = 0;
            GameNode prevNode = currentGameNode.prevNode;
            while (prevNode != null) {
                minMoves++;
                prevNode = prevNode.prevNode;
            }
            return minMoves;
        }
        else return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (isSolvable() && currentGameNode != null) {
            ArrayList<Board> solutionBoards = new ArrayList<>();
            solutionBoards.add(currentGameNode.board);
            GameNode prevNode = currentGameNode.prevNode;
            while (prevNode != null) {
                solutionBoards.add(prevNode.board);
                prevNode = prevNode.prevNode;
            }
            Collections.reverse(solutionBoards);
            return solutionBoards;
        }
        else {
            return null;
        }
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
        private final GameNode prevNode;
        private final int moves;
        private final int manhattan;

        GameNode(Board board, GameNode prevNode, int moves) {
            this.board = board;
            this.prevNode = prevNode;
            this.moves = moves;
            this.manhattan = board.manhattan();
        }
    }

    private class ManhattanPriorityBoardComparator implements Comparator<GameNode> {
        public int compare(GameNode gameNode1, GameNode gameNode2) {
            return gameNode1.moves + gameNode1.manhattan - (gameNode2.moves + gameNode2.manhattan);
        }
    }

    private class HammingPriorityBoardComparator implements Comparator<GameNode> {
        public int compare(GameNode gameNode1, GameNode gameNode2) {
            return gameNode1.moves + gameNode1.board.hamming() - gameNode2.moves + gameNode2.board
                    .hamming();
        }
    }
}


