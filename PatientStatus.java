package ver1;

/*
 * In the original project, this was written by Chase Vaughn
 */

public enum PatientStatus {
    WAITING("Waiting"),
    READY("Ready"),
    ON_HOLD("On Hold"),
    IN_PROGRESS("In Progress"),
    CHECKED_OUT("Checked Out");

    private String toString;

    PatientStatus(String toString) {
        this.toString = toString;
    }

    public String toString() {
        return toString;
    }
}
