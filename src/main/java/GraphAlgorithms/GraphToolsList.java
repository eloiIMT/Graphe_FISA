package GraphAlgorithms;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import AdjacencyList.AdjacencyListDirectedGraph;
import AdjacencyList.AdjacencyListDirectedValuedGraph;
import AdjacencyList.AdjacencyListUndirectedValuedGraph;
import Collection.Triple;
import Nodes_Edges.Arc;
import Nodes_Edges.DirectedNode;
import Nodes_Edges.UndirectedNode;

public class GraphToolsList  extends GraphTools {

	private static int _DEBBUG =0;

	private static int[] visite;
	private static int[] debut;
	private static int[] fin;
	private static List<Integer> order_CC;
	private static int cpt=0;

	//--------------------------------------------------
	// 				Constructors
	//--------------------------------------------------

	public GraphToolsList(){
		super();
	}

	// ------------------------------------------
	// 				Accessors
	// ------------------------------------------



	// ------------------------------------------
	// 				Methods
	// ------------------------------------------

	public void BFS(AdjacencyListDirectedGraph g) {
		HashMap<DirectedNode,Boolean> visitees = new HashMap<DirectedNode,Boolean>();
		DirectedNode s = g.getNodes().get(0);
		visitees.put(s,true);
		Queue<DirectedNode> fifo = new LinkedList<DirectedNode>();
		fifo.add(s);
		while (!fifo.isEmpty()){
			DirectedNode v = fifo.poll();
			System.out.println("Noeud visité: " + v);
			for (Arc a : v.getArcSucc()) {
				DirectedNode w = a.getSecondNode();
				if (!visitees.containsKey(w)) {
					visitees.put(w,true);
					fifo.add(w);
				}
			}
		}
	}


	public static void main(String[] args) {
		int[][] Matrix = GraphTools.generateGraphData(10, 20, false, false, true, 100001);
		GraphTools.afficherMatrix(Matrix);
		AdjacencyListDirectedGraph al = new AdjacencyListDirectedGraph(Matrix);
		System.out.println(al);

		GraphToolsList gtl = new GraphToolsList();
		System.out.println("BFS on the graph: ");
		gtl.BFS(al);
	}
}
