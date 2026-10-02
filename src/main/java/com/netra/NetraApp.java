package com.netra;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import com.netra.search.SearchRepository;
import com.netra.search.SearchResult;
import java.util.List;

public class NetraApp extends Application {

    @Override
    public void start(Stage stage) {
        ImageView background = new ImageView(new Image(
                getClass().getResourceAsStream("/com/netra/images/earth_network_bg.jpg")));
        background.setPreserveRatio(false);
        background.fitWidthProperty().bind(stage.widthProperty());
        background.fitHeightProperty().bind(stage.heightProperty());

        Label name = new Label("NETRA");
        name.setTextFill(Color.WHITE);
        name.setFont(Font.font("Arial", FontWeight.BOLD, 64));

        Label slogan = new Label("Navigate the Web Smarter");
        slogan.setTextFill(Color.web("#8CFFB8"));
        slogan.setFont(Font.font("Arial", 18));

        TextField search = new TextField();
        search.setPromptText("Search the web...");
        search.setPrefSize(650, 54);
        search.setStyle("""
                -fx-background-color: rgba(0,0,0,0.78);
                -fx-background-radius: 28;
                -fx-border-color: #00ff88;
                -fx-border-width: 1.5;
                -fx-border-radius: 28;
                -fx-text-fill: white;
                -fx-prompt-text-fill: #789486;
                -fx-font-size: 17px;
                -fx-padding: 0 22px;
                """);

        Button go = new Button("SEARCH");
        go.setPrefSize(115, 54);
        go.setStyle("""
                -fx-background-color: #00ff88;
                -fx-background-radius: 27;
                -fx-text-fill: #00150b;
                -fx-font-weight: bold;
                -fx-cursor: hand;
                """);

        HBox searchBox = new HBox(10, search, go);
        searchBox.setAlignment(Pos.CENTER);

        Button java = topicButton("Java");
        Button spring = topicButton("Spring Boot");
        Button ai = topicButton("AI");
        Button ml = topicButton("Machine Learning");
        Button cloud = topicButton("Cloud");

        HBox topics = new HBox(5, java, spring, ai, ml, cloud);
        topics.setAlignment(Pos.CENTER);

        java.setOnAction(e -> search.setText("Java"));
        spring.setOnAction(e -> search.setText("Spring Boot"));
        ai.setOnAction(e -> search.setText("AI"));
        ml.setOnAction(e -> search.setText("Machine Learning"));
        cloud.setOnAction(e -> search.setText("Cloud"));

        VBox content = new VBox(14, name, slogan, searchBox, topics);
        content.setAlignment(Pos.CENTER);
        content.setTranslateY(-160);

        StackPane root = new StackPane(background, content);
        root.setStyle("-fx-background-color: #020806;");

        go.setOnAction(e -> search(search));
        search.setOnAction(e -> search(search));

        Scene scene = new Scene(root, 1200, 750);
        stage.setTitle("NETRA - Search Engine");
        stage.setScene(scene);
        stage.show();
    }

    private void search(TextField field) {
        String query = field.getText().trim();

        if (query.isEmpty()) {
            return;
        }

        SearchRepository repository = new SearchRepository();
        List<SearchResult> results = repository.search(query);

        System.out.println("Results found: " + results.size());

        for (SearchResult result : results) {
            System.out.println(result.getTitle());
            System.out.println(result.getUrl());
            System.out.println("-------------------");
        }
    }

    private Button topicButton(String text) {
        Button button = new Button(text);

        button.setStyle("""
            -fx-background-color: transparent;
            -fx-text-fill: #789486;
            -fx-font-size: 13px;
            -fx-cursor: hand;
            -fx-padding: 4 8;
            """);

        button.setOnMouseEntered(e -> button.setStyle("""
        -fx-background-color: rgba(0,255,136,0.10);
        -fx-text-fill: #00ff88;
        -fx-background-radius: 16;
        -fx-font-size: 16px;
        -fx-cursor: hand;
        -fx-padding: 8 14;
        """));

        button.setOnMouseExited(e -> button.setStyle("""
        -fx-background-color: transparent;
        -fx-text-fill: #789486;
        -fx-font-size: 16px;
        -fx-cursor: hand;
        -fx-padding: 8 14;
        """));

        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
