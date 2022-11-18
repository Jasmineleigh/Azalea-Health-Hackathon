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

    public List<Room> getRooms() {
        return rooms;
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
    	patients.remove(i);
    	patients.add(p);
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
