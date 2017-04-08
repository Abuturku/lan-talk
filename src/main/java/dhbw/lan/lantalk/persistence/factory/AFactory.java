package dhbw.lan.lantalk.persistence.factory;

import javax.persistence.EntityManager;

public abstract class AFactory<T> {

	protected EntityManager em;

	public AFactory() {

	}
}
