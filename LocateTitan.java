package avengers;

/**
 * 
 * Using the Adjacency Matrix of n vertices and starting from Earth (vertex 0), 
 * modify the edge weights using the functionality values of the vertices that each edge 
 * connects, and then determine the minimum cost to reach Titan (vertex n-1) from Earth (vertex 0).
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * LocateTitanInputFile name is passed through the command line as args[0]
 * Read from LocateTitanInputFile with the format:
 *    1. g (int): number of generators (vertices in the graph)
 *    2. g lines, each with 2 values, (int) generator number, (double) funcionality value
 *    3. g lines, each with g (int) edge values, referring to the energy cost to travel from 
 *       one generator to another 
 * Create an adjacency matrix for g generators.
 * 
 * Populate the adjacency matrix with edge values (the energy cost to travel from one 
 * generator to another).
 * 
 * Step 2:
 * Update the adjacency matrix to change EVERY edge weight (energy cost) by DIVIDING it 
 * by the functionality of BOTH vertices (generators) that the edge points to. Then, 
 * typecast this number to an integer (this is done to avoid precision errors). The result 
 * is an adjacency matrix representing the TOTAL COSTS to travel from one generator to another.
 * 
 * Step 3:
 * LocateTitanOutputFile name is passed through the command line as args[1]
 * Use Dijkstra’s Algorithm to find the path of minimum cost between Earth and Titan. 
 * Output this number into your output file!
 * 
 * Note: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut (here, minCost represents the minimum cost to 
 *   travel from Earth to Titan):
 *     StdOut.setFile(outputfilename);
 *     StdOut.print(minCost);
 *  
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/LocateTitan locatetitan.in locatetitan.out
 * 
 * @author Yashas Ravi
 * 
 */

public class LocateTitan {
    
    public static void main (String [] args) {
    
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);

        int adjacencyMatrix[][];
        Double functionality[];

        int V = StdIn.readInt();

        adjacencyMatrix = new int[V][V];
        functionality = new Double[V];

        for(int i = 0; i < V; i++){
            StdIn.readInt();
            functionality[i] = StdIn.readDouble();
        }

        for(int i = 0; i < V; i++){
            for(int j = 0; j < V; j++){
                adjacencyMatrix[i][j] = StdIn.readInt();
            }
        }

        for(int i = 0; i < V; i++){
            for(int j = 0; j < V; j++){
                adjacencyMatrix[i][j] = (int) ((double) adjacencyMatrix[i][j]/(functionality[i] * functionality[j]));
            }
        }

        //Dijkstra's Algorithm
        int minCost[] = new int[V]; //keeps track of min cost
        //to reach each node from node 0

        boolean DijkstraSet[] = new boolean[V]; 
        //Array that keeps track
        //of which nodes are on path already
        
        minCost[0] = 0;
        for (int i = 1; i < V;i++){
            minCost[i] = Integer.MAX_VALUE;
            DijkstraSet[i] = false;
        }

        //Find least value path

        for (int i = 0; i < V - 1; i++){
        
        int currentSource = -1;

        //
        for(int x = 0; x < V; x++){
            if(!DijkstraSet[x] && (currentSource == -1 || minCost[x] < minCost[currentSource])){
                currentSource = x;
            }
        }

        DijkstraSet[currentSource] = true;

        for(int j = 0; j < V; j++){

            if(!DijkstraSet[j] &&
            adjacencyMatrix[currentSource][j] != 0 &&
            minCost[currentSource] != Integer.MAX_VALUE
            ){
                int newDist = minCost[currentSource] + adjacencyMatrix[currentSource][j];
                if(newDist < minCost[j]){
                    minCost[j] = newDist;
                }
            }
        }
    }
    StdOut.println(minCost[V-1]);
    
    
}
}

