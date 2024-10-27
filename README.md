# Project-3-Fast-Response-k-Server-Placement
Using a dynamic programming approach, we store previously computed results to avoid redundant computations. 
This optimizes the solution, reducing the problem from exponential time (without dynamic programming) to polynomial time. 
When only one server remains for n clients, the problem can be solved efficiently using a greedy approach with the weighted median, which takes O(n) time. Since we employ dynamic programming, additional space of **O(k.n2)** is required to store intermediate results and prevent recalculations. 
The experimental and theoretical graphs, when plotted, show the same pattern, which is **O(k.n3)**.

Program solves the k-server placement problem using dynamic programming. 
The goal is to place k servers across n clients to minimize the total traffic cost, where traffic cost is the sum of traffic weights multiplied by their distances from the nearest server.
The program uses a greedy method to find the optimal location for a single server (weighted median).
A **3D dynamic programming (DP) table ** is used to store intermediate results and avoid redundant computations. It recursively fills the DP table by splitting the clients between different servers and tracking the optimal split points.
After calculating the minimum cost using minTrafficCost(), the program retrieves the optimal server locations using the split points. It also generates random weights for clients, measures execution time, and prints the result. 
The main logic efficiently handles the problem in **O(n²k)** time complexity.
