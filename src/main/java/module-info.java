module com.example.movieinfo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    opens com.example.movieinfo to javafx.fxml;
    exports com.example.movieinfo;
}