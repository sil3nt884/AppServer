package Server;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class Inter extends HandlerInterceptorAdapter {


	/*
	 * mp4 handling.
	 * (non-Javadoc)
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse res, Object handler) throws Exception {
		System.out.println(request.getHeader("X-FORWARDED-FOR")+":"+request.getPathInfo());
		System.out.println(request.getRemoteAddr()+":"+request.getPathInfo());
		if (request.getPathInfo().contains(".mp4")) {
			File file = new File("/web/" + request.getPathInfo());
			res.setContentLength((int) file.length());
			// res.setHeader("Content-Lenght", (int) file.length()+"");
			res.setHeader("Content-Type", "video/mp4");
			res.setStatus(HttpServletResponse.SC_OK);
			res.setHeader("Video", file.getName());
			System.out.println("headers set");
		}

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

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
