import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.XML;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
public class XMLtoJSON {
	private static final String JSONpath2 = "C:\\Users\\Usuario\\git\\proyectoIEI\\archivosDatos\\bibliotequesNEW.json";
	public static void main(String[] args) throws IOException, JSONException {
		// TODO Auto-generated method stub
		int PRETTY_PRINT_INDENT_FACTOR = 4;
		String xmlString = null;
		String xmlFile = "C:\\Users\\Usuario\\git\\proyectoIEI\\archivosDatos\\biblioteques (2).xml";
		xmlString = new String(Files.readAllBytes(Paths.get(xmlFile)));
		org.json.JSONObject xmlJSONObj = null;
		xmlJSONObj = XML.toJSONObject(xmlString);
		String jsonFile = "C:\\Users\\Usuario\\git\\proyectoIEI\\archivosDatos\\bibliotequesNEW.json";
		String jsonPrettyPrintString = null;
		try (FileWriter fileWriter = new FileWriter(jsonFile)){
			fileWriter.write(xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR));
			jsonPrettyPrintString= xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
			System.out.println(jsonPrettyPrintString);
		}
		
		 conexionBD conexion = new conexionBD();
		 Connection cn = null;
		 Statement stm = null;
	     ResultSet rs = null;
	     
		 try {
	            JSONParser parser = new JSONParser();
	            Object obj = parser.parse(new FileReader(JSONpath2));
	            JSONArray json = new JSONArray();
	            json.add(obj);
	            
	            cn = conexion.conectar();
	            stm = cn.createStatement();
	            PreparedStatement ps;

	            for (int i = 0; i < json.size(); i++) {
	            	JSONObject object = (JSONObject) json.get(i);
	            	String st_ins = "INSERT INTO biblioteca(nombre,tipo,direccion,codPostal,longitud,latitud,telefono,email,descripción)"
	                 		+ " VALUES (?,?,?,?,?,?,?,?,?)";
	            	ps = cn.prepareStatement(st_ins);
	            	System.out.println(object.keySet());
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
}