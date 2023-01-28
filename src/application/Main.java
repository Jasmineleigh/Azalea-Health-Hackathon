package ver1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
	// buttons for check-in and wait-list
    protected Button btnNewPatient, btnCheckIn, btnMoveUp, btnMoveDown;
    protected Button btnAddRoom, btnPrepPatient, btnMoreData, btnData;
    protected TextArea txaPatientData;
    
    // search bar variables
    protected TextField txfSearchPatient;
    protected Button btnSearch, btnFilter;
    protected int numSearchBarClicks = 0;
    protected int numNewPatients = 0;
    
    // data entered by patient
    protected TextField txfFirstName, txfLastName, txfAge, txfBirthdate, txfAddress,
    					txfCity, txfState, txfPatientPhone, txfSSN,
    					txfEmergRelationship, txfEmergName, txfEmergPhone;
    
    protected ToggleGroup tgrpSex, tgrpMarital, tgrpRace, tgrpIllnesses;
    protected Label lblFirstName, lblLastName, lblAge, lblBirthdate, lblAddress;
    protected Button btnSubmitNew, btnCloseNew, btnSubmitPrep, btnClosePrep, btnCloseEmptyRoom, btnCloseRoom;
    
    // data entered by nurse upon preparation
    protected TextField txfHeight, txfWeight, txfBPM, txfSystolicNum, txfDiastolicNum;
    protected Label lblHeight, lblWeight, lblBPM, lblSystolicNum, lblDiastolicNum;
    
    protected Text txtStatus;
    protected TextArea txaNotes = new TextArea();
    // data for queue management
    protected Label lblWaitList, lblFreeRooms, lblCheckedOut;
    protected ListView<Patient> lvwWaitingPatients = new ListView<>();
    protected ListView<Patient> lvwPatients = new ListView<>();
    protected Hospital azaleaHealth = createHospital();
    
    // update
    protected Button btnRoom1, btnRoom2, btnRoom3, btnRoom4, btnRoom5, btnRoom6;
	protected Button btnAddNote, btnHold, btnCheckout, btnInProgress;
	protected List<Button> roomButtons = new ArrayList<>();
    
	protected RadioButton rdFemale = new RadioButton("Female");
	protected RadioButton rdMale = new RadioButton("Male");
	protected RadioButton rdPreferNot = new RadioButton("Prefer Not To Say");
	protected String sex = null;

	protected RadioButton rdWidow = new RadioButton("Widow");
	protected RadioButton rdDivorced = new RadioButton("Divorced");
	protected RadioButton rdMarried = new RadioButton("Married");
	protected RadioButton rdSingle = new RadioButton("Single");
	protected String marital = null;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            Pane root = buildGUI();
            Scene scene = new Scene(root, 1600, 750);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private Pane buildGUI() {
    	lblCheckedOut = new Label("Checked-Out");
    	lblWaitList = new Label("Wait-List");
    	lblFreeRooms = new Label("Available Rooms");
    	
    	
    	GridPane fullPane = new GridPane();
    	fullPane.setVgap(25);
    	fullPane.setHgap(25);
    	fullPane.setPadding(new Insets(10, 10, 60, 60));
   
    	
    	GridPane firstCol = new GridPane();
    	firstCol.setPadding(new Insets(10, 10, 10, 10));
    	firstCol.setVgap(20);
    	firstCol.setHgap(30);
    	
    	firstCol.add(lblWaitList, 0, 0);
    	firstCol.add(buildListOfWaitingPatients(), 0, 1);
    	firstCol.add(buildRoomButtonsDisplay(), 1, 1);
    	
    	
     	GridPane secondCol = new GridPane();
     	secondCol.setVgap(20);
     	secondCol.setHgap(30);
     	secondCol.setPadding(new Insets(10, 10, 10, 10));
     	
    	secondCol.add(lblCheckedOut, 0, 0);
    	secondCol.add(buildSearchBar(), 0, 1);
    	
    	fullPane.add(firstCol, 0, 0);
    	fullPane.add(secondCol, 1, 0);

        VBox root = new VBox();
        root.getStyleClass().add("root");
        root.getChildren().addAll(fullPane);
        return root;
    }
    
   
    // search for patient
    private VBox buildSearchBar() {
        lvwPatients.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lvwPatients.setPrefHeight(600);
        lvwPatients.setPrefWidth(300);
   
        lvwPatients.setOnMouseClicked(new EventHandler<MouseEvent>() {
        	@Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    if(mouseEvent.getClickCount() == 2){
                    	//add a separate window to display data 
                    	try {
							lvwPatients.getSelectionModel().getSelectedItem().buildPatientDataDisplay();
							if(lvwPatients.getSelectionModel().getSelectedItem().getStatus() == PatientStatus.CHECKED_OUT)
								showCheckInScreen(lvwPatients.getSelectionModel().getSelectedItem());
                    	} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                        System.out.print(lvwPatients.getSelectionModel().getSelectedItem().getPatientData());
                    }
                }
            }
        });
        
        HBox hBoxSearch = new HBox();
        hBoxSearch.setSpacing(3);
        
        HBox hBoxSearchButtons = new HBox();
        
        txfSearchPatient = new TextField("i.e. jane doe");
        txfSearchPatient.setPrefHeight(28);
        txfSearchPatient.setPrefWidth(250);
        
        txfSearchPatient.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
			
	        	numSearchBarClicks = 0;
	        	txfSearchPatient.setText("i.e. jane doe");
			
			}
        	
        });
        
        txfSearchPatient.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				if(numSearchBarClicks == 0) {
					txfSearchPatient.clear();
					numSearchBarClicks++;
				}

			}
        	
        });
        
        txfSearchPatient.setOnKeyTyped(new EventHandler<KeyEvent> () {

			@Override
			public void handle(KeyEvent arg0) {
				// TODO Auto-generated method stub
				lvwPatients.getItems();
				String search = txfSearchPatient.getText();
				List<Patient> found = azaleaHealth.getPatientsWithTheseLetters(search);
				
				lvwPatients.getItems().clear();
				
				for(Patient p:found) {
					lvwPatients.getItems().add(p);
				}
			}
        	
        });

        
        btnSearch = new Button("   ");
        btnSearch.getStyleClass().add("search_button");
        
        btnFilter = new Button("   ");
        btnFilter.getStyleClass().add("filter_button");
        
        hBoxSearchButtons.getChildren().addAll(btnFilter, btnSearch);
        
        hBoxSearch.getChildren().addAll(txfSearchPatient, hBoxSearchButtons);
       

        VBox display = new VBox();
        display.getStyleClass().add("list");
        display.getChildren().addAll(hBoxSearch,lvwPatients);

        return display;
    }

    // Waiting patients display
    private VBox buildListOfWaitingPatients() {
        lvwWaitingPatients.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        lvwWaitingPatients.setPrefHeight(300);
        lvwWaitingPatients.setPrefWidth(300);

        List<Patient> waiting = azaleaHealth.getPatients();
        System.out.println(waiting);
        for(Patient p : waiting) {
            if(p.getStatus() == PatientStatus.WAITING) lvwWaitingPatients.getItems().add(p);
        }
        VBox vBoxContainer = new VBox();
        HBox display = new HBox();
        display.getStyleClass().add("list");
        display.getChildren().addAll(buildWaitlistButtons(), lvwWaitingPatients);
        
        vBoxContainer.getChildren().add(display);

        return vBoxContainer;
    }

    private Pane buildWaitlistButtons() {
        VBox buttonsBox = new VBox();
        buttonsBox.getStyleClass().add("buttons_box");

        btnNewPatient = new Button("New Patient");
        btnNewPatient.setOnAction(new CreateNewPatientEventHandler());
        
        btnCheckIn = new Button("Check-In");
        btnCheckIn.setOnAction(new EventHandler<ActionEvent> () {
    		@Override
    		public void handle(ActionEvent event) {
    			int fromCheckout = lvwPatients.getSelectionModel().getSelectedIndex();
    			Patient toCheckIn = lvwPatients.getSelectionModel().getSelectedItem();
    			lvwPatients.getItems().remove(fromCheckout);
    			toCheckIn.setStatus(PatientStatus.WAITING);
    			lvwWaitingPatients.getItems().add(toCheckIn);
    		}
    	});

        btnMoveUp = new Button("Move Up");
        btnMoveUp.setOnAction(new CreateMoveUpEventHandler());

        btnMoveDown = new Button("MoveDown");
        btnMoveDown.setOnAction(new CreateMoveDownEventHandler());
        
        btnPrepPatient = new Button("Prepare");
        btnPrepPatient.setOnAction(new CreatePrepareEventHandler());

        buttonsBox.getChildren().addAll(btnNewPatient, btnCheckIn, btnMoveUp, btnMoveDown, btnPrepPatient);

        return buttonsBox;
    }

    /************************************
     * new patient event handler methods*
     ************************************/
    public class CreateNewPatientEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent arg0) {
            
        	showApplicationScreen();

        }

    }
    
    public void showApplicationScreen() {
    	
		try {
			Stage primaryStage = new Stage();
		    btnCloseNew = new Button("Close");
		    btnSubmitNew = new Button("Submit");
		    Pane grdRootPane = buildApplicationDisplay();
		   
		    btnSubmitNew.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	if(primaryDataMissing()) {
	            		showMissingDataScreen();
	            	}else {
		            	int patientID = azaleaHealth.addPatient();
	
		            	azaleaHealth.getPatient(patientID).setFirstName(txfFirstName.getText());
		            	azaleaHealth.getPatient(patientID).setLastName(txfLastName.getText());
		            	azaleaHealth.getPatient(patientID).setAddress(txfAddress.getText());
		            	azaleaHealth.getPatient(patientID).setCity(txfCity.getText());
		            	azaleaHealth.getPatient(patientID).setState(txfState.getText());
		            	azaleaHealth.getPatient(patientID).setAge(Integer.parseInt(txfAge.getText()));
		            	
		            	String month = txfBirthdate.getText().substring(0,2);
		            	String day = txfBirthdate.getText().substring(3,5);
		            	String year = txfBirthdate.getText().substring(6);
		            	
		            	azaleaHealth.getPatient(patientID).setBirthdate(LocalDate.parse(year+"-"+month+"-"+day));
		            	azaleaHealth.getPatient(patientID).setPhone(txfPatientPhone.getText());
		            	azaleaHealth.getPatient(patientID).setSex(sex);
		            	azaleaHealth.getPatient(patientID).setMaritalStatus(marital);
		            	azaleaHealth.getPatient(patientID).setSSN(txfSSN.getText());
		            	
		            	rdFemale.setSelected(false);
		            	rdMale.setSelected(false);
		            	rdPreferNot.setSelected(false);
		            	rdSingle.setSelected(false);
		            	rdMarried.setSelected(false);
		            	rdDivorced.setSelected(false);
		            	rdWidow.setSelected(false);
		            	
		            	if(!txfEmergName.getText().isEmpty() && 
		            		!txfEmergPhone.getText().isEmpty() && 
		            		!txfEmergRelationship.getText().isEmpty()) {
		            		
		            		EmergencyContact eC = new EmergencyContact(txfEmergName.getText(), 
		            													txfEmergPhone.getText(),
		            													txfEmergRelationship.getText());
		            		azaleaHealth.getPatient(patientID).setEmergContact(eC);
		            		
		            	}
		            	 
		            	
		                lvwWaitingPatients.getItems().add(azaleaHealth.getPatient(patientID));
		                primaryStage.close();
		                System.out.println(azaleaHealth.getPatient(patientID).getPatientData());
	            
	            	}
	            }

			
	        });
		    
		   
		    btnCloseNew.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	                primaryStage.close();
	            }
	        });
		    
		    Scene scene = new Scene(grdRootPane,1600, 750);
			scene.getStylesheets().add(getClass().getResource("new_application_stylesheet.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Application");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
    }
    
	private boolean primaryDataMissing() {
		
		Boolean hasMissing = false;
		
		if(txfFirstName.getText().isEmpty()) {
			hasMissing = true;
			txfFirstName.getStyleClass().add("text-field2");
		}
		
		if(txfLastName.getText().isEmpty()) {
			hasMissing = true;
			txfLastName.getStyleClass().add("text-field2");
		}
		
		if(txfAge.getText().isEmpty()) {
			hasMissing = true;
			txfAge.getStyleClass().add("text-field2");
		}
		
		if(txfBirthdate.getText().isEmpty()) {
			hasMissing = true;
			txfBirthdate.getStyleClass().add("text-field2");
		}
		
		if (sex == null) {
			hasMissing = true;
			rdFemale.getStyleClass().add("radio-button2");
			rdMale.getStyleClass().add("radio-button2");
			rdPreferNot.getStyleClass().add("radio-button2");
		}
		
		if(marital == null){
			hasMissing = true;
			rdSingle.getStyleClass().add("radio-button2");
			rdMarried.getStyleClass().add("radio-button2");
			rdDivorced.getStyleClass().add("radio-button2");
			rdWidow.getStyleClass().add("radio-button2");
		}
		
		if(txfAddress.getText().isEmpty()) {
			hasMissing = true;
			txfAddress.getStyleClass().add("text-field2");
		}
		
		if(txfCity.getText().isEmpty()) {
			hasMissing = true;
			txfCity.getStyleClass().add("text-field2");
		}
		
		if(txfState.getText().isEmpty()) {
			hasMissing = true;
			txfState.getStyleClass().add("text-field2");
		}
		
		if(txfPatientPhone.getText().isEmpty()) {
			hasMissing = true;
			txfPatientPhone.getStyleClass().add("text-field2");
		}else {
			
		}
		
		if(txfSSN.getText().isEmpty()) {
			hasMissing = true;
			txfSSN.getStyleClass().add("text-field2");
		}
		
		return hasMissing;
	}

    private Pane buildApplicationDisplay() throws FileNotFoundException {
    	// Holds each row of the form
    	GridPane application = new GridPane();
    	
    	// Holds Azalea Health logo
    	VBox vBoxLogo = new VBox();
		InputStream stream = new FileInputStream("src\\ver1\\azalea_health.png");
		Image imgAH = new Image(stream);
		
		vBoxLogo.getStyleClass().add("-fx-padding:15px;");
		vBoxLogo.setAlignment(Pos.CENTER);
		
		ImageView imgvwBody = new ImageView();
		imgvwBody.setImage(imgAH);
		imgvwBody.setFitHeight(vBoxLogo.getWidth());
		vBoxLogo.getChildren().add(imgvwBody);
    	
		// Holds patient information
    	VBox vBoxPatientInformation = new VBox();
    	vBoxPatientInformation.setSpacing(20);
    	vBoxPatientInformation.getStyleClass().add("vbox");
    	
    	// Holds emergency contact information
    	VBox vBoxEmergencyContact = new VBox();
    	vBoxEmergencyContact.setSpacing(20);
    	vBoxEmergencyContact.getStyleClass().add("vbox");
    	
    	// Holds both patient and emergency contact info
    	VBox vBoxInformationContainer = new VBox();
    	vBoxInformationContainer.getStyleClass().add("vbox");
    	vBoxInformationContainer.setAlignment(Pos.CENTER);
    	
    	// Holds first name and last name data collectors (first row)
    	GridPane row1 = new GridPane();
    	row1.getStyleClass().add("-fx-background-color: #87cefa;");
    	row1.setHgap(15);
    	// Holds b-day, age, sex, marital status data collectors (second row)
    	GridPane row2 = new GridPane();
    	row2.getStyleClass().add("-fx-background-color: #87cefa;");
    	row2.setHgap(15);
    	// Holds address, city, state, and ssn data collectors (third row)
    	GridPane row3 = new GridPane();
    	row3.getStyleClass().add("-fx-background-color: #87cefa;");
    	row3.setHgap(15);
    	// Holds Emergency Contact Label (fourth row)
    	GridPane row4 = new GridPane();
    	row4.getStyleClass().add("-fx-background-color: #87cefa;");
    	row4.setHgap(15);
    	// Holds emergency contact name, phone, and relationship data collectors (fifth row)
    	GridPane row5 = new GridPane();
    	row5.getStyleClass().add("-fx-background-color: #87cefa;");
    	row5.setHgap(15);
    	
    	// Holds Radio buttons for sex & marital selection
    	HBox hBoxSex = new HBox();
    	hBoxSex.setSpacing(10);
    	HBox hBoxMarital = new HBox();
    	hBoxMarital.setSpacing(10);
    
    	// Holds submit and close buttons
    	HBox buttons = new HBox();
    	buttons.setSpacing(10);
    	
    	/*------------------------
    	 * Patient data variables
    	 -------------------------*/
    	Label lblPatientInfo = new Label("PATIENT INFORMATION");

		Label lblFirstName = new Label ("First Name:");
		txfFirstName = new TextField();
		Label lblLastName = new Label("Last Name:");
		txfLastName = new TextField();
		Label lblAge = new Label("Age:");
		txfAge = new TextField();	
		Label lblBirthdate = new Label("Birthdate:");
		txfBirthdate = new TextField("i.e. 07/30/2001");
		// clears txf once the mouse clicks it
		txfBirthdate.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				txfBirthdate.clear();
				
			}
			
		});
		
		// gender radio buttons
		Label lblSex = new Label("Sex:");
		rdFemale.setToggleGroup(tgrpSex);
		rdFemale.getStyleClass().add("radio-button1");
		rdMale.setToggleGroup(tgrpSex);
		rdMale.getStyleClass().add("radio-button1");
		rdPreferNot.setToggleGroup(tgrpSex);
		rdPreferNot.getStyleClass().add("radio-button1");
		
		// If radio button is selected, store it to the sex variable
		rdFemale.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				sex = rdFemale.getText();
				
			}
			
		});
		
		rdMale.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				sex = rdMale.getText();
				
			}
			
		});

		rdPreferNot.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				sex = rdPreferNot.getText();
				
			}
			
		});
		
		
		// marital radio buttons
		Label lblMaritalStatus = new Label ("Marital Status:");
		rdSingle.setToggleGroup(tgrpMarital);
		rdSingle.getStyleClass().add("radio-button1");		
		rdMarried.setToggleGroup(tgrpMarital);
		rdMarried.getStyleClass().add("radio-button1");
		rdDivorced.setToggleGroup(tgrpMarital);
		rdDivorced.getStyleClass().add("radio-button1");
		rdWidow.setToggleGroup(tgrpMarital);
		rdWidow.getStyleClass().add("radio-button1");
		
		// If radio button selected, store in marital variable
		rdSingle.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				marital = rdSingle.getText();
				
			}
			
		});
		
		rdMarried.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				marital = rdMarried.getText();
				
			}
			
		});

		rdDivorced.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				marital = rdDivorced.getText();
				
			}
			
		});
		
		rdWidow.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				marital = rdWidow.getText();
				
			}
			
		});
		
		Label lblAddress = new Label("Address:");
		txfAddress = new TextField();	
		Label lblCity = new Label("City:");
		txfCity = new TextField();
		Label lblState = new Label("State:");
		txfState = new TextField();
		Label lblPhone = new Label("Phone:");
		txfPatientPhone = new TextField();
		
		Label lblSSN = new Label("SSN:");
		txfSSN = new TextField();
		
		
		/*-------------------------------
		 * Emergency Contact Information
		 --------------------------------*/
		Label lblEmergencyContact = new Label("EMERGENCY CONTACT");
		
		Label lblEmergName = new Label("Name:");
		txfEmergName = new TextField();
		Label lblEmergPhone = new Label("Phone:");
		txfEmergPhone = new TextField();
		Label lblEmergRelationship = new Label("Relationship:");
		txfEmergRelationship = new TextField();

		// submit 
		btnSubmitNew.setAlignment(Pos.CENTER);
		// close
		btnCloseNew.setAlignment(Pos.CENTER);

		// add styling to textfields
		txfFirstName.getStyleClass().add("text-field1");	
		txfLastName.getStyleClass().add("text-field1");
		txfAge.getStyleClass().add("text-field1");
		txfBirthdate.getStyleClass().add("text-field1");
		txfAddress.getStyleClass().add("text-field1");
		txfCity.getStyleClass().add("text-field1");
		txfState.getStyleClass().add("text-field1");
		txfPatientPhone.getStyleClass().add("text-field1");
		txfSSN.getStyleClass().add("text-field1");
		txfEmergName.getStyleClass().add("text-field1");
		txfEmergPhone.getStyleClass().add("text-field1");
		txfEmergRelationship.getStyleClass().add("text-field1");
		
		if(numNewPatients > 0) {
			hBoxSex.getChildren().clear();
			hBoxMarital.getChildren().clear();
    	}
		
		hBoxSex.getChildren().addAll(rdFemale,rdMale,rdPreferNot);
		hBoxMarital.getChildren().addAll(rdSingle, rdMarried, rdDivorced, rdWidow);
		
		row1.add(lblFirstName, 0, 0);
		row1.add(txfFirstName, 1, 0);
		row1.add(lblLastName, 2, 0);
		row1.add(txfLastName, 3, 0);
		
		row2.add(lblBirthdate, 0, 0);
		row2.add(txfBirthdate, 1, 0);
		row2.add(lblAge, 2, 0);
		row2.add(txfAge, 3, 0);
		row2.add(lblSex, 4, 0);
		row2.add(hBoxSex, 5, 0);
		row2.add(lblMaritalStatus, 6, 0);
		row2.add(hBoxMarital, 7, 0);
		
		row3.add(lblAddress, 0, 0);
		row3.add(txfAddress, 1, 0);
		row3.add(lblCity, 2, 0);
		row3.add(txfCity, 3, 0);
		row3.add(lblState, 4, 0);
		row3.add(txfState, 5, 0);
		row3.add(lblPhone, 6, 0);
		row3.add(txfPatientPhone, 7, 0);
		row3.add(lblSSN, 8, 0);
		row3.add(txfSSN, 9, 0);
		
		row4.add(lblEmergencyContact, 0, 0);
		
		row5.add(lblEmergName, 0, 0);
		row5.add(txfEmergName, 1, 0);
		row5.add(lblEmergPhone, 2, 0);
		row5.add(txfEmergPhone, 4, 0);
		row5.add(lblEmergRelationship, 9, 0);
		row5.add(txfEmergRelationship, 10, 0);
		
		
		vBoxPatientInformation.getChildren().addAll(vBoxLogo, lblPatientInfo,row1, row2, row3);
		vBoxEmergencyContact.getChildren().addAll(row4, row5);
		vBoxInformationContainer.getChildren().addAll(vBoxPatientInformation,vBoxEmergencyContact);
		buttons.getChildren().addAll(btnSubmitNew, btnCloseNew);
		application.add(vBoxInformationContainer, 0, 0);
		application.add(buttons, 0, 1);
		
		numNewPatients++;
		
		return application;
	}
    /******************************************
     *End of new Patient event handler methods*
     ******************************************/
    
    // Move Patient down in list (purpose of this process unknown)
	public class CreateMoveUpEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent arg0) {
            int selected = lvwWaitingPatients.getSelectionModel().getSelectedIndex();
            Patient sel = lvwWaitingPatients.getSelectionModel().getSelectedItem();

            if(selected > 0) {
                lvwWaitingPatients.getItems().add(selected - 1, sel);
                lvwWaitingPatients.getItems().remove(selected + 1);
                lvwWaitingPatients.getSelectionModel().select(sel);
                lvwWaitingPatients.getSelectionModel().clearSelection(selected);
            }

        }

    }

	// Move Patient up in list (purpose of this process unknown)
    public class CreateMoveDownEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent arg0) {
            int selected = lvwWaitingPatients.getSelectionModel().getSelectedIndex();
            Patient sel = lvwWaitingPatients.getSelectionModel().getSelectedItem();

            if(selected < lvwWaitingPatients.getItems().size() - 1) {
                lvwWaitingPatients.getItems().add(selected + 2, sel);
                lvwWaitingPatients.getItems().remove(selected);
                lvwWaitingPatients.getSelectionModel().select(sel);
                lvwWaitingPatients.getSelectionModel().clearSelection(0);
            }
        }

    }
    
    private Pane buildRoomButtonsDisplay() {
    	GridPane grid = new GridPane();
 
    	int column = 0;
    	int row = 0;
    	int i = 0;
	
		while(row < ((azaleaHealth.getRooms().size())/2)&& i < azaleaHealth.getRooms().size()||
				row < ((azaleaHealth.getRooms().size())/3)&& i < azaleaHealth.getRooms().size()) {
			while(column < 3 && i < azaleaHealth.getRooms().size()) {
				roomButtons.add(azaleaHealth.getRooms().get(i).getRoomButton());
				grid.add(azaleaHealth.getRooms().get(i).getRoomButton(), column, row);
				i++;
				column++;
			}
			column = 0;
			row++;
		}
		
		if(azaleaHealth.getRooms().size()==1) {
			grid.add(azaleaHealth.getRooms().get(0).getRoomButton(), 0, 0);
		}

    	
    	return grid;
    	
    }

    // Preparing patient
    public class CreatePrepareEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent arg0) {
            Patient selWaiting = lvwWaitingPatients.getItems().get(0);
            preparePatientScreen(azaleaHealth.getFreeRooms().get(0),azaleaHealth.getPatient(selWaiting));
            
        }
        
        // Stage for Room Prep
		private Room preparePatientScreen(Room r, Patient p) {
			try {
				Stage primaryStage = new Stage();
			    Pane grdRootPane = buildPreparationApplicationDisplay(p);
			    
			    btnSubmitPrep.setOnAction(new EventHandler<ActionEvent>() {
		            @Override
		            public void handle(ActionEvent event) {
		            	double height = Double.parseDouble(txfHeight.getText());
		            	double weight = Double.parseDouble(txfWeight.getText());
		            	int bPM = Integer.parseInt(txfBPM.getText());
		            	int systolic = Integer.parseInt(txfSystolicNum.getText());
		            	int diastolic = Integer.parseInt(txfDiastolicNum.getText());
		            	
		            	azaleaHealth.getPatient(p).setHeight(height);
		            	azaleaHealth.getPatient(p).setWeight(weight);
		            	azaleaHealth.getPatient(p).setHeartRate(bPM);
		            	azaleaHealth.getPatient(p).setSystolicNumber(systolic);
		            	azaleaHealth.getPatient(p).setDiastolicNumber(diastolic);
		            	
		            	azaleaHealth.getRoom(r).addPatient(azaleaHealth.getPatient(p));
		            	azaleaHealth.getPatient(p).setStatus(PatientStatus.READY);
		            	lvwWaitingPatients.getItems().remove(r.getPatient());
		                
		                r.getRoomButton().getStyleClass().add("unavailable_room_button");

		            	primaryStage.close();
		            }
		        });
			    
			    btnClosePrep.setOnAction(new EventHandler<ActionEvent>() {
		            @Override
		            public void handle(ActionEvent event) {
		                primaryStage.close();
		            }
		        });
			    
			    Scene scene = new Scene(grdRootPane, 1000, 400);
				scene.getStylesheets().add(getClass().getResource("prepare_patient.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.setTitle("Application");
				primaryStage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			return r;
			
		}

		// Prepare Patient Pane
		private Pane buildPreparationApplicationDisplay(Patient p) {

	    	GridPane application = new GridPane();
	    	HBox midRow = new HBox();
	    	midRow.setSpacing(10);
	    	HBox bottomRow = new HBox();
	    	bottomRow.setSpacing(10);
	    	HBox topRow = new HBox();
	    	topRow.setSpacing(10);
	    	HBox secRow = new HBox();
	    	secRow.setSpacing(10);
	    	
	    	lblFirstName = new Label ("First Name");
		
			lblLastName = new Label("Last Name");
			
			lblAge = new Label("Age");
			
			lblBirthdate = new Label("Birthdate");
			
			lblAddress = new Label("Address");
	    	
	    	String first = azaleaHealth.getPatient(p).getFirstName();
	    	String last = azaleaHealth.getPatient(p).getLastName();
	    	int age = azaleaHealth.getPatient(p).getAge();
	    	
	    	Label lblFirst = new Label(": "+first+"\t\t");
	    	Label lblLast = new Label(": "+last+"\t");
	    	Label lblAg = new Label(": "+age+"\t");
	    	
	    	topRow.getChildren().addAll(lblFirstName, lblFirst, lblLastName, lblLast, lblAge, lblAg);
			
			lblHeight = new Label ("Height:");
			txfHeight = new TextField();
			
			lblWeight = new Label("   Weight:");
			txfWeight = new TextField();
			
			lblBPM = new Label("   BPM:");
			txfBPM = new TextField();
			
			lblSystolicNum = new Label("Systolic Number:");
			txfSystolicNum = new TextField();
			
			lblDiastolicNum = new Label("   Diastolic Number:");
			txfDiastolicNum = new TextField();
			
			HBox buttons = new HBox();
			
			btnSubmitPrep = new Button("Submit");
			btnClosePrep = new Button("Close");
			
			buttons.getChildren().addAll(btnSubmitPrep, btnClosePrep);
			midRow.getChildren().addAll(lblHeight, txfHeight, lblWeight, txfWeight, lblBPM, txfBPM);
			bottomRow.getChildren().addAll(lblSystolicNum, txfSystolicNum, lblDiastolicNum, txfDiastolicNum);
			
			application.add(topRow, 0, 0);
			application.add(midRow, 0, 1);
			application.add(bottomRow, 0, 2);
			application.add(buttons, 0, 3);
			
			return application;
		}

    }
    
    // Working on implementation of what happens when a room button is pressed
    

    // start main method
    public static void main(String[] args) {
        launch(args);
    }
    
    /*
     * Helpers
     */
    
    // create hospital object
    private Hospital createHospital() {
        Hospital azaleaHealth = new Hospital();
        azaleaHealth.addPatient();
        azaleaHealth.addPatient();
        azaleaHealth.addPatient();
        azaleaHealth.addRoom(100);
        azaleaHealth.addRoom(101);
        azaleaHealth.addRoom(102);
        azaleaHealth.addRoom(103);
        azaleaHealth.addRoom(104);
        azaleaHealth.addRoom(105);
        azaleaHealth.addRoom(106);
        azaleaHealth.addRoom(107);
        azaleaHealth.addRoom(108);
        azaleaHealth.addRoom(109);
        azaleaHealth.addRoom(110);
        azaleaHealth.addRoom(111);
        azaleaHealth.addRoom(112);
        azaleaHealth.addRoom(113);
        azaleaHealth.addRoom(114);
        
        
        for(Patient p: azaleaHealth.getPatients()) {
        	if(p.getId()==101) {
        		p.setFirstName("Jasee");
        		p.setLastName("Merth");
        		p.setAge(21);
        		p.setAddress("213 South Highway 129");
        		p.setBirthdate(LocalDate.parse("2015-07-04"));
        		p.setSex("Female");
        		p.setState("CA");
        		p.setCity("Stockton");
        		p.setSSN("xxx-xx-0000");
        		p.setMaritalStatus("Single");
        	}
        	if(p.getId()==100) {
        		p.setFirstName("Kay");
        		p.setLastName("Merth");
        		p.setAge(16);
        		p.setAddress("123 South Highway 129");
        		p.setBirthdate(LocalDate.parse("2016-11-05"));
        		p.setSex("Female");
        		p.setState("CA");
        		p.setCity("Stockton");
        		p.setSSN("xxx-xx-0000");
        		p.setMaritalStatus("Single");
        	}
        	if(p.getId()==102) {
        		p.setFirstName("Brock");
        		p.setLastName("Merth");
        		p.setAge(52);
        		p.setAddress("113 South Highway 129");
        		p.setBirthdate(LocalDate.parse("1980-09-08"));
        		p.setSex("Male");
        		p.setState("CA");
        		p.setCity("Stockton");
        		p.setSSN("xxx-xx-0000");
        		p.setMaritalStatus("Married");
        	}
        }
        
        EmergencyContact ec = new EmergencyContact("Martha Merth", "5555555555","Mother");
        System.out.println(ec);
        return azaleaHealth;
    }
    
    public void showMissingDataScreen() {
    	try {
			Stage primaryStage = new Stage();
		    GridPane grdRootPane = new GridPane();
		    Label lblMissingData = new Label("Missing Data!");
		    lblMissingData.setTextFill(Color.RED);
		    grdRootPane.add(lblMissingData, 0, 0);
		    Scene scene = new Scene(grdRootPane, 200, 100);
			scene.getStylesheets().add(getClass().getResource("new_application_stylesheet.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Application");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
    }
    
    // fix formatting
    public void showCheckInScreen(Patient p) {
    	try {
			Stage primaryStage = new Stage();
		    GridPane grdRootPane = new GridPane();
		    
		    HBox hBoxButtons = new HBox();
		    String msg = "Would you like to\n   check-in this \n    patient?\n\n";
		    
		    Label lblCheckIn = new Label(msg);
		    Button btnYes = new Button("Yes");
		    Button btnNo = new Button("No");
		    
		    btnYes.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					p.setStatus(PatientStatus.WAITING);
					lvwWaitingPatients.getItems().add(p);
					primaryStage.close();
					
				}
		    	
		    });
		    
		    btnNo.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					primaryStage.close();
				}
		    	
		    });
		    hBoxButtons.getChildren().addAll(btnYes, btnNo);
		    
		    grdRootPane.add(lblCheckIn, 0, 0);
		    grdRootPane.add(hBoxButtons, 0, 1);
		    
		    Scene scene = new Scene(grdRootPane, 400, 300);
			scene.getStylesheets().add(getClass().getResource("new_application_stylesheet.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Application");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
    }
  
}
//  
