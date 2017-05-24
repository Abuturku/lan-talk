package dhbw.lan.lantalk.application.beans;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dhbw.lan.lantalk.persistence.factory.CommentFactory;

import java.io.Serializable;

@Named
@RequestScoped
public class CommentManagerBean implements Serializable {

	private static final long serialVersionUID = 822448000842189585L;
	
	@Inject
	private CommentFactory commentFactory;
	
	
	
}
