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
		String xmlFile = "C:\\Users\\Usuario\\git\\proyectoIEI\\archivosDatos\\CAT.xml";
		xmlString = new String(Files.readAllBytes(Paths.get(xmlFile)));
		org.json.JSONObject xmlJSONObj = null;
		xmlJSONObj = XML.toJSONObject(xmlString);
		String jsonFile = "C:\\Users\\Usuario\\git\\proyectoIEI\\archivosDatos\\bibliotequesNEW.json";
		String jsonPrettyPrintString = null;
		try (FileWriter fileWriter = new FileWriter(jsonFile)){
			fileWriter.write(xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR));
			jsonPrettyPrintString= xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
			//System.out.println(jsonPrettyPrintString);
		}
		
		 conexionBD conexion = new conexionBD();
		 Connection cn = null;
		 Statement stm = null;
	     ResultSet rs = null;
	     
		 try {
	            JSONParser parser = new JSONParser();
	            JSONObject objXML = (JSONObject)parser.parse(new FileReader(JSONpath2));
	            String response = "response";
	            String row = "row";
	            objXML = (JSONObject) objXML.remove(response);
	            //objXML = (JSONObject) objXML.remove(row);
	            String g = (objXML.remove(row)).toString();
	            String g2 = g.replace("\",", "\", \n ");
	            FileWriter fileWriter = new FileWriter("C:\\Users\\Usuario\\git\\proyectoIEI\\archivosDatos\\bibliotequesNEW2.json");
	            fileWriter.write(g2);
	            fileWriter.close();
	            
	            Object j = parser.parse(g2);
	            JSONArray array = (JSONArray) j;
	            
	            
	            cn = conexion.conectar();
	            stm = cn.createStatement();
	            PreparedStatement ps;

	            for (int i = 0; i < array.size(); i++) {
	            	JSONObject object =(JSONObject) array.get(i);
	            	String st_ins = "INSERT INTO biblioteca(nombre,tipo,direccion,codPostal,longitud,latitud,telefono,email,descripción)"
	                 		+ " VALUES (?,?,?,?,?,?,?,?,?)";
	            	
	            	ps = cn.prepareStatement(st_ins);
	            	
	            	String nombre = object.get("nom").toString().
	            			replace("Ã©","é").replace("Ã³","ó").replace("Ã","i").
	            			replace("i¨","è").replace("i²","ò");  
	            	String propietats = object.get("propietats").toString().
	            			replace("Ã©","é").replace("Ã³","ó").replace("Ã","i").
	            			replace("i¨","è").replace("i²","ò");
	            	String tipo = "Publica";
	            	if (propietats.contains("Privada")) {tipo = "Privada";}
	            	String direccion = object.get("via").toString().
	            			replace("Ã©","é").replace("Ã³","ó").replace("Ã","i").
	            			replace("i¨","è").replace("i²","ò");
	            	String codPostal = object.get("cpostal").toString();
	            	if(codPostal.length() == 4) {codPostal = "0" + codPostal;}
	            	//System.out.println(codPostal);
	            	Float longitud = Float.valueOf(object.get("longitud").toString());
	            	Float latitud = Float.valueOf(object.get("latitud").toString());
	            	String telefono = "";
	            	try {
	            	telefono = object.get("telefon1").toString().replace(" ","");
	            	}catch (Exception e) {}
	            	String email = object.get("email").toString();	
	            	String descripcion = object.get("propietats").toString()
	            			.replace("br","").replace(",","").replace(":[","")
	            			.replace("{\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"","").replace("\"\"\"\"\"\"\"]\"content\"\"","").
	            			replace("Ã©","é").replace("Ã³","ó").replace("Ã","i").
	            			replace("i¨","è").replace("i²","ò");
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
	            	String nombreL = object.get("poblacio").toString().
	            			replace("Ã©","é").replace("Ã³","ó").replace("Ã","i").
	            			replace("i¨","è").replace("i²","ò"); 
	            	String cod = codPostal.substring(2);
	            	boolean existePK = false;
	            	rs = stm.executeQuery ("SELECT codigo FROM localidad");
	            	while (rs.next()) {
	            		 String codBD =  (rs.getString("codigo"));
	             	    if(cod.equals(codBD)){existePK = true;}	            		
	            	}
	            	if(existePK == false) {
	            	String st_ins2 = "INSERT INTO localidad(nombre,codigo)"
	                 		+ " VALUES (?,?)";
	            	ps = cn.prepareStatement(st_ins2);
	            	ps.setString(1,nombreL);
	            	ps.setString(2,cod);
	            	ps.executeUpdate();
	            	}
	            	//provincia
	            	String comarca = object.get("comarca").toString().
	            			replace("Ã©","é").replace("Ã³","ó").replace("Ã","i").
	            			replace("i¨","è").replace("i²","ò"); 
	            	String nombreM = ComarcaToProvincia(comarca);
	            	String codM = codPostal.substring(0,2);
	            	boolean existePK2 = false;
	            	rs = stm.executeQuery ("SELECT codigo FROM provincia");
	            	while (rs.next())
	            	{
	            	    String codBD2 =  (rs.getString("codigo"));
	            	    if(codM.equals(codBD2)){existePK2 = true;}
	            	}
	            	if(existePK2 == false){
	            	String st_ins3 = "INSERT INTO provincia(nombre,codigo)"
	                 		+ " VALUES (?,?)";
	            	ps = cn.prepareStatement(st_ins3);
	            	ps.setString(1,nombreM);
	            	ps.setString(2,codM);
	            	ps.executeUpdate();
	            	}
	            	
	            }
	            System.out.println("OK");
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
	public static String ComarcaToProvincia(String c) {
		String resp = "Girona";
		if (c.equals("Vallès Oriental") || c.equals("Bages") || c.equals("Anoia") || c.equals("Barcelonès") || c.equals("Vallès Occidental")) {resp = "Barcelona";}
		return resp;
	}
	
}