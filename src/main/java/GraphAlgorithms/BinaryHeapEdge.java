package GraphAlgorithms;

import java.util.ArrayList;
import java.util.List;

import Nodes_Edges.Edge;
import Nodes_Edges.UndirectedNode;

public class BinaryHeapEdge {

	/**
	 * A list structure for a faster management of the heap by indexing
	 * 
	 */
	private  List<Edge> binh;

    public BinaryHeapEdge() {
        this.binh = new ArrayList<Edge>();
    }

    public boolean isEmpty() {
        return binh.isEmpty();
    }

    /**
	 * Insert a new edge in the binary heap
	 * 
	 * @param from one node of the edge
	 * @param to one node of the edge
	 * @param val the edge weight
	 */
    public void insert(UndirectedNode from, UndirectedNode to, int val) {
        Edge e = new Edge(from, to, val);
        binh.add(e);
        int i = binh.size() - 1;
        while (i > 0) {
            int parent = (i - 1) / 2;
            if (binh.get(i).getWeight() < binh.get(parent).getWeight()) {
                swap(i, parent);
                i = parent;
            } else {
                break;
            }
        }
    }

    
    /**
	 * Removes the root edge in the binary heap, and swap the edges to keep a valid binary heap
	 * 
	 * @return the edge with the minimal value (root of the binary heap)
	 * 
	 */
    public Edge remove() {
        if (binh.isEmpty()) return null;
        Edge removed = new Edge(binh.get(0).getFirstNode(), binh.get(0).getSecondNode(), binh.get(0).getWeight());
        Edge last = binh.remove(binh.size() - 1);
        if (binh.isEmpty()) return removed;
        binh.set(0, last);
        int i = 0;
        while (true) {
            int best = getBestChildPos(i);
            if (best == -1) break;
            if (binh.get(i).getWeight() > binh.get(best).getWeight()) {
                swap(i, best);
                i = best;
            } else {
                break;
            }
        }
        return removed;
    }
    

    private boolean isLeaf(int src) {
        int lastIndex = binh.size() - 1;
        return (2 * src + 1) > lastIndex;
    }

    /**
	 * From an edge indexed by src, find the child having the least weight and return it
	 * 
	 * @param src an index of the list edges
	 * @return the index of the child edge with the least weight
	 */
    private int getBestChildPos(int src) {
        int lastIndex = binh.size() - 1;
        if (isLeaf(src)) {
            return -1;
        } else {
            int left = 2 * src + 1;
            int right = 2 * src + 2;
            if (right > lastIndex) return left;
            return (binh.get(left).getWeight() < binh.get(right).getWeight()) ? left : right;
        }
    }

    
    /**
	 * Swap two edges in the binary heap
	 * 
	 * @param i an index of the list edges
	 * @param j an index of the list edges
	 */
    private void swap(int i, int j) {
    	Edge temp = binh.get(i);
    	binh.set(i, binh.get(j));
    	binh.set(j, temp);
    }

    
    /**
	 * Create the string of the visualisation of a binary heap
	 * 
	 * @return the string of the binary heap
	 */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Edge no: binh) {
            s.append(no).append(", ");
        }
        return s.toString();
    }
    
    
    private String space(int x) {
		StringBuilder res = new StringBuilder();
		for (int i=0; i<x; i++) {
			res.append(" ");
		}
		return res.toString();
	}
	
	/**
	 * Print a nice visualisation of the binary heap as a hierarchy tree
	 * 
	 */	
	public void lovelyPrinting(){
		int nodeWidth = this.binh.get(0).toString().length();
		int depth = 1+(int)(Math.log(this.binh.size())/Math.log(2));
		int index=0;
		
		for(int h = 1; h<=depth; h++){
			int left = ((int) (Math.pow(2, depth-h-1)))*nodeWidth - nodeWidth/2;
			int between = ((int) (Math.pow(2, depth-h))-1)*nodeWidth;
			int i =0;
			System.out.print(space(left));
			while(i<Math.pow(2, h-1) && index<binh.size()){
				System.out.print(binh.get(index) + space(between));
				index++;
				i++;
			}
			System.out.println("");
		}
		System.out.println("");
	}
	
	// ------------------------------------
    // 					TEST
	// ------------------------------------

	/**
	 * Recursive test to check the validity of the binary heap
	 * 
	 * @return a boolean equal to True if the binary tree is compact from left to right
	 * 
	 */
    private boolean test() {
        return this.isEmpty() || testRec(0);
    }

    private boolean testRec(int root) {
    	//System.out.println("root= "+root);
    	int lastIndex = binh.size()-1; 
        if (isLeaf(root)) {
            return true;
        } else {
            int left = 2 * root + 1;
            int right = 2 * root + 2;
            //System.out.println("left = "+left);
            //System.out.println("right = "+right);
            if (right >= lastIndex) {
                return binh.get(left).getWeight() >= binh.get(root).getWeight() && testRec(left);
            } else {
                return binh.get(left).getWeight() >= binh.get(root).getWeight() && testRec(left)
                    && binh.get(right).getWeight() >= binh.get(root).getWeight() && testRec(right);
            }
        }
    }

    // Implémentation de l'algorithme de Prim (exo 3, question 10)
    public List<Edge> Prim(List<UndirectedNode> nodes, List<Edge> edges, UndirectedNode start) {
        List<Edge> mst = new ArrayList<>();
        List<UndirectedNode> visited = new ArrayList<>();
        BinaryHeapEdge heap = new BinaryHeapEdge();

        visited.add(start);

        for (Edge e : edges) {
            if (e.getFirstNode().equals(start) || e.getSecondNode().equals(start)) {
                heap.insert((UndirectedNode) e.getFirstNode(), (UndirectedNode) e.getSecondNode(), e.getWeight());
            }
        }

        while (mst.size() < nodes.size() - 1 && !heap.isEmpty()) {
            Edge minEdge = heap.remove();
            UndirectedNode u = (UndirectedNode) minEdge.getFirstNode();
            UndirectedNode v = (UndirectedNode) minEdge.getSecondNode();

            if (visited.contains(u) && visited.contains(v)) {
                continue;
            }

            mst.add(minEdge);

            UndirectedNode newNode = visited.contains(u) ? v : u;
            visited.add(newNode);

            for (Edge e : edges) {
                UndirectedNode n1 = (UndirectedNode) e.getFirstNode();
                UndirectedNode n2 = (UndirectedNode) e.getSecondNode();
                if ((n1.equals(newNode) && !visited.contains(n2)) || (n2.equals(newNode) && !visited.contains(n1))) {
                    heap.insert(n1, n2, e.getWeight());
                }
            }
        }
        return mst;
    }

    // Test Prim sur un graphe simple
    public static void testPrimSimple() {
        System.out.println("\n--- Test Prim sur un graphe simple ---");
        List<UndirectedNode> nodes = new ArrayList<>();
        for (int i = 0; i < 4; i++) nodes.add(new UndirectedNode(i));
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(nodes.get(0), nodes.get(1), 1));
        edges.add(new Edge(nodes.get(0), nodes.get(2), 4));
        edges.add(new Edge(nodes.get(1), nodes.get(2), 2));
        edges.add(new Edge(nodes.get(1), nodes.get(3), 5));
        edges.add(new Edge(nodes.get(2), nodes.get(3), 3));
        BinaryHeapEdge bhe = new BinaryHeapEdge();
        List<Edge> mst = bhe.Prim(nodes, edges, nodes.get(0));
        System.out.println("Arbre couvrant de poids minimal (Prim):");
        int total = 0;
        for (Edge e : mst) {
            System.out.println(e);
            total += e.getWeight();
        }
        System.out.println("Poids total attendu: 6");
        System.out.println("Poids total obtenu: " + total);
        System.out.println("\nArêtes attendues (ordre quelconque):");
        System.out.println("(n_0,n_1,1)\n(n_1,n_2,2)\n(n_2,n_3,3)");
    }

    // Test Prim sur un graphe plus complexe
    public static void testPrimComplexe() {
        System.out.println("\n--- Test Prim sur un graphe plus complexe ---");
        List<UndirectedNode> nodes = new ArrayList<>();
        for (int i = 0; i < 6; i++) nodes.add(new UndirectedNode(i));
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(nodes.get(0), nodes.get(1), 4));
        edges.add(new Edge(nodes.get(0), nodes.get(2), 4));
        edges.add(new Edge(nodes.get(1), nodes.get(2), 2));
        edges.add(new Edge(nodes.get(2), nodes.get(3), 3));
        edges.add(new Edge(nodes.get(2), nodes.get(4), 6));
        edges.add(new Edge(nodes.get(2), nodes.get(5), 8));
        edges.add(new Edge(nodes.get(3), nodes.get(4), 3));
        edges.add(new Edge(nodes.get(4), nodes.get(5), 9));

        BinaryHeapEdge bhe = new BinaryHeapEdge();
        List<Edge> mst = bhe.Prim(nodes, edges, nodes.get(0));
        System.out.println("Arbre couvrant de poids minimal (Prim):");
        int total = 0;
        for (Edge e : mst) {
            System.out.println(e);
            total += e.getWeight();
        }
        System.out.println("Poids total attendu: 20");
        System.out.println("Poids total obtenu: " + total);
    }

    // Test du tas binaire d'arêtes
    public static void testHeapRemove() {
        BinaryHeapEdge jarjarBin = new BinaryHeapEdge();
        System.out.println("\n--- Test remove sur le tas binaire d'arêtes ---");
        for (int k = 10; k > 0; k--) {
            int rand = 10 + k; // valeur fixe pour test stable
            jarjarBin.insert(new UndirectedNode(k), new UndirectedNode(k+30), rand);
        }
        System.out.println("Tas avant suppression: " + jarjarBin);
        while (!jarjarBin.isEmpty()) {
            System.out.println("Remove: " + jarjarBin.remove());
        }
    }

    public static void testInsertionsBinaryHeap() {
        BinaryHeap heap = new BinaryHeap();
        System.out.println("Insertions: 4, 10, 8, 6, 3");
        heap.insert(4);
        heap.insert(10);
        heap.insert(8);
        heap.insert(6);
        heap.insert(3);
        System.out.println("Tas après insertions: " + heap);
    }

    public static void main(String[] args) {
        BinaryHeapEdge jarjarBin = new BinaryHeapEdge();
        System.out.println(jarjarBin.isEmpty()+"\n");
        int k = 10;
        int m = k;
        int min = 2;
        int max = 20;
        while (k > 0) {
            int rand = min + (int) (Math.random() * ((max - min) + 1));                        
            jarjarBin.insert(new UndirectedNode(k), new UndirectedNode(k+30), rand);            
            k--;
        }
        System.out.println("Tas généré aléatoirement : " + jarjarBin);
        System.out.println("Le tas est valide: " + jarjarBin.test());

        // Lancement des tests
        testHeapRemove();
        testPrimSimple();
        testPrimComplexe();
        testInsertionsBinaryHeap();
    }

}

