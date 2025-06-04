package AdjacencyList;

import java.util.ArrayList;
import java.util.List;

import AdjacencyMatrix.AdjacencyMatrixUndirectedGraph;
import GraphAlgorithms.GraphTools;
import Nodes_Edges.Edge;
import Nodes_Edges.UndirectedNode;


public class AdjacencyListUndirectedGraph {

	//--------------------------------------------------
    // 				Class variables
    //--------------------------------------------------

	protected List<UndirectedNode> nodes; // list of the nodes in the graph
	protected List<Edge> edges; // list of the edges in the graph
    protected int nbNodes; // number of nodes
    protected int nbEdges; // number of edges

    
    //--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------
    
	public AdjacencyListUndirectedGraph() {
		 this.nodes = new ArrayList<UndirectedNode>();
		 this.edges = new ArrayList<Edge>();
		 this.nbNodes = 0;
	     this.nbEdges = 0;
	}
	
		
	public AdjacencyListUndirectedGraph(List<UndirectedNode> nodes,List<Edge> edges) {
		this.nodes = nodes;
		this.edges = edges;
        this.nbNodes = nodes.size();
        this.nbEdges = edges.size();
        
    }

    public AdjacencyListUndirectedGraph(int[][] matrix) {
        this.nbNodes = matrix.length;
        this.nodes = new ArrayList<UndirectedNode>();
        this.edges = new ArrayList<Edge>();
        
        for (int i = 0; i < this.nbNodes; i++) {
            this.nodes.add(new UndirectedNode(i));
        }
        for (UndirectedNode n1 : this.getNodes()) {
            for (int j = n1.getLabel(); j < matrix[n1.getLabel()].length; j++) {
            	UndirectedNode n2 = this.getNodes().get(j);
                if (matrix[n1.getLabel()][j] != 0) {
                    Edge e1 = new Edge(n1,n2);
                    n1.addEdge(e1);
                    this.edges.add(e1);
                	n2.addEdge(new Edge(n2,n1));
                    this.nbEdges++;
                }
            }
        }
    }

    public AdjacencyListUndirectedGraph(AdjacencyListUndirectedGraph g) {
        super();
        this.nbNodes = g.getNbNodes();
        this.nbEdges = g.getNbEdges();
        this.nodes = new ArrayList<UndirectedNode>();
        this.edges = new ArrayList<Edge>();
        
        
        for (UndirectedNode n : g.getNodes()) {
            this.nodes.add(new UndirectedNode(n.getLabel()));
        }
        
        for (Edge e : g.getEdges()) {
        	this.edges.add(e);
        	UndirectedNode new_n   = this.getNodes().get(e.getFirstNode().getLabel());
        	UndirectedNode other_n = this.getNodes().get(e.getSecondNode().getLabel());
        	new_n.addEdge(new Edge(e.getFirstNode(),e.getSecondNode(),e.getWeight()));
        	other_n.addEdge(new Edge(e.getSecondNode(),e.getFirstNode(),e.getWeight()));
        }        
    }
    

    // ------------------------------------------
    // 				Accessors
    // ------------------------------------------
    
    /**
     * Returns the list of nodes in the graph
     */
    public List<UndirectedNode> getNodes() {
        return this.nodes;
    }
    
    /**
     * Returns the list of edges in the graph
     */
    public List<Edge> getEdges() {
        return this.edges;
    }

    /**
     * Returns the number of nodes in the graph
     */
    public int getNbNodes() {
        return this.nbNodes;
    }
    
    /**
     * @return the number of edges in the graph
     */ 
    public int getNbEdges() {
        return this.nbEdges;
    }

    /**
     * @return true if there is an edge between x and y
     */
    public boolean isEdge(UndirectedNode x, UndirectedNode y) {
        for (Edge edge: this.getEdges()) {
            if (edge.getFirstNode().equals(x)){
                if (edge.getSecondNode().equals(y)){
                    return true;
                }
            }
            if (edge.getFirstNode().equals(y)){
                if (edge.getSecondNode().equals(x)){
                    return true;
                }
            }
        }
    	return false;
    }

    /**
     * Removes edge (x,y) if there exists one. And remove this edge and the inverse in the list of edges from the two extremities (nodes)
     */
    public void removeEdge(UndirectedNode x, UndirectedNode y) {
    	if(isEdge(x,y)){
            Edge edgeXY = new Edge(x, y);
            Edge edgeYX = new Edge(y, x);
            edges.remove(edgeXY);

            for (UndirectedNode node: this.getNodes()) {
                if (node.equals(x)) {
                    node.getIncidentEdges().removeIf(obj -> obj.equals(edgeXY));
                } else if (node.equals(y)) {
                    node.getIncidentEdges().removeIf(obj -> obj.equals(edgeYX));
                }
            }

            nbEdges -= 1;
    	}
    }

    /**
     * Adds edge (x,y) if it is not already present in the graph, requires that nodes x and y already exist. 
     * And adds this edge to the incident list of both extremities (nodes) and into the global list "edges" of the graph.
     * In non-valued graph, every edge has a cost equal to 0.
     */
    public void addEdge(UndirectedNode x, UndirectedNode y) {
    	if(!isEdge(x,y)){
            Edge edgeXY = new Edge(x, y);
            Edge edgeYX = new Edge(y, x);
            edges.add(edgeXY);

            for (UndirectedNode node: this.getNodes()) {
                if (node.equals(x)) {
                    node.getIncidentEdges().add(edgeXY);
                } else if (node.equals(y)) {
                    node.getIncidentEdges().add(edgeYX);
                }
            }

            nbEdges += 1;
    	}
    }

    //--------------------------------------------------
    // 					Methods
    //--------------------------------------------------
    
    

    /**
     * @return the corresponding nodes in the list this.nodes
     */
    public UndirectedNode getNodeOfList(UndirectedNode v) {
        return this.getNodes().get(v.getLabel());
    }
    
    /**
     * @return a matrix representation of the graph 
     */
    public int[][] toAdjacencyMatrix() {
        int[][] matrix = new int[nbNodes][nbNodes];
        for (Edge edge : edges) {
            int i = edge.getFirstNode().getLabel();
            int j = edge.getSecondNode().getLabel();
            matrix[i][j] = 1;
            matrix[j][i] = 1;
        }
        return matrix;
    }

    
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("List of nodes and their neighbours :\n");
        for (UndirectedNode n : this.nodes) {
            s.append("Node ").append(n).append(" : ");
            s.append("\nList of incident edges : ");
            for (Edge e : n.getIncidentEdges()) {
                s.append(e).append("  ");
            }
            s.append("\n");            
        }
        s.append("\nList of edges :\n");
        for (Edge e : this.edges) {
        	s.append(e).append("  ");
        }
        s.append("\n");
        return s.toString();
    }

    public static void main(String[] args) {
        int[][] mat = GraphTools.generateGraphData(10, 20, false, true, false, 100001);
        GraphTools.afficherMatrix(mat);
        AdjacencyListUndirectedGraph al = new AdjacencyListUndirectedGraph(mat);
        System.out.println(al);        
        System.out.println("(n_2,n_5) is it in the graph ? " +  al.isEdge(al.getNodes().get(2), al.getNodes().get(5)));

        AdjacencyListUndirectedGraph adjacencyList = new AdjacencyListUndirectedGraph(new int[][]{
            {0, 1, 0},
            {1, 0, 1},
            {0, 1, 0}
        });

        System.out.println("Testing isEdge method:");
        testIsEdge(adjacencyList, adjacencyList.getNodes().get(0), adjacencyList.getNodes().get(1), true);
        testIsEdge(adjacencyList, adjacencyList.getNodes().get(0), adjacencyList.getNodes().get(2), false);

        System.out.println("\nTesting addEdge method:");
        testAddEdge(adjacencyList, adjacencyList.getNodes().get(0), adjacencyList.getNodes().get(2), true);
        testAddEdge(adjacencyList, adjacencyList.getNodes().get(1), adjacencyList.getNodes().get(2), false);

        System.out.println("\nTesting removeEdge method:");
        testRemoveEdge(adjacencyList, adjacencyList.getNodes().get(0), adjacencyList.getNodes().get(2), false);
        testRemoveEdge(adjacencyList, adjacencyList.getNodes().get(1), adjacencyList.getNodes().get(2), true);

        System.out.println("\nTesting toAdjacencyMatrix method:");
        testToAdjacencyMatrix(adjacencyList, new int[][]{
                {0, 1, 0},
                {1, 0, 1},
                {0, 1, 0}
        });
        adjacencyList = new AdjacencyListUndirectedGraph(new int[][]{
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        });
        testToAdjacencyMatrix(adjacencyList, new int[][]{
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        });
    }

    private static void testIsEdge(AdjacencyListUndirectedGraph al, UndirectedNode x, UndirectedNode y, boolean expected) {
        AdjacencyListUndirectedGraph adjacencyList = new AdjacencyListUndirectedGraph(al);
        System.out.println("== Test isEdge(" + x + ", " + y + ") ==");
        System.out.println("\tisEdge(" + x + "," + y + ") : " + (adjacencyList.isEdge(x, y) == expected ? "OK" : "KO") +
                " | " + adjacencyList.getEdges() + " : value");
    }

    private static void testAddEdge(AdjacencyListUndirectedGraph al, UndirectedNode x, UndirectedNode y, boolean expectedAdd) {
        AdjacencyListUndirectedGraph adjacencyList = new AdjacencyListUndirectedGraph(al);
        System.out.println("=== Test addEdge(" + x + ", " + y + ") ===");

        // Save before state
        int nbEdgeBefore = adjacencyList.getNbEdges();
        System.out.println("\t[Before] all edges of the graph: " + adjacencyList.getEdges());

        adjacencyList.addEdge(x, y);

        // Check if the edge was added correctly
        boolean ok = false;
        if (expectedAdd){
            ok = adjacencyList.isEdge(x, y) && adjacencyList.isEdge(y, x) && (nbEdgeBefore + 1 == adjacencyList.getNbEdges());
        } else {
            ok = adjacencyList.isEdge(x, y) && adjacencyList.isEdge(y, x) && (nbEdgeBefore == adjacencyList.getNbEdges());
        }

        System.out.println("\t[After] all edges of the graph: " + adjacencyList.getEdges());
        System.out.println("\tResult: " + (ok ? "OK" : "KO"));
    }

    private static void testRemoveEdge(AdjacencyListUndirectedGraph al, UndirectedNode x, UndirectedNode y, boolean expectedRemove) {
        AdjacencyListUndirectedGraph adjacencyList = new AdjacencyListUndirectedGraph(al);
        System.out.println("=== Test removeEdge(" + x + ", " + y + ") ===");

        // Save before state
        int nbEdgeBefore = adjacencyList.getNbEdges();
        System.out.println("\t[Before] all edges of the graph: " + adjacencyList.getEdges());

        adjacencyList.removeEdge(x, y);

        // Check if the edge was removed correctly
        boolean ok = false;
        if (expectedRemove){
            ok = !adjacencyList.isEdge(x, y) && !adjacencyList.isEdge(y, x) && (nbEdgeBefore - 1 == adjacencyList.getNbEdges());
        } else {
            ok = !adjacencyList.isEdge(x, y) && !adjacencyList.isEdge(y, x) && (nbEdgeBefore == adjacencyList.getNbEdges());
        }
        System.out.println("\t[After] all edges of the graph: " + adjacencyList.getEdges());
        System.out.println("\tResult: " + (ok ? "OK" : "KO"));
    }

    private static void testToAdjacencyMatrix(AdjacencyListUndirectedGraph al, int[][] expectedMatrix) {
        AdjacencyListUndirectedGraph adjacencyList = new AdjacencyListUndirectedGraph(al);
        System.out.println("=== Test toAdjacencyMatrix ===");

        int[][] matrix = adjacencyList.toAdjacencyMatrix();

        System.out.println("\tEdge: " + adjacencyList.getEdges());
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
}
