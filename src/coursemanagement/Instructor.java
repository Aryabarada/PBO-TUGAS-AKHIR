package coursemanagement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

public class Instructor extends Akun{

    private String email;
    private ArrayList<String> courses;
    private ArrayList<String> students;
    private int moduleID;

    /**
     * Buat objek baru untuk Dosen
     */
    public Instructor(){
    }

    public Instructor(int ID,String name,int moduleID,String email){
        this.ID = ID;
        this.name=name;
        this.moduleID=moduleID;
        this.email=email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName(){
        return name;
    }
    public int getID() {
        return ID;
    }

    public int getModuleID() {
        return moduleID;
    }

    public void setModuleID(int moduleID) {
        this.moduleID = moduleID;
    }

    public ArrayList<String> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<String> students) {
        this.students = students;
    }

    public ArrayList<String> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<String> courses) {
        this.courses = courses;
    }

    /**
     * cek apakah dosen sudah ada di database atau belum
     * id Instructor ID
     * @return boolean
     */
    @Override
    public boolean isInDatabase(int id) {
        String sql = "select instructor_id,instructor_name,instructor_email,module_id from instructor where instructor_id = ?";
        try {
            pst = Database.getConnection().prepareStatement(sql);
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
     *  inisialisasi semua field untuk dosen
     *  rs Result set from query
     */
    private void initializeAccount(ResultSet rs) {

        try {
            this.ID = rs.getInt(1);
            this.name = rs.getString(2);
            this.email=rs.getString(3);
            this.moduleID=rs.getInt(4);
        } catch (SQLException ex) {
            System.out.println();
            System.out.println("Error: "+ ex.getMessage());
        }
    }

    /**
     * display modules/matkul yang ada di database
     */
    public void displayModules() {
        String sql = "select module_id,module_name from module where instructor_id=?";
        try {
            PreparedStatement pstmt = Database.getConnection().prepareStatement(sql);
            pstmt.setInt(1, getID());
            ResultSet rs = pstmt.executeQuery();

            courses = new ArrayList<String>();
            while (rs.next()) {
                courses.add("ID Matkul: "+rs.getString(1) + " - " +"Nama Matkul: "+ rs.getString(2));
            }
        } catch (SQLException ex) {
            System.out.println();
            System.out.println("Error: "+ ex.getMessage());
        }

        Iterator itr = courses.iterator();//mendapatkan iterator dari arraylist
        while (itr.hasNext()) {//cek apakah masih ada elemen
            System.out.println(itr.next());//print elemen dan maju ke elemen berikutnya
        }
    }

    /**
     * insert instructor to database
     */
    public void insertInstructorIntoDB() {
        String sql = "insert into instructor(instructor_id,instructor_name,instructor_email,module_id) values (?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = Database.getConnection().prepareStatement(sql);
            pstmt.setInt(1, getID());
            pstmt.setString(2, getName());
            pstmt.setString(3, this.email);
            pstmt.setInt(4, this.moduleID);
            pstmt.executeUpdate();
            pstmt.close();
            System.out.println();
            System.out.println("Dosen Berhasil Ditambahkan!!");
        } catch (SQLException ex) {
            System.out.println();
            System.out.println("Error: "+ ex.getMessage());
        }
    }

    /**
     * cek apakah dosen sudah mengajar matkul atau belum
     *  moduleID Module ID
     *  instructorID Instructor ID
     * @return boolean
     */
    public boolean checkInstructor(int moduleID,int instructorID){
        String sql = "select * from instructor where instructor_id = ? and module_id=?";
        try {
            pst = Database.getConnection().prepareStatement(sql);
            pst.setInt(1, instructorID);
            pst.setInt(2,moduleID);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println();
            System.out.println("Error: "+ ex.getMessage());
        }
        return false;
    }

    /**
     * tampilkan daftar mahasiswa yang diampu
     */
    public void displayStudentFromInstructor(){
        Student st = new Student();
        st.displayStudents(getID());
    }

    /**
     * tampilkan daftar dosen
     * @param id
     */
    public void displayInstructors(int id){
        String sql = "select module_id from student where student_id=?";
        try {
            PreparedStatement pstmt = Database.getConnection().prepareStatement(sql);
            pstmt.setInt(1,id);
            ResultSet rs = pstmt.executeQuery();

            ArrayList<String> instructors = new ArrayList<String>();

            while (rs.next()) {
                String sql1 = "select instructor_id, instructor_name,module_id from instructor where module_id = " +
                        rs.getString(1);
                pst = Database.getConnection().prepareStatement(sql1);
                ResultSet instructorInClass = pst.executeQuery();
                try {
                    if (instructorInClass.next()) {
                        instructors.add("ID Dosen: " + instructorInClass.getString(1) + " - "
                                + "Nama Dosen: " + instructorInClass.getString(2) + " - " +
                                "ID Matkul: " + instructorInClass.getString(3));
                    }
                }
                catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
            System.out.println();
            Iterator itr = instructors.iterator();//mendapatkan iterator dari arraylist
            while (itr.hasNext()) {//cek apakah masih ada elemen
                System.out.println(itr.next());//print elemen dan maju ke elemen berikutnya
            }
            System.out.println();
            System.out.println("---------------------------------------");
            System.out.println();
        } catch (SQLException ex) {
            System.out.println();
            System.out.println("Error: "+ ex.getMessage());
        }
    }

    /**
     * add grade
     */
    public void addGrade() {
        System.out.println("---------------------------------------");
        displayStudentFromInstructor();
        System.out.println("---------------------------------------");
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Masukkan ID Mahasiswa yang ingin dinilai: ");
            int studentID = Integer.parseInt(sc.nextLine());
            System.out.print("Masukkan ID Matkul Mahasiswa: ");
            int moduleID = Integer.parseInt(sc.nextLine());
            Student st = new Student();
            if(st.checkStudent(moduleID,studentID)==true) {
                if (check(studentID) == true) {
                    if (st.isInDatabase(studentID) == true) {
                        System.out.print("Masukkan Nilai: ");
                        int marks = Integer.parseInt(sc.nextLine());
                        String sql = "update student set marks= ? where student_id= ? and module_id= ?";
                        try {
                            PreparedStatement pstmt = Database.getConnection().prepareStatement(sql);
                            pstmt.setInt(1, marks);
                            pstmt.setInt(2, studentID);
                            pstmt.setInt(3,moduleID);
                            pstmt.executeUpdate();
                            pstmt.close();
                            System.out.println();
                            System.out.println("Nilai berhasil ditambahkan!!");
                            System.out.println();
                        } catch (SQLException ex) {
                            System.out.println();
                            System.out.println("Error: "+ ex.getMessage());
                        }
                    } else {
                        System.out.println("ID Tidak tersedia!");
                    }
                } else {
                    System.out.println();
                    System.out.println("Error!!Tidak bisa memberikan nilai ke matkul yang tidak diajarkan!!");
                }
            }else{
                System.out.println();
                System.out.println("ID Mahasiswa yang anda masukkan tidak mempelajari matkul tersebut!");
                System.out.println();
            }
        }catch(InputMismatchException e){
            System.out.println("Error! Enter integer type value!");
        }
    }

    /**
     * cek apakah dosen mengajar mahasiswa tertentu
     * id student ID
     * @return boolean
     */
    public boolean check(int id){
        String sql="select module_id from student where student_id=?";
        try{
            pst=Database.getConnection().prepareStatement(sql);
            pst.setInt(1,id);
            ResultSet rs = pst.executeQuery();

            while(rs.next()){
                if(checkInstructor(Integer.parseInt(rs.getString(1)),getID())==true){
                    return true;
                }
            }
        }catch (SQLException e){
            System.out.println();
            System.out.println("Error: "+ e.getMessage());
        }
        return  false;
    }
}
