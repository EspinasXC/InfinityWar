package avengers;

/**
 * Given a starting event and an Adjacency Matrix representing a graph of all possible 
 * events once Thanos arrives on Titan, determine the total possible number of timelines 
 * that could occur AND the number of timelines with a total Expected Utility (EU) at 
 * least the threshold value.
 * 
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * UseTimeStoneInputFile name is passed through the command line as args[0]
 * Read from UseTimeStoneInputFile with the format:
 *    1. t (int): expected utility (EU) threshold
 *    2. v (int): number of events (vertices in the graph)
 *    3. v lines, each with 2 values: (int) event number and (int) EU value
 *    4. v lines, each with v (int) edges: 1 means there is a direct edge between two vertices, 0 no edge
 * 
 * Note 1: the last v lines of the UseTimeStoneInputFile is an ajacency matrix for a directed
 * graph. 
 * The rows represent the "from" vertex and the columns represent the "to" vertex.
 * 
 * The matrix below has only two edges: (1) from vertex 1 to vertex 3 and, (2) from vertex 2 to vertex 0
 * 0 0 0 0
 * 0 0 0 1
 * 1 0 0 0
 * 0 0 0 0
 * 
 * Step 2:
 * UseTimeStoneOutputFile name is passed through the command line as args[1]
 * Assume the starting event is vertex 0 (zero)
 * Compute all the possible timelines, output this number to the output file.
 * Compute all the posssible timelines with Expected Utility higher than the EU threshold,
 * output this number to the output file.
 * 
 * Note 2: output these number the in above order, one per line.
 * 
 * Note 3: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut:
 *     StdOut.setFile(outputfilename);
 *     //Call StdOut.print() for total number of timelines
 *     //Call StdOut.print() for number of timelines with EU >= threshold EU 
 * 
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/UseTimeStone usetimestone.in usetimestone.out
 * 
 * @author Yashas Ravi
 * 
 */

public class UseTimeStone {

    static int pathCounter(int x, int adjacencyMatrix[][], int count){
        
        for (int i = 0; i < adjacencyMatrix.length; i++){

            if (adjacencyMatrix[x][i] == 1){
                count++;
                count = pathCounter(i, adjacencyMatrix, count);
            }
            }
        return count;
    } 

    static int[] dfs1(int x, int adjacencyMatrix[][], int[] eu, int[] pathCost,int ipathCost){

        for (int i = 0; i < adjacencyMatrix.length; i++){

            if (adjacencyMatrix[x][i] == 1){

                ipathCost += eu[i];

                pathCost[0]++;

                pathCost[pathCost[0]] = ipathCost;

                pathCost = dfs1(i, adjacencyMatrix, eu, pathCost, ipathCost);

                ipathCost = ipathCost - eu[i];
            }

            }

        return pathCost;
    } 

    public static void main (String [] args) {
    	
        //Initialization
        //-------------------------------------------------------------------
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);
    	
        int threshold = StdIn.readInt();
        int V = StdIn.readInt();
        int adjacencyMatrix[][] = new int[V][V];
        int[] eu = new int[V];
        
        for(int i = 0; i < V; i++){
            StdIn.readInt();
            eu[i] = StdIn.readInt();
        }

        for(int i = 0; i < V; i++){
            for(int j = 0; j < V; j++){
                adjacencyMatrix[i][j] = StdIn.readInt();
            }
        }
        //-------------------------------------------------------------------

        //Counting paths
        int totalPaths = pathCounter(0, adjacencyMatrix, 1);

        int[] pathCost = new int[totalPaths + 1];

        //All take the EU of the starting event, so add it
        //First index of array is the path number
        pathCost[0] = 1;
        pathCost[1] = eu[0];
        
        pathCost = dfs1(0, adjacencyMatrix, eu, pathCost, eu[0]);

        StdOut.println(totalPaths);

        //Print out result
        //-------------------------------------------------------------------
        int count = 0;
        for(int i = 1; i < pathCost.length;i++){
            if(pathCost[i] >= threshold){
                count++;
            }
        }
        StdOut.println(count);
        //-------------------------------------------------------------------

    }
}
