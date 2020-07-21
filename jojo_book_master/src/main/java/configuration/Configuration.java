package configuration;

public class Configuration {

    public static String iconPATH = "src/main/resources/jojo.jpg";
    public static String bgPATH = "src/main/resources/jojo_bg.jpg";

    public static String DB_URL = "jdbc:mysql://127.0.0.1:3306/jojo_bookmanagement?user=root&password=981221HOPE&serverTimezone=UTC"
            + "&useUnicode=true" +
            "&characterEncoding=true";

    public static int maxItemSize = 100;

    //編譯生成可執行文件: mvn clean dependency:copy-dependencies compile assembly:single
    //執行該文件:(改成你的绝对路径)
    //java -jar C:\Users\86188\Desktop\数据库大作业\jojo_book_master\target\jojo_book_management-1.1-jar-with-dependencies.jar
}
