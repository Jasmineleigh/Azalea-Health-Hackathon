package ver1;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
	// buttons for check-in and wait-list
    protected Button btnNewPatient, btnCheckIn, btnMoveUp, btnMoveDown;
    protected Button btnAddRoom, btnPrepPatient, btnHold, btnCheckout, btnInProgress, btnMoreData, btnData;
    protected TextArea txaPatientData;
    
    // search bar variables
    protected TextField txfSearchPatient;
    protected Button btnSearch;
    protected Button filter;
    
    // data entered by patient
    protected TextField txfFirstName, txfLastName, txfAge, txfBirthdate, txfAddress;
    protected Label lblFirstName, lblLastName, lblAge, lblBirthdate, lblAddress;
    protected Button btnSubmit, btnClose;
    protected ListView<String> lvwRaceSelection = new ListView<>();
    
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
            Scene scene = new Scene(root, 1200, 590);
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

    private VBox buildCheckedout() {
        lvwCheckedout.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lvwCheckedout.setPrefHeight(600);
        lvwCheckedout.setPrefWidth(300);
        
        txfSearchPatient = new TextField();
        
       

        VBox display = new VBox();
        display.getStyleClass().add("list");
        display.getChildren().addAll(lvwCheckedout);

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
        btnPrepPatient.setOnAction(new CreateAddPatientEventHandler());

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
            int patientID = azaleaHealth.addPatient();
            List<Patient> patients = azaleaHealth.getPatients();
            Patient patient = null;
            int index = 0;
            int i = 0;
            for(Patient p : patients) {
                if(p.getId() == patientID) {
                	patient = p;
                	index = i;
                	break;
                }
                i++;
            }

            if(patient != null) {
            	azaleaHealth.getPatients().set(index, showApplicationScreen(patient));
                System.out.println("Adding Patient...");
            
            }
        }

    }
    
    public Patient showApplicationScreen(Patient patient) {
    	Patient newPatient = patient;
		try {
			Stage primaryStage = new Stage();
		    Pane grdRootPane = buildApplicationDisplay();
		    
		   
		    btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	String firstName = txfFirstName.getText();
	            	String lastName = txfLastName.getText();
	            	String address = txfAddress.getText();
	            	int age = Integer.parseInt(txfAge.getText());
	            	LocalDate birthdate = LocalDate.parse(txfBirthdate.getText());
	            	Patient newP = new Patient(patient.getId(), firstName, lastName,
	            										address, age, birthdate);
	                lvwWaitingPatients.getItems().add(newP);
	                azaleaHealth.addPatient(newP);
	            	System.out.println(newP.getPatientData());
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
		
		return newPatient;
    }

    private Pane buildApplicationDisplay() {
    	// txfFirstName, txfLastName, txfAge, txfBirthdate, txfAddress
    	// lblFirstName, lblLastName, lblAge, lblBirthdate, lblAddress
    	// btnSubmit, btnClose
    	
    	GridPane application = new GridPane();
    	HBox topRow = new HBox();
    	HBox bottomRow = new HBox();
    	
		lblFirstName = new Label ("First Name");
		txfFirstName = new TextField();
		
		lblLastName = new Label("Last Name");
		txfLastName = new TextField();
		
		lblAge = new Label("Age");
		txfAge = new TextField();
		
		lblBirthdate = new Label("Birthdate");
		txfBirthdate = new TextField();
		
		lblAddress = new Label("Address");
		txfAddress = new TextField();
		
		HBox buttons = new HBox();
		
		btnSubmit = new Button("Submit");
		btnClose = new Button("Close");
		
		buttons.getChildren().addAll(btnSubmit, btnClose);
		topRow.getChildren().addAll(lblFirstName, txfFirstName, lblLastName, txfLastName, lblAge, txfAge);
		bottomRow.getChildren().addAll(lblBirthdate, txfBirthdate, lblAddress, txfAddress);
		application.add(topRow, 0, 0);
		application.add(bottomRow, 0, 1);
		application.add(buttons, 0, 2);
		
		return application;
	}

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

    public class CreateAddPatientEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent arg0) {
            int selectedWaiting = lvwWaitingPatients.getSelectionModel().getSelectedIndex();
            Patient selWaiting = lvwWaitingPatients.getSelectionModel().getSelectedItem();
            int selectedRoom = lvwFreeRooms.getSelectionModel().getSelectedIndex();
            Room selRoom = lvwFreeRooms.getSelectionModel().getSelectedItem();

            List<Patient> patients = azaleaHealth.getPatients();
            List<Room> rooms = azaleaHealth.getFreeRooms();
            
           
            for(Room r : rooms) {
                if(selRoom.equals(r)) {
                    for(Patient p : patients) {
                        if(selWaiting.getId()== p.getId()) {
                            r.addPatient(p);
                            p.setStatus(PatientStatus.READY);
                            r = preparePatientScreen(r);
                            lvwSortedRooms.getItems().add(r);
                            break;
                        }
                    }
                }
            }

            lvwWaitingPatients.getItems().remove(selectedWaiting);
            lvwFreeRooms.getItems().remove(selectedRoom);

        }

		private Room preparePatientScreen(Room r) {
			try {
				Stage primaryStage = new Stage();
			    Pane grdRootPane = buildPreparationApplicationDisplay(r.getPatient());
			   
			    btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
		            @Override
		            public void handle(ActionEvent event) {
		            	double height = Double.parseDouble(txfHeight.getText());
		            	double weight = Double.parseDouble(txfWeight.getText());
		            	int bPM = Integer.parseInt(txfBPM.getText());
		            	int systolic = Integer.parseInt(txfSystolicNum.getText());
		            	int diastolic = Integer.parseInt(txfDiastolicNum.getText());
		            	
		            	r.getPatient().setHeight(height);
		            	r.getPatient().setWeight(weight);
		            	r.getPatient().setHeartRate(bPM);
		            	r.getPatient().setSystolicNumber(systolic);
		            	r.getPatient().setDiastolicNumber(diastolic);
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

		private Pane buildPreparationApplicationDisplay(Patient p) {

	    	GridPane application = new GridPane();
	    	HBox midRow = new HBox();
	    	HBox bottomRow = new HBox();
	    	HBox topRow = new HBox();
	    	HBox secRow = new HBox();
	    	
	    	String first = p.getFirstName();
	    	String last = p.getLastName();
	    	int age = p.getAge();
	    	String birthdate = p.getBirthdate().toString();
	    	String address = p.getAddress();
	    	
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
    public class CreateRoomEventHandler implements EventHandler<ActionEvent> {
    	
    	@Override
    	public void handle (ActionEvent event) {
    		try {
				Stage primaryStage = new Stage();
			    Pane grdRootPane = buildPreparationApplicationDisplay(r.getPatient());
			   
			    btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
		            @Override
		            public void handle(ActionEvent event) {
		            	double height = Double.parseDouble(txfHeight.getText());
		            	double weight = Double.parseDouble(txfWeight.getText());
		            	int bPM = Integer.parseInt(txfBPM.getText());
		            	int systolic = Integer.parseInt(txfSystolicNum.getText());
		            	int diastolic = Integer.parseInt(txfDiastolicNum.getText());
		            	
		            	r.getPatient().setHeight(height);
		            	r.getPatient().setWeight(weight);
		            	r.getPatient().setHeartRate(bPM);
		            	r.getPatient().setSystolicNumber(systolic);
		            	r.getPatient().setDiastolicNumber(diastolic);
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
				scene.getStylesheets().add(getClass().getResource("roomstylesheet.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.setTitle("Room");
				primaryStage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}
    	}
    }
    
    private void buildRoomInformationDisplay(Room r) {
    	
    }

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

    public static void main(String[] args) {
        launch(args);
    }

    private Hospital createHospital() {
        Hospital azaleaHealth = new Hospital();
        azaleaHealth.addPatient();
        azaleaHealth.addRoom(100);
        azaleaHealth.addRoom(101);
        azaleaHealth.addRoom(102);
        return azaleaHealth;
    }
  
}
