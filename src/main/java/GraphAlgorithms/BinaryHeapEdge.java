package GraphAlgorithms;

import java.util.ArrayList;
import java.util.List;

import Nodes_Edges.DirectedNode;
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
            return Integer.MAX_VALUE;
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
	 * @param father an index of the list edges
	 * @param child an index of the list edges
	 */
    private void swap(int father, int child) {         
    	Edge temp = binh.get(father);
    	binh.get(father).setFirstNode(binh.get(child).getFirstNode());
    	binh.get(father).setSecondNode(binh.get(child).getSecondNode());
    	binh.get(father).setWeight(binh.get(child).getWeight());
    	binh.get(child).setFirstNode(temp.getFirstNode());
    	binh.get(child).setSecondNode(temp.getSecondNode());
    	binh.get(child).setWeight(temp.getWeight());
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
        // A completer
        System.out.println(jarjarBin);
        System.out.println(jarjarBin.test());
    }

}

