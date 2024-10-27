import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class FastResponsekServerPlacement {

    //Greedy technique which to find the weighted median location for one server to be placed(e.g. W(i,j,1) case) .
    public static int findWeightedMedian(int[] w, int i, int j) {
        int totalWeight = 0;
        for (int x = i; x <= j; x++) {
            totalWeight += w[x];
        }

        double halfWeight = totalWeight / 2.0;

        //Compute prefix sums to find the weighted median
        int[] prefixSum = new int[j - i + 1];
        prefixSum[0] = w[i];
        for (int k = 1; k < prefixSum.length; k++) {
            prefixSum[k] = prefixSum[k - 1] + w[i + k];
        }

        // Find the smallest index where prefixSum >= halfWeight
        for (int k = 0; k < prefixSum.length; k++) {
            if (prefixSum[k] >= halfWeight) {
                return i + k;  //Return the optimal location (index in original array)
            }
        }
        return i;  //Fallback (shouldn't happen in practice)
    }

    //This part finds the minimum cost with k servers placed optimally.
    public static int minTrafficCost(int[] w, int n, int k, int[][][] splitPoint) {
        // Handle base cases
        if (n == 1 && k == 1) {
            return 0;  //Set Cost as 0 when only one server, one client
        }
        if (n == 1 && k > 1) {
            return Integer.MAX_VALUE;  //Set to cost is infinity one client gets more than one server
        }

        //Initialize a 3D DP table with MAX_VALUE
        int[][][] dp = new int[n][n][k + 1];
        for (int[][] mat : dp) {
            for (int[] row : mat) {
                Arrays.fill(row, Integer.MAX_VALUE);
            }
        }

        //Base case: Only 1 server
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                int medianLocation = findWeightedMedian(w, i, j);
                dp[i][j][1] = 0;
                for (int x = i; x <= j; x++) {
                    dp[i][j][1] += w[x] * Math.abs(x - medianLocation);
                }
            }
        }

        //Fill the table for more than 1 server
        for (int m = 2; m <= k; m++) {  //For m = 2 to k servers
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    for (int x = i; x < j; x++) {
                        if (dp[i][x][m - 1] != Integer.MAX_VALUE && dp[x + 1][j][1] != Integer.MAX_VALUE) {
                            int cost = dp[i][x][m - 1] + dp[x + 1][j][1];
                            if (cost < dp[i][j][m]) {
                                dp[i][j][m] = cost;
                                splitPoint[i][j][m] = x;  //Store the split point
                            }
                        }
                    }
                }
            }
        }

        // Returns minimum cost for placing k servers across all n clients.
        return dp[0][n - 1][k];
    }

    //Function for optimal server locations
    public static void findOptimalLocations(int[] w, int[][][] splitPoint, int i, int j, int m, int[] serverLocations, int index) {
        if (m == 1) {
            serverLocations[index] = findWeightedMedian(w, i, j);
            return;
        }
        int x = splitPoint[i][j][m]; //Get the split point
        findOptimalLocations(w, splitPoint, i, x, m - 1, serverLocations, index);
        findOptimalLocations(w, splitPoint, x + 1, j, 1, serverLocations, index + 1);
    }

    //Main method takes input from user i.e. no of Clients and Servers
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        //Take user input for size of the array and number of servers
        System.out.print("Enter number of Clients(n): ");
        int n = scanner.nextInt();
        System.out.print("Number of servers available(k): ");
        int k = scanner.nextInt();

        //Random weights in array
        int[] w = new int[n];
        for (int i = 0; i < n; i++) {
            w[i] = random.nextInt(100) + 1; //Range of values in array(weight) between 1 and 100
        }

        int[][][] splitPoint = new int[n][n][k + 1];

        //Start Clock
        long startTime = System.nanoTime();

        //Function call for calculating minimum cost
        int minCost = minTrafficCost(w, n, k, splitPoint);

        //Stop Clock
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime; //Time in ns

        System.out.println("Minimum cost with " + k + " servers: " + minCost);
        System.out.println("Elapsed time: " + elapsedTime + " ns");

        //Array to store the optimal server locations
        int[] serverLocations = new int[k];
        findOptimalLocations(w, splitPoint, 0, n - 1, k, serverLocations, 0);

        //Print the optimal server locations
//        System.out.print("Optimal locations of servers(indices): ");
//        for (int loc : serverLocations) {
//            System.out.print(loc + " ");
//        }
//        System.out.println();

        scanner.close();
    }
}
