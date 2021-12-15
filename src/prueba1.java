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
	
    private static final String JSONpath = "C:\\Users\\Usuario\\git\\proyectoIEI\\archivosDatos\\EUS.json";
    
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
            	
            	//provincia
            	String nombreM = object.get("territory").toString().
            			replace("Ã©","é").replace("Ã³","ó").replace("Ã¡","á"); 
            	String codM = object.get("postalcode").toString().replace(".","").substring(0,2);
            	boolean existePK2 = false;
            	rs = stm.executeQuery ("SELECT codigo FROM provincia");
            	while (rs.next())
            	{
            	    String codBD2 =  (rs.getString("codigo"));
            	    if(codM.equals(codBD2)){existePK2 = true;}
            	}
            	
            	if(existePK2 == false) {
            	String st_ins3 = "INSERT INTO provincia(nombre,codigo)"
                 		+ " VALUES (?,?)";
            	ps = cn.prepareStatement(st_ins3);
            	ps.setString(1,nombreM);
            	ps.setString(2,codM);
            	ps.executeUpdate();
            	}
            	//localidad
            	String nombreL = object.get("municipality").toString().
            			replace("Ã©","é").replace("Ã³","ó").replace("Ã¡","á"); 
            	String cod = object.get("postalcode").toString().replace(".","").substring(2);
            	String codMfk = object.get("postalcode").toString().replace(".","").substring(0,2);
            	boolean existePK = false;
            	rs = stm.executeQuery ("SELECT codigo FROM localidad");
            	while (rs.next())
            	{
            	    String codBD =  (rs.getString("codigo"));
            	    if(cod.equals(codBD)){existePK = true;}
            	}
            	if (existePK == false) {
            	String st_ins2 = "INSERT INTO localidad(nombre,codigo,CodigoPro)"
                 		+ " VALUES (?,?,?)";
            	ps = cn.prepareStatement(st_ins2);
            	ps.setString(1,nombreL);
            	ps.setString(2,cod);
            	ps.setString(3,codMfk);
            	ps.executeUpdate();
            	}
            	//biblioteca
            	String st_ins = "INSERT INTO biblioteca(nombre,tipo,direccion,codPostal,longitud,"
            			+ "latitud,telefono,email,descripción,codigoLoc)"
                 		+ " VALUES (?,?,?,?,?,?,?,?,?,?)";
            	ps = cn.prepareStatement(st_ins);
            	String nombre = object.get("documentName").toString().
            			replace("Ã©","é").replace("Ã³","ó").replace("Ã¡","á").replace("Ã±","ñ");        
            	String tipo = "publico";  
            	String direccion2 = object.get("address").toString().
            			replace("Ã©","é").replace("Ã³","ó").replace("Ã¡","á");
            	String direccion = "C\\ " + direccion2.substring(0,direccion2.length()-2) ;
            	String codPostal = object.get("postalcode").toString().replace(".","").
            			replace("Ã©","é").replace("Ã³","ó").replace("Ã¡","á");          			
            	Float longitud = Float.valueOf(object.get("lonwgs84").toString());
            	Float latitud = Float.valueOf(object.get("latwgs84").toString());
            	String telefono = object.get("phone").toString().replace(" ","");
            	if (telefono.length() > 9) {telefono = telefono.substring(0,9);}
            	String email = object.get("email").toString().
            			replace("Ã©","é").replace("Ã³","ó").replace("Ã¡","á");	
            	String descripcion = object.get("documentDescription").toString().
            			replace("Ã©","é").replace("Ã³","ó").replace("Ã¡","á");
            	String codL = object.get("postalcode").toString().replace(".","").substring(2); 
            	ps.setString(1,nombre);
            	ps.setString(2,tipo);
            	ps.setString(3,direccion);
            	ps.setString(4,codPostal);
            	ps.setFloat(5,longitud);
            	ps.setFloat(6,latitud);
            	ps.setString(7,telefono);
            	ps.setString(8,email);
            	ps.setString(9,descripcion);
            	ps.setString(10,codL);
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