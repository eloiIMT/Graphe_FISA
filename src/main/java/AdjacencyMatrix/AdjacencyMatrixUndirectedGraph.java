package AdjacencyMatrix;


import GraphAlgorithms.GraphTools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import AdjacencyList.AdjacencyListUndirectedGraph;

/**
 * This class represents the undirected graphs structured by an adjacency matrix.
 * We consider only simple graph
 */
public class AdjacencyMatrixUndirectedGraph {
	
	//--------------------------------------------------
    // 				Class variables
    //--------------------------------------------------

    protected int nbNodes;		// Number of vertices
    protected int nbEdges;		// Number of edges/arcs
    protected int[][] matrix;	// The adjacency matrix

  
   
	
	//--------------------------------------------------
	// 				Constructors
	//-------------------------------------------------- 
	
	public AdjacencyMatrixUndirectedGraph() {
		this.matrix = new int[0][0];
        this.nbNodes = 0;
        this.nbEdges = 0;
	}
	
	public AdjacencyMatrixUndirectedGraph(int[][] mat) {
		this.nbNodes=mat.length;
		this.nbEdges = 0;
		this.matrix = new int[this.nbNodes][this.nbNodes];
		for(int i = 0; i<this.nbNodes; i++){
			for(int j = i; j<this.nbNodes; j++){
				this.matrix[i][j] = mat[i][j];
				this.matrix[j][i] = mat[i][j];
				this.nbEdges += mat[i][j];
			}
		}	
	}
	
	public AdjacencyMatrixUndirectedGraph(AdjacencyListUndirectedGraph g) {
		this.nbNodes = g.getNbNodes(); 				
		this.nbEdges = g.getNbEdges(); 				
		this.matrix = g.toAdjacencyMatrix(); 
	}

	//--------------------------------------------------
	// 					Accessors
	//--------------------------------------------------

	/**
     * @return the matrix modeling the graph
     */
    public int[][] getMatrix() {
        return this.matrix;
    }

    /**
     * @return the number of nodes in the graph (referred to as the order of the graph)
     */
    public int getNbNodes() {
        return this.nbNodes;
    }
	
    /**
	 * @return the number of edges in the graph
 	 */	
	public int getNbEdges() {
		return this.nbEdges;
	}

	/**
	 * 
	 * @param v the vertex selected
	 * @return a list of vertices which are the neighbours of x
	 */
	public List<Integer> getNeighbours(int v) {
		List<Integer> l = new ArrayList<>();
		for(int i = 0; i<matrix[v].length; i++){
			if(matrix[v][i]>0){
				l.add(i);
			}
		}
		return l;
	}
	
	// ------------------------------------------------
	// 					Methods 
	// ------------------------------------------------		
	
	/**
     	* @return true if the edge is in the graph.
     	*/
	public boolean isEdge(int x, int y) {
		return matrix[x][y] == 1;
	}
	
	/**
     	* removes the edge (x,y) if there exists one between these nodes in the graph.
    	 */
	public void removeEdge(int x, int y) {
		if (isEdge(x,y)){
			matrix[x][y] = 0;
			matrix[y][x] = 0;
			nbEdges -= 1;
		}
	}

	/**
     	* adds the edge (x,y) if there is not already one.
     	*/
	public void addEdge(int x, int y) {
		if (!isEdge(x,y)){
			matrix[x][y] = 1;
			matrix[y][x] = 1;
			nbEdges += 1;
		}
	}

	
	/**
    	* @return the adjacency matrix representation int[][] of the graph
    	*/
	public int[][] toAdjacencyMatrix() {
		return this.matrix;
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("\nAdjacency Matrix: \n");
		for (int[] ints : this.matrix) {
			for (int anInt : ints) {
				s.append(anInt).append("\t");
			}
			s.append("\n");
		}
		s.append("\n");
		return s.toString();
	}

	public static void main(String[] args) {
		int[][] mat2 = GraphTools.generateGraphData(10, 35, false, true, false, 100001);
		AdjacencyMatrixUndirectedGraph am = new AdjacencyMatrixUndirectedGraph(mat2);
		System.out.println(am);
		System.out.println("nodes = "+am.getNbNodes()+ "\nedges = "+am.getNbEdges() +"\n");
		
		// Neighbours of vertex 2 :
		System.out.println("Neighbours of vertex 2 : ");
		List<Integer> t2 = am.getNeighbours(2);
		for (Integer integer : t2) {
			System.out.print(integer + ", ");
		}
		
		// We add three edges {3,5} :
		System.out.println("\n\nisEdge(3, 5) ? " + am.isEdge(3, 5));
		for(int i = 0; i<3;i++){
			am.addEdge(i, 5);
		}

		System.out.println("\n"+am);
		
		System.out.println("\nAfter removing one edge {3,5} :");
		am.removeEdge(3,5);
		System.out.println(am);

		System.out.println("Testing isEdge method:");
		testIsEdge(am, 2, 5); // should be YES
		testIsEdge(am, 3, 5); // should be NO

		System.out.println("\nTesting addEdge method:");
		testAddEdge(am, 3, 5); // should add edge
		testAddEdge(am, 2, 5); // should edge already exists

		System.out.println("\nTesting removeEdge method:");
		testRemoveEdge(am, 1, 1); // should edge already not be here
		testRemoveEdge(am, 2, 1); // should remove edge
	}

	private static void testIsEdge(AdjacencyMatrixUndirectedGraph am, int i, int j) {
		System.out.println("== Test isEdge(" + i + ", " + j + ") ==");
		System.out.println("\tisEdge(" + i + "," + j + ") : " + (am.isEdge(i, j) ? "YES" : "NO") +
				" | " + am.getMatrix()[i][j] + " : value");
	}

	private static void testAddEdge(AdjacencyMatrixUndirectedGraph am, int i, int j) {
		System.out.println("=== Test addEdge(" + i + ", " + j + ") ===");

		// Save before state
		int beforeIJ = am.getMatrix()[i][j];
		int beforeJI = am.getMatrix()[j][i];

		am.addEdge(i, j);

		// Save after state
		int afterIJ = am.getMatrix()[i][j];
		int afterJI = am.getMatrix()[j][i];

		// Check if the edge was added correctly
		boolean ok = am.isEdge(i, j) && am.isEdge(j, i);

		System.out.println("\t[Before] matrix[" + i + "][" + j + "] = " + beforeIJ +
				" | matrix[" + j + "][" + i + "] = " + beforeJI);
		System.out.println("\t[After ] matrix[" + i + "][" + j + "] = " + afterIJ +
				" | matrix[" + j + "][" + i + "] = " + afterJI);
		System.out.println("\tResult: " + (ok ? "OK" : "KO"));
	}

	private static void testRemoveEdge(AdjacencyMatrixUndirectedGraph am, int i, int j) {
		System.out.println("=== Test removeEdge(" + i + ", " + j + ") ===");

		// Save before state
		int beforeIJ = am.getMatrix()[i][j];
		int beforeJI = am.getMatrix()[j][i];

		am.removeEdge(i, j);

		// Save after state
		int afterIJ = am.getMatrix()[i][j];
		int afterJI = am.getMatrix()[j][i];

		// Check if the edge was removed correctly
		boolean ok = !am.isEdge(i, j) && !am.isEdge(j, i);

		System.out.println("\t[Before] matrix[" + i + "][" + j + "] = " + beforeIJ +
				" | matrix[" + j + "][" + i + "] = " + beforeJI);
		System.out.println("\t[After ] matrix[" + i + "][" + j + "] = " + afterIJ +
				" | matrix[" + j + "][" + i + "] = " + afterJI);
		System.out.println("\tResult: " + (ok ? "OK" : "KO"));
	}
}
