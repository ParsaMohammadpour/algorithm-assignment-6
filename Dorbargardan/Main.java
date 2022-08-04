import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.PriorityQueue;

public class Main {

    static long s;
    static long f;

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {
            PriorityQueue<Long> y2 = new PriorityQueue<>(), y3 = new PriorityQueue<>(), y4 = new PriorityQueue<>();
            s = Long.parseLong(reader.readLine());
            f = Long.parseLong(reader.readLine());
            long y;
            int n = Integer.parseInt(reader.readLine()), xi, xj;
            String[] line;
            for (int i = 0; i < n; i++) {
                line = reader.readLine().split(" ");
                xi = Integer.parseInt(line[0]);
                xj = Integer.parseInt(line[1]);
                y = Long.parseLong(line[2]);
                switch (Integer.min(xi, xj)) {
                    case 1: {
                        if (y >= s)
                            y2.add(y);
                    }
                    break;
                    case 2:
                        y3.add(y);
                        break;
                    case 3: {
                        if (y >= f)
                            y4.add(y);
                    }
                    break;
                }
            }
            long x = findMinDis(y2,y3,y4);
            if (x == Long.MAX_VALUE){
                writer.write("-1\n");
                writer.flush();
            }else {
                writer.write(x + "\n");
                writer.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static long findMinDis(PriorityQueue<Long> y2, PriorityQueue<Long> y3, PriorityQueue<Long> y4) {
        long answer = Long.MAX_VALUE;
        while (y2.size() > 0 && y3.size() > 0 && y4.size() > 0){
            if (y2.peek() < y3.peek()){
                y2.poll();
                continue;
            }
            if (y4.peek() < y3.peek()){
                y4.poll();
                continue;
            }
            answer = Long.min(answer, y2.peek() - s + y2.peek() - y3.peek() + y4.peek() - y3.peek() + y4.peek() - f + 3);
            y3.poll();
        }
        return answer;
    }
}