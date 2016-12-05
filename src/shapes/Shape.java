package shapes;
import java.awt.Point;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Shape implements ShapeIF {

	private Color borderColor;
	private Color fillColor = Color.WHITE;
	private float opacity;
	private double area;
	private Point center, startPoint, endPoint;

	private double[] xVertices;
	private double[] yVertices;

	private int numberOfPoints;
	
	private boolean filled = false;
	private boolean toFill = false;

	public void setCenter(Point center) {
		this.center = center;
	}
	public void setArea(double area) {
		this.area = area;
	}
	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	public void setNumberOfPoints(int numberOfPoints) {
		this.numberOfPoints = numberOfPoints;
	}

	public int getNumberOfPoints() {
		return numberOfPoints;
	}

	public Point getCenter() {
		calCenter();
		return center;
	}
	public double getArea() {
		return area;
	}

	public Color getBorderColor() {
		return borderColor;
	}
	public Color getFillColor() {
		return fillColor;
	}
	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}
	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
	}
	public Point getStartPoint() {
		return startPoint;
	}
	public Point getEndPoint() {
		return endPoint;
	}

	@Override
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void startDraw(GraphicsContext gc) {
		// TODO Auto-generated method stub

	}
	@Override
	public boolean contains(Point point) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void calCenter() {
		// TODO Auto-generated method stub

	}
	@Override
	public void setPoints() {
		// TODO Auto-generated method stub

	}
	public double[] getxVertices() {
		return xVertices;
	}
	public void setxVertices(double[] xVertices) {
		this.xVertices = xVertices;
	}
	public double[] getyVertices() {
		return yVertices;
	}
	public void setyVertices(double[] yVertices) {
		this.yVertices = yVertices;
	}
	public boolean isFilled() {
		return filled;
	}
	public void setFilled(boolean filled) {
		this.filled = filled;
	}
	public boolean isToFill() {
		return toFill;
	}
	public void setToFill(boolean toFill) {
		this.toFill = toFill;
	}


}
