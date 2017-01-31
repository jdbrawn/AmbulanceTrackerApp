package main;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class DataModel {
	private PatientTableModel _patients = new PatientTableModel();
	private AmbulanceTableModel _ambulances = new AmbulanceTableModel();
	
	public PatientTableModel getPatients() {
		return _patients;
	}
	
	public AmbulanceTableModel getAmbulances() {
		return _ambulances;
	}
	
	
	public Ambulance nextMove(Ambulance ambulance){
		String status = ambulance.getStatus();
		
		if (status.equals("At Station")){
			// make list of pending patients
			ArrayList<Patient> pending = new ArrayList<Patient>();
			for (int i=0; i < _patients.data().size(); i++){
				if (_patients.getValueAt(i, 2).equals("Pending")){
					pending.add(_patients.get(i));
				}
			}			
			// determine closest patient to ambulance
			double closestDist = 99999999;
			Patient closestP = new Patient();
			boolean found = false;
			for (int i=0; i < pending.size(); i++){
				Patient tempP = pending.get(i);
				double distance = getDistance(ambulance.getX(), ambulance.getY(), tempP.getX(), tempP.getY());
				if (distance < closestDist){
					closestDist = distance;
					closestP = tempP;
					found = true;
				}
			}			
			// assign closest patient to ambulance
			if (found){
				ambulance.setPatient(closestP.getID());
			}						
			// change status to 'Responding'
			if (found){
				ambulance.setStatus("Responding");
			}
			// change patient status to 'Assigned'
			if (found){
				_patients.getByID(Integer.parseInt(ambulance.getPatient())).setStatus("Assigned");
			}
		}
		else if (status.equals("Responding")){
			Patient assigned = _patients.getByID(Integer.parseInt(ambulance.getPatient()));
			
			// move ambulance 4 moves
			for (int i=0; i<4; i++){
				if (getDistance(ambulance.getX(), ambulance.getY(), assigned.getX(), assigned.getY()) > 0){
					// move 1 move
					int dx = Math.abs(assigned.getX() - ambulance.getX());
					int dy = Math.abs(assigned.getY() - ambulance.getY());
					
					if (dx > dy){
						if ((assigned.getX() - ambulance.getX()) > 0){
							int newX = ambulance.getX() + 1;
							ambulance.setX(newX);
						}
						else{
							int newX = ambulance.getX() - 1;
							ambulance.setX(newX);
						}
					}
					else{
						if ((assigned.getY() - ambulance.getY()) > 0){
							int newY = ambulance.getY() + 1;
							ambulance.setY(newY);
						}
						else{
							int newY = ambulance.getY() - 1;
							ambulance.setY(newY);
						}
					}
				}
			}
			// if ambulance reaches patient, change status to 'At Scene'
			if (getDistance(ambulance.getX(), ambulance.getY(), assigned.getX(), assigned.getY()) == 0){
				ambulance.setStatus("At Scene");
			}
		}
		else if (status.equals("At Scene")){
			int timeAtScene = ambulance.getTime();
			// if ambulance has been at scene for 4 seconds, change status to 'Transporting'		
			if (timeAtScene == 4){
				ambulance.setStatus("Transporting");
				ambulance.setTime(0);
				// also change patients status to 'Transporting' and set ambulance
				_patients.getByID(Integer.parseInt(ambulance.getPatient())).setStatus("Transporting");
				_patients.getByID(Integer.parseInt(ambulance.getPatient())).setAmbulance(ambulance.getID());
			}
			// otherwise do nothing
			else{
				ambulance.setTime(timeAtScene + 1);
			}
		}
		else if (status.equals("Transporting")){
			// move ambulance 3 moves
			for (int i=0; i<3; i++){
				if (getDistance(ambulance.getX(), ambulance.getY(), 50, 50) > 0){
					// move 1 move
					int dx = Math.abs(50 - ambulance.getX());
					int dy = Math.abs(50 - ambulance.getY());
					
					if (dx > dy){
						if ((50 - ambulance.getX()) > 0){
							int newX = ambulance.getX() + 1;
							ambulance.setX(newX);
						}
						else{
							int newX = ambulance.getX() - 1;
							ambulance.setX(newX);
						}
					}
					else{
						if ((50 - ambulance.getY()) > 0){
							int newY = ambulance.getY() + 1;
							ambulance.setY(newY);
						}
						else{
							int newY = ambulance.getY() - 1;
							ambulance.setY(newY);
						}
					}
				}
			}
			// if ambulance reaches hospital, change status to 'At Destination'
			if (getDistance(ambulance.getX(), ambulance.getY(), 50, 50) == 0){
				ambulance.setStatus("At Destination");
				// also change patient status to 'Completed' and unassign patient from ambulance
				_patients.getByID(Integer.parseInt(ambulance.getPatient())).setStatus("Completed");
				_patients.getByID(Integer.parseInt(ambulance.getPatient())).setAmbulance("");
			}
		}
		else if (status.equals("At Destination")){
			int timeAtScene = ambulance.getTime();
			// if ambulance has been at hospital for 2 seconds, change status to 'Returning'		
			if (timeAtScene == 2){
				ambulance.setStatus("Returning");
				ambulance.setTime(0);
			}
			// otherwise do nothing
			else{
				ambulance.setTime(timeAtScene + 1);
			}
		}
		else if (status.equals("Returning")){
			int maxAmbulances = (_ambulances.getRowCount() + 2) / 3; //hopefully this is right lol (+1)
			ArrayList<String> stations = new ArrayList<String>();
			// determine available stations
			if (numAmbulances(10,0) < maxAmbulances){
				stations.add("10,0");
			}
			if (numAmbulances(30,80) < maxAmbulances){
				stations.add("30,80");
			}
			if (numAmbulances(90,20) < maxAmbulances){
				stations.add("90,20");
			}
			// determine nearest available station
			int shortestDist = 99999999;
			int targetX = 0;
			int targetY = 0;
			for (int i=0; i < stations.size(); i++){
				String[] coords = stations.get(i).split(",");
				int tempX = Integer.parseInt(coords[0]);
				int tempY = Integer.parseInt(coords[1]);
				double tempDist = getDistance(ambulance.getX(), ambulance.getY(), tempX, tempY);
				
				if (tempDist < shortestDist){
					shortestDist = (int) tempDist;
					targetX = tempX;
					targetY = tempY;
				}
			}
			// move 3 moves towards this station
			for (int i=0; i<3; i++){
				if (getDistance(ambulance.getX(), ambulance.getY(), targetX, targetY) > 0){
					// move 1 move
					int dx = Math.abs(targetX - ambulance.getX());
					int dy = Math.abs(targetY - ambulance.getY());
					
					if (dx > dy){
						if ((targetX - ambulance.getX()) > 0){
							int newX = ambulance.getX() + 1;
							ambulance.setX(newX);
						}
						else{
							int newX = ambulance.getX() - 1;
							ambulance.setX(newX);
						}
					}
					else{
						if ((targetY - ambulance.getY()) > 0){
							int newY = ambulance.getY() + 1;
							ambulance.setY(newY);
						}
						else{
							int newY = ambulance.getY() - 1;
							ambulance.setY(newY);
						}
					}
				}
			}
			// if ambulance reaches station, change status to 'At Station'
			if (getDistance(ambulance.getX(), ambulance.getY(), targetX, targetY) == 0){
				ambulance.setStatus("At Station");
			}
		}
		return ambulance;
	}
	
	private int numAmbulances(int x, int y) {
		int total = 0;
		for (int i=0; i < _ambulances.getRowCount(); i++){
			if (_ambulances.get(i).getX() == x && _ambulances.get(i).getY() == y){
				total += 1;
			}
		}
		return total;
	}

	public double getDistance(int x1, int y1, int x2, int y2){
		return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
	}
	
	
	public void Save() {
		try {
			Path patientPath = Paths.get("patients-2.csv");
			Files.deleteIfExists(patientPath);
			Files.write(patientPath, _patients.save(), StandardOpenOption.CREATE);

			Path ambulancePath = Paths.get("ambulances-2.csv");
			Files.deleteIfExists(ambulancePath);
			Files.write(ambulancePath, _ambulances.save(), StandardOpenOption.CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static DataModel Load() {
		DataModel data = new DataModel();

		PatientTableModel patients = data.getPatients();
		try {
			Path patientPath = Paths.get("patients.csv");
			String s = patientPath.toAbsolutePath().toString();
			System.out.println("Looking for patients.csv in " + s);			
			List<String> lines = Files.readAllLines(patientPath);
			for (int loop = 1; loop < lines.size(); loop++) {
				String line = lines.get(loop);
				if ((line != null) && (line != "")) {
					patients.parseAndAdd(line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		AmbulanceTableModel ambulances = data.getAmbulances();
		try {
			Path ambulancesPath = Paths.get("ambulances.csv");
			String s = ambulancesPath.toAbsolutePath().toString();
			System.out.println("Looking for patients.csv in " + s);			
			List<String> lines = Files.readAllLines(ambulancesPath);
			for (int loop = 1; loop < lines.size(); loop++) {
				String line = lines.get(loop);
				if ((line != null) && (line != "")) {
					ambulances.parseAndAdd(line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return data;
	}
}
