package com.mahindra.dao.base.interfaces;

import java.util.ArrayList;

import com.mahindra.dao.base.GenericDAO;
import com.mahindra.database.pojo.QuestionPojo;
import com.mahindra.database.pojo.ResponsePojo;

public interface CodeDataDAO extends GenericDAO {
	
	public ArrayList<QuestionPojo> getFeedbackQuestions() throws Exception;
	
	public boolean insertResponse(ArrayList<ResponsePojo> userResponseList) throws Exception ;
}
