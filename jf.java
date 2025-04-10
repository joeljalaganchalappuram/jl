import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.stage.Stage;
import javafx.geometry.Insets;

public class ColorChangerApp extends Application {

    private boolean ctrlPressed = false;
    private boolean altPressed = false;

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 400, 300);

        scene.setOnKeyPressed(event -> handleKeyPressed(event, root));
        scene.setOnKeyReleased(this::handleKeyReleased);

        primaryStage.setTitle("Color Changer with Key Combination");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Request focus so key events are captured
        root.requestFocus();
    }

    private void handleKeyPressed(KeyEvent event, StackPane root) {
        if (event.getCode() == KeyCode.CONTROL) {
            ctrlPressed = true;
        }
        if (event.getCode() == KeyCode.ALT) {
            altPressed = true;
        }

        if (ctrlPressed && altPressed && event.getCode() == KeyCode.O) {
            changeBackgroundColor(root, Color.ORANGE);
        }
    }

    private void handleKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.CONTROL) {
            ctrlPressed = false;
        }
        if (event.getCode() == KeyCode.ALT) {
            altPressed = false;
        }
    }

    private void changeBackgroundColor(StackPane pane, Color color) {
        pane.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public static void main(String[] args) {
        launch(args);
    }
}

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCartApp extends Application {

    private Map<String, Integer> cart = new HashMap<>();
    private TextArea cartDisplay = new TextArea();

    @Override
    public void start(Stage primaryStage) {
        Button addAppleBtn = new Button("Add Apple");
        Button addBananaBtn = new Button("Add Banana");
        Button addOrangeBtn = new Button("Add Orange");

        // Event Handlers
        addAppleBtn.setOnAction(e -> addItem("Apple"));
        addBananaBtn.setOnAction(e -> addItem("Banana"));
        addOrangeBtn.setOnAction(e -> addItem("Orange"));

        cartDisplay.setEditable(false);
        cartDisplay.setPrefHeight(200);

        VBox root = new VBox(10, addAppleBtn, addBananaBtn, addOrangeBtn, cartDisplay);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(root, 300, 300);
        primaryStage.setTitle("Shopping Cart Simulator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addItem(String item) {
        cart.put(item, cart.getOrDefault(item, 0) + 1);
        updateCartDisplay();
    }

    private void updateCartDisplay() {
        StringBuilder sb = new StringBuilder("🛒 Cart Items:\n");
        cart.forEach((item, quantity) -> sb.append(item).append(": ").append(quantity).append("\n"));
        cartDisplay.setText(sb.toString());
    }

    public static void main(String[] args) {
        launch(args);
    }
}


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.stage.Stage;

public class ColorPreferenceForm extends Application {

    private Label selectedColorLabel;

    @Override
    public void start(Stage primaryStage) {
        // Toggle buttons for color selection
        ToggleButton redButton = new ToggleButton("Red");
        ToggleButton greenButton = new ToggleButton("Green");
        ToggleButton blueButton = new ToggleButton("Blue");

        // Group toggle buttons so only one can be selected
        ToggleGroup colorGroup = new ToggleGroup();
        redButton.setToggleGroup(colorGroup);
        greenButton.setToggleGroup(colorGroup);
        blueButton.setToggleGroup(colorGroup);

        // Label to show the selected color
        selectedColorLabel = new Label("No color selected");
        selectedColorLabel.setFont(Font.font(16));

        // Event Listener: update label and optionally background
        colorGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle != null) {
                ToggleButton selected = (ToggleButton) newToggle;
                String color = selected.getText();
                selectedColorLabel.setText("Selected Color: " + color);
                updateBackgroundColor((VBox) selected.getParent(), color);
            } else {
                selectedColorLabel.setText("No color selected");
            }
        });

        VBox root = new VBox(10, redButton, greenButton, blueButton, selectedColorLabel);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-alignment: center;");

        Scene scene = new Scene(root, 300, 250);
        primaryStage.setTitle("Color Preference Form");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateBackgroundColor(VBox box, String colorName) {
        Color color;
        switch (colorName.toLowerCase()) {
            case "red": color = Color.RED; break;
            case "green": color = Color.GREEN; break;
            case "blue": color = Color.BLUE; break;
            default: color = Color.LIGHTGRAY;
        }
        box.setBackground(new Background(new BackgroundFill(color.deriveColor(0, 1, 1, 0.3),
                CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public static void main(String[] args) {
        launch(args);
    }
}

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TwoButtonClickApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a label
        Label statusLabel = new Label("Click a button!");

        // Create two buttons
        Button button1 = new Button("Button 1");
        Button button2 = new Button("Button 2");

        // Set action listeners for buttons
        button1.setOnAction(e -> statusLabel.setText("Button-1 clicked."));
        button2.setOnAction(e -> statusLabel.setText("Button-2 clicked."));

        // Layout
        VBox root = new VBox(10, button1, button2, statusLabel);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Scene and stage setup
        Scene scene = new Scene(root, 300, 200);
        primaryStage.setTitle("Two Button Click App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
