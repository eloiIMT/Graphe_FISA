package AdjacencyList;

import java.util.ArrayList;
import java.util.List;



import GraphAlgorithms.GraphTools;
import Nodes_Edges.Arc;
import Nodes_Edges.DirectedNode;
import Nodes_Edges.Edge;
import Nodes_Edges.UndirectedNode;



public class AdjacencyListDirectedGraph {

	//--------------------------------------------------
    // 				Class variables
    //--------------------------------------------------

	private static int _DEBBUG =0;
	
	protected List<DirectedNode> nodes; // list of the nodes in the graph
	protected List<Arc> arcs; // list of the arcs in the graph
    protected int nbNodes; // number of nodes
    protected int nbArcs; // number of arcs
	
    

    
    //--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------
 

	public AdjacencyListDirectedGraph(){
		this.nodes = new ArrayList<DirectedNode>();
		this.arcs= new ArrayList<Arc>();
		this.nbNodes = 0;
	    this.nbArcs = 0;		
	}
	
	public AdjacencyListDirectedGraph(List<DirectedNode> nodes,List<Arc> arcs) {
		this.nodes = nodes;
		this.arcs= arcs;
        this.nbNodes = nodes.size();
        this.nbArcs = arcs.size();                
    }

    public AdjacencyListDirectedGraph(int[][] matrix) {
        this.nbNodes = matrix.length;
        this.nodes = new ArrayList<DirectedNode>();
        this.arcs= new ArrayList<Arc>();
        
        for (int i = 0; i < this.nbNodes; i++) {
            this.nodes.add(new DirectedNode(i));
        }
        
        for (DirectedNode n1 : this.getNodes()) {
            for (int j = 0; j < matrix[n1.getLabel()].length; j++) {
            	DirectedNode n2 = this.getNodes().get(j);
                if (matrix[n1.getLabel()][j] != 0) {
                	Arc a = new Arc(n1,n2);
                    n1.addArc(a);
                    this.arcs.add(a);                    
                    n2.addArc(a);
                    this.nbArcs++;
                }
            }
        }
    }

    public AdjacencyListDirectedGraph(AdjacencyListDirectedGraph g) {
        super();
        this.nodes = new ArrayList<>();
        this.arcs= new ArrayList<Arc>();
        this.nbNodes = g.getNbNodes();
        this.nbArcs = g.getNbArcs();
        
        for(DirectedNode n : g.getNodes()) {
            this.nodes.add(new DirectedNode(n.getLabel()));
        }
        
        for (Arc a1 : g.getArcs()) {
        	this.arcs.add(a1);
        	DirectedNode new_n   = this.getNodes().get(a1.getFirstNode().getLabel());
        	DirectedNode other_n = this.getNodes().get(a1.getSecondNode().getLabel());
        	Arc a2 = new Arc(a1.getFirstNode(),a1.getSecondNode(),a1.getWeight());
        	new_n.addArc(a2);
        	other_n.addArc(a2);
        }  

    }

    // ------------------------------------------
    // 				Accessors
    // ------------------------------------------

    /**
     * Returns the list of nodes in the graph
     */
    public List<DirectedNode> getNodes() {
        return nodes;
    }
    
    /**
     * Returns the list of nodes in the graph
     */
    public List<Arc> getArcs() {
        return arcs;
    }

    /**
     * Returns the number of nodes in the graph
     */
    public int getNbNodes() {
        return this.nbNodes;
    }
    
    /**
	 * @return the number of arcs in the graph
 	 */
    public int getNbArcs() {
        return this.nbArcs;
    }

    /**
	 * @return true if arc (from,to) exists in the graph
 	 */
    public boolean isArc(DirectedNode from, DirectedNode to) {
        for (Arc arc: this.getArcs()) {
            if (arc.getFirstNode().equals(from)){
                if (arc.getSecondNode().equals(to)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
	 * Removes the arc (from,to), if it exists. And remove this arc and the inverse in the list of arcs from the two extremities (nodes)
 	 */
    public void removeArc(DirectedNode from, DirectedNode to) {
        if(isArc(from,to)){
            Arc arc = new Arc(from, to);
            arcs.remove(arc);

            for (DirectedNode node: this.getNodes()) {
                if (node.equals(from)) {
                    node.getArcSucc().removeIf(obj -> obj.equals(arc));
                }
                if (node.equals(to)) {
                    node.getArcPred().removeIf(obj -> obj.equals(arc));
                }
            }

            nbArcs -= 1;
        }
    }

    /**
	* Adds the arc (from,to) if it is not already present in the graph, requires the existing of nodes from and to. 
	* And add this arc to the incident list of both extremities (nodes) and into the global list "arcs" of the graph.   	 
  	* On non-valued graph, every arc has a weight equal to 0.
 	*/
    public void addArc(DirectedNode from, DirectedNode to) {
        if(!isArc(from,to)){
            Arc arc = new Arc(from, to);
            arcs.add(arc);

            for (DirectedNode node: this.getNodes()) {
                if (node.equals(from)) {
                    node.getArcSucc().add(arc);
                }
                if (node.equals(to)) {
                    node.getArcPred().add(arc);
                }
            }

            nbArcs += 1;
        }
    }

    //--------------------------------------------------
    // 				Methods
    //--------------------------------------------------

     /**
     * @return the corresponding nodes in the list this.nodes
     */
    public DirectedNode getNodeOfList(DirectedNode src) {
        return this.getNodes().get(src.getLabel());
    }

    /**
     * @return the adjacency matrix representation int[][] of the graph
     */
    public int[][] toAdjacencyMatrix() {
        int[][] matrix = new int[nbNodes][nbNodes];
        for (Arc arc : arcs) {
            int i = arc.getFirstNode().getLabel();
            int j = arc.getSecondNode().getLabel();
            matrix[i][j] = 1;
        }
        return matrix;
    }

    /**
	 * @return a new graph implementing IDirectedGraph interface which is the inverse graph of this
 	 */
    public AdjacencyListDirectedGraph computeInverse() {
        // Copie du graphe (copie profonde attendue sur les nœuds)
        AdjacencyListDirectedGraph g = new AdjacencyListDirectedGraph(this);

        // Nettoie tous les arcs
        for (DirectedNode n : g.getNodes()) {
            n.getArcSucc().clear();
            n.getArcPred().clear();
        }
        g.getArcs().clear();

        // Inverse les arcs
        for (Arc arc : this.getArcs()) {
            int fromLabel = arc.getFirstNode().getLabel();
            int toLabel = arc.getSecondNode().getLabel();

            DirectedNode from = g.getNodes().get(fromLabel);
            DirectedNode to = g.getNodes().get(toLabel);

            Arc reversedArc = new Arc(to, from);

            to.getArcSucc().add(reversedArc);
            from.getArcPred().add(reversedArc);
            g.getArcs().add(reversedArc);
        }

        return g;
    }


    @Override
    public String toString(){
    	StringBuilder s = new StringBuilder();
        s.append("List of nodes and their successors/predecessors :\n");
        for (DirectedNode n : this.nodes) {
            s.append("\nNode ").append(n).append(" : ");
            s.append("\nList of out-going arcs: ");
            for (Arc a : n.getArcSucc()) {
                s.append(a).append("  ");
            }
            s.append("\nList of in-coming arcs: ");
            for (Arc a : n.getArcPred()) {
                s.append(a).append("  ");
            }
            s.append("\n");
        }
        s.append("\nList of arcs :\n");
        for (Arc a : this.arcs) {
        	s.append(a).append("  ");
        }
        s.append("\n");
        return s.toString();
    }

    public static void main(String[] args) {
        int[][] Matrix = GraphTools.generateGraphData(10, 20, false, false, false, 100001);
        GraphTools.afficherMatrix(Matrix);
        AdjacencyListDirectedGraph al = new AdjacencyListDirectedGraph(Matrix);
        System.out.println(al);
        System.out.println("(n_7,n_3) is it in the graph ? " +  al.isArc(al.getNodes().get(7), al.getNodes().get(3)));

        AdjacencyListDirectedGraph adjacencyList = new AdjacencyListDirectedGraph(new int[][]{
                {0, 1, 0},
                {0, 0, 0},
                {0, 1, 0}
        });

        System.out.println("Testing isArc method:");
        testIsArc(adjacencyList, adjacencyList.getNodes().get(0), adjacencyList.getNodes().get(1), true);
        testIsArc(adjacencyList, adjacencyList.getNodes().get(0), adjacencyList.getNodes().get(2), false);

        System.out.println("\nTesting addArc method:");
        testAddArc(adjacencyList, adjacencyList.getNodes().get(0), adjacencyList.getNodes().get(2), true);
        testAddArc(adjacencyList, adjacencyList.getNodes().get(2), adjacencyList.getNodes().get(1), false);


        System.out.println("\nTesting removeEdge method:");
        testRemoveArc(adjacencyList, adjacencyList.getNodes().get(0), adjacencyList.getNodes().get(1), true);
        testRemoveArc(adjacencyList, adjacencyList.getNodes().get(1), adjacencyList.getNodes().get(2), false);

        System.out.println("\nTesting toAdjacencyMatrix method:");
        testToAdjacencyMatrix(adjacencyList, new int[][]{
                {0, 1, 0},
                {0, 0, 0},
                {0, 1, 0}
        });
        adjacencyList = new AdjacencyListDirectedGraph(new int[][]{
                {0, 1, 0},
                {0, 0, 1},
                {0, 0, 0}
        });
        testToAdjacencyMatrix(adjacencyList, new int[][]{
                {0, 1, 0},
                {0, 0, 1},
                {0, 0, 0}
        });

        System.out.println("\nTesting computeInverse method:");
        AdjacencyListDirectedGraph expectedInverse = new AdjacencyListDirectedGraph(new int[][]{
                {0, 0, 0},
                {1, 0, 0},
                {0, 1, 0}
        });
        testComputeInverse(adjacencyList, expectedInverse);
        expectedInverse = new AdjacencyListDirectedGraph(new int[][]{
                {0, 1, 0},
                {1, 0, 1},
                {0, 0, 0}
        });
        adjacencyList = new AdjacencyListDirectedGraph(new int[][]{
                {0, 1, 0},
                {1, 0, 0},
                {0, 1, 0}
        });
        testComputeInverse(adjacencyList, expectedInverse);
        expectedInverse = new AdjacencyListDirectedGraph(new int[][]{
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        });
        adjacencyList = new AdjacencyListDirectedGraph(new int[][]{
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        });
        testComputeInverse(adjacencyList, expectedInverse);

    }

    private static void testIsArc(AdjacencyListDirectedGraph al, DirectedNode x, DirectedNode y, boolean expected) {
        AdjacencyListDirectedGraph adjacencyList = new AdjacencyListDirectedGraph(al);
        System.out.println("== Test isArc(" + x + ", " + y + ") ==");
        System.out.println("\tisArc(" + x + "," + y + ") : " + (adjacencyList.isArc(x, y) == expected ? "OK" : "KO") +
                " | " + adjacencyList.getArcs() + " : value");
    }

    private static void testAddArc(AdjacencyListDirectedGraph al, DirectedNode x, DirectedNode y, boolean expectedAdd) {
        AdjacencyListDirectedGraph adjacencyList = new AdjacencyListDirectedGraph(al);
        System.out.println("=== Test addArc(" + x + ", " + y + ") ===");

        // Save before state
        int nbArcBefore = adjacencyList.getNbArcs();
        System.out.println("\t[Before] all arcs of the graph: " + adjacencyList.getArcs());

        adjacencyList.addArc(x, y);

        // Check if the edge was added correctly
        boolean ok = false;
        if (expectedAdd){
            ok = adjacencyList.isArc(x, y) && (nbArcBefore + 1 == adjacencyList.getNbArcs());
        } else {
            ok = adjacencyList.isArc(x, y) && (nbArcBefore == adjacencyList.getNbArcs());
        }

        System.out.println("\t[After] all arcs of the graph: " + adjacencyList.getArcs());
        System.out.println("\tResult: " + (ok ? "OK" : "KO"));
    }

    private static void testRemoveArc(AdjacencyListDirectedGraph al, DirectedNode x, DirectedNode y, boolean expectedRemove) {
        AdjacencyListDirectedGraph adjacencyList = new AdjacencyListDirectedGraph(al);
        System.out.println("=== Test removeArc(" + x + ", " + y + ") ===");

        // Save before state
        int nbArcBefore = adjacencyList.getNbArcs();
        System.out.println("\t[Before] all arcs of the graph: " + adjacencyList.getArcs());

        adjacencyList.removeArc(x, y);

        // Check if the edge was added correctly
        boolean ok = false;
        if (expectedRemove){
            ok = !adjacencyList.isArc(x, y) && (nbArcBefore - 1 == adjacencyList.getNbArcs());
        } else {
            ok = !adjacencyList.isArc(x, y) && (nbArcBefore == adjacencyList.getNbArcs());
        }

        System.out.println("\t[After] all arcs of the graph: " + adjacencyList.getArcs());
        System.out.println("\tResult: " + (ok ? "OK" : "KO"));
    }

    private static void testToAdjacencyMatrix(AdjacencyListDirectedGraph al, int[][] expectedMatrix) {
        AdjacencyListDirectedGraph adjacencyList = new AdjacencyListDirectedGraph(al);
        System.out.println("=== Test toAdjacencyMatrix ===");

        int[][] matrix = adjacencyList.toAdjacencyMatrix();

        System.out.println("\tArc: " + adjacencyList.getArcs());
        System.out.println("\tAdjacency Matrix: ");
        GraphTools.afficherMatrix(matrix);

        boolean ok = true;
        if (matrix.length != expectedMatrix.length) {
            ok = false;
        } else {
            for (int i = 0; i < matrix.length; i++) {
                if (matrix[i].length != expectedMatrix[i].length) {
                    ok = false;
                    break;
                }
                for (int j = 0; j < matrix[i].length; j++) {
                    if (matrix[i][j] != expectedMatrix[i][j]) {
                        ok = false;
                        break;
                    }
                }
            }
        }
        System.out.println("\tResult: " + (ok ? "OK" : "KO"));
    }

    private static void testComputeInverse(AdjacencyListDirectedGraph al, AdjacencyListDirectedGraph expectedInverse) {
        AdjacencyListDirectedGraph adjacencyList = new AdjacencyListDirectedGraph(al);
        System.out.println("=== Test computeInverse ===");

        AdjacencyListDirectedGraph inverseGraph = adjacencyList.computeInverse();
        System.out.println("\tOriginal Graph: ");
        System.out.println(adjacencyList.getArcs());

        System.out.println("\tInverse Graph: ");
        System.out.println(inverseGraph.getArcs());
        boolean ok = true;

        //System.out.println(adjacencyList);
        //System.out.println(inverseGraph);

        if (inverseGraph.getNbNodes() != expectedInverse.getNbNodes() || inverseGraph.getNbArcs() != expectedInverse.getNbArcs()) {
            ok = false;
        } else {
            for (DirectedNode n : inverseGraph.getNodes()) {
                if (!expectedInverse.getNodes().contains(n)) {
                    ok = false;
                    break;
                }
            }
            for (Arc a : inverseGraph.getArcs()) {
                if (!expectedInverse.getArcs().contains(a)) {
                    ok = false;
                    break;
                }
            }
        }
        System.out.println("\tResult: " + (ok ? "OK" : "KO"));
    }
}
