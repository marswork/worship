package lc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by wshung on 2018/12/16.
 */
public class DesignSnakeGame {
    public static final void main(String[] args) {
        List<int[]> foods = new ArrayList<>();
        foods.add(new int[]{1,2});
        foods.add(new int[]{0,1});
        DesignSnakeGame game = new DesignSnakeGame(3, 2, foods);
        System.out.println("score:" + game.move("R"));
        System.out.println("score:" + game.move("D"));
        System.out.println("score:" + game.move("R"));
        System.out.println("score:" + game.move("U"));
        System.out.println("score:" + game.move("L"));
        System.out.println("score:" + game.move("U"));
    }
    public DesignSnakeGame(int width, int height, List<int[]> foods) {
        this.foods = foods;
        this.width = width;
        this.height = height;
        ListNode snakeHead = new ListNode(0, 0);
        head.next = snakeHead;
        snakeHead.prev = head;
        snakeHead.next = tail;
        tail.prev = snakeHead;
        snake.add(snakeHead);
    }
    Set<ListNode> snake = new HashSet<>();
    ListNode head = new ListNode(0, 0);
    ListNode tail = new ListNode(0, 0);
    public class ListNode {
        public int i;
        public int j;
        public ListNode next = null;
        public ListNode prev = null;
        public ListNode(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

    private int width;
    private int height;
    private List<int[]> foods = null;
    private int fi = 0;
    private int score = 0;
    private ListNode remove(ListNode cur) {
        ListNode prev = cur.prev;
        ListNode next = cur.next;
        prev.next = next;
        next.prev = prev;
        return cur;
    }
    private void addFirst(ListNode cur) {
        ListNode next = head.next;
        head.next = cur;
        cur.prev = head;
        cur.next = next;
        next.prev = cur;
    }
    private void addLast(ListNode cur) {
        ListNode prev = tail.prev;
        tail.prev = cur;
        cur.next = tail;
        prev.next = cur;
        cur.prev = prev;
    }
    public int move(String dir) {
        ListNode snakeHead = head.next;
        int i = snakeHead.i, j = snakeHead.j;
        System.out.println("from i:" + i + " j:" + j + " move " + dir);
        ListNode snakeTail = remove(tail.prev);
        snake.remove(snakeTail);
        switch (dir) {
            case "U": i--; break;
            case "D": i++; break;
            case "L": j--; break;
            case "R": j++; break;
        }
        ListNode newSnakeHead = new ListNode(i, j);
        if (i < 0 || i == height || j < 0 || j == width || snake.contains(newSnakeHead)) {
            return -1;
        }
        addFirst(newSnakeHead);
        snake.add(newSnakeHead);
        if (foods.get(fi)[0] == newSnakeHead.i && foods.get(fi)[1] == newSnakeHead.j) {
            System.out.println("eat food at i:" + foods.get(fi)[0] + " j:" + foods.get(fi)[1]);
            score++;
            addLast(snakeTail);
            snake.add(snakeTail);
            fi++;
        }
        return score;
    }
}
