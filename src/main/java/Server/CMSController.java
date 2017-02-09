package Server;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import POJO.Password;
import POJO.Session;
import POJO.User;

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

	/**
	 * Getting the login page.
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/cms/logon" }, method = { RequestMethod.GET })
	public ModelAndView logon(ModelAndView model, HttpServletRequest request, HttpServletResponse response) {
		model.setViewName("/cms/logon.html");
		return model;
	}

	/**
	 * Getting the registration page.
	 * 
	 * @param model
	 * @return
	 */

	@RequestMapping(value = { "/cms/registration" }, method = { RequestMethod.GET })
	public ModelAndView getRegsitationPage(ModelAndView model) {
		model.setViewName("/cms/registration.html");
		return model;
	}

	/**
	 * Adding users to the db.json file
	 * 
	 * @param model
	 * @return
	 */

	@RequestMapping(value = { "/cms/addUser" }, method = { RequestMethod.POST })
	public ModelAndView addUser(ModelAndView model, HttpServletRequest request, HttpServletResponse response) {
		String user = request.getParameter("user");
		String pass = request.getParameter("pass");
		String email = request.getParameter("email");
		Password password = new Password();
		byte[] salt = null;
		byte[] passbtye = null;
		try {
			salt = Password.genrateSalt();
			passbtye = password.getEncryptedPassword(pass, salt);

		} catch (NoSuchAlgorithmException | InvalidKeySpecException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		pass = password.getHexValue(passbtye);
		String saltstring = password.getHexValue(salt);

		User created = new User("1", "1", saltstring, "1");

		ObjectMapper mapper = new ObjectMapper();

		String[] userlist = new File("/web/cms/users/").list();
		int size = userlist.length;
		System.out.println(size);

		try {
			File db = new File("/web/cms/users/" + user + (size + 1) + ".json");
			mapper.writeValue(db, created);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.setViewName("/cms/registration.html");
		return model;

	}

	/**
	 * Login in. here i set the user. later on ill make this grab something from
	 * am embebed database.
	 * 
	 * @param model
	 * @return
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 */
	// creates an new endpoint in this example its /login
	// (www.websites.com/cms/login)
	@RequestMapping(value = { "/cms/login" }, method = { RequestMethod.POST })
	public ModelAndView login(ModelAndView model, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws NoSuchAlgorithmException, InvalidKeySpecException {
		ObjectMapper mapper = new ObjectMapper();
		String[] userlist = new File("/web/cms/users/").list();
		boolean login = false;
		String user = request.getParameter("user");
		String pass = request.getParameter("pass");

		/**
		 * getting the "th:name" Parameter which is then set as the user name.
		 * <form action="/cms/login" method="post"> user:
		 * <input th:name="user" type="text"></input> pass:
		 * <input type="password"> </input> <input type="submit"> </input>
		 */
		User usr = null;
		for (int i = 0; i < userlist.length; i++) {
			try {
				usr = mapper.readValue(new File("/web/cms/users/" + userlist[i]), User.class);

				if (usr.getUser().equalsIgnoreCase(user)) {
					Password passwrd = new Password();
					byte[] salt = passwrd.hexStringToByteArray(usr.getSalt());
					byte[] encryptedPassword = passwrd.hexStringToByteArray(usr.getPass());
					if (passwrd.authenticate(pass, encryptedPassword, salt)) {
						login = true;
					}
				}
			} catch (IOException e) {
				model.setViewName("/cms/registration.html");
			}
		}

		if (login) {
			Session sess = createNewSession(usr.getUser(),usr.getSalt());
			session.setAttribute("sessionUser", sess.getUser());
			session.setAttribute("sessionID", sess.getId());
			session.setAttribute("sessionText", "Logout");
			session.setAttribute("sessionHref", "Logout");
		}
		model.setViewName("/cms/index.html");
		return model;
	}

	private Session createNewSession(String user, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
		String id = getHexValue(getEncryptedPassword(user,hexStringToByteArray(salt)));
		System.out.println(id);
		long time =0;
		Session session = new Session (id, user, time);
		return session;
	}
	
	
	private  byte[] getEncryptedPassword(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
		String algorithm = "PBKDF2WithHmacSHA256";
		int derivedKeyLength = 256;
		int iterations = 20000;
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, derivedKeyLength);
		SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);
		return f.generateSecret(spec).getEncoded();

	}
	
	private String getHexValue(byte[] passbtye){
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < passbtye.length; i++) {
			sb.append(Integer.toString((passbtye[i] & 0xff) + 0x100, 16).substring(1));
		}

		return sb.toString();
	}
	
	private byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}

}
