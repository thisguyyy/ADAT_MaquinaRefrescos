package accesoDatos;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

public class HibernateGrisolia implements I_Acceso_Datos {

	public HibernateGrisolia() {

		System.out.println("Acceso a datos _ Hibernate - Alvaro Grisolia");
	}
	
	

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {

		HashMap<Integer, Deposito> MapDepositos = null;
		MapDepositos = new HashMap<Integer, Deposito>();

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session s = sf.openSession();

		Query qUpdate = s.createQuery("FROM Deposito");

		List list = qUpdate.getResultList();
		Iterator equiposIterator = list.iterator();

		while (equiposIterator.hasNext()) {

			Deposito miDisp = (Deposito) equiposIterator.next();
			MapDepositos.put(miDisp.getValor(), miDisp);
		}
		return MapDepositos;
	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {

		HashMap<String, Dispensador> MapDispensadores = null;
		MapDispensadores = new HashMap<String, Dispensador>();

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session s = sf.openSession();

		Query qUpdate = s.createQuery("FROM Dispensador");

		List list = qUpdate.getResultList();
		Iterator equiposIterator = list.iterator();

		while (equiposIterator.hasNext()) {

			Dispensador miDisp = (Dispensador) equiposIterator.next();
			MapDispensadores.put(miDisp.getClave(), miDisp);
		}
		return MapDispensadores;
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		boolean todoOK = true;
		
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session s = sf.openSession();
		
		
		for (Deposito miDep : depositos.values()) {
			s.beginTransaction();
			Deposito miDeposito = miDep;
			s.update(miDeposito);
			s.getTransaction().commit();
		}
		
		s.close();
		
		return todoOK;
	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
		boolean todoOK = true;
		
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session s = sf.openSession();
		
		for (Dispensador miDisp : dispensadores.values()) {
			s.beginTransaction();
			Dispensador miDispensador = miDisp;
			s.update(miDispensador);
			s.getTransaction().commit();
		}
		
		s.close();
		
		return todoOK;
	}
}
