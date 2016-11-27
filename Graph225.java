/*
 * University of Victoria
 * CSC 225 - Fall 2016
 * 	Graphs DFS Implementation - A4
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
		int arrSize = 15;

		public Graph() {
			adjacencyMatrix = new int [arrSize][arrSize];
			fillMatrix(-1, 15);
		}

		//Helper method, Fills the (n x n) Matrix with int n filler which serves as filler
		public void fillMatrix (int filler, int n){
			//System.out.println("FillMatrix");
			//System.out.println("n FillMatrix is " + n);
			for (int col = 0 ; col < n ; col++){
				for (int row = 0 ; row < n ; row ++){
					adjacencyMatrix[row][col] = filler;
					//System.out.println("Adding " + filler + " " + adjacencyMatrix[row][col] );
				}
			}
		}

		//Helper method, Prints adjacencyMatrix on terminal
		public void print (){
			for (int col = 0 ; col < adjacencyMatrix.length ; col++){
				for (int row = 0 ; row < adjacencyMatrix[col].length ; row++){
					System.out.print(adjacencyMatrix[row][col]);
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
			fillMatrix(0, n);
			if (density == 1){
				int m = (7*n)/5;
				//System.out.println("Generate - m: " + m + " with d:" + density);
				fillRandom(n, m);
			}else if (density == 2){
				int m = (n*n)/4;
				//System.out.println("Generate - m:" + m  + " with d:" + density);
				fillRandom(n, m);
			} else if(density == 3){
				int m = (2*(n*n))/5;
				//System.out.println("Generate - m:" + m  + " with d:" + density);
				fillRandom(n, m);
			} else {
				System.out.println("Density must be 1, 2 or 3");
			}
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
				if (adjacencyMatrix[randRow][randCol] != 1){
					adjacencyMatrix[randRow][randCol] = 1;
					//System.out.println("Added:" + counter);
					counter ++;
				}
			}
			System.out.println(" fillRandom; Total number of edges added: " + counter);
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
		public void read(String file) throws IOException {
			try{
				File path = new File (file);
				Scanner fileReader = new Scanner (path);
				int col=0; 	//rows
				int row=0;	//columns
				
				while (fileReader.hasNextLine()){	
					String line = fileReader.nextLine();
					Scanner tokenizer = new Scanner (line);
					while (tokenizer.hasNextInt()){
						int token = tokenizer.nextInt();
						adjacencyMatrix[col][row]=token;
						if (col < adjacencyMatrix.length-1){
							col ++;
						}
					}
					col = 0;
					row ++;
				}
				print();
			} catch (IOException io){
				System.out.println("Something bad happens while reading the input file");
			}
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
			for (int i = 0 ; adjacencyMatrix[i][0] != -1 ; i++){ //condition stops the loop when -1 found
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
			throw new UnsupportedOperationException("This method has not been implemented yet.");
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
			throw new UnsupportedOperationException("This method has not been implemented yet.");
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
			throw new UnsupportedOperationException("This method has not been implemented yet.");
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
		public int[] preOrder(Graph graph) {
			throw new UnsupportedOperationException("This method has not been implemented yet.");
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
		public int[] postOrder(Graph graph) {
			throw new UnsupportedOperationException("This method has not been implemented yet.");
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
			throw new UnsupportedOperationException("This method has not been implemented yet.");
		}
	}
	public static void main(String[] args) {

		/*
		//Testing GENERATE/WRITE, matrix size 0x0, density 1. Expected: Empty matrix
		Graph graphy = new Graph ();
		graphy.generate(1,1);
		graphy.print();
		try{
				graphy.write("fn.txt");
			} catch (IOException io){
				System.out.println("An error has ocurred while writting in the file");
			}
		//*/

		/*
		//Testing GENERATE/WRITE, matrix size 0x0, density 1. Expected: Empty matrix
			Graph graphy = new Graph ();
			graphy.generate(0,1);
			graphy.print();
			try{
				graphy.write("fn.txt");
			} catch (IOException io){
				System.out.println("An error has ocurred while writting in the file");
			}
		//*/

		/*
		//Testing GENERATE/WRITE, matrix size 5x5, density 1. Expected: 7 edges in 5x5 matrix
			Graph graphy = new Graph ();
			graphy.generate(5,1);
			graphy.print();
			try{
				graphy.write("fn.txt");
			} catch (IOException io){
				System.out.println("An error has ocurred while writting in the file");
			}
		//*/

		/*
		//Testing GENERATE/WRITE, matrix size 10x10, density 2. Expected: 25 edges in 10x10 matrix
			Graph graphy = new Graph ();
			graphy.generate(10,2);
			graphy.print();
			try{
				graphy.write("fn.txt");
			} catch (IOException io){
				System.out.println("An error has ocurred while writting in the file");
			}
		//*/

		/*
		//Testing GENERATE/WRITE, matrix size 10x10, density 3. Expected: 40 edges in 15x15 matrix
			Graph graphy = new Graph ();
			graphy.generate(10,3);
			graphy.print();
			try{
				graphy.write("fn.txt");
			} catch (IOException io){
				System.out.println("An error has ocurred while writting in the file");
			}
		//*/

		/*
		//Testing GENERATE/WRITE, matrix size 5x5, density 3. Expected: 3 edges in 15x15 matrix
			Graph graphy = new Graph ();
			graphy.generate(5,3);
			graphy.print();
			try{
				graphy.write("fn.txt");
			} catch (IOException io){
				System.out.println("An error has ocurred while writting in the file");
			}

		//*/

		/*
		//Testing GENERATE, matrix size 16 - index out of bounds expected
			graphy.generate(16,5);
			graphy.print();
		//*/
	}

}
