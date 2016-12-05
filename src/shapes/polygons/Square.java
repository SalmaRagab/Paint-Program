package shapes.polygons;
import java.awt.Point;

import javafx.scene.canvas.GraphicsContext;

public class Square extends Rectangle {
	 private double xtemp[] = {0};
	 private double ytemp[] = {0};

	public Square() {
		setNumberOfPoints(4);
		setxVertices(new double[4]);
		setyVertices(new double[4]);
	}
	@Override
	public void setPoints() {
		xtemp = new double[4];
		ytemp = new double[4];
		double side = ((getEndPoint().getX()) - getStartPoint().getX());
		double newY = ((getStartPoint().getY()) + side);
		Point newEndPoint = new Point((int)getEndPoint().getX(), (int)newY);

		setEndPoint(newEndPoint);
		double side2 = ((getEndPoint().getY()) - getStartPoint().getY());

		if ((int)side != (int) side2) {
		}

		xtemp[0] = getStartPoint().getX();
		ytemp[0] = getStartPoint().getY();

		xtemp[1] = getEndPoint().getX();
		ytemp[1] = getStartPoint().getY();

		xtemp[2] = getEndPoint().getX();
		ytemp[2] = getEndPoint().getY();

		xtemp[3] = getStartPoint().getX();
		ytemp[3] = getStartPoint().getY() + side;

		setxVertices(xtemp);
		setyVertices(ytemp);

		setxPoints(getxVertices());
		setyPoints(getyVertices());
		calCenter();

	}
}
