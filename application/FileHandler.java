package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/*import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;*/

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class FileHandler {
	public void readFile() {
		//try {
			FileChooser fileChooser = new FileChooser();
	    	fileChooser.setTitle("Open JSON file");
	    	fileChooser.setInitialDirectory(new File("./"));
	    	fileChooser.getExtensionFilters().addAll(new ExtensionFilter("JSON Files", "*.JSON"));
	    	
	    	File file = fileChooser.showOpenDialog(null);
		
	    	/*Object obj = new JSONParser().parse(new FileReader(file));
			JSONObject jo = (JSONObject) obj;
			System.out.println(obj);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
	}
	
	public void saveFile() {
		
	}
}
