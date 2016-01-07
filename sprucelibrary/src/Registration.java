/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Touhidul_MTI
 */
import java.sql.*;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;

//user and authority registration form
public class Registration {

    static Stage regStage;
    static Scene regScene;
    static Scene regSceneA;
    static String fullName, userName, contact, password, address;//for user
    static String fullNameA, userNameA, contactA, passwordA, addressA;//for authority (sign A)
    
    static Label regMessage, regMessageA;
    //create user registration from and ensure proper input
    //create new table in database for new registered user

    public static void registrationForm() {
        regStage = new Stage();
        regStage.setTitle("Registration Form");
        regStage.initModality(Modality.APPLICATION_MODAL);

        authorityRegistrationForm();//call authority registration form to be ready

        GridPane gpUserReg = new GridPane();
        gpUserReg.setPadding(new Insets(10, 10, 10, 10));
        gpUserReg.setHgap(10);
        gpUserReg.setVgap(10);

        //user registration
        Label userRegistrationL = new Label("User Registration");
        gpUserReg.setConstraints(userRegistrationL, 1, 0);
        userRegistrationL.getStyleClass().add("headline");//css

        //fullname label
        Label fullnameL = new Label("Full Name");
        gpUserReg.setConstraints(fullnameL, 0, 2);
        fullnameL.getStyleClass().add("labelStyle");//css
        //fullname field
        TextField fullnameF = new TextField();
        fullnameF.setPromptText("Enter Your Full Name");//for watermark writing
        gpUserReg.setConstraints(fullnameF, 1, 2);
        fullnameF.getStyleClass().add("textFieldStyle");
        fullnameF.setPrefWidth(250); //increasing text field width
        //automatically increase field in this form 

        //username label
        Label userNameL = new Label("Username");
        gpUserReg.setConstraints(userNameL, 0, 3);
        userNameL.getStyleClass().add("labelStyle");//css
        //user field
        TextField userNameF = new TextField();
        userNameF.setPromptText("Enter Username");//for watermark writing
        gpUserReg.setConstraints(userNameF, 1, 3);
        userNameF.getStyleClass().add("textFieldStyle");

        //contact
        Label contactL = new Label("Email/Phone");
        gpUserReg.setConstraints(contactL, 0, 4);
        contactL.getStyleClass().add("labelStyle");//css
        //contact field
        TextField contactF = new TextField();
        contactF.setPromptText("Valid Email or Phone");//for watermark writing
        gpUserReg.setConstraints(contactF, 1, 4);
        contactF.getStyleClass().add("textFieldStyle");

        //address
        Label addressL = new Label("Address");
        gpUserReg.setConstraints(addressL, 0, 5);
        addressL.getStyleClass().add("labelStyle");//css
        //address field
        TextField addressF = new TextField();
        addressF.setPromptText("Residential Address");//for watermark writing
        gpUserReg.setConstraints(addressF, 1, 5);
        addressF.getStyleClass().add("textFieldStyle");

        //password label
        Label passwordL = new Label("Password");
        gpUserReg.setConstraints(passwordL, 0, 6);
        passwordL.getStyleClass().add("labelStyle");//css
        //pass field
        PasswordField passwordF = new PasswordField();
        passwordF.setPromptText("Enter Password");//for watermark writing
        gpUserReg.setConstraints(passwordF, 1, 6);
        passwordF.getStyleClass().add("textFieldStyle");

//        //student ckheck
//        Label studentL=new Label("Are You Student?");
//        gpUserReg.setConstraints(studentL,0,6);
//        //yes no
//        CheckBox studentCheck=new CheckBox("(Click if Yes)");
//        gpUserReg.setConstraints(studentCheck,1,6);
        //submit button
        Button submit = new Button("Submit Now");
        gpUserReg.setConstraints(submit, 1, 8);
        //message level
        regMessage = new Label();
        gpUserReg.setConstraints(regMessage, 1, 9);
        regMessage.getStyleClass().add("MessageStyle");//css

        //authority registration
        Button authorityB = new Button("Authority Registration");
        gpUserReg.setConstraints(authorityB, 1, 15);
        authorityB.getStyleClass().add("registrationButtonStyle");

        //close registration form
        Button closeReg = new Button("Close Registration Form");
        gpUserReg.setConstraints(closeReg, 1, 19);
        closeReg.getStyleClass().add("closeButtonStyle");

        submit.setOnAction(e -> {

            if (fullnameF.getText().isEmpty() || userNameF.getText().isEmpty()
                    || contactF.getText().isEmpty() || addressF.getText().isEmpty()
                    || passwordF.getText().isEmpty()) {    //check any field is empty or not
                regMessage.setText("You Missed to \nFill up Something!");
            } else {
                int existUsernameCount = 0;//to check duplicate username
                //database
                try {
                    Statement stmt = DatabaseConnection.stmt;

                    //create a personal table for this new user using ***
                    //table name will be username+Spruce ****
                    String usernameQuery = "select Username from userinfo";//check in userinfo
                    ResultSet rSet = stmt.executeQuery(usernameQuery);//username get from userinfo

                    while (rSet.next()) {
                        if (userNameF.getText().equalsIgnoreCase(rSet.getString("Username"))) {
                            existUsernameCount = 1;
                            break;
                        }
                    }
                    
                    if (existUsernameCount == 0) {
                        String authorityUsernameQuery = "select Username from authorityinfo";//check in authority info
                        rSet = stmt.executeQuery(authorityUsernameQuery);//username get from authorityinfo

                        while (rSet.next()) {
                            if (userNameF.getText().equalsIgnoreCase(rSet.getString("Username"))) {
                                existUsernameCount = 1;
                                break;
                            }
                        }
                    }

                } catch (Exception excep) {
                   // System.out.println("Error Location: Registration -> RegistrationForm()" + excep);
                }
                if (existUsernameCount == 0) {
                    fullName = fullnameF.getText();
                    userName = userNameF.getText();
                    contact = contactF.getText();
                    address = addressF.getText();
                    password = passwordF.getText();

                    //database store
                    try {
                        Statement stmt = DatabaseConnection.stmt;

                        //create a personal table for this new user using ***
                        //table name will be username+Spruce ****
                        String newTableName = userName + "Spruce";
                        String newTableCreateQuery = "create table " + newTableName + " (Book_Name varchar(50),"
                                + "Book_Id varchar(15) primary key, Issue_Date Date, Due_Date Date, Fine_TK double)";
                        stmt.executeUpdate(newTableCreateQuery);//table creating for this user

                        // insert new userinfo into UserInfo table
                        String userInfoInsertQuery = "insert into UserInfo values('" + fullName + "','" + userName + "','" + contact + "','" + address + "','" + password + "')";
                        stmt.executeUpdate(userInfoInsertQuery);

                    } catch (Exception excep) {
                        //System.out.println("Error Location: Registration -> RegistrationForm()" + excep);
                    }
                    fullnameF.clear();//clear input
                    userNameF.clear();
                    contactF.clear();
                    addressF.clear();
                    passwordF.clear();
                //these 2 lines no need more
                    //LibraryProject.enteredUserName = userName;//for login test
                    //LibraryProject.enteredPassword = password;//delete after adding database

                    //regMessage.setText("Your Registration Successfull!\n"
                    //                 + "Now You can Login.");
                    regStage.close();
                    LibraryMain.logoutMessage.setText("Registration Successfull!\n"
                            + "You Can Login Now");
                } else {
                    regMessage.setText("Username " + userNameF.getText() + "\n"
                            + "Already Exist!");
                }
            }
        });
        authorityB.setOnAction(e -> {
            regStage.setScene(regSceneA);//appear authority registration form
            regMessage.setText("");//set empty, in case previous message displayed
        });
        closeReg.setOnAction(e -> {
            regStage.close();
        });

        gpUserReg.getChildren().addAll(submit, fullnameL, fullnameF, userNameL, userNameF,
                contactL, contactF, addressL, addressF, passwordL, passwordF,
                userRegistrationL, regMessage, authorityB, closeReg);
        gpUserReg.setAlignment(Pos.CENTER);

        regScene = new Scene(gpUserReg, 430, 600);

        regScene.getStylesheets().add("WelcomeSceneStyle.css");//css

        regStage.setScene(regScene);
        regStage.showAndWait();
    }

    public static void authorityRegistrationForm() {
        //last letter every word contains refer to authority related
        GridPane gpUserRegA = new GridPane();
        gpUserRegA.setPadding(new Insets(10, 10, 10, 10));
        gpUserRegA.setHgap(10);
        gpUserRegA.setVgap(10);

        //user registration
        Label userRegistrationLA = new Label("Authority Registration");
        gpUserRegA.setConstraints(userRegistrationLA, 1, 0);
        userRegistrationLA.getStyleClass().add("headline");//css

        //fullname label
        Label fullnameLA = new Label("Full Name");
        gpUserRegA.setConstraints(fullnameLA, 0, 2);
        fullnameLA.getStyleClass().add("labelStyle");//css
        //fullname field
        TextField fullnameFA = new TextField();
        fullnameFA.setPromptText("Enter Your Full Name");//for watermark writing
        gpUserRegA.setConstraints(fullnameFA, 1, 2);
        fullnameFA.getStyleClass().add("textFieldStyle");//css
        fullnameFA.setPrefWidth(250); //increasing text field width
        //automatically increase field in this form 

        //username label
        Label userNameLA = new Label("Username");
        gpUserRegA.setConstraints(userNameLA, 0, 3);
        userNameLA.getStyleClass().add("labelStyle");//css
        //user field
        TextField userNameFA = new TextField();
        userNameFA.setPromptText("Enter Username");//for watermark writing
        gpUserRegA.setConstraints(userNameFA, 1, 3);
        userNameFA.getStyleClass().add("textFieldStyle");//css

        //contact
        Label contactLA = new Label("Email/Phone");
        gpUserRegA.setConstraints(contactLA, 0, 4);
        contactLA.getStyleClass().add("labelStyle");//css
        //contact field
        TextField contactFA = new TextField();
        contactFA.setPromptText("Valid Email or Phone");//for watermark writing
        gpUserRegA.setConstraints(contactFA, 1, 4);
        contactFA.getStyleClass().add("textFieldStyle");//css

        //address
        Label addressLA = new Label("Address");
        gpUserRegA.setConstraints(addressLA, 0, 5);
        addressLA.getStyleClass().add("labelStyle");//css
        //address field
        TextField addressFA = new TextField();
        addressFA.setPromptText("Residential Address");//for watermark writing
        gpUserRegA.setConstraints(addressFA, 1, 5);
        addressFA.getStyleClass().add("textFieldStyle");//css

        //password label
        Label passwordLA = new Label("Password");
        gpUserRegA.setConstraints(passwordLA, 0, 6);
        passwordLA.getStyleClass().add("labelStyle");//css
        //pass field
        PasswordField passwordFA = new PasswordField();
        passwordFA.setPromptText("Enter Password");//for watermark writing
        gpUserRegA.setConstraints(passwordFA, 1, 6);
        passwordFA.getStyleClass().add("textFieldStyle");//css

        //special administrative key
        Label adminKeyLA = new Label("Admin Key");
        gpUserRegA.setConstraints(adminKeyLA, 0, 7);
        adminKeyLA.getStyleClass().add("labelStyle");//css
        //admin key textfield
        TextField adminKeyFA = new TextField();
        adminKeyFA.setPromptText("Admin Permission Key");//for watermark writing
        gpUserRegA.setConstraints(adminKeyFA, 1, 7);
        adminKeyFA.getStyleClass().add("textFieldStyle");//css

//        //student ckheck
//        Label studentL=new Label("Are You Student?");
//        gpUserReg.setConstraints(studentL,0,6);
//        //yes no
//        CheckBox studentCheck=new CheckBox("(Click if Yes)");
//        gpUserReg.setConstraints(studentCheck,1,6);
        //submit button
        Button submitA = new Button("Submit Now");
        gpUserRegA.setConstraints(submitA, 1, 9);

        //message level
        regMessageA = new Label();
        gpUserRegA.setConstraints(regMessageA, 1, 10);
        regMessageA.getStyleClass().add("MessageStyle");//css

        //authority registration
        Button userBA = new Button("User Registration");
        gpUserRegA.setConstraints(userBA, 1, 15);
        userBA.getStyleClass().add("registrationButtonStyle");//css

        //close registration form
        Button closeRegA = new Button("Close Registration");
        gpUserRegA.setConstraints(closeRegA, 1, 19);
        closeRegA.getStyleClass().add("closeButtonStyle");//css

        submitA.setOnAction(e -> {

            if (fullnameFA.getText().isEmpty() || userNameFA.getText().isEmpty()
                    || contactFA.getText().isEmpty() || addressFA.getText().isEmpty()
                    || passwordFA.getText().isEmpty() || adminKeyFA.getText().isEmpty()) {
                regMessageA.setText("You Missed to \nFill up Something!");
            } else if (!adminKeyFA.getText().equals("admin")) {
                regMessageA.setText("You Must Need \nAuthorization Key!");
            } else {
                int existUsernameCount = 0;//to check duplicate username
                //database
                try {
                    Statement stmt = DatabaseConnection.stmt;

                    //create a personal table for this new user using ***
                    //table name will be username+Spruce ****
                    String AuthorityUsernameQuery = "select Username from authorityinfo";//check in authorityinfo
                    ResultSet rSet = stmt.executeQuery(AuthorityUsernameQuery);//username get from authorityinfo
                    
                    while (rSet.next()) {
                        if (userNameFA.getText().equalsIgnoreCase(rSet.getString("Username"))) {
                            existUsernameCount = 1;
                            break;
                        }
                    }
                    
                    if (existUsernameCount == 0) {
                        String usernameQuery = "select Username from userinfo";//check in userinfo
                        rSet = stmt.executeQuery(usernameQuery);//username get from authorityinfo

                        while (rSet.next()) {
                            if (userNameFA.getText().equalsIgnoreCase(rSet.getString("Username"))) {
                                existUsernameCount = 1;
                                break;
                            }
                        }
                    }

                } catch (Exception excep) {
                    //System.out.println("Error Location: Registration -> authorityRegistrationForm()" + excep);
                }
                if (existUsernameCount == 0) {
                    fullNameA = fullnameFA.getText();
                    userNameA = userNameFA.getText();
                    contactA = contactFA.getText();
                    addressA = addressFA.getText();
                    passwordA = passwordFA.getText();
                    //database store
                    try {
                        Statement stmt = DatabaseConnection.stmt;

                        // insert new userinfo into UserInfo table
                        String authorityInfoInsertQuery = "insert into AuthorityInfo values('" + fullNameA + "','" + userNameA + "','" + contactA + "','" + addressA + "','" + passwordA + "')";
                        stmt.executeUpdate(authorityInfoInsertQuery);

                    } catch (Exception excep) {
                        //System.out.println("Error Location: Registration -> AuthorityRegistrationForm()" + excep);
                    }
                    fullnameFA.clear();//clear input
                    userNameFA.clear();
                    contactFA.clear();
                    addressFA.clear();
                    passwordFA.clear();
                //regMessage.setText("Your Registration Successfull!\n"
                    //                 + "Now You can Login.");
                    regStage.close();
                    LibraryMain.logoutMessage.setText("Registration Successfull!\n"
                            + "You Can Login Now");
                } else {
                    regMessageA.setText("Username " + userNameFA.getText() + "\n"
                            + "Already Exist!");
                }
            }
        });
        userBA.setOnAction(e -> {
            regStage.setScene(regScene);//appear user registration form
            regMessageA.setText("");//set empty, in case previous message displayed
        });

        closeRegA.setOnAction(e -> {
            regStage.close();
        });

        gpUserRegA.getChildren().addAll(submitA, fullnameLA, fullnameFA, userNameLA, userNameFA,
                contactLA, contactFA, addressLA, addressFA, passwordLA, passwordFA,
                userRegistrationLA, regMessageA, adminKeyLA, adminKeyFA, userBA, closeRegA);
        gpUserRegA.setAlignment(Pos.CENTER);

        regSceneA = new Scene(gpUserRegA, 430, 600);

        regSceneA.getStylesheets().add("WelcomeSceneStyle.css");
    }
}
