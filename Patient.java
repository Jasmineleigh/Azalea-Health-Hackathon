package ver1;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * In the original project, this was written by Lucas Keasbey
 * and Chase Vaughn Collaboratively
 */

public class Patient implements Comparable<Patient> {
	// automatically assigned upon creation
    protected int id;
    
    // variables given by patient (added by jlmerritt)
    protected String firstName = "Jane/ John";
    protected String lastName = "Doe";
    protected String address, city, state;
    protected String phone;
    protected int age;
    protected LocalDate birthdate;
    protected String sex, maritalStatus, race;
    protected String SSN;
    protected EmergencyContact emergContact;
    
    
    // variables set by doctor or nurse (added by jlmerritt)
    protected double height = -1;
    protected double weight = -1;
    protected int heartRate = -1;
    protected int systolicNumber = -1;
    protected int diastolicNumber = -1;
    
    // variables provided by either patient, nurse or doctor (added by jlmerritt)
    protected ArrayList<HealthIssue> healthIssues = new ArrayList<>(); 
    
    // set by nurse or doctor
    protected PatientStatus status;
    protected List<String> notes;
    
    // check for if biographical info is entered (added by jlmerritt)
    protected boolean hasBiographical = false;

    public Patient(int id) {
        this.id = id;
        this.status = PatientStatus.WAITING;
        notes = new ArrayList<>();
    }
    
    public Patient(int id, String firstName, String lastName, 
    		String address, String city, String state, int age, 
    		LocalDate birthdate, String SSN, String sex) {
    	
    	this(id);
    	this.firstName = firstName;
    	this.lastName = lastName;
    	this.address = address;
    	this.city = city;
    	this.state = state;
    	this.age = age;
    	this.birthdate = birthdate;
    	this.SSN = SSN;
    	this.sex = sex;
    	
//    	this.race = race;
//    	hasBiographical = true;
    }

    public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public EmergencyContact getEmergContact() {
		return emergContact;
	}

	public void setEmergContact(EmergencyContact emergContact) {
		this.emergContact = emergContact;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public int getHeartRate() {
		return heartRate;
	}

	public void setHeartRate(int heartRate) {
		this.heartRate = heartRate;
	}

	public int getSystolicNumber() {
		return systolicNumber;
	}

	public void setSystolicNumber(int systolicNumber) {
		this.systolicNumber = systolicNumber;
	}

	public int getDiastolicNumber() {
		return diastolicNumber;
	}

	public void setDiastolicNumber(int diastolicNumber) {
		this.diastolicNumber = diastolicNumber;
	}

	public ArrayList<HealthIssue> getHealthIssues() {
		return healthIssues;
	}

	public void setHealthIssues(ArrayList<HealthIssue> healthIssues) {
		this.healthIssues = healthIssues;
	}

	public String getRace() {
		return race;
	}

	public int getId() {
        return id;
    }

    public PatientStatus getStatus() {
        return status;
    }

    public int compareTo(Patient p) {
        if(status == p.status) {
            return this.id - p.id;
        }
        return status.ordinal() - p.status.ordinal();
    }

    public void setStatus(PatientStatus status) {
        this.status = status;
    }
    
    public String getSSN() {
		return SSN;
	}

	public void setSSN(String sSN) {
		SSN = sSN;
	}

	public List<String> getNotes() {
		return notes;
	}
	
	public void addNote(String note) {
		notes.add(note);
	}
	
	public String getBloodPressure() {
    	return systolicNumber + "/" + diastolicNumber;
    }
    
    public String getPatientData() {
    	String data = "";
    	
    	if(!firstName.equals("Jane/ John")) {
    		data += String.format("Name: %s %s, ID: %d\nAge: %d, Birthdate: %s, Race: %s"
    				+ "\nAddress: %s\n", firstName, lastName, id, age, birthdate.toString(), race, address);
    		
    		data += String.format("-------------------------------------------\n"
    								+ "Height: %.1f, Weight: %.1f, BPM: %d\n"
    								+ "Blood Pressure: %d/ %d\n"
    								+ "-------------------------------------------\n", 
    								height, weight, heartRate, systolicNumber, diastolicNumber);
    		return data;
    	}
    		return "ID: " + id + " (No further data)";
    }

    @Override
    public String toString() {
        return "Name: " + firstName + " " + lastName + ", ID: " + id + " (" + status + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return id == ((Patient) obj).id;
    }

}
