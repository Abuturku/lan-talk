package dhbw.lan.lantalk.application.managedbeans;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import dhbw.lan.lantalk.persistence.factory.CommentFactory;
import dhbw.lan.lantalk.persistence.factory.PostFactory;

@ManagedBean
@SessionScoped
public class PostManagedBean {
	
	@Inject
	private PostFactory postFactory;
	
	@Inject
	private CommentFactory commentFactory;
	
	
	@PostConstruct
	public void init(){
		
	}
}
