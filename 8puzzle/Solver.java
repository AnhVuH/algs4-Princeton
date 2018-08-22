/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {
    private MinPQ<SearchNode> priorityQueue = new MinPQ<SearchNode>(new NodeOrder());
    private MinPQ<SearchNode> twinPriorityQueue = new MinPQ<SearchNode>(new NodeOrder());

    private boolean isSolvable;
    private int moves;
    private Stack<Board> solutions = new Stack<>();

    public Solver (Board initial){
        moves =0;
        isSolvable = false;
        if(initial == null){
            throw  new IllegalArgumentException();
        }

        priorityQueue.insert(new SearchNode(0,initial ,null ));
        twinPriorityQueue.insert(new SearchNode(0,initial.twin() ,null ));
        Board prevBoard = null;


        while(true){
            SearchNode minNode =  priorityQueue.delMin();
            Board minNodeBoard = minNode.currentBoard;

            moves = minNode.move;

            SearchNode minTwinNode = twinPriorityQueue.delMin();
            Board minTwinNodeBoard = minTwinNode.currentBoard;

            if(minNodeBoard.isGoal()){
                isSolvable = true;
                moves = minNode.move;
                SearchNode currentNode = minNode;
                while(true){
                    solutions.push(currentNode.currentBoard);
                    if(currentNode.currentBoard.equals(initial)){
                        break;
                    }
                    currentNode =currentNode.prevNode;
                    // StdOut.println(currentNode.currentBoard);
                }
                break;
            }
            else if(minTwinNodeBoard.isGoal()){
                break;
            }
            else if(minNode.move==0){
                for (Board board :minNodeBoard.neighbors()) {
                    SearchNode node = new SearchNode(moves+1,board ,minNode);
                    priorityQueue.insert(node);

                }
                for (Board board :minTwinNodeBoard.neighbors()) {
                    SearchNode node = new SearchNode(moves+1,board ,minTwinNode);
                    twinPriorityQueue.insert(node);

                }
            }
            else{
                for (Board board :minNodeBoard.neighbors()) {
                    if(!board.equals(minNode.prevNode.currentBoard)){
                        SearchNode node = new SearchNode(moves+1,board ,minNode);
                        priorityQueue.insert(node);
                    }
                }
                for (Board board :minTwinNodeBoard.neighbors()) {
                    if(!board.equals(minTwinNode.prevNode.currentBoard)){
                        SearchNode node = new SearchNode(moves+1,board ,minTwinNode);
                        twinPriorityQueue.insert(node);
                    }
                }
            }

        }

    }
    private class SearchNode {
        SearchNode prevNode;
        Board currentBoard;
        int move;
        int priority;
        public SearchNode(int move, Board currentboard, SearchNode prevNode){
            this.move = move;
            this.currentBoard = currentboard;
            this.prevNode = prevNode;
            this.priority = currentboard.manhattan()+ move;
        }


    }
    private class NodeOrder implements Comparator<SearchNode> {

        @Override
        public int compare(SearchNode node1, SearchNode node2) {
            int subPriority =node1.priority-node2.priority;
            if(subPriority!=0) return subPriority;
            else return node1.currentBoard.manhattan()-node2.currentBoard.manhattan();

        }
    }


    public boolean isSolvable() {
        return isSolvable;
    }

    public int moves(){
        return moves;
    }

    public Iterable<Board> solution(){
        if(isSolvable) return solutions;
        else return null;
    }
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()){
                StdOut.println(board);
            }

        }
    }


}
