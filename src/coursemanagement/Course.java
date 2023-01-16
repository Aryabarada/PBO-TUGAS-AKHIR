package coursemanagement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

public class Course { //Jurusan
    private int courseID;
    private String courseName;
    private ArrayList<String> courseLists;

    /**
     * Buat objek baru untuk Jurusan
     */

    //buat setter dan getter
    public Course() {
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCourseID() {
        return courseID;
    }

    public ArrayList<String> getCourseLists() {
        return courseLists;
    }

    public void setCourseLists(ArrayList<String> courseLists) {
        this.courseLists = courseLists;
    }

    /**
     * Tambah Jurusan
     */
    public void addCourse() {
        try {
            System.out.print("Masukkan ID Jurusan: ");
            Scanner sc = new Scanner(System.in);
            int id = Integer.parseInt(sc.nextLine());

            if(isInDatabase(id)==false) {
                System.out.print("Masukkan Nama Jurusan: ");
                String course = sc.nextLine();
                insertCourseIntoDB(id, course);
            }else{
                System.out.println();
                System.out.println("Jurusan Sudah tersedia!!");
            }
        }catch(InputMismatchException e){
            System.out.println("Error! Masukkan tipe data integer!!");
            System.out.println();
        }catch (Exception ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }

    /**
     * tambahkan matkul ke database
     * id course id
     * course course name
     */
    private void insertCourseIntoDB(int id, String course) {
        String sql = "INSERT into course (course_id, course_name) values (?,?)";
        try {
            PreparedStatement pst = Database.getConnection().prepareStatement(sql);
            pst.setInt(1, id);
            pst.setString(2, course);
            pst.executeUpdate();
            pst.close();
            System.out.println();
            System.out.println("Jurusan berhasil ditambahkan!!!");
            System.out.println();
        } catch (SQLException ex) {
            System.out.println();
            System.out.println("Error: "+ex.getMessage());
        }
    }

    /**
     * cek apakah mata kuliah sudah ada di database atau belum
     * id Course ID
     * @return boolean
     */
    public boolean isInDatabase(int id) {
        String sql = "select course_id,course_name from course where course_id = ?";
        try {
            PreparedStatement pst = Database.getConnection().prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                initializeAccount(rs);
                return true;
            }
        } catch (SQLException ex) {
            System.out.println();
            System.out.println("Error: "+ ex.getMessage());
        }
        return false;
    }

    /**
     * Initialize all fields for course
     * rs Result set from query
     */
    private void initializeAccount(ResultSet rs) {

        try {
            this.courseID = rs.getInt(1);
            this.courseName = rs.getString(2);
        } catch (SQLException ex) {
            System.out.println();
            System.out.println("Error: "+ ex.getMessage());
        }
    }
    /**
     * delete course
     */
    public void deleteCourse() {
        String sql = "select course_id, course_name from course";
        try {
            PreparedStatement pstmt = Database.getConnection().prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();
            courseLists = new ArrayList<String>();
            while (rs.next()) {
                courseLists.add("ID Jurusan: "+rs.getString(1) + " - " +"Nama Jurusan: "+ rs.getString(2));
            }

        } catch (SQLException ex) {
            System.out.println();
            System.out.println("Error: "+ ex.getMessage());
        }
        Iterator itr = courseLists.iterator();//mendapatkan iterator dari arraylist
        while (itr.hasNext()) {//cek apakah ada elemen berikutnya
            System.out.println(itr.next());//print elemen dan maju ke elemen berikutnya

        }
        System.out.println();
        System.out.println("--------------------------------------");
        try {
            System.out.print("Masukkan ID Jurusan yang ingin dihapus: ");
            Scanner sc = new Scanner(System.in);
            int id = Integer.parseInt(sc.nextLine());
            if(isInDatabase(id)==true) {
                deleteCourseFromDB(id);
            }else{
                System.out.println("ID Jurusan tidak ditemukan!!");
            }
        }catch (InputMismatchException ex){
            System.out.println("Error!! Masukkan Tipe data integer!!");
            System.out.println();
        }
    }

    /**
     * delete course/Jurusan from database
     *  id course id to be deleted
     */
    private void deleteCourseFromDB(int id){
        String sql = "delete from course where course_id=?";
        try {
            PreparedStatement pstmt = Database.getConnection().prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();
            System.out.println("Jurusan berhasil dihapus!!");
            System.out.println();
        } catch (SQLException ex) {
            System.out.println();
            System.out.println("Error: "+ ex.getMessage());
        }
    }

    /**
     * Tampilan Jurusan
     */
    public void displayCourses() {
        String sql = "select course_id,course_name from course where status=?";
        try {
            PreparedStatement pstmt = Database.getConnection().prepareStatement(sql);
            pstmt.setString(1,"True");
            ResultSet rs = pstmt.executeQuery();

            courseLists = new ArrayList<String>();
            while (rs.next()) {
                courseLists.add("ID Jurusan: "+rs.getString(1) + " - Nama Jurusan: " + rs.getString(2));
            }
        } catch (SQLException ex) {
            System.out.println();
            System.out.println("Error: "+ ex.getMessage());
        }
        System.out.println("--------------------------------------");
        Iterator itr = courseLists.iterator();//mendapatkan iterator dari arraylist
        while (itr.hasNext()) {//cek apakah ada elemen berikutnya
            System.out.println(itr.next());//print elemen dan maju ke elemen berikutnya
        }
        System.out.println("--------------------------------------");
        System.out.println();
    }

    /**
     * display modules
     *  courseID course id
     *  level course level
     */
    public void displayModules(int courseID,int level) {
        String sql = "select module_id,module_name from module where course_id=? and level=?";
        try {
            PreparedStatement pstmt = Database.getConnection().prepareStatement(sql);
            pstmt.setInt(1, courseID);
            pstmt.setInt(2, level);
            ResultSet rs = pstmt.executeQuery();

            courseLists = new ArrayList<String>();
            while (rs.next()) {
                courseLists.add(rs.getString(1) + " - " + rs.getString(2));
            }
        } catch (SQLException ex) {
            System.out.println();
            System.out.println("Error: "+ ex.getMessage());
        }
        Iterator itr = courseLists.iterator();//mendapatkan iterator dari arraylist
        while (itr.hasNext()) {//cek apakah ada elemen berikutnya
            System.out.println(itr.next());//print elemen dan maju ke elemen berikutnya
        }
    }
}