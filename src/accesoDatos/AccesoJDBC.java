package accesoDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;

import auxiliares.LeeProperties;
import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

public class AccesoJDBC implements I_Acceso_Datos {

	private String driver, urlbd, user, password; // Datos de la conexion
	private Connection conn1;

	public AccesoJDBC() {
		System.out.println("ACCESO A DATOS - Acceso JDBC");

		try {
			HashMap<String, String> datosConexion;

			LeeProperties properties = new LeeProperties("Ficheros/config/accesoJDBC.properties");
			datosConexion = properties.getHash();

			driver = datosConexion.get("driver");
			urlbd = datosConexion.get("urlbd");
			user = datosConexion.get("user");
			password = datosConexion.get("password");
			conn1 = null;

			Class.forName(driver);
			conn1 = DriverManager.getConnection(urlbd, user, password);
			if (conn1 != null) {
				System.out.println("Conectado a la base de datos");
			}

		} catch (ClassNotFoundException e1) {
			System.out.println("ERROR: No Conectado a la base de datos. No se ha encontrado el driver de conexion");
			// e1.printStackTrace();
			System.out.println("No se ha podido inicializar la maquina\n Finaliza la ejecucion");
			System.exit(1);
		} catch (SQLException e) {
			System.out.println("ERROR: No se ha podido conectar con la base de datos");
			System.out.println(e.getMessage());
			// e.printStackTrace();
			System.out.println("No se ha podido inicializar la maquina\n Finaliza la ejecucion");
			System.exit(1);
		}
	}

	public int cerrarConexion() {
		try {
			conn1.close();
			System.out.println("Cerrada conexion");
			return 0;
		} catch (Exception e) {
			System.out.println("ERROR: No se ha cerrado corretamente");
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {

		PreparedStatement stm = null;
		ResultSet rs = null;

		HashMap<Integer, Deposito> obtenerDepBBDD = null;

		try {

			obtenerDepBBDD = new HashMap<Integer, Deposito>();

			String query = "SELECT * FROM depositos";
			stm = conn1.prepareStatement(query);
			rs = stm.executeQuery();

			while (rs.next()) {

				Deposito miDeposito = new Deposito(rs.getString(2), Integer.parseInt(rs.getString(3)),
						Integer.parseInt(rs.getString(4)));
				obtenerDepBBDD.put(Integer.parseInt(rs.getString(3)), miDeposito);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return obtenerDepBBDD;
	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {

		PreparedStatement stm = null;
		ResultSet rs = null;

		HashMap<String, Dispensador> obtenerDispBBDD = null;

		try {

			obtenerDispBBDD = new HashMap<String, Dispensador>();

			String query = "SELECT * FROM dispensadores";
			stm = conn1.prepareStatement(query);
			rs = stm.executeQuery();

			while (rs.next()) {
				Dispensador miDispensador = new Dispensador(rs.getString(2), rs.getString(3),
						Integer.parseInt(rs.getString(4)), Integer.parseInt(rs.getString(5)));
				obtenerDispBBDD.put(rs.getString(1), miDispensador);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obtenerDispBBDD;
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		boolean todoOK = true;

		PreparedStatement stm = null;
		try {
			for (Deposito miDeposito : depositos.values()) {
				String query = "UPDATE depositos SET cantidad = " + miDeposito.getCantidad() + " WHERE valor = "
						+ miDeposito.getValor();
				stm = conn1.prepareStatement(query);
				stm.executeUpdate(query);
				stm.close();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return todoOK;
	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
		boolean todoOK = true;
		PreparedStatement stm = null;
		try {
			for (Dispensador miDispensador : dispensadores.values()) {
				if (miDispensador.getCantidad() != 0) {
					String query = "Update dispensadores Set cantidad = " + miDispensador.getCantidad()
							+ " where clave = " + "'" + miDispensador.getClave() + "'";
					stm = conn1.prepareStatement(query);
					stm.executeUpdate(query);
					stm.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return todoOK;
	}

} // Fin de la clase