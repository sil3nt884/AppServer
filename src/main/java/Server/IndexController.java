package Server;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class IndexController
{

	

	File[] lists;
	
	public void  getUploads (){
	
		 File folder = new File("/web/uploads");
		 System.err.println(folder.getAbsolutePath());
		 File[] list= folder.listFiles();
		 lists = list;
		 
		
	}
	
	public void upload(String file){
		
	}
	
	
	
	 @RequestMapping(value={"/upload"}, method={RequestMethod.POST})
	  public String upload( @RequestParam("upload") MultipartFile file ,HttpServletResponse response) throws IOException
	  {	  
	    
		File newfile = new File ("/web/uploads/"+file.getOriginalFilename());
		InputStream in = file.getInputStream();
		BufferedInputStream  bin = new BufferedInputStream(in);
		int read = 0;
		FileOutputStream out =  new FileOutputStream (newfile);
		
		while((read = bin.read()) != -1){
			out.write(read);
		
		}
		out.flush();
		out.close();
		
		
		return "index.html";
		 	
	   
	  }
	 
	 @RequestMapping(value={"/uploads"}, method={RequestMethod.GET})
	  public ModelAndView getUpload( ModelAndView model,HttpServletRequest request ,HttpServletResponse response)
	  {
	    
		 	
		  	model.setViewName("upload.html");
			return model;
	    
	    	
	   
	  }
	
	
	
	
	  @RequestMapping(value={"/"}, method={RequestMethod.GET})
	  public ModelAndView getDomain(ModelAndView model, HttpServletRequest request ,HttpServletResponse response)
	  {
	    
		  	getUploads ();
		  	model.addObject("files", lists);
		  	model.setViewName("index.html");
	    
	    	
	    return model;
	  }
	  
	
	  
	  

	
}


