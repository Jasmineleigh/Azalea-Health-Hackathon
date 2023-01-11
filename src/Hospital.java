package ver1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
 * In the original project, this was written by Lucas Keasbey
 * and Chase Vaughn Collaboratively
 */

public class Hospital {
    protected List<Room> rooms;
    protected List<Patient> patients;
    private int nextID = 100;

    public Hospital() {
        this.patients = new ArrayList<>();
        this.rooms = new ArrayList<>();
    }

    public Hospital(Collection<Room> rooms) {
        this();
        this.rooms.addAll(rooms);
    }

    public boolean addRoom(int rn) {
        Room room = new Room(rn);
        if(rooms.contains(room)) return false;
        rooms.add(room);
        return true;
    }
    
    public List<Patient> getPatientsWithTheseLetters(String search){
    	List<Patient> foundPatients = new ArrayList<>();
    	int size = search.length();
    	if(isInteger(search)) {
    		return getPatientWithId(Integer.parseInt(search));
    	}else {
    		for(Patient p:patients) {
    			String subPName = p.getFirstName().substring(0,size);
    			if(subPName.equals(search)) {
    				foundPatients.add(p);
    			}
    		}
    	}
    	return foundPatients;
    }

    public List<Patient> getPatientWithId(int search) {
    	List<Patient> foundPatients = new ArrayList<>();
    	
		for(int i = 0; i < patients.size();i++) {
			if(patients.get(i).getId() == search) {
				foundPatients.add(patients.get(i));
				break;
			}
		}
		return foundPatients;
	}

	private boolean isInteger(String search) {
		try {
			int num = Integer.parseInt(search);
			return true;
		}catch(Exception e) {
			System.out.println("Not an int");
		}
		return false;
	}

	public List<Room> getRooms() {
        return rooms;
    }
	
	public Room getRoom(int number) {
		for(Room r:rooms) {
			if(r.getRoomNumber()== number) {
				return r;
			}
		}
		return null;
	}
	
	public Room getRoom(Room r) {
		return getRoom(r.getRoomNumber());
	}
	

    public List<Room> getFreeRooms() {
        List<Room> free = new ArrayList<>();
        for(Room r : rooms) {
            if(r.isFree()) free.add(r);
        }
        return free;
    }

    public List<Patient> getPatients() {
        return patients;
    }
    
    public Patient getPatient(int id) {
    	for(Patient p: patients) {
    		if(id == p.getId()) {
    			return p;
    		}
    	}
    	
    	return null;
    }
    
    public Patient getPatient(Patient patient) {
    	return getPatient(patient.getId());
    }

    public List<Patient> getcheckedOutPatients(){
    	List<Patient> checkedOutPatients = new ArrayList<>();
    	for(Patient p: patients) {
    		if(p.getStatus() == PatientStatus.CHECKED_OUT) {
    			checkedOutPatients.add(p);
    		}
    	}
    	return checkedOutPatients;
    }
    
    public int addPatient() {
        Patient p = new Patient(nextID++);
        patients.add(p);
        return p.getId();
    }
    
    public Patient addPatient(Patient p) {
    	int i = 0;
    	for(Patient patient: patients) {
    		if(patient.getId()==p.getId()) {
    			break;
    		}
    		i++;
    	}
    	
    	patients.set(i, p);
    	
    	return p;
    }

    public List<Patient> getWaiting() {
        List<Patient> waiting = new ArrayList<>();
        for(Patient p : patients) {
            if(p.getStatus() == PatientStatus.WAITING) waiting.add(p);
        }
        return waiting;
    }

    public List<Patient> getReady() {
        List<Patient> ready = new ArrayList<>();
        for(Patient p : patients) {
            if(p.getStatus() == PatientStatus.READY) ready.add(p);
        }
        return ready;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Hospital");
        for(Room r : rooms) {
            sb.append("\n");
            sb.append(r);
        }
        return sb.toString();
    }

}
