import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import javax.xml.bind.annotation.XmlRootElement;

import org.json.XML;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;



public class prueba1 {
	
    private static final String pathJSON = "C:\\Users\\Usuario\\git\\proyectoIEI\\archivosDatos\\bibliotecas.json";
    private static final String pathXML = "/Prueba/biblioteques.xml";
    
    
    
    public static void main(String[] args) throws Exception {
        
        conexionBD conexion = new conexionBD();
    	Connection cn = null;
    	Statement stm = null;
    	ResultSet rs = null;
    	
    	XMLToJSON();
    	
    	
    	
    	try {
            JSONParser parserJSON = new JSONParser();
            Object objJSON = parserJSON.parse(new FileReader(pathJSON));
            Object objXML = parserJSON.parse(new FileReader(pathXML));;
            JSONArray json = (JSONArray) objJSON;
            JSONArray xml = (JSONArray) objXML;
            
            cn = conexion.conectar();
            stm = cn.createStatement();
            PreparedStatement ps;

            for (int i = 0; i < json.size(); i++) {
            	JSONObject object =(JSONObject) json.get(i);
            	
            	String st_ins = "INSERT INTO biblioteca(nombre,tipo,direccion,codPostal,longitud,latitud,telefono,email,descripción)"
                 		+ " VALUES (?,?,?,?,?,?,?,?,?)";
            	
            	ps = cn.prepareStatement(st_ins);
            	
            	
            	
            	
            	String nombre = object.get("documentName").toString();  
            	String tipo = "publico";  
            	String direccion = object.get("address").toString();
            	String codPostal = object.get("postalcode").toString().replace(".","");
            	Float longitud = Float.valueOf(object.get("lonwgs84").toString());
            	Float latitud = Float.valueOf(object.get("latwgs84").toString());
            	String telefono = object.get("phone").toString();
            	String email = object.get("email").toString();	
            	String descripcion = object.get("documentDescription").toString();
            	
            	ps.setString(1,nombre);
            	ps.setString(2,tipo);
            	ps.setString(3,direccion);
            	ps.setString(4,codPostal);
            	ps.setFloat(5,longitud);
            	ps.setFloat(6,latitud);
            	ps.setString(7,telefono);
            	ps.setString(8,email);
            	ps.setString(9,descripcion);
            	
            	ps.executeUpdate();
            }
            
       	}catch (Exception ex) {
                ex.printStackTrace();
        }finally{
            	try {
            		if(cn != null) {
            		cn.close();
            		}
            	}catch(Exception e) {}
        }
    }



	private static void XMLToJSON() {
		int PRETTY_PRINT_INDENT_FACTOR = 4;
		String xmlString = null;
		try {
			xmlString = new String(Files.readAllBytes(Paths.get(pathXML)));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		org.json.JSONObject xmlJSONObj = null;
		xmlJSONObj = XML.toJSONObject(xmlString);
		String jsonFile = "c:\\datos\\CAT\\biblioteques.json";
		String jsonPrettyPrintString = null;
		try (FileWriter fileWriter = new FileWriter(jsonFile)){
		fileWriter.write(xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR));
		jsonPrettyPrintString= xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
		System.out.println(jsonPrettyPrintString);
		} catch (IOException e) {
			
			e.printStackTrace();
		}}
		
	}



	
