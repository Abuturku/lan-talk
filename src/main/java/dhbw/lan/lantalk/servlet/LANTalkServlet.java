package dhbw.lan.lantalk.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dhbw.lan.lantalk.persistence.factory.UserFactory;
import dhbw.lan.lantalk.persistence.objects.User;

public class LANTalkServlet extends HttpServlet {
	private static final long serialVersionUID = 9146025682936704820L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.getWriter().append("Hello World!");

//		User user = new User();
//		user.setName("Niklas");
//		UserFactory.getInstance().create(user);
	}
}
