package Server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class IndexController
{
	@RequestMapping(value={"/"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	public ModelAndView  getString(ModelAndView model)
	{
		model.addObject("test", "hello world");
		model.setViewName("index.html");
		return model;
	}


	
}


