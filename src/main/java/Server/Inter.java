package Server;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class Inter extends HandlerInterceptorAdapter {

	/*
	 * mp4 handling. (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#
	 * preHandle(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse res, Object handler) throws Exception {
		System.out.println(request.getHeader("X-FORWARDED-FOR") + ":" + request.getPathInfo());
		System.out.println(request.getRemoteAddr() + ":" + request.getPathInfo());
		if (request.getPathInfo().contains(".mp4")) {
			File file = new File("/web/" + request.getPathInfo());
			res.setContentLength((int) file.length());
			// res.setHeader("Content-Lenght", (int) file.length()+"");
			res.setHeader("Content-Type", "video/mp4");
			res.setStatus(HttpServletResponse.SC_OK);
			res.setHeader("Video", file.getName());
			System.out.println("headers set");
		}
	
	
		
		 res.getWriter()
			.write("<!-- Latest compiled " + "and minified CSS -->\r\n<link rel=\"stylesheet\""
					+ " href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" "
					+ "integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\""
					+ " crossorigin=\"anonymous\">\r\n\r\n<!-- Optional theme -->\r\n<link rel=\"stylesheet\" "
					+ "href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css\" "
					+ "integrity=\"sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp\""
					+ " crossorigin=\"anonymous\">\r\n\r\n" + "<!-- Latest compiled and minified JavaScript -->"
					+ "\r\n<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\" "
					+ "integrity=\"sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa\"" + " "
					+ "crossorigin=\"anonymous\"></script>");
	
	 res.getWriter().flush();

	 String test = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		System.out.println("html :"+test);

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse res, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (request.getPathInfo().contains(".mp4")) {
			File file = new File("/web/" + request.getPathInfo());
			res.setContentLength((int) file.length());
			// res.setHeader("Content-Lenght", (int) file.length()+"");
			res.setHeader("Content-Type", "video/mp4");
			res.setStatus(HttpServletResponse.SC_OK);
			res.setHeader("Video", file.getName());
			System.out.println("headers set");
		
		}
		
		String test = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		System.out.println("html :"+test);
	
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	
		String test = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		System.out.println("html :"+test);
	}

}
