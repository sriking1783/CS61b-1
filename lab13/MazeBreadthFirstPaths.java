import java.util.Observable;
import java.util.Queue;
import java.util.ArrayDeque;
/** 
 *  @author Josh Hug
 */

public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields: 
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int a;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        a = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[a] = 0;
        edgeTo[a] = a;     
    }

    /** Conducts a breadth first search of the maze starting at vertex x. */
    private void bfs(int s) {
        /* Your code here. */
        Queue<Integer> fringe = new ArrayDeque<Integer>();
        distTo[s] = 0;
        marked[s] = true;
        fringe.add(s);
        announce();
        if (s == t) {
            targetFound = true;
        }
        if (targetFound) {
            return;
        }
        while (!fringe.isEmpty()) {
            int v = fringe.remove();
            if (v == t) {
                targetFound = true;
            }
            if (targetFound) {
                return;
            }
            for (int x : maze.adj(v)) {
                if (!marked[x]) {
                    edgeTo[x] = v;
                    distTo[x] = distTo[v] + 1;
                    marked[x] = true;
                    fringe.add(x);
                    announce();
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs(a);
    }
} 

