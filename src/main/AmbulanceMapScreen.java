package main;

import java.awt.*;
import javax.swing.*;

public class AmbulanceMapScreen extends JPanel {

	private static final long serialVersionUID = 1L;
	private DataModel data;
	
	public AmbulanceMapScreen(DataModel data) {
		this.data = data;
		setBorder(BorderFactory.createLineBorder(Color.black));
		
	}
	
	public Dimension getPreferredSize() {
        return new Dimension(400,520);
    }
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
        
		// draw hospital
		g.setColor(Color.RED);
		g.drawRect(200, 200, 25, 25);
		g.fillRect(200, 200, 25, 25);
		
		// draw stations
		// Greenfields
		g.setColor(Color.BLUE);
		g.drawRect(40, 0, 20, 20);
		g.fillRect(40, 0, 20, 20);
		// Bluelane
		g.drawRect(120, 320, 20, 20);
		g.fillRect(120, 320, 20, 20);
		// Redvill
		g.drawRect(360, 80, 20, 20);
		g.fillRect(360, 80, 20, 20);
		
		// draw ambulances
		g.setColor(Color.GREEN);
		for (Ambulance ambulance: data.getAmbulances().getAmbulanceList()){
			int tempX = ambulance.getX() * 4;
			int tempY = ambulance.getY() * 4;
			g.drawOval(tempX, tempY, 18, 18);
			g.fillOval(tempX, tempY, 18, 18);
		}
		
		// draw patients
		g.setColor(Color.DARK_GRAY);
		for (Patient patient: data.getPatients().data()){
			if (patient.getStatus().equals("Transporting")){
				int tempX = data.getAmbulances().getByID(patient.getAmbulance()).getX() * 4;
				int tempY = data.getAmbulances().getByID(patient.getAmbulance()).getY() * 4;
				g.drawOval(tempX, tempY, 10, 10);
				g.fillOval(tempX, tempY, 10, 10);
			}
			if (patient.getStatus().equals("Completed")){
				g.drawOval(200, 200, 10, 10);
				g.fillOval(200, 200, 10, 10);
			}
			else{
				int tempX = patient.getX() * 4;
				int tempY = patient.getY() * 4;
				g.drawOval(tempX, tempY, 10, 10);
				g.fillOval(tempX, tempY, 10, 10);
			}
		}
		// make key
		g.setColor(Color.BLACK);
		g.drawLine(0, 400, 400, 400);
		g.drawLine(400, 0, 400, 400);
		g.setFont(new Font("default", Font.BOLD, 16));
		g.drawString("Key:", 10, 420);
		
		g.setFont(new Font("default", Font.PLAIN, 16));
		g.drawString("Hospital:", 10, 450);		
		g.drawString("Stations:", 10, 490);		
		g.drawString("Ambulances:", 210, 450);		
		g.drawString("Patients:", 210, 490);
		
		g.setColor(Color.RED);
		g.drawRect(100, 432, 25, 25);
		g.fillRect(100, 432, 25, 25);
		
		g.setColor(Color.BLUE);
		g.drawRect(102, 475, 20, 20);
		g.fillRect(102, 475, 20, 20);
		
		g.setColor(Color.GREEN);
		g.drawOval(310, 435, 18, 18);
		g.fillOval(310, 435, 18, 18);
		
		g.setColor(Color.DARK_GRAY);
		g.drawOval(313, 480, 10, 10);
		g.fillOval(313, 480, 10, 10);
    }
}
