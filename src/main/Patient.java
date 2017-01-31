package main;

public class Patient {
	private int _id;
	private int _x;
	private int _y;
	private String _status;
	private String _ambulance;
	private Boolean _isNew = false;
	
	public Patient(String line) {
		String[] parts = line.split(",");
		_id = Integer.parseInt(parts[0]);
		_x = Integer.parseInt(parts[1]);
		_y = Integer.parseInt(parts[2]);
		_status = parts[3].substring(1, parts[3].length() - 1);
		if ((parts.length > 4) && (parts[4] != "")) {
			_ambulance = parts[4].substring(1,  parts[4].length() - 1);
		}
	}
	
	public Patient() {
		_isNew = true;
	}

	public int getID() {
		return _id;
	}
	
	public int getX() {
		return _x;
	}
	
	public int getY() {
		return _y;
	}
	
	public String getLocation() {
		return "(" + Integer.toString(_x) + "," + Integer.toString(_y) + ")";
	}
	
	public String getStatus() {
		return _status;
	}
	
	public String getAmbulance() {
		return _ambulance;
	}
	
	public Boolean getIsNew() {
		return _isNew;
	}

	public String save() {
		_isNew = false;
		return Integer.toString(_id) + "," +
				Integer.toString(_x) + "," + 
				Integer.toString(_y) + ",\"" + 
				_status + "\"," +
				((_ambulance == null) || (_ambulance == "") ? "" : "\"" + _ambulance + "\"");
	}

	public void setID(int value) {
		_id = value;		
	}

	public void setX(int value) {
		_x = value;		
	}

	public void setY(int value) {
		_y = value;		
	}

	public void setStatus(String value) {
		_status = value;		
	}

	public void setAmbulance(String value) {
		_ambulance = value;		
	}
}
