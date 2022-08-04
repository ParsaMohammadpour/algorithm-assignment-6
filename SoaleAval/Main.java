import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.PriorityQueue;

public class Main {

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {
            int n, keyNumber, ballNumber;
            PriorityQueue<Integer> keys = new PriorityQueue<>();
            PriorityQueue<Integer> balls = new PriorityQueue<>();
            String[] line = reader.readLine().split(" ");
            n = Integer.parseInt(line[0]);
            keyNumber = Integer.parseInt(line[1]);
            ballNumber = Integer.parseInt(line[2]);
            line = reader.readLine().split(" ");
            boolean[] boxes = new boolean[n + 1];
            int temp, min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
            for (int i = 0; i < line.length; i++) {
                //Keys
                temp = Integer.parseInt(line[i]);
                if (!boxes[temp])
                    keys.add(temp);
                boxes[temp] = true;
                min = Integer.min(temp, min);
                max = Integer.max(max, temp);
            }
            if (ballNumber == 0){
                writer.write("0\n");
                writer.flush();
                System.exit(0);
            }
            line = reader.readLine().split(" ");
            boxes = new boolean[n + 1];
            for (int i = 0; i < line.length; i++) {
                //Balls
                temp = Integer.parseInt(line[i]);
                if (!boxes[temp])
                    balls.add(temp);
                boxes[temp] = true;
                min = Integer.min(temp, min);
                max = Integer.max(max, temp);
            }
            int pre = keys.poll(), current = pre;
            int ball = balls.peek();
            int price = 0;
            boolean isFirstOpen = false;
            if (ball <= pre) {
                price = pre - ball + 1;
                isFirstOpen = true;
                while (balls.size() > 0 && balls.peek() <= pre)
                    balls.poll();
            }
            int maxDiffrence;
            while (keys.size() > 0) {
                if (balls.size() == 0)
                    break;
                current = keys.poll();
                if (balls.peek() > current) {
                    pre = current;
                    isFirstOpen = false;
                    continue;
                }
                price += current - pre;
                if (!isFirstOpen)
                    price += 1;
                temp = balls.poll();
                int dif1 = temp - pre;
                if (isFirstOpen)
                    dif1 -= 1;
                maxDiffrence = Integer.MIN_VALUE;
                while (balls.size() > 0 && balls.peek() <= current) {
                    maxDiffrence = Integer.max(maxDiffrence, balls.peek() - temp - 1);
                    temp = balls.poll();
                }
                isFirstOpen = true;
                int dif2 = current - temp;
                price -= Integer.max(Integer.max(maxDiffrence, dif1), dif2);
                if (dif2 > dif1 && dif2 > maxDiffrence)
                    isFirstOpen = false;
                pre = current;
            }
            if (balls.size() > 0) {
                price += max - current;
                if (!isFirstOpen)
                    price++;
            }
            writer.write(price + "\n");
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}