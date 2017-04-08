package dhbw.lan.lantalk.persistence.factory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import dhbw.lan.lantalk.persistence.objects.IPrimKey;

public abstract class AFactory<T extends IPrimKey> {

	/**
	 * Is the class of the database object
	 */
	private final Class<T> clazz;
	/**
	 * Connection to database
	 */
	private final EntityManagerFactory entityManagerFactory;

	/**
	 * 
	 * @param clazz
	 *            set the parameter {@link AFactory#clazz}
	 */
	protected AFactory(Class<T> clazz) {
		this.clazz = clazz;
		this.entityManagerFactory = Persistence.createEntityManagerFactory("dhbw.lan.lantalk.persistence.factory");
	}

	/**
	 * Create a object on Database
	 * 
	 * @param object
	 *            The object to create
	 */
	public void create(T object) {
		EntityManager entityManager = this.entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(object);
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	/**
	 * 
	 * @param object
	 *            the object which should be updated to the parameter from the
	 *            database
	 * @return the actual object from database
	 */
	public T get(T object) {
		return get(object.getID());
	}

	/**
	 * 
	 * @param id
	 *            the id of the object in the database
	 * @return the object from database
	 */
	public T get(int id) {
		EntityManager entitymanager = this.entityManagerFactory.createEntityManager();
		entitymanager.getTransaction().begin();
		T object = (T) entitymanager.find(this.clazz, id);
		entitymanager.getTransaction().commit();
		entitymanager.close();
		return object;
	}

	/**
	 * Update the commit object in the database.
	 * 
	 * @param object
	 *            The current object
	 * @return new reference of the updated object
	 */
	public T update(T object) {
		EntityManager entitymanager = this.entityManagerFactory.createEntityManager();
		entitymanager.getTransaction().begin();
		T databaseobject = entitymanager.find(this.clazz, object.getID());
		setParameter(databaseobject, object);
		entitymanager.getTransaction().commit();
		entitymanager.close();
		return databaseobject;
	}

	/**
	 * Transfer parameter between classes
	 * 
	 * @param toSet
	 *            Class were the parameter should be set
	 * @param orginal
	 *            Class from were the parameter will be taken
	 */
	protected abstract void setParameter(T toSet, T orginal);

	/**
	 * 
	 * @param object
	 *            Delete the object in the database
	 */
	public void delete(T object) {
		delete(object.getID());
	}

	/**
	 * 
	 * @param id
	 *            Delete the object with the commit id
	 */
	public void delete(int id) {
		EntityManager entitymanager = this.entityManagerFactory.createEntityManager();
		entitymanager.getTransaction().begin();
		T object = entitymanager.find(this.clazz, id);
		entitymanager.remove(object);
		entitymanager.getTransaction().commit();
		entitymanager.close();
	}
}
