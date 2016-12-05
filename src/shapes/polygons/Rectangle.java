package shapes.polygons;
import java.awt.Point;


public class Rectangle extends Polygons {

	private double length;
	private double width;
	private double diagonal;
	private double xtemp[] = {0};
	private double ytemp[] = {0};

	public Rectangle() {
		setNumberOfPoints(4);
		setxVertices(new double[4]);
		setyVertices(new double[4]);

	}
	public void setDiagonal(double diagonal) {
		this.diagonal = diagonal;
	}

	public void setWidth(double width) {
		this.width = width;
	}
	public void setLength(double length) {
		this.length = length;
	}
	public double getDiagonal() {
		calDiagonal();
		return diagonal;
	}
	public double getWidth() {
		calWidth();
		return width;
	}
	public double getLength() {
		calLength();
		return length;
	}

	@Override
	public void setPoints() {
		xtemp = new double[4];
		ytemp = new double[4];
		xtemp[0] = getStartPoint().getX();
		ytemp[0] = getStartPoint().getY();

		xtemp[1] = getEndPoint().getX();
		ytemp[1] = getStartPoint().getY();

		xtemp[2] = getEndPoint().getX();
		ytemp[2] = getEndPoint().getY();

		xtemp[3] = getStartPoint().getX();
		ytemp[3] = getEndPoint().getY();
		
		setxVertices(xtemp);
		setyVertices(ytemp);

		setxPoints(getxVertices());
		setyPoints(getyVertices());

		calCenter();


	}

	private void calLength() {
		this.length = getStartPoint().distance(new Point((int)getxVertices()[3], (int)getyVertices()[3]));
	}

	private void calWidth() {
		this.width = getStartPoint().distance(new Point((int)getxVertices()[1], (int)getyVertices()[1]));
	}
	private void calDiagonal() {
		this.diagonal = getStartPoint().distance(getEndPoint());
	}


	@Override
	public void calCenter() {
		int pointX = (int) getxVertices()[0] + (int) (0.5 * width);
		int pointY = (int) getyVertices()[0] + (int) (0.5 * length);
		setCenter(new Point(pointX, pointY));
		}
}