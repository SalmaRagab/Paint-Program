package program;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Gui extends Application implements EventHandler<ActionEvent>{
	 Canvas canvas;
	 GridPane mainLayout;
	 Scene scene;
	 public Stage primaryStage;
	 GuiController controller;
	 int row1Counter = 0;
	 int row2Counter = 0;
	 int drawingButtonSize;

	 Button lineBtn, ellipseBtn, rectangleBtn, addedBtn,deleteBtn,
	squareBtn, fillBtn, undoBtn, redoBtn , saveBtn, loadBtn, newBtn, extensionBtn;
	 ColorPicker colorPicker;

	 List<Button> row1Array = new ArrayList<Button>(); 
	 List<Button> row2Array = new ArrayList<Button>(); 
	 
	public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
    	createButtons();
    	setView();
    	controller = new GuiController(this); 
    	addCanvasHandlers();
        primaryStage.setTitle("Paint");
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    private void createButtons() {
        colorPicker = new ColorPicker();
        lineBtn = new Button();
        ellipseBtn = new Button();
        rectangleBtn = new Button();
        squareBtn = new Button();
        fillBtn = new Button();
        saveBtn = new Button();
        loadBtn = new Button();
        newBtn = new Button();
        undoBtn = new Button();
        redoBtn = new Button();
        extensionBtn = new Button();
        deleteBtn = new Button();
        
    	row1Array = Arrays.asList(fillBtn,deleteBtn,lineBtn, ellipseBtn, rectangleBtn, squareBtn);
    	row2Array = Arrays.asList(newBtn, saveBtn, loadBtn,
    					undoBtn, redoBtn , extensionBtn);
    	
    	styleButton();
        eventButtons();
    }
    
     void styleButton() {
    	drawingButtonSize = 45;
    	
    	int editingButtonSize = 45;
    	
    	for (Button button : row1Array) {
			button.setMaxSize(drawingButtonSize, drawingButtonSize);
			button.setMinSize(drawingButtonSize, drawingButtonSize);
			
		}
    	for (Button button : row2Array) {
			button.setMaxSize(editingButtonSize, editingButtonSize);
			button.setMinSize(editingButtonSize, editingButtonSize);
		}
    	
    	
    	
//    	background    	
//    	scene.getStylesheets().add(style2);
    	rectangleBtn.setStyle("-fx-background-image: url('/resources/rectangle.png');");
    	ellipseBtn.setStyle("-fx-background-image: url('/resources/ellipse.png');");
    	squareBtn.setStyle("-fx-background-image: url('/resources/square.png');");
    	lineBtn.setStyle("-fx-background-image: url('/resources/line.png');");
    	saveBtn.setStyle("-fx-background-image: url('/resources/save.png');");
    	loadBtn.setStyle("-fx-background-image: url('/resources/load.png');");
    	undoBtn.setStyle("-fx-background-image: url('/resources/undo.png');");
    	redoBtn.setStyle("-fx-background-image: url('/resources/redo.png');");
    	deleteBtn.setStyle("-fx-background-image: url('/resources/clear.png');");
    	newBtn.setStyle("-fx-background-image: url('/resources/new.png');");
    	fillBtn.setStyle("-fx-background-image: url('/resources/fill.png');");
    	extensionBtn.setStyle("-fx-background-image: url('/resources/extension.png');");
    	
    	

		colorPicker.setValue(Color.BLACK);
		colorPicker.setMaxWidth(drawingButtonSize);
		

    }
    
    private void eventButtons() {
        lineBtn.setOnAction(this);
        ellipseBtn.setOnAction(this);
        rectangleBtn.setOnAction(this);
        squareBtn.setOnAction(this);
        fillBtn.setOnAction(this);
        colorPicker.setOnAction(this);
        undoBtn.setOnAction(this);
        redoBtn.setOnAction(this);
        saveBtn.setOnAction(this);
        loadBtn.setOnAction(this);
        newBtn.setOnAction(this);
        extensionBtn.setOnAction(this);
        deleteBtn.setOnAction(this);
    }

    void addChildren() {
    	 mainLayout.getChildren().addAll(lineBtn, ellipseBtn, rectangleBtn,
					squareBtn, fillBtn, undoBtn, redoBtn ,deleteBtn,
					saveBtn, loadBtn, newBtn, colorPicker, extensionBtn, canvas);

    }
    
	private void setView() {
    	mainLayout = new GridPane();
    	canvas = new Canvas(950, 500);
   	 	setConstrains();
   	 	addChildren();
//    	Initialisation
        scene = new Scene(mainLayout);
        scene.getStylesheets().add("style.css");
        mainLayout.setHgap(15);
        mainLayout.setVgap(8);





    }
	
	private void addCanvasHandlers() {
		controller.canvasClickHandler();
    	controller.canvasDragHandler();
    	controller.canvasReleaseHandler();
    	controller.canvasKeyHandler();
		canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, controller.clickHandler);
		canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, controller.dragHandler);
		canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, controller.releaseHandler);
        scene.setOnKeyPressed(controller.canvasBackspaceHandler);
	}
	
	@SuppressWarnings("static-access")
	 void setConstrains() {
		
//		color picker
		mainLayout.setConstraints(colorPicker, row1Counter++,2);
		
//		menus
		
		
//		toolbar
		
		for (Button button : row2Array) {
			mainLayout.setConstraints(button, row2Counter++,1);
		}
		
//		Drawing Buttons
		
		for (Button button : row1Array) {
			mainLayout.setConstraints(button,row1Counter++, 2);
		}
		

		
		
		
		
//		canvas
		mainLayout.setConstraints(canvas, 0, 3, 15,1);

	
	}
	
	@SuppressWarnings({ "rawtypes", "static-access" })
	void adjustAdderButton(Class loadedClass) {
		
		String className = loadedClass.getName();
		addedBtn = new Button();
		addedBtn.setId(className);
		String packageName = className;
		className = className.substring(className.lastIndexOf(".") + 1);
		try {
			addedBtn.setStyle("-fx-background-image: url('/resources/" + className.toLowerCase() + ".png');");
		} catch (Exception e) {
			addedBtn.setText(className);
		}
		
		addedBtn.setMaxSize(drawingButtonSize, drawingButtonSize);
		addedBtn.setMinSize(drawingButtonSize, drawingButtonSize);
		addedBtn.setOnAction(this);
		mainLayout.setConstraints(addedBtn, row1Counter++, 2);
		mainLayout.getChildren().add(addedBtn);
				

	}
		

	
	@Override
	public void handle(ActionEvent event) {
		controller.handler(event);
	}

}
