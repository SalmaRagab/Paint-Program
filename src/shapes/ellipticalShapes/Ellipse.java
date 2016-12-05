package shapes.ellipticalShapes;
import shapes.polygons.Rectangle;
import javafx.scene.canvas.GraphicsContext;

public class Ellipse extends EllipticalShapes {

	@Override
	public void draw(GraphicsContext gc) {
		helpingRec();
		super.draw(gc);
	}
	
	public void helpingRec() {
		drawingRec = new Rectangle();
		drawingRec.setStartPoint(getStartPoint());
		drawingRec.setEndPoint(getEndPoint());
	}

}
