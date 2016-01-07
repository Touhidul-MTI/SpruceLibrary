/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Touhidul_MTI
 */
//authrity can manage users
public class ManageUserList {
    private String fullname, username, contact, address, password;
    public ManageUserList(){
        this.fullname = "";
        this.username = "";
        this.contact = "";
        this.address = "";
        this.password = "";
    }
    public ManageUserList(String fullname, String username,
            String contact, String address, String password){
        this.fullname = fullname;
        this.username = username;
        this.contact = contact;
        this.address = address;
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
