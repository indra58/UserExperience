package com.greencomputingnepal.userexperience.contact;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;

import java.util.List;

/**
 * Created by i7 on 5/15/2017.
 */

@Table(name = "Contact")
public class Contact extends Model{

    @Column(name = "name", unique = true)
    private String name;
    @Column(name = "contactno")
    private String contactno;

    public Contact() {
        super();
    }

    public Contact(String name, String contactno) {
        this.name = name;
        this.contactno = contactno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public static List<Contact> getAllData(){
        return new Select()
                .from(Contact.class)
                .execute();
    }

    public static Contact getDataFromName(String name, String contactno){
        return new Select()
                .from(Contact.class)
                .where("name =? AND contactno=?", name, contactno)
                .executeSingle();
    }

    public static Contact deleteSingleData(String name){
        return (Contact) new Delete()
                .from(Contact.class)
                .where("name =?", name)
                .execute();
    }

    public static Contact deleteAll(){
        return (Contact) new Delete()
                .from(Contact.class)
                .execute();
    }

    public static void updateContact(String name, String contactno){
        new Update(Contact.class)
                .set("contactno =?", contactno)
                .where("name =?", name)
                .execute();
    }
}