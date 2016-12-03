/*
 * University of Victoria
 * CSC 225 - Fall 2016
 * 	Graphs DFS Implementat
 *	Daniel Olaya Moran - V00855054
 */
import java.io.*;
import java.util.*;

public class Graph225 {

	/**
	 * Simple representation of an undirected graph, using a square, symmetric
	 * adjacency matrix.
	 * <p>
	 * An adjacency matrix M represents a graph G=(V,E) where V is a set of n
	 * vertices and E is a set of m edges. The size of the matrix is {@code n},
	 * where {@code n} is in the range {@code [4, 15]} only. Thus, the rows and
	 * columns of the matrix are in the range {@code [0, n-1]} representing
	 * vertices. The elements of the matrix are 1 if the edge exists in the
	 * graph and 0 otherwise. Since the graph is undirected, the matrix is
	 * symmetric and contains 2m 1’s.
	 */
	public static class Graph {

		/**
		 * An adjacency matrix representation of this graph
		 */
		private int[][] adjacencyMatrix;
		private int arrSize = 16;	// max array size
		private int [] visited;		//0 for unvisited, 1 for visited
		private int [] id; // stores the component number for each vertex
		private int [] parent; // stores the parent of the visited node
		private	int size;	// actual array size, without -1 fillers
		private int ccTotal; // counter for number of connected componetns
		private boolean hasCycle;
		private int[][] preOrder;
		private int[][] postOrder;
		private int test;


		public Graph() {
			adjacencyMatrix = new int [arrSize][arrSize];
			fillMatrix(-1, arrSize, adjacencyMatrix);
			ccTotal = 0;
		}

		//Helper method, Fills the (n x n) Matrix with int n filler which serves as filler
		public void fillMatrix (int filler, int n, int[][] matrix){

			for (int col = 0 ; col < n ; col++){
				for (int row = 0 ; row < n ; row ++){
					matrix[row][col] = filler;
				}
			}
		}



		//Helper method, Prints adjacencyMatrix on terminal
		public void print (int[][] matrix){
			for (int col = 0 ; col < matrix.length ; col++){
				for (int row = 0 ; row < matrix[col].length ; row++){
					System.out.print(matrix[row][col]);
					System.out.print(" ");
				}
				System.out.println();
			}
		}

		/**
		 * Generate a random graph as specified in the assignment statement.
		 * 
		 * @param n
		 *            The size of the graph
		 * @param density
		 * @param
		 *            The density of the graph
		 */
		public void generate(int n, int density) {
			fillMatrix(0, n, adjacencyMatrix);
			if (density == 1){
				int m = (7*n)/5;
				System.out.println("Generating graph with density:" + density + ", m:"+ m );
				fillRandom(n, m);
			}else if (density == 2){
				int m = (n*n)/4;
				System.out.println("Generating graph with density:" + density + ", m:"+ m );
				fillRandom(n, m);
			} else if(density == 3){
				int m = (2*(n*n))/5;
				System.out.println("Generating graph with density:" + density + ", m:"+ m );
				fillRandom(n, m);
			} else {
				System.out.println("Density must be 1, 2 or 3");
			}
			sizeGraph();
		}

		/** 
		 * Helper method, generates two random numbers between 0 and n and uses themt to create a random position in adjacencyMatrix
		 * coutner keeps track of the total edges added.
		 *	@param n - graph size
		 *	@param m - number of random edges to generate
		 */
		public void fillRandom (int n, int m){
			int randCol = 0;
			int randRow = 0;
			int counter = 0;
			Random rand = new Random ();
			while (counter < m){
				randRow = rand.nextInt(n);
				randCol = rand.nextInt(n);
				if (adjacencyMatrix[randRow][randCol] != 1 && (randRow != randCol)){ //checks for self-loops 
					adjacencyMatrix[randRow][randCol] = 1;
					adjacencyMatrix[randCol][randRow] = 1;
					counter ++;
				}
			}
		}

		/**
		 * Reads an adjacency matrix from the specified file, and updates this
		 * graph's data. For the file structure please refer to the sample input
		 * file {@code testadjmat.txt}).
		 * 
		 * @param file
		 *            The input file
		 * @throws IOException
		 *             If something bad happens while reading the input file.
		 */
		public void read(String file) throws IOException 
		{
			File path = new File (file);
			Scanner fileReader = new Scanner (path);
			int col=0; 	//rows
			int row=0;	//columns
			while (fileReader.hasNextLine())
			{	
				String line = fileReader.nextLine();
				Scanner tokenizer = new Scanner (line);
				while (tokenizer.hasNextInt())
				{
					int token = tokenizer.nextInt();
					adjacencyMatrix[col][row]=token;
					if (col < adjacencyMatrix.length-1)
					{
						col ++;
					}
				}
				col = 0;
				row ++;
			}
			sizeGraph();
		}

		/**
		 * Writes the adjacency matrix representing this graph in the specified
		 * file.
		 * 
		 * @param file
		 *            The path of the output file
		 * @throws IOException
		 *             If something bad happens while writing the file.
		 */
		public void write(String file) throws IOException {
			FileWriter fw = new FileWriter (file);
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 0 ; adjacencyMatrix[i][0] != -1 ; i++){//condition stops the loop when -1 found
				for (int j = 0 ; adjacencyMatrix[i][j] != -1 ; j++){
					bw.write(String.valueOf(adjacencyMatrix[j][i]));
					bw.write(" ");
				}
				bw.newLine();	
			}
			bw.close();
		}

		/**
		 * @return an adjacency matrix representation of this graph
		 */
		public int[][] getAdjacencyMatrix() {
			return this.adjacencyMatrix;
		}

		/**
		 * Updates this graph's adjacency matrix
		 * 
		 * @param m
		 *            The adjacency matrix representing the new graph
		 */
		public void setAdjacencyMatrix(int[][] m) {
			this.adjacencyMatrix = m;
		}

		//Helper method, calculates size of Graph
		public void sizeGraph (){
			for (int i =0 ; i < adjacencyMatrix[0].length ; i++){
				if (adjacencyMatrix[0][i] != -1){
					size++;
				}
			}
		}


		/**
		 * Traverses the given graph starting at the specified vertex, using the
		 * depth first search graph traversal algorithm.
		 * <p>
		 * <b>NOTICE</b>: adjacent vertices must be visited in strictly increasing
		 * order (for automated marking)
		 * 
		 * @param graph
		 *            The graph to traverse
		 * @param vertex
		 *            The starting vertex (as per its position in the adjacency
		 *            matrix)
		 * @return a vector R of n elements where R[j] is 1 if vertex j can be
		 *         reached from {@code vertex} and 0 otherwise
		 */
		public int[] reach(Graph graph, int vertex) {
			Stack <Integer> stack = new Stack <Integer>();
			visited = new int [size];
			id = new int [size];
			parent = new int [size];
			dfs(vertex);
			return visited;	
		}

		public void printArr (int[] arr){
			for (int i =0 ; i < arr.length ; i ++)
			{
				System.out.print(arr[i]);
				System.out.print(" ");
			}
			System.out.println();
			System.out.println();
		}

		public void dfs (int vertex){
			Stack <Integer> stack = new Stack <Integer>();
			int curr = 0;
			int parentIndex = vertex;
			stack.push(vertex);			
			while (!stack.empty()){
				curr = stack.pop();
				if (visited[curr] == 0){
					id[curr] = ccTotal; 	//marks CC number
					visited[curr] = 1;		//mark parent after discovery
					for (int i=size-1 ; i >= 0 ; i--){
						if (adjacencyMatrix[curr][i] == 1 && visited[i] == 0){
							stack.push(i);
							parent[i] = curr;
						}
						if ( (parent[curr] != i) && (adjacencyMatrix[curr][i] == 1 && visited[i] == 1) ){
							hasCycle = true;
						}
					}
				}
			}
		}

		public void dfsPre (int vertex){
			Stack <Integer> stack = new Stack <Integer>();
			//Stack <Integer> posty = new Stack <Integer>();
			//int postySize =0;
			visited = new int [size];
			id = new int [size];
			parent = new int [size];
			int curr = 0;
			//int postCounter =0;
			int preCounter =0;
			boolean skipFirst = true;
			int parentIndex = vertex;
			stack.push(vertex);
			//int [] postAdded = new int [size];
			//postAdded[vertex] =1;
			//System.out.println("DFS Method, Vertex: " + stack.peek());
			while (!stack.empty()){
				//boolean allChildrenVisited = true;
				//System.out.println("\n---====NEW WHILE LOOP=====---- Curr(poped):" +stack.peek()+ ", parentIndex:" + parentIndex);
				curr = stack.pop();
				//System.out.println("Has Curr been visited? Visited[Curr]:" + visited[curr]);
				if (visited[curr] == 0){
					id[curr] = ccTotal; 	//marks CC number
					visited[curr] = 1;		//mark parent after discovery
					if (!skipFirst){	//Skips recording the root node on the final preOrder matrix
						preOrder[preCounter][vertex] = curr;
						preCounter++;
					}
					if (skipFirst){
						skipFirst =false;
					}
					//System.out.println("****PREORDER, changing preOrder[preCounter:"+preCounter+"][vertex:"+vertex+"] = curr:"+curr);
					//System.out.println("M unvisited, changing visited[curr] = 1, parent["+curr+"]:" + parentIndex);
					for (int i=size-1 ; i >= 0 ; i--){
						//System.out.println("Looking for edges (1) - adjacencyMatrix[curr:"+curr+"][i:" +i+"] = " + adjacencyMatrix[curr][i]);
						if (adjacencyMatrix[curr][i] == 1 && visited[i] == 0){
							//System.out.println("DFS PUSHING:" + i + ", parent[i:"+i+"] = "+curr);
							stack.push(i);
							parent[i] = curr;
							//allChildrenVisited = false;
							//posty.push(curr);
							//postCounter++;
							//System.out.println("+++POSTY["+curr+"] ="+curr);
						}
						//hasCyle method
						if ( (parent[curr] != i) && (adjacencyMatrix[curr][i] == 1 && visited[i] == 1) ){
							hasCycle = true;
						}
					}
				}
				/*
				if (allChildrenVisited == true){ 
					if (!skipFirst){	
						postOrder[postCounter][vertex] = curr;
						System.out.println("---RECORDED VERTEX: "+ curr);
						postCounter++;
						postAdded[curr] = 1;
					}
					if (skipFirst){
						skipFirst =false;
					}
				}*/

			}
			/*
			while (postySize >= 1){
				if (postAdded[posty.peek()] ==0){
					System.out.println("---RECORDED VERTEX:"+posty.peek()+"), postySize:"+postySize);
					postOrder[postCounter][vertex] = posty.pop();
					postCounter++;
					postySize--;
				}
				int mf =posty.pop();
				postySize--;
				System.out.println("Just poped this useless mf "+ mf);
			}
			*/
		}

		/**
		 * Computes the post-order for each vertex in the given graph.
		 * <p>
		 * <b>NOTICE</b>: adjacent vertices must be visited in strictly increasing
		 * order (for automated marking)
		 * 
		 * @param graph
		 *            The graph
		 * @return a vector R of n elements, representing the post-order of
		 *         {@code graph}
		 */
		public int[][] postOrder(Graph graph) {
			postOrder = new int[size][size];/*
			preOrder = new int[size][size];

			fillMatrix(-1, size, postOrder);
			for (int i = 0 ; i <= size-1; i++){
				dfsPre(i);
			}*/
			return postOrder;
		}
		

		/**
		 * Computes the pre-order for each vertex in the given graph.
		 * <p>
		 * <b>NOTICE</b>: adjacent vertices must be visited in strictly increasing
		 * order (for automated marking)
		 * 
		 * @param graph
		 *            The graph
		 * @return a vector R of n elements, representing the pre-order of
		 *         {@code graph}
		 */
		public int[][] preOrder(Graph graph) {			
			//postOrder = new int[size][size];
			preOrder = new int[size][size];
			fillMatrix(-1, size, preOrder);
			for (int i = 0 ; i <= size-1; i++){
				dfsPre(i);
			}
			return preOrder;
		}

		/**
		 * Computes the number connected components of a given graph.
		 * <p>
		 * <b>NOTICE</b>: adjacent vertices must be visited in strictly increasing
		 * order (for automated marking)
		 * 
		 * @param graph
		 *            The graph
		 * @return The number of connected component in {@code graph}
		 */
		public int connectedComponents(Graph graph) {
			visited = new int [size];
			id = new int [size];
			parent = new int [size];
			for (int i = size-1; i>=0 ; i--){
				if (id[i] == 0){
					ccTotal ++;
					dfs(i);
				}
			}
			return ccTotal;	
		}


		/**
		 * Determines whether a given graph contains at least one cycle.
		 * <p>
		 * <b>NOTICE</b>: adjacent vertices must be visited in strictly increasing
		 * order (for automated marking)
		 * 
		 * @param graph
		 *            The graph
		 * @return whether or not {@code graph} contains at least one cycle
		 */
		public boolean hasCycle(Graph graph) {
			hasCycle = false;
			visited = new int [size];
			id = new int [size];
			parent = new int [size];
			for (int i = 0 ; i <= size-1; i++)
			{
				if (id[i] == 0){
					ccTotal ++;
					dfs(i);
				}
			}
			return hasCycle;
		}


		/**
		 * test and exercise the algorithms and data structures developed for the
		 * first five parts of this assignment extensively. The output generated by
		 * this method must convince the marker that the algorithms and data
		 * structures are implemented as specified. For example:
		 * <ul>
		 * <li>Generate graphs of different sizes and densities
		 * <li>Test the algorithms for different graphs
		 * <li>Test your algorithms using the sample input file testadjmat.txt
		 * 
		 * @throws Exception
		 *             if something bad happens!
		 */
		public void test() throws Exception {
			switch (test){
				case 1:
					generate (5,3);
					write("fn.txt");
					System.out.println("\nTest1: Testing Generate(4x4 - density 3), Write (output: fn.txt), HasCycles");		
					System.out.println("\nPrinting adjacencyMatrix:");
					print(adjacencyMatrix);
					break;
				case 2:
					generate (11,2);
					write("fn.txt");
					System.out.println("\nTest2: Testing Generate(10x10 - density 2), Write (output: fn.txt), HasCycles");
					System.out.println("\nPrinting adjacencyMatrix:");
					print(adjacencyMatrix);
					break;
				case 3:
					generate (15,1);
					write("fn.txt");
					System.out.println("\nTest3: Testing Generate(15x15 - density 1), Write (output: fn.txt), HasCycles");
					System.out.println("\nPrinting adjacencyMatrix:");
					print(adjacencyMatrix);
					break;
				case 4:
					System.out.println("Test4: Testing Read, HasCycle, ConnectedComponents, Write from  file: testadjmat.txt");
					read("testadjmat.txt");
					write("fn.txt");
					System.out.println("\nPrinting adjacencyMatrix:");
					print(adjacencyMatrix);
					break;
				case 5:
					System.out.println("Test5: Testing Read, HasCycle, ConnectedComponents, Write from  file: test1.txt");
					read("test1.txt");
					write("fn.txt");
					System.out.println("\nPrinting adjacencyMatrix:");
					print(adjacencyMatrix);
					break;
				case 6:
					System.out.println("Test6: Testing READ, WRITE from file: test2.txt");
					read("test2.txt");
					write("fn.txt");
					System.out.println("\nPrinting adjacencyMatrix:");
					print(adjacencyMatrix);
					break;
				case 7:
					System.out.println("Test7: Pre-order traversal and REACH using testadjmat.txt");
					read("Part5.txt");
					break;	
			}
		}
	}
	public static void main(String[] args) {
		
		//Change graphy.test value from 1 to 3
		//Test: 1; Generates 4x4 density 3, calculates conected componets (terminal), calculates if graph has cycles (terminal), prints adjacency matrix (terminal), creates output file "fn.txt" with adjacency matrix
		//Test: 2; Generates 10x10 density 2, calculates conected componets (terminal), calculates if graph has cycles (terminal), prints adjacency matrix (terminal), creates output file "fn.txt" with adjacency matrix
		//Test: 3; Generates 15x15 density 1, calculates conected componets (terminal), calculates if graph has cycles (terminal), prints adjacency matrix (terminal), creates output file "fn.txt" with adjacency matrix
		//Test: 4; Read from  "testadjmat.xt", calculates conected componets (terminal), calculates if graph has cycles (terminal), prints adjacency matrix (terminal), creates output file "fn.txt" with adjacency matrix
		//Test: 5; Read from  "test1.xt"- No cycle and 1 connected component. calculates conected componets (terminal), calculates if graph has cycles (terminal), prints adjacency matrix (terminal), creates output file "fn.txt" with adjacency matrix
		//Test: 6; Read from  "test2.xt"- No cycle and 2 connected component. calculates conected componets (terminal), calculates if graph has cycles (terminal), prints adjacency matrix (terminal), creates output file "fn.txt" with adjacency matrix	
		Graph graphy = new Graph ();
		graphy.test = 1; 	//Modify this int (from 1 to 6) to run different tests
		try{
			graphy.test();
			int cc = graphy.connectedComponents(graphy);
			boolean cycle = graphy.hasCycle(graphy);
			System.out.println("Connected Components: "+ cc );
			System.out.println("Graph has cycles?: "+ cycle);
			System.out.println("Check file fn.txt for output (write method)");
		} catch (Exception e){
			System.out.println("Unexpected error happened");
		}
		//*/

		/*
		//Test 7: Testing Pre-order traversal with "Part5.txt" file. Pre-order[][] printed on terminal. Reach method results (terminal)
		// Post order testing not included, could not generate 
		Graph graphy = new Graph ();
		try{
			graphy.test = 7; 	//this int should be modified to run different tests
			graphy.test();
			graphy.preOrder(graphy);
			System.out.println("\nPrinting adjacencyMatrix:");
			graphy.print(graphy.adjacencyMatrix);
			System.out.println();
			for(int i = 0 ; i < graphy.size ; i++){
				int []temp = graphy.reach(graphy, i);
				System.out.println("Testing REACH - Printing resulting[] from vertex:" + i);
				graphy.printArr(temp);
			}
		
			System.out.println("\nTesting PRE-Order traversal:");
			graphy.print(graphy.preOrder);
			System.out.println();

		} catch (Exception e){
			System.out.println("Unexpected error happened");
		}
		//*/
	}
}