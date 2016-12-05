package operations;
import java.util.List;

import shapes.Shape;


public interface FileIF {

	/**
	 * Saves into a .JSon file.
	 * @param path The path to save the file in
	 */
	public void saveJson(String path);

	/**
	 * Saves into a .xml file.
	 * @param path The path to save the file in
	 */

	public void saveXml(String path);

	/**
	 * Loads a .JSon file.
	 * @param path The path of the file to be loaded
	 * @return A list of Shapes to be read and translated into drawings
	 */
	public List<Shape> loadJson(String path);

	/**
	 *Loads a .xml file.
	 * @param path The path of the file to be loaded
	 * @return A list of Shapes to be read and translated into drawings
	 */
	public List<Shape> loadXml(String path);

	/**
	 * The classloader method.
	 * @return A class that is loaded
	 */
	public Class classLoader();

}
