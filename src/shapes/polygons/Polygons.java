package shapes.polygons;
import java.awt.Point;

import shapes.Shape;
import shapes.others.Line;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Polygons extends Shape {



	public void setxPoints(double[] xVertices) {
		setxVertices(xVertices);
	}
	public void setyPoints(double[] yVertices) {
		setyVertices(yVertices);
	}


	public double[] getxPoints() {
		return getxVertices();
	}
	public double[] getyPoints() {
		return getyVertices();
	}

	@Override
	public boolean contains(Point point) {
		if (isToFill() || isFilled()) {

		  int i, j;
		  boolean c = false;
		  for (i = 0, j = getxVertices().length-1 ; i < getxVertices().length; j = i++) {
		    if ( ((getyVertices()[i]>point.getY()) != (getyVertices()[j]>point.getY())) &&
		     (point.getX() < (getxVertices()[j]-getxVertices()[i]) * (point.getX()-getyVertices()[i]) / (getyVertices()[j]-getyVertices()[i]) + getxVertices()[i]) )
		    	c = !c;
		  }
		  return c;
		}
		
		for (int i = 0; i < getNumberOfPoints() - 1; i++) {
			Line helpingLine = new Line();
			helpingLine.setStartPoint(new Point((int)getxVertices()[i], (int)getyVertices()[i]));
			if (i == 0) { // the zero node is linked with the second and the last node
				helpingLine.setEndPoint(new Point((int)getxVertices()[1], (int)getyVertices()[1]));
				if (helpingLine.contains(point)) {
					return true;
				} else {
					helpingLine.setEndPoint(new Point((int)getxVertices()[getNumberOfPoints() - 1], (int)getyVertices()[getNumberOfPoints() - 1]));
					if (helpingLine.contains(point)) {
						return true;
					}
				}
			} else {
				helpingLine.setEndPoint(new Point((int)getxVertices()[i + 1], (int)getyVertices()[i + 1]));
				if (helpingLine.contains(point)) {
					return true;
				}
			}
		}
		return false;
	}


	@Override
	public void draw(GraphicsContext gc) {
		setPoints();
		gc.setLineWidth(3);
		gc.setStroke(getBorderColor());
		if (isFilled()) {
			gc.setFill(getFillColor());
			gc.fillPolygon(getxVertices(), getyVertices(), getNumberOfPoints());
		}
		gc.strokePolygon(getxVertices(), getyVertices(), getNumberOfPoints());
	}
}