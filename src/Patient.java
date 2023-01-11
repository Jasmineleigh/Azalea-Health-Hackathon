package ver1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/*
 * In the original project, this was written by Lucas Keasbey
 * and Chase Vaughn Collaboratively
 */

public class Patient implements Comparable<Patient>{
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
    
    protected Text txtStatus;
    
    protected Button btnClose = new Button("Close");

    public Patient(int id) {
        this.id = id;
        this.status = PatientStatus.WAITING;
        txtStatus = new Text(status + " ");
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
	public Text getTxtStatus() {
		return txtStatus;
	}
	public void setTxtStatus(String status) {
		txtStatus.setText(status);
	}
	public void buildPatientDataDisplay() throws FileNotFoundException {
		
		try {
			Stage primaryStage = new Stage();
		    GridPane grdGUI = buildGUI();
		    grdGUI.add(btnClose, 0, 1);
			Pane grdRootPane = grdGUI;
		    Scene scene = new Scene(grdRootPane, 1600, 810);
			scene.getStylesheets().add(getClass().getResource("room_data_stylesheet.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Room");
			primaryStage.show();
			
			btnClose.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					primaryStage.close();
				}
				
			});
			
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	public GridPane buildGUI() throws FileNotFoundException {
		GridPane root = new GridPane();
		GridPane col1 = new GridPane();
		GridPane col2 = new GridPane();
		
    	TextArea txaNotes = new TextArea();
    	
    	Button btnAddNote = new Button("Add Note");
		
		VBox vBoxLogo = new VBox();
		InputStream stream = new FileInputStream("src\\ver1\\azalea_health_smaller.png");
		Image imgAH = new Image(stream);
		ImageView imgvwLogo = new ImageView();
		
		vBoxLogo.getStyleClass().add("-fx-padding:15px;");
		vBoxLogo.setAlignment(Pos.CENTER_RIGHT);
		imgvwLogo.setImage(imgAH);
		imgvwLogo.setFitHeight(vBoxLogo.getWidth());
		vBoxLogo.getChildren().addAll(imgvwLogo);
		
		// needs to be able to set status
		
		Label lblStatus = new Label("STATUS: ");
		Label lblName = new Label("Name: ");
		Label lblAge = new Label("Age: ");
		Label lblDOB = new Label("DOB: ");
		Label lblSex = new Label("Sex:");
		Label lblMarital = new Label("Marital Status:");
		Label lblAddress = new Label("Address: ");
		Label lblHeight = new Label("Height: ");
		Label lblWeight = new Label("Weight: ");		
		Label lblBPM = new Label("BPM: ");
		Label lblBloodPressure = new Label("Blood Pressure: ");
		Label lblState = new Label("State: ");
		Label lblCity = new Label("City: ");
		Label lblNotes = new Label("Notes: ");
		
		txaNotes.setEditable(true);
		txaNotes.setPrefHeight(250);
		txaNotes.setPrefWidth(450);
		
		
		// Hold data
		HBox hBoxLine1 = new HBox();
		hBoxLine1.setSpacing(3);
		
		HBox hBoxLine2 = new HBox();
		hBoxLine2.setSpacing(3);
		
		HBox hBoxLine3 = new HBox();
		hBoxLine3.setSpacing(3);

		
		HBox hBoxLine4 = new HBox();
		hBoxLine4.setSpacing(3);
		
		HBox hBoxLine5 = new HBox();
		hBoxLine5.setSpacing(3);	
		
		VBox vBoxLine6 = new VBox();
		vBoxLine6.setSpacing(10);
		
		btnAddNote = new Button("Add Note");
		btnAddNote.setAlignment(Pos.CENTER_RIGHT);
		

		// column 1 contents
		if(sex.equals("Female")) {
			VBox vBoxBody = new VBox();
			InputStream str = new FileInputStream("src\\ver1\\f.jpg");
			Image imgFemale = new Image(str);
			ImageView imgvwBody = new ImageView();
			
			imgvwBody.setImage(imgFemale);
			vBoxBody.getStyleClass().add("vbox");
			vBoxBody.getChildren().add(imgvwBody);
			
			col1.getChildren().add(vBoxBody);
		}else if(sex.equals("Male")) {
			VBox vBoxBody = new VBox();
			InputStream str = new FileInputStream("src\\ver1\\m.png");
			Image imgMale = new Image(str);
			ImageView imgvwBody = new ImageView();
			
			imgvwBody.setImage(imgMale);
			vBoxBody.getStyleClass().add("vbox");
			vBoxBody.getChildren().add(imgvwBody);
			vBoxBody.setAlignment(Pos.CENTER);
			col1.getChildren().add(vBoxBody);
		}
		
		
		txtStatus.setText(txtStatus.getText().toUpperCase());
		txtStatus.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 14));
		
		Text txtName = new Text(firstName + " " + lastName + "  ");
		txtName.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 14));
		
		
		Text txtAge = new Text(age + "  ");
		txtAge.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 14));
		
		Text txtDOB = new Text(birthdate.getMonthValue() + "/ "
							+ birthdate.getDayOfMonth() + "/ "
							+birthdate.getYear() + "  ");
		txtDOB.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 14));
		
		Text txtSex = new Text(sex + "  ");
		txtSex.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 14));
		
		Text txtmarital = new Text(maritalStatus + "  ");
		txtmarital.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 14));
		
		Text txtAddress = new Text(address + "  ");
		txtAddress.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 14));
		
		Text txtState = new Text(state + "  ");
		txtState.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 14));
		
		Text txtCity = new Text(city + "  ");
		txtCity.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 14));
		
		Text txtHeight = new Text(height + "  ");
		txtHeight.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 14));
		
		Text txtWeight = new Text(weight + "  ");
		txtWeight.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 14));
		
		Text txtBPM = new Text(heartRate+"  ");
		txtBPM.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 14));
		
		Text txtBloodPressure = new Text(getBloodPressure() + "  ");
		txtBloodPressure.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 14));
		
		hBoxLine1.getChildren().addAll(lblStatus, txtStatus);
		hBoxLine2.getChildren().addAll(lblName, txtName, lblAge, txtAge, lblDOB, txtDOB, lblSex, txtSex);
		hBoxLine3.getChildren().addAll(lblMarital, txtmarital);
		hBoxLine4.getChildren().addAll(lblAddress, txtAddress, lblCity, txtCity, lblState, txtState);
		hBoxLine5.getChildren().addAll(lblHeight, txtHeight, lblWeight, txtWeight, lblBPM, txtBPM, lblBloodPressure, txtBloodPressure);
		vBoxLine6.getChildren().addAll(lblNotes, txaNotes, btnAddNote);

		col2.setVgap(20);
		col2.add(hBoxLine1, 0, 0);
		col2.add(hBoxLine2, 0, 1);
		col2.add(hBoxLine3, 0, 2);
		col2.add(hBoxLine4, 0, 3);
		col2.add(hBoxLine5, 0, 4);
		col2.add(vBoxLine6, 0, 5);
	
		root.setHgap(20);
		root.add(col1, 0, 0);
		root.add(col2, 1, 0);
		root.add(vBoxLogo, 2, 0);
		return root;

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
