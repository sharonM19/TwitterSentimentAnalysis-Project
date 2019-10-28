package com.twitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;

@Controller
public class TwitterController {
	
	
	@RequestMapping(value="/welcome")
    public String test() {
        return "welcome";
    }
	
	
	@RequestMapping(value ="/results" , method = RequestMethod.POST)
	public String firstPage(Model model,TwitterForm twitterForm) {
		String line = "";
		float[] percent = new float[] {0, 0, 0};
		String input = twitterForm.getTwitterInput();
		System.out.println("Input Parameter-->"+input);
		
		// set up the command and parameter
		String pythonScriptPath = "F:\\MyData\\BITS\\Project\\twitterCode.py";
		String[] cmd = new String[3];
		cmd[0] = "python"; 
		cmd[1] = pythonScriptPath;
		cmd[2]= input;
				 
		// create runtime to execute external command
		Runtime rt = Runtime.getRuntime();
	    Process pr;
	    
	 		try {
					pr = rt.exec(cmd);
				
				// retrieve output from python script
				BufferedReader bfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));
				String message = new String();
				
				while((line = bfr.readLine()) != null) {
				   // display each output line form python script
					
					message += line;
			      }
				
			      System.out.println(" -->"+message);
			      
			      
			      Pattern p = Pattern.compile("[a-zA-Z]*([0-9]+[.]{1}[0-9]*)[%]*");
				  Matcher m = p.matcher(message);
				  int count = 0;
					while(m.find() && count<3)
					{
						percent[count] = Float.valueOf(m.group(count));
						count++;
					}
					for(float a: percent)

						System.out.println(a);


					if(percent[2]==0)
						percent[2]=100-percent[0]-percent[1];
					
					model.addAttribute("positive", percent[0]);
					model.addAttribute("negative", percent[1]);
					model.addAttribute("neutral", percent[2]);
				
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
	 		
	 		Gson gsonObj = new Gson();
	 		Map<Object,Object> map = null;
	 		List<Map<Object,Object>> list = new ArrayList<Map<Object,Object>>();
	 		 
	 		map = new HashMap<Object,Object>(); map.put("label", "Postive Tweets"); map.put("y", percent[0]); map.put("exploded", true); list.add(map);
	 		map = new HashMap<Object,Object>(); map.put("label", "Negative Tweets"); map.put("y", percent[1]); list.add(map);
	 		map = new HashMap<Object,Object>(); map.put("label", "Neutral Tweets"); map.put("y", percent[2]); list.add(map);
		    
	 		String dataPoints = gsonObj.toJson(list);
	 		
	 		model.addAttribute("dataPoints", dataPoints);
				
		return "results" ;
	}
}
