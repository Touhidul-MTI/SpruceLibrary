/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Touhidul_MTI
 */
public class LibraryBookList {
    private String libraryBookId, libraryBookName,libraryBookAuthorName,libraryShelfId,bookCategory;
    
    public LibraryBookList(){
        this.libraryBookId = "";
        this.libraryBookName = "";
        this.libraryBookAuthorName = "";
        this.libraryShelfId = "";
        this.bookCategory = "";
    }
    public LibraryBookList(String libraryBookId, String libraryBookName, String libraryBookAuthorName, String libraryShelfId, String bookCategory){
        this.libraryBookId = libraryBookId;
        this.libraryBookName = libraryBookName;
        this.libraryBookAuthorName = libraryBookAuthorName;
        this.libraryShelfId = libraryShelfId;
        this.bookCategory = bookCategory;
    }

    public String getLibraryBookId() {
        return libraryBookId;
    }

    public void setLibraryBookId(String libraryBookId) {
        this.libraryBookId = libraryBookId;
    }

    public String getLibraryBookName() {
        return libraryBookName;
    }

    public void setLibraryBookName(String libraryBookName) {
        this.libraryBookName = libraryBookName;
    }

    public String getLibraryBookAuthorName() {
        return libraryBookAuthorName;
    }

    public void setLibraryBookAuthorName(String libraryBookAuthorName) {
        this.libraryBookAuthorName = libraryBookAuthorName;
    }

    public String getLibraryShelfId() {
        return libraryShelfId;
    }

    public void setLibraryShelfId(String libraryShelfId) {
        this.libraryShelfId = libraryShelfId;
    }
    
    public String getBookCategory() {
        return bookCategory;
    }

    public void setbookCategory(String bookCategory) {
        this.bookCategory = bookCategory;
    }
}
