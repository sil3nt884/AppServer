package Server;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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

	/**
	 * Getting the login page.
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/cms" }, method = { RequestMethod.GET })
	public ModelAndView logon(ModelAndView model, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		System.out.println(session.getAttribute("sessionID"));
		if (session.isNew()  || session.getAttribute("sessionID") == null) {
			model.setViewName("/cms/logon.html");
		} else if (!session.isNew() && session.getAttribute("sessionID") != null) {
			model.setViewName("/cms/index.html");
		}
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

		User created = new User(user, pass, saltstring, email);

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
	
	private boolean checkSession(HttpSession session) throws IOException{
		String id = (String) session.getAttribute("sessionID");
		boolean sessionAlive = false;
		if (id != null) {
			System.out.println(id);
			File json = new File("/web/cms/session/");
			File[] list = json.listFiles();
			for (int i = 0; i < list.length; i++) {
				if (list[i].getName().contains(id)) {
					sessionAlive = true;
					break;
				}
			}

			
	}
		return sessionAlive;
	}
	

	@RequestMapping(value = { "/cms/addPage" }, method = { RequestMethod.GET })
	public void createNewPage( HttpSession session, HttpServletResponse response) throws IOException {
		String id = (String) session.getAttribute("sessionID");
		if (id != null) {
			
			if (checkSession(session)) {
				response.sendRedirect("/cms/apps/creator");
					
			}
			else if(!checkSession(session)){
				response.sendRedirect("/cms/sessionend");
			}

		}
		
		
	}
	
	@RequestMapping(value = { "/cms/apps/creator" }, method = { RequestMethod.GET })
		public ModelAndView creator( ModelAndView model ,HttpSession session, HttpServletResponse response) throws IOException {
		if (checkSession(session)) {
			model.setViewName("cms/apps/pagecreator/creator.html");
				
		}
		else if(!checkSession(session)){
			response.sendRedirect("/cms/sessionend");
		}
	
			return model;
		
		
		}
		
	
	
	@RequestMapping(value = { "/cms/sessionend" }, method = { RequestMethod.GET })
	public ModelAndView sessionend( ModelAndView model ,HttpSession session, HttpServletResponse response) throws IOException, InterruptedException {
		boolean notfound = false;
		String id = (String) session.getAttribute("sessionID");
		String[] file = new File("/web/cms/session/").list();
		for (int i = 0; i < file.length; i++) {
			File json = new File("/web/cms/session/" + file[i]);
			if (!json.getName().contains(id)) {
				notfound = true;
				break;
			}
		}
		
		if(notfound){
		session.removeAttribute("sessionUser");
		session.removeAttribute("sessionID");
		session.removeAttribute("sessionText");
		session.removeAttribute("sessionHref");
		session.invalidate();
		model.setViewName("/cms/sessionend.html");
		}
		else if(session.getAttribute("SessionID") !=null && (!notfound)){
			 response.sendRedirect("/cms");
		}
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
	 * @throws IOException
	 */
	// creates an new endpoint in this example its /login
	// (www.websites.com/cms/login)
	@RequestMapping(value = { "/cms/login" }, method = { RequestMethod.POST })
	public ModelAndView login(ModelAndView model, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
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
						break;
					}
				}
			} catch (IOException e) {
				model.setViewName("/cms/registration.html");
			}
		}

		if (login) {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			Session sess = createNewSession(usr.getUser() + ":" + sdf.format(cal.getTime()), usr.getSalt());
			session.setAttribute("sessionUser", sess.getUser().split(":")[0]);
			session.setAttribute("sessionID", sess.getId());
			session.setAttribute("sessionText", "Logout");
			session.setAttribute("sessionHref", "/cms/logout");
			session.setMaxInactiveInterval(1200);
			response.sendRedirect("/cms");

		} else if (!login) {
			model.setViewName("/cms/failed.html");
		}

		return model;
	}

	private Session createNewSession(String user, String salt)
			throws NoSuchAlgorithmException, InvalidKeySpecException {

		String id = getHexValue(getEncryptedPassword(user, hexStringToByteArray(salt)));
		System.out.println(id);
		long time = 1200000;
		Session session = new Session(id, user, time);
		ObjectMapper mapper = new ObjectMapper();
		try {
			File db = new File("/web/cms/session/" + session.getUser().split(":")[0] + "_" + session.getId() + ".json");
			mapper.writeValue(db, session);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return session;
	}

	@RequestMapping(value = { "/cms/logout" }, method = { RequestMethod.GET })
	public ModelAndView logout(ModelAndView model, HttpSession session) {
		String id = (String) session.getAttribute("sessionID");
		String[] file = new File("/web/cms/session/").list();
		for (int i = 0; i < file.length; i++) {
			File json = new File("/web/cms/session/" + file[i]);
			if (json.getName().contains(id)) {
				System.out.println("id: " + id + "json deleted " + json.getName());
				json.delete();
			}
		}

		session.removeAttribute("sessionUser");
		session.removeAttribute("sessionID");
		session.removeAttribute("sessionText");
		session.removeAttribute("sessionHref");
		session.invalidate();
		model.setViewName("/cms/logon.html");

		return model;

	}

	private byte[] getEncryptedPassword(String password, byte[] salt)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		String algorithm = "PBKDF2WithHmacSHA256";
		int derivedKeyLength = 256;
		int iterations = 20000;
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, derivedKeyLength);
		SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);
		return f.generateSecret(spec).getEncoded();

	}

	private String getHexValue(byte[] passbtye) {
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
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}

}
