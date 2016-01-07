/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Touhidul_MTI
 */
//sql library
import java.sql.*;
//date and calender library
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
//javafx library
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

//user panel after login
public class User extends LibraryMain {

    static Scene userScene;
    static MenuBar userMenuBar;
    static BorderPane bpUser;
    static VBox vxUserRecord;

    static TableView<LibraryBookList> tableBookList;
    static TableView<UserBookList> tableU;
    static TableView<SearchBookList> tableSearchList;
    static VBox vbEdit;

    static Label dropMessage;

    //user panel after login
    //appears record table of current books taken by current user
    public static void setUserScene() {
        setMenu();
        setTable();
        setUserListEdit();
        bpUser = new BorderPane();

        //  bpUser.setStyle("-fx-background-color:#22aa33");
//        Label loginSuccessMessage = new Label();
//        loginSuccessMessage.setText("Loged in successfully!");
//        loginSuccessMessage.setPadding(new Insets(0,25,60,25));
        Label currentRecord = new Label();
        currentRecord.setText("Your Current Records");
        currentRecord.getStyleClass().add("headline");//css
        currentRecord.setPadding(new Insets(0, 25, 60, 25));
//        
//        Button logoutB = new Button("Log out");
//        logoutB.setOnAction(e ->{//go back to welcome scene and show message when logout
//            window.setScene(welcomeScene);
//            logoutMessage.setText("You Are Loged Out!");
//        });
//        
        vxUserRecord = new VBox(5);
        vxUserRecord.setPadding(new Insets(25, 50, 25, 50));
        vxUserRecord.setAlignment(Pos.CENTER);

        vxUserRecord.getChildren().addAll(currentRecord, tableU, vbEdit);

        bpUser.setTop(userMenuBar);  //menubar not changable
        bpUser.setCenter(vxUserRecord);//changable part

        userScene = new Scene(bpUser, 1200, 650);

        userScene.getStylesheets().add("WelcomeSceneStyle.css");

    }

    //set user menu and menubar and actions
    public static void setMenu() {
        //user Menu
        Menu currentUser = new Menu(currentUserName);//current logged in user name
        //on user menu
        //book library menu
        Menu bookLibrary = new Menu("Book Library");

        //user menu items
        MenuItem home = new MenuItem("Home");
        SeparatorMenuItem separator1 = new SeparatorMenuItem();//separator line
        MenuItem userProfile = new MenuItem("Profile");
        SeparatorMenuItem separator2 = new SeparatorMenuItem();
        MenuItem logoutMI = new MenuItem("Log Out");

        currentUser.getItems().addAll(home, separator1, userProfile, separator2, logoutMI);

        //book library menu items
        MenuItem bookList = new MenuItem("Library Book List");
        SeparatorMenuItem separator3 = new SeparatorMenuItem();
        MenuItem searchBook = new MenuItem("Book Search and Categories");

        bookLibrary.getItems().addAll(bookList, separator3, searchBook);

        //Home menu item action
        home.setOnAction(e -> {   //changing the scene part to user book list
            bpUser.setCenter(vxUserRecord);//without menubar inside borderpane
        });
        //view profile menu item action
        userProfile.setOnAction(e -> {
            showUserProfile();
            dropMessage.setText("");//set drop message empty, in case if dropped anything before

        });
        //logout menu item action
        logoutMI.setOnAction(e -> {//go back to welcome scene and show message when logout
            window.setScene(welcomeScene);
            logoutMessage.setText("You Are Logged Out!");
        });

        //booklist menu item action
        bookList.setOnAction(e -> {
            showBookList();
            dropMessage.setText("");//set drop message empty, in case if dropped anything before
        });

        //search menu item action
        searchBook.setOnAction(e -> {
            searchOptions();
            dropMessage.setText("");//set drop message empty, in case if dropped anything before
        });

        userMenuBar = new MenuBar();

        userMenuBar.getMenus().addAll(currentUser, bookLibrary);

    }

    //user profile set
    //show editable current user profile info
    public static void showUserProfile() {
        GridPane gpUserProfile = new GridPane();
        gpUserProfile.setPadding(new Insets(50, 50, 50, 275));
        gpUserProfile.setHgap(40);
        gpUserProfile.setVgap(10);

        //initialixe string to keep store data
        String fullname = "";
        String username = "";
        String contact = "";
        String address = "";
        //load from database userinfotable to profile scene
        try {
            Statement stmt = DatabaseConnection.stmt;

            //currentUser from logged in and adding spruce to get the current user table ***
            //special case we set table name = username+spruce ****
            //String currentUserProfileInfo = currentUserName + "Spruce";
            ResultSet rSet = stmt.executeQuery("Select * from userinfo where Username='" + currentUserName + "'");

            while (rSet.next()) {

                fullname = rSet.getString("fullname");
                username = rSet.getString("username");
                contact = rSet.getString("contact");
                address = rSet.getString("address");
            }
        } catch (Exception ex) {
            //System.out.println("Error loc: User-> showUserProfile()" + ex);
        }

        //user profile
        Label userProfile = new Label("Your Profile");
        gpUserProfile.setConstraints(userProfile, 1, 0);
        userProfile.getStyleClass().add("headline");//css
        //chane profile
        Label editProfile = new Label("Edit Profile");
        gpUserProfile.setConstraints(editProfile, 3, 0);
        editProfile.getStyleClass().add("headline");//css

        //full name
        Label uNameL = new Label("Fullname :");
        gpUserProfile.setConstraints(uNameL, 0, 3);
        uNameL.getStyleClass().add("labelStyle");//css
        //full name result
        Label uName = new Label(fullname);
        gpUserProfile.setConstraints(uName, 1, 3);
        uName.getStyleClass().add("profileLabelStyle");//css
        //edit fullname
        TextField uNameEdit = new TextField();
        uNameEdit.setPromptText("Enter New Name");
        gpUserProfile.setConstraints(uNameEdit, 3, 3);
        uNameEdit.getStyleClass().add("textFieldStyle");//css
        uNameEdit.setPrefWidth(200);//set width, set one will 
        //automatically set other's on same layout

        //username
        Label uUsernameL = new Label("Username :");
        gpUserProfile.setConstraints(uUsernameL, 0, 4);
        uUsernameL.getStyleClass().add("labelStyle");//css
        //username result
        Label uUsername = new Label(username);
        gpUserProfile.setConstraints(uUsername, 1, 4);
        uUsername.getStyleClass().add("profileLabelStyle");//css
        //edit username not editable
        Label uUsernameEdit = new Label(username + "  (Not Changeable)");
        gpUserProfile.setConstraints(uUsernameEdit, 3, 4);
        uUsernameEdit.getStyleClass().add("profileLabelStyle");//css

        //contact
        Label uContactL = new Label("Email/Phone :");
        gpUserProfile.setConstraints(uContactL, 0, 5);
        uContactL.getStyleClass().add("labelStyle");//css
        //contact result
        Label uContact = new Label(contact);
        gpUserProfile.setConstraints(uContact, 1, 5);
        uContact.getStyleClass().add("profileLabelStyle");//css
        //edit contact
        TextField uContactEdit = new TextField();
        uContactEdit.setPromptText("Enter New Email/Phone");
        gpUserProfile.setConstraints(uContactEdit, 3, 5);
        uContactEdit.getStyleClass().add("textFieldStyle");//css

        //address
        Label uAddressL = new Label("Address :");
        gpUserProfile.setConstraints(uAddressL, 0, 6);
        uAddressL.getStyleClass().add("labelStyle");//css
        //address result
        Label uAddress = new Label(address);
        gpUserProfile.setConstraints(uAddress, 1, 6);
        uAddress.getStyleClass().add("profileLabelStyle");//css
        //edit address
        TextField uAddressEdit = new TextField();
        uAddressEdit.setPromptText("Enter New Address");
        gpUserProfile.setConstraints(uAddressEdit, 3, 6);
        uAddressEdit.getStyleClass().add("textFieldStyle");//css

        //password
        Label uPasswordL = new Label("Password :");
        gpUserProfile.setConstraints(uPasswordL, 0, 7);
        uPasswordL.getStyleClass().add("labelStyle");//css
        //pass result
        Label uPassword = new Label("********");
        gpUserProfile.setConstraints(uPassword, 1, 7);
        uPassword.getStyleClass().add("profileLabelStyle");//css
        //edit pass
        PasswordField uPasswordEdit = new PasswordField();
        uPasswordEdit.setPromptText("Enter New Password");
        gpUserProfile.setConstraints(uPasswordEdit, 3, 7);
        uPasswordEdit.getStyleClass().add("textFieldStyle");//css

        //update button
        Button update = new Button("Update Profile");
        gpUserProfile.setConstraints(update, 3, 9);

        //update message
        Label updateMessage = new Label();
        gpUserProfile.setConstraints(updateMessage, 3, 10);
        updateMessage.getStyleClass().add("MessageStyle");//css

        //back button
        Button back = new Button("Back to Home");
        gpUserProfile.setConstraints(back, 3, 20);
        back.getStyleClass().add("closeButtonStyle");//css

        //update button action
        update.setOnAction(e -> {
            //insert updated profile data to user info table of database
            try {
                Statement stmt = DatabaseConnection.stmt;

                String nameInput = uNameEdit.getText().toString();
                if (!nameInput.isEmpty()) {
                    stmt.executeUpdate("update userinfo set fullname='" + nameInput
                            + "' where username='" + currentUserName + "'");//update name
                    uName.setText(nameInput);//show new name
                    uNameEdit.setText("");//clear dield
                }

                String contactInput = uContactEdit.getText().toString();
                if (!contactInput.isEmpty()) {
                    stmt.executeUpdate("update userinfo set contact='" + contactInput
                            + "' where username='" + currentUserName + "'");//update contact
                    uContact.setText(contactInput);//show new contact
                    uContactEdit.setText("");//clear dield
                }
                String addressInput = uAddressEdit.getText().toString();
                if (!addressInput.isEmpty()) {
                    stmt.executeUpdate("update userinfo set address='" + addressInput
                            + "' where username='" + currentUserName + "'");//update address
                    uAddress.setText(addressInput);//show new address
                    uAddressEdit.setText("");//clear dield
                }
                String passInput = uPasswordEdit.getText().toString();
                if (!passInput.isEmpty()) {
                    stmt.executeUpdate("update userinfo set password='" + passInput
                            + "' where username='" + currentUserName + "'");//update password
                    uPasswordEdit.setText("");//clear dield
                }
                //show updated message
                updateMessage.setText("Profile has been Updated Successfully!");

            } catch (Exception ex2) {
                //System.out.println("Error loc: User-> showUserProfile()" + ex2);
            }
        });

        //back button action
        back.setOnAction(e -> {
            bpUser.setCenter(vxUserRecord);//scene part change to user book list record
        });

        gpUserProfile.getChildren().addAll(userProfile, editProfile, uNameL, uName, uNameEdit,
                uUsernameL, uUsername, uUsernameEdit, uContactL, uContact, uContactEdit,
                uAddressL, uAddress, uAddressEdit, uPasswordL, uPassword, uPasswordEdit, update, updateMessage, back);

        //change scene part to userprofile (keeping menubar unchanged)
        bpUser.setCenter(gpUserProfile);
    }

    //set user book list table
    //create fx table and show data for current user through observablelist,UserBookList Class
    public static void setTable() {

        tableU = new TableView<>();

        //column set
        //book name col
        TableColumn<UserBookList, String> bookNameColumnU = new TableColumn<>("Book Name");
        bookNameColumnU.setCellValueFactory(new PropertyValueFactory<>("bookNameU"));
        bookNameColumnU.setMinWidth(300);
        //book id col
        TableColumn<UserBookList, String> bookIdColumnU = new TableColumn<>("Book Id");
        bookIdColumnU.setCellValueFactory(new PropertyValueFactory<>("bookIdU"));
        bookIdColumnU.setMinWidth(200);
        //issue date col
        TableColumn<UserBookList, String> issueDateateColumnU = new TableColumn<>("Issue Date");
        issueDateateColumnU.setCellValueFactory(new PropertyValueFactory<>("issueDateU"));
        issueDateateColumnU.setMinWidth(200);
        //due date col
        TableColumn<UserBookList, String> dueDateateColumnU = new TableColumn<>("Due Date");
        dueDateateColumnU.setCellValueFactory(new PropertyValueFactory<>("dueDateU"));
        dueDateateColumnU.setMinWidth(200);
        //fine col
        TableColumn<UserBookList, String> fineColumnU = new TableColumn<>("Fine (TK)");
        fineColumnU.setCellValueFactory(new PropertyValueFactory<>("fineU"));
        fineColumnU.setMinWidth(150);

        //call to check and update fine before displaying usertable
        updateFine();//every time user login, check the due date is over or not with fine 0
        //if due date over, set the fine to 100

        tableU.setItems(getTableDataU());//get items from observable array
        tableU.getColumns().addAll(bookNameColumnU, bookIdColumnU, issueDateateColumnU, dueDateateColumnU, fineColumnU);

    }

    //check, compare duedate and today date, then update fine
    //every time user login, check the due date is over or not with fine 0
    //if due date over, set the fine to 100
    public static void updateFine() {
        //current date from calender
        //comparing due date and set fine
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//date format set
        Calendar c = Calendar.getInstance();//todays calendar date time gatting
        Date todayDate = c.getTime();//get as date
        //String set_Due_Date_As_String = dateFormat.format(set_Due_Date.getTime());//fit format and auto date as string format

        //data insert from library book list to current user book list
        try {
            Statement stmt = DatabaseConnection.stmt;
            String currentUserTableName = currentUserName + "Spruce";

            String getDateQuery = "Select * from " + currentUserTableName + " ";
            ResultSet rSet = stmt.executeQuery(getDateQuery);

            while (rSet.next()) {
                //need to create statement every time in this case not interfare the resultset statement
                //if not created, then after one fine update, result will be closed and won't go for next    
                Statement stmtTemp = DatabaseConnection.con.createStatement();

                String bookName = rSet.getString("Book_Name");
                String bookId = rSet.getString("Book_Id");
                Date dueDate = rSet.getDate("Due_Date");
                Double fine = rSet.getDouble("Fine_TK");
                    //System.out.println(bookId+" "+bookName+" "+dueDate+" "+fine);

                //if dueDate is not after todayDate means date over
                //and fine is less than 0, then set fine
                if (!dueDate.after(todayDate) && fine <= 0) {
                    //update fine
                    String insertFineQuery = "update " + currentUserTableName
                            + " set Fine_TK=100.0 where Book_Id='" + bookId + "' ";
                    stmtTemp.executeUpdate(insertFineQuery);
                }
            }

        } catch (Exception excep) {
            //System.out.println("Error Location: User -> updateFine()" + excep);
        }
    }

    //user book list observable array
    //load all current user data from database currentUser table to observablelist
    public static ObservableList<UserBookList> getTableDataU() {
        //observableArrayList
        ObservableList<UserBookList> tableDataU = FXCollections.observableArrayList();

        //load from database table to booklist table
        try {
            Statement stmt = DatabaseConnection.stmt;

            //currentUser from logged in and adding spruce to get the current user table ***
            //special case we set table name = username+spruce ****
            String currentUserTableName = currentUserName + "Spruce";
            ResultSet rSet = stmt.executeQuery("Select * from " + currentUserTableName + "");

            while (rSet.next()) {

                String book_name = rSet.getString("Book_Name");
                String book_id = rSet.getString("Book_Id");
                String issue_date = rSet.getString("Issue_Date");
                String due_date = rSet.getString("Due_Date");

                String fine_tkString = rSet.getString("Fine_TK");
                double fine_tk = Double.parseDouble(fine_tkString);//converting to double

                tableDataU.add(new UserBookList(book_name, book_id, issue_date, due_date, fine_tk));
//                LibraryBookList newUBL = new LibraryBookList();
//                newLBL.setLibraryBookId(rSet.getString("Book_Id"));
//                newLBL.setLibraryBookName(rSet.getString("Book_Name"));
//                newLBL.setLibraryBookAuthorName(rSet.getString("Author_Name"));
//                newLBL.setLibraryShelfId(rSet.getString("Shelf_Id"));
//                
//                tableBookList.getItems().add(newLBL);
            }
        } catch (Exception e) {
            //System.out.println("error loc: user-> getTableDataU() "+e);
        }

//        tableDataU.add(new UserBookList("Tom and Jerry","a-101","12/12/12",100));
//        tableDataU.add(new UserBookList("Only Tom","b-202","13/12/12",200));
//        tableDataU.add(new UserBookList("Only Jerry","c-303","14/12/12",150));
        return tableDataU;
    }

    // add and drop (user list table)
    //drop option and action for current user
    public static void setUserListEdit() {
        dropMessage = new Label();//shows add/drop success/fails
        dropMessage.getStyleClass().add("MessageStyle");//css

        Button drop = new Button("Drop Selected Record");
        //drop action
        drop.setOnAction(e -> {
            ObservableList<UserBookList> selectedToDrop, allTableItems;

            allTableItems = tableU.getItems();
            selectedToDrop = tableU.getSelectionModel().getSelectedItems();//get selected items
            //get the bookid from selected row
            String selectedUserBookId = selectedToDrop.get(0).getBookIdU();
            //drop/delete the whole row
            selectedToDrop.forEach(allTableItems::remove);

            //drop the selected row from database currentusertable by bookid
            try {
                Statement stmt = DatabaseConnection.stmt;
                String currentUserTableName = currentUserName + "Spruce";

                String dropCurentUserQuery = "delete from " + currentUserTableName + " where book_id='" + selectedUserBookId + "'";
                stmt.executeUpdate(dropCurentUserQuery);

                dropMessage.setText("Book has been Dropped Successfully!");
            } catch (Exception excep) {
               // System.out.println("Error Location: User -> UsershowBookList "+excep);
            }
        });

        vbEdit = new VBox();
        vbEdit.setPadding(new Insets(10, 10, 10, 10));
        vbEdit.setSpacing(15);
        vbEdit.setAlignment(Pos.CENTER);
        vbEdit.getChildren().addAll(dropMessage, drop);
    }

    //library book list table 
    //fx table create and load data through observable list
    //book add option and action for current user
    public static void showBookList() {

        //ribrary books list
        Label spruceLibraryBooks = new Label("Spruce Library Books");
        spruceLibraryBooks.getStyleClass().add("headline");//css

        tableBookList = new TableView<>();

        //column set
        //book id col
        TableColumn<LibraryBookList, String> libraryBookIdColumn = new TableColumn<>("Book Id");
        libraryBookIdColumn.setCellValueFactory(new PropertyValueFactory<>("libraryBookId"));
        libraryBookIdColumn.setMinWidth(170);
        //book name col
        TableColumn<LibraryBookList, String> libraryBookNameColumn = new TableColumn<>("Book Name");
        libraryBookNameColumn.setCellValueFactory(new PropertyValueFactory<>("libraryBookName"));
        libraryBookNameColumn.setMinWidth(290);
        //book author col
        TableColumn<LibraryBookList, String> libraryBookAuthorNameColumn = new TableColumn<>("Author Name");
        libraryBookAuthorNameColumn.setCellValueFactory(new PropertyValueFactory<>("libraryBookAuthorName"));
        libraryBookAuthorNameColumn.setMinWidth(290);
        //shelf id col
        TableColumn<LibraryBookList, String> libraryShelfIdColumn = new TableColumn<>("Shelf Id");
        libraryShelfIdColumn.setCellValueFactory(new PropertyValueFactory<>("libraryShelfId"));
        libraryShelfIdColumn.setMinWidth(170);
        //category column
        TableColumn<LibraryBookList, String> bookCategoryColumn = new TableColumn<>(" Book Category");
        bookCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("bookCategory"));
        bookCategoryColumn.setMinWidth(190);

        //set booklistvalue
        tableBookList.setItems(getLibraryTableData());//get items from observable array
        tableBookList.getColumns().addAll(libraryBookIdColumn, libraryBookNameColumn,
                libraryBookAuthorNameColumn, libraryShelfIdColumn, bookCategoryColumn);

        //addSuccessMessage label
        Label addSuccessMessage = new Label("");
        addSuccessMessage.getStyleClass().add("MessageStyle");//css

        //addbook button
        Button addBook = new Button("Add to My List");

        //back to home button
        Button back = new Button("Back to Home");
        back.getStyleClass().add("closeButtonStyle");//css

        //addbook action
        addBook.setOnAction(e -> {
            ObservableList<LibraryBookList> selectedToAdd;

            selectedToAdd = tableBookList.getSelectionModel().getSelectedItems();//get selected row
            //that row's 0 index contains object of librarybooklist class
            //get the value as string
            String selectedLibraryBookId = selectedToAdd.get(0).getLibraryBookId();
            String selectedLibraryBookName = selectedToAdd.get(0).getLibraryBookName();
            //String selectedLibraryBookAuthorName = selectedToAdd.get(0).getLibraryBookAuthorName();
            //String selectedLibraryShelfId = selectedToAdd.get(0).getLibraryShelfId();

            //before add this selected books, first check if book is already added before or not
            int existAlreadyBookIdCounter = 0;
            try {
                Statement stmt = DatabaseConnection.stmt;
                String currentUserTableName = currentUserName + "Spruce";

                String bookIdQuery = "Select Book_Id from " + currentUserTableName + " ";
                ResultSet rSet = stmt.executeQuery(bookIdQuery);

                while (rSet.next()) {
                    String existBookId = rSet.getString("Book_Id");
                    if (selectedLibraryBookId.equalsIgnoreCase(existBookId)) {

                        existAlreadyBookIdCounter = 1;//if already found in userlist, then count 1
                        break;
                    }
                }
            } catch (Exception excep) {
                //System.out.println("Error Location: User -> UsershowBookList" + excep);
            }
            if (existAlreadyBookIdCounter == 0) {//if not found then add to list
                //get current calendar date and add 7 days as due date, then insert
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//date format set
                Calendar cal = Calendar.getInstance();//calendar date time getting
                Date set_Issue_Date = cal.getTime();//current date
                String set_Issue_Date_As_String = dateFormat.format(set_Issue_Date.getTime());//fit format and auto date as string format
                
                cal.add(Calendar.DATE, 7);//add 7 days due
                Date set_Due_Date = cal.getTime();//get as date (7 days extended)
                String set_Due_Date_As_String = dateFormat.format(set_Due_Date.getTime());//fit format and auto date as string format

                //data insert from library book list to current user book list
                try {
                    Statement stmt = DatabaseConnection.stmt;
                    String currentUserTableName = currentUserName + "Spruce";

                    String insertCurentUserQuery = "insert into " + currentUserTableName + " values('" + selectedLibraryBookName + "','"
                            + selectedLibraryBookId + "','"+set_Issue_Date_As_String+"','" + set_Due_Date_As_String + "',0)";
                    stmt.executeUpdate(insertCurentUserQuery);

                } catch (Exception excep2) {
                    //System.out.println("Error Location: User -> UsershowBookList" + excep2);
                }

                //set the necessary value to User BookList
                //must follow user table and obsevablelist
                UserBookList newBook = new UserBookList();

                newBook.setBookNameU(selectedLibraryBookName);//book name
                newBook.setBookIdU(selectedLibraryBookId);//book idd
                newBook.setIssueDateU(set_Issue_Date_As_String);//issue date
                newBook.setDueDateU(set_Due_Date_As_String);//due date
                newBook.setFineU(0);//fine

                //data load to show from library book list to current user book list
                tableU.getItems().add(newBook);

                // show add Success Message
                addSuccessMessage.setText("Succeessfully Added to Your Book List!");
            } else {
                addSuccessMessage.setText("You Already Have This Book!");
            }

        });

        //back button action
        back.setOnAction(e -> {
            bpUser.setCenter(vxUserRecord);//scene part change to user book list record
        });
        VBox vbLibrary = new VBox(20);//creating vbox only to set padding around
        vbLibrary.setPadding(new Insets(20, 30, 20, 30));
        vbLibrary.setAlignment(Pos.CENTER);
        vbLibrary.getChildren().addAll(spruceLibraryBooks, tableBookList, addBook, addSuccessMessage, back);

        bpUser.setCenter(vbLibrary);//changing the scene part without menubar inside borderpane
    }

    //library book list observable array
    //initially load all library books 
    //from database table LibraryBook to observable array
    public static ObservableList<LibraryBookList> getLibraryTableData() {
        //observableArrayList
        ObservableList<LibraryBookList> libraryTableData = FXCollections.observableArrayList();

        //load from database table to booklist table
        try {
            Statement stmt = DatabaseConnection.stmt;

            ResultSet rSet = stmt.executeQuery("Select * from LibraryBook");

            while (rSet.next()) {
                // LibraryBookList newLBL = new LibraryBookList();

                String book_id = rSet.getString("Book_Id");
                String book_name = rSet.getString("Book_Name");
                String author_name = rSet.getString("Author_Name");
                String shelf_id = rSet.getString("Shelf_Id");
                String book_category = rSet.getString("Book_Category");
                libraryTableData.add(new LibraryBookList(book_id, book_name, author_name, shelf_id, book_category));
//                newLBL.setLibraryBookId(rSet.getString("Book_Id"));
//                newLBL.setLibraryBookName(rSet.getString("Book_Name"));
//                newLBL.setLibraryBookAuthorName(rSet.getString("Author_Name"));
//                newLBL.setLibraryShelfId(rSet.getString("Shelf_Id"));
//                
//                tableBookList.getItems().add(newLBL);
            }
        } catch (Exception e) {
            //System.out.println("database error inside getLibraryTableData()");
        }

        //default table value manually
//        libraryTableData.add(new LibraryBookList("a-101","Tom and Jerry","Doggy Cat","sd-2"));
//        libraryTableData.add(new LibraryBookList("b-102","Tom","Doggy","sd-2"));
//        libraryTableData.add(new LibraryBookList("c-103","Jerry","Cat","sd-3"));
        return libraryTableData;
    }

    //search book list table
    //according to search box, data load from library table to search table
    //search by choosing category 
    //book add button and action
    public static void searchOptions() {

        //search text type box
        TextField searchBox = new TextField();//textfield for typing
        searchBox.setPromptText("Type Here to Search");
        searchBox.setPrefWidth(600);
        searchBox.getStyleClass().add("textFieldStyle");//css

        //search by category choose  
        ComboBox<String> chooseCategoryToSearch = new ComboBox<>();
        chooseCategoryToSearch.getItems().addAll("Book_Id", "Book_Name",
                "Author_Name", "Shelf_Id", "Book_Category");
        chooseCategoryToSearch.setValue("Search by  (Default: by Book Name)");
        //chooseCategoryToSearch.setPromptText("Search by... (Default: by Book Name)");
        chooseCategoryToSearch.getStyleClass().add("textFieldStyle");//css

        //hbox to putsearch box and search by in one line
        HBox hbSearch = new HBox(10);
        hbSearch.getChildren().addAll(searchBox, chooseCategoryToSearch);
        hbSearch.setAlignment(Pos.CENTER);

        Button searchButton = new Button("S E A R C H");//click to search

        tableSearchList = new TableView<>();

        //column set
        //book id col
        TableColumn<SearchBookList, String> searchBookIdColumn = new TableColumn<>("Book Id");
        searchBookIdColumn.setCellValueFactory(new PropertyValueFactory<>("searchBookId"));
        searchBookIdColumn.setMinWidth(170);
        //book name col
        TableColumn<SearchBookList, String> searchBookNameColumn = new TableColumn<>("Book Name");
        searchBookNameColumn.setCellValueFactory(new PropertyValueFactory<>("searchBookName"));
        searchBookNameColumn.setMinWidth(290);
        //book author col
        TableColumn<SearchBookList, String> searchBookAuthorNameColumn = new TableColumn<>("Author Name");
        searchBookAuthorNameColumn.setCellValueFactory(new PropertyValueFactory<>("searchBookAuthorName"));
        searchBookAuthorNameColumn.setMinWidth(290);
        //shelf id col
        TableColumn<SearchBookList, String> searchShelfIdColumn = new TableColumn<>("Shelf Id");
        searchShelfIdColumn.setCellValueFactory(new PropertyValueFactory<>("searchShelfId"));
        searchShelfIdColumn.setMinWidth(170);
        //category column
        TableColumn<SearchBookList, String> searchBookCategoryColumn = new TableColumn<>("Book Category");
        searchBookCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("searchBookCategory"));
        searchBookCategoryColumn.setMinWidth(190);

        //set booklistvalue
        //tableSearchList.setItems(getSearchTableData());//get items from observable array
        tableSearchList.getColumns().addAll(searchBookIdColumn, searchBookNameColumn,
                searchBookAuthorNameColumn, searchShelfIdColumn, searchBookCategoryColumn);

        //addSuccessMessage label
        Label addSuccessMessage = new Label("");
        addSuccessMessage.getStyleClass().add("MessageStyle");//css

        //addbook button
        Button addBook = new Button("Add to My List");

        //back to home button
        Button back = new Button("Back to Home");
        back.getStyleClass().add("closeButtonStyle");//css

        //search button action
        searchButton.setOnAction(e -> {

            //the text which is going to be searched : action in search button action
            String textToSearch = searchBox.getText().toString();

            //choosen category to search by, according to which column of table we search
            //ex: select * from table where book_name like '%t%'; here book_name is choosen category
            //action in search button action
            String textToSearchByChoosen = "Book_Name";//default ->search by Book_name

            if (chooseCategoryToSearch.getValue().toString().equals("Book_Id")) {
                //System.out.println("a");
                textToSearchByChoosen = "Book_Id";
            } else if (chooseCategoryToSearch.getValue().toString().equals("Book_Name")) {
                //System.out.println("b");
                textToSearchByChoosen = "Book_Name";
            } else if (chooseCategoryToSearch.getValue().toString().equals("Author_Name")) {
                //System.out.println("c");
                textToSearchByChoosen = "Author_Name";
            } else if (chooseCategoryToSearch.getValue().toString().equals("Shelf_Id")) {
                //System.out.println("d");
                textToSearchByChoosen = "Shelf_Id";
            } else if (chooseCategoryToSearch.getValue().toString().equals("Book_Category")) {
                // System.out.println("e");
                textToSearchByChoosen = "Book_Category";
            }

            //set booklistvalue : send search text and choosen cate text also
            tableSearchList.setItems(getSearchTableData(textToSearch, textToSearchByChoosen));//get items from observable array
        });

        //addbook action
        addBook.setOnAction(e -> {
            ObservableList<SearchBookList> selectedToAdd;

            selectedToAdd = tableSearchList.getSelectionModel().getSelectedItems();//get selected row
            //that row's 0 index contains object of Searchbooklist class
            //get the value as string
            String selectedSearchBookId = selectedToAdd.get(0).getSearchBookId();
            String selectedSearchBookName = selectedToAdd.get(0).getSearchBookName();
            //String selectedSearchBookAuthorName = selectedToAdd.get(0).getSearchBookAuthorName();
            //String selectedSearchShelfId = selectedToAdd.get(0).getSearchShelfId();
            //before add this selected books, first check if book is already added before or not

            int existAlreadyBookIdCounter = 0;
            try {
                Statement stmt = DatabaseConnection.stmt;
                String currentUserTableName = currentUserName + "Spruce";

                String bookIdQuery = "Select Book_Id from " + currentUserTableName + " ";
                ResultSet rSet = stmt.executeQuery(bookIdQuery);

                while (rSet.next()) {
                    String existBookId = rSet.getString("Book_Id");
                    if (selectedSearchBookId.equalsIgnoreCase(existBookId)) {

                        existAlreadyBookIdCounter = 1;//if already found in userlist, then count 1
                        break;
                    }
                }
            } catch (Exception excep) {
                //System.out.println("Error Location: User -> Serachoptions()" + excep);
            }
            if (existAlreadyBookIdCounter == 0) {//if not found then add to list
                //get current calendar date and add 7 days as due date, then insert
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//date format set
                Calendar cal = Calendar.getInstance();//calendar date time gatting
                Date set_Issue_Date = cal.getTime();//current date
                String set_Issue_Date_As_String = dateFormat.format(set_Issue_Date.getTime());//fit format and auto date as string format
                
                cal.add(Calendar.DATE, 7);//add 7 days due
                Date set_Due_Date = cal.getTime();//get as date
                String set_Due_Date_As_String = dateFormat.format(set_Due_Date.getTime());//fit format and auto date as string format

                //data insert from library book list to current user book list
                try {
                    Statement stmt = DatabaseConnection.stmt;
                    String currentUserTableName = currentUserName + "Spruce";

                    String insertCurentUserQuery = "insert into " + currentUserTableName + " values('" + selectedSearchBookName + "','"
                            + selectedSearchBookId + "','"+set_Issue_Date_As_String+"','" + set_Due_Date_As_String + "',0)";
                    stmt.executeUpdate(insertCurentUserQuery);

                } catch (Exception excep2) {
                    //System.out.println("Error Location: User -> searchOptions()");
                }

            //set the necessary value to User BookList
                //must follow user table and obsevablelist
                UserBookList newBook = new UserBookList();

                newBook.setBookNameU(selectedSearchBookName);//book name
                newBook.setBookIdU(selectedSearchBookId);//book idd
                newBook.setIssueDateU(set_Issue_Date_As_String);//issue date
                newBook.setDueDateU(set_Due_Date_As_String);//due date
                newBook.setFineU(0);//fine

                //data load to show from library book list to current user book list
                tableU.getItems().add(newBook);

                // show add Success Message
                addSuccessMessage.setText("Succeessfully Added to Your Book List!");
            } else {
                addSuccessMessage.setText("You Already Have This Book!");
            }
        });

        //back button action
        back.setOnAction(e -> {
            bpUser.setCenter(vxUserRecord);//scene part change to user book list record
        });

        VBox vbSearch = new VBox(20);//creating vbox only to set padding around
        vbSearch.setPadding(new Insets(20, 30, 20, 30));
        vbSearch.setAlignment(Pos.CENTER);
        vbSearch.getChildren().addAll(hbSearch, searchButton, tableSearchList,
                addBook, addSuccessMessage, back);

        bpUser.setCenter(vbSearch);//changing the scene part without menubar inside borderpane
    }

    //search book list observable array
    //initially load no books
    //load books from database library book table to 
    // observable array in search according to search
    //take text to search and choosen text of search by
    public static ObservableList<SearchBookList> getSearchTableData(String textToSearch, String textToSearchByChoosen) {
        //observableArrayList
        ObservableList<SearchBookList> searchTableData = FXCollections.observableArrayList();

        //search result shows in serch table loaded from library table database
        try {
            Statement stmt = DatabaseConnection.stmt;

            //search query : textToSearch is from searchbox field
            //textToSearchByChoosen is from choosen search by combo box
            ResultSet rSet = stmt.executeQuery("Select * from LibraryBook where " + textToSearchByChoosen + " like '%" + textToSearch + "%'");
            //ResultSet rSet = stmt.executeQuery("Select * from LibraryBook");

            while (rSet.next()) {
                // SearchBookList newLBL = new SearchBookList();

                String book_id = rSet.getString("Book_Id");
                String book_name = rSet.getString("Book_Name");
                String author_name = rSet.getString("Author_Name");
                String shelf_id = rSet.getString("Shelf_Id");
                String book_category = rSet.getString("Book_Category");
                searchTableData.add(new SearchBookList(book_id, book_name, author_name, shelf_id, book_category));
                //                newLBL.setLibraryBookId(rSet.getString("Book_Id"));
                //                newLBL.setLibraryBookName(rSet.getString("Book_Name"));
                //                newLBL.setLibraryBookAuthorName(rSet.getString("Author_Name"));
                //                newLBL.setLibraryShelfId(rSet.getString("Shelf_Id"));
                //                
                //                tableBookList.getItems().add(newLBL);
            }
        } catch (Exception e) {
            //System.out.println("Error loc: User->getSearchTableData() " + e);
        }
        //default table value manually
//        libraryTableData.add(new LibraryBookList("a-101","Tom and Jerry","Doggy Cat","sd-2"));
//        libraryTableData.add(new LibraryBookList("b-102","Tom","Doggy","sd-2"));
//        libraryTableData.add(new LibraryBookList("c-103","Jerry","Cat","sd-3"));
        return searchTableData;
    }

}
