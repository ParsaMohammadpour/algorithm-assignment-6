import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Main {
    static ArrayList<Node>[] ways;
    static int[][] graph;
    static int[] map;
    static ArrayList<Integer> destination;
    static int n, m, t, j;
    static int stableTime = 0;
    static int max = Integer.MIN_VALUE;
    static int[] zero;
    static int[] dynamic;
    static boolean[] passed;
    static long answer = Long.MAX_VALUE;
    static boolean isJetUsed = false;


    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {
            String[] lines = reader.readLine().split(" ");
            n = Integer.parseInt(lines[0]);
            m = Integer.parseInt(lines[1]);
            int k = Integer.parseInt(lines[2]);
            t = Integer.parseInt(lines[3]);
            j = Integer.parseInt(lines[4]);
            destination = new ArrayList();
            map = new int[n];
            for (int i = 0; i < m; i++) {
                lines = reader.readLine().split(" ");
                int temp = Integer.parseInt(lines[0]);
                if (temp != 0) {
                    destination.add(temp);
                    map[destination.get(destination.size() - 1)] = destination.size();
                }
                stableTime += Long.parseLong(lines[1]);
            }
            graph = new int[destination.size()][destination.size()];
            dynamic = new int[destination.size()];
            ways = new ArrayList[n];
            for (int i = 0; i < k; i++) {
                lines = reader.readLine().split(" ");
                int n1, n2;
                int w;
                n1 = Integer.parseInt(lines[0]);
                n2 = Integer.parseInt(lines[1]);
                w = Integer.parseInt(lines[2]);
                if (ways[n1] == null)
                    ways[n1] = new ArrayList<>();
                if (ways[n2] == null)
                    ways[n2] = new ArrayList<>();
                ways[n1].add(new Node(n2, w));
                ways[n2].add(new Node(n1, w));
            }
            zero = new int[m];
            for (int i = 0; i < destination.size(); i++) {
                Dijkstra(destination.get(i), i);
            }
            passed = new boolean[destination.size()];
//            System.out.println("Fasele sefr ta har kodom : " + Arrays.toString(zero));
//            System.out.println("Index of the city in the graph : ");
//            for (int i = 0; i < map.length; i++) {
//                System.out.print("city number : " + i + "->"+ (map[i] - 1) + "    ");
//            }
//            System.out.println();
//            for (int i = 0; i < graph.length; i++) {
//                System.out.println("Fasele har node graph : " + Arrays.toString(graph[i]));
//            }
            TSP(0, 0, -1, -1);
//            System.out.println(answer);
            if (answer == -1) {
                writer.write("NO\n");
                writer.flush();
            } else {
                if (answer <= t) {
                    writer.write("YES+JET\n");
                    writer.flush();
                } else {
                    writer.write("NO\n");
                    writer.flush();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void Dijkstra(int start, int i) {
        PriorityQueue<Node> heap = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node node, Node t1) {
                return Long.compare(node.price, t1.price);
            }
        });
        int[] d = new int[n];
        if (ways[start] == null)
            return;
        for (Node node :
                ways[start]) {
            heap.add(new Node(node.city, node.price));
            if (d[node.city] == 0 || d[node.city] > node.price)
                d[node.city] = node.price;
        }
        Node minNode;
        while (heap.size() > 0) {
            minNode = heap.poll();
            for (Node node :
                    ways[minNode.city]) {
                if ((node.city != start) && (d[node.city] == 0 || d[minNode.city] + node.price < d[node.city])) {
                    d[node.city] = d[minNode.city] + node.price;
                    heap.add(new Node(node.city, d[node.city]));
                }
            }
        }
        for (int k = 0; k < d.length; k++) {
            if (map[k] != 0)
                graph[i][map[k] - 1] = d[k];
        }
        if (d[i] != 0 && zero[i] != 0)
            zero[i] = Integer.min(d[0], zero[i]);
        else
            zero[i] = d[0];
    }


    static void TSP(int counter, long current, int pre, long max) throws Exception{
//            System.out.println(current + "    " + pre + "   " + counter + "    " + Arrays.toString(passed) + "    " + answer);
        if (counter == passed.length){
            if (stableTime + current + zero[pre] <= t){
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
                writer.write("YES\n");
                writer.flush();
                System.exit(0);
            }
            if (stableTime + current + zero[pre] +j - max < answer){
                answer = stableTime + current + zero[pre] +j - max;
                isJetUsed = true;
            }
            return;
        }
        for (int i = 0; i < passed.length; i++) {
            if (passed[i])
                continue;
            if ((pre != -1) && (graph[map[destination.get(i)] - 1][pre] != 0)){
                passed[i] = true;
                TSP(counter + 1, current + graph[map[destination.get(i)] - 1][pre],map[destination.get(i)] - 1, Long.max(max,graph[map[destination.get(i)] - 1][pre] ));
                passed[i] = false;
            }else if ((pre == -1) && (zero[map[destination.get(i)] - 1] != 0)){
                passed[i] = true;
                TSP(counter + 1, current + zero[map[destination.get(i)] - 1],map[destination.get(i)] - 1, Long.max(max, zero[map[destination.get(i)] - 1]));
                passed[i] = false;
            }else {
                answer = -1;
                return;
            }
        }
    }
}

class Node {
    int city;
    int price;

    public Node(int city, int price) {
        this.city = city;
        this.price = price;
    }
}