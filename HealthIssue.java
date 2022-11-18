package ver1;

// Written by jlmerritt
public class HealthIssue {
	private String name;
	private String location = null;
	private int painSeverity;
	
	public HealthIssue(String name, int painSeverity) {
		this.name = name;
		this.painSeverity = painSeverity;
	}
	
	public HealthIssue(String name, int painSeverity, String location) {
		this(name, painSeverity);
		this.location = location;
	}
	
	public String getName() {
		return name;
	}
	
	public String getLocation() {
		if(location != null) {
			return location;
		}
		return "Not specified";
	}
	
	public int getPainSeverity() {
		return painSeverity;
	}
	
	@Override
	public String toString() {
		return String.format("Symptom: %s, Location: %s, Pain Severity: %d", name, getLocation(), painSeverity);
	}

}
