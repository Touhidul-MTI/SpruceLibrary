------------------------------------SpruceLibrary---------------------------------- 
Contents: A- Specifications
	  B- Setup
	  C- Conclusion
---------------------------
___________________________
A - Specifications:
-------------------
1. Starting/Welcome window:
	-Login area for both Book readers (Users) and Librarians (Authority).
	-Registration Button which display window for General users, below
		there is authority reg button to display authority registration,
		Authorization key is must for authority reg (I've set the key "Admin").
	-Developer/Designer Informations.
	-Exit Button with confermation sequrity.
2. User:
When a user (Book reader) logs in, a new window appears:
	-Current logged in username will be appeared in menubar.
	-User will see his current activity records like the books he borrowed, 
		issue date, due date, any fine occured or not etc.
	-User can drop books from his curren list.
	-From menu, user can see his profile details and can edit as well.
	-User can select new books from menu where all books are listed in book library.
	-User can search books clicking from menu and according to their choiceful
		categories as well.
	-User can't add one book two times.
	-When a book is added to user current list issue and due (issue + 7 days) date
		will be set autometically, due date over makes 100 tk auto fine.
	-There is logout option in menu.
3. Authority:
When an authority (Librarian) logs in, a new window appears:
	-Current logged in username will be appeared in menubar
	-Authority will see the total registered user, authority number and total books
		stored in the library.
	-From menu, authority can see his profile details and can edit as well.
	-Authority can search books, add new books to library and drop books from there.
	-Authority can see users infoormations and delete users as well.
	-There is logout option in menu.
---------------------------------------------------------------------------------------
_______________________________________________________________________________________
B - Setup:
----------
1. Need Java Runtime Environment in PC/Laptop. So download and install JDK (Java 
	Development Kit) any V.
2. Whole \SpruceLibrary_Runnable_Jar_With_Supporting_Files folder containing:
	-SpruceLibrary.jar file &
	-A sub-folder named lib which contains mysql-connector-java-5.1.37-bin.jar file.
	-(Inside lib folder, you can use any version of mysql-connector-java).
3. Need to create server for SpruceLibrary: (You can use XAMPP or anything you like)
	-Create a database named "spruce_library_database", username="root", password="".
	-Inside database, create 3 tables named LibraryBook, UserInfo & AuthorityInfo.
		Table Creation: Must follow column name, type, key.
	-LibraryBook
		Book_Id varchar(15) Primary Key
		Book_Name varchar(50)
		Author_Name varchar(50)
		Shelf_Id varchar (15)
		Book_Category varchar(30)
	-UserInfo
		Fullname varchar(50)
		Username varchar(40) Primary Key
		Contact varchar(40)
		Address varchar(50)
		Password varchar(30)
	-AuthorityInfo
		Fullname varchar(50)
		Username varchar(40) Primary Key
		Contact varchar(40)
		Address varchar(50)
		Password varchar(30)
4. When upper 3 steps are done, you can run SpruceLibrary.jar file.
5. For Authority Registration you need an authorization key, for now key="Admin".
------------------------------------------------------------------------------------------
__________________________________________________________________________________________
C - Conclusion:
---------------
1. Try this SpruceLibrary a Library management desktop application using JavaFX and mysql.
2. For any query, help feel free to contact me through emails below, contact info also
	given on SpruceLibrary app in desginer/developer area.
	-touhidul.mti@gmail.com
	-mdtislam93@gmail.com
3. Take a visit to new blog MTILAB.BLOGSPOT.COM
4. ThankYou!
------------------------------------------------------------------------------------------
__________________________________________________________________________________________
