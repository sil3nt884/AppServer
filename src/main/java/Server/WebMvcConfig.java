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
		registry.addResourceHandler(new String[] { "blog" }).addResourceLocations(new String[] { "/blog/" });
		registry.addResourceHandler(new String[] { "images/**" }).addResourceLocations(new String[] { "images/" });
	}
}

