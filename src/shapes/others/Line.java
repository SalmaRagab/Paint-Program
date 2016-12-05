package shapes.others;
import java.awt.Point;

import shapes.Shape;
import javafx.scene.canvas.GraphicsContext;

public class Line extends Shape {

	public Line() {
		setNumberOfPoints(2);
		setxVertices(new double[2]);
		setyVertices(new double[2]);
	}

	@Override
	public void setPoints() {


		getxVertices()[0] = getStartPoint().getX();
		getxVertices()[1] = getEndPoint().getX();

		getyVertices()[0] = getStartPoint().getY();
		getyVertices()[1] = getEndPoint().getY();

	}

	@Override
	public void draw(GraphicsContext gc) {
		setPoints();
		gc.setLineWidth(4);
		gc.setStroke(getBorderColor());
		gc.strokeLine(getStartPoint().getX(), getStartPoint().getY(), getEndPoint().getX(), getEndPoint().getY());
	}


	@Override
	public boolean contains(Point point) {

		double startX = getStartPoint().getX();
		double startY= getStartPoint().getY();
		double endX = getEndPoint().getX();
		double endY = getEndPoint().getY();
		double pointX = point.getX();
		double pointY = point.getY();

		double lineLength = Math.sqrt(Math.pow((startX-endX),(double) 2)+ Math.pow((startY-endY),(double) 2));
		double length1 = Math.sqrt(Math.pow((startX-pointX),(double) 2)+ Math.pow((startY-pointY),(double) 2));
		double length2 = Math.sqrt(Math.pow((endX-pointX),(double) 2)+ Math.pow((endY-pointY),(double) 2));
		if (Math.abs(length1 + length2 - lineLength) < 1) {
			return true;
		} else {
			return false;
		}
	}

}
