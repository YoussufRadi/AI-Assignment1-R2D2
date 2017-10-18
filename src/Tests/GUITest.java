package Tests;

import Assignment1.HelpR2D2;
import Assignment1.MyState;
import GUI.*;
import Grid.*;
import Grid.Cell;
import Search.GeneralSearch;
import Search.Node;
import Search.QueuingFunction;

public class GUITest {
	
	public static void main(String[] args) {
		MainWindow window = new MainWindow();
		Grid g = window.getGrid();
		System.out.println();
		System.out.println("Testing");
		Cell [] rockPositions = g.getRockPositions();
		System.out.println("Rocks");
		for (int i = 0; i < rockPositions.length; i++){
			Cell c = rockPositions[i];
			System.out.print("R" + c.getX() + " C" + c.getY() + " ");
		}
		System.out.println();
		Cell [] obstaclePositions = g.getObstaclePositions();
		System.out.println("Obstacles");
		for (int i = 0; i < obstaclePositions.length; i++){
			Cell c = obstaclePositions[i];
			System.out.print("R" + c.getX() + " C" + c.getY() + " ");
		}
		System.out.println();
		Cell [] padPositions = g.getPadPositions();
		System.out.println("Pads");
		for (int i = 0; i < padPositions.length; i++){
			Cell c = padPositions[i];
			System.out.print("R" + c.getX() + " C" + c.getY() + " ");
		}
		System.out.println();
		Cell teleport = g.getTeleportPosition();
		Cell currentPosition = g.getAgentPosition();
		
	}

}
