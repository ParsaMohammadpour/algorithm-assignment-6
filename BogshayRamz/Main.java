import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    static int n;
    static char[] binary;
    static ArrayList<String> pm;
    static int end;

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {
            ArrayList<String> sorted = new ArrayList<>();
            ArrayList<String> temp = new ArrayList<>();
            n = Integer.parseInt(reader.readLine());
            pm = new ArrayList<>();
            String s;
            for (int i = 0; i < n; i++) {
                s = reader.readLine();
                if (s.toLowerCase().contains("mosi"))
                    sorted.add(s);
                else
                    temp.add(s);
            }
            binary = reader.readLine().toCharArray();
            sorted.sort(String::compareTo);
            temp.sort(String::compareTo);
            String[] allSorted = new String[sorted.size() + temp.size()];
            for (int i = 0; i < allSorted.length; i++) {
                if (i < sorted.size())
                    allSorted[i] = sorted.get(i);
                else
                    allSorted[i] = temp.get(i - sorted.size());
            }
            end = binary.length;
            find_PM(allSorted);
            for (int i = pm.size() - 1; i >= 0; i--) {
                writer.write(pm.get(i) + "\n");
                writer.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void find_PM(String[] temp) {
//        System.out.println("********************************************************************************************");
//        System.out.println(pm.toString());
//        System.out.println(Arrays.toString(temp));
        if (temp.length == 1) {
            pm.add(temp[0]);
            return;
        }
        int startIndex = end - temp.length;
//        System.out.println(Arrays.toString(binary));
//        System.out.println("End : " + end);
//        System.out.println("For Loop Start : " + startIndex + "    End Of The Loop : " + (startIndex + temp.length - 1));
        String[] leftTree = new String[temp.length / 2], rightTree = new String[(temp.length + 1) / 2];
        int left_counter = (temp.length / 2) - 1;
        int right_counter = ((temp.length + 1) / 2) - 1;
        for (int i = startIndex + temp.length - 1; i >= startIndex; i--) {
            end--;
//            System.out.print(left_counter + "   ");
            if (binary[i] == '0')
                leftTree[left_counter--] = temp[i - startIndex];
            else
                rightTree[right_counter--] = temp[i - startIndex];
        }
        find_PM(rightTree);
        find_PM(leftTree);
    }
}