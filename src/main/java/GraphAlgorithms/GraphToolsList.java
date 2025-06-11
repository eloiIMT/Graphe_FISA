package GraphAlgorithms;

import java.util.*;

import AdjacencyList.AdjacencyListDirectedGraph;
import Nodes_Edges.Arc;
import Nodes_Edges.DirectedNode;

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

	public List<DirectedNode> BFS(AdjacencyListDirectedGraph g) {
		HashMap<DirectedNode,Boolean> visitees = new HashMap<DirectedNode,Boolean>();
		DirectedNode s = g.getNodes().get(0);
		List<DirectedNode> result = new ArrayList<DirectedNode>();
		visitees.put(s,true);
		Queue<DirectedNode> fifo = new LinkedList<DirectedNode>();
		fifo.add(s);
		while (!fifo.isEmpty()){
			DirectedNode v = fifo.poll();
			result.add(v);
			for (Arc a : v.getArcSucc()) {
				DirectedNode w = a.getSecondNode();
				if (!visitees.containsKey(w)) {
					visitees.put(w,true);
					fifo.add(w);
				}
			}
		}
		return result;
	}

	private void explorerSommet(DirectedNode s, List<DirectedNode> visitees) {
		visitees.add(s);
		for (Arc a : s.getArcSucc()) {
			DirectedNode voisin = a.getSecondNode();
			if (!visitees.contains(voisin)) {
				explorerSommet(voisin, visitees);
			}
		}
	}

	private List<DirectedNode> explorerGraphe(AdjacencyListDirectedGraph g) {
		List<DirectedNode> visitees = new ArrayList<DirectedNode>();
		DirectedNode s = g.getNodes().get(0);
		visitees.add(s);
		for (Arc a : s.getArcSucc()) {
			DirectedNode voisin = a.getSecondNode();
			if (!visitees.contains(voisin)) {
				explorerSommet(voisin, visitees);
			}
		}
		return visitees;
	}


	public static void main(String[] args) {
		int[][] Matrix = GraphTools.generateGraphData(10, 20, false, false, true, 100001);
		GraphTools.afficherMatrix(Matrix);
		AdjacencyListDirectedGraph al = new AdjacencyListDirectedGraph(Matrix);
		System.out.println(al);

		GraphToolsList gtl = new GraphToolsList();
		System.out.println("BFS on the graph: ");
		for (DirectedNode n : gtl.BFS(al)) {
			System.out.print(n+" ");
		}
		System.out.println("\nExpected: n_0 n_4 n_2 n_6 n_9 n_5 n_3 n_8 n_1");

		System.out.println("\nDFS on the graph: ");
		for (DirectedNode n : gtl.explorerGraphe(al)) {
			System.out.print(n+" ");
		}
		System.out.println("\nExpected: n_0 n_4 n_2 n_6 n_9 n_5 n_3 n_8 n_1");
	}
}
