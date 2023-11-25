import java.io.BufferedReader;
import java.io.*;
import java.sql.Array;
import java.util.ArrayList;

public class Main {

    static int n; // possibile stop times
    static int m; // min. time between departures
    static int k; // number of bus stops
    static ArrayList<Integer> costs= new ArrayList<>();

    static int[][] optValues;

    public static void main(String[] args) throws IOException {

        File file = new File("C:\\Users\\bkn25\\IdeaProjects\\Bussin\\src\\stdin");
        BufferedReader br = new BufferedReader(new FileReader(file));

//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] constants = br.readLine().split(" ");
        n = Integer.parseInt(constants[0]);
        m = Integer.parseInt(constants[1]);
        k = Integer.parseInt(constants[2]);

        String[] costString = br.readLine().split(" ");
        for (int i = 0; i < n; i++) {
            costs.add(Integer.parseInt(costString[i]));
        }
        for (int i = 0; i < m; i++) {
            costs.add(0);
        }

        optValues= new int[k][n+m]; // makes the thing
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < n+m; j++) {
                optValues[i][j]=-1;
            }
        }

        System.out.println(OPT(k,1));
        System.out.println("Hello world!");
    }

    // returns smallest total lateness with b buses from time to n
    // or if time > n, returns 0
    public static int OPT(int buses, int time) {
        buses--;
        time--;
        if (optValues[buses][time] != -1) {
            return optValues[buses][time];
        }
        else if (buses == 1 && time >= n) { // one bus left
            optValues[buses][time]=lateness(time, n);
            return optValues[buses][time];
        }
        else if (buses == 1 && time < n) { // last bus is after n, place in first available spot
            optValues[buses][time]=0;
            return optValues[buses][time];
        }
        else { // find min
            int min = 999999999;
            int nextBus = -1;
            for (int i = time + m; i < n + m; i++) { // in each iteration,
                int a = OPT(buses - 1, i);
                if (a < min) {
                    min = a;
                    nextBus = i;
                }
            }
            optValues[buses][time] = min + lateness(time, nextBus);
            return optValues[buses][time];
        }
    }


    public static int lateness(int start, int finish) {
        int sum = 0;
        for (int i = start; i < finish + 1; i++) { // e.g., when it's (1, n), loops from i=1 to i=n
            sum += costs.get(i - 1) * (finish - i); // price at that time
        }
        return sum;
    }

}
