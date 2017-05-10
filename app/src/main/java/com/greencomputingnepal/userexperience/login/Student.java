package com.greencomputingnepal.userexperience.login;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;

import java.util.List;

/**
 * Created by i7 on 5/9/2017.
 */

@Table(name = "Student")  // Annotation to create table
public class Student extends Model {
    @Column(name = "email", unique = true)  // Annotation to create column in table
    private String email;     // Data Type of the defined column of table..
    @Column(name = "password")
    private String password;

    public Student() {
        super();
    }

    public Student(String email, String password) {
        super();
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // TO get all students data from table
    public static List<Student> getAllData() {
        return new Select()
                .from(Student.class)
                .execute();
    }

    // get data of student according to email
    public static Student getDataUsingEmail(String email) {
        return new Select()
                .from(Student.class)
                .where("email = ?", email)
                .executeSingle();
    }

    // delete particular data of student according to email
    public static Student deleteDataUsingEmail(String email) {
        return (Student) new Delete()
                .from(Student.class)
                .where("email = ?", email)
                .execute();
    }

    // delete all student data from table
    public static Student deleteAll() {
        return (Student) new Delete()
                .from(Student.class)
                .execute();
    }

    // Update password according to the email address of student
    public static void updatePassword(String email, String password) {
        new Update(Student.class)
                .set("password = ?", password)
                .where("email = ?", email)
                .execute();
    }
}