package operations;
import java.awt.Point;
import java.util.List;
import shapes.Shape;
import shapes.ellipticalShapes.EllipticalShapes;
import shapes.polygons.Polygons;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class ShapesOperations implements ShapesOperationsIF {

	private Shape shape;
	private List<Shape> shapesList;
	private int index;
	private Point moveStartPoint;
	private Point moveEndPoint;
	private int vertexIndex;

	public void setMoveStartPoint(Point moveStartPoint) {
		this.moveStartPoint = moveStartPoint;
	}
	public void setMoveEndPoint(Point moveEndPoint) {
		this.moveEndPoint = moveEndPoint;
	}
	public Point getMoveStartPoint() {
		return moveStartPoint;
	}
	public Point getMoveEndPoint() {
		return moveEndPoint;
	}

	public ShapesOperations(Shape shape ,List<Shape> shapeslist, int index) {
		setShape(shape);
		setShapesList(shapeslist);
		setIndex(index);
	}
	public void setShape(Shape shape) {
		this.shape = shape;
	}
	public void setShapesList(List<Shape> shapesList) {
		this.shapesList = shapesList;
	}
	public void setIndex(int index) {
		this.index = index;
	}

	public Shape getShape() {
		return shape;
	}
	public List<Shape> getShapesList() {
		return shapesList;
	}
	public int getIndex() {
		return index;
	}

	@Override
	public void select(GraphicsContext gc) {
	    gc.setStroke(Color.SKYBLUE);
	    gc.setGlobalAlpha(0.9);

		if (shape.getNumberOfPoints() != 2) { // not line
			gc.setLineDashes(10);
			Polygons p = new Polygons();
			p.setNumberOfPoints(shape.getNumberOfPoints());
			p.setxPoints(shape.getxVertices());
			p.setyPoints(shape.getyVertices());
			p.draw(gc);
		}

		gc.setLineDashes(null);



		int i = 0;
		for (i = 0; i<shape.getNumberOfPoints(); i++) {
			gc.setLineWidth(5);
			gc.strokeOval(shape.getxVertices()[i]-3,shape.getyVertices()[i]-3, 5, 5);
		}
	}


	@Override
	public void move() {
		//distances from start and end points.
		int distanceStartx;
		int distanceStarty;
		int distanceEndx;
		int distanceEndy;

		distanceStartx = (int) shape.getStartPoint().getX() - (int) moveStartPoint.getX();
		distanceStarty = (int) moveStartPoint.getY() - (int) shape.getStartPoint().getY();
		distanceEndx = (int) shape.getEndPoint().getX() - (int) moveStartPoint.getX();
		distanceEndy = (int) moveStartPoint.getY() - (int) shape.getEndPoint().getY();


		int newStartx = distanceStartx + (int) moveEndPoint.getX();
		int newStarty = (int) moveEndPoint.getY() - distanceStarty;
		int newEndx = distanceEndx + (int) moveEndPoint.getX();
		int newEndy =  (int) moveEndPoint.getY() - distanceEndy;

		shape.setStartPoint(new Point(newStartx, newStarty));
		shape.setEndPoint(new Point(newEndx, newEndy));


	}

	@Override
	public void delete() {
		shapesList.remove(index);
	}

	@Override
	public void resize() {

		Point newStartPoint = new Point();
		int newStartIndex = 0;


			if (shape.getNumberOfPoints() == 3) { //triangle
			if (vertexIndex == 0) {
				newStartPoint = moveEndPoint;
				moveEndPoint  = shape.getEndPoint();
			} else{
				newStartPoint = shape.getStartPoint();
				moveEndPoint  = moveEndPoint;
			}
		} else {
			if (shape.getNumberOfPoints() == 2) { // line
				if (vertexIndex == 0) {
					newStartIndex = 1;
				} else if (vertexIndex == 1) {
					newStartIndex = 0;
				}
			} else {
				newStartIndex = vertexIndex + 2;
				shape.setEndPoint(moveEndPoint);
				if (newStartIndex >= shape.getxVertices().length) {
					newStartIndex = newStartIndex - shape.getxVertices().length;
				}
			}
			newStartPoint.x = (int) shape.getxVertices()[newStartIndex];
			newStartPoint.y = (int) shape.getyVertices()[newStartIndex];

		}
		shape.setStartPoint(newStartPoint);
		shape.setEndPoint(moveEndPoint);

		if (shape instanceof EllipticalShapes) {
			EllipticalShapes newShape = new EllipticalShapes();
			newShape = (EllipticalShapes) shape;
			newShape.checkDirection();
			shape.setStartPoint(newShape.getStartPoint());
			shape.setEndPoint(newShape.getEndPoint());

		}
	}
	@Override
	public void undo() {

	}
	@Override
	public void redo() {

	}

	public boolean isVertex() {
		for (int i = 0; i < shape.getxVertices().length; i++) {
			if ((Math.abs(shape.getxVertices()[i] - moveStartPoint.getX()) < 20) && ((Math.abs(shape.getyVertices()[i] - moveStartPoint.getY()) < 20))) {
				vertexIndex = i;
				return true;
			}
		}
		return false;
	}

}