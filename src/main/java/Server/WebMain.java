package Server;

import java.io.IOException;

import javax.servlet.MultipartConfigElement;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.gzip.GzipHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebMain {


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
		GzipHandler gzipHandler = new GzipHandler();
		gzipHandler.setIncludedMethods(new String[] { "GET", "POST" });

		gzipHandler.setServer(server);
		ContextHandlerCollection contexHandlers = new ContextHandlerCollection();
		contexHandlers.setHandlers(new Handler[] { getServletContextHandler(getContext()) });
		gzipHandler.setHandler(contexHandlers);
		server.setHandler(gzipHandler);
		
		server.start();
		server.join();
	}

	private static ServletContextHandler getServletContextHandler(WebApplicationContext context) throws IOException {
		ServletContextHandler contextHandler = new ServletContextHandler();
		contextHandler.setErrorHandler(null);
		contextHandler.setContextPath("/");
		ServletHolder  holder = new ServletHolder(new DispatcherServlet(context));
		holder.getRegistration().setMultipartConfig(new MultipartConfigElement ("/web/uploads"));
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
