package Assignment1;

import java.util.ArrayList;

import Grid.Cell;
import Grid.CellStatus;
import Grid.Grid;
import Search.Node;
import Search.Problem;
import Search.State;

public class HelpR2D2 extends Problem {
	private Cell telePosition;
	private Cell[] obstaclesPositions;
	private Cell[] padsPositions;
    private int height;
    private int width;
    
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public Cell getTelePosition() {
		return telePosition;
	}

	public void setTelePosition(Cell telePosition) {
		this.telePosition = telePosition;
	}

	public Cell[] getObstaclesPositions() {
		return obstaclesPositions;
	}

	public void setObstaclesPositions(Cell[] obstaclesPositions) {
		this.obstaclesPositions = obstaclesPositions;
	}

	public Cell[] getPadsPositions() {
		return padsPositions;
	}

	public void setPadsPositions(Cell[] padsPositions) {
		this.padsPositions = padsPositions;
	}

	public HelpR2D2(String[] operators, State initState, State[] stateSpace, Cell telePosition,
			Cell[] obstaclesPosition, Cell[] padsPositions) {
		super.setOperators(operators);
		super.setInitState(initState);
		this.telePosition = telePosition;
		this.obstaclesPositions = obstaclesPosition;
		this.padsPositions = padsPositions;
		// super.stateSpace?
	}

	public int pathCost(Node n) {

		// return path cost later
		return 0;
	}

	@Override
	public boolean goalTest(Node node) {
		MyState state = (MyState) node.getCurrentState();
		if (state.getCurrentPosition().equals(this.telePosition)) { // comparing
																	// issue
			System.out.println("R2D2 on teleport cell!");
			if (state.getUnactivatedPads() == 0) {
				System.out.println("Goal success");
				return true;
			}

		}
		return false;
	}

	@Override
	public ArrayList<Node> Expand(Node node) {

		return null;
	}

	public Node up(Node node) {
		MyState state = (MyState) node.getCurrentState();
		Cell[] rocksPositions = state.getRocksPositions();
		Cell currentPosition = state.getCurrentPosition();
		int unactivatedPads = state.getUnactivatedPads();
		Cell [] padsPositions = this.getPadsPositions();
		Cell [] obstaclesPostitions = this.getObstaclesPositions();
		
		// check if upper cell is a ceiling
		if (currentPosition.getY() == 0)
			return null;
		
		//get upper cell
		Cell upperCell = new Cell();
		
		//check if upper cell is an obstacle
		for(int i = 0; i < obstaclesPostitions.length; i++) {
			if(obstaclesPostitions[i].getX() == currentPosition.getX()
					&& obstaclesPostitions[i].getY() == currentPosition.getY() - 1) {
				return null;
			}
		}
		
		//check if upper cell is a rock
		int rockIndex = 0;
		for (int i = 0; i < rocksPositions.length; i++) {
			if (rocksPositions[i].getX() == currentPosition.getX() 
					&& rocksPositions[i].getY() == currentPosition.getY() - 1) {
				rockIndex = i;
				upperCell.setHasRock(true);
				upperCell.setX(rocksPositions[i].getX());
				upperCell.setY(rocksPositions[i].getY());
				//check if the rock is on pad
				for(int j = 0; j < this.padsPositions.length; j++) {
					if(padsPositions[i].getX() == currentPosition.getX()
							&& padsPositions[i].getY() == currentPosition.getY() - 1) {
						upperCell.setStatus(CellStatus.pressurePad);
					}
				}
				break;
			}
		}
		//for now if a rock is on a pad don't move it
		if(upperCell.isActivated()) return null;
		
		//upper cell is a rock
		if(upperCell.getHasRock()) {
			
			//check if second to upper cell is a ceiling
			if(currentPosition.getY() == 1) return null;
			
			//get second to upper
			Cell secondToUpper = new Cell();
			
			//check if second to upper is a rock
			for(int i = 0; i < rocksPositions.length; i++) {
				if (rocksPositions[i].getX() == currentPosition.getX() 
						&& rocksPositions[i].getY() == currentPosition.getY() - 2) {
					return null;
				}
			}
			//check if second to upper is an obstacle
			for(int i = 0; i < obstaclesPostitions.length; i++) {
				if(obstaclesPostitions[i].getX() == currentPosition.getX()
						&& obstaclesPostitions[i].getY() == currentPosition.getY() - 2) {
					return null;
				}
			}
			//check if second to upper is a pad
			for(int i = 0; i < padsPositions.length; i++) {
				if(padsPositions[i].getX() == currentPosition.getX()
						&& padsPositions[i].getY() == currentPosition.getY() - 2) {
					unactivatedPads -= 1; break;
				}
			}
			
			Cell newPosition = new Cell();
			newPosition.setX(currentPosition.getX());
			newPosition.setY(currentPosition.getY()-1);
			rocksPositions[rockIndex].setY(rocksPositions[rockIndex].getY() - 1);
			
			MyState newState = new MyState(newPosition, unactivatedPads, rocksPositions);
			
			Node newNode = new Node(node, newState, node.getDepth()+1, node.getPathCost()+1, "UP");
			
			return newNode;
				
		}
		//upper cell is free
		else {
			Cell newPosition = new Cell();
			newPosition.setX(currentPosition.getX());
			newPosition.setY(currentPosition.getY()-1);
			
			MyState newState = new MyState(newPosition, unactivatedPads, rocksPositions);
			
			Node newNode = new Node(node, newState, node.getDepth()+1, node.getPathCost()+1, "UP");
			
			return newNode;
		}

	}

	public Node down(Node node) {
		return null;
	}

	public Node left(Node node) {
		return null;
	}

	public Node right(Node node) {
		return null;
	}
	public static void main(String[] args) {
		Cell currentPosition = new Cell();
		currentPosition.setX(1);
		currentPosition.setY(2);
		
		Cell rockPosition = new Cell();
		rockPosition.setX(0);
		rockPosition.setY(1);
		
		Cell [] rockPositions = new Cell [1];
		rockPositions[0] = rockPosition;
		
		MyState initState = new MyState(currentPosition, 1, rockPositions);
		
		Cell teleport = new Cell();
		teleport.setX(2);
		teleport.setY(2);
		
		Cell obstacle = new Cell();
		obstacle.setX(2);
		obstacle.setY(0);
		
		Cell [] obstacles = new Cell[1];
		obstacles[0] = obstacle;
		
		Cell pad = new Cell();
		pad.setX(0);
		pad.setY(1);
		
		Cell [] pads = new Cell[1];
		pads[0] = pad;
		
		String [] ops = new String [4];
		MyState [] stateSpace = new MyState[4];
		
		HelpR2D2 problem = new HelpR2D2(ops, initState, stateSpace, teleport, obstacles, pads);
		
		Node node = new Node(null, initState, 0, 0, "UP");
		Node x = problem.up(node);
		
		MyState newState = (MyState) x.getCurrentState();
		
		System.out.println(newState.getCurrentPosition().getY());
		
	}
}
