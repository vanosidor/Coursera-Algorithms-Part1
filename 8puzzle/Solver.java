/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

// TODO puzzle3x3-13.txt
// TODO puzzle3x3-27.txt (long time)
// TODO puzzle3x3-29.txt (long time)
// TODO puzzle3x3-30.txt (out of memory)

// puzzle14.txt
// Delete number: 1700902 Insert number: 5308585
//         Time elapsed total: 10.003 s
//         Moves total: 850450

// puzzle16.txt
// Delete number: 362144 Insert number: 1192402
//         Time elapsed total: 3.05 s
//         Moves total: 181071
//         Minimum number of moves = 16


public class Solver {
    private boolean solvable;
    private Stack<Board> solutionBoards;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        // TODO remove
        Stopwatch stopwatch = new Stopwatch();
        int deleteNumber = 0;
        int insertNumber = 0;

        solutionBoards = new Stack<>();
        MinPQ<SearchNode> searchNodes = new MinPQ<>();
        searchNodes.insert(new SearchNode(initial, null));
        searchNodes.insert(new SearchNode(initial.twin(), null));


        while (!searchNodes.min().board.isGoal()) {
            SearchNode currentSearchNode = searchNodes.delMin();
            deleteNumber++;

            for (Board b : currentSearchNode.board.neighbors()) {
                if (currentSearchNode.prevNode == null || !b
                        .equals(currentSearchNode.prevNode.board)) {
                    searchNodes.insert(new SearchNode(b, currentSearchNode));
                    insertNumber++;
                }
            }
        }

        SearchNode currentSearchNode = searchNodes.min();
        while (currentSearchNode.prevNode != null) {
            solutionBoards.push(currentSearchNode.board);
            currentSearchNode = currentSearchNode.prevNode;
        }

        solutionBoards.push(currentSearchNode.board);

        if (currentSearchNode.board.isGoal()) solvable = true;

        // old not optimal solution
        /*Stopwatch stopwatch = new Stopwatch();
        MinPQ<SearchNode> initialBoardPQ = new MinPQ<>(new ManhattanPriorityBoardComparator());
        int moves = 0;
        initialBoardPQ.insert(new SearchNode(initial, null, moves));

        MinPQ<SearchNode> twinBoardPQ = new MinPQ<>(new ManhattanPriorityBoardComparator());
        twinBoardPQ.insert(new SearchNode(initial.twin(), null, moves));
        SearchNode twinSearchNode;

        while (true) {
            SearchNode currentSearchNode = initialBoardPQ.delMin();
            twinSearchNode = twinBoardPQ.delMin();
            deleteNumber += 2;

            if (currentSearchNode.board.isGoal()) {
                solvable = true;
                break;
            }

            if (twinSearchNode.board.isGoal()) {
                solvable = false;
                break;
            }

            moves++;

            for (Board b : currentSearchNode.board.neighbors()) {
                if (currentSearchNode.prevNode == null || !b
                        .equals(currentSearchNode.prevNode.board)) {
                    initialBoardPQ
                            .insert(new SearchNode(b, currentSearchNode, moves));
                    // StdOut.print(b);
                    insertNumber++;
                }
            }

            for (Board b : twinSearchNode.board.neighbors()) {
                if (twinSearchNode.prevNode == null || !b.equals(twinSearchNode.prevNode.board))
                    twinBoardPQ
                            .insert(new SearchNode(b, twinSearchNode, moves));
                insertNumber++;
            }
        }*/
        StdOut.println("Delete number: " + deleteNumber + " Insert number: " + insertNumber);
        StdOut.println("Time elapsed total: " + stopwatch.elapsedTime() + " s");
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable()) return solutionBoards.size() - 1;
        else return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (solvable) return solutionBoards;
        else return null;
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


    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final SearchNode prevNode;
        private int moves;
        private int manhattan;

        SearchNode(Board board, SearchNode prevNode) {
            this.board = board;
            this.prevNode = prevNode;
            this.manhattan = board.manhattan();
            if (prevNode != null) moves = prevNode.moves;
            else moves = 0;
        }

        public int compareTo(SearchNode that) {
            return this.moves + this.manhattan - (that.moves
                    + that.manhattan);
        }
    }
}


