package dhbw.lan.lantalk.application.managedbeans;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import dhbw.lan.lantalk.persistence.factory.UserFactory;

@ManagedBean
@SessionScoped
public class UserManagedBean {
	
	@Inject
	private UserFactory userFactory;
	
	@PostConstruct
	public void init(){
		
	}
}
