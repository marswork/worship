/*

打棒球得分，给了一个String[] input，求最终score
- 如果是 integer， 就加给score（有负值）
- 如果是“x”, 将上一个值double ，加给score； 若没有上一个值，上一个值按0计算
- 如果是“z”, 上一个成绩作废， score剪掉上一值
- 如果是“+”，将上两个值相加，然后加给score

我的解法是用一个stack挨个处理，input是个string[];
题目我看了半天，stack的操作应该是
- z直接pop；
- x先pop，然后再将double的值放进去；
- +是先pop出来两个值，加给score后，在按原样放回去，并把他俩的和也放进去

例子： 输入 ["5", "-2", "4", "Z", "X", 9, "+", "+"]
输出 27

5 : sum = 5
-2 : sum = 5 - 2 = 3
4 : sum = 3 + 4 = 7
Z : sum = 7 - 4 = 3
X : sum = 3 + -2 * 2 = -1 (因为4被移除了，前一个成绩是-2)
9 : sum = -1 + 9 = 8
+ : sum = 8 + 9 - 4 = 13 (前两个成绩是9和-4)
+ : sum = 13 + 9 + 5 = 27 (前两个成绩是5 和 9)

Note: 0 for any missing score
 */

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class BaseBallScore {
    private Stack<Integer> scoreHistory = new Stack<>();
    private long totalScore = 0;
    // Holds three operators: Plus, X and Z
    private Map<String, Operator> ops = new HashMap<String, Operator>() {{
        put("+", new PlusOperator());
        put("X", new XOperator());
        put("Z", new ZOperator());
    }};

    public long computeScore(String[] input) {
        for (String op : input) {
            if (ops.containsKey(op)) {
                totalScore += ops.get(op).eval(scoreHistory);
            } else {
                totalScore += numOperator(op);
            }
        }

        return totalScore;
    }


    private int numOperator(String token) {
        int num = Integer.valueOf(token);
        scoreHistory.push(num);
        return num;
    }


    private interface Operator {
        long eval(Stack<Integer> scoreHistory);
    }

    private class PlusOperator implements Operator {
        @Override
        public long eval(Stack<Integer> scoreHistory) {
            // Order matters as we are operating with stack
            int y = scoreHistory.isEmpty() ? 0 : scoreHistory.pop();
            int x = scoreHistory.isEmpty() ? 0 : scoreHistory.pop();
            int sum = x + y;

            // Order matters as we are operating with stack
            scoreHistory.push(x);
            scoreHistory.push(y);
            scoreHistory.push(sum);

            return sum;
        }
    }

    private class XOperator implements Operator {
        @Override
        public long eval(Stack<Integer> scoreHistory) {
            int x = scoreHistory.isEmpty() ? 0 : scoreHistory.pop();
            int y = x * 2;
            scoreHistory.push(y);
            return y;
        }
    }

    private class ZOperator implements Operator {
        @Override
        public long eval(Stack<Integer> scoreHistory) {
            int x = scoreHistory.isEmpty() ? 0 : scoreHistory.pop();

            // Negate x as we are going to subtract it from the total score
            // using addition.
            return -x;
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello, Alexa!");
        String[] input = {"5", "-2", "4", "Z", "X", "9", "+", "+"};
        BaseBallScore sol = new BaseBallScore();
        System.out.printf("expected: 27, actual: %d\n", sol.computeScore(input));
    }
}
