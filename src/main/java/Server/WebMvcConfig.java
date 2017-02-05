package Server;

import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.server.handler.ResourceHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.handler.MappedInterceptor;

@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter
{
	/**
	 * Do not tocuh this file does not need to change.
	 */
	
	public void addResourceHandlers(ResourceHandlerRegistry registry)
	{
		
		
		
		registry.addResourceHandler(new String[] { "/**" }).addResourceLocations(new String[] { "/" }).setCacheControl(CacheControl.maxAge(30, TimeUnit.DAYS));
		
		
		
	}
	
	 @Bean
	    public MappedInterceptor timingInterceptor() {
	        return new MappedInterceptor(new String[] { "/**" }, inter ());
	    }
	
	  @Bean
	  public HandlerInterceptorAdapter inter (){
		 Inter in = new Inter();
		return in ;
		  
	  }
	
	  @Override
	    public void addInterceptors(InterceptorRegistry registry) {
	        registry.addInterceptor(timingInterceptor()).addPathPatterns("/**");
	        super.addInterceptors(registry);
	        
	       
	    }
	
}

