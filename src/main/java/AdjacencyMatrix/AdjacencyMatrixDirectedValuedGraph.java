package AdjacencyMatrix;

import GraphAlgorithms.GraphTools;


public class AdjacencyMatrixDirectedValuedGraph extends AdjacencyMatrixDirectedGraph {

	//--------------------------------------------------
	// 				Class variables
	//-------------------------------------------------- 

	// No class variable, we use the matrix variable but with costs values 

	//--------------------------------------------------
	// 				Constructors
	//-------------------------------------------------- 

	public AdjacencyMatrixDirectedValuedGraph(int[][] matrixVal) {
		super(matrixVal);
	}

	
	// ------------------------------------------------
	// 					Methods
	// ------------------------------------------------	
	
	
	/**
     * adds the arc (from,to,cost). If there is already one initial cost, we replace it.
     */	
	public void addArc(int from, int to, int cost ) {
        this.matrix[from][to] = cost;
    }

	
	
	@Override
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
        int[][] matrixValued = GraphTools.generateValuedGraphData(10, false, false, true, false, 100001);
        AdjacencyMatrixDirectedValuedGraph am = new AdjacencyMatrixDirectedValuedGraph(matrixValued);
        System.out.println(am);
        System.out.println("--------------------");
        am.addArc(0, 2, 5);
        System.out.println("Après ajout arc (0,2,5):\n" + am);
        if (am.matrix[0][2] == 5) {
            System.out.println("Test ajout arc (0,2,5): OK");
        } else {
            System.out.println("Test ajout arc (0,2,5): ERREUR");
        }
        System.out.println("--------------------");
        am.addArc(0, 2, 77);
        System.out.println("Après modification coût arc (0,2,77):\n" + am);
        if (am.matrix[0][2] == 77) {
            System.out.println("Test modification coût arc (0,2,77): OK");
        } else {
            System.out.println("Test modification coût arc (0,2,77): ERREUR");
        }
        System.out.println("--------------------");
    }
}
