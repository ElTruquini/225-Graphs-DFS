
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
			fillZeros();
			print();
		}

		//Fills the newly created Matrix with -1
		public void fillZeros (){
			for (int col = 0 ; col < adjacencyMatrix.length ; col++){
				for (int row = 0 ; row < adjacencyMatrix[0].length ; row ++){
					adjacencyMatrix[row][col] = -1;
				}
			}
		}

		//Prints adjacencyMatrix
		public void print (){
			System.out.println(adjacencyMatrix.length);
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
		 *            The density of the graph
		 */
		public void generate(int n, int density) {
			throw new UnsupportedOperationException("This method has not been implemented yet.");
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
			File path = new File (file);
			Scanner fileReader = new Scanner (path);
			int col=0; 	//rows
			int row=0;	//columns
			System.out.println("Tokenizer");

			while (fileReader.hasNextLine()){
				
				String line = fileReader.nextLine();
				Scanner tokenizer = new Scanner (line);

				while (tokenizer.hasNextInt()){
					int token = tokenizer.nextInt();
					System.out.print(token);
					System.out.print(" ");
					System.out.println(adjacencyMatrix[0][0]);
					adjacencyMatrix[row][col]=token;
					if (adjacencyMatrix.length < row){
						row ++;
					} else {
						row = 0;
					}
				}
				System.out.println();

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
			throw new UnsupportedOperationException("This method has not been implemented yet.");
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

	public static void main(String[] args) {
		Graph graphy = new Graph ();
		try{
			graphy.read("test.txt");

		} catch (IOException ex){
			throw new UnsupportedOperationException(".");

		}
	}

}
