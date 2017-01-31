package main;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public final class AmbulanceTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -7793587759092916548L;
	private String[] _columnNames = {"ID",
			"Location",
			"Status",
			"Patient"};
	private ArrayList<Ambulance> _data = new ArrayList<Ambulance>();
	
	public int getColumnCount() {
		return _columnNames.length;
	}
	
	public ArrayList<Ambulance> getAmbulanceList(){
		return _data;
	}
	
	public String getColumnName(int col) {
		return _columnNames[col];
	}

	public int getRowCount() {
		return _data.size();
	}

	public Ambulance getByID(String id) {
		for (Ambulance ambulance : _data) {
			if (id.equals(ambulance.getID())) {
				return ambulance;
			}
		}
		return null;
	}

	public Ambulance get(int row) {
		return _data.get(row);
	}

	public Object getValueAt(int row, int col) {
		Ambulance ambulance = _data.get(row);
		switch (col) {
		case 0:
			return ambulance.getID();
		case 1:
			return ambulance.getLocation();
		case 2:
			return ambulance.getStatus();
		case 3:
			return ambulance.getPatient();
		}
		return null;
	}

	public void parseAndAdd(String line) {
		Ambulance newAmbulance = new Ambulance(line);
		_data.add(newAmbulance);
	}
	
	public void add(Ambulance ambulance) {
		_data.add(ambulance);
	}

	public List<String> save() {
		ArrayList<String> lines = new ArrayList<String>();
		lines.add("\"id\",\"x.location\",\"y.location\",\"status\",\"patient\"");
		for (Ambulance ambulance: _data) {
			lines.add(ambulance.save());
		}
		return lines;
	}
}
