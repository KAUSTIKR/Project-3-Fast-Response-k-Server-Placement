# Project-3-Fast-Response-k-Server-Placement
Using a dynamic programming approach, we store previously computed results to avoid redundant computations. 
This optimizes the solution, reducing the problem from exponential time (without dynamic programming) to polynomial time. 
When only one server remains for n clients, the problem can be solved efficiently using a greedy approach with the weighted median, which takes O(n) time. Since we employ dynamic programming, additional space of O(k.n2) is required to store intermediate results and prevent recalculations. 
The experimental and theoretical graphs, when plotted, show the same pattern, which is O(k.n3).
