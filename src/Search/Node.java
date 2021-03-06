package Search;

public class Node implements Comparable<Node> {

	//Class representing the 5 tuple of any search node for a general search tree
	private Node parent;
	private State currentState;
	private int depth;
	private int pathCost;
	private String operator;
	//order variable is used to order the node in the priority queue in case of greedy or A*
	private int order;

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public State getCurrentState() {
		return currentState;
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getPathCost() {
		return pathCost;
	}

	public void setPathCost(int pathCost) {
		this.pathCost = pathCost;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Node(Node parent, State currentState, int depth, int pathCost,
			String operator) {
		this.parent = parent;
		this.currentState = currentState;
		this.depth = depth;
		this.pathCost = pathCost;
		this.operator = operator;
		this.order = this.pathCost;
	}

	@Override
	public int compareTo(Node o) {
		if (o.order > this.order)
			return -1;
		else if (o.order < this.order)
			return 1;
		return 0;
	}

}
