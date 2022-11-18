package ver1;

/*
 * In the original project, this was written by Chase Vaugn
 * and Lucas Keasbey collaboratively
 */

public class Room implements Comparable<Room> {
    private int roomNumber;
    private Patient p = null;

    public Room(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public boolean addPatient(Patient readyPatient) {
        if(isFree()) {
            p = readyPatient;
            return true;
        }
        return false;
    }

    public Patient removePatient() {
        if(isFree()) return null;
        Patient pat = this.getPatient();
        pat.setStatus(PatientStatus.CHECKED_OUT);
        p = null;
        return pat;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public Patient getPatient() {
        return p;
    }

    public boolean isFree() {
        return p == null;
    }

    public String getRoomStatus() {
        if(isFree())
            return "Room is free";
        else {
            return String.format("Room is not free; %s", p.toString());
        }
    }

    public int compareTo(Room r) {
        if(!(isFree() && r.isFree())) {
            return this.getPatient().compareTo(r.getPatient());
        }
        return roomNumber - r.roomNumber;

    }

    @Override
    public String toString() {
        if(isFree())
            return "Room " + roomNumber + " is free.";
        else {
            return "Room " + roomNumber + " has " + p.toString();
        }
    }

}
