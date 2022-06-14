package com.tistory.pentode;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	private final String PATH = "C:/workspace/workspace_neon/spring_upload/src/main/webapp/WEB-INF/upload/";
	
	@Autowired
	MappingJackson2JsonView jsonView;
	
	@RequestMapping(value = "/uploadForm.do")
	public String uploadForm() throws Exception {
		return "uploadForm";
	}
	
	@RequestMapping(value="/upload.do", method=RequestMethod.POST, produces="text/plain")
	public ModelAndView upload(MultipartHttpServletRequest request) throws Exception {
		
		ModelAndView model = new ModelAndView();
		model.setView(jsonView);
		
		Iterator<String> itr =  request.getFileNames();
		
        if(itr.hasNext()) {
        	List<MultipartFile> mpf = request.getFiles(itr.next());
            // 임시 파일을 복사한다.
            for(int i = 0; i < mpf.size(); i++) {

                File file = new File(PATH + mpf.get(i).getOriginalFilename());
                logger.info(file.getAbsolutePath());
                mpf.get(i).transferTo(file);
                
            }
            
            JSONObject json = new JSONObject();
            json.put("code", "true");
            model.addObject("result", json);
            return model;
            
        } else {
        	
            JSONObject json = new JSONObject();
            json.put("code", "false");
            model.addObject("result", json);
            return model;
            
        }
	}

}
