package com.school.servlet;

import javax.servlet.http.HttpServlet;
import com.school.Util;
import com.school.model.StudentModel;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

public class EditStudentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(AddStudentServlet.class.getName());

	/**
	 * Handles POST request to the servlet
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject json = new JSONObject();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		boolean operationSuccess = false;
		StudentModel model = null;
		try {
			String studentIdStr = request.getParameter("id");
			String classIdStr = request.getParameter("class_id");
			String firstName = request.getParameter("first_name");
			String lastName = request.getParameter("last_name");
			String email = request.getParameter("email");


			boolean invalidStudentId = Util.parameterCheck(studentIdStr);
            if(invalidStudentId){
                json.put("NOT ACCEPTABLE PARAMETERS", HttpServletResponse.SC_NOT_ACCEPTABLE);
                response.getWriter().write(json.toString());
                return;
            }
			boolean invalidClassId = Util.parameterCheck(classIdStr);
			boolean invalidFirstName = Util.parameterCheck(firstName);
			boolean invalidLastName = Util.parameterCheck(lastName);
			boolean invalidEmail = Util.parameterCheck(email);
			if(invalidClassId && invalidFirstName && invalidLastName && invalidEmail){
                json.put("BAD REQUEST", HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(json.toString());
				return;
			}
			model = new StudentModel();
			int studentId = Integer.parseInt(studentIdStr);
			if(classIdStr != null && !classIdStr.trim().isEmpty()){
                int classId = Integer.parseInt(classIdStr);
                operationSuccess = model.updateStudentInformation(studentId, classId, firstName, lastName, email);
            }else{
                operationSuccess = model.updateStudentInformation(studentId, firstName, lastName, email);
            }


			JSONObject responseObject = new JSONObject();
			if(operationSuccess) {
				responseObject.put("status", "Student is edited successfully!");
			} else {
				responseObject.put("status", "Failed to edit servlet!");
			}
			PrintWriter out = response.getWriter();
			out.println(responseObject.toString());
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
            json.put("NOT FOUND", HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write(json.toString());
		} finally {
			model.close();
		}
	}

}
