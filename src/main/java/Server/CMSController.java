package Server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * Content Management System.
 * 
 * @author Ricky basedir : /web/cms/
 * 
 */

@Controller
public class CMSController {

	String user = "no user";

	/**
	 * Creating an endpoint to bring user the CMS loading page.(GET)
	 * 
	 * @param model
	 * @return index.html
	 */
	// creates an new endpoint in this example its /login (www.websites.com/cms)
	@RequestMapping(value = { "/cms" }, method = { RequestMethod.GET })
	public ModelAndView index(ModelAndView model) {
		model.setViewName("/cms/index.html");
		/*
		 * //adding $variables The first para is the name you see in the html.
		 * here i name it $user. The second value is the the value you want it
		 * display when the html renders. e.g: Welcome: <p th:text="${user}">
		 * <p/>
		 * 
		 */
		model.addObject("user", user);
		return model;
	}

	@RequestMapping(value = { "/cms/logon" }, method = { RequestMethod.GET })
	public ModelAndView logon(ModelAndView model, HttpServletRequest request, HttpServletResponse response) {
		model.setViewName("/cms/logon.html");
		return model;
	}

	/**
	 * Login in. here i set the user. later on ill make this grab something from
	 * am embebed database.
	 * 
	 * @param model
	 * @return
	 */
	// creates an new endpoint in this example its /login
	// (www.websites.com/cms/login)
	@RequestMapping(value = { "/cms/login" }, method = { RequestMethod.POST })
	public ModelAndView login(ModelAndView model, HttpServletRequest request, HttpServletResponse response) {
		/**
		 * getting the "th:name" Parameter which is then set as the user name.
		 * <form action="/cms/login" method="post"> user:
		 * <input th:name="user" type="text"></input> pass:
		 * <input type="password"> </input> <input type="submit"> </input>
		 */
		user = request.getParameter("user");
		model.addObject("user", user);
		model.setViewName("/cms/index.html");
		return model;
	}

}
