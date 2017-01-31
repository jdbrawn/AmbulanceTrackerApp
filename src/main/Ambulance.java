package main;

public class Ambulance {
	private String _id;
	private int _x;
	private int _y;
	private String _status;
	private int _patient;
	private Boolean _isNew = false;
	private int time;
	
	public Ambulance(String line) {
		String[] parts = line.split(",");
		_id = parts[0].substring(1, parts[0].length() - 1);
		_x = Integer.parseInt(parts[1]);
		_y = Integer.parseInt(parts[2]);
		_status = parts[3].substring(1, parts[3].length() - 1);
		if ((parts.length > 4) && (parts[4] != "")) {
			_patient = Integer.parseInt(parts[4]);
		}
		time = 0;
	}

	public Ambulance() {
		_isNew = true;
		time = 0;
	}

	public int getTime() {
		return time;
	}
	
	public String getID() {
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

	public Boolean getIsNew() {
		return _isNew;
	}

	public String getPatient() {
		if (_patient > 0) {
			return Integer.toString(_patient);
		} 
		return "";
	}

	public String save() {
		_isNew = false;
		return "\"" + _id + "\"," +
				Integer.toString(_x) + "," + 
				Integer.toString(_y) + ",\"" + 
				_status + "\"," +
				(_patient == 0 ? "" : Integer.toString(_patient));
	}
	
	public void setTime(int time) {
		this.time = time;
	}
	
	public void setID(String value) {
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

	public void setPatient(int value) {
		_patient = value;		
	}
}
