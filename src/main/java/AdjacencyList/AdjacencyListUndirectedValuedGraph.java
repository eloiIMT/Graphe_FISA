package AdjacencyList;

import java.util.ArrayList;

import GraphAlgorithms.GraphTools;
import Nodes_Edges.Edge;
import Nodes_Edges.UndirectedNode;

public class AdjacencyListUndirectedValuedGraph extends AdjacencyListUndirectedGraph{

	//--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------

    public AdjacencyListUndirectedValuedGraph(int[][] matrixVal) {
    	super();
    	this.nbNodes = matrixVal.length;
        
        for (int i = 0; i < this.nbNodes; i++) {
            this.nodes.add(new UndirectedNode(i));            
        }
        for (UndirectedNode n1 : this.getNodes()) {
            for (int j = n1.getLabel(); j < matrixVal[n1.getLabel()].length; j++) {
            	UndirectedNode n2 = this.getNodes().get(j);
                if (matrixVal[n1.getLabel()][j] != 0) {
                	Edge e1 = new Edge(n1,n2,matrixVal[n1.getLabel()][j]);
                    n1.addEdge(e1);
                    this.edges.add(e1);
                	n2.addEdge(new Edge(n2,n1,matrixVal[n1.getLabel()][j]));
                    this.nbEdges++;
                }
            }
        }
    }

    //--------------------------------------------------
    // 				Methods
    //--------------------------------------------------
    

    /**
     * Adds the edge (from,to) with cost if it is not already present in the graph.
     * And adds this edge to the incident list of both extremities (nodes) and into the global list "edges" of the graph.
     */
    public void addEdge(UndirectedNode x, UndirectedNode y, int cost) {
        Edge edge = new Edge(x, y, cost);
        if (!this.edges.contains(edge)) {
            this.edges.add(edge);
            x.addEdge(edge);
            y.addEdge(new Edge(y, x, cost));
            this.nbEdges++;
        } else {
            // Si l'arête existe déjà, on met à jour le poids
            for (Edge e : edges) {
                if ((e.getFirstNode().equals(x) && e.getSecondNode().equals(y)) ||
                    (e.getFirstNode().equals(y) && e.getSecondNode().equals(x))) {
                    e.setWeight(cost);
                }
            }
            x.addEdge(new Edge(x, y, cost));
            y.addEdge(new Edge(y, x, cost));
        }
    }
    
    
    
    public static void main(String[] args) {
        int[][] matrixValued = GraphTools.generateValuedGraphData(10, false, true, true, false, 100001);
        GraphTools.afficherMatrix(matrixValued);
        AdjacencyListUndirectedValuedGraph al = new AdjacencyListUndirectedValuedGraph(matrixValued);
        System.out.println(al);
        System.out.println("Does edge (n_5,n_6) exist ? "+ al.getEdges().contains(new Edge(al.getNodes().get(6),al.getNodes().get(5))));
        System.out.println("--------------------");
        al.addEdge(al.getNodes().get(1), al.getNodes().get(3), 7);
        System.out.println("Après ajout arête (1,3,7):\n" + al);
        System.out.println("--------------------");
        al.addEdge(al.getNodes().get(1), al.getNodes().get(3), 42);
        System.out.println("Après modification coût arête (1,3,42):\n" + al);
        System.out.println("--------------------");
        System.out.println("Existence arête (1,3): " + al.getEdges().contains(new Edge(al.getNodes().get(1), al.getNodes().get(3))));
    }
}
