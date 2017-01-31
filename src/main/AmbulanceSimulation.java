/*
 * COMPSCI 230 S2 2016 Assignment 3
 * 
 * Name: John David Brawn
 * UPI: jbra988
 * Email: jdbrawn@bu.edu
 */

package main;

import javax.swing.JFrame;

public class AmbulanceSimulation {

	public static void main(String[] args) {
		DataModel model = DataModel.Load();
		
		JFrame f = new JFrame("Ambulance Map");
		f.setLocation(600, 25);
        f.add(new AmbulanceMapScreen(model));
        f.pack();
		
		AmbulanceListWindow window = new AmbulanceListWindow(model, f);
		f.setVisible(true);
		window.show();
		
	}

}
