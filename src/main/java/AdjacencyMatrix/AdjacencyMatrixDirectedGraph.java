package AdjacencyMatrix;


import GraphAlgorithms.GraphTools;
import Nodes_Edges.AbstractNode;
import Nodes_Edges.DirectedNode;

import java.util.ArrayList;
import java.util.List;

import AdjacencyList.AdjacencyListDirectedGraph;

/**
 * This class represents the directed graphs structured by an adjacency matrix.
 * We consider only simple graph
 */
public class AdjacencyMatrixDirectedGraph {

	//--------------------------------------------------
    // 				Class variables
    //--------------------------------------------------

    protected int nbNodes;		// Number of vertices
    protected int nbArcs;		// Number of edges/arcs
    protected int[][] matrix;	// The adjacency matrix
	
	//--------------------------------------------------
	// 				Constructors
	//-------------------------------------------------- 

    public AdjacencyMatrixDirectedGraph() {
        this.matrix = new int[0][0];
        this.nbNodes = 0;
        this.nbArcs = 0;
    }

      
	public AdjacencyMatrixDirectedGraph(int[][] mat) {
		this.nbNodes = mat.length;
		this.nbArcs = 0;
		this.matrix = new int[this.nbNodes][this.nbNodes];
		for(int i = 0; i<this.nbNodes; i++){
			for(int j = 0; j<this.nbNodes; j++){
				this.matrix[i][j] = mat[i][j];
				this.nbArcs += mat[i][j];
			}
		}
	}

	public AdjacencyMatrixDirectedGraph(AdjacencyListDirectedGraph g) {
		this.nbNodes = g.getNbNodes();
		this.nbArcs = g.getNbArcs();
		this.matrix = g.toAdjacencyMatrix();
	}

	//--------------------------------------------------
	// 					Accessors
	//--------------------------------------------------


    /**
     * Returns the matrix modeling the graph
     */
    public int[][] getMatrix() {
        return this.matrix;
    }

    /**
     * Returns the number of nodes in the graph (referred to as the order of the graph)
     */
    public int getNbNodes() {
        return this.nbNodes;
    }
	
    /**
	 * @return the number of arcs in the graph
 	 */	
	public int getNbArcs() {
		return this.nbArcs;
	}

	
	/**
	 * @param u the vertex selected
	 * @return a list of vertices which are the successors of u
	 */
	public List<Integer> getSuccessors(int u) {
		List<Integer> succ = new ArrayList<Integer>();
		for(int v =0;v<this.matrix[u].length;v++){
			if(this.matrix[u][v]>0){
				succ.add(v);
			}
		}
		return succ;
	}

	/**
	 * @param v the vertex selected
	 * @return a list of vertices which are the predecessors of v
	 */
	public List<Integer> getPredecessors(int v) {
		List<Integer> pred = new ArrayList<Integer>();
		for(int u =0;u<this.matrix.length;u++){
			if(this.matrix[u][v]>0){
				pred.add(u);
			}
		}
		return pred;
	}
	
	
	// ------------------------------------------------
	// 					Methods 
	// ------------------------------------------------		
	
	/**
	 * @return true if the arc (from,to) exists in the graph.
 	 */
	public boolean isArc(int from, int to) {
		return matrix[from][to] == 1;
	}

	/**
	 * removes the arc (from,to) if there exists one between these nodes in the graph.
	 */
	public void removeArc(int from, int to) {
		if (isArc(from, to)){
			matrix[from][to] = 0;
			nbArcs -= 1;
		}
	}

	/**
	 * Adds the arc (from,to). 
	 */
	public void addArc(int from, int to) {
		if (!isArc(from, to)){
			matrix[from][to] = 1;
			nbArcs += 1;
		}
	}

	/**
	 * @return a new graph which is the inverse graph of this.matrix
 	 */
	public AdjacencyMatrixDirectedGraph computeInverse() {
		AdjacencyMatrixDirectedGraph amInv = new AdjacencyMatrixDirectedGraph(this.matrix);

		for (int i =0; i < amInv.getNbNodes(); i++) {
			for (int j =0; j < amInv.getNbNodes(); j++) {
				if (isArc(i,j)) {
					amInv.removeArc(i,j);
				} else {
					amInv.addArc(i,j);
				}
			}
		}

		return amInv;
	}

	@Override
	public String toString(){
		StringBuilder s = new StringBuilder("Adjacency Matrix: \n");
		for (int[] ints : matrix) {
			for (int anInt : ints) {
				s.append(anInt).append("\t");
			}
			s.append("\n");
		}
		s.append("\n");
		return s.toString();
	}

	public static void main(String[] args) {
		int[][] matrix2 = GraphTools.generateGraphData(10, 20, false, false, false, 100001);
		AdjacencyMatrixDirectedGraph am = new AdjacencyMatrixDirectedGraph(matrix2);
		System.out.println(am);
		System.out.println("n = "+am.getNbNodes()+ "\nm = "+am.getNbArcs() +"\n");
		
		// Successors of vertex 1 :
		System.out.println("Sucesssors of vertex 1 : ");
		List<Integer> t = am.getSuccessors(1);
		for (Integer integer : t) {
			System.out.print(integer + ", ");
		}
		
		// Predecessors of vertex 2 :
		System.out.println("\n\nPredecessors of vertex 2 : ");
		List<Integer> t2 = am.getPredecessors(2);
		for (Integer integer : t2) {
			System.out.print(integer + ", ");
		}

		System.out.println("\n\nTesting isArc method:");
		testIsArc(am, 1, 2); // should be YES
		testIsArc(am, 0, 2); // should be NO

		System.out.println("\nTesting addArc method:");
		testAddArc(am, 0, 2); // should add arc
		testAddArc(am, 1, 2); // should arc already exists

		System.out.println("\nTesting removeArc method:");
		testRemoveArc(am, 0, 1); // should arc already not be here
		testRemoveArc(am, 0, 2); // should remove arc
	}

	private static void testIsArc(AdjacencyMatrixDirectedGraph am, int i, int j) {
		System.out.println("== Test isArc(" + i + ", " + j + ") ==");
		System.out.println("\tisArc(" + i + "," + j + ") : " + (am.isArc(i, j) ? "YES" : "NO") +
				" | " + am.getMatrix()[i][j] + " : value");
	}

	private static void testAddArc(AdjacencyMatrixDirectedGraph am, int i, int j) {
		System.out.println("=== Test addArc(" + i + ", " + j + ") ===");

		// Save before state
		int beforeIJ = am.getMatrix()[i][j];

		am.addArc(i, j);

		// Save after state
		int afterIJ = am.getMatrix()[i][j];

		// Check if the edge was added correctly
		boolean ok = am.isArc(i, j);

		System.out.println("\t[Before] matrix[" + i + "][" + j + "] = " + beforeIJ);
		System.out.println("\t[After ] matrix[" + i + "][" + j + "] = " + afterIJ);
		System.out.println("\tResult: " + (ok ? "OK" : "KO"));
	}

	private static void testRemoveArc(AdjacencyMatrixDirectedGraph am, int i, int j) {
		System.out.println("=== Test removeArc(" + i + ", " + j + ") ===");

		// Save before state
		int beforeIJ = am.getMatrix()[i][j];

		am.removeArc(i, j);

		// Save after state
		int afterIJ = am.getMatrix()[i][j];

		// Check if the edge was removed correctly
		boolean ok = !am.isArc(i, j);

		System.out.println("\t[Before] matrix[" + i + "][" + j + "] = " + beforeIJ);
		System.out.println("\t[After ] matrix[" + i + "][" + j + "] = " + afterIJ);
		System.out.println("\tResult: " + (ok ? "OK" : "KO"));
	}
}
