package operations;

import java.util.ArrayList;
import java.util.List;
import java.awt.Point;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import program.Gui;
import program.GuiController;
import shapes.Shape;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;



public class File implements FileIF {

	private String fileName;
	private List<Shape> savedList;
	Gui gui;
	GuiController controller;


	public File(GuiController controller) {
		this.controller = controller;
		this.gui = controller.gui;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public void setSavedList(List<Shape> savedList) {
		this.savedList = savedList;
	}
	public String getFileName() {
		return fileName;
	}
	public List<Shape> getSavedList() {
		return savedList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public void saveJson(String path) {
		JSONObject obj;
		JSONArray array;
		array = new JSONArray();

		for (Shape saveShape : savedList) {
			obj = new JSONObject();
			obj.put("class", saveShape.getClass().toString());
			obj.put("start point", saveShape.getStartPoint().toString());
			obj.put("end point", saveShape.getEndPoint().toString());
			obj.put("border color", saveShape.getBorderColor().toString());
			obj.put("fill color", saveShape.getFillColor().toString());
			obj.put("is filled", saveShape.isFilled());

			array.add(obj);
		}

		try {

			obj = new JSONObject();
			obj.put("array", array);
			FileWriter file = new FileWriter(path);
			file.write(obj.toJSONString());
			file.flush();
			file.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void saveXml(String path) {
		XStream xstream = new XStream(new DomDriver());
		String xml = xstream.toXML(savedList);

		try {
			FileWriter file = new FileWriter(path);
			file.write(xml);
			file.flush();
			file.close();
		} catch (Exception e) {
		}

	}

	@Override
	public List<Shape> loadJson(String path) {
		savedList = new ArrayList<>();
		JSONParser parser = new JSONParser();
		JSONArray array = new JSONArray();

		try {
			FileReader reader = new  FileReader(path);
			Object file = parser.parse(reader);
			JSONObject obj = (JSONObject) file;
			int j = 0;
			array = (JSONArray) obj.get("array");
			savedList= new ArrayList<>();
			Shape temp = new Shape();
			JSONObject tempObj = new JSONObject();
			tempObj = (JSONObject) array.get(0);
			temp.setStartPoint(toPoint(tempObj.get("start point").toString()));
			temp.setEndPoint(toPoint(tempObj.get("end point").toString()));

			while (j < array.size()) {

				tempObj = (JSONObject) array.get(j++);
				try {
					Class newClass = Class.forName((tempObj.get("class").toString()).replace("class ", ""));
					temp = (Shape) newClass.newInstance();

				} catch (Exception e) {
					for (Class tempClass : controller.loadedClasses) {
						if (tempClass.getName().equals((tempObj.get("class").toString()).replace("class ", ""))) {
							temp = (Shape)tempClass.newInstance();
						}
					}
				}
//				Class newClass = Class.forName((tempObj.get("class").toString()).replace("class ", ""));
//				temp = (Shape) newClass.newInstance();
				temp.setStartPoint(toPoint(tempObj.get("start point").toString()));
				temp.setEndPoint(toPoint(tempObj.get("end point").toString()));
				temp.setBorderColor(Color.web(tempObj.get("border color").toString()));
				temp.setFillColor(Color.web(tempObj.get("fill color").toString()));
				temp.setFilled(Boolean.parseBoolean((tempObj.get("is filled").toString())));
				savedList.add(temp);

			}



			return savedList;

		} catch (Exception e) {

		}

		return null;
	}

	@Override
	public List<Shape> loadXml(String path) {
		Object read = null;
		try {
			XStream xstream = new XStream(new DomDriver());
			xstream.processAnnotations(ArrayList.class);
			read = xstream.fromXML(new java.io.File(path));
			savedList = (List<Shape>) read;

			return savedList;
		} catch (Exception e) {
			try {
				read = tryXml(0);
			} catch (Exception e2) {
//				classes not loaded
			}
		}

		return savedList;


	}

	public Object tryXml(int i){
		Class cl;
		Shape read;
		try {
			cl = controller.loadedClasses.get(i);
			read = (Shape) cl.newInstance();
			return read;

		} catch (Exception e) {
			if ( i > controller.loadedClasses.size()){
				throw new RuntimeException();
			}
			tryXml(i++);
		}
		return null;

	}

	public void fileExplorerSave(){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choose saving location");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("JSON Files", "*.JSon"),new ExtensionFilter("XML Files", "*.XML"));
		java.io.File file1 = fileChooser.showSaveDialog(gui.primaryStage);
		String fileExtension = "";
		if (file1 != null) {
			String fileName = file1.getName();
			fileExtension = fileName.substring(fileName.indexOf(".") + 1, file1.getName().length());
		}
		String path = file1.getAbsolutePath();
		if (fileExtension.equals("JSon")) {
			saveJson(path);
		} else if (fileExtension.equals("XML")) {
			saveXml(path);
		}
	}
	 public void fileExplorerLoad() {
		 FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Choose file location");

			//Set extension filter
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("JSON Files", "*.JSon"),
					new ExtensionFilter("XML Files", "*.XML"));

			//Show save file dialog
			java.io.File chosenFile = fileChooser.showOpenDialog(gui.primaryStage);
			String fileExtension = "";
			String path = "";
			if (chosenFile != null) {
				String fileName = chosenFile.getName();
				fileExtension = fileName.substring(fileName.indexOf(".") + 1, chosenFile.getName().length());
				path = chosenFile.getAbsolutePath();
			}

			setSavedList(controller.shapesList);
			if (fileExtension.equals("JSon")) {
				controller.shapesList = loadJson(path);
			} else if (fileExtension.equals("XML")) {
				controller.shapesList = loadXml(path);
			}
	 }

	private Point toPoint(String point) {
		Character c;
		int i=0;
		int x;
		int y;
		String xstring = "";
		String yString = "";
		c = point.charAt(i);
		while (!c.equals('[')){
			i++;
			c = point.charAt(i);
		}
		i++;
		i++;
		i++;
		c = point.charAt(i);
		while (!c.equals(',')){
			xstring = xstring + c;
			i++;
			c = point.charAt(i);

		}

		x = Integer.parseInt(xstring);
		i++;
		i++;
		i++;
		c = point.charAt(i);
		while (!c.equals(']')){
			yString = yString + c;
			i++;
			c = point.charAt(i);
		}
		y = Integer.parseInt(yString);

		Point newPoint = new Point(x, y);
		return newPoint;

	}


	@Override
	public Class classLoader() {
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Choose class location");

			//Set extension filter
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("JAR Files", "*.JAR"));

			java.io.File chosenFile = fileChooser.showOpenDialog(gui.primaryStage);
			String path = "";
			String fileName = "";
			if (chosenFile != null) {
				fileName = chosenFile.getName();
				path = chosenFile.getAbsolutePath();
			}
			String newFileName = "";
			fileName = fileName.substring(0, fileName.length() - 4);
			if (fileName.equals("Triangle")) {
				newFileName = "shapes.polygons." + fileName;

			} else if (fileName.equals("Circle")) {
				newFileName = "shapes.ellipticalShapes." + fileName;
			}

			URLClassLoader urlClassLoader = null;
			urlClassLoader = URLClassLoader.newInstance(new URL[] {new URL("file:" + path)});
			Class<?> cl = null;
			cl = urlClassLoader.loadClass(newFileName);

			return cl;
		} catch (Exception e) {

		}
		return null;

	}







}
