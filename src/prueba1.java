import java.io.FileNotFoundException;
import java.io.FileReader;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;

public class prueba1 {
    
    private static final String JSONpath = "C:\\Users\\Usuario\\git\\proyectoIEI\\archivosDatos\\bibliotecas.json";

    private static void ReadJSON(){

    	try {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(JSONpath));
        JSONArray json = (JSONArray) obj;

        for (int i = 0; i < json.size(); i++) {

        JSONObject object =(JSONObject) json.get(i);
        //ejemplos que hay q sustituir por nuestras columnas
        String clave = object.get("documentName").toString();  
        String title = object.get("placename").toString();  
        System.err.println("nnn:"+clave);
        System.err.println("lugar:"+title);}
    	}catch (Exception ex) {
            System.out.println("Exception: "+ ex.getMessage());
        }
  }
    
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        ReadJSON();
    }
}