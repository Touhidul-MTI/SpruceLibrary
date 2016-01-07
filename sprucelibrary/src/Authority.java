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

//authority panel class after login
public class Authority extends LibraryMain {

    static Scene authorityScene;
    static MenuBar authorityMenuBar;
    static BorderPane bpAuthority;
    static VBox vxAuthorityControl;
    
    static TableView<ManageUserList> tableManageUser;
    static TableView<ManageLibraryBookList> tableManageBook;
    
    static Label totalBooksShow, totalUsersShow;
    static int totalUsers,totalBooks ;
    
    //authority panel after login
    public static void setAuthorityScene() {
        setMenu();
        bpAuthority = new BorderPane();
        //authorityPanelMessage
        Label authorityPanelMessage = new Label();
        authorityPanelMessage.setText("Spruce Library Authority Panel");
        authorityPanelMessage.setId("authorityPanelId");//css
        authorityPanelMessage.setPadding(new Insets(0, 25, 60, 25));

        //database
        int totalAuthority = 0;
        try {
            Statement stmt = DatabaseConnection.stmt;
            
            String userCountquery = "select count(*) from userinfo";
            ResultSet rSet = stmt.executeQuery(userCountquery);//total user
            while (rSet.next()) {
                totalUsers = rSet.getInt("count(*)");
            }
            
            String bookCountQuery = "select count(*) from librarybook";
            rSet = stmt.executeQuery(bookCountQuery);//total books
            while (rSet.next()) {
                totalBooks = rSet.getInt("count(*)");
            }
            
            String authorityCountQuery = "select count(*) from authorityInfo";
            rSet = stmt.executeQuery(authorityCountQuery);//total books
            while (rSet.next()) {
                totalAuthority = rSet.getInt("count(*)");
            }

        } catch (Exception excep) {
           // System.out.println("Error Location: Authority -> setAuthorityScene()" + excep);
        }
        //total Authority
        Label totalAuthorityLabel = new Label("Total Authority: ");
        totalAuthorityLabel.getStyleClass().add("labelStyle");//css
        Label totalAuthorityShow = new Label(totalAuthority+" Authority");
        totalAuthorityShow.getStyleClass().add("headline");//css
        
        HBox hbAuthority = new HBox(20);
        hbAuthority.getChildren().addAll(totalAuthorityLabel,totalAuthorityShow);
        //hbUser.setAlignment(Pos.CENTER);
        
        //total users
        Label totalUsersLabel = new Label("Total Users: ");
        totalUsersLabel.getStyleClass().add("labelStyle");//css
        totalUsersShow = new Label(totalUsers+" Users");
        totalUsersShow.getStyleClass().add("headline");//css
        
        HBox hbUser = new HBox(20);
        hbUser.getChildren().addAll(totalUsersLabel,totalUsersShow);
        //hbUser.setAlignment(Pos.CENTER);
        
        //total books
        Label totalBooksLabel = new Label("Total Books: ");
        totalBooksLabel.getStyleClass().add("labelStyle");//css
        totalBooksShow = new Label(totalBooks+" Books");
        totalBooksShow.getStyleClass().add("headline");//css
        
        HBox hbBook = new HBox(20);
        hbBook.getChildren().addAll(totalBooksLabel,totalBooksShow);
        //hbBook.setAlignment(Pos.CENTER);
        
        vxAuthorityControl = new VBox(30);
        vxAuthorityControl.setPadding(new Insets(10, 100, 30,100));
        vxAuthorityControl.setAlignment(Pos.CENTER);

        vxAuthorityControl.getChildren().addAll(authorityPanelMessage, hbAuthority, hbUser, hbBook);
        //authorityPanelMessage.setAlignment(Pos.TOP_CENTER);
        
        
        bpAuthority.setTop(authorityMenuBar);  //menubar not changable
        bpAuthority.setCenter(vxAuthorityControl);//changable part

        authorityScene = new Scene(bpAuthority, 1200, 650);

        authorityScene.getStylesheets().add("WelcomeSceneStyle.css");

    }

    //set authority menu and menubar and actions
    public static void setMenu() {
        //Author Menu
        Menu currentAuthority = new Menu(currentAuthorityName);//current logged in authority name

        //management menu 
        Menu Management = new Menu("Management");

        //authority menu items
        MenuItem home = new MenuItem("Home");
        SeparatorMenuItem separator1 = new SeparatorMenuItem();//separator line
        MenuItem authorityProfile = new MenuItem("Profile");
        SeparatorMenuItem separator2 = new SeparatorMenuItem();
        MenuItem logoutMI = new MenuItem("Log Out");

        currentAuthority.getItems().addAll(home, separator1, authorityProfile, separator2, logoutMI);

        //book library menu items
        MenuItem manageBooks = new MenuItem("Manage Library Books");
        SeparatorMenuItem separator3 = new SeparatorMenuItem();
        MenuItem manageUsers = new MenuItem("Manage Users");

        Management.getItems().addAll(manageBooks, separator3, manageUsers);

        //Home menu item action
        home.setOnAction(e -> {   //changing the scene part to user book list
            bpAuthority.setCenter(vxAuthorityControl);//without menubar inside borderpane
        });
        //view profile menu item action
        authorityProfile.setOnAction(e -> {
            showAuthorityProfile();
        });
        //logout menu item action
        logoutMI.setOnAction(e -> {//go back to welcome scene and show message when logout
            window.setScene(welcomeScene);
            logoutMessage.setText("You Are Logged Out!");
        });

        //book management menu item action
        manageBooks.setOnAction(e -> {
            manageLibraryBooks();
        });

        //user management menu item action
        manageUsers.setOnAction(e -> {
            manageUsers();
        });

        authorityMenuBar = new MenuBar();

        authorityMenuBar.getMenus().addAll(currentAuthority, Management);

    }

    //authority profile set
    //show current Authority info, info is editable
    public static void showAuthorityProfile() {
        GridPane gpAuthorityProfile = new GridPane();
        gpAuthorityProfile.setPadding(new Insets(50, 50, 50, 275));
        gpAuthorityProfile.setHgap(40);
        gpAuthorityProfile.setVgap(10);

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
            ResultSet rSet = stmt.executeQuery("Select * from AuthorityInfo where Username='" + currentAuthorityName + "'");

            while (rSet.next()) {

                fullname = rSet.getString("fullname");
                username = rSet.getString("username");
                contact = rSet.getString("contact");
                address = rSet.getString("address");
            }
        } catch (Exception ex) {
            //System.out.println("Error loc: Authority-> showAuthorityProfile()" + ex);
        }

        //user profile
        Label authorityProfile = new Label("Your Profile");
        gpAuthorityProfile.setConstraints(authorityProfile, 1, 0);
        authorityProfile.getStyleClass().add("headline");//css
        //chane profile
        Label editProfile = new Label("Edit Profile");
        gpAuthorityProfile.setConstraints(editProfile, 3, 0);
        editProfile.getStyleClass().add("headline");//css

        //full name
        Label aNameL = new Label("Fullname :");
        gpAuthorityProfile.setConstraints(aNameL, 0, 3);
        aNameL.getStyleClass().add("labelStyle");//css
        //full name result
        Label aName = new Label(fullname);
        gpAuthorityProfile.setConstraints(aName, 1, 3);
        aName.getStyleClass().add("profileLabelStyle");//css
        //edit fullname
        TextField aNameEdit = new TextField();
        aNameEdit.setPromptText("Enter New Name");
        gpAuthorityProfile.setConstraints(aNameEdit, 3, 3);
        aNameEdit.getStyleClass().add("textFieldStyle");//css
        aNameEdit.setPrefWidth(200);//set width, set one will 
        //automatically set other's on same layout

        //username
        Label aUsernameL = new Label("Username :");
        gpAuthorityProfile.setConstraints(aUsernameL, 0, 4);
        aUsernameL.getStyleClass().add("labelStyle");//css
        //username result
        Label aUsername = new Label(username);
        gpAuthorityProfile.setConstraints(aUsername, 1, 4);
        aUsername.getStyleClass().add("profileLabelStyle");//css
        //edit username not editable
        Label aUsernameEdit = new Label(username + "  (Not Changeable)");
        gpAuthorityProfile.setConstraints(aUsernameEdit, 3, 4);
        aUsernameEdit.getStyleClass().add("profileLabelStyle");//css

        //contact
        Label aContactL = new Label("Email/Phone :");
        gpAuthorityProfile.setConstraints(aContactL, 0, 5);
        aContactL.getStyleClass().add("labelStyle");//css
        //contact result
        Label aContact = new Label(contact);
        gpAuthorityProfile.setConstraints(aContact, 1, 5);
        aContact.getStyleClass().add("profileLabelStyle");//css
        //edit contact
        TextField aContactEdit = new TextField();
        aContactEdit.setPromptText("Enter New Email/Phone");
        gpAuthorityProfile.setConstraints(aContactEdit, 3, 5);
        aContactEdit.getStyleClass().add("textFieldStyle");//css

        //address
        Label aAddressL = new Label("Address :");
        gpAuthorityProfile.setConstraints(aAddressL, 0, 6);
        aAddressL.getStyleClass().add("labelStyle");//css
        //address result
        Label aAddress = new Label(address);
        gpAuthorityProfile.setConstraints(aAddress, 1, 6);
        aAddress.getStyleClass().add("profileLabelStyle");//css
        //edit address
        TextField aAddressEdit = new TextField();
        aAddressEdit.setPromptText("Enter New Address");
        gpAuthorityProfile.setConstraints(aAddressEdit, 3, 6);
        aAddressEdit.getStyleClass().add("textFieldStyle");//css

        //password
        Label aPasswordL = new Label("Password :");
        gpAuthorityProfile.setConstraints(aPasswordL, 0, 7);
        aPasswordL.getStyleClass().add("labelStyle");//css
        //pass result
        Label aPassword = new Label("********");
        gpAuthorityProfile.setConstraints(aPassword, 1, 7);
        aPassword.getStyleClass().add("profileLabelStyle");//css
        //edit pass
        PasswordField aPasswordEdit = new PasswordField();
        aPasswordEdit.setPromptText("Enter New Password");
        gpAuthorityProfile.setConstraints(aPasswordEdit, 3, 7);
        aPasswordEdit.getStyleClass().add("textFieldStyle");//css

        //update button
        Button update = new Button("Update Profile");
        gpAuthorityProfile.setConstraints(update, 3, 9);

        //update message
        Label updateMessage = new Label();
        gpAuthorityProfile.setConstraints(updateMessage, 3, 10);
        updateMessage.getStyleClass().add("MessageStyle");//css

        //back button
        Button back = new Button("Back to Home");
        gpAuthorityProfile.setConstraints(back, 3, 20);
        back.getStyleClass().add("closeButtonStyle");//css

        //update button action
        update.setOnAction(e -> {
            //insert updated profile data to authority info table of database
            try {
                Statement stmt = DatabaseConnection.stmt;

                String nameInput = aNameEdit.getText();
                if (!nameInput.isEmpty()) {
                    stmt.executeUpdate("update AuthorityInfo set fullname='" + nameInput
                            + "' where username='" + currentAuthorityName + "'");//update name
                    aName.setText(nameInput);//show new name
                    aNameEdit.setText("");//clear dield
                }

                String contactInput = aContactEdit.getText();
                if (!contactInput.isEmpty()) {
                    stmt.executeUpdate("update AuthorityInfo set contact='" + contactInput
                            + "' where username='" + currentAuthorityName + "'");//update contact
                    aContact.setText(contactInput);//show new contact
                    aContactEdit.setText("");//clear dield
                }
                String addressInput = aAddressEdit.getText();
                if (!addressInput.isEmpty()) {
                    stmt.executeUpdate("update AuthorityInfo set address='" + addressInput
                            + "' where username='" + currentAuthorityName + "'");//update address
                    aAddress.setText(addressInput);//show new address
                    aAddressEdit.setText("");//clear dield
                }
                String passInput = aPasswordEdit.getText();
                if (!passInput.isEmpty()) {
                    stmt.executeUpdate("update AuthorityInfo set password='" + passInput
                            + "' where username='" + currentAuthorityName + "'");//update password
                    aPasswordEdit.setText("");//clear dield
                }
                //show updated message
                updateMessage.setText("Profile Has Been Updated Successfully!");

            } catch (Exception ex2) {
                //System.out.println("Error loc: Authority-> showAuthorityProfile()" + ex2);
            }
        });

        //back button action
        back.setOnAction(e -> {
            bpAuthority.setCenter(vxAuthorityControl);//scene part change to user book list record
        });

        gpAuthorityProfile.getChildren().addAll(authorityProfile, editProfile, aNameL, aName, aNameEdit,
                aUsernameL, aUsername, aUsernameEdit, aContactL, aContact, aContactEdit,
                aAddressL, aAddress, aAddressEdit, aPasswordL, aPassword, aPasswordEdit, update, updateMessage, back);

        //change scene part to userprofile (keeping menubar unchanged)
        bpAuthority.setCenter(gpAuthorityProfile);
    }

    //search book list table
    //add new books in library, drop books from library
    //according to search box, data load from library table to search table
    //search by choosing category 
    public static void manageLibraryBooks() {

        //search text type box
        TextField searchBox = new TextField();//textfield for typing
        searchBox.setPromptText("Type Here to Search");
        searchBox.setPrefWidth(500);
        searchBox.getStyleClass().add("textFieldStyle");//css

        //search by category choose  
        ComboBox<String> chooseCategoryToSearch = new ComboBox<>();
        chooseCategoryToSearch.getItems().addAll("Book_Id", "Book_Name",
                "Author_Name", "Shelf_Id", "Book_Category");
        chooseCategoryToSearch.setValue("Search by  (Default: by Book Name)");
        //chooseCategoryToSearch.setPromptText("Search by... (Default: by Book Name)");
        chooseCategoryToSearch.getStyleClass().add("textFieldStyle");//css

        Button searchButton = new Button("S E A R C H");//click to search

        //hbox to putsearch box and search by in one line
        HBox hbSearch = new HBox(10);
        hbSearch.getChildren().addAll(chooseCategoryToSearch, searchBox, searchButton);
        hbSearch.setAlignment(Pos.CENTER);

        tableManageBook = new TableView<>();

        //column set
        //book id col
        TableColumn<ManageLibraryBookList, String> manageBookIdColumn = new TableColumn<>("Book Id");
        manageBookIdColumn.setCellValueFactory(new PropertyValueFactory<>("libraryBookId"));
        manageBookIdColumn.setMinWidth(165);
        //book name col
        TableColumn<ManageLibraryBookList, String> manageBookNameColumn = new TableColumn<>("Book Name");
        manageBookNameColumn.setCellValueFactory(new PropertyValueFactory<>("libraryBookName"));
        manageBookNameColumn.setMinWidth(290);
        //book author col
        TableColumn<ManageLibraryBookList, String> manageBookAuthorNameColumn = new TableColumn<>("Author Name");
        manageBookAuthorNameColumn.setCellValueFactory(new PropertyValueFactory<>("libraryBookAuthorName"));
        manageBookAuthorNameColumn.setMinWidth(290);
        //shelf id col
        TableColumn<ManageLibraryBookList, String> manageShelfIdColumn = new TableColumn<>("Shelf Id");
        manageShelfIdColumn.setCellValueFactory(new PropertyValueFactory<>("libraryShelfId"));
        manageShelfIdColumn.setMinWidth(165);
        //category column
        TableColumn<ManageLibraryBookList, String> manageBookCategoryColumn = new TableColumn<>("Book Category");
        manageBookCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("bookCategory"));
        manageBookCategoryColumn.setMinWidth(200);

        //set booklistvalue
        //tableSearchList.setItems(getSearchTableDataAuthority());//get items from observable array
        tableManageBook.getColumns().addAll(manageBookIdColumn, manageBookNameColumn,
                manageBookAuthorNameColumn, manageShelfIdColumn, manageBookCategoryColumn);
        //initially load all books, when searchbutton clicked, then only search results
        tableManageBook.setItems(getManageBooksTableData("", "Book_Name"));

        //addSuccessMessage label
        //Label addSuccessMessage = new Label("");
        //addSuccessMessage.getStyleClass().add("MessageStyle");//css
        //back to home button
        Button back = new Button("Back to Home");
        back.getStyleClass().add("closeButtonStyle");//css

        //search button action
        searchButton.setOnAction(e -> {

            //the text which is going to be searched : action in search button action
            String textToSearch = searchBox.getText();

            //choosen category to search by, according to which column of table we search
            //ex: select * from table where book_name like '%t%'; here book_name is choosen category
            //action in search button action
            String textToSearchByChoosen = "Book_Name";//default ->search by Book_name

            if (chooseCategoryToSearch.getValue().equals("Book_Id")) {
                //System.out.println("a");
                textToSearchByChoosen = "Book_Id";
            } else if (chooseCategoryToSearch.getValue().equals("Book_Name")) {
                //System.out.println("b");
                textToSearchByChoosen = "Book_Name";
            } else if (chooseCategoryToSearch.getValue().equals("Author_Name")) {
                //System.out.println("c");
                textToSearchByChoosen = "Author_Name";
            } else if (chooseCategoryToSearch.getValue().equals("Shelf_Id")) {
                //System.out.println("d");
                textToSearchByChoosen = "Shelf_Id";
            } else if (chooseCategoryToSearch.getValue().equals("Book_Category")) {
                // System.out.println("e");
                textToSearchByChoosen = "Book_Category";
            }

            //set booklistvalue : send search text and choosen cate text also
            tableManageBook.setItems(getManageBooksTableData(textToSearch, textToSearchByChoosen));//get items from observable array
        });

        //back button action
        back.setOnAction(e -> {
            bpAuthority.setCenter(vxAuthorityControl);//scene part change to user book list record
        });

        VBox vbAddDrop = setBookAddDrop();

        VBox vbSearch = new VBox(20);//creating vbox only to set padding around
        vbSearch.setPadding(new Insets(20, 30, 20, 30));
        vbSearch.setAlignment(Pos.CENTER);
        vbSearch.getChildren().addAll(hbSearch, tableManageBook, vbAddDrop, back);

        bpAuthority.setCenter(vbSearch);//changing the scene part without menubar inside borderpane
    }

    //search book list observable array
    //initially load all books
    //load books from database library book table to 
    //observable array in search according to search
    //take text to search and choosen text of search by
    public static ObservableList<ManageLibraryBookList> getManageBooksTableData(String textToSearch, String textToSearchByChoosen) {
        //observableArrayList
        ObservableList<ManageLibraryBookList> manageTableData = FXCollections.observableArrayList();

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
                manageTableData.add(new ManageLibraryBookList(book_id, book_name, author_name, shelf_id, book_category));
            }
        } catch (Exception e) {
           // System.out.println("Error loc: Authority->getSearchTableDataAuthority() " + e);
        }
        //default table value manually
//        libraryTableData.add(new LibraryBookList("a-101","Tom and Jerry","Doggy Cat","sd-2"));
//        libraryTableData.add(new LibraryBookList("b-102","Tom","Doggy","sd-2"));
//        libraryTableData.add(new LibraryBookList("c-103","Jerry","Cat","sd-3"));
        return manageTableData;
    }

    // add new book and drop (in laibrary list table in database)
    //add and drop option and action (from library table in db)
    public static VBox setBookAddDrop() {

        //book id
        TextField bookIdInput = new TextField();
        bookIdInput.setPromptText("Book Id");
        bookIdInput.setMinWidth(130);
        bookIdInput.getStyleClass().add("textFieldStyle");//css
        //book name
        TextField bookNameInput = new TextField();
        bookNameInput.setPromptText("Book Name");
        bookNameInput.setMinWidth(250);
        bookNameInput.getStyleClass().add("textFieldStyle");//css
        //author name
        TextField authorNameInput = new TextField();
        authorNameInput.setPromptText("Author Name");
        authorNameInput.setMinWidth(250);
        authorNameInput.getStyleClass().add("textFieldStyle");//css
        //shelf id
        TextField shelfIdInput = new TextField();
        shelfIdInput.setPromptText("Shelf Id");
        shelfIdInput.setMinWidth(130);
        shelfIdInput.getStyleClass().add("textFieldStyle");//css
        //book category
        TextField bookCategoryInput = new TextField();
        bookCategoryInput.setPromptText("Book Category");
        bookCategoryInput.setMinWidth(160);
        bookCategoryInput.getStyleClass().add("textFieldStyle");//css

        Label addDropMessage = new Label();//shows add/drop success/fails
        addDropMessage.getStyleClass().add("MessageStyle");//css

        Button add = new Button("Add New Record to Library");
        Button drop = new Button("Drop Selected Record");
        //add action
        add.setOnAction(e -> {
            try {
                String bookId = bookIdInput.getText();
                String bookName = bookNameInput.getText();
                String author = authorNameInput.getText();
                String shelfId = shelfIdInput.getText();
                String category = bookCategoryInput.getText();

                Statement stmt = DatabaseConnection.stmt;
                ResultSet rSet = stmt.executeQuery("Select book_id from LibraryBook");
                int checker = 0;//use to check duplicate id exist and stuffs
                //book_id must be unique(primary key), to avoid insert error, fisrt check id
                while (rSet.next()) {
                    if (rSet.getString("book_id").equalsIgnoreCase(bookId)) {
                        addDropMessage.setText("Book_Id Already Exists, Choose Another Id");
                        checker = 1;
                        break;
                    }
                }
                if (checker == 0) {//means unique id input
                    //ensure no textfield is empty
                    if (!bookId.isEmpty() && !bookName.isEmpty() && !author.isEmpty()
                            && !shelfId.isEmpty() && !category.isEmpty()) {

                        String newBookInsert = "insert into LibraryBook values('" + bookId + "','" + bookName
                                + "','" + author + "','" + shelfId + "','" + category + "')";
                        stmt.executeUpdate(newBookInsert);

                        //add new value to table to  show
                        ManageLibraryBookList newBook = new ManageLibraryBookList();
                        newBook.setLibraryBookId(bookId);//book id
                        newBook.setLibraryBookName(bookName);//book name
                        newBook.setLibraryBookAuthorName(author);//author name
                        newBook.setLibraryShelfId(shelfId);//shelf id
                        newBook.setbookCategory(category);//category

                        tableManageBook.getItems().add(newBook);

                        addDropMessage.setText("New Book Has Been Added Successfully!");
                        
                        totalBooks++; // increase book numbers
                        totalBooksShow.setText(totalBooks+" Books");
                        
                        bookIdInput.clear();
                        bookNameInput.clear();
                        authorNameInput.clear();
                        shelfIdInput.clear();
                        bookCategoryInput.clear();
                    } else {
                        addDropMessage.setText("Please, Fill Up Properly!");
                    }
                }
            } catch (Exception ex) {
                //System.out.println("Error loc: Authority-> setBookAddDrop()" + ex);
            }
        });
        //drop action
        drop.setOnAction(e -> {
            ObservableList<ManageLibraryBookList> selectedToDrop, allTableItems;

            allTableItems = tableManageBook.getItems();
            selectedToDrop = tableManageBook.getSelectionModel().getSelectedItems();//get selected items
            //get the bookid from selected row
            String selectedBookId = selectedToDrop.get(0).getLibraryBookId();
            //drop/delete the whole row
            selectedToDrop.forEach(allTableItems::remove);

            //drop the selected row from database currentusertable by bookid
            try {
                Statement stmt = DatabaseConnection.stmt;

                String dropBookQuery = "delete from LibraryBook where book_id='" + selectedBookId + "'";
                stmt.executeUpdate(dropBookQuery);

                addDropMessage.setText("Book Has Been Deleted Successfully!");
                totalBooks--;// decrease book numbers
                totalBooksShow.setText(totalBooks+" Books");
            } catch (Exception ex2) {
                //System.out.println("Error Loc: Authority -> setBookAddDrop()" + ex2);
            }
        });

        HBox hbEdit = new HBox();
        hbEdit.setPadding(new Insets(10, 0, 5, 0));
        hbEdit.setSpacing(15);
        //hbEdit.setAlignment(Pos.CENTER);

        hbEdit.getChildren().addAll(bookIdInput, bookNameInput, authorNameInput,
                shelfIdInput, bookCategoryInput);

        HBox hbEdit2 = new HBox();
        hbEdit2.setPadding(new Insets(0, 0, 5, 0));
        hbEdit2.setSpacing(15);
        hbEdit2.getChildren().addAll(drop, addDropMessage);
        hbEdit2.setAlignment(Pos.CENTER);

        //add.setAlignment(Pos.CENTER);
        VBox vbAddDrop = new VBox(10);
        vbAddDrop.getChildren().addAll(hbEdit2, hbEdit, add);
        //vbAddDrop.setAlignment(Pos.CENTER);
        return vbAddDrop;
    }

    //manage users
    //search users, see users info
    //delete users
    public static void manageUsers() {
        //search text type box
        TextField searchBox = new TextField();//textfield for typing
        searchBox.setPromptText("Type Here to Search");
        searchBox.setPrefWidth(500);
        searchBox.getStyleClass().add("textFieldStyle");//css

        //search by category choose  
        ComboBox<String> chooseCategoryToSearch = new ComboBox<>();
        chooseCategoryToSearch.getItems().addAll("Fullname",
                "Username", "Contact", "Address");
        chooseCategoryToSearch.setValue("Search by  (Default: by Fullname)");
        //chooseCategoryToSearch.setPromptText("Search by... (Default: by Fullname)");
        chooseCategoryToSearch.getStyleClass().add("textFieldStyle");//css

        Button searchButton = new Button("S E A R C H");//click to search

        //hbox to putsearch box and search by in one line
        HBox hbSearch = new HBox(10);
        hbSearch.getChildren().addAll(chooseCategoryToSearch, searchBox, searchButton);
        hbSearch.setAlignment(Pos.CENTER);

        tableManageUser = new TableView<>();

        //column set
        //book name col
        TableColumn<ManageUserList, String> manageFullnameColumn = new TableColumn<>("Fullname");
        manageFullnameColumn.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        manageFullnameColumn.setMinWidth(250);
        //book author col
        TableColumn<ManageUserList, String> manageUsernameColumn = new TableColumn<>("Username");
        manageUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        manageUsernameColumn.setMinWidth(155);
        //shelf id col
        TableColumn<ManageUserList, String> manageContactColumn = new TableColumn<>("Contact");
        manageContactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        manageContactColumn.setMinWidth(250);
        //category column
        TableColumn<ManageUserList, String> manageAddressColumn = new TableColumn<>("Address");
        manageAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        manageAddressColumn.setMinWidth(300);
        //password column
        TableColumn<ManageUserList, String> managePasswordColumn = new TableColumn<>("Password");
        managePasswordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        managePasswordColumn.setMinWidth(155);

        //set booklistvalue
        //tableSearchList.setItems(getSearchTableDataAuthority());//get items from observable array
        tableManageUser.getColumns().addAll(manageFullnameColumn,
                manageUsernameColumn, manageContactColumn, manageAddressColumn, managePasswordColumn);
        //initially load all books, when searchbutton clicked, then only search results
        tableManageUser.setItems(getManageUsersTableData("", "Fullname"));

        //addSuccessMessage label
        //Label addSuccessMessage = new Label("");
        //addSuccessMessage.getStyleClass().add("MessageStyle");//css
        //addbook button
        //Button addBook = new Button("Add to My List");
        //back to home button
        Button back = new Button("Back to Home");
        back.getStyleClass().add("closeButtonStyle");//css

        //search button action
        searchButton.setOnAction(e -> {

            //the text which is going to be searched : action in search button action
            String textToSearch = searchBox.getText();

            //choosen category to search by, according to which column of table we search
            //ex: select * from table where book_name like '%t%'; here book_name is choosen category
            //action in search button action
            String textToSearchByChoosen = "Fullname";//default ->search by Book_name

            if (chooseCategoryToSearch.getValue().equals("Fullname")) {
                //System.out.println("b");
                textToSearchByChoosen = "Fullname";
            } else if (chooseCategoryToSearch.getValue().equals("Username")) {
                //System.out.println("c");
                textToSearchByChoosen = "Username";
            } else if (chooseCategoryToSearch.getValue().equals("Contact")) {
                //System.out.println("d");
                textToSearchByChoosen = "Contact";
            } else if (chooseCategoryToSearch.getValue().equals("Address")) {
                // System.out.println("e");
                textToSearchByChoosen = "Address";
            }

            //set booklistvalue : send search text and choosen cate text also
            tableManageUser.setItems(getManageUsersTableData(textToSearch, textToSearchByChoosen));//get items from observable array
        });

        //back button action
        back.setOnAction(e -> {
            bpAuthority.setCenter(vxAuthorityControl);//scene part change to user book list record
        });

        HBox hbEdit = setUserDrop();

        VBox vbSearch = new VBox(20);//creating vbox only to set padding around
        vbSearch.setPadding(new Insets(20, 30, 20, 30));
        vbSearch.setAlignment(Pos.CENTER);
        vbSearch.getChildren().addAll(hbSearch, tableManageUser, hbEdit, back);

        bpAuthority.setCenter(vbSearch);//changing the scene part without menubar inside borderpane
    }

    //initially load all users
    //load users from database 
    // observable array in search according to search
    //take text to search and choosen text of search by
    public static ObservableList<ManageUserList> getManageUsersTableData(String textToSearch, String textToSearchByChoosen) {
        //observableArrayList
        ObservableList<ManageUserList> manageUserTableData = FXCollections.observableArrayList();

        //search result shows in serch table loaded from library table database
        try {
            Statement stmt = DatabaseConnection.stmt;

            //search query : textToSearch is from searchbox field
            //textToSearchByChoosen is from choosen search by combo box
            ResultSet rSet = stmt.executeQuery("Select * from UserInfo where " + textToSearchByChoosen + " like '%" + textToSearch + "%'");
            //ResultSet rSet = stmt.executeQuery("Select * from LibraryBook");

            while (rSet.next()) {
                // SearchBookList newLBL = new SearchBookList();

                String fullname = rSet.getString("Fullname");
                String username = rSet.getString("Username");
                String contact = rSet.getString("Contact");
                String address = rSet.getString("Address");
                String password = rSet.getString("Password");
                manageUserTableData.add(new ManageUserList(fullname, username, contact, address, password));
            }
        } catch (Exception e) {
            //System.out.println("Error loc: Authority->getManageUsersTableData() " + e);
        }
        //default table value manually
//        libraryTableData.add(new LibraryBookList("a-101","Tom and Jerry","Doggy Cat","sd-2"));
//        libraryTableData.add(new LibraryBookList("b-102","Tom","Doggy","sd-2"));
//        libraryTableData.add(new LibraryBookList("c-103","Jerry","Cat","sd-3"));
        return manageUserTableData;
    }

    //drop users
    //drop actions

    public static HBox setUserDrop() {

        Label dropMessage = new Label();//shows add/drop success/fails
        dropMessage.getStyleClass().add("MessageStyle");//css

        Button drop = new Button("Drop Selected User");

        //drop action
        drop.setOnAction(e -> {
            ObservableList<ManageUserList> selectedToDrop, allTableItems;

            allTableItems = tableManageUser.getItems();
            selectedToDrop = tableManageUser.getSelectionModel().getSelectedItems();//get selected items
            //get the bookid from selected row
            String selectedUsername = selectedToDrop.get(0).getUsername();
            //drop/delete the whole row
            selectedToDrop.forEach(allTableItems::remove);

            //drop the selected row from database currentusertable by bookid
            try {
                Statement stmt = DatabaseConnection.stmt;
                //drop user from user info
                String dropUserQuery = "delete from UserInfo where Username='" + selectedUsername + "'";
                stmt.executeUpdate(dropUserQuery);
                //drop user data table from database
                String userTableNameToDrop = selectedUsername + "spruce";
                String dropUserTableQuery = "drop table " + userTableNameToDrop + "";
                stmt.executeUpdate(dropUserTableQuery);
                
                totalUsers--;//decrease book numbers
                totalUsersShow.setText(totalUsers+" Users");
                dropMessage.setText("User Has Been Deleted Successfully!");
                
            } catch (Exception ex2) {
               // System.out.println("Error Loc: Authority -> setUserAddDrop()" + ex2);
            }
        });

        HBox hbEdit = new HBox();
        hbEdit.setPadding(new Insets(0, 0, 5, 0));
        hbEdit.setSpacing(10);
        hbEdit.getChildren().addAll(drop, dropMessage);
        hbEdit.setAlignment(Pos.CENTER);

        return hbEdit;
    }

}
