package com.school.servlet;

import com.school.data.Student;
import com.school.model.StudentModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class StudentDataServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(StudentDataServlet.class.getName());

    /**
     * Handles GET request to the servlet
     *
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject obj = new JSONObject();
        JSONArray rowsArray = new JSONArray();
        StudentModel model = null;
        try {
            model = new StudentModel();
            List<Student> rowList = model.getStudentList();

            for(Student rowObj : rowList){

                JSONObject projectObject = new JSONObject();

                projectObject.put("id", rowObj.getId());
                projectObject.put("class_id", rowObj.getClassId());
                projectObject.put("first_name", rowObj.getFirstName());
                projectObject.put("last_name", rowObj.getLastName());
                projectObject.put("email", rowObj.getEmail());
                rowsArray.add(projectObject);
            }
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        } finally {
            model.close();
        }

        obj.put("student_list", rowsArray);

        response.setContentType("application/json");
        response.setHeader("Cache-Control", "nocache");
        response.setCharacterEncoding("utf-8");

        PrintWriter out = response.getWriter();
        out.println(obj.toString());
    }

}
