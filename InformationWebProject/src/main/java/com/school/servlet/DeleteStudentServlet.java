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

public class DeleteStudentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(DeleteStudentServlet.class.getName());

    /**
     * Handles POST request to the servlet
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        boolean success = false;
        StudentModel model = null;
        try {
            String studentIdStr = request.getParameter("id");

            boolean invalidStudentId = Util.parameterCheck(studentIdStr);
            if(invalidStudentId){
                response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
                return;
            }
            model = new StudentModel();
            int studentId = Integer.parseInt(studentIdStr);
            success = model.deleteStudent(studentId);

            JSONObject responseObject = new JSONObject();
            if(success) {
                responseObject.put("status", "Student is successfully removed!");
            } else {
                responseObject.put("status", "Failed to remove servlet!");
            }
            PrintWriter out = response.getWriter();
            out.println(responseObject.toString());
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } finally {
            model.close();
        }
    }
}
