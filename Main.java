package ver1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;
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
    protected Button btnAddRoom, btnPrepPatient, btnHold, btnCheckout, btnInProgress, btnMoreData, btnData;
    protected TextArea txaPatientData;
    
    // search bar variables
    protected TextField txfSearchPatient;
    protected Button btnSearch, btnFilter;
    protected int numSearchBarClicks = 0;
    
    // data entered by patient
    protected TextField txfFirstName, txfLastName, txfAge, txfBirthdate, txfAddress,
    					txfCity, txfState, txfPatientPhone, txfSSN,
    					txfEmergRelationship, txfEmergName, txfEmergPhone;
    
    protected ToggleGroup tgrpSex, tgrpMarital, tgrpRace, tgrpIllnesses;
    protected Label lblFirstName, lblLastName, lblAge, lblBirthdate, lblAddress;
    protected Button btnSubmit, btnClose;
    
    // data entered by nurse upon preparation
    protected TextField txfHeight, txfWeight, txfBPM, txfSystolicNum, txfDiastolicNum;
    protected Label lblHeight, lblWeight, lblBPM, lblSystolicNum, lblDiastolicNum;
    
    // data for queue management
    protected Label lblWaitList, lblFreeRooms, lblOccupiedRooms, lblCheckedOut, lblGetData;
    protected ListView<Patient> lvwWaitingPatients = new ListView<>();
    protected ListView<Room> lvwFreeRooms = new ListView<>();
    protected ListView<Room> lvwSortedRooms = new ListView<>();
    protected ListView<Patient> lvwCheckedout = new ListView<>();
    protected Hospital azaleaHealth = createHospital();
    
    // update
    protected Button btnRoom1, btnRoom2, btnRoom3, btnRoom4, btnRoom5, btnRoom6;

    @Override
    public void start(Stage primaryStage) {
        try {
            Pane root = buildGUI();
            Scene scene = new Scene(root, 1600, 650);
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
    	lblOccupiedRooms = new Label("Used Rooms");
    	lblGetData = new Label("Get Data");
    	
    	
    	GridPane fullPane = new GridPane();
    	fullPane.setVgap(25);
    	fullPane.setHgap(25);
    	fullPane.setPadding(new Insets(10, 10, 60, 60));
   
    	
    	GridPane firstCol = new GridPane();
//    	firstCol.getStyleClass().add("grid1");
    	firstCol.setPadding(new Insets(10, 10, 10, 10));
    	firstCol.setVgap(20);
    	firstCol.setHgap(30);
    	
    	firstCol.add(lblWaitList, 0, 0);
    	firstCol.add(buildListOfWaitingPatients(), 0, 1);
    	firstCol.add(lblFreeRooms, 1, 0);
    	firstCol.add(buildListOfFreeRooms(), 1, 1);
    	firstCol.add(lblGetData, 0, 2);
    	firstCol.add(lblOccupiedRooms, 1, 2);
    	firstCol.add(buildRoomButtonsDisplay(), 1, 3);
    	firstCol.add(buildPatientDataDisplay(), 0, 3);
    	firstCol.add(btnMoreData, 1, 4);
    	
    	
     	GridPane secondCol = new GridPane();
     	secondCol.setVgap(20);
     	secondCol.setHgap(30);
     	secondCol.setPadding(new Insets(10, 10, 10, 10));
     	
    	secondCol.add(lblCheckedOut, 0, 0);
    	secondCol.add(buildCheckedout(), 0, 1);
    	secondCol.add(btnData, 0, 2);
    	
    	fullPane.add(firstCol, 0, 0);
    	fullPane.add(secondCol, 1, 0);

        VBox root = new VBox();
        root.getStyleClass().add("root");
        root.getChildren().addAll(fullPane);
        return root;
    }

    private HBox buildDisplay() {
        HBox display = new HBox();
        display.getStyleClass().add("h_box");
        VBox vBoxWaitingPatients = buildListOfWaitingPatients();
        VBox vBoxFreeRooms = buildListOfFreeRooms();
        VBox vBoxSortedRooms = buildListOfSortedRooms();
        
        display.getChildren().addAll(vBoxWaitingPatients, vBoxFreeRooms, vBoxSortedRooms);

        return display;
    }
    
    private Pane buildRoomButtonsDisplay() {
    	GridPane grid = new GridPane();
    	grid.setHgap(5);
    	grid.setVgap(5);
    	
    	btnRoom1 = new Button("Room 100");
    	btnRoom1.getStyleClass().add("available_room_button");
    	btnRoom1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {
					buildRoomDataDisplay(btnRoom1.getText());
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
    		
    	});
    	
    	btnRoom2 = new Button("Room 101");
    	btnRoom2.getStyleClass().add("available_room_button");
    	
    	btnRoom3 = new Button("Room 102");
    	btnRoom3.getStyleClass().add("available_room_button");
    	
    	btnRoom4 = new Button("Room 103");
    	btnRoom4.getStyleClass().add("available_room_button");
    	
    	btnRoom5 = new Button("Room 104");
    	btnRoom5.getStyleClass().add("available_room_button");
    	
    	btnRoom6 = new Button("Room 105");
    	btnRoom6.getStyleClass().add("available_room_button");
    	
    	grid.add(btnRoom1, 0, 0);
    	grid.add(btnRoom2, 0, 1);
    	grid.add(btnRoom3, 1, 0);
    	grid.add(btnRoom4, 1, 1);
    	grid.add(btnRoom5, 2, 0);
    	grid.add(btnRoom6, 2, 1);
    	
    	return grid;
    	
    }
    
    private Pane buildPatientDataDisplay() {
    	HBox display = new HBox();
    	display.getStyleClass().add("h_box");
    	
    	txaPatientData = new TextArea();
    	txaPatientData.setMaxHeight(200);
    	txaPatientData.setMaxWidth(300);
    	
    	btnData = new Button("Get Info");
    	btnData.setOnAction(new EventHandler<ActionEvent> () {
    		@Override
    		public void handle(ActionEvent event) {
    			List<Patient> patients = azaleaHealth.getPatients();
    			Patient selected = lvwCheckedout.getSelectionModel().getSelectedItem();
    			
    			for(Patient p: patients) {
    				if(selected.getId() == p.getId()) {
    					txaPatientData.setText(p.getPatientData());
    				}
    			}
    			
    			
    		}
    	});
    	
    	btnMoreData = new Button("Get Info");
    	btnMoreData.setOnAction(new EventHandler<ActionEvent> () {
    		@Override
    		public void handle(ActionEvent event) {
    			List<Patient> patients = azaleaHealth.getPatients();
    			Patient selected = lvwSortedRooms.getSelectionModel().getSelectedItem().getPatient();
    			
    			for(Patient p: patients) {
    				if(selected.getId() == p.getId()) {
    					txaPatientData.setText(p.getPatientData());
    				}
    			}
    			
    			
    		}
    	});
    	
    	
    	display.getChildren().add(txaPatientData);
    	
    	return display;
    }

    // search for patient
    private VBox buildCheckedout() {
        lvwCheckedout.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lvwCheckedout.setPrefHeight(600);
        lvwCheckedout.setPrefWidth(300);
        
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
				lvwCheckedout.getItems();
				String search = txfSearchPatient.getText();
				List<Patient> found = azaleaHealth.getPatientsWithTheseLetters(search);
				
				lvwCheckedout.getItems().clear();
				
				for(Patient p:found) {
					lvwCheckedout.getItems().add(p);
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
        display.getChildren().addAll(hBoxSearch,lvwCheckedout);

        return display;
    }

    private VBox buildListOfSortedRooms() {
        lvwSortedRooms.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lvwSortedRooms.setPrefHeight(200);
        lvwSortedRooms.setPrefWidth(350);

        VBox display = new VBox();
        display.getStyleClass().add("list");
        display.getChildren().add(lvwSortedRooms);

        return display;
    }

    private VBox buildListOfFreeRooms() {
        lvwFreeRooms.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lvwFreeRooms.setPrefHeight(300);
        lvwFreeRooms.setPrefWidth(300);

        List<Room> freeRooms = azaleaHealth.getFreeRooms();

        for(Room r : freeRooms) {
            lvwFreeRooms.getItems().add(r);
        }

        VBox display = new VBox();
        HBox hBoxList = new HBox();
        hBoxList.getStyleClass().add("list");
        hBoxList.getChildren().addAll(buildExamRoomButtons(), lvwFreeRooms);
        display.getChildren().add(hBoxList);

        return display;
    }

    private VBox buildListOfWaitingPatients() {
        lvwWaitingPatients.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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
    			int fromCheckout = lvwCheckedout.getSelectionModel().getSelectedIndex();
    			Patient toCheckIn = lvwCheckedout.getSelectionModel().getSelectedItem();
    			lvwCheckedout.getItems().remove(fromCheckout);
    			toCheckIn.setStatus(PatientStatus.WAITING);
    			lvwWaitingPatients.getItems().add(toCheckIn);
    		}
    	});

        btnMoveUp = new Button("Move Up");
        btnMoveUp.setOnAction(new CreateMoveUpEventHandler());

        btnMoveDown = new Button("MoveDown");
        btnMoveDown.setOnAction(new CreateMoveDownEventHandler());

        buttonsBox.getChildren().addAll(btnNewPatient, btnCheckIn, btnMoveUp, btnMoveDown);

        return buttonsBox;
    }
    
    public VBox buildExamRoomButtons() {
    	VBox hBoxExamRoomButtons = new VBox();
    	hBoxExamRoomButtons.getStyleClass().add("buttons_box");

        btnPrepPatient = new Button("Prepare");
        btnPrepPatient.setOnAction(new CreatePrepareEventHandler());

        btnHold = new Button("Hold");
        btnHold.setOnAction(new CreateHoldEventHandler());

        btnInProgress = new Button("In-Progress");
        btnInProgress.setOnAction(new CreateInProgressEventHandler());

        btnCheckout = new Button("CheckOut");
        btnCheckout.setOnAction(new CreateCheckoutEventHandler());
        
        hBoxExamRoomButtons.getChildren().addAll(btnPrepPatient, btnHold, btnInProgress, btnCheckout);
        
        return hBoxExamRoomButtons;
    }

    public class CreateNewPatientEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent arg0) {
            
        	showApplicationScreen();

        }

    }
    
    public Patient showApplicationScreen() {
    	Patient newPatient = null;
    	
		try {
			Stage primaryStage = new Stage();
		    Pane grdRootPane = buildApplicationDisplay();
		    
		   
		    btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	
	            	int patientID = azaleaHealth.addPatient();

	            	azaleaHealth.getPatient(patientID).setFirstName(txfFirstName.getText());
	            	azaleaHealth.getPatient(patientID).setLastName(txfLastName.getText());
	            	azaleaHealth.getPatient(patientID).setAddress(txfAddress.getText());
	            	azaleaHealth.getPatient(patientID).setAge(Integer.parseInt(txfAge.getText()));
	            	azaleaHealth.getPatient(patientID).setBirthdate(LocalDate.parse(txfBirthdate.getText()));
	          
	                lvwWaitingPatients.getItems().add(azaleaHealth.getPatient(patientID));
	                primaryStage.close();
	                System.out.println(azaleaHealth.getPatient(patientID).getPatientData());
	            }
	        });
		    
		    
		    btnClose.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	                primaryStage.close();
	            }
	        });
		    
		    Scene scene = new Scene(grdRootPane, 900, 200);
			scene.getStylesheets().add(getClass().getResource("new_application_stylesheet.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Application");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return newPatient;
    }

    private Pane buildApplicationDisplay() throws FileNotFoundException {
    	// txfFirstName, txfLastName, txfAge, txfBirthdate, txfAddress
    	// lblFirstName, lblLastName, lblAge, lblBirthdate, lblAddress
    	// btnSubmit, btnClose
    	
    	// *******************make all gridpanes not hboxes**********************
    	
    	GridPane application = new GridPane();
    	
    	VBox vBoxBody = new VBox();
		InputStream stream = new FileInputStream("src\\ver1\\azalea_health.png");
		Image imgAH = new Image(stream);
		
		vBoxBody.getStyleClass().add("-fx-padding:15px;");
		vBoxBody.setAlignment(Pos.CENTER);
		
		ImageView imgvwBody = new ImageView();
		imgvwBody.setImage(imgAH);
		imgvwBody.setFitHeight(vBoxBody.getWidth());
		// iv.setFitHeight(textSize);
		// iv.setScaleY(iconSize/textSize);
		vBoxBody.getChildren().add(imgvwBody);
    	
    	VBox box1 = new VBox();
    	box1.setSpacing(20);
    	box1.getStyleClass().add("vbox");
    	
    	VBox box2 = new VBox();
    	box2.setSpacing(20);
    	box2.getStyleClass().add("vbox");
    	
    	VBox box3 = new VBox();
    	box3.getStyleClass().add("vbox");
    	box3.setAlignment(Pos.CENTER);
    	
    	GridPane row1 = new GridPane();
    	row1.getStyleClass().add("-fx-background-color: #87cefa;");
    	row1.setHgap(15);
    	
    	GridPane row2 = new GridPane();
    	row2.getStyleClass().add("-fx-background-color: #87cefa;");
    	row2.setHgap(15);
    	
    	GridPane row3 = new GridPane();
    	row3.getStyleClass().add("-fx-background-color: #87cefa;");
    	row3.setHgap(15);
    	
    	GridPane row4 = new GridPane();
    	row4.getStyleClass().add("-fx-background-color: #87cefa;");
    	row4.setHgap(15);
    	
    	GridPane row5 = new GridPane();
    	row5.getStyleClass().add("-fx-background-color: #87cefa;");
    	row5.setHgap(15);
    	
    	
    	HBox hBoxSex = new HBox();
    	hBoxSex.setSpacing(10);
    	
    	HBox hBoxMarital = new HBox();
    	hBoxMarital.setSpacing(10);
    	
    	HBox buttons = new HBox();
    	buttons.setSpacing(10);
    	
    	Label lblPatientInfo = new Label("PATIENT INFORMATION");

		Label lblFirstName = new Label ("First Name:");
		txfFirstName = new TextField();
		
		Label lblLastName = new Label("Last Name:");
		txfLastName = new TextField();
		
		Label lblAge = new Label("Age:");
		txfAge = new TextField();	
		
		Label lblBirthdate = new Label("Birthdate:");
		txfBirthdate = new TextField();
		
		// gender radio buttons
		Label lblSex = new Label("Sex:");
		RadioButton rdFemale = new RadioButton("Female");
		rdFemale.setToggleGroup(tgrpSex);
		RadioButton rdMale = new RadioButton("Male");
		rdMale.setToggleGroup(tgrpSex);
		RadioButton rdPreferNot = new RadioButton("Prefer Not To Say");
		rdPreferNot.setToggleGroup(tgrpSex);
		
		hBoxSex.getChildren().addAll(rdFemale,rdMale,rdPreferNot);
		
		// marital radio buttons
		Label lblMaritalStatus = new Label ("Marital Status:");
		RadioButton rdSingle = new RadioButton("Single");
		rdSingle.setToggleGroup(tgrpMarital);
		RadioButton rdMarried = new RadioButton("Married");
		rdMarried.setToggleGroup(tgrpMarital);
		RadioButton rdDivorced = new RadioButton("Divorced");
		rdDivorced.setToggleGroup(tgrpMarital);
		RadioButton rdWidow = new RadioButton("Widow");
		rdWidow.setToggleGroup(tgrpMarital);
		
		
		hBoxMarital.getChildren().addAll(rdSingle, rdMarried, rdDivorced, rdWidow);
		
		Label lblAddress = new Label("Address:");
		txfAddress = new TextField();	
		
		Label lblCity = new Label("City:");
		txfCity = new TextField();
		
		Label lblState = new Label("State:");
		txfState = new TextField();
		
		Label lblPhone = new Label("Phone:");
		txfPatientPhone = new TextField();
		
		Text parenth1 = new Text("(");
		parenth1.setFill(Color.GHOSTWHITE);
		parenth1.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		Text parenth2 = new Text(")");
		parenth2.setFill(Color.GHOSTWHITE);
		parenth2.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		Text dash = new Text("-");
		dash.setFill(Color.GHOSTWHITE);
		dash.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		
		Label lblSSN = new Label("SSN:");
		txfSSN = new TextField();
		
		Label lblEmergencyContact = new Label("EMERGENCY CONTACT");
		
		Label lblEmergName = new Label("Name:");
		txfEmergName = new TextField();
		
		Label lblEmergPhone = new Label("Phone:");
		txfEmergPhone = new TextField();
		
		Label lblEmergRelationship = new Label("Relationship:");
		txfEmergRelationship = new TextField();

		btnSubmit = new Button("Submit");
		btnSubmit.setAlignment(Pos.CENTER);
		
		btnClose = new Button("Close");
		btnClose.setAlignment(Pos.CENTER);
		
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
		
		
		box1.getChildren().addAll(vBoxBody, lblPatientInfo,row1, row2, row3);
		box2.getChildren().addAll(row4, row5);
		box3.getChildren().addAll(box1,box2);
		buttons.getChildren().addAll(btnSubmit, btnClose);
		application.add(box3, 0, 0);
		
		return application;
	}

    // Move Patient down in list (purpose of this process unknown)
	public class CreateMoveUpEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent arg0) {
            int selected = lvwWaitingPatients.getSelectionModel().getSelectedIndex();
            Patient sel = lvwWaitingPatients.getSelectionModel().getSelectedItem();

            if(selected > 0) {
                lvwWaitingPatients.getItems().add(selected - 1, sel);
                lvwWaitingPatients.getItems().remove(selected + 1);
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
            }

        }

    }

    // Preparing patient
    public class CreatePrepareEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent arg0) {
            Patient selWaiting = lvwWaitingPatients.getSelectionModel().getSelectedItem();
            Room selRoom = lvwFreeRooms.getSelectionModel().getSelectedItem();
                
            preparePatientScreen(azaleaHealth.getRoom(selRoom),azaleaHealth.getPatient(selWaiting));

        }
        
        // Stage for Room Prep
		private Room preparePatientScreen(Room r, Patient p) {
			try {
				Stage primaryStage = new Stage();
			    Pane grdRootPane = buildPreparationApplicationDisplay(p);
			   
			    btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
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
		                lvwFreeRooms.getItems().remove(r);
		            	
		            	primaryStage.close();
		            }
		        });
			    
			    
			    btnClose.setOnAction(new EventHandler<ActionEvent>() {
		            @Override
		            public void handle(ActionEvent event) {
		                primaryStage.close();
		            }
		        });
			    
			    Scene scene = new Scene(grdRootPane, 600, 400);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
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
	    	HBox bottomRow = new HBox();
	    	HBox topRow = new HBox();
	    	HBox secRow = new HBox();
	    	
	    	lblFirstName = new Label ("First Name");
		
			lblLastName = new Label("Last Name");
			
			lblAge = new Label("Age");
			
			lblBirthdate = new Label("Birthdate");
			
			lblAddress = new Label("Address");
	    	
	    	String first = azaleaHealth.getPatient(p).getFirstName();
	    	String last = azaleaHealth.getPatient(p).getLastName();
	    	int age = azaleaHealth.getPatient(p).getAge();
	    	String birthdate = azaleaHealth.getPatient(p).getBirthdate().toString();
	    	String address = azaleaHealth.getPatient(p).getAddress();
	    	
	    	Label lblFirst = new Label(": "+first+"\t\t");
	    	Label lblLast = new Label(": "+last+"\t");
	    	Label lblAg = new Label(": "+age+"\t");
	    	Label lblBDay = new Label(": "+birthdate+"\t");
	    	Label lblAddr = new Label(": "+address+"\t");
	    	
	    	topRow.getChildren().addAll(lblFirstName, lblFirst, lblLastName, lblLast, lblAge, lblAg);
	    
			secRow.getChildren().addAll(lblBirthdate, lblBDay, lblAddress, lblAddr);
			
			lblHeight = new Label ("Height");
			txfHeight = new TextField();
			
			lblWeight = new Label("Weight");
			txfWeight = new TextField();
			
			lblBPM = new Label("BPM");
			txfBPM = new TextField();
			
			lblSystolicNum = new Label("Systolic Number");
			txfSystolicNum = new TextField();
			
			lblDiastolicNum = new Label("Diastolic Number");
			txfDiastolicNum = new TextField();
			
			HBox buttons = new HBox();
			
			btnSubmit = new Button("Submit");
			btnClose = new Button("Close");
			
			buttons.getChildren().addAll(btnSubmit, btnClose);
			midRow.getChildren().addAll(lblHeight, txfHeight, lblWeight, txfWeight, lblBPM, txfBPM);
			bottomRow.getChildren().addAll(lblSystolicNum, txfSystolicNum, lblDiastolicNum, txfDiastolicNum);
			
			application.add(topRow, 0, 0);
			application.add(secRow, 0, 1);
			application.add(midRow, 0, 2);
			application.add(bottomRow, 0, 3);
			application.add(buttons, 0, 4);
			
			return application;
		}

    }
    
    // Working on implementation of what happens when a room button is pressed
    public void buildRoomDataDisplay(String number) throws FileNotFoundException {
		int roomNumber = Integer.parseInt(number.substring(number.indexOf("1"),number.length()));
		GridPane root = new GridPane();
		GridPane col1 = new GridPane();
		GridPane col2 = new GridPane();
		
		// check
		System.out.println(roomNumber);
		
		VBox vBoxBody = new VBox();
		InputStream stream = new FileInputStream("src\\ver1\\female.jpg");
		Image imgFemale = new Image(stream);
		
		
		ImageView imgvwBody = new ImageView();
		imgvwBody.setImage(imgFemale);
		vBoxBody.getChildren().add(imgvwBody);
		col1.getChildren().add(vBoxBody);
		
		Room room = azaleaHealth.getRoom(roomNumber);
		
		// needs to be able to set status
		
		Label lblStatus = new Label("STATUS: ");
		Label lblName = new Label("Name: ");
		Label lblAge = new Label("Age: ");
		Label lblDOB = new Label("DOB: ");
		Label lblAddress = new Label("Address: ");
		Label lblHeight = new Label("Height: ");
		Label lblWeight = new Label("Weight");		
		Label lblBPM = new Label("BPM: ");
		Label lblBloodPressure = new Label("Blood Pressure: ");
		Label lblRace = null;
		Label lblSex = null;
		Label lblState = null;
		Label lblZip = null;
		Label lblIllnesses = null;
		Label lblNotes = new Label("Notes: ");
		
		
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
		
//		HBox hBoxLine6 = new HBox();
//		hBoxLine6.setSpacing(3);
		
		
		
		
		if(room.isFree()) {
			hBoxLine1.getChildren().addAll(lblStatus);
			hBoxLine2.getChildren().addAll(lblName,lblAge,lblDOB);
//			hBoxLine3.getChildren().addAll(lblRace, lblSex);
			hBoxLine4.getChildren().addAll(lblAddress);
			hBoxLine5.getChildren().addAll(lblHeight, lblWeight, lblBPM, lblBloodPressure);
//			hBoxLine6.getChildren().addAll(lblIllnesses);
			
		}else {
			Text txtStatus = new Text(room.getPatient().getStatus()+"");

			Text txtName = new Text(room.getPatient().getFirstName() + " " + room.getPatient().getLastName());

			Text txtAge = new Text(room.getPatient().getAge()+"");

			Text txtDOB = new Text(room.getPatient().getBirthdate().getMonthValue() + "/ "
								+ room.getPatient().getBirthdate().getDayOfMonth() + "/ "
								+room.getPatient().getBirthdate().getYear());
			
			Text txtSex = null;
			
			Text txtRace = null;
			
			Text txtAddress = new Text(room.getPatient().getAddress());
			
			Text txtState = null;
			
			Text txtZip = null;
			
			Text txtHeight = new Text(room.getPatient().getHeight() + "");
			
			Text txtWeight = new Text(room.getPatient().getWeight()+"");
			
			Text txtBPM = new Text(room.getPatient().getHeartRate()+"");
			
			Text txtBloodPressure = new Text(room.getPatient().getBloodPressure());
		
			Text txtIllnesses = null;
			
			hBoxLine1.getChildren().addAll(lblStatus, txtStatus);
			hBoxLine2.getChildren().addAll(lblName, txtName, lblAge, txtAge, lblDOB, txtDOB);
//			hBoxLine3.getChildren().addAll(lblRace, txtRace, lblSex, txtSex);
			hBoxLine4.getChildren().addAll(lblAddress, txtAddress);
			hBoxLine5.getChildren().addAll(lblHeight, txtHeight, lblWeight, txtWeight, lblBPM, txtBPM, lblBloodPressure, txtBloodPressure);
//			hBoxLine6.getChildren().addAll(lblIllnesses, txtIllnesses);
		}

		col2.add(hBoxLine1, 0, 0);
		col2.add(hBoxLine2, 0, 1);
//		col2.add(hBoxLine3, 0, 2);
		col2.add(hBoxLine4, 0, 3);
		col2.add(hBoxLine5, 0, 4);
//		col2.add(hBoxLine6, 0, 5);

		root.add(col1, 0, 0);
		root.add(col2, 1, 0);
		
		
		try {
			Stage primaryStage = new Stage();
		    Pane grdRootPane = root;

		    
		    Scene scene = new Scene(grdRootPane, 600, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Room");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		
	}

    // Hold Event Handler
	public class CreateHoldEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent arg0) {
            Room roomSel = lvwSortedRooms.getSelectionModel().getSelectedItem();
            int roomSelected = lvwSortedRooms.getSelectionModel().getSelectedIndex();
            roomSel.getPatient().setStatus(PatientStatus.ON_HOLD);

            lvwSortedRooms.getItems().remove(roomSelected);
            lvwSortedRooms.getItems().add(roomSel);

        }

    }
	// In progress Event Handler
    public class CreateInProgressEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent arg0) {
            Room roomSel = lvwSortedRooms.getSelectionModel().getSelectedItem();
            int roomSelected = lvwSortedRooms.getSelectionModel().getSelectedIndex();
            roomSel.getPatient().setStatus(PatientStatus.IN_PROGRESS);

            lvwSortedRooms.getItems().remove(roomSelected);
            lvwSortedRooms.getItems().add(roomSel);

        }

    }
    // check out event handler
    public class CreateCheckoutEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent arg0) {
            Room roomSel = lvwSortedRooms.getSelectionModel().getSelectedItem();
            int roomSelected = lvwSortedRooms.getSelectionModel().getSelectedIndex();
            roomSel.getPatient().setStatus(PatientStatus.CHECKED_OUT);

            lvwSortedRooms.getItems().remove(roomSelected);
            Patient pat = roomSel.removePatient();
            lvwCheckedout.getItems().add(pat);
            lvwFreeRooms.getItems().add(roomSel);

        }

    }

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
        azaleaHealth.addRoom(100);
        azaleaHealth.addRoom(101);
        azaleaHealth.addRoom(102);
        for(Patient p: azaleaHealth.getPatients()) {
        	if(p.getId()==101) {
        		p.setFirstName("Jasmine");
        		p.setLastName("Merritt");
        		p.setAge(21);
        		p.setAddress("1213 South Highway 129");
        		p.setBirthdate(LocalDate.parse("2001-07-04"));
        	}
        }
        
        EmergencyContact ec = new EmergencyContact("Marva Merritt", "9123815383","Mother");
        System.out.println(ec);
        return azaleaHealth;
    }
  
}
