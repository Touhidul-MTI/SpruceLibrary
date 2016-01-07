/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Touhidul_MTI
 */
//user current records need to be displayed
public class UserBookList {
    private double  fineU;
    private String bookNameU, bookIdU, issueDateU, dueDateU;
    
    public UserBookList(){
        this.bookNameU = "";
        this.bookIdU = "";
        this.issueDateU = "";
        this.dueDateU = "";
        this.fineU = 0.0;
    }
    public UserBookList(String bookNameU, String bookIdU, String issueDateU, String dueDateU, double fineU){
        this.bookNameU = bookNameU;
        this.bookIdU = bookIdU;
        this.issueDateU = issueDateU;
        this.dueDateU = dueDateU;
        this.fineU = fineU;
    }

    public String getBookNameU() {
        return bookNameU;
    }

    public void setBookNameU(String bookNameU) {
        this.bookNameU = bookNameU;
    }

     public String getBookIdU() {
        return bookIdU;
    }

    public void setBookIdU(String bookIdU) {
        this.bookIdU = bookIdU;
    }
    
    public String getDueDateU() {
        return dueDateU;
    }

    public void setDueDateU(String dueDateU) {
        this.dueDateU = dueDateU;
    }
    
    public String getIssueDateU() {
        return issueDateU;
    }

    public void setIssueDateU(String issueDateU) {
        this.issueDateU = issueDateU;
    }

    public double getFineU() {
        return fineU;
    }

    public void setFineU(double fineU) {
        this.fineU = fineU;
    }
}
