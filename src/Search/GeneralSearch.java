package Search;

import java.util.*;

import Assignment1.HelpR2D2;
import Assignment1.MyState;
import Grid.Cell;

public class GeneralSearch {

	private Deque<Node> nodes;
	private QueuingFunction qingFunc;
	private PriorityQueue<Node> nodesPrio = new PriorityQueue<>();
	private Problem problem;

	public Deque<Node> getNodes() {
		return nodes;
	}

	public void setNodes(Deque<Node> nodes) {
		this.nodes = nodes;
	}

	public QueuingFunction getQingFunc() {
		return qingFunc;
	}

	public void setQingFunc(QueuingFunction qingFunc) {
		this.qingFunc = qingFunc;
	}

	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	public GeneralSearch(Problem problem, QueuingFunction qingFunc) {
		this.problem = problem;
		this.qingFunc = qingFunc;
		nodes = new LinkedList<Node>();
	}

	public Node search() {
		Node initialNode = new Node(null, this.problem.getInitState(), 0, 0, "");

		if (this.qingFunc == QueuingFunction.BF || this.qingFunc == QueuingFunction.DF) {
			nodes.addFirst(initialNode);
			while (!nodes.isEmpty()) {
				Node node = nodes.pop();
				if (problem.goalTest(node)) {
					System.out.println("SUCCESS!!");
					return node;
				}

				if (this.problem.pastState(node))
					continue; // check if state is already traversed before
				switch (this.qingFunc) {
				case BF:
					nodes = this.BFS(nodes, node);
					break;
				case DF:
					nodes = this.DFS(nodes, node);
					break;
				default:
					break;
				}
			}
		} else if (this.qingFunc == QueuingFunction.ID) {
			return IDS(nodes,initialNode);
		} else {
			nodesPrio.add(initialNode);
			while (!nodesPrio.isEmpty()) {
				Node node = nodesPrio.poll();
				if (problem.goalTest(node)) {
					System.out.println("SUCCESS!!");
					return node;
				}

				if (this.problem.pastState(node))
					continue; // check if state is already traversed before
				switch (this.qingFunc) {
				case UC:
					nodesPrio = this.uniformedCost(node, nodesPrio);
					break;
				case Greedy:
					nodesPrio = this.greedy(node, nodesPrio);
					break;
				case AStar:
					nodesPrio = this.AStar(node, nodesPrio);
					break;
				default:
					break;
				}
			}
		}
		return null;
	}

	public Deque<Node> BFS(Deque<Node> nodes, Node node) {
		ArrayList<Node> children = this.problem.Expand(node);
		// int i = 0;
		for (Node childNode : children) {
			// System.out.println(i++);
			nodes.addLast(childNode);
		}
		return nodes;
	}

	public Deque<Node> DFS(Deque<Node> nodes, Node node) {
		ArrayList<Node> children = this.problem.Expand(node);
		// int i = 0;
		for (Node childNode : children) {
			// System.out.println(i++);
			nodes.addFirst(childNode);
		}
		return nodes;
	}

	public Node IDS(Deque<Node> nodes, Node intialNode) {
		int counter = 0;
		do {
			nodes.add(intialNode);
			while (!nodes.isEmpty()) {
				System.out.println("Counter"+counter);
				Node node = nodes.pop();
				if (problem.goalTest(node)) {
					System.out.println("SUCCESS!!");
					return node;
				}
				if (this.problem.pastState(node))
					continue; // check if state is already traversed before
				nodes = IDS(nodes, node, counter);
				System.out.println("IDS");
			}
			counter++;
			this.problem.clearPastState();
		} while (true);
	}

	public Deque<Node> IDS(Deque<Node> nodes, Node node, int counter) {
		ArrayList<Node> children = this.problem.Expand(node);
		// int i = 0;
		for (Node childNode : children) {
			// System.out.println(i++);
			//System.out.println(childNode.getPathCost());
			if (counter < childNode.getPathCost())
				return nodes;
			nodes.addFirst(childNode);
		}
		//System.out.println("size"+ nodes.size());
		return nodes;
	}

	public PriorityQueue<Node> uniformedCost(Node node, PriorityQueue<Node> nodes) {
		ArrayList<Node> children = this.problem.Expand(node);
		// int i = 0;
		for (Node childNode : children) {
			// System.out.println(i++);
			nodes.add(childNode);
		}
		return nodes;
	}
	
	public PriorityQueue<Node> greedy(Node node, PriorityQueue<Node> nodes) {
		ArrayList<Node> children = this.problem.Expand(node);
		// int i = 0;
		for (Node childNode : children) {
			// System.out.println(i++);
			int heuristicValue = node.getCurrentState().heuristic();
			childNode.setOrder(heuristicValue);
			nodes.add(childNode);
		}
		return nodes;
	}
	
	public PriorityQueue<Node> AStar(Node node, PriorityQueue<Node> nodes) {
		ArrayList<Node> children = this.problem.Expand(node);
		// int i = 0;
		for (Node childNode : children) {
			// System.out.println(i++);
			int heuristicValue = 3;
			childNode.setOrder(childNode.getPathCost() + heuristicValue);
			nodes.add(childNode);
		}
		return nodes;
	}

	public static void main(String[] args) {
		Cell currentPosition = new Cell();
		currentPosition.setX(0);
		currentPosition.setY(2);

		Cell rockPosition = new Cell();
		rockPosition.setX(0);
		rockPosition.setY(1);

		Cell[] rockPositions = new Cell[1];
		rockPositions[0] = rockPosition;

		MyState initState = new MyState(currentPosition, 1, rockPositions);

		Cell teleport = new Cell();
		teleport.setX(2);
		teleport.setY(2);

		Cell obstacle = new Cell();
		obstacle.setX(2);
		obstacle.setY(0);

		Cell[] obstacles = new Cell[1];
		obstacles[0] = obstacle;

		Cell pad = new Cell();
		pad.setX(0);
		pad.setY(0);

		Cell[] pads = new Cell[1];
		pads[0] = pad;

		String[] ops = new String[4];
		MyState[] stateSpace = new MyState[4];

		HelpR2D2 problemR2D2 = new HelpR2D2(ops, initState, stateSpace, teleport, obstacles, pads, 3, 3);

		GeneralSearch gs = new GeneralSearch(problemR2D2, QueuingFunction.Greedy);
		Node n = gs.search();
		System.out.println("max depth: "+ n.getDepth());
		while (n != null) {
			System.out.println("Operator: " + n.getOperator());
			n = n.getParent();
		}
	}
}
