package coursemanagement;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

public class Administrator {

    private String name;
    private String username;
    private String password;
    private ArrayList<String> courseList;
    private PreparedStatement pst;

    /**
     * buat instance/objek dari kelas Administrator
     */
    public Administrator(){}

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName(){
        return name;
    }

    /**
     * Admin log in
     * @return true or false berdasarkan username dan password yang dimasukkan oleh admin
     */
    public boolean adminLogin(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Masukkan username: ");
        String username = sc.nextLine();
        System.out.print("Masukkan password: ");
        String pass = sc.nextLine();
        System.out.println("---------------------------------------");
        /*
        cek apakah username ada dalam database
         */
        if(isInDatabase(username)==false){
            if(validateAdmin(username,pass)==false){
                return false;
            }
            else{
                System.out.println();
                System.out.println("Incorrect password!");
                System.out.println("---------------------------------------");
            }
        }else{
            System.out.println();
//            System.out.println("Username doesn't exist!");
            System.out.println("---------------------------------------");
        }
        return true;
    }

    /**
     * validasi username dan password
     *  user username of the admin
     *  pass password of the admin
     * @return true or false
     */
    private boolean validateAdmin(String user, String pass){
        File file = new File("admin.txt"); //ambil dari file txt
        try{
            FileReader fr = new FileReader(file);
            Scanner sc = new Scanner(fr);
            /*
            //konfirmasi username dan password
             */
            while(sc.hasNext()){
                String username = sc.nextLine();
                String password = sc.nextLine();
                if(username.equals(user)){
                    if(password.equals(pass)){
                        return true;
                    }
                }
            }
        }
        catch(IOException e){
            System.out.println("Error: "+e.getMessage());
        }
        return false;
    }

    /**
     * cek apakah admin ada dalam database atau tidak
     *  username admin username
     * @return true or false
     */
    private boolean isInDatabase(String username) {
        String sql = "select admin_name,admin_username, admin_password from admin where admin_username=?";
        try {
            pst = Database.getConnection().prepareStatement(sql);
            pst.setString(1, username);
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
     * bidang administrator
     *
     *  rs =  Result set from query
     */
    private void initializeAccount(ResultSet rs) {

        try {
            this.name = rs.getString(1);
            this.username = rs.getString(2);
            this.password = rs.getString(3);
        } catch (SQLException ex) {
            System.out.println();
            System.out.println("Error: "+ ex.getMessage());
        }
    }

    /**
     * Cancel a course
     */
    public void cancelCourse() { //fungsi cancel course
        String sql = "select course_id, course_name,status from course";
        try {
            PreparedStatement pstmt = Database.getConnection().prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();
            courseList = new ArrayList<String>();
            while (rs.next()) {
                courseList.add(rs.getString(1) + " - " + rs.getString(2));
            }

        } catch (SQLException ex) {
            System.out.println();
            System.out.println("Error: "+ ex.getMessage());
        }
        /*
        Menampilkan courses
         */
        System.out.println("--------------------------------------");
        Iterator itr = courseList.iterator();//mendapatkan iterator dari arraylist
        while (itr.hasNext()) {//cek apakah ada elemen selanjutnya
            System.out.println(itr.next());//print elemen
        }
        System.out.println("--------------------------------------");
        System.out.println();
        try {
            System.out.print("Masukkan ID Jurusan yang ingin di cancel: ");
            Scanner sc = new Scanner(System.in);
            int id = Integer.parseInt(sc.nextLine());
            Course co = new Course();
            if(co.isInDatabase(id)==true) {
                String sql1 = "select status from course where course_id = ?";
                try {
                    PreparedStatement pstmt = Database.getConnection().prepareStatement(sql1);
                    pstmt.setInt(1, id);
                    ResultSet rs = pstmt.executeQuery();
                    while (rs.next()) {
                        if (rs.getString(1).equals("False")) {
                            System.out.println("Jurusan sudah dibatalkan!");
                            return;
                        }
                    }
                } catch (SQLException ex) {
                    System.out.println();
                    System.out.println("Error: "+ ex.getMessage());
                }
                changeCourseStatusFromDB(id, "False");
            }else{
                System.out.println("ID Jurusan tidak tersedia!!");
                System.out.println();
            }
        }catch (InputMismatchException e){
            System.out.println("Error!! Enter Integer type value!!");
        }catch (Exception ex){
            System.out.println("Error: "+ ex.getMessage());
        }

    }

    /**
     * resume a course
     */
    public void resumeCourse() {
        String sql = "select course_id, course_name,status from course";
        try {
            PreparedStatement pstmt = Database.getConnection().prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();
            courseList = new ArrayList<String>();
            while (rs.next()) {
                courseList.add(rs.getString(1) + " - " + rs.getString(2));
            }

        } catch (SQLException ex) {
            System.out.println();
            System.out.println("Error: "+ ex.getMessage());
        }

        System.out.println();
        System.out.println("--------------------------------------");
        Iterator itr = courseList.iterator();//mendapatkan iterator
        while (itr.hasNext()) {//cek apakah iterator memiliki elemen
            System.out.println(itr.next());//menampilkan elemen dan maju ke selanjutnya
        }
        System.out.println("--------------------------------------");
        System.out.println();
        try {
            System.out.print("Masukkan ID Jurusan yang ingin diresume: ");
            Scanner sc = new Scanner(System.in);
            int id = Integer.parseInt(sc.nextLine());
            Course co = new Course();
            if(co.isInDatabase(id)==true) {
                String sql1 = "select status from course where course_id = ?";
                try {
                    PreparedStatement pstmt = Database.getConnection().prepareStatement(sql1);
                    pstmt.setInt(1, id);
                    ResultSet rs = pstmt.executeQuery();
                    while (rs.next()) {
                        if (rs.getString(1).equals("True")) {
                            System.out.println("Jurusan sudah di resumed!");
                            return;
                        }
                    }
                } catch (SQLException ex) {
                    System.out.println();
                    System.out.println("Error: "+ ex.getMessage());
                }
                changeCourseStatusFromDB(id, "True");
            }else{
                System.out.println("Error!! ID Jurusan Sudah ada!!");
            }
        }catch (InputMismatchException e){
            System.out.println("Error! Masukkan hanya tipe data integer!!");
        }

    }

    /**
     * update status of course in database
     *id course id
     * newStatus course status
     */
    private void changeCourseStatusFromDB(int id,String newStatus){
        String sql = "update course set status=? where course_id=?";
        try {
            PreparedStatement pstmt = Database.getConnection().prepareStatement(sql);
            pstmt.setString(1, newStatus);
            pstmt.setInt(2,id);
            pstmt.executeUpdate();
            pstmt.close();
            System.out.println("Berhasil!! Status Jurusan berhasil diubah!!");
        } catch (SQLException ex) {
            System.out.println();
            System.out.println("Error: "+ ex.getMessage());
        }
    }

    /**
     * add matkul
     */
    public void addModule() {
        try {
            Course co = new Course();
            co.displayCourses();
            System.out.println();
            System.out.print("Masukkan ID Jurusan: ");
            Scanner sc = new Scanner(System.in);
            int courseID = Integer.parseInt(sc.nextLine());
            System.out.print("Masukkan Nama Matkul: ");
            String moduleName = sc.nextLine();
            System.out.print("Masukkan ID Matkul: ");
            int moduleID = Integer.parseInt(sc.nextLine());
            System.out.print("Masukkan Semester : ");
            int level = Integer.parseInt(sc.nextLine());
            System.out.println();
            Module mo = new Module();
            if(mo.isInDatabase(moduleID)==false) {
                insertModuleIntoDB(moduleID, moduleName, courseID, level);
            }else{
                System.out.println("Error!! Module ID already exists!!");
            }
        }
        catch (InputMismatchException e){
            System.out.println("Error! Enter integer type value!!");
        }catch (Exception ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }

    /**
     * add module to database
     *  moduleID module ID
     *  name module name
     *  courseID course ID
     *  level course level
     */
    private void insertModuleIntoDB(int moduleID,String name,int courseID,int level) {
        String sql = "INSERT into module (module_id,module_name, course_id,level) values (?,?,?,?)";
        try {
            PreparedStatement pst = Database.getConnection().prepareStatement(sql);
            pst.setInt(1, moduleID);
            pst.setString(2, name);
            pst.setInt(3,courseID);
            pst.setInt(4,level);
            pst.executeUpdate();
            pst.close();
            System.out.println();
            System.out.println("Berhasil! Matkul berhasil ditambahkan!!");
            System.out.println();
        } catch (SQLException ex) {
            System.out.println();
            System.out.println("Error: "+ ex.getMessage());
        }
    }

    /**
     * update Jurusan
     */
    public void updateCourse(){
        Course co = new Course();
        co.displayCourses();
        Scanner sc = new Scanner(System.in);
        try {
            System.out.print("Masukkan ID Jurusan: ");
            int courseID = Integer.parseInt(sc.nextLine());
            Course co1 = new Course();
            if(co1.isInDatabase(courseID)==true) {
                System.out.print("Masukkan Nama Baru Jurusan: ");
                String courseName = sc.nextLine();
                String sql = "update course set course_name=? where course_id=?";
                try {
                    PreparedStatement pstmt = Database.getConnection().prepareStatement(sql);
                    pstmt.setString(1, courseName);
                    pstmt.setInt(2, courseID);
                    pstmt.executeUpdate();
                    pstmt.close();
                    System.out.println();
                    System.out.println("Jurusan Berhasil Diubah!!");
                    System.out.println();
                } catch (SQLException ex) {
                    System.out.println();
                    System.out.println("Error: "+ ex.getMessage());
                }
            }else{
                System.out.println("Error!! ID Jurusan tidak tersedia!!");
                System.out.println();
            }
        }catch (InputMismatchException ex){
            System.out.println("Error:"+ex.getMessage());
        }catch (Exception e){
            System.out.println("Error:"+e.getMessage());
        }
    }

    /**
     * update Matkul
     */
    public void updateModule(){
        try {
            Course co = new Course();
            co.displayCourses();
            System.out.println();

            Scanner sc = new Scanner(System.in);
            System.out.print("Masukkan ID Jurusan: ");
            int courseID = Integer.parseInt(sc.nextLine());
            System.out.println();
            System.out.print("Masukkan Semester: ");
            int level = Integer.parseInt(sc.nextLine());
            System.out.println();
            co.displayModules(courseID, level);
            System.out.print("Masukkan ID Matkul yang ingin diubah: ");
            int moduleID = Integer.parseInt(sc.nextLine());
            System.out.println();
            Module mo = new Module();
            if(mo.isInDatabase(moduleID)==true) {
                System.out.print("Masukkan Nama matkul yang baru: ");
                String moduleName = sc.nextLine();
                System.out.println();

                String sql = "update module set module_name=? where module_id=? and level=?";
                try {
                    PreparedStatement pstmt = Database.getConnection().prepareStatement(sql);
                    pstmt.setString(1, moduleName);
                    pstmt.setInt(2, moduleID);
                    pstmt.setInt(3, level);
                    pstmt.executeUpdate();
                    pstmt.close();
                    System.out.println();
                    System.out.println("Matkul berhasil diubah!!!");
                    System.out.println();
                } catch (SQLException ex) {
                    System.out.println();
                    System.out.println("Error: "+ ex.getMessage());
                }
            }else{
                System.out.println("Error!! Matkul tidak tersedia!!");
            }
        }catch (InputMismatchException ex){
            System.out.println("Error: "+ex.getMessage());
        }catch (Exception e){
            System.out.println("Error: "+e.getMessage());
        }
    }

    /**
     * assign new instructor
     */
    public void assignNewInstructor(){
        try {
            System.out.print("Masukkan Nama Dosen: ");
            Scanner sc = new Scanner(System.in);
            String instructorName = sc.nextLine();
            System.out.print("Masukkan ID Dosen: ");
            int instructorID = Integer.parseInt(sc.nextLine());
            System.out.print("Masukkan ID Matkul: ");
            int moduleID = Integer.parseInt(sc.nextLine());
            System.out.print("Masukkan Email Dosen: ");
            String email = sc.nextLine();
            System.out.println();

            Instructor ins = new Instructor(instructorID, instructorName, moduleID, email);
            Module mo = new Module();
        /*
        checking if module Id exists in the database and instructor is teaching specified student
         */
            if (mo.isInDatabase(moduleID) == true) {
                if (ins.checkInstructor(moduleID, instructorID) == false) {
                    ins.insertInstructorIntoDB();
                } else {
                    System.out.println("DOSEN SUDAH MENGAMPU MATKUL TERSEBUT!");
                }
                updateInstructorOnModule(moduleID, instructorID);
            } else {
                System.out.println("Matkul tidak tersedia!");
            }
        }catch (InputMismatchException e){
            System.out.println("Error: "+e.getMessage());
        }catch (Exception ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }

    /**
     * assign instructor to course // dosen ke jurusan
     */
    public void assignInstructorToCourse(){

        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Masukkan ID Dosen:");
            int instructorID = Integer.parseInt(sc.nextLine());
            System.out.println("Masukkan ID Matkul:");
            int moduleID = Integer.parseInt(sc.nextLine());
            Instructor ins = new Instructor();
            ins.setModuleID(moduleID);
            if (ins.isInDatabase(instructorID) == true) {
                Module mo = new Module();
                if (mo.isInDatabase(moduleID) == true) {
                    if (ins.checkInstructor(moduleID, instructorID) == false) {
                        ins.setModuleID(moduleID);
                        ins.insertInstructorIntoDB();
                    } else {
                        System.out.println("DOSEN SUDAH MENGAMPU MATKUL TERSEBUT!");
                    }
                    updateInstructorOnModule(moduleID, instructorID);
                } else {
                    System.out.println("Matkul tidak tersedia");
                }
            } else {
                System.out.println("ID Dosen tidak tersedia");
            }
        }catch (InputMismatchException ex){
            System.out.println("Error! hanya masukkan angka!");
        }
    }

    /**
     * update instructor on module // dosen di matkul
     */
    public void updateInstructorOnModule(int moduleID,int instructorID){

        String sql = "update module set instructor_id=? where module_id=?";
        try {
            PreparedStatement pstmt = Database.getConnection().prepareStatement(sql);
            pstmt.setInt(1, instructorID);
            pstmt.setInt(2,moduleID);
            pstmt.executeUpdate();
            pstmt.close();
            System.out.println("Dosen berhasil diubah di Matkul!!");
            System.out.println();
        } catch (SQLException ex) {
            System.out.println();
            System.out.println("Error: "+ ex.getMessage());
        }
    }
    /**
     * update matkul
     */
    public void updateInstructor() {

        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Masukkan ID Dosen: ");
            int instructorID = Integer.parseInt(sc.nextLine());
            Instructor ins = new Instructor();
            if(ins.isInDatabase(instructorID)==true) {
                System.out.print("Masukkan email baru dosen: ");
                String email = sc.nextLine();

                String sql = "update instructor set instructor_email=? where instructor_id=?";
                try {
                    PreparedStatement pstmt = Database.getConnection().prepareStatement(sql);
                    pstmt.setString(1, email);
                    pstmt.setInt(2, instructorID);
                    pstmt.executeUpdate();
                    pstmt.close();
                    System.out.println("Dosen berhasil diubah di table dosen!!");
                } catch (SQLException ex) {
                    System.out.println();
                    System.out.println("Error: "+ ex.getMessage());
                }
            }else{
                System.out.println("ID Dosen tidak tersedia!!!");
                System.out.println();
            }
        }catch (InputMismatchException ex){
            System.out.println("Enter integer type value!!");
        }catch (Exception e){
            System.out.println("Error:"+e.getMessage());
        }
    }

    /**
     * result slip
     */
    public void resultSlip() {

        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Masukkan ID Mahasiswa yang ingin dibuat laporan: ");
            int studentID = Integer.parseInt(sc.nextLine());
            System.out.println("\n\n\n");
            Student st = new Student();
            if (st.isInDatabase(studentID) == true) {
                String sql = "select student_id,student_name,module_id,marks from student where student_id=?";
                try {
                    pst = Database.getConnection().prepareStatement(sql);
                    pst.setInt(1, studentID);
                    ResultSet rs = pst.executeQuery();
                    ArrayList<String> marks= new ArrayList<>();
                    System.out.println("--------------------------------------");
                    System.out.println("           Hasil Laporan              ");
                    System.out.println("--------------------------------------");
                    int total=0;
                    int pass=0;
                    int fail=0;

                    while(rs.next()){
                        this.name=rs.getString(2);
                        if(Integer.parseInt(rs.getString(4))>39) {
                            pass++;
                            marks.add("ID Jurusan: "+rs.getString(3)+"\t"+"Nilai: "+rs.getString(4)+"\t"+"Result: Lulus\n");
                        }else{
                            marks.add("ID Jurusan"+rs.getString(3)+"\t"+rs.getString(4)+"\t"+"Hasil: Gagal\n");
                            fail++;
                        }
                    }
                    System.out.println();
                    System.out.println("Nama Mahasiswa: "+getName());
                    System.out.println("ID Mahasiswa: "+studentID);
                    System.out.println();
                    System.out.println("--------------------------------------");
                    Iterator itr = marks.iterator();
                    while(itr.hasNext()){
                        System.out.println(itr.next());
                    }
                    System.out.println("--------------------------------------");
                    System.out.println();
                    if(pass>=fail){
                        System.out.println("Selamat ! Anda bisa naik ke semester lanjut!");
                    }else{
                        System.out.println("Maaf, kamu belum bisa lanjut!");
                    }
                    System.out.println("--------------------------------------");

                } catch (SQLException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } else {
                System.out.println("ID Mahasiswa tidak tersedia!!");
            }
        }catch (InputMismatchException e){
            System.out.println("Error! Hanya Masukkan angka!");
        }
        catch (Exception ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }
}
