package com.school.servlet;

import com.school.Util;
import com.school.data.Student;
import com.school.model.StudentModel;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

public class AddStudentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(AddStudentServlet.class.getName());

    /**
     * Handles POST request to the servlet
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        boolean operationSuccess = false;
        StudentModel model = null;
        try {
            String classIdStr = request.getParameter("class_id").trim();
            String firstName = request.getParameter("first_name").trim();
            String lastName = request.getParameter("last_name").trim();
            String email = request.getParameter("email").trim();


            boolean parameterCheckValue = Util.parameterCheck(classIdStr, firstName, lastName, email);
            if(parameterCheckValue){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            // Data validation
            model = new StudentModel();
            int classId = Integer.parseInt(classIdStr);
            Student student = new Student();
            student.setClassId(classId);
            student.setFirstName(firstName);
            student.setLastName(lastName);
            student.setEmail(email);
            operationSuccess = model.addStudent(student);

            JSONObject responseObject = new JSONObject();
            if(operationSuccess) {
                responseObject.put("status", "Operation is successfully finished.");
            } else {
                responseObject.put("status", "Operation is failed.");
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
