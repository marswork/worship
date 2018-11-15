
import java.util.LinkedList;
import java.util.Queue;

/**
 * Find the min steps of maze. 意思是说有一个M*N的maze，0代表可以通过，1代表不可以通过，
 * 然后给你一个出口（x,y），找从（0,0）到出口的最少steps，如果找不到path就返回-1.
 *
 */
public class MazeMinSteps {
    static class Point {
        public int x, y;
        public int cost;

        public Point(int x, int y, int cost) {
            this.x = x;
            this.y = y;
            this.cost = cost;
        }
    }

    public int minSteps(int[][] maze, int dx, int dy) {
        Point point = bfs(maze, dx, dy);
        return (point == null) ? -1 : point.cost;
    }

    private Point bfs(int[][] maze, int dx, int dy) {
        final int m = maze.length, n = maze[0].length;
        final int[][] dirs = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        boolean[][] visited = new boolean[m][n]; // false

        Queue<Point> que = new LinkedList<>();
        que.offer(new Point(0, 0, 0));

        while (!que.isEmpty()) {
            Point curPoint = que.poll();
            for (int[] d : dirs) {
                int r = curPoint.x + d[0], c = curPoint.y + d[1];
                int cost = curPoint.cost;
                if (0 <= r && r < m && 0 <= c && c < n && !visited[r][c]
                        && maze[r][c] == 0) {
                    Point nextPoint = new Point(r, c, cost + 1);

                    if (r == dx && c == dy) { // Destination point
                        return nextPoint;
                    }

                    que.offer(nextPoint);
                    visited[r][c] = true;
                }
            }
        }

        return null;
    }

    public static void main(String[] args) {
        MazeMinSteps sol = new MazeMinSteps();
        int[][] maze = {
                {0, 1, 1, 1, 1},
                {0, 1, 0, 0, 0},
                {0, 1, 0, 1, 0},
                {0, 0, 0, 1, 0},
                {0, 1, 1, 1, 0}
        };

        System.out.println(sol.minSteps(maze, 4, 4));

        int[][] maze2 = {
                {0, 1, 0, 1, 0},
                {0, 1, 0, 0, 0},
                {0, 1, 0, 1, 0},
                {0, 0, 0, 1, 0},
                {0, 1, 1, 1, 0}
        };

        System.out.println(sol.minSteps(maze2, 0, 4));

        int[][] mazeNoPath = {
                {0, 1, 0, 1, 0},
                {0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0},
                {0, 1, 1, 0, 0}
        };

        System.out.println(sol.minSteps(mazeNoPath, 4, 3));
    }
}
