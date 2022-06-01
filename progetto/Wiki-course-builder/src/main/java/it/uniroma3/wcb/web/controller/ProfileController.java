package it.uniroma3.wcb.web.controller;

import it.uniroma3.wcb.persistence.model.UserTeachingStyle;
import it.uniroma3.wcb.persistence.service.GRTeachingStyleDto;
import it.uniroma3.wcb.persistence.service.TeachingStyleService;
import it.uniroma3.wcb.util.GenericResponse;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author Andrea Tarantini, Alessandra Milita
 *
 */
@Controller
public class ProfileController {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
    private TeachingStyleService teachingStyleService;
	
	@RequestMapping(value = "/user/saveTeachingStyle", method = RequestMethod.POST)
    @ResponseBody
    public GenericResponse saveTeachingStyle(@Valid final GRTeachingStyleDto grTeachingStyleDto, final HttpServletRequest request) throws Exception {
        LOGGER.debug("Registering user teaching style: {}", grTeachingStyleDto);
        
        UserTeachingStyle registeredTS = teachingStyleService.saveUserTeachingStyle(grTeachingStyleDto);
        
        if (registeredTS == null) {
            throw new Exception("Error while registering user teaching style");
        }

        return new GenericResponse("success");
    }
	
	@RequestMapping(value = "/user/loadTeachingStyle", method = RequestMethod.POST)
    @ResponseBody
    public GRTeachingStyleDto loadTeachingStyle(final HttpServletRequest request) throws Exception {
        LOGGER.debug("Loading user teaching style");
        
        GRTeachingStyleDto registeredTS = teachingStyleService.loadUserTeachingStyle();
        
        return registeredTS;
    }
	
	@RequestMapping(value = "/user/checkTeachingStyleExistence", method = RequestMethod.GET)
    @ResponseBody
    public GenericResponse checkTeachingStyleExistence(final HttpServletRequest request) throws Exception {
        LOGGER.debug("Checking user teaching style existence");
        GRTeachingStyleDto registeredTS = teachingStyleService.loadUserTeachingStyle();
        return new GenericResponse(String.valueOf(registeredTS!=null));
    } 
	
}
