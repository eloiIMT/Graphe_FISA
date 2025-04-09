package AdjacencyMatrix;

import GraphAlgorithms.GraphTools;


public class AdjacencyMatrixUndirectedValuedGraph extends AdjacencyMatrixUndirectedGraph {

	//--------------------------------------------------
	// 				Class variables
	//-------------------------------------------------- 

	// No class variable, we use the matrix variable but with costs values

	//--------------------------------------------------
	// 				Constructors
	//-------------------------------------------------- 

	public AdjacencyMatrixUndirectedValuedGraph(int[][] matrixVal) {
		super(matrixVal);
	}


	
	// ------------------------------------------------
	// 					Methods 
	// ------------------------------------------------	
	
	
	/**
     * adds the edge (x,y,cost). If there is already one initial cost, we replace it.
     */
	public void addEdge(int x, int y, int cost ) {
		// A completer
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder("\n Matrix of Costs: \n");
		for (int[] lineCost : this.matrix) {
			for (int i : lineCost) {
				s.append(i).append("\t");
			}
			s.append("\n");
		}
		s.append("\n");
		return s.toString();
	}
	
	
	public static void main(String[] args) {
		int[][] matrixValued = GraphTools.generateValuedGraphData(10, false, true, true, false, 100001);
		AdjacencyMatrixUndirectedValuedGraph am = new AdjacencyMatrixUndirectedValuedGraph(matrixValued);
		System.out.println(am);
		// A completer
	}

}
