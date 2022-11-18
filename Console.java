package ver1;

import java.io.InputStream;
import java.util.Scanner;

/*
 * In the original project, this was written by Chase Vaughn
 */

public class Console {
    private Hospital hospital = new Hospital();
    private InputStream in;
    private Scanner scanner;

    public Console(InputStream in) {
        this.in = in;
    }

    public void start() {
        scanner = new Scanner(in);
        System.out.print("> ");
        while(scanner.hasNextLine()) {
            parseCommand(scanner.nextLine());
            System.out.print("\n> ");
        }
        stop();
    }

    public void stop() {
        scanner.close();
        System.exit(0);
    }

    private void parseCommand(String input) {
        String[] split = input.split(" ");
        if(split.length == 0) return;
        String cmd = split[0];
        String[] args = new String[split.length - 1];
        if(args.length > 0) {
            System.arraycopy(split, 1, args, 0, args.length);
        }

        switch(cmd.toLowerCase()) {
        case "exit" -> {
            stop();
        }
        case "addpatient" -> {
            hospital.addPatient();
        }
        case "addroom" -> {
            if(args.length == 0) {
                System.out.println("Missing argument: <room number>");
                return;
            }
            int number = -1;
            try {
                number = Integer.valueOf(args[0]);
            } catch(NumberFormatException e) {
                System.out.println("Invalid number: " + args[0]);
                return;
            }
            hospital.addRoom(number);
        }
        case "getpatients" -> {
            System.out.println(hospital.getPatients());
        }
        case "getwaiting" -> {
            System.out.println(hospital.getWaiting());
        }
        case "getready" -> {
            System.out.println(hospital.getReady());
        }
        case "setstatus" -> {
            if(args.length == 0) {
                System.out.println("Missing argument: <patient ID>");
                return;
            } else if(args.length == 1) {
                System.out.println("Missing argument: <status>");
                return;
            }
            int id = -1;
            try {
                id = Integer.valueOf(args[0]);
            } catch(NumberFormatException e) {
                System.out.println("Invalid ID: " + args[0]);
                return;
            }
            Patient toChange = null;
            for(Patient p : hospital.patients) {
                if(p.id == id) {
                    toChange = p;
                    break;
                }
            }
            if(toChange == null) {
                System.out.println("Patient not found: " + id);
                return;
            }
            PatientStatus newStatus = null;
            for(PatientStatus status : PatientStatus.values()) {
                if(status.name().equalsIgnoreCase(args[1])) {
                    newStatus = status;
                    break;
                }
            }
            if(newStatus == null) {
                System.out.println("Status must be one of the following:");
                for(PatientStatus status : PatientStatus.values()) {
                    System.out.println("- " + status.name().toLowerCase());
                }
                return;
            }
            toChange.status = newStatus;
        }
        case "reorder" -> {
            if(args.length < 2) {
                System.out.println("Command 'reorder' requires two arguments: <patient> <up/down>");
                return;
            }
            int id = -1;
            try {
                id = Integer.valueOf(args[0]);
            } catch(NumberFormatException e) {
                System.out.println("Invalid ID: " + args[0]);
                return;
            }
            Patient patient = null;
            int i = 0;
            for(Patient p : hospital.patients) {
                if(p.id == id) {
                    patient = p;
                    break;
                }
                i++;
            }
            if(patient == null) {
                System.out.println("Patient not found: " + id);
                return;
            }
            if(!(args[1].equalsIgnoreCase("up") || args[1].equalsIgnoreCase("down"))) {
                System.out.println("Argument 2 must be 'up' or 'down'");
                return;
            }
            if(args[1].equalsIgnoreCase("up")) {
                if(i == 0) {
                    System.out.println("Patient " + id + " is already at the start of the queue");
                    return;
                }
                hospital.patients.set(i, hospital.patients.get(i - 1));
                hospital.patients.set(i - 1, patient);
            } else {
                if(i == hospital.patients.size() - 1) {
                    System.out.println("Patient " + id + " is already at the end of the queue");
                    return;
                }
                hospital.patients.set(i, hospital.patients.get(i + 1));
                hospital.patients.set(i + 1, patient);
            }
        }
        default -> {
            System.out.println("Unknown command: " + cmd);
        }
        }
    }
}
