package accesoDatos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;

import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

/*
 * Todas los accesos a datos implementan la interfaz de Datos
 */

public class FicherosTexto implements I_Acceso_Datos {

	File fDis; // FicheroDispensadores
	File fDep; // FicheroDepositos

	public FicherosTexto() {
		System.out.println("ACCESO A DATOS - FICHEROS DE TEXTO");
	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {

		HashMap<Integer, Deposito> depositosCreados = null;

		FileReader fr = null;
		try {
			depositosCreados = new HashMap<Integer, Deposito>();
			fr = new FileReader("./Ficheros/datos/depositos.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader bf = new BufferedReader(fr);
		String sCadena;
		try {
			while ((sCadena = bf.readLine()) != null) {
				System.out.println(sCadena);

				String[] depCreadosArr = sCadena.split(";");

				Deposito miDeposito = new Deposito(depCreadosArr[0], Integer.parseInt(depCreadosArr[1]),
						Integer.parseInt(depCreadosArr[2]));

				depositosCreados.put(Integer.parseInt(depCreadosArr[1]), miDeposito);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return depositosCreados;
	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {

		HashMap<String, Dispensador> dispensadoresCreados = null;

		File Ffichero = new File("./Ficheros/datos/dispensadores.txt");

		if (Ffichero.exists()) {
			BufferedReader Flee = null;
			try {
				dispensadoresCreados = new HashMap<String, Dispensador>();
				Flee = new BufferedReader(new FileReader(Ffichero));
			} catch (FileNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			String Slinea;
			try {
				while ((Slinea = Flee.readLine()) != null) {
					String[] LineaSplit = Slinea.split(";");
					Dispensador Dispens = new Dispensador(LineaSplit[0], LineaSplit[1], Integer.parseInt(LineaSplit[2]),
							Integer.parseInt(LineaSplit[3]));
					dispensadoresCreados.put(Dispens.getClave(), Dispens);
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				Flee.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return dispensadoresCreados;
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		boolean todoOK = true;

		String ficheroOrg = "Ficheros/datos/depositos.txt";
		// borrar el fichero actual

		BufferedWriter bw = null;

		ficheroOrg.replaceAll("", "");

		// crear con los nuevos datos

		try {

			File nuevoFichero = new File("Ficheros/datos/depositos.txt");

			bw = new BufferedWriter(new FileWriter(nuevoFichero));

			for (Deposito miDeposito : depositos.values()) {

				bw.write(miDeposito.getNombreMoneda() + ";" + miDeposito.getValor() + ";" + miDeposito.getCantidad());
				bw.newLine();
			}

			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return todoOK;

	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {

		boolean todoOK = true;

		String ficheroOrg = "Ficheros/datos/dispensadores.txt";
		// borrar el fichero actual

		BufferedWriter bw = null;

		ficheroOrg.replaceAll("", "");

		// crear con los nuevos datos

		try {
			File nuevoFichero = new File("Ficheros/datos/dispensadores.txt");

			bw = new BufferedWriter(new FileWriter(nuevoFichero));

			for (Dispensador miDispensador : dispensadores.values()) {

				bw.write(miDispensador.getClave() + ";" + miDispensador.getNombreProducto() + ";"
						+ miDispensador.getPrecio() + ";" + miDispensador.getCantidad());
				bw.newLine();

			}

			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return todoOK;
	}

} // Fin de la clase