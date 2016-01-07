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
//import java.sql.SQLException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

//main class
//app runs from here
public class LibraryMain extends Application {

    public static Stage window;
    static Scene welcomeScene;
    GridPane gpLogin;
    static Label logoutMessage;

    static String currentUserName = null, currentPassword = null;
    static String currentAuthorityName = null, currentAuthorityPassword = null;
    Label quote;


    public static void main(String[] args) {
        launch(args);
    }

    //main/first/welcome window with properties
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Spruce Library");

        //window.setResizable(false);
        window.setMinWidth(1215);
        window.setMinHeight(685);

        //window.setMaxWidth(1370);
        //window.setMaxHeight(725);
        Image icon = new Image("/pic/icon.png");
        
        window.getIcons().add(icon);
        window.setOnCloseRequest(e ->{ //App closing permission on red cross press
            e.consume();
            closeApp();
        });

        Label libraryName = new Label("Welcome to Spruce Library!");
        libraryName.setId("libraryNameStyleId");
        run_Spruce_LibraryDatabase_Database();//start database
        
        //set login form
        loginForm();

        Image library = new Image(getClass().getResourceAsStream("/pic/library.png"), 600, 390, true, true);
        ImageView wallpaper = new ImageView(library);
        wallpaper.setId("waqllpaperStyle");//css

        String quotation = "Read the best books first, or you may not have a chance to read"
            + " them at all. –Henry David Thoreau";
        quote = new Label(quotation);
        quote.setId("quoteStyle");//css

        StackPane sp = new StackPane();
        sp.getChildren().add(wallpaper);
        sp.setPadding(new Insets(10, 0, 10, 20));
        
        //developer button
        Button ddButton = new Button("Designer/Developer");
        ddButton.setStyle("-fx-background-color:#383838;-fx-text-fill: #993333");//css
        ddButton.setOnAction(e -> {
            new DesignerAndDeveloper().designerAndDeveloper();
        });
        //exit button
        Button exit = new Button("Exit");
        exit.setStyle("-fx-background-color:#383838");
        exit.setOnAction(e -> {
            closeApp();
        });
        Label about = new Label("SPRUCE LIBRARY:\n"
                + "(A Library Management Software)\n"
                + "-Authority Login/Registration\n"
                + "-Authority Control Panel\n"
                + "-Authority Ability\n"
                + "        -View/Edit Profile\n"
                + "        -Control Users\n"
                + "        -Manage Library Books\n"
                + "-User Login/Registration\n"
                + "-Individual User Panel\n"
                + "-Users Ability\n"
                + "        -View/Edit Profile\n"
                + "        -Search Book(By Category)\n"
                + "        -Add/Drop Books\n"
                + "        -Issue/Due Date And Fine");

        about.setId("aboutStyle");//css
        VBox vxAbout = new VBox(130);
        vxAbout.getChildren().addAll(about, exit);

        HBox hxQuote = new HBox(30);
        hxQuote.getChildren().addAll(ddButton, quote);
        
        //create borderpane and set all in border pane
        BorderPane bpWindow = new BorderPane();
        bpWindow.setPadding(new Insets(25, 25, 20, 30));

        bpWindow.setTop(libraryName);//set on top of borderpane
        bpWindow.setAlignment(libraryName, Pos.CENTER);
        bpWindow.setLeft(vxAbout);//set on left of borderpane
        vxAbout.setAlignment(Pos.CENTER);
        //bpWindow.setAlignment(about, Pos.CENTER);
        bpWindow.setCenter(sp);//set on center of borderpane
        bpWindow.setRight(gpLogin);//set on right of borderpane
        bpWindow.setBottom(hxQuote);//set on bottom of borderpane
        

        welcomeScene = new Scene(bpWindow, 1200, 650);
        //css set
        welcomeScene.getStylesheets().add("WelcomeSceneStyle.css");
        window.setScene(welcomeScene);
        window.show();
    }

    //proper app closing
    public void closeApp() {
        boolean closePermission = Confirmation.confirm("Are You Sure You Want to Leave Library?");
        if (closePermission) {
            window.close();
        }
    }

    //login form and actions
    private void loginForm() {

        gpLogin = new GridPane();
        gpLogin.setPadding(new Insets(10, 10, 10, 10));
        gpLogin.setHgap(10);
        gpLogin.setVgap(10);

        //logout message
        logoutMessage = new Label();
        gpLogin.setConstraints(logoutMessage, 1, 0);
        logoutMessage.getStyleClass().add("MessageStyle");//css

        //uselogin label
        Label userlogin = new Label("User Login");
        gpLogin.setConstraints(userlogin, 1, 2);
        userlogin.getStyleClass().add("headline");//css

        //usename label
        Label userNameL = new Label("Username");
        gpLogin.setConstraints(userNameL, 0, 4);
        userNameL.getStyleClass().add("labelStyle");//css
        //username field
        TextField userName = new TextField();
        userName.setPromptText("Enter Username");//for watermark writing
        gpLogin.setConstraints(userName, 1, 4);
        userName.getStyleClass().add("textFieldStyle");

        //password label
        Label passwordL = new Label("Password");
        gpLogin.setConstraints(passwordL, 0, 5);
        passwordL.getStyleClass().add("labelStyle");//css
        //password field

        // TextField password = new TextField();
        PasswordField password = new PasswordField();
        password.setPromptText("Enter Password");//for watermark writing
        gpLogin.setConstraints(password, 1, 5);
        password.getStyleClass().add("textFieldStyle");

        //login button
        Button loginB = new Button("Log In");
        gpLogin.setConstraints(loginB, 1, 6);

        //error label
        Label loginMessage = new Label();
        gpLogin.setConstraints(loginMessage, 1, 7);
        loginMessage.getStyleClass().add("MessageStyle");//css
        //registration
        Label regLabel = new Label("Join to the Spruce Library");
        gpLogin.setConstraints(regLabel, 1, 10);
        Button registration = new Button("Free Registration");
        gpLogin.setConstraints(registration, 1, 11);
        registration.getStyleClass().add("registrationButtonStyle");

        //label,field,button to grid
        gpLogin.getChildren().addAll(loginB, userNameL, userName, passwordL, password,
                loginMessage, userlogin, logoutMessage, regLabel, registration);
        gpLogin.setAlignment(Pos.CENTER);

        ///action
        loginB.setOnAction(e -> {
            if (!userName.getText().isEmpty() && !password.getText().isEmpty()) {
                //database query
                try {
                    Statement stmt = DatabaseConnection.stmt;
                    String lineToBeExecuted = "select username, password from UserInfo";
                    ResultSet rSet = stmt.executeQuery(lineToBeExecuted);

                    int foundCounter = 0;//to check any valid result matched or not
                    while (rSet.next()) {
                        if (userName.getText().equalsIgnoreCase(rSet.getString("username"))
                                && password.getText().equals(rSet.getString("password"))) {

                            currentUserName = rSet.getString("username");//if match, then keep to in user scene
                            currentPassword = rSet.getString("password");//and usefull for individual table 

                            password.clear();//clear pass input
                            loginMessage.setText("");

                            User.setUserScene();//call to set user scene
                            window.setScene(User.userScene);//switch scene to user

                            foundCounter = 1;//here we found/matched a valid user
                            break;
                        }
                    }
                    if (foundCounter == 0) {//if not found valid user
                        lineToBeExecuted = "select username, password from AuthorityInfo";
                        rSet = stmt.executeQuery(lineToBeExecuted);

                        while (rSet.next()) {
                            if (userName.getText().equalsIgnoreCase(rSet.getString("username"))
                                    && password.getText().equals(rSet.getString("password"))) {

                                currentAuthorityName = rSet.getString("username");//if match, then keep to in authority scene
                                currentAuthorityPassword = rSet.getString("password");//and usefull for individual table 

                                password.clear();//clear pass input
                                loginMessage.setText("");

                                Authority.setAuthorityScene();//call to set Authority scene
                                window.setScene(Authority.authorityScene);//switch scene to Authority

                                foundCounter = 1;//here we found/matched a valid user
                                break;
                            }
                        }

                    }
                    if (foundCounter == 0) {
                        loginMessage.setText("Invalid Username or Password!,\n"
                                + "Please, Try Again \n"
                                + "or, Register Now.");
                        password.clear();//clear pass input
                        logoutMessage.setText("");
                    }

                } catch (Exception excp) {
                    //System.out.println("Error loc: LibraryProject -> LoginForm()" + excp);
                }
            } else {
                loginMessage.setText("Invalid Username or Password!,\n"
                        + "Please, Try Again \n"
                        + "or, Register Now.");
                logoutMessage.setText("");
            }
        });
        
        //registration button action
        registration.setOnAction(e -> {
            logoutMessage.setText("");
            loginMessage.setText("");
            userName.clear();//clear input
            password.clear();
            Registration.registrationForm();
        });
    }

    //call for database connection setup
    private void run_Spruce_LibraryDatabase_Database() throws SQLException {
        try {
            DatabaseConnection dbObject = new DatabaseConnection();
            dbObject.connectDatabase();
        } catch (Exception e) {
            //System.out.println("Error Location: LibraryProject -> run_Spruce_LibraryDatabase_Database()");
        }
    }
}
