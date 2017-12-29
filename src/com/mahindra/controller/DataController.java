/*
 * Author: Jonathan Almeida 
 */

package com.mahindra.controller;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mahindra.common.constants.CommonConstants;
import com.mahindra.dao.base.impl.CodeDataDAOImpl;
import com.mahindra.dao.base.interfaces.CodeDataDAO;
import com.mahindra.database.pojo.QuestionPojo;
import com.mahindra.database.pojo.ResponsePojo;

public class DataController extends HttpServlet {
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Gson gsonResponse = new Gson();
		try {
			
			CodeDataDAO dao = new CodeDataDAOImpl();
			System.out.println("FEEDBACK QUESTIONS----\n"+gsonResponse.toJson(dao.getFeedbackQuestions()));
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String message = null;
		String jdata = null;
		message = request.getParameter("message");
		jdata = request.getParameter("JDATA");
		CodeDataDAO dao = null;
		System.out.println("DataController:doPost @@@@@ message :: " + message + " jdata :: " + jdata);

		if (null != message && CommonConstants.OP_GET_FEEDBACK_QUESTIONS.equalsIgnoreCase(message)) {
			ArrayList<QuestionPojo> questionList = null;
			Gson gsonResponse = new Gson();
			
			try {
				
				dao = new CodeDataDAOImpl();
				questionList = dao.getFeedbackQuestions();
				System.out.println("FEEDBACK QUESTIONS----\n"+gsonResponse.toJson(questionList));
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(gsonResponse.toJson(questionList));
				
			} catch (Exception e) {
				System.out.println("ERROR IN DataController-getFeedbackQuestions"+e.getMessage());
				e.printStackTrace();
			}
			
		}
		else if (null != message && CommonConstants.OP_CAPTURE_RESPONSE.equalsIgnoreCase(message)) {
			Gson gson = new Gson();
			Type temp = new TypeToken<HashMap<String,ArrayList<ResponsePojo>>>(){}.getType();
			
			try {
				
				HashMap<String, ArrayList<ResponsePojo>> userResponse = gson.fromJson(jdata, temp);
				ArrayList<ResponsePojo> userResponseList = userResponse.get("response");
				dao = new CodeDataDAOImpl();
				boolean insertStatus = dao.insertResponse(userResponseList);
				System.out.println("RESPONSE CAPTURED ---"+insertStatus);
				response.getWriter().write(""+insertStatus);
				
			} catch (Exception e) {
				
				System.out.println("ERROR IN DataController-getFeedbackQuestions"+e.getMessage());
				e.printStackTrace();
				
			}
		}
	}

}
