package GraphAlgorithms;

import java.util.*;

import AdjacencyList.AdjacencyListDirectedGraph;
import Nodes_Edges.Arc;
import Nodes_Edges.DirectedNode;
import Nodes_Edges.AbstractNode;
import Collection.Pair;

public class GraphToolsList  extends GraphTools {

	private static int[] visite;
	private static int[] debut;
	private static int[] fin;
	private static int cpt=0;

	private static final int NON_VISITE = 0;
	private static final int EN_COURS = 1;
	private static final int TOTALEMENT_VISITE = 2;

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
		visite[s.getLabel()] = EN_COURS;
		debut[s.getLabel()] = cpt++;
		visitees.add(s);
		for (Arc a : s.getArcSucc()) {
			DirectedNode voisin = a.getSecondNode();
			if (visite[voisin.getLabel()] == NON_VISITE) {
				explorerSommet(voisin, visitees);
			}
		}
		visite[s.getLabel()] = TOTALEMENT_VISITE;
		fin[s.getLabel()] = cpt++;
	}

	private List<DirectedNode> explorerGraphe(AdjacencyListDirectedGraph g) {
		int taille = g.getNodes().size();
		cpt = 0;
		visite = new int[taille];
		debut = new int[taille];
		fin = new int[taille];
		List<DirectedNode> visitees = new ArrayList<DirectedNode>();
		for (DirectedNode s : g.getNodes()) {
			if (visite[s.getLabel()] == NON_VISITE) {
				explorerSommet(s, visitees);
			}
		}
		return visitees;
	}

	private void explorerSommetBis(DirectedNode s, List<DirectedNode> composante) {
		visite[s.getLabel()] = EN_COURS;
		composante.add(s);
		for (Arc a : s.getArcSucc()) {
			DirectedNode voisin = a.getSecondNode();
			if (visite[voisin.getLabel()] == NON_VISITE) {
				explorerSommetBis(voisin, composante);
			}
		}
		visite[s.getLabel()] = TOTALEMENT_VISITE;
	}

	public List<DirectedNode> sommetsParFinDecroissante(AdjacencyListDirectedGraph g) {
		explorerGraphe(g);

		List<DirectedNode> nodes = g.getNodes();
		nodes.sort((a, b) -> Integer.compare(fin[b.getLabel()], fin[a.getLabel()]));
		return nodes;
	}

	private List<List<DirectedNode>> explorerGrapheBis(AdjacencyListDirectedGraph gInverse, List<DirectedNode> ordre) {
		List<List<DirectedNode>> cfc = new ArrayList<>();
		Arrays.fill(visite, NON_VISITE);
		for (DirectedNode s : ordre) {
			if (visite[s.getLabel()] == NON_VISITE) {
				List<DirectedNode> composante = new ArrayList<>();
				explorerSommetBis(s, composante);
				cfc.add(composante);
			}
		}
		return cfc;
	}

	public List<List<DirectedNode>> getComposantesFortementConnexes(AdjacencyListDirectedGraph g) {
		List<DirectedNode> ordre = sommetsParFinDecroissante(g);

		AdjacencyListDirectedGraph gInverse = g.computeInverse();

		int taille = g.getNodes().size();
		visite = new int[taille];
		Arrays.fill(visite, NON_VISITE);

		List<DirectedNode> ordreInverse = new ArrayList<>(ordre);
		Collections.reverse(ordreInverse);

		return explorerGrapheBis(gInverse, ordreInverse);
	}

	public HashMap<DirectedNode, Integer> dijkstra(AdjacencyListDirectedGraph g, DirectedNode s) {
		HashMap<DirectedNode, Integer> distances = new HashMap<>();
		PriorityQueue<Pair<DirectedNode, Integer>> pq = new PriorityQueue<>(Comparator.comparingInt(Pair::getRight));

		for (DirectedNode node : g.getNodes()) {
			distances.put(node, Integer.MAX_VALUE);
		}

		distances.put(s, 0);
		pq.add(new Pair<>(s, 0));

		while (!pq.isEmpty()) {
			Pair<DirectedNode, Integer> pair = pq.poll();
			DirectedNode u = pair.getLeft();
			int u_dist = pair.getRight();

			if (u_dist > distances.get(u)) {
				continue;
			}

			for (Arc arc : u.getArcSucc()) {
				DirectedNode v = arc.getSecondNode();
				int weight = arc.getWeight();
				if (distances.get(u) != Integer.MAX_VALUE && distances.get(u) + weight < distances.get(v)) {
					distances.put(v, distances.get(u) + weight);
					pq.add(new Pair<>(v, distances.get(v)));
				}
			}
		}
		return distances;
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

	// Test Dijkstra sur un graphe avec sommet isolé
	public static void testDijkstraSommetIsole() {
		System.out.println("\nTest Dijkstra - sommet isolé :");
		// Graphe : 0 --1--> 1 --2--> 2, 3 isolé
		int[][] matrix = {
			{0, 1, 0, 0},
			{0, 0, 2, 0},
			{0, 0, 0, 0},
			{0, 0, 0, 0}
		};
		AdjacencyListDirectedGraph g = new AdjacencyListDirectedGraph(matrix);
		GraphToolsList gtl = new GraphToolsList();
		HashMap<DirectedNode, Integer> d = gtl.dijkstra(g, g.getNodes().get(0));
		for (int i = 0; i < g.getNodes().size(); i++) {
			System.out.println("Distance vers n_"+i+" : " + (d.get(g.getNodes().get(i)) == Integer.MAX_VALUE ? "Infinity" : d.get(g.getNodes().get(i))));
		}
		System.out.println("Attendu : 0, 1, 3, Infinity");
	}

	// Test Dijkstra sur un graphe avec cycle
	public static void testDijkstraCycle() {
		System.out.println("\nTest Dijkstra - cycle :");
		// Graphe : 0 --1--> 1 --1--> 2 --1--> 0
		int[][] matrix = {
			{0, 1, 0},
			{0, 0, 1},
			{1, 0, 0}
		};
		AdjacencyListDirectedGraph g = new AdjacencyListDirectedGraph(matrix);
		GraphToolsList gtl = new GraphToolsList();
		HashMap<DirectedNode, Integer> d = gtl.dijkstra(g, g.getNodes().get(0));
		for (int i = 0; i < g.getNodes().size(); i++) {
			System.out.println("Distance vers n_"+i+" : " + (d.get(g.getNodes().get(i)) == Integer.MAX_VALUE ? "Infinity" : d.get(g.getNodes().get(i))));
		}
		System.out.println("Attendu : 0, 1, 2");
	}

	// Test Dijkstra sur un graphe avec plusieurs chemins
	public static void testDijkstraPlusieursChemins() {
		System.out.println("\nTest Dijkstra - plusieurs chemins :");
		// Graphe : 0 --1--> 1 --1--> 3 ; 0 --5--> 2 --1--> 3
		int[][] matrix = {
			{0, 1, 5, 0},
			{0, 0, 0, 1},
			{0, 0, 0, 1},
			{0, 0, 0, 0}
		};
		AdjacencyListDirectedGraph g = new AdjacencyListDirectedGraph(matrix);
		GraphToolsList gtl = new GraphToolsList();
		HashMap<DirectedNode, Integer> d = gtl.dijkstra(g, g.getNodes().get(0));
		for (int i = 0; i < g.getNodes().size(); i++) {
			System.out.println("Distance vers n_"+i+" : " + (d.get(g.getNodes().get(i)) == Integer.MAX_VALUE ? "Infinity" : d.get(g.getNodes().get(i))));
		}
		System.out.println("Attendu : 0, 1, 5, 2");
	}

	// Test Dijkstra sur un graphe avec poids élevés et zéros hors diagonale
	public static void testDijkstraPoidsEleves() {
		System.out.println("\nTest Dijkstra - poids élevés et zéros :");
		// Graphe : 0 --1000--> 1 ; 0 --2--> 2 ; 2 --1--> 1
		int[][] matrix = {
			{0, 1000, 2},
			{0, 0, 0},
			{0, 1, 0}
		};
		AdjacencyListDirectedGraph g = new AdjacencyListDirectedGraph(matrix);
		GraphToolsList gtl = new GraphToolsList();
		HashMap<DirectedNode, Integer> d = gtl.dijkstra(g, g.getNodes().get(0));
		for (int i = 0; i < g.getNodes().size(); i++) {
			System.out.println("Distance vers n_"+i+" : " + (d.get(g.getNodes().get(i)) == Integer.MAX_VALUE ? "Infinity" : d.get(g.getNodes().get(i))));
		}
		System.out.println("Attendu : 0, 3, 2");
	}

	public static void main(String[] args) {
		int[][] Matrix = GraphTools.generateGraphData(10, 20, true, true, false, 100);
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
		for (int s : visite) {
			System.out.print(s+" ");
		}
		System.out.println();
		for (int s : debut) {
			System.out.print(s+" ");
		}
		System.out.println();
		for (int s : fin) {
			System.out.print(s+" ");
		}

		System.out.println("\nComposantes fortement connexes :");
		for (List<DirectedNode> comp : gtl.getComposantesFortementConnexes(al)) {
			System.out.println(comp);
		}
		System.out.println("\nExpected: [n_2], [n_7], [n_1, n_0, n_4, n_6, n_9, n_5, n_3, n_8]");

		// Test Dijkstra
		System.out.println("\nTest de Dijkstra depuis le noeud 0:");
		DirectedNode sourceNode = al.getNodes().get(0);
		HashMap<DirectedNode, Integer> distances = gtl.dijkstra(al, sourceNode);

		List<DirectedNode> sortedNodes = new ArrayList<>(distances.keySet());
		sortedNodes.sort(Comparator.comparingInt(AbstractNode::getLabel));

		for (DirectedNode node : sortedNodes) {
			System.out.println("Distance vers " + node + " : " + (distances.get(node) == Integer.MAX_VALUE ? "Infinity" : distances.get(node)));
		}

		testDijkstraSommetIsole();
		testDijkstraCycle();
		testDijkstraPlusieursChemins();
		testDijkstraPoidsEleves();
	}
}
