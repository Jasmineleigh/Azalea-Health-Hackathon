package ver1;

public class EmergencyContact {
	private String name, phone, relationship;
	
	public EmergencyContact(String name, String phone, String relationship) {
		this.name = name;
		this.phone = phone;
		this.relationship = relationship;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPhone() {
		return String.format("(%s)%s-%s", phone.substring(0, 3),phone.substring(3, 6), phone.substring(6,10));
	}
	@Override
	public String toString() {
		return String.format("Name: %s, Phone: %s, Relationship: %s", name, getPhone(), relationship);
	}

}
