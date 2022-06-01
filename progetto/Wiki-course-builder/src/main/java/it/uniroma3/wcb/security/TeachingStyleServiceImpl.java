package it.uniroma3.wcb.security;

import it.uniroma3.wcb.persistence.dao.UserRepository;
import it.uniroma3.wcb.persistence.model.UserTeachingStyle;
import it.uniroma3.wcb.persistence.model.User;
import it.uniroma3.wcb.persistence.service.GRTeachingStyleDto;
import it.uniroma3.wcb.persistence.service.TeachingStyleService;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Andrea Tarantini, Alessandra Milita
 *
 */
@Service("teachingStyleService")
@Transactional
public class TeachingStyleServiceImpl implements TeachingStyleService{

	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	private RequestUserService userDetailsService;
	
	@Override
	public UserTeachingStyle saveUserTeachingStyle(final GRTeachingStyleDto teachingStyleDto) {
		
		try {
			User user = this.userDetailsService.getRequestUser();
          
        	UserTeachingStyle ts = fromDto(teachingStyleDto);
                
        	user.setTeachingStyle(ts);
        	
        	user = userRepository.save(user);
        	
        	return user.getTeachingStyle();
            
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
	}

	@Override
	public GRTeachingStyleDto loadUserTeachingStyle() {
		try {
			
			User user = this.userDetailsService.getRequestUser();
            return toDto(user.getTeachingStyle());
            
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
	}
	
	private UserTeachingStyle fromDto(GRTeachingStyleDto teachingStyleDto){
		UserTeachingStyle ts = new UserTeachingStyle();
		
		ts.setDelegator(teachingStyleDto.getDelegator());
		ts.setDelegatorRange(teachingStyleDto.getDelegator_range());
		ts.setPersonalModel(teachingStyleDto.getPersonalmodel());
		ts.setPersonalModelRange(teachingStyleDto.getPersonalmodel_range());
		ts.setExpert(teachingStyleDto.getExpert());
		ts.setExpertRange(teachingStyleDto.getExpert_range());
		ts.setFacilitator(teachingStyleDto.getFacilitator());
		ts.setFacilitatorRange(teachingStyleDto.getFacilitator_range());
		ts.setFormalAuthority(teachingStyleDto.getFormalauthority());
		ts.setFormalAuthorityRange(teachingStyleDto.getFormalauthority_range());
		
		ts.setGender(teachingStyleDto.getGender());
		ts.setLevel(teachingStyleDto.getLevel());
		ts.setDiscipline(teachingStyleDto.getDiscipline());
		ts.setRace(teachingStyleDto.getRace());
		ts.setTeacherrank(teachingStyleDto.getTeacherrank());
		
		ts.setSurveyModule(getSurveyModuleFromDto(teachingStyleDto).toString());
		return ts;
	}
	
	private GRTeachingStyleDto toDto(UserTeachingStyle ts){
		if(ts==null)
			return null;
		
		GRTeachingStyleDto dto = new GRTeachingStyleDto();
		
		dto.setDelegator(ts.getDelegator());
		dto.setDelegator_range(ts.getDelegatorRange());
		dto.setPersonalmodel(ts.getPersonalModel());
		dto.setPersonalmodel_range(ts.getPersonalModelRange());
		dto.setExpert(ts.getExpert());
		dto.setExpert_range(ts.getExpertRange());
		dto.setFacilitator(ts.getFacilitator());
		dto.setFacilitator_range(ts.getFacilitatorRange());
		dto.setFormalauthority(ts.getFormalAuthority());
		dto.setFormalauthority_range(ts.getFormalAuthorityRange());
		
		dto.setGender(ts.getGender());
		dto.setLevel(ts.getLevel());
		dto.setDiscipline(ts.getDiscipline());
		dto.setRace(ts.getRace());
		dto.setTeacherrank(ts.getTeacherrank());
		
		setDtoSurveyForm(dto, ts.getSurveyModule());
		
		return dto;
	}
	
	
	public JSONObject getSurveyModuleFromDto(GRTeachingStyleDto dto){
		JSONObject obj = new JSONObject();
		obj.put("q1", dto.getQ1());
		obj.put("q2", dto.getQ2());
		obj.put("q3", dto.getQ3());
		obj.put("q4", dto.getQ4());
		obj.put("q5", dto.getQ5());
		obj.put("q6", dto.getQ6());
		obj.put("q7", dto.getQ7());
		obj.put("q8", dto.getQ8());
		obj.put("q9", dto.getQ9());
		obj.put("q10", dto.getQ10());
		obj.put("q11", dto.getQ11());
		obj.put("q12", dto.getQ12());
		obj.put("q13", dto.getQ13());
		obj.put("q14", dto.getQ14());
		obj.put("q15", dto.getQ15());
		obj.put("q16", dto.getQ16());
		obj.put("q17", dto.getQ17());
		obj.put("q18", dto.getQ18());
		obj.put("q19", dto.getQ19());
		obj.put("q20", dto.getQ20());
		obj.put("q21", dto.getQ21());
		obj.put("q22", dto.getQ22());
		obj.put("q23", dto.getQ23());
		obj.put("q24", dto.getQ24());
		obj.put("q25", dto.getQ25());
		obj.put("q26", dto.getQ26());
		obj.put("q27", dto.getQ27());
		obj.put("q28", dto.getQ28());
		obj.put("q29", dto.getQ29());
		obj.put("q30", dto.getQ30());
		obj.put("q31", dto.getQ31());
		obj.put("q32", dto.getQ32());
		obj.put("q33", dto.getQ33());
		obj.put("q34", dto.getQ34());
		obj.put("q35", dto.getQ35());
		obj.put("q36", dto.getQ36());
		obj.put("q37", dto.getQ37());
		obj.put("q38", dto.getQ38());
		obj.put("q39", dto.getQ39());
		obj.put("q40", dto.getQ40());
	
		return obj;
	}
	
	private void setDtoSurveyForm(GRTeachingStyleDto dto, String serializeObject){
		if(serializeObject!=null && !"".equals(serializeObject.trim())){
			JSONObject obj = new JSONObject(serializeObject);
			
			dto.setQ1(obj.getString("q1"));
			dto.setQ2(obj.getString("q2"));
			dto.setQ3(obj.getString("q3"));
			dto.setQ4(obj.getString("q4"));
			dto.setQ5(obj.getString("q5"));
			dto.setQ6(obj.getString("q6"));
			dto.setQ7(obj.getString("q7"));
			dto.setQ8(obj.getString("q8"));
			dto.setQ9(obj.getString("q9"));
			dto.setQ10(obj.getString("q10"));
			dto.setQ11(obj.getString("q11"));
			dto.setQ12(obj.getString("q12"));
			dto.setQ13(obj.getString("q13"));
			dto.setQ14(obj.getString("q14"));
			dto.setQ15(obj.getString("q15"));
			dto.setQ16(obj.getString("q16"));
			dto.setQ17(obj.getString("q17"));
			dto.setQ18(obj.getString("q18"));
			dto.setQ19(obj.getString("q19"));
			dto.setQ20(obj.getString("q20"));
			dto.setQ21(obj.getString("q21"));
			dto.setQ22(obj.getString("q22"));
			dto.setQ23(obj.getString("q23"));
			dto.setQ24(obj.getString("q24"));
			dto.setQ25(obj.getString("q25"));
			dto.setQ26(obj.getString("q26"));
			dto.setQ27(obj.getString("q27"));
			dto.setQ28(obj.getString("q28"));
			dto.setQ29(obj.getString("q29"));
			dto.setQ30(obj.getString("q30"));
			dto.setQ31(obj.getString("q31"));
			dto.setQ32(obj.getString("q32"));
			dto.setQ33(obj.getString("q33"));
			dto.setQ34(obj.getString("q34"));
			dto.setQ35(obj.getString("q35"));
			dto.setQ36(obj.getString("q36"));
			dto.setQ37(obj.getString("q37"));
			dto.setQ38(obj.getString("q38"));
			dto.setQ39(obj.getString("q39"));
			dto.setQ40(obj.getString("q40"));
		}
	}
	
}
