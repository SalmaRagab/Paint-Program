package shapes;
import java.awt.Point;

import javafx.scene.canvas.GraphicsContext;

public interface ShapeIF {

	public void startDraw(GraphicsContext gc);

	public void draw(GraphicsContext gc);

	/**
	 * Checks if the point pressed by the user is
	 * contained by a shape or no.
	 * @param point The point pressed by the user
	 * @return True is it is contained by a shape
	 */
	boolean contains(Point point);

	/**
	 * Calculates the center of the shape.
	 */
	void calCenter();

	/**
	 * Sets the points of the shape to a data structure.
	 */
	public void setPoints();
}
