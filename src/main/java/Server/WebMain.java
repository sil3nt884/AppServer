package Server;

import java.io.IOException;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.rewrite.handler.RewriteHandler;
import org.eclipse.jetty.rewrite.handler.Rule;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.handler.gzip.GzipHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebMain {

	/**
	 * Server up never needs to change 
	 * do not touch this file.
	 * 
	 * @param args
	 */
	
	
	public static void main(String[] args) {
		try {
			new WebMain().startJetty(80);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void startJetty(int port) throws Exception {
		System.err.println("Starting server at port {}" + port);
		Server server = new Server(port);
		ContextHandlerCollection contexHandlers = new ContextHandlerCollection();
		contexHandlers.setHandlers(new Handler[] { getServletContextHandler(getContext()) });

		GzipHandler gzipHandler = new GzipHandler();
		gzipHandler.setIncludedMethods(new String[] { "GET", "POST" });
		gzipHandler.setServer(server);
		
		RewriteHandler rewrite = new RewriteHandler();
		rewrite.addRule(new Rule(){

			@Override
			public String matchAndApply(String path, HttpServletRequest req, HttpServletResponse res)
					throws IOException {
				
				return path ;
			}
			
		});
		//rewrite.setHandler();

		HandlerList handlerList = new HandlerList();
		handlerList.setHandlers(new Handler[] { gzipHandler, contexHandlers});

		server.setHandler(handlerList);

		server.start();
		server.join();

	}

	private static ServletContextHandler getServletContextHandler(WebApplicationContext context) throws IOException {
		ServletContextHandler contextHandler = new ServletContextHandler();
		contextHandler.setErrorHandler(null);
		contextHandler.setContextPath("/");
		DispatcherServlet dispatcher;
		ServletHolder holder = new ServletHolder( dispatcher = new DispatcherServlet(context));
		
		holder.getRegistration().setMultipartConfig( new MultipartConfigElement ("/web/uplaods"));
	
		contextHandler.addServlet(holder, "/*");
		contextHandler.addEventListener(new ContextLoaderListener(context));
		contextHandler.setResourceBase("/web");
		AppConfig.setContext(context);
		return contextHandler;
	}

	private static WebApplicationContext getContext() {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();

		context.setConfigLocation("Server");

		return context;
	}
}
