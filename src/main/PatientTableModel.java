package main;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public final class PatientTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -8592336616162732686L;
	private String[] _columnNames = {"ID",
			"Location",
			"Status",
			"Ambulance"};
	private ArrayList<Patient> _data = new ArrayList<Patient>();

	public int getColumnCount() {
		return _columnNames.length;
	}
	
	public String getColumnName(int col) {
		return _columnNames[col];
	}

	public int getRowCount() {
		return _data.size();
	}
	
	public Patient get(int row) {
		return _data.get(row);
	}

	public Patient getByID(int id) {
		for (Patient patient : _data) {
			if (patient.getID() == id) {
				return patient;
			}
		}
		return null;
	}
	
	public int getMaxID() {
		int id = 0;
		for (Patient patient : _data) {
			int thisID = patient.getID();
			if (thisID > id) {
				id = thisID;
			}
		}
		return id;
	}

	public List<Patient> data() {
		return _data;
	}

	public Object getValueAt(int row, int col) {
		Patient patient = _data.get(row);
		switch (col) {
		case 0:
			return patient.getID();
		case 1:
			return patient.getLocation();
		case 2:
			return patient.getStatus();
		case 3:
			return patient.getAmbulance();
		}
		return null;
	}

	public void parseAndAdd(String line) {
		Patient newPatient = new Patient(line);
		_data.add(newPatient);
	}
	
	public void add(Patient patient) {
		_data.add(patient);
	}

	public List<String> save() {
		ArrayList<String> lines = new ArrayList<String>();
		lines.add("\"id\",\"x.location\",\"y.location\",\"status\",\"ambulance\"");
		for (Patient patient : _data) {
			lines.add(patient.save());
		}
		return lines;
	}
}
