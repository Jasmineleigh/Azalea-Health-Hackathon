package ver1;

import java.time.LocalDate;
import java.util.List;

public class HospitalTest {

    public void test1() {
        Hospital h = makeHospital();
        System.out.println(h);
    }

    public Hospital makeHospital() {
        Hospital h = new Hospital();
        h.addRoom(1);
        h.addRoom(2);
        h.addRoom(3);
        return h;
    }
    
    public static void main(String[]args) {
    	Hospital h = new Hospital();
        h.addRoom(1);
        h.addRoom(2);
        h.addRoom(3);
        h.addPatient();
        h.addPatient();
        
        for(Patient p: h.getPatients()) {
        	if(p.getId()==101) {
        		p.setFirstName("Jasmine");
        		p.setLastName("Merritt");
        		p.setAge(21);
        		p.setAddress("1213 South Highway 129");
        		p.setBirthdate(LocalDate.parse("2001-07-04"));
        	}
        }
        
        List<Patient> found = h.getPatientsWithTheseLetters("Ja");
        System.out.println("Hi");
        
        for(Patient p: found) {
        	System.out.println(p);
        }
    }

}
