package coursemanagement;

import java.sql.PreparedStatement;

public abstract class Akun { //class akun dengan abstract class

    String name;
    int ID;
    PreparedStatement pst;

    abstract boolean isInDatabase(int id); //method boolean abstract digunakan untuk mengecek apakah id sudah ada di database atau belum

}
