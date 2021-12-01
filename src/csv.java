import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.dataformat.csv.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class csv {

	public static void main(String[] args) throws IOException {

		File input = new File("C:\\Users\\Usuario\\git\\proyectoIEI\\archivosDatos\\CV.csv");
		try {
			CsvSchema csv = CsvSchema.emptySchema().withHeader().withColumnSeparator(';');
			
			
//			CsvMapper csvMapper = new CsvMapper();
//			MappingIterator<OrderLine> orderLines = csvMapper.readerFor(OrderLine.class).with(csv).readValues(input);
//			ObjectMapper mapper = new ObjectMapper();
//			System.out.println(orderLines);
//			mapper.configure(SerializationFeature.INDENT_OUTPUT, true).writeValue(new File("/Users/sergiaguileramorales/Desktop/CSV.json"), orderLines.readAll());

			 //CsvSchema csv = CsvSchema.emptySchema().withHeader();
			CsvMapper csvMapper = new CsvMapper();
			MappingIterator<Map<?, ?>> mappingIterator = csvMapper.reader().forType(Map.class).with(csv)
					.readValues(input);
			
			List<Map<?, ?>> list = mappingIterator.readAll();
			// System.out.println(list);
			
			String doc = list.toString().replace(", NOM_PROVINCIA=","\", \n\"NOM_PROVINCIA\" : \"")
					.replace(", COD_MUNICIPIO=","\", \n\"COD_MUNICIPIO\" : \"").replace("COD_PROVINCIA=","\"COD_PROVINCIA\" : \"")
					.replace(", NOM_MUNICIPIO=","\", \n\"NOM_MUNICIPIO\" : \"")
					.replace(", TIPO=","\", \n\"TIPO\" : \"")
					.replace(", NOMBRE=","\", \n\"NOMBRE\" : \"")
					.replace(", DIRECCION=","\", \n\"DIRECCION\" : \"")
					.replace(", CP=","\", \n\"CP\" : \"")
					.replace(", DECRETO=","\", \n\"DECRETO\" : \"")
					.replace(", WEB=","\", \n\"WEB\" : \"")
					.replace(", DESC_CARACTER=","\", \n\"DESC_CARACTER\" : \"")
					.replace(", COD_CARACTER=","\", \n\"COD_CARACTER\" : \"")
					.replace(", CENTRAL=","\", \n\"CENTRAL\" : \"")
					.replace(", EMAIL=","\", \n\"EMAIL\" : \"")
					.replace(", CATALOGO=","\", \n\"CATALOGO\" : \"")
					.replace(", WEB=","\", \n\"WEB\" : \"")
					.replace(", FAX=","\", \n\"FAX\" : \"")
					.replace(", TELEFONO=","\", \n\"TELEFONO\" : \"")
					.replace("}", "\"}");
					
					
//					
//					.replace("COD_CARACTER=","COD_CARACTER\" : \"").replace("CENTRAL=","CENTRAL\" : \"").replace("EMAIL=","EMAIL\" : \"")
//					.replace("CATALOGO=","CATALOGO\" : \"").replace("WEB=","WEB\" : \"").replace("FAX=","FAX\" : \"").
//					replace("TELEFONO=","TELEFONO\" : \"");
			FileWriter fileWriter = new FileWriter("C:\\Users\\Usuario\\git\\proyectoIEI\\archivosDatos\\CV2.json");
			fileWriter.write(doc);
			fileWriter.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//SUBIR DATOS A LA BBDD
		conexionBD conexion = new conexionBD();
		 Connection cn = null;
		 Statement stm = null;
	     ResultSet rs = null;
	     try {
	            JSONParser parser = new JSONParser();
	            Object obj = parser.parse(new FileReader("C:\\Users\\Usuario\\git\\proyectoIEI\\archivosDatos\\CV2.json"));
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
	            	String nombre = object.get("NOMBRE").toString();  
	            	String tipo =  object.get("DESC_CARACTER").toString();
	            	String direccion = object.get("DIRECCION").toString();
	            	String codPostal = object.get("CP").toString().replace(".","");
	            	if(codPostal.length() == 4) {codPostal = "0" + codPostal;}
	            	Float longitud = (float) 0.0;
	            	Float latitud = (float) 0.0;
	            	String telefono = object.get("TELEFONO").toString().substring(5);
	            	String email = object.get("EMAIL").toString();	
	            	String descripcion = object.get("TIPO").toString();
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
	            	String nombreL = object.get("NOM_MUNICIPIO").toString(); 
	            	String cod = object.get("COD_MUNICIPIO").toString();
	            	if(cod.length() == 2) {cod = "0" + cod;}
	            	String st_ins2 = "INSERT INTO localidad(nombre,codigo)"
	                 		+ " VALUES (?,?)";
	            	ps = cn.prepareStatement(st_ins2);
	            	ps.setString(1,nombreL);
	            	ps.setString(2,cod);
	            	ps.executeUpdate();
	            	//provincia
	            	String nombreM = object.get("NOM_PROVINCIA").toString(); 
	            	String codM = object.get("COD_PROVINCIA").toString();
	            	if(codM.length() == 1) {codM = "0" + codM;}
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
	}
}

