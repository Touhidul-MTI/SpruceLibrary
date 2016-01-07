/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Touhidul_MTI
 */
//seach result needs a table to show list
public class SearchBookList {
    private String searchBookId, searchBookName, searchBookAuthorName, searchShelfId, searchBookCategory;
    
    public SearchBookList(){
        this.searchBookId = "";
        this.searchBookName = "";
        this.searchBookAuthorName = "";
        this.searchShelfId = "";
        this.searchBookCategory = "";
    }
     public SearchBookList(String searchBookId, String searchBookName, String searchBookAuthorName, String searchShelfId, String searchBookCategory){
        this.searchBookId = searchBookId;
        this.searchBookName = searchBookName;
        this.searchBookAuthorName = searchBookAuthorName;
        this.searchShelfId = searchShelfId;
        this.searchBookCategory = searchBookCategory;
    }

    public String getSearchBookId() {
        return searchBookId;
    }

    public void setSearchBookId(String searchBookId) {
        this.searchBookId = searchBookId;
    }

    public String getSearchBookName() {
        return searchBookName;
    }

    public void setSearchBookName(String searchBookName) {
        this.searchBookName = searchBookName;
    }

    public String getSearchBookAuthorName() {
        return searchBookAuthorName;
    }

    public void setSearchBookAuthorName(String searchBookAuthorName) {
        this.searchBookAuthorName = searchBookAuthorName;
    }

    public String getSearchShelfId() {
        return searchShelfId;
    }

    public void setSearchShelfId(String searchShelfId) {
        this.searchShelfId = searchShelfId;
    }

    public String getSearchBookCategory() {
        return searchBookCategory;
    }

    public void setSearchBookCategory(String searchBookCategory) {
        this.searchBookCategory = searchBookCategory;
    }
    
}
