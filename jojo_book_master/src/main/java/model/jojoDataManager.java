package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import configuration.*;

public class jojoDataManager {

    public static jojoDataManager sharedInstance;


    public String currentUser;
    public int currentUserId;

    public String currentAdmin;
    public int currentAdminId;

    public int maxItemSize = Configuration.maxItemSize;

    private static final String DB_URL = Configuration.DB_URL;

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    public jojoDataManager() {
        setSharedInstance();
    }

    private void setSharedInstance() {
        sharedInstance = this;
    }

    private String trimStringToSQL(String initialString) {
        return initialString.replace("'", "\\'");
    }

    public boolean connectToDatabase() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println(DB_URL);
            System.out.println("Connecting to database");
            connection = DriverManager.getConnection(DB_URL);
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Database connection error.");
            return false;
        }
        return true;
    }

    public boolean logoutFromDatabase() {
        try {
            if (connection != null) connection.close();
            if (statement != null) statement.close();
            if (resultSet != null) resultSet.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Database close error.");
            return false;
        }
        return true;
    }
        public boolean readerLogin(String userName, String password) {
        String sql = "SELECT cno FROM card WHERE name = '" + userName + "'";
        System.out.println(sql);
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (!resultSet.next()) {
                System.out.println("User name not found.");
                return false;
            }
            int reader_id = resultSet.getInt("cno");
            sql = "SELECT password FROM card WHERE cno = " + String.valueOf(reader_id);
            resultSet = statement.executeQuery(sql);
            resultSet.next();
            String systemPassword = resultSet.getString("password");
            if (password.equals(systemPassword)) {
                System.out.println("Reader identified.");
                this.currentUser = userName;
                this.currentUserId = reader_id;
                return true;
            } else {
                System.out.println("Wrong password, login denied.");
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Error when trying to login the reader.");
            return false;
        }
    }

    public boolean adminLogin(String adminName, String password) {
        String sql = "SELECT admin_id FROM admin WHERE name = '" + adminName + "'";
        System.out.println(sql);
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (!resultSet.next()) {
                System.out.println("Admin name not found.");
                return false;
            }
            int Admin_id = resultSet.getInt("admin_id");
            sql = "SELECT password FROM admin WHERE admin_id = " + String.valueOf(Admin_id);
            resultSet = statement.executeQuery(sql);
            resultSet.next();
            String systemPassword = resultSet.getString("password");
            if (password.equals(systemPassword)) {
                System.out.println("Admin identified.");
                this.currentAdmin = adminName;
                this.currentAdminId = Admin_id;
                return true;
            } else {
                System.out.println("Wrong password, login denied.");
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Error when trying to login the admin.");
            return false;
        }
    }


    //导入一本书
    public boolean importBooks(String bno, String category,String title,  String press, String year,
                               String author, String price, String number) {
        String sql_bno = "SELECT * FROM book WHERE bno = '" + bno + "'";
        category = trimStringToSQL(category);
        title = trimStringToSQL(title);
        press = trimStringToSQL(press);
        author = trimStringToSQL(author);
        String sql = "INSERT INTO Book VALUES ("+ bno + ",'"+ category + "','" + title + "','" + press +
                "'," + year + ",'" + author + "'," + price + "," + number + "," + number + ")";
        System.out.println(sql);
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql_bno);
            if (resultSet.next()) {
                System.out.println("Book Bno found.");
                int old_total = resultSet.getInt("total");
                int new_total = old_total +  Integer.parseInt(number);
                String new_total_string = Integer.toString(new_total);
                int old_stock = resultSet.getInt("stock");
                int new_stock = old_stock + Integer.parseInt(number);
                String new_stock_string = Integer.toString(new_stock);
                String sql_update_total = "UPDATE book SET total =  " + new_total_string + " WHERE bno= '" + bno + "'";
                String sql_update_stock = "UPDATE book SET stock =  " + new_stock_string + " WHERE bno= '" + bno + "'";


                statement.execute(sql_update_total);
                statement.execute(sql_update_stock);
            }
            else {
                System.out.println("Book Bno not found.");
                statement.execute(sql);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Error occurred when inserting a book to the database");
            return false;
        }
        return true;
    }

    //从文件批量导入
    public boolean importBooksFromFile(String fileAddress) {
        try {
            File dataFile = new File(fileAddress);
            //解决中文乱码问题
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(dataFile),"UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            if (!dataFile.isFile() || !dataFile.exists()) {
                System.out.println("File not found");
                return false;
            }
            String[] itemContainer;
            String newItem = bufferedReader.readLine();
            System.out.println(newItem);

            while (newItem != null) {
                itemContainer = newItem.split(" ");
                if (importBooks(itemContainer[0], itemContainer[1], itemContainer[2], itemContainer[3],
                        itemContainer[4], itemContainer[5], itemContainer[6], itemContainer[7])) {
                    System.out.println("Book inserted from file");
                } else {
                    System.out.println("Error when inserting a book from file");
                    return false;
                }
                newItem = bufferedReader.readLine();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Error occurred when import book data file");
            return false;
        }
        return true;
    }

    //按照书号查找书目
    public boolean searchBnoForBook(String[] dataContainer, String bno) {
        String sql = "SELECT * FROM book WHERE bno = '" + bno + "'";
        System.out.println(bno);
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (!resultSet.next()) {
                System.out.println("Book Bno not found.");
                return false;
            }
            dataContainer[0] = resultSet.getString("bno");
            dataContainer[1] = resultSet.getString("category");
            dataContainer[2] = resultSet.getString("title");
            dataContainer[3] = resultSet.getString("press");
            dataContainer[4] = String.valueOf(resultSet.getInt("year"));
            dataContainer[5] = resultSet.getString("author");
            dataContainer[6] = String.valueOf(resultSet.getDouble("price"));
            dataContainer[7] = String.valueOf(resultSet.getInt("total"));
            dataContainer[8] = String.valueOf(resultSet.getInt("stock"));
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Error occurred when searching for a book");
            return false;
        }
        return true;
    }
    //选取所有书目
    public boolean selectAllBooks(String[][] dataContainer) {
        String sql = "SELECT * FROM book";
        System.out.println(sql);
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            int iterator = 0;
            while (resultSet.next()) {
                dataContainer[iterator][0] = resultSet.getString("bno");
                dataContainer[iterator][1] = resultSet.getString("category");
                dataContainer[iterator][2] = resultSet.getString("title");
                dataContainer[iterator][3] = resultSet.getString("press");
                dataContainer[iterator][4] = String.valueOf(
                        resultSet.getInt("year"));
                dataContainer[iterator][5] = resultSet.getString("author");
                dataContainer[iterator][6] = String.valueOf(
                        resultSet.getDouble("price"));
                dataContainer[iterator][7] = String.valueOf(
                        resultSet.getInt("total"));
                dataContainer[iterator][8] = String.valueOf(
                        resultSet.getInt("stock"));
                iterator ++;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Failed when fetching the book data");
            return false;
        }
        return true;
    }
    //选取所有用户
    public boolean selectAllUsers(String[][] dataContainer) {
        String sql = "SELECT * FROM card";
        System.out.println(sql);
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            int iterator = 0;
            while (resultSet.next()) {
                dataContainer[iterator][0] = resultSet.getString("cno");
                dataContainer[iterator][1] = resultSet.getString("name");
                dataContainer[iterator][2] = resultSet.getString("password");
                dataContainer[iterator][3] = resultSet.getString("department");
                if(resultSet.getInt("type") == 1) dataContainer[iterator][4] = "本科生";
                else if(resultSet.getInt("type") == 2) dataContainer[iterator][4] = "研究生";
                else if(resultSet.getInt("type") == 3) dataContainer[iterator][4] = "教师";
                else if(resultSet.getInt("type") == 4) dataContainer[iterator][4] = "其他";
                iterator ++;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Failed when fetching the Reader data");
            return false;
        }
        return true;
    }
    //按照不同的筛选条件查找书目
    public boolean searchBook(String[][] dataContainer, String[] sqlstring){
        String sql =  "SELECT * FROM book WHERE";
        String category = sqlstring[0];
        String title = sqlstring[1];
        String press = sqlstring[2];
        String author = sqlstring[3];
        String begin_year = sqlstring[4];
        String end_year = sqlstring[5];
        int price = Integer.parseInt(sqlstring[6]);

        boolean first = true;
        if(category.length() != 0 || title.length() != 0 || press.length() != 0
                || author.length() != 0 || begin_year.length() > 1 || end_year.length() > 1 || price > 0){
            try {
                if(category.length() != 0){
                    if(first){
                        sql += " category= '" + category + "'";
                        first = false;
                    }
                    else sql += " and category= '" + category + "'";
                }
                if(title.length() != 0){
                    if(first){
                        sql += " title like  '%" + title + "%'";
                        first = false;
                    }
                    else sql += " and title like '%" + title + "%'";
                }
                if(press.length() != 0){
                    if(first){
                        sql += " press= '" + press + "'";
                        first = false;
                    }
                    else sql += " and press= '" + press + "'";
                }
                if(author.length() != 0){
                    if(first){
                        sql += " author= '" + author + "'";
                        first = false;
                    }
                    else sql += " and author= '" + author + "'";
                }
                if(begin_year.length() > 1  && end_year.length() > 1){
                    System.out.println(begin_year.length());
                    System.out.println(end_year.length());
                    if(first){
                        sql += " year >= " + begin_year + "" + " and year <= " + end_year + "";
                        first = false;
                    }
                    else sql += " and year >= " + begin_year + "" + " and year <= " + end_year + "";
                }
                if( price != 0){
                    if(first){
                        switch(price){
                            case 1: sql += " price >= " + 0 + ""  + " and price <= " + 20 + "";
                                break;
                            case 2: sql += " price >= " + 20 + "" + " and price <= " + 50 + "";
                                break;
                            case 3: sql += " price >= " + 50 + "" + " and price <= " + 100 + "";
                                break;
                            case 4: sql += " price >= " + 100 + "";
                                break;
                        }
                        first = false;
                    }
                    else {
                        switch(price){
                            case 1: sql += " and price >= " + 0 + "" + " and price <= " + 20 + "";
                                break;
                            case 2: sql += " and price >= " + 20 + "" + " and price <= " + 50 + "";
                                break;
                            case 3: sql += " and price >= " + 50 + "" + " and price <= " + 100 + "";
                                break;
                            case 4: sql += " and price >= " + 100 + "";
                        }
                    }
                }
                statement = connection.createStatement();
                System.out.println(sql);
                ResultSet resultSet = statement.executeQuery(sql);
                int iterator = 0;
                while (resultSet.next()) {
                    dataContainer[iterator][0] = resultSet.getString("bno");
                    dataContainer[iterator][1] = resultSet.getString("category");
                    dataContainer[iterator][2] = resultSet.getString("title");
                    dataContainer[iterator][3] = resultSet.getString("press");
                    dataContainer[iterator][4] = String.valueOf(
                            resultSet.getInt("year"));
                    dataContainer[iterator][5] = resultSet.getString("author");
                    dataContainer[iterator][6] = String.valueOf(
                            resultSet.getDouble("price"));
                    dataContainer[iterator][7] = String.valueOf(
                            resultSet.getInt("total"));
                    dataContainer[iterator][8] = String.valueOf(
                            resultSet.getInt("stock"));
                    iterator ++;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                System.out.println("Error occurred when searching for a book");
                return false;
            }
            return true;
        }
        else return false;
    }

    //编辑，更新书目信息
    public boolean updateBook(String newDataContainer[]) {
        String sql = "UPDATE Book SET " + "press = '" + trimStringToSQL(newDataContainer[3]) +
                "', author = '" + trimStringToSQL(newDataContainer[5]) +
                "', year = " + trimStringToSQL(newDataContainer[4]) +
                ", price = " + trimStringToSQL(newDataContainer[6]) +
                ", category = '" + trimStringToSQL(newDataContainer[1]) +
                "', title = '" + trimStringToSQL(newDataContainer[2]) +
                "',total = " + trimStringToSQL(newDataContainer[7]) +
                " ,stock = " + trimStringToSQL(newDataContainer[8]) +
                " WHERE bno = '" + trimStringToSQL(newDataContainer[0]) + "'";
        System.out.println(sql);
        try {
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Error occurred when updating a book");
            return false;
        }
        return true;
    }
    //添加用户
    public boolean addReader(String newDataContainer[]) {
        String sql = "INSERT INTO card VALUES (" + newDataContainer[0] + ",'" + newDataContainer[1] + "'," +
                newDataContainer[3] + ",'" + newDataContainer[2] + "'," + newDataContainer[4] + ")";
        System.out.println(sql);
        try {
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Error occurred when inserting a reader");
            return false;
        }
        return true;
    }
    //删除用户
    public boolean dropReader(String ID) {
        String sql = "DELETE FROM card WHERE cno = '" + ID + "'";
        System.out.println(sql);
        try {
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Error occurred when dropping a user");
            return false;
        }
        return true;
    }
    //查看书目库存量
    public int checkForBookAmount(String bno) {
        String sql = "SELECT stock FROM Book WHERE bno = " +
                trimStringToSQL(bno) + "";
        int curr_stock = 0;
        System.out.println(sql);
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (!resultSet.next()) {
                System.out.println("Book not found");
                return -1;
            }
            curr_stock = Integer.parseInt(resultSet.getString("stock"));
            if (curr_stock < 1) {
                System.out.println("Book no stock");
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Error occurred when searching for a book");
            return -2;
        }
        return curr_stock;
    }

    //自助借书
    public boolean SelfborrowBook(String bno) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //Borrow_Date
        long br_time = new java.util.Date().getTime();
        String borrow_Date = format.format(br_time);

        //calculate Return_deadline
        Calendar calendar =new GregorianCalendar();
        java.util.Date borrow_date_util = null;
        borrow_date_util = format.parse(borrow_Date);

        java.sql.Date borrow_date_sql = new java.sql.Date(borrow_date_util.getTime());

        calendar.setTime(borrow_date_sql);
        calendar.add(calendar.DATE, 60);

        //Return_Deadline
        java.util.Date return_ddl = (java.util.Date)calendar.getTime();
        String return_deadline = format.format(return_ddl);

        String sql = "INSERT INTO borrow (cno, bno, borrow_date, return_deadline, admin_id)" +
                " VALUES ("+ currentUserId +","+ bno +", '"+ borrow_Date +"', '"+ return_deadline +"', " + 0 + ")";
        System.out.println(sql);
        try {
            statement = connection.createStatement();
            statement.execute(sql);
            sql = "UPDATE book SET stock = stock - 1" + " WHERE bno= " + bno + "";
            System.out.println(sql);
            statement.execute(sql);
            System.out.println("stock has been updated");
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Book to borrow not found");
            return false;
        }
        return true;
    }
    //管理员借书
    public boolean AdminborrowBook(String bno, String cno) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //Borrow_Date
        long br_time = new java.util.Date().getTime();
        String borrow_Date = format.format(br_time);

        //calculate Return_deadline
        Calendar calendar =new GregorianCalendar();
        java.util.Date borrow_date_util = null;
        borrow_date_util = format.parse(borrow_Date);

        java.sql.Date borrow_date_sql = new java.sql.Date(borrow_date_util.getTime());

        calendar.setTime(borrow_date_sql);
        calendar.add(calendar.DATE, 60);

        //Return_Deadline
        java.util.Date return_ddl = (java.util.Date)calendar.getTime();
        String return_deadline = format.format(return_ddl);

        String sql = "INSERT INTO borrow (cno, bno, borrow_date, return_deadline, admin_id)" +
                " VALUES ("+ cno +","+ bno +", '"+ borrow_Date +"', '"+ return_deadline +"', " + currentAdminId + ")";
        System.out.println(sql);
        try {
            statement = connection.createStatement();
            statement.execute(sql);
            sql = "UPDATE book SET stock = stock - 1" + " WHERE bno= " + bno + "";
            System.out.println(sql);
            statement.execute(sql);
            System.out.println("stock has been updated");
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Book to borrow not found");
            return false;
        }
        return true;
    }
    //还书
    public boolean returnBook(String bno) {
        String sql = "SELECT * FROM Book WHERE bno = '" + bno + "'";
        System.out.println(sql);
        int return_num = 0;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (!resultSet.next()) {
                System.out.println("Book to return not found");
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Book to return not found");
            return false;
        }
        sql = "SELECT * FROM borrow WHERE bno = " + bno + " AND cno = "
                + String.valueOf(currentUserId);
        System.out.println(sql);
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (!resultSet.next()) {
                System.out.println("Book has been returned.");
                return false;
            }
            else{
                resultSet.beforeFirst();
                while (resultSet.next()){
                    //get borrow record
                    String borrow_cno = String.valueOf(
                            resultSet.getInt("cno"));;
                    String borrow_bno = resultSet.getString("bno");;
                    String borrow_date = String.valueOf(
                            resultSet.getTimestamp("borrow_date"));
                    String borrow_ddl = String.valueOf(
                            resultSet.getTimestamp("return_deadline"));
                    String admin = resultSet.getString("admin_id");;
                    //time format transformation
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    java.util.Date borrow_deadline = null;
                    borrow_deadline = format.parse(borrow_ddl);
                    java.sql.Date Borrow_deadline = new java.sql.Date(borrow_deadline.getTime());
                    long return_time = new java.util.Date().getTime();
                    String Return_time = format.format(return_time);

                    java.util.Date return_time_util = null;
                    return_time_util = format.parse(Return_time);

                    java.sql.Date return_date_sql = new java.sql.Date(return_time_util.getTime());
                    int overtime = 0;
                    if(Borrow_deadline.before(return_date_sql)) overtime = 1;
                    //insert return record
                    sql = "INSERT INTO return_record VALUES ("+ String.valueOf(currentUserId) +","+ bno +", '"+ borrow_date +"', '"+ Return_time +"', " + 0 + ", "+ overtime +")";
                    statement = connection.createStatement();
                    statement.execute(sql);
                    return_num ++;
                    //阻塞线程2秒，防止同时还书
                    Thread.sleep(2000);
                }
                //delete borrow record
                sql = "DELETE FROM borrow WHERE bno = " + bno + " AND cno = "
                        + String.valueOf(currentUserId);
                statement = connection.createStatement();
                statement.execute(sql);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Book to return not found");
            return false;
        }
        sql = "UPDATE book SET stock = stock + "+ return_num+ "" + " WHERE bno= " + bno + "";
        System.out.println(sql);
        try {
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Book to return not found");
            return false;
        }
        System.out.println(sql);
        return true;
    }
    //管理员还书
    public boolean AdminreturnBook(String bno, String cno) {
        String sql = "SELECT * FROM Book WHERE bno = " + bno + "";
        System.out.println(sql);
        int return_num = 0;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (!resultSet.next()) {
                System.out.println("Book to return not found");
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Book to return not found");
            return false;
        }
        sql = "SELECT * FROM borrow WHERE bno = " + bno + " AND cno = "
                + String.valueOf(cno);
        System.out.println(sql);
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (!resultSet.next()) {
                System.out.println("Book has been returned.");
                return false;
            }
            else{
                resultSet.beforeFirst();
                while (resultSet.next()){
                    //get borrow record
                    String borrow_cno = String.valueOf(
                            resultSet.getInt("cno"));;
                    String borrow_bno = resultSet.getString("bno");;
                    String borrow_date = String.valueOf(
                            resultSet.getTimestamp("borrow_date"));
                    String borrow_ddl = String.valueOf(
                            resultSet.getTimestamp("return_deadline"));
                    String admin = resultSet.getString("admin_id");;
                    //time format transformation
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    java.util.Date borrow_deadline = null;
                    borrow_deadline = format.parse(borrow_ddl);
                    java.sql.Date Borrow_deadline = new java.sql.Date(borrow_deadline.getTime());
                    long return_time = new java.util.Date().getTime();
                    String Return_time = format.format(return_time);

                    java.util.Date return_time_util = null;
                    return_time_util = format.parse(Return_time);

                    java.sql.Date return_date_sql = new java.sql.Date(return_time_util.getTime());
                    int overtime = 0;
                    if(Borrow_deadline.before(return_date_sql)) overtime = 1;
                    //insert return record
                    sql = "INSERT INTO return_record VALUES ("+ String.valueOf(cno) +","+ bno +", '"+ borrow_date +"', '"+ Return_time +"', " + currentAdminId + ", "+ overtime +")";
                    statement = connection.createStatement();
                    statement.execute(sql);
                    return_num ++;
                    //阻塞线程2秒，防止同时还书
                    Thread.sleep(2000);
                }
                //delete borrow record
                sql = "DELETE FROM borrow WHERE bno = " + bno + " AND cno = "
                        + String.valueOf(cno);
                statement = connection.createStatement();
                statement.execute(sql);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Book to return not found");
            return false;
        }
        sql = "UPDATE book SET stock = stock + "+ return_num+ "" + " WHERE bno= " + bno + "";
        System.out.println(sql);
        try {
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Book to return not found");
            return false;
        }
        System.out.println(sql);
        return true;
    }
    //取得借书记录
    public boolean fetchReaderBorrowRecord(String[][] dataContainer) {
        String sql = "SELECT * FROM borrow natural join book WHERE cno = " + String.valueOf(currentUserId) +
                " ORDER BY borrow_date DESC";
        System.out.println(sql);
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            int iterator = 0;
            while (resultSet.next()) {
                dataContainer[iterator][0] = String.valueOf(
                        resultSet.getInt("cno"));
                dataContainer[iterator][1] = resultSet.getString("bno");
                dataContainer[iterator][2] = resultSet.getString("title");
                dataContainer[iterator][3] = String.valueOf(
                        resultSet.getTimestamp("borrow_date"));
                dataContainer[iterator][4] = String.valueOf(
                        resultSet.getTimestamp("return_deadline"));
                String admin = resultSet.getString("admin_id");
                int admin_int = Integer.parseInt(admin);
                if(admin_int == 0) dataContainer[iterator][5] = "自主借书";
                else dataContainer[iterator][5] = admin;
                iterator ++;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Error when fetching user's borrow record");
            return false;
        }
        return true;
    }

    public boolean fetchReaderBorrowRecord_Admin(String[][] dataContainer, String cno) {
        String sql = "SELECT * FROM borrow natural join book WHERE cno = " + String.valueOf(cno) +
                " ORDER BY borrow_date DESC";
        System.out.println(sql);
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            int iterator = 0;
            while (resultSet.next()) {
                dataContainer[iterator][0] = String.valueOf(
                        resultSet.getInt("cno"));
                dataContainer[iterator][1] = resultSet.getString("bno");
                dataContainer[iterator][2] = resultSet.getString("title");
                dataContainer[iterator][3] = String.valueOf(
                        resultSet.getTimestamp("borrow_date"));
                dataContainer[iterator][4] = String.valueOf(
                        resultSet.getTimestamp("return_deadline"));
                String admin = resultSet.getString("admin_id");
                int admin_int = Integer.parseInt(admin);
                if(admin_int == 0) dataContainer[iterator][5] = "自主借书";
                else dataContainer[iterator][5] = admin;
                iterator ++;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Error when fetching user's borrow record");
            return false;
        }
        return true;
    }

    public boolean fetchReaderReturnRecord(String[][] dataContainer) {
        String sql = "SELECT * FROM return_record natural join book WHERE cno = " + String.valueOf(currentUserId) +
                " ORDER BY return_date DESC";
        System.out.println(sql);
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            int iterator = 0;
            while (resultSet.next()) {
                dataContainer[iterator][0] = String.valueOf(
                        resultSet.getInt("cno"));
                dataContainer[iterator][1] = resultSet.getString("bno");
                dataContainer[iterator][2] = resultSet.getString("title");
                dataContainer[iterator][3] = String.valueOf(
                        resultSet.getTimestamp("borrow_date"));
                dataContainer[iterator][4] = String.valueOf(
                        resultSet.getTimestamp("return_date"));
                //overtime or not
                int overtime = Integer.parseInt(resultSet.getString("overtime"));
                if(overtime == 0) dataContainer[iterator][5] = "否";
                else dataContainer[iterator][5] = "是";
                //admin_id
                String admin = resultSet.getString("admin_id");
                int admin_int = Integer.parseInt(admin);
                if(admin_int == 0) dataContainer[iterator][6] = "自主借书";
                else dataContainer[iterator][6] = admin;
                iterator ++;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Error when fetching user's return record");
            return false;
        }
        return true;
    }

    public boolean selectAllBorrows(String[][] dataContainer) {
        String sql = "SELECT * FROM borrow natural join book ORDER BY cno";
        System.out.println(sql);
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            int iterator = 0;
            while (resultSet.next()) {
                dataContainer[iterator][0] = String.valueOf(
                        resultSet.getInt("cno"));
                dataContainer[iterator][1] = resultSet.getString("bno");
                dataContainer[iterator][2] = resultSet.getString("title");
                dataContainer[iterator][3] = String.valueOf(
                        resultSet.getTimestamp("borrow_date"));
                dataContainer[iterator][4] = String.valueOf(
                        resultSet.getTimestamp("return_deadline"));
                String admin = resultSet.getString("admin_id");
                int admin_int = Integer.parseInt(admin);
                if(admin_int == 0) dataContainer[iterator][5] = "自主借书";
                else dataContainer[iterator][5] = admin;
                iterator ++;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Error when fetching user's borrow record");
            return false;
        }
        return true;
    }

    public boolean selectAllReturns(String[][] dataContainer) {
        String sql = "SELECT * FROM return_record natural join book ORDER BY cno";
        System.out.println(sql);
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            int iterator = 0;
            while (resultSet.next()) {
                dataContainer[iterator][0] = String.valueOf(
                        resultSet.getInt("cno"));
                dataContainer[iterator][1] = resultSet.getString("bno");
                dataContainer[iterator][2] = resultSet.getString("title");
                dataContainer[iterator][3] = String.valueOf(
                        resultSet.getTimestamp("borrow_date"));
                dataContainer[iterator][4] = String.valueOf(
                        resultSet.getTimestamp("return_date"));
                //overtime or not
                int overtime = Integer.parseInt(resultSet.getString("overtime"));
                if (overtime == 0) dataContainer[iterator][5] = "否";
                else dataContainer[iterator][5] = "是";
                //admin_id
                String admin = resultSet.getString("admin_id");
                int admin_int = Integer.parseInt(admin);
                if (admin_int == 0) dataContainer[iterator][6] = "自主借书";
                else dataContainer[iterator][6] = admin;
                iterator++;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Error when fetching user's return record");
            return false;
        }
        return true;
    }
}
