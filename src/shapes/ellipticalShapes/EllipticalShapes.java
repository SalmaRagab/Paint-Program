package shapes.ellipticalShapes;
import java.awt.Point;
import shapes.Shape;
import shapes.polygons.Rectangle;
import javafx.scene.canvas.GraphicsContext;

public class EllipticalShapes extends Shape {

	public Rectangle drawingRec;
	/**
	 * The ellipse's major axis
	 * from the center to the boundary line.
	 */
	public double a;
	/**
	 * The ellipse's minor axis
	 * from the center to the boundary line.
	 */
	public double b;

	public void draw(GraphicsContext gc) {
		Point startPointTemp = getStartPoint();
		drawingRec.setPoints();
		setxVertices(drawingRec.getxVertices());
		setyVertices(drawingRec.getyVertices());
		setNumberOfPoints(4);
		a = drawingRec.getLength();
		b = drawingRec.getWidth();
		int startIndex = checkDirection();
		setStartPoint(new Point((int) getxVertices()[startIndex] ,(int) getyVertices()[startIndex]));
		gc.setLineWidth(3);
		gc.setStroke(getBorderColor());
		
		
		if (isFilled()) {
			gc.setFill(getFillColor());
			gc.fillOval(getStartPoint().getX(), getStartPoint().getY(), b, a);
		}
		gc.strokeOval(getStartPoint().getX(), getStartPoint().getY(), b, a);
		setStartPoint(startPointTemp);
	}

	@Override
	public boolean contains(Point point) {
		double xResult = Math.pow(((point.getX() - drawingRec.getCenter().getX())/ b), 2);
		double yResult = Math.pow(((point.getY() - drawingRec.getCenter().getY())/ a), 2);

		if (isToFill() || isFilled()) {
			if ((Math.abs(xResult + yResult) < 0.35)) {
				return true;
			} else if (drawingRec.contains(point)) {
				return true;
			}
		}
		else {

			if ((Math.abs(xResult + yResult) < 0.35) && (Math.abs(xResult + yResult) > 0.18)) {
				return true;
			} else if (drawingRec.contains(point)) {
				return true;
			}
		}

		return false;
	}
	
	
	public int checkDirection() {
		double minx;
		double miny;
		int index = 0;
		int startIndex = 0;
		boolean finished = false;
		minx = getxVertices()[0];
		miny = getyVertices()[0];
		int i = 0;
		int j = 0;
		for (i = 0; i < getxVertices().length && !finished; i++) {
			if (getxVertices()[i] <= minx) {
				minx = getxVertices()[i];
				index = i;
				for (j = 0; j < getyVertices().length; j++) {
					if (getyVertices()[i] <= miny) {
						if (j == index) {
							startIndex = index;
							break;
						}
					}
				}
			}
		}
		return startIndex;

	}
}
