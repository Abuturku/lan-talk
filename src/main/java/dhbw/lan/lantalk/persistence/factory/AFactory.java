package dhbw.lan.lantalk.persistence.factory;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import dhbw.lan.lantalk.persistence.objects.IPrimKey;

public abstract class AFactory<T extends IPrimKey> implements Serializable {

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
	@Transactional
	public T create(T object) {
		entityManager.persist(object);
		return object;
	}

	/**
	 * 
	 * @param object
	 *            the object which should be updated to the parameter from the
	 *            database
	 * @return the actual object from database
	 */
	public T get(T object) {
		return reattach(object);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<T> getAll() {
		return entityManager.createQuery("SELECT x FROM " + clazz.getName() + " x").getResultList();
	}

	/**
	 * 
	 * @param id
	 *            the id of the object in the database
	 * @return the object from database
	 */
	@Transactional
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
	@Transactional
	public T update(T object) {
		return this.entityManager.merge(object);
	}

	/**
	 * 
	 * @param object
	 *            Delete the object in the database
	 */
	public void delete(T object) {
		this.entityManager.remove(reattach(object));
	}

	/**
	 * 
	 * @param id
	 *            Delete the object with the commit id
	 */
	@Transactional
	public void delete(int id) {
		T object = entityManager.find(this.clazz, id);
		entityManager.remove(object);
	}

	/**
	 * If the instance of object is not a managed resource it will be reattached
	 * 
	 * @param object
	 *            to be reattached
	 * @return managed resource;
	 */
	public T reattach(T object) {
		return this.entityManager.contains(object) ? object : get(object.getId());
	}

}
