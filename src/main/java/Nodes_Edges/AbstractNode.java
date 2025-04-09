package Nodes_Edges;


public abstract class AbstractNode {

	//--------------------------------------------------
	// 				Class variables
	//--------------------------------------------------

	private int label;
	

	//--------------------------------------------------
	// 				Constructors
	//--------------------------------------------------

	public AbstractNode(int i) {
		this.label = i;
	}

	
	// ------------------------------------------
	// 				Accessors
	// ------------------------------------------

	/**
	 * @return an integer which is a unique identifier of a node
	 */
	public int getLabel(){
		return this.label;
	}

	
	/**
	 * check if two nodes are equals => the label is the key
	 * @param n an object which is an abstract node
	 * @return true iff this and n are equal
	 */
	public boolean equals(Object n) {
		return n instanceof AbstractNode && ((AbstractNode) n).getLabel() == this.getLabel();
	}

	public String toString() {
		String s = "n_"+label;
		return s;
	}
}
