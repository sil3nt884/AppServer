package Server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class IndexController
{

	
	@RequestMapping(value={"/"}, method={RequestMethod.GET})
	public ModelAndView  getString(ModelAndView model)
	{
		model.addObject("test", "hello world");
		model.setViewName("index.html");
		return model;
	}
	
	
	  @RequestMapping(value={"/"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  public ModelAndView getDomain(ModelAndView model, HttpServletRequest request)
	  {
	    String url = request.getRequestURL().toString();
	    if (url.contains("localhost")) {
	      model.setViewName("tabbitalks/index.html");
	    }
	    return model;
	  }
	  
	  @RequestMapping(value={"/tabbitalks"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  public ModelAndView getTabbitalks(ModelAndView model)
	  {
	    model.setViewName("tabbitalks/index.html");
	    return model;
	  }


	
}


