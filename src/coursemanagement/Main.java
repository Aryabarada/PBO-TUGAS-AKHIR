package coursemanagement;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void mainMenu(){
        Scanner scan = new Scanner(System.in);
        System.out.println("************************************");
        System.out.println("Selamat datang di Aplikasi KRS");
        System.out.println("************************************");
        int selectedOption = 0;
        System.out.println();
        System.out.println("Ingin Login Sebagai ?");
        System.out.println("");
        System.out.println("---------------------------------------");
        System.out.println("          1. Mahasiswa                 ");
        System.out.println("          2. Dosen                     ");
        System.out.println("          3. Course Administrator      ");
        System.out.println("          4. Exit                      ");
        System.out.println("---------------------------------------");
        System.out.print("\nMasukkan Pilihan: ");
        try{
            selectedOption = scan.nextInt();
            switch (selectedOption){
                case 1:
                    System.out.println("\n\n\n");
                    studentMenu();
                    break;

                case 2:
                    System.out.println("\n\n\n");
                    instructorMenu();
                    break;

                case 3:
                    System.out.println("\n\n\n");
                    adminMenu();
                    break;

                case 4:
                    System.out.println("Menutup Aplikasi...");
                    System.exit(0);

                default:
                    System.out.println("Error! Hanya Masukkan nomor yang tersedia!!!");
                    System.out.println("\n\n");
                    mainMenu();
            }
        }catch(InputMismatchException e) {
            scan.next();
            System.out.println("Hanya Masukkan nomor yang tersedia!!!");
            System.out.println();
            System.out.println();
            System.out.println();
            mainMenu();
        }
    }
    public static void studentMenu(){
        Scanner scan = new Scanner(System.in);
        System.out.println("************************************");
        System.out.println("         Menu Mahasiswa             ");
        System.out.println("************************************");
        int selectedOption = 0;
        System.out.println();
        System.out.println("Apakah Anda sudah Terdaftar?");
        System.out.println("");
        System.out.println("--------------------------------------");
        System.out.println("1. sudah");
        System.out.println("2. belum");
        System.out.println("3. Return to Main menu");
        System.out.println("4. Exit");
        System.out.println("--------------------------------------");
        System.out.println();
        System.out.print("\nMasukkan Pilihan: ");
        try{ // try catch untuk memastikan inputan berupa angka
            selectedOption = scan.nextInt();
            switch (selectedOption){
                case 1:
                    System.out.println("\n\n\n");
                    enrolledStudentMenu();
                    break;

                case 2:
                    System.out.println("\n\n\n");
                    Student st1 = new Student();
                    st1.enroll();
                    System.out.println("---------------------------------------");
                    break;

                case 3:
                    System.out.println("\n\n\n");
                    mainMenu();
                    break;

                case 4:
                    System.out.println("Menutup Program...");
                    System.exit(0);

                default:
                    System.out.println("Error! Hanya Masukkan nomor yang tersedia!!!");
                    System.out.println("\n\n");
                    studentMenu();
            }
        }catch(InputMismatchException e) {
            scan.next();
            System.out.println("Hanya Masukkan nomor yang tersedia!!!");
            System.out.println();
            System.out.println();
            System.out.println();
            studentMenu();
        }

    }
    public static void enrolledStudentMenu(){
        Scanner scan = new Scanner(System.in);
        System.out.println("************************************");
        System.out.println("     Menu Pendaftaran Mahasiswa      ");
        System.out.println("************************************");
        int selectedOption = 0;
        System.out.println();
        System.out.println("");
        System.out.println("--------------------------------------");
        System.out.println("        1. Lihat Dosen                ");
        System.out.println("        2. Kembali Ke menu Mahasiswa  ");
        System.out.println("        3. Return to Main Menu        ");
        System.out.println("        4. Exit                       ");
        System.out.println("--------------------------------------");
        System.out.println();
        System.out.print("\nMasukkan Pilihan: ");
        try{
            selectedOption = scan.nextInt();
            switch (selectedOption){
                case 1:
                    System.out.println("\n\n");
                    System.out.print("Masukkan ID Mahasiswa:");
                    Scanner sc = new Scanner(System.in);
                    int studentID=sc.nextInt();
                    Student st = new Student();
                    if(st.isInDatabase(studentID)){
                        System.out.println("---------------------------------------");
                        System.out.println("             Daftar Dosen          ");
                        System.out.println("---------------------------------------");
                        st.displayInstructorsOnStudent();
                    }else{
                        System.out.println("ID Mahasiswa tidak ditemukan!!");
                        System.out.println("\n\n\n");
                        studentMenu();
                    }
                    break;

                case 2:
                    System.out.println("\n\n\n");
                    studentMenu();
                    break;

                case 3:
                    System.out.println("\n\n\n");
                    mainMenu();
                    break;

                case 4:
                    System.out.println("Menutup Aplikasi...");
                    System.exit(0);

                default:
                    System.out.println("Error! Hanya Masukkan nomor yang tersedia!!!");
                    System.out.println("\n\n");
                    enrolledStudentMenu();
            }
        }catch(InputMismatchException e) {
            scan.next();
            System.out.println("Hanya Masukkan nomor yang tersedia!!!");
            System.out.println();
            System.out.println();
            System.out.println();
            enrolledStudentMenu();
        }
    }

    public static void instructorMenu(){
        Scanner scan = new Scanner(System.in);
        System.out.println("************************************");
        System.out.println("          Menu Dosen           ");
        System.out.println("************************************");
        int selectedOption = 0;
        System.out.println();
        System.out.println("--------------------------------------");
        System.out.println("          1. Lihat Matkul             ");
        System.out.println("          2. Lihat Mahasiswa          ");
        System.out.println("          3. Tambahkan Nilai          ");
        System.out.println("          4. Return to Main Menu       ");
        System.out.println("          5. Exit                      ");
        System.out.println("---------------------------------------");
        System.out.print("\nMasukkan Pilihan: ");
        try{
            selectedOption = scan.nextInt();
            switch (selectedOption){
                case 1:
                    System.out.println("\n\n\n");
                    System.out.print("Masukkan ID Dosen: ");
                    Scanner sc = new Scanner(System.in);
                    int instructorID = Integer.parseInt(sc.nextLine());
                    Instructor ins = new Instructor();
                    if(ins.isInDatabase(instructorID)==true) {
                        System.out.println("\n\n\n");
                        System.out.println("          Daftar Matkul                 ");
                        System.out.println("---------------------------------------");
                        ins.displayModules();
                        System.out.println("---------------------------------------");
                    }
                    break;

                case 2:
                    System.out.println("\n\n\n");
                    System.out.print("Masukkan ID Dosen: ");
                    Scanner sc1 = new Scanner(System.in);
                    int instructorID1 = Integer.parseInt(sc1.nextLine());
                    Instructor ins1 = new Instructor();
                    if(ins1.isInDatabase(instructorID1)==true) {
                        System.out.println("\n\n\n");
                        System.out.println("          Dafar Mahasiswa              ");
                        System.out.println("---------------------------------------");
                        ins1.displayStudentFromInstructor();
                        System.out.println("---------------------------------------");
                    }
                    break;

                case 3:
                    System.out.println("\n\n\n");
                    System.out.print("Masukkan ID Dosen: ");
                    Scanner sc2 = new Scanner(System.in);
                    int instructorID2 = Integer.parseInt(sc2.nextLine());
                    Instructor ins2 = new Instructor();
                    if(ins2.isInDatabase(instructorID2)==true) {
                        System.out.println("\n\n\n");
                        System.out.println("---------------------------------------");
                        System.out.println("          Tambahkan Nilai              ");
                        ins2.addGrade();
                        System.out.println();
                        System.out.println("---------------------------------------");
                    }
                    break;

                case 4:
                    System.out.println("\n\n\n");
                    mainMenu();
                    break;

                case 5:
                    System.out.println("Menutup Program...");
                    System.exit(0);

                default:
                    System.out.println("Error! Hanya Masukkan nomor yang tersedia!!!");
                    System.out.println("\n\n");
                    instructorMenu();
            }
        }catch(InputMismatchException e) {
            scan.next();
            System.out.println("Hanya Masukkan nomor yang tersedia!!!");
            System.out.println();
            System.out.println();
            System.out.println();
            instructorMenu();
        }
    }

    public static void adminMenu(){
        Scanner scan = new Scanner(System.in);
        System.out.println("************************************");
        System.out.println("          Admin Menu                ");
        System.out.println("************************************");
        int selectedOption = 0;
        System.out.println();
        System.out.println("--------------------------------------");
        System.out.println("          1. Log In                   ");
        System.out.println("          2. Return to Main Menu       ");
        System.out.println("          3. Exit                      ");
        System.out.println("---------------------------------------");
        System.out.println();
        System.out.print("\nMasukkan Pilihan: ");
        try{
            selectedOption = scan.nextInt();
            switch (selectedOption){
                case 1:
                    System.out.println("\n\n\n");
                    System.out.println("---------------------------------------");
                    System.out.println("          Admin Login                   ");
                    System.out.println("---------------------------------------");
                    Scanner sc = new Scanner(System.in);
                    Administrator ad = new Administrator();
                    if(ad.adminLogin()==true){
                        System.out.println();
                        System.out.println("login successful....");
                        System.out.println("\n\n\n");
                        adminLoggedInMenu();
                    }else{
                        System.out.println("\n\n\n");
                        adminMenu();
                    }
                    break;

                case 2:
                    System.out.println("\n\n\n");
                    mainMenu();
                    break;

                case 3:
                    System.out.println("Menutup Program...");
                    System.exit(0);

                default:
                    System.out.println("Hanya Masukkan nomor yang tersedia!!!");
                    System.out.println("\n\n");
                    adminMenu();
            }
        }catch(InputMismatchException e) {
            scan.next();
            System.out.println("Hanya Masukkan nomor yang tersedia!!!");
            System.out.println();
            System.out.println();
            System.out.println();
            adminMenu();
        }
    }
    public static void adminLoggedInMenu(){
        Scanner scan = new Scanner(System.in);
        System.out.println("************************************");
        System.out.println("          Admin Panel               ");
        System.out.println("************************************");
        int selectedOption = 0;
        System.out.println();
        System.out.println("--------------------------------------");
        System.out.println("          1. Tambah Jurusan           ");
        System.out.println("          2. Tambah Matkul             ");
        System.out.println("          3. Hapus Jurusan             ");
        System.out.println("          4. Edit Jurusan              ");
        System.out.println("          5. Edit Matkul               ");
        System.out.println("          6. Tambah Dosen              ");
        System.out.println("          7. Tugas Dosen               ");
        System.out.println("          8. Cancel Jurusan            ");
        System.out.println("          9. Resume Course             ");
        System.out.println("          10. Update Dosen             ");
        System.out.println("          11. Buat Laporan             ");
        System.out.println("          12. Log Out                  ");
        System.out.println("          13. Return to Main menu      ");
        System.out.println("          14. Exit                     ");
        System.out.println("");
        System.out.println("---------------------------------------");
        System.out.print("\nMasukkan Pilihan: ");
        try{
            Administrator ad = new Administrator();
            selectedOption = scan.nextInt();
            switch (selectedOption){
                case 1:
                    System.out.println("\n\n\n");
                    System.out.println("--------------------------------------");
                    System.out.println("        Tambah Jurusan               ");
                    System.out.println("--------------------------------------");
                    Course co = new Course();
                    co.addCourse();
                    System.out.println("--------------------------------------");
                    System.out.println();
                    break;

                case 2:
                    System.out.println("\n\n\n");
                    System.out.println("--------------------------------------");
                    System.out.println("          Tambah Matkul              ");
                    System.out.println("--------------------------------------");
                    ad.addModule();
                    System.out.println("--------------------------------------");
                    System.out.println();
                    break;

                case 3:
                    System.out.println("\n\n\n");
                    System.out.println("--------------------------------------");
                    System.out.println("           Delete Course              ");
                    System.out.println("--------------------------------------");
                    Course co1 = new Course();
                    co1.deleteCourse();
                    System.out.println("--------------------------------------");
                    System.out.println();
                    break;

                case 4:
                    System.out.println("\n\n\n");
                    System.out.println("--------------------------------------");
                    System.out.println("           Update Jurusan             ");
                    System.out.println("--------------------------------------");
                    ad.updateCourse();
                    System.out.println();
                    System.out.println("--------------------------------------");
                    break;

                case 5:
                    System.out.println("\n\n\n");
                    System.out.println("--------------------------------------");
                    System.out.println("            Update Matkul            ");
                    System.out.println("--------------------------------------");
                    ad.updateModule();
                    System.out.println();
                    System.out.println("--------------------------------------");
                    break;

                case 6:
                    System.out.println("\n\n\n");
                    System.out.println("--------------------------------------");
                    System.out.println("       Tambah Dosen Baru              ");
                    System.out.println("--------------------------------------");
                    ad.assignNewInstructor();
                    System.out.println();
                    System.out.println("--------------------------------------");
                    break;

                case 7:
                    System.out.println("\n\n\n");
                    System.out.println("--------------------------------------");
                    System.out.println("        Tugas Dosen                   ");
                    System.out.println("--------------------------------------");
                    ad.assignInstructorToCourse();
                    System.out.println();
                    System.out.println("--------------------------------------");
                    break;

                case 8:
                    System.out.println("\n\n\n");
                    System.out.println("--------------------------------------");
                    System.out.println("            Cancel Jurusan            ");
                    System.out.println("--------------------------------------");
                    ad.cancelCourse();
                    System.out.println();
                    System.out.println("--------------------------------------");
                    break;

                case 9:
                    System.out.println("\n\n\n");
                    System.out.println("--------------------------------------");
                    System.out.println("          Resume Course               ");
                    System.out.println("--------------------------------------");
                    ad.resumeCourse();
                    System.out.println();
                    System.out.println("--------------------------------------");
                    break;

                case 10:
                    System.out.println("\n\n\n");
                    System.out.println("--------------------------------------");
                    System.out.println("       Update Dosen              ");
                    System.out.println("--------------------------------------");
                    ad.updateInstructor();
                    System.out.println();
                    System.out.println("--------------------------------------");
                    break;

                case 11:
                    System.out.println("\n\n\n");
                    ad.resultSlip();
                    break;

                case 12:
                    System.out.println("\n\n\n");
                    adminMenu();
                    break;

                case 13:
                    System.out.println("\n\n\n");
                    mainMenu();
                    break;

                case 14:
                    System.out.println("Keluar dari Program...");
                    System.exit(0);

                default:
                    System.out.println("Error! Hanya Masukkan angka!!!");
                    System.out.println("\n\n");
                    adminLoggedInMenu();
            }
        }catch(InputMismatchException e) {
            scan.next();
            System.out.println("Please Hanya Masukkan angka!!!");
            System.out.println();
            System.out.println();
            System.out.println();
            adminLoggedInMenu();
        }
    }
    //main method to run whole program
    public static void main(String[] args) {
        mainMenu();
    }
}
