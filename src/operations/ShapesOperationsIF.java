package operations;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;


public interface ShapesOperationsIF {

	public void undo ();

	public void redo ();

	public void move ();

	public void delete();

	public void resize();

	public void select(GraphicsContext gc);

}
