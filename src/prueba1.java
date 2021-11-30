import java.io.FileReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
            	//biblioteca
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
            	//localidad
            	String nombreL = object.get("municipality").toString(); 
            	String cod = object.get("postalcode").toString().replace(".","").substring(2);
            	String st_ins2 = "INSERT INTO localidad(nombre,codigo)"
                 		+ " VALUES (?,?)";
            	ps = cn.prepareStatement(st_ins2);
            	ps.setString(1,nombreL);
            	ps.setString(2,cod);
            	ps.executeUpdate();
            	//municipio
            	String nombreM = object.get("territory").toString(); 
            	String codM = object.get("postalcode").toString().replace(".","").substring(0,2);
            	String st_ins3 = "INSERT INTO provincia(nombre,codigo)"
                 		+ " VALUES (?,?)";
            	ps = cn.prepareStatement(st_ins3);
            	ps.setString(1,nombreM);
            	ps.setString(2,codM);
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
    	System.out.println("OK");
    }
    
}