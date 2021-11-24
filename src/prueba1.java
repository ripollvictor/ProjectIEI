import java.io.FileReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;



public class prueba1 {
	
    private static final String JSONpath = "C:\\Users\\Usuario\\git\\proyectoIEI\\archivosDatos\\bibliotecas.json";
    
    public static void main(String[] args) throws Exception {
        
        conexionBD conexion = new conexionBD();
    	Connection cn = null;
    	Statement stm = null;
    	ResultSet rs = null;
    	
    	try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(JSONpath));
            JSONArray json = (JSONArray) obj;
            
            cn = conexion.conectar();
            stm = cn.createStatement();
            PreparedStatement ps;

            for (int i = 0; i < json.size(); i++) {
            	JSONObject object =(JSONObject) json.get(i);
            	
            	String st_ins = "INSERT INTO biblioteca(nombre,tipo,direccion,codPostal,longitud,latitud,telefono,email,descripción)"
                 		+ " VALUES (?,?,?,?,?,?,?,?,?)";
            	
            	ps = cn.prepareStatement(st_ins);
            	
            	String nombre = "ssssssssssss"; //object.get("documentName").toString();  
            	String tipo = "publico";  
            	String direccion = object.get("address").toString();
            	String codPostal = object.get("postalcode").toString();
            	Float longitud = Float.valueOf(object.get("lonwgs84").toString());
            	Float latitud = Float.valueOf(object.get("latwgs84").toString());
            	Integer telefono = 11111111; //Integer.valueOf( object.get("phone").toString());
            	String email = object.get("email").toString();
            	String descripcion = "ssssssssssssssssss" ; //object.get("documentDescription").toString();
            	
            	ps.setString(1,nombre);
            	ps.setString(2,tipo);
            	ps.setString(3,direccion);
            	ps.setString(4,codPostal);
            	ps.setFloat(5,longitud);
            	ps.setFloat(6,latitud);
            	ps.setInt(7,telefono);
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