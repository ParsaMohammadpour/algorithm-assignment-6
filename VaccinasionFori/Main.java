import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Main {
    static ArrayList<Node>[] ways;
    static boolean[] station;
    static int n, m;

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {
            String[] line = reader.readLine().split(" ");
            n = Integer.parseInt(line[0]);
            m = Integer.parseInt(line[1]);
            ArrayList<Integer> reds = new ArrayList<>();
            station = new boolean[n + 1];
            ways = new ArrayList[n + 1];
            for (int i = 1; i < n + 1; i++) {
                line = reader.readLine().split(" ");
                if (Integer.parseInt(line[0]) == 1)
                    reds.add(i);
                if (Integer.parseInt(line[1]) == 1)
                    station[i] = true;
                ways[i] = new ArrayList<>();
            }
            int n1, n2;
            long n3;
            for (int i = 0; i < m; i++) {
                line = reader.readLine().split(" ");
                n1 = Integer.parseInt(line[0]);
                n2 = Integer.parseInt(line[1]);
                n3 = Long.parseLong(line[2]);
                ways[n1].add(new Node(n2, n3));
                ways[n2].add(new Node(n1, n3));
            }
            for (int i = 0; i < reds.size(); i++) {
                writer.write(reds.get(i) + " " + Dijkstra(reds.get(i)) + "\n");
                writer.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static long Dijkstra(int start) {
        if (station[start])
            return 0;
        PriorityQueue<Node> heap = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node node, Node t1) {
                return Long.compare(node.price, t1.price);
            }
        });
        long[] d = new long[n + 1];
        for (Node node :
                ways[start]) {
            heap.add(new Node(node.city, node.price));
            d[node.city] = node.price;
        }
        Node minNode;
        while (heap.size() > 0) {
            minNode = heap.poll();
            if (station[minNode.city])
                return d[minNode.city];
            for (Node node :
                    ways[minNode.city]) {
                if (d[node.city] == 0 || d[minNode.city] + node.price < d[node.city]){
                    d[node.city] = d[minNode.city] + node.price;
                    heap.add(new Node(node.city, d[node.city]));
                }
            }
        }
        return -1;
    }
}

class Node {
    int city;
    long price;

    public Node(int city, long price) {
        this.city = city;
        this.price = price;
    }
}