package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import Grid.*;

public class MainWindow extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel mainPanel;
	Grid grid;
	
	public MainWindow(){
		
		this.setTitle("Help R2D2");
		this.setSize(800, 600);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				
		this.mainPanel = new JPanel();
		mainPanel.setSize(800, 600);
		mainPanel.setLayout(null);
		mainPanel.setBackground(Color.black);
		
		Cell obs = new Cell();
		obs.setBounds(20, 20, 50, 50);
		obs.createObstacle();
		mainPanel.add(obs);
		JLabel obsLabel = new JLabel("Obs");
		obsLabel.setForeground(Color.white);
		obsLabel.setBounds(20, 80, 50, 30);
		mainPanel.add(obsLabel);
		
		Cell pad = new Cell();
		pad.setBounds(90, 20, 50, 50);
		pad.createPad();
		mainPanel.add(pad);
		JLabel padLabel = new JLabel("Pad");
		padLabel.setForeground(Color.white);
		padLabel.setBounds(90, 80, 50, 30);
		mainPanel.add(padLabel);
		
		Cell teleport = new Cell();
		teleport.setBounds(150, 20, 50, 50);
		teleport.createTeleport();
		mainPanel.add(teleport);
		JLabel teleportLabel = new JLabel("Tele");
		teleportLabel.setForeground(Color.white);
		teleportLabel.setBounds(150, 80, 50, 30);
		mainPanel.add(teleportLabel);
		
		Cell agent = new Cell();
		agent.setBounds(220, 20, 50, 50);
		agent.addAgent();
		mainPanel.add(agent);
		JLabel agentLabel = new JLabel("Agent");
		agentLabel.setForeground(Color.white);
		agentLabel.setBounds(220, 80, 50, 30);
		mainPanel.add(agentLabel);
		
		Cell rock = new Cell();
		rock.setBounds(290, 20, 50, 50);
		rock.addRock();
		mainPanel.add(rock);
		JLabel rockLabel = new JLabel("Rock");
		rockLabel.setForeground(Color.white);
		rockLabel.setBounds(290, 80, 50, 30);
		mainPanel.add(rockLabel);
				
		
		this.drawGrid(Grid.genGrid());
		grid.displayGrid();
		
		JButton df = new JButton("DF");
		df.setBounds(350, 100, 100, 30);
		df.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		JButton bf = new JButton("BF");
		bf.setBounds(350, 150, 100, 30);
		JButton id = new JButton("ID");
		id.setBounds(350, 200, 100, 30);
		JButton uc = new JButton("UC");
		uc.setBounds(350, 250, 100, 30);
		JButton greedy1 = new JButton("Greedy 1");
		greedy1.setBounds(350, 300, 100, 30);
		JButton greedy2 = new JButton("Greedy 2");
		greedy2.setBounds(350, 350, 100, 30);
		JButton aStar1 = new JButton("A* 1");
		aStar1.setBounds(350, 400, 100, 30);
		JButton aStar2 = new JButton("A* 2");
		aStar2.setBounds(350, 450, 100, 30);	
		JButton next = new JButton("Next");
		next.setBounds(500, 350, 100, 30);		
		mainPanel.add(df);
		mainPanel.add(bf);
		mainPanel.add(id);
		mainPanel.add(uc);
		mainPanel.add(greedy1);
		mainPanel.add(greedy2);
		mainPanel.add(aStar1);
		mainPanel.add(aStar2);
		mainPanel.add(next);
		
		JLabel path = new JLabel("Veeeeryyyyy looooong teeeeeeeeext!");
		path.setBounds(50, 500, 550, 50);
		path.setForeground(Color.white);
		
		mainPanel.add(path);
		this.add(mainPanel);
		this.validate();
		this.repaint();
		
	}
	
	public void drawGrid(Grid g){
		this.grid = g;
		for (int i = 0; i < grid.getWidth(); i++){
			for (int j = 0; j < grid.getHeight(); j++){
				Cell cell = new Cell();
				cell.setBounds(20+(i*50), 150+(j*50), 50, 50);
				switch (grid.getCells()[i][j].getStatus()) {
				case obstacle:
					cell.createObstacle();
					break;
				case pressurePad:
					cell.createPad();
				case teleport:
					cell.createTeleport();
				default:
					break;
				}
				
				if (grid.getCells()[i][j].getHasRock()){
					if (grid.getCells()[i][j].getStatus() == CellStatus.pressurePad){
						cell.addRockToPad();
					}
					else {
						if (grid.getCells()[i][j].getStatus() == CellStatus.teleport){
							cell.addRockToTeleport();
						}
						else {
							cell.addRock();
						}
					}
				}
				
				if (grid.getAgentPosition().getX() == i && grid.getAgentPosition().getY() == j){
					if (grid.getCells()[i][j].getStatus() == CellStatus.pressurePad){
						cell.addAgentToPad();
					}
					else {
						if (grid.getCells()[i][j].getStatus() == CellStatus.teleport){
							cell.addAgentTeleport();
						}
						else {
							cell.addAgent();
						}
					}
				}
				mainPanel.add(cell);
			}
		}
		this.validate();
		this.repaint();
	}
	
	
	public static void main(String[] args) {
		new MainWindow();
	}

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}
}
