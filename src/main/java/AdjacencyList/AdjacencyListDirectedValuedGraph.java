package AdjacencyList;

import java.util.ArrayList;

import GraphAlgorithms.GraphTools;
import Nodes_Edges.Arc;
import Nodes_Edges.DirectedNode;
import Nodes_Edges.Edge;
import Nodes_Edges.UndirectedNode;

public class AdjacencyListDirectedValuedGraph extends AdjacencyListDirectedGraph {

	//--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------

	public AdjacencyListDirectedValuedGraph(int[][] matrixVal) {
    	super();
    	this.nbNodes = matrixVal.length;
        for (int i = 0; i < this.nbNodes; i++) {
            this.nodes.add(new DirectedNode(i));
        }
        for (DirectedNode n1 : this.getNodes()) {
            for (int j = 0; j < matrixVal[n1.getLabel()].length; j++) {
            	DirectedNode n2 = this.getNodes().get(j);
                if (matrixVal[n1.getLabel()][j] != 0) {
                	Arc a1 = new Arc(n1,n2,matrixVal[n1.getLabel()][j]);
                    n1.addArc(a1);
                    this.arcs.add(a1);
                	n2.addArc(a1);
                    this.nbArcs ++;
                }
            }
        }            	
    }

    // ------------------------------------------
    // 				Accessors
    // ------------------------------------------
    

    /**
     * Adds the arc (from,to) with cost if it is not already present in the graph. 
     * And adds this arc to the incident list of both extremities (nodes) and into the global list "arcs" of the graph.
     */
    public void addArc(DirectedNode from, DirectedNode to, int cost) {
    	// A completer      
    }
    
    
    
    
    public static void main(String[] args) {
        int[][] matrixValued = GraphTools.generateValuedGraphData(10, false, false, true, false, 100001);
        GraphTools.afficherMatrix(matrixValued);
        AdjacencyListDirectedValuedGraph al = new AdjacencyListDirectedValuedGraph(matrixValued);
        System.out.println(al);        
        // A completer
    }
	
}
