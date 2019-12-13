package modelo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class BDProducto {
	private static final String PERSISTENCE_UNIT_NAME = "producto";
	private static EntityManagerFactory factory;

	// si el producto no existe lo guarda en la base de datos, por id.
	public static void insertar(Todo Todo) {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();

		if (!existeId(Todo.getId())) {
			em.getTransaction().begin();
			em.persist(Todo);
			em.getTransaction().commit();
			em.close();
		}
	}

	// actualiza el resumen y descripción de un Todo existente.
	public static void actualizar(Todo Todo) {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();

		if (existeId(Todo.getId())) {
			Query q = em.createQuery("SELECT u FROM Todo u WHERE u.id = :id");
			q.setParameter("id", Todo.getId());
			
			Todo resultado = (Todo) q.getSingleResult();
			resultado.setResumen(Todo.getResumen());
			resultado.setDescripcion(Todo.getDescripcion());
			
			em.getTransaction().begin();
			em.merge(resultado);
			em.getTransaction().commit();
			em.close();
		}
	}

	// borrar un Producto de la base de datos
	public static void eliminar(Todo Todo) {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();

		if (existeId(Todo.getId())) {
			Query q = em.createQuery("SELECT u FROM Todo u WHERE u.id = :id");
			q.setParameter("id", Todo.getId());

			Todo resultado = (Todo) q.getSingleResult();
			
			em.getTransaction().begin();
			em.remove(resultado);
			em.getTransaction().commit();			
			em.close();
		}
	}

	// devuelve el Todo seleccionado
	public static Todo seleccionarTodo(String id) {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		
		Todo resultado = null;

		if (existeId(id)) {
			Query q = em.createQuery("SELECT u FROM Todo u WHERE u.id = :id");
			q.setParameter("id", id);
			resultado = (Todo) q.getSingleResult();
			em.close();
		}

		return resultado;
	}

	// nos dice si el Todo existe
	public static boolean existeId(String id) {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		
		Query q = em.createQuery("SELECT u FROM Todo u WHERE u.id = :id");
		q.setParameter("id", id);
		
		try {
			q.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		} finally {
			em.close();
		}
	}
	
	// devuelve una lista de Todos
	public static List<Todo> listarTodos() {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		
		Query q = em.createQuery("SELECT u FROM Todo u");
		
		@SuppressWarnings("unchecked")
		List<Todo> resultado = q.getResultList();
		em.close();
		
		return resultado;
	}
}
