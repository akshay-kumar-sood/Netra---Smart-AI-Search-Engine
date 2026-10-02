package com.netra;

import com.netra.search.SearchRepository;
import com.netra.search.SearchResult;
import com.netra.search.SnippetGenerator;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
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

import java.awt.Desktop;
import java.net.URI;
import java.util.List;

public class NetraApp extends Application {

    @Override
    public void start(Stage stage) {
        showHomePage(stage);
    }

    // =========================
    // HOME PAGE
    // =========================

    private void showHomePage(Stage stage) {

        ImageView background = new ImageView(new Image(
                getClass().getResourceAsStream(
                        "/com/netra/images/earth_network_bg.jpg"
                )
        ));

        background.setPreserveRatio(false);
        background.fitWidthProperty().bind(stage.widthProperty());
        background.fitHeightProperty().bind(stage.heightProperty());

        Label name = new Label("NETRA");
        name.setTextFill(Color.WHITE);
        name.setFont(Font.font(
                "Arial",
                FontWeight.BOLD,
                64
        ));

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

        HBox searchBox = new HBox(
                10,
                search,
                go
        );

        searchBox.setAlignment(Pos.CENTER);

        Button java = topicButton("Java");
        Button spring = topicButton("Spring Boot");
        Button ai = topicButton("AI");
        Button ml = topicButton("Machine Learning");
        Button cloud = topicButton("Cloud");

        HBox topics = new HBox(
                5,
                java,
                spring,
                ai,
                ml,
                cloud
        );

        topics.setAlignment(Pos.CENTER);

        java.setOnAction(e -> search.setText("Java"));
        spring.setOnAction(e -> search.setText("Spring Boot"));
        ai.setOnAction(e -> search.setText("AI"));
        ml.setOnAction(e -> search.setText("Machine Learning"));
        cloud.setOnAction(e -> search.setText("Cloud"));

        // New search always starts from page 1

        go.setOnAction(e ->
                openSearchResults(
                        stage,
                        search.getText(),
                        1
                )
        );

        search.setOnAction(e ->
                openSearchResults(
                        stage,
                        search.getText(),
                        1
                )
        );

        VBox content = new VBox(
                14,
                name,
                slogan,
                searchBox,
                topics
        );

        content.setAlignment(Pos.CENTER);
        content.setTranslateY(-160);

        StackPane root = new StackPane(
                background,
                content
        );

        root.setStyle(
                "-fx-background-color: #020806;"
        );

        Scene scene = new Scene(
                root,
                1200,
                750
        );

        stage.setTitle("NETRA - Search Engine");
        stage.setScene(scene);
        stage.show();
    }

    // =========================
    // SEARCH RESULTS PAGE
    // =========================

    private void openSearchResults(
            Stage stage,
            String query,
            int page
    ) {

        String searchQuery = query.trim();

        if (searchQuery.isEmpty()) {
            return;
        }

        SearchRepository repository =
                new SearchRepository();

        List<SearchResult> results =
                repository.search(
                        searchQuery,
                        page
                );

        // Check whether another page exists

        boolean hasNext =
                repository.hasNext(
                        searchQuery,
                        page
                );

        Label heading = new Label(
                "Search results for: " + searchQuery
        );

        heading.setStyle("""
                -fx-text-fill: white;
                -fx-font-size: 22px;
                -fx-font-weight: bold;
                """);

        VBox resultsBox = new VBox(12);
        resultsBox.setMaxWidth(850);

        // =========================
        // DISPLAY RESULTS
        // =========================

        for (SearchResult result : results) {

            Label title = new Label(
                    result.getTitle()
            );

            title.setWrapText(true);

            title.setStyle("""
                    -fx-text-fill: #00C853;
                    -fx-font-size: 19px;
                    -fx-font-weight: bold;
                    """);

            // URL

            Hyperlink url = new Hyperlink(
                    result.getUrl()
            );

            url.setWrapText(true);

            url.setStyle("""
                    -fx-text-fill: #4DA6FF;
                    -fx-font-size: 13px;
                    -fx-border-color: transparent;
                    """);

            url.setOnAction(e -> {

                try {

                    Desktop.getDesktop().browse(
                            new URI(result.getUrl())
                    );

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

            // Snippet

            String snippet =
                    SnippetGenerator.generate(
                            result.getContent(),
                            searchQuery
                    );

            Label description =
                    new Label(snippet);

            description.setWrapText(true);
            description.setMaxHeight(78);

            description.setStyle("""
                    -fx-text-fill: #b8c9c0;
                    -fx-font-size: 14px;
                    """);

            // Result card

            VBox card = new VBox(
                    5,
                    title,
                    url,
                    description
            );

            card.setMaxWidth(850);

            card.setStyle("""
                    -fx-background-color: rgba(0,0,0,0.78);
                    -fx-background-radius: 12;
                    -fx-border-color: rgba(0,255,136,0.25);
                    -fx-border-radius: 12;
                    -fx-padding: 16;
                    """);

            resultsBox.getChildren().add(card);
        }

        // =========================
        // NO RESULTS
        // =========================

        if (results.isEmpty()) {

            Label noResult = new Label(
                    "No results found for \"" +
                            searchQuery + "\""
            );

            noResult.setStyle("""
                    -fx-text-fill: #789486;
                    -fx-font-size: 16px;
                    """);

            resultsBox.getChildren().add(noResult);
        }

        // =========================
        // PAGINATION
        // =========================

        Button previous =
                new Button("← Previous");

        Button next =
                new Button("Next →");

        Label pageLabel =
                new Label("Page " + page);

        pageLabel.setStyle("""
                -fx-text-fill: #8CFFB8;
                -fx-font-size: 14px;
                -fx-font-weight: bold;
                """);

        // Previous is disabled on page 1

        previous.setDisable(page == 1);

        // Next is disabled when there is
        // no next page

        next.setDisable(!hasNext);

        previous.setStyle("""
                -fx-background-color: #101815;
                -fx-text-fill: #8CFFB8;
                -fx-background-radius: 8;
                -fx-padding: 8 14;
                -fx-cursor: hand;
                """);

        next.setStyle("""
                -fx-background-color: #101815;
                -fx-text-fill: #8CFFB8;
                -fx-background-radius: 8;
                -fx-padding: 8 14;
                -fx-cursor: hand;
                """);

        // Previous page

        previous.setOnAction(e ->
                openSearchResults(
                        stage,
                        searchQuery,
                        page - 1
                )
        );

        // Next page

        next.setOnAction(e ->
                openSearchResults(
                        stage,
                        searchQuery,
                        page + 1
                )
        );

        HBox pagination = new HBox(
                15,
                previous,
                pageLabel,
                next
        );

        pagination.setAlignment(Pos.CENTER);

        // =========================
        // BACK BUTTON
        // =========================

        Button back =
                new Button("← Back");

        back.setStyle("""
                -fx-background-color: transparent;
                -fx-text-fill: #8CFFB8;
                -fx-font-size: 14px;
                -fx-cursor: hand;
                """);

        back.setOnAction(e ->
                showHomePage(stage)
        );

        // =========================
        // MAIN CONTENT
        // =========================

        VBox content = new VBox(
                20,
                heading,
                resultsBox,
                pagination,
                back
        );

        content.setAlignment(Pos.TOP_CENTER);
        content.setMaxWidth(900);

        StackPane root =
                new StackPane(content);

        root.setStyle("""
                -fx-background-color: #020806;
                -fx-padding: 50;
                """);

        Scene resultsScene =
                new Scene(
                        root,
                        1200,
                        750
                );

        stage.setTitle(
                "NETRA - Search Results"
        );

        stage.setScene(resultsScene);
    }

    // =========================
    // TOPIC BUTTON
    // =========================

    private Button topicButton(String text) {

        Button button =
                new Button(text);

        button.setStyle("""
                -fx-background-color: transparent;
                -fx-text-fill: #789486;
                -fx-font-size: 13px;
                -fx-cursor: hand;
                -fx-padding: 4 8;
                """);

        button.setOnMouseEntered(e ->
                button.setStyle("""
                        -fx-background-color:
                                rgba(0,255,136,0.10);
                        -fx-text-fill: #00ff88;
                        -fx-background-radius: 16;
                        -fx-font-size: 16px;
                        -fx-cursor: hand;
                        -fx-padding: 8 14;
                        """)
        );

        button.setOnMouseExited(e ->
                button.setStyle("""
                        -fx-background-color: transparent;
                        -fx-text-fill: #789486;
                        -fx-font-size: 16px;
                        -fx-cursor: hand;
                        -fx-padding: 8 14;
                        """)
        );

        return button;
    }

    // =========================
    // MAIN
    // =========================

    public static void main(String[] args) {
        launch(args);
    }
}