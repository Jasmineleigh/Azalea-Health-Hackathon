package ver1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

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
 * In the original project, this was written by Chase Vaughn
 * and Lucas Keasbey collaboratively
 */

public class Room implements Comparable<Room> {
    private int roomNumber;
    private Patient p = null;
    private Boolean hasPatient = false;
    private Button room;

    public Room(int roomNumber) {
        this.roomNumber = roomNumber;
        room = new Button("Room " + roomNumber);
        room.getStyleClass().add("available_room_button");
        room.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {
					buildRoomDataDisplay();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
        	
        });
    }

    public boolean addPatient(Patient readyPatient) {
        if(isFree()) {
            p = readyPatient;
            hasPatient = true;
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
    
    public Button getRoomButton() {
    	return room;
    }
    
    public void buildRoomDataDisplay() throws FileNotFoundException {
    	GridPane root = new GridPane();
    	Button btnClose = new Button("Close");
    	Button btnHold = new Button("Hold");
        Button btnInProgress = new Button("In-Progress");
        Button btnCheckout = new Button("CheckOut");

    	if(isFree()) {
			Pane pnFree = new Pane();
			pnFree.setPrefSize(900, 675);
			
			VBox vBoxLogo = new VBox();
			InputStream stream = new FileInputStream("src\\ver1\\azalea_health_smaller.png");
			Image imgAH = new Image(stream);
			ImageView imgvwLogo = new ImageView();
			
			vBoxLogo.getStyleClass().add("-fx-padding:15px;");
			vBoxLogo.setAlignment(Pos.CENTER_RIGHT);
			imgvwLogo.setImage(imgAH);
			imgvwLogo.setFitHeight(vBoxLogo.getWidth());
			vBoxLogo.getChildren().addAll(imgvwLogo);
			
			VBox vBoxFreeRoom = new VBox();
			vBoxFreeRoom.setPrefSize(900, 675);
			vBoxFreeRoom.setAlignment(Pos.CENTER);
			Label lblRoomEmpty = new Label("ROOM IS EMPTY");
			Text txtPreparePatient = new Text("Please prepare a patient from the waiting room.");
			txtPreparePatient.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 18));
			lblRoomEmpty.setAlignment(Pos.CENTER);
			vBoxFreeRoom.getChildren().addAll(lblRoomEmpty, txtPreparePatient);
			root.add(vBoxLogo, 2, 1);
			root.add(btnClose,0, 2);
			btnClose.setAlignment(Pos.CENTER);
			root.add(vBoxFreeRoom, 0, 1);
			
		}else {
			root.add(p.buildGUI(), 0, 0);
			HBox hBoxExamRoomButtons = new HBox();
	    	hBoxExamRoomButtons.setSpacing(5);
	        hBoxExamRoomButtons.getChildren().addAll(btnHold, btnInProgress, btnCheckout, btnClose);

			root.setVgap(10);
			root.add(hBoxExamRoomButtons, 0, 1);
		}
    	
		try {
			Stage primaryStage = new Stage();
		    Pane grdRootPane = root;
		    Scene scene = new Scene(grdRootPane, 1600, 800);
			scene.getStylesheets().add(getClass().getResource("room_data_stylesheet.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Room");
			primaryStage.show();
			
		    btnInProgress.setOnAction(new EventHandler<ActionEvent> () {

	            @Override
	            public void handle(ActionEvent arg0) {
	            	p.setStatus(PatientStatus.IN_PROGRESS);
                	p.setTxtStatus(p.getStatus()+"");
	            }
	        });

			
			btnHold.setOnAction(new EventHandler<ActionEvent>() {
	        	   // Hold Event Handler
	                @Override
	                public void handle(ActionEvent arg0) {
	                	p.setStatus(PatientStatus.ON_HOLD);
	                	p.setTxtStatus(p.getStatus()+"");
	                }
	        });
			
			btnCheckout.setOnAction(new EventHandler<ActionEvent>() {
				

				@Override
				public void handle(ActionEvent arg0) {
	            	room.getStyleClass().removeIf(style -> style.equals("unavailable_room_button"));
	            	
					p.setStatus(PatientStatus.CHECKED_OUT);
	            	removePatient();
	            	primaryStage.close();
				}
	        	
	        });
			
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

    @Override
    public String toString() {
        if(isFree())
            return "Room " + roomNumber + " is free.";
        else {
            return "Room " + roomNumber + " has " + p.toString();
        }
    }

}
