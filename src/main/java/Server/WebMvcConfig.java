package Server;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter
{
	public void addResourceHandlers(ResourceHandlerRegistry registry)
	{
		registry.addResourceHandler(new String[] { "/**" }).addResourceLocations(new String[] { "/" });
		
		
		
	}
}

