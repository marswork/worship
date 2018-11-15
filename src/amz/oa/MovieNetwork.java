
import java.util.*;

/**
 * 6.  movie network
 * <p>
 * 题目如下： 假设有个Movie类，
 * <p>
 * public class Movie {
 * int movieId;
 * float rating;
 * List<Movie> similarMovies
 * // 还有其他的getters
 * }
 * 现在要求找到 k个和movie最相似 的movies。
 * <p>
 * 函数的signature大概是这样的：
 * public static List<Movie> getNearest(Movie movie, int numSimilar)
 * <p>
 * <p>
 * 举个栗子： m0 <-->m1,similarity 2
 * mo <--> m2, similarity 3
 * m1 <--> m3, similarity
 * 4 m2 <--> m5, similaity 5
 * <p>
 * 如果要返回和mo最相似的movie, 那么应该返回 m5 (只有有一条路径从 m1到 m5, 并且 5是最大的）； 如果返回3个最相似的就返回 m2,
 * m3， m5（顺序不重要）； 如果需要返回10个，但是相似的只有9个，那么就返回9个。 source movie本身不能在返回结果里面。 可以的一个做法是
 * dfs + min-Heap(PriorityQueue)， 我们一直做dfs， 每次碰到一个新的movie，如果现在queue的size比
 * k小的话，直接加到queue里面； 如果新movie的rating比queue top movie的rating高的话， 把顶部movie
 * 踢出队列，加上这个新的。
 * <p>
 * <p>
 * update: 应该返回 m5 (只有有一条路径从 m1到 m5, 并且 5是最大的） --> 应该返回 m5 (只要有一条路径从 m1到 m5, 并且
 * 5是最大的）. Wara
 */
public class MovieNetwork {
    static class Movie {
        private int movieId;
        private float rating;
        private List<Movie> similarMovies;

        public Movie(int movieId, float rating) {
            this.movieId = movieId;
            this.rating = rating;
            this.similarMovies = new ArrayList<>();
        }

        public void addSimilarMovie(Movie... movies) {
            for (Movie m : movies) {
                this.similarMovies.add(m);
            }
        }

        public int getMovieId() {
            return movieId;
        }

        public float getRating() {
            return rating;
        }

        public List<Movie> getSimilarMovies() {
            return similarMovies;
        }

        @Override
        public String toString() {
            return "{\"Movie\":{"
                    + "\"movieId\":\"" + movieId + "\""
                    + ",\"rating\":\"" + rating + "\""
                    + "}}";
        }
    }

    public static Set<Movie> getNearest(Movie movie, int numSimilar) {
        if (movie == null || numSimilar <= 0) return Collections.emptySet();
        Comparator<Movie> compareByRating = Comparator.comparing(Movie::getRating);
        Queue<Movie> heap = new PriorityQueue<>(compareByRating);
        Set<Movie> visited = new HashSet<>();
        visited.add(movie);
        Queue<Movie> que = new LinkedList<>();
        que.offer(movie);

        while (!que.isEmpty()) {
            Movie cur = que.poll();
            for (Movie m : cur.getSimilarMovies()) {
                if (visited.add(m)) {
                    heap.offer(m);
                    if (heap.size() > numSimilar) {
                        heap.poll();
                    }
                    que.offer(m);
                }
            }
        }
        return new LinkedHashSet<>(heap);
    }

    public static void main(String[] args) {
        Movie m1 = new Movie(1, 1.0f);
        Movie m2 = new Movie(2, 2.2f);
        Movie m3 = new Movie(3, 3.3f);
        Movie m4 = new Movie(4, 4.4f);
        Movie m5 = new Movie(5, 5.5f);
        Movie m6 = new Movie(6, 0.5f);

        // m1: m2, m6
        m1.addSimilarMovie(m2, m6);
        // m2: m3, m5, m6
        m2.addSimilarMovie(m3, m6, m5);
        // m3: m4, m6
        m3.addSimilarMovie(m4, m6);
        // m4: m1, m5
        m4.addSimilarMovie(m1, m5);
        // m5: m6
        m5.addSimilarMovie(m6);
        // m6: m2, m3
        m6.addSimilarMovie(m2, m3);

        Set<Movie> top = getNearest(m1, 3);
        System.out.println(top);

    }
}

