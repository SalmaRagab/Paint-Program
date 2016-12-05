package program;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import operations.File;
import operations.ShapesOperations;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import shapes.Shape;
import shapes.ellipticalShapes.Ellipse;
import shapes.others.Line;
import shapes.polygons.Rectangle;
import shapes.polygons.Square;

public class GuiController {


	 GraphicsContext gc;
	 Shape shape;
	 int shapeIndex;
	 Shape tempShape = new Shape();
	 Point startPoint;
	 Point endPoint;
	 ShapesOperations operations;
	 File file;
	 @SuppressWarnings("rawtypes")
	 Class<?> loadedClass;
	 String className;
	 public List<Class> loadedClasses = new ArrayList<>();

	 public List<Shape> shapesList= new ArrayList<Shape>();
	 Stack<List<Shape>> undoStack = new Stack<List<Shape>>();
	 Stack<List<Shape>> redoStack = new Stack<List<Shape>>();

	 boolean linePressed, ellipsePressed, addedBtnPressed,
	 rectanglepressed, squarePressed, fillPressed,startDrawing,
	 shapeSelected, dragged  = false;

	 EventHandler<MouseEvent> clickHandler, dragHandler, releaseHandler;
	 EventHandler<KeyEvent> canvasBackspaceHandler;

	 Color borderColor = Color.BLACK;
	 Color fillColor =Color.BLACK ;
	 Color BackGroundColor = Color.WHITE ;
	 public Gui gui;


	 public GuiController(Gui gui){
		    this.gui = gui;
		    file = new File(this);
			try {
				undoStack.push(CloneList(shapesList));
			} catch (Exception e) {

			}

		 	gc = gui.canvas.getGraphicsContext2D();
		 	gc.setFill(BackGroundColor);
		 	gc.fillRect(0, 0, gui.canvas.getWidth(), gui.canvas.getHeight());
	 }

	 void canvasClickHandler() {
		clickHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				redoStack = new Stack<List<Shape>>();
				reDraw();
				startPoint = new Point((int) event.getX(), (int) event.getY());
				endPoint = new Point((int) event.getX(), (int) event.getY());
				autoCreate();
				if (shapeSelected) {
					if (fillPressed) {
						shape.setFillColor(fillColor);
						shape.setFilled(true);
						shape.setToFill(false);
						reDraw();
						fillPressed = false;
						shapeSelected = false;

						try {

							undoStack.push(CloneList(shapesList));

						} catch (Exception e) {

						}

					} else {
						operations.setMoveStartPoint(startPoint);
						operations.select(gc);
					}
				} else if (startDrawing) {
					shape.setStartPoint(startPoint);
					shape.setEndPoint(endPoint);
					shape.setBorderColor(borderColor);
					shape.startDraw(gc);
				} else {

				}

			}
		};
	}

	 void canvasDragHandler() {
		dragHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				endPoint = new Point((int) event.getX(), (int) event.getY());
				if (shapeSelected) {
					startDrawing = false;
					dragged = true;
					operations.setMoveEndPoint(endPoint);
					if (operations.isVertex()) {
						operations.resize();
					} else {
						operations.move();
					}
					reDraw();
					operations.select(gc);
					operations.setMoveStartPoint(endPoint);
				} else if (startDrawing) {
					dragged = true;
					reDraw();
					shape.setEndPoint(endPoint);
					shape.draw(gc);
				}

			}
		};
	}

	 void canvasReleaseHandler() {
		releaseHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (dragged) {
					if (startDrawing) {
						shapesList.add(shape);
					}
					try {
						undoStack.push(CloneList(shapesList));
					} catch (Exception e) {
					}
				}
					dragged = false;
			}
		};
	}

	 void canvasKeyHandler() {
		canvasBackspaceHandler = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				if (shapeSelected) {
				if ((key.getCode() == KeyCode.BACK_SPACE) || (key.getCode() == KeyCode.DELETE)) {
					operations.delete();
					shapeSelected = false;
					reDraw();
					try {
						undoStack.push(CloneList(shapesList));
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
					}
			}
			}
		};
	}


		 void setBtnsFalse() {
			linePressed = false;
			ellipsePressed = false;
			rectanglepressed = false;
			squarePressed = false;
			fillPressed = false;
			addedBtnPressed = false;
		}

		void reDraw() {
				clear();
				for (Shape drawShape : shapesList) {
					drawShape.draw(gc);
				}

			}

		Shape contains(Point point) {
			for (Shape searchShape : shapesList) {
				if (fillPressed) {
					searchShape.setToFill(true);
				}
				if (searchShape.contains(point)) {
					shapeIndex = shapesList.indexOf(searchShape);
					return searchShape;
				}
			}
			return null;
		}

		void autoCreate() {
			if ((tempShape = contains(startPoint)) != null) {
				shape = tempShape;
				operations = new ShapesOperations(shape , shapesList, shapeIndex);
				shapeSelected = true;
			} else if (linePressed) {

				shapeSelected = false;
				startDrawing = true;
				shape = new Line();

			} else if (ellipsePressed) {
				shapeSelected = false;
				startDrawing = true;
				shape = new Ellipse();

			} else if (rectanglepressed) {
				shapeSelected = false;
				startDrawing = true;
				shape = new Rectangle();

			} else if (squarePressed) {
				shapeSelected = false;
				startDrawing = true;
				shape = new Square();

			} else if (addedBtnPressed) {

				shapeSelected = false;
				startDrawing = true;
//				Class<?> cl;
				try {
					for (Class tempClass : loadedClasses) {
						if (tempClass.getName().equals(className)) {
							shape = (Shape)tempClass.newInstance();
						}
					}
//					cl = Class.forName(packageName);



				} catch (Exception e) {
				}


			}
		}

		void handler(ActionEvent event) {
			if (event.getSource() == gui.newBtn) {
				clearCanvas();
			} else if (event.getSource() == gui.deleteBtn) {
				if (shapeSelected) {
					operations.delete();
					shapeSelected = false;
					reDraw();
					try {
						undoStack.push(CloneList(shapesList));
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
			}
				}else if (event.getSource() == gui.fillBtn) {
				fillPressed = true;
			} else if (event.getSource() == gui.colorPicker) {
				colorValue();
			}else if (event.getSource() == gui.undoBtn) {
				undo();
			} else if (event.getSource() == gui.redoBtn) {
				redo();
			} else if(event.getSource() == gui.saveBtn) {
				file.setSavedList(shapesList);
				file.fileExplorerSave();
			} else if (event.getSource() == gui.loadBtn) {
				file.fileExplorerLoad();
				reDraw();
			} else if (event.getSource() == gui.extensionBtn) {
				loadedClass = file.classLoader();
				loadedClasses.add(loadedClass);
				gui.adjustAdderButton(loadedClass);

			} else {
				setBtnsFalse();
				if (event.getSource() == gui.lineBtn) {
					linePressed = true;
				} else if (event.getSource() == gui.ellipseBtn) {
					ellipsePressed = true;
				} else if (event.getSource() == gui.rectangleBtn) {
					rectanglepressed = true;
				} else if (event.getSource() == gui.squareBtn) {
					squarePressed = true;
				} else  {
					addedBtnPressed = true;
					className = ((Node) event.getSource()).getId();
				}
			}
		}

		private List<Shape> CloneList(List<Shape> oldList) throws InstantiationException, IllegalAccessException {
			List<Shape> tempList = new ArrayList<Shape>();
			Shape newShape;
			Class<? extends Shape> newClass;
			for (Shape oldShape : oldList) {
				newClass = oldShape.getClass();
				newShape = (Shape) newClass.newInstance();
				newShape.setStartPoint(oldShape.getStartPoint());
				newShape.setEndPoint(oldShape.getEndPoint());
				newShape.setBorderColor(oldShape.getBorderColor());
				newShape.setFilled(oldShape.isFilled());
				newShape.setToFill(oldShape.isToFill());
				newShape.setFillColor(oldShape.getFillColor());
				tempList.add(newShape);
			}
			return tempList;
		}

		private void clearCanvas() {
			try {
				undoStack.push(CloneList(shapesList));
			} catch (Exception e) {
			}
			gc.clearRect(0, 0, gui.canvas.getWidth(), gui.canvas.getHeight());
		 	gc.setFill(BackGroundColor);
		 	gc.fillRect(0, 0, gui.canvas.getWidth(), gui.canvas.getHeight());
			shapesList = new ArrayList<Shape>();
			autoCreate();
		}

		void clear() {
			gc.clearRect(0, 0, gui.canvas.getWidth(), gui.canvas.getHeight());
		 	gc.setFill(BackGroundColor);
		 	gc.fillRect(0, 0, gui.canvas.getWidth(), gui.canvas.getHeight());
		}
		private void colorValue() {
				fillColor = gui.colorPicker.getValue();
				borderColor = gui.colorPicker.getValue();

		}

		private void undo() {
			if(undoStack.size() > 1) {
				shapeSelected = false;
				redoStack.push(undoStack.pop());
				try {
					shapesList = CloneList(undoStack.peek());
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				reDraw();
			}
		}

		private void redo() {
			if(redoStack.size() != 0) {
				shapeSelected = false;
				undoStack.push(redoStack.pop());
				try {
					shapesList = CloneList(undoStack.peek());
				} catch (Exception e) {
					}
				}
				reDraw();
			}

}
