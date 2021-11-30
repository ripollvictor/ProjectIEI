import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
public class XMLtoJSON {
	public static void main(String[] args) throws IOException, JSONException {
		// TODO Auto-generated method stub
		int PRETTY_PRINT_INDENT_FACTOR = 4;
		String xmlString = null;
		String xmlFile = "C:\\Users\\Usuario\\git\\proyectoIEI\\archivosDatos\\biblioteques (2).xml";
		xmlString = new String(Files.readAllBytes(Paths.get(xmlFile)));
		JSONObject xmlJSONObj = null;
		xmlJSONObj = XML.toJSONObject(xmlString);
		String jsonFile = "C:\\Users\\Usuario\\git\\proyectoIEI\\archivosDatos\\bibliotequesNEW.json";
		String jsonPrettyPrintString = null;
		try (FileWriter fileWriter = new FileWriter(jsonFile)){
			fileWriter.write(xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR));
			jsonPrettyPrintString= xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
			System.out.println(jsonPrettyPrintString);
		}
		} 

}