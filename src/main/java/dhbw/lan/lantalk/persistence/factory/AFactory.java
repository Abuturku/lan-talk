package dhbw.lan.lantalk.persistence.factory;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dhbw.lan.lantalk.persistence.objects.IPrimKey;

public abstract class AFactory<T extends IPrimKey> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8385764731346098260L;
	/**
	 * Is the class of the database object
	 */
	private final Class<T> clazz;
	/**
	 * Connection to database
	 */
	@PersistenceContext
	private EntityManager entityManager;
	/**
	 * 
	 * @param clazz
	 *            set the parameter {@link AFactory#clazz}
	 */
	protected AFactory(Class<T> clazz) {
		this.clazz = clazz;
	}

	/**
	 * Create a object on Database
	 * 
	 * @param object
	 *            The object to create
	 */
	public void create(T object) {
		entityManager.persist(object);
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
		T object = (T) entityManager.find(this.clazz, id);
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
		T databaseobject = entityManager.find(this.clazz, object.getID());
		setParameter(databaseobject, object);
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
		T object = entityManager.find(this.clazz, id);
		entityManager.remove(object);
	}
}
