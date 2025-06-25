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

	// Question 2: getBestChildPos
	private int getBestChildPos(int[] nodes, int src, int size) {
	    int left = 2 * src + 1;
	    int right = 2 * src + 2;
	    if (left >= size) return -1; // no children
	    if (right >= size) return left; // only left child
	    return (nodes[left] < nodes[right]) ? left : right;
	}

	// Question 3: insert
	public boolean insert(int[] nodes, int value, int size) {
	    if (size >= nodes.length) return false;
	    nodes[size] = value;
	    int i = size;
	    while (i > 0) {
	        int parent = (i - 1) / 2;
	        if (nodes[i] < nodes[parent]) {
	            int tmp = nodes[i];
	            nodes[i] = nodes[parent];
	            nodes[parent] = tmp;
	            i = parent;
	        } else {
	            break;
	        }
	    }
	    return true;
	}

	// Question 6: remove
	public int remove(int[] nodes, int size) {
	    if (size == 0) return -1;
	    int removed = nodes[0];
	    nodes[0] = nodes[size - 1];
	    int i = 0;
	    while (true) {
	        int best = getBestChildPos(nodes, i, size - 1);
	        if (best == -1) break;
	        if (nodes[i] > nodes[best]) {
	            int tmp = nodes[i];
	            nodes[i] = nodes[best];
	            nodes[best] = tmp;
	            i = best;
	        } else {
	            break;
	        }
	    }
	    return removed;
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
