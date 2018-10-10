package com.school.model;

import com.school.data.Student;
import com.school.db.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class StudentModel {

    protected static final Logger LOGGER = Logger.getLogger(StudentModel.class.getName());
    protected Connection conn;

    public StudentModel() throws ClassNotFoundException, SQLException{
        try {
            conn = DataBase.getConnection(true);
        }catch (Exception e){
            LOGGER.severe(e.getMessage());
        }
    }

    /**
     * Retrieve all servlet params from db
     *
     * @return List of Student
     */
    public  List<Student> getStudentList() throws ClassNotFoundException, SQLException{
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Student> result = new ArrayList<>();

        try {
            stmt = conn.prepareStatement("SELECT sd.id, sd.class_id, sd.first_name, sd.last_name, sd.email " +
                    "FROM student_details sd " +
                    "LEFT JOIN classes c ON c.id = sd.class_id");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setClassId(rs.getInt("class_id"));
                student.setFirstName(rs.getString("first_name"));
                student.setLastName(rs.getString("last_name"));
                student.setEmail(rs.getString("email"));

                result.add(student);
            }
        } catch (SQLException se) {
            LOGGER.severe(se.getMessage());
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
                LOGGER.severe(se2.getMessage());
            }
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException se) {
                LOGGER.severe(se.getMessage());
            }
        }

        return result;
    }

    /**
     * Update servlet information
     *
     * @params studentId, classId, firstName, lastName, email
     * @return True if operation is successful, false otherwise
     */
    public boolean updateStudentInformation(int studentId, int classId, String firstName, String lastName, String email) {
        PreparedStatement stmt = null;
        Connection conn = null;
        boolean success = false;

        try {
            conn = DataBase.getConnection(true);
            stmt = conn.prepareStatement("UPDATE student_details SET class_id = COALESCE(?, class_id), " +
                    "first_name = COALESCE(?, first_name), last_name = COALESCE(?, last_name), " +
                    "email = COALESCE(?, email)  WHERE id = ?");
            stmt.setInt(1, classId);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            stmt.setString(4, email);
            stmt.setInt(5, studentId);

            stmt.executeUpdate();

            success = true;
        } catch (SQLException se) {
            LOGGER.severe(se.getMessage());
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
                LOGGER.severe(se2.getMessage());
            }
        }

        return success;
    }


    /**
     * Update servlet information
     *
     * @params studentId, firstName, lastName, email
     * @return True if operation is successful, false otherwise
     */
    public boolean updateStudentInformation(int studentId, String firstName, String lastName, String email) {
        PreparedStatement stmt = null;
        Connection conn = null;
        boolean success = false;

        try {
            conn = DataBase.getConnection(true);
            stmt = conn.prepareStatement("UPDATE student_details SET first_name = COALESCE(?, first_name), " +
                    "last_name = COALESCE(?, last_name), email = COALESCE(?, email)  WHERE id = ?");
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, email);
            stmt.setInt(4, studentId);

            stmt.executeUpdate();

            success = true;
        } catch (SQLException se) {
            LOGGER.severe(se.getMessage());
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
                LOGGER.severe(se2.getMessage());
            }
        }

        return success;
    }

    /**
     * Create a new servlet entry
     *
     * @param studentInformation
     * @return True if operation is successful, false otherwise
     */
    public boolean addStudent(Student studentInformation) {
        PreparedStatement stmt = null;
        Connection conn = null;
        boolean success = false;

        try {
            conn = DataBase.getConnection(true);
            stmt = conn.prepareStatement("INSERT INTO student_details (class_id, first_name, last_name, email) VALUES (?, ?, ?, ?)");
            stmt.setInt(1, studentInformation.getClassId());
            stmt.setString(2, studentInformation.getFirstName());
            stmt.setString(3, studentInformation.getLastName());
            stmt.setString(4, studentInformation.getEmail());

            stmt.executeUpdate();

            success = true;
        } catch (SQLException se) {
            LOGGER.severe(se.getMessage());
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
                LOGGER.severe(se2.getMessage());
            }
        }

        return success;
    }

    /**
     * Delete a servlet entry
     *
     * @param studentId
     * @return True if operation is successful, false otherwise
     */
    public boolean deleteStudent(int studentId) {
        PreparedStatement stmt = null;
        Connection conn = null;
        int i = 0;
        try {
            conn = DataBase.getConnection(true);
            stmt = conn.prepareStatement("DELETE FROM student_details WHERE id = ?;");
            stmt.setInt(1, studentId);

            i = stmt.executeUpdate();
        } catch (SQLException se) {
            LOGGER.severe(se.getMessage());
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
                LOGGER.severe(se2.getMessage());
            }
        }

        return i > 0;
    }

    /**
     * Close the connection
     */
    public void close() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            // do nothing
            LOGGER.severe(e.getMessage());
        }
    }


}
