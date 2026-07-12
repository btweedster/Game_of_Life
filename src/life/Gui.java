package life;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Gui extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int CELL_SIZE = 10;
    
    private static final int COLS = WIDTH / CELL_SIZE;
    private static final int ROWS = HEIGHT / CELL_SIZE;

    private AnimationTimer timer;
    
    private Controls controller = new Controls();
    private Coord click_start = null;
    private Coord click_end = null;
    private boolean draw_selection_box = false;
    
    
    private Coord get_mouse_coord(MouseEvent e) {
        long x = (long) (e.getX() / CELL_SIZE);
        long y = (long) (e.getY() / CELL_SIZE);
        return new Coord(x, y);
    }
    
    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Control Buttons
        Button prevBtn = new Button("Previous");
        prevBtn.setOnAction(_ -> {
        	controller.previous();
        	update(gc);
        });
        
        Button nextBtn = new Button("Next");
        nextBtn.setOnAction(_ -> {
        	controller.next();
        	update(gc);
        });
        
        Button playBtn = new Button("Start");
        playBtn.setOnAction(_ -> {
        	controller.toggle_playing();
        	if (controller.is_playing()) {
        		playBtn.setText("Pause");
        		prevBtn.setDisable(true);
        		nextBtn.setDisable(true);
        	} else {
        		playBtn.setText("Play");
        		prevBtn.setDisable(false);
        		nextBtn.setDisable(false);
        	}
        });
        
        Button resetBtn = new Button("Reset");
        resetBtn.setOnAction(_ -> {
        	controller.reset();
    		playBtn.setText("Play");
    		prevBtn.setDisable(false);
    		nextBtn.setDisable(false);
            update(gc);
        });
        
        TextInputDialog shapeNameDialog = new TextInputDialog("My Shape");
        shapeNameDialog.setTitle("Save Selection to Library");
        shapeNameDialog.setHeaderText(null); // get rid of the ugly header section
        shapeNameDialog.setGraphic(null); // get rid of the ugly icon too
        shapeNameDialog.setContentText("Name:");
        
        Button loadBtn = new Button("Load Shape");
        loadBtn.setOnAction(_ -> {
        	open_load_menu();
        });
        loadBtn.setDisable(true);
        
        Button saveBtn = new Button("Save Shape");
        saveBtn.setOnAction(_ -> {
        	Optional<String> result = shapeNameDialog.showAndWait();
        	result.ifPresent(name -> {
                controller.save_selection_to_library(name);
                saveBtn.setDisable(true);
                loadBtn.setDisable(false);
            });
        });
        saveBtn.setDisable(true);
        

        HBox controls = new HBox(10, prevBtn, playBtn, nextBtn, resetBtn, saveBtn, loadBtn);
        BorderPane root = new BorderPane();
        
        canvas.setOnMousePressed(e -> {
        	click_start = get_mouse_coord(e);
            controller.clear_selection();
    		saveBtn.setDisable(true);
        });
        
        canvas.setOnMouseDragged(e -> {
            click_end = get_mouse_coord(e);

            if (!click_start.equals(click_end)) {
            	// drag has left the original cell, draw a selection box
            	controller.select_cells(click_start, click_end);
            	draw_selection_box = true;
            	draw_selection(gc);
            }
        });
        
        canvas.setOnMouseReleased(e -> {
            click_end = get_mouse_coord(e);
            draw_selection_box = false;
            
        	if (click_start.equals(click_end)) {
        		// start and stop on the same cell, treat as a click
        		controller.clear_selection();
        		saveBtn.setDisable(true);
        		controller.place_shape(click_end);
        	} else {
            	controller.select_cells(click_start, click_end);
            	saveBtn.setDisable(false);
        	}
        });
        
        root.setCenter(canvas);
        root.setBottom(controls);

        // Core Game Loop Execution
        timer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= controller.get_speed()) { 
                    if (controller.is_playing()) {
                        controller.next();
                    } else {
                		playBtn.setText("Play");
                		prevBtn.setDisable(false);
                		nextBtn.setDisable(false);
                    }
                    update(gc);
                    lastUpdate = now;
                }
            }
        };
        timer.start();

        primaryStage.setTitle("Comp 645: Game of Life");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        
        update(gc); // Initial draw
    }

    // Update the UI grid to show the latest info
    private void update(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        Set<Coord> cells = controller.get_current_for_display();
        Selection current_sel = controller.get_current_selection();
        
        for (int x = 0; x < COLS; x++) {
            for (int y = 0; y < ROWS; y++) {
            	Coord current = new Coord(x, y);
                if (cells.contains(current)) {
                	gc.setFill(Color.WHITE);
                	
                	if (
            			current_sel != null &&
            			current_sel.contains(current)
        			) {
                		gc.setFill(Color.LIME);
                	}
                	
                    gc.fillRect(
                		x * CELL_SIZE,
                		y * CELL_SIZE,
                		CELL_SIZE - 1,
                		CELL_SIZE - 1
            		);
                }
            }
        }
        
        if (current_sel != null) {
        	draw_selection(gc);
        }
        
    }
    
    private void draw_selection(GraphicsContext gc) {
    	Selection current_sel = controller.get_current_selection();
    	if (draw_selection_box) {
	    	gc.setStroke(Color.LIME);
	    	gc.setLineWidth(2);
	    	gc.setLineDashes(2.5, 2.5);
	    	gc.strokeRect(
				current_sel.get_min_x() * CELL_SIZE,
				current_sel.get_min_y() * CELL_SIZE,
				current_sel.get_width() * CELL_SIZE + CELL_SIZE,
				current_sel.get_height() * CELL_SIZE + CELL_SIZE
			);
    	}
    }
    
    private void open_load_menu() {
    	Map<String, Board> shapes = controller.list_shapes();
    	
        ObservableList<String> items = FXCollections.observableArrayList(
            shapes.keySet()
        );

        // 2. Create the ListView and bind the list to it
        ListView<String> listView = new ListView<>(items);

        // 3. Setup the new Stage (Window)
        Stage newStage = new Stage();
        newStage.setTitle("Load Shape");

        // Listen for selection changes
        listView.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> {
            if (newValue != null) {
                controller.set_shape(shapes.get(newValue));
            	newStage.close();
            }
        });
        
        VBox layout = new VBox(listView);
        Scene scene = new Scene(layout, 200, 250);
        
        newStage.setScene(scene);
        newStage.show();
    	
    }

    public static void main(String[] args) {
        launch(args);
    }
}