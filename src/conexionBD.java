import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexionBD {
	
	public static final String CONTROLADOR = "com.mysql.jdbc.Driver";
	public static final String URL = "jdbc:mysql://localhost:3306/proyecto";
	public static final String USUARIO = "root";
	public static final String CONTRASEÑA = "R11112000";
	
	public Connection conectar() {
		
		Connection conexion = null;
		
		try {
			Class.forName(CONTROLADOR);
			conexion = DriverManager.getConnection(URL,USUARIO,CONTRASEÑA);
			System.out.println("Conexion OK");
		}catch(ClassNotFoundException c) {System.out.println("Error conexion 1 ");
		}catch(SQLException e) {System.out.println("Error conexion 2 ");e.printStackTrace();}
		
		return conexion;
	}
	public static void main(String[] args) {
		
		
	}
}

