import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class FactorialVisualizer extends Application {

    private TextField inputField;
    private Button startBtn, pauseBtn, stepBtn, stopBtn;
    private VBox callStackBox;
    private VBox returnStackBox;
    private Pane arrowPane; // Draw arrows between adjacent boxes
    private Label statusLabel;

    private int n;
    private List<Integer> callStack;

    private Timeline timeline;
    private int animStep = 0;

    private enum Phase { CALL, BASE, RETURN, DONE }

    private Phase phase;

    @Override
    public void start(Stage stage) {
        inputField = new TextField("5");
        inputField.setPrefWidth(60);

        startBtn = new Button("Start");
        pauseBtn = new Button("Pause");
        stepBtn = new Button("Step");
        stopBtn = new Button("Stop");

        pauseBtn.setDisable(true);
        stepBtn.setDisable(true);
        stopBtn.setDisable(true);

        HBox controls = new HBox(10, new Label("Enter n (0-10):"), inputField, startBtn, pauseBtn, stepBtn, stopBtn);
        controls.setAlignment(Pos.CENTER);
        controls.setPadding(new Insets(10));

        callStackBox = new VBox(10);
        callStackBox.setPadding(new Insets(10));
        callStackBox.setStyle("-fx-border-color: black; -fx-padding: 10;");
        Label callLabel = new Label("Call Stack");
        callLabel.setFont(Font.font(16));
        callStackBox.getChildren().add(callLabel);

        returnStackBox = new VBox(10);
        returnStackBox.setPadding(new Insets(10));
        returnStackBox.setStyle("-fx-border-color: black; -fx-padding: 10;");
        Label returnLabel = new Label("Return Stack");
        returnLabel.setFont(Font.font(16));
        returnStackBox.getChildren().add(returnLabel);

        arrowPane = new Pane();
        arrowPane.setPrefWidth(150);

        HBox stacksBox = new HBox(30, callStackBox, arrowPane, returnStackBox);
        stacksBox.setAlignment(Pos.TOP_CENTER);
        stacksBox.setPadding(new Insets(20));

        statusLabel = new Label("Ready. Enter n and press Start.");
        statusLabel.setFont(Font.font(14));
        statusLabel.setPadding(new Insets(10));
        statusLabel.setWrapText(true);

        BorderPane root = new BorderPane();
        root.setTop(controls);
        root.setCenter(stacksBox);
        root.setBottom(statusLabel);
        BorderPane.setAlignment(statusLabel, Pos.CENTER);
        root.setStyle("-fx-font-family: Arial;");

        Scene scene = new Scene(root, 900, 650);
        stage.setScene(scene);
        stage.setTitle("Factorial Calls and Returns Animation");
        stage.show();

        startBtn.setOnAction(e -> startAnimation());
        pauseBtn.setOnAction(e -> pauseAnimation());
        stepBtn.setOnAction(e -> stepAnimation());
        stopBtn.setOnAction(e -> stopAnimation());
        inputField.setOnAction(e -> resetAnimation());

        callStack = new ArrayList<>();
    }

    private void startAnimation() {
        if (!readInput()) return;

        resetAnimationState();

        buildCallStack(n);

        statusLabel.setText("Starting factorial calls animation...");

        timeline = new Timeline(new KeyFrame(Duration.seconds(1.2), e -> animateStep()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        startBtn.setDisable(true);
        pauseBtn.setDisable(false);
        stepBtn.setDisable(true);
        stopBtn.setDisable(false);
        inputField.setDisable(true);

        phase = Phase.CALL;
        animStep = 0;
    }

    private void pauseAnimation() {
        if (timeline != null) {
            timeline.pause();
            statusLabel.setText("Animation paused.");
            pauseBtn.setDisable(true);
            stepBtn.setDisable(false);
            startBtn.setDisable(false);
        }
    }

    private void stepAnimation() {
        if (timeline != null) {
            animateStep();
        }
    }

    private void stopAnimation() {
        if (timeline != null) {
            timeline.stop();
        }
        statusLabel.setText("Animation stopped.");
        startBtn.setDisable(false);
        pauseBtn.setDisable(true);
        stepBtn.setDisable(true);
        stopBtn.setDisable(true);
        inputField.setDisable(false);
    }

    private void resetAnimation() {
        if (timeline != null) timeline.stop();

        startBtn.setDisable(false);
        pauseBtn.setDisable(true);
        stepBtn.setDisable(true);
        stopBtn.setDisable(true);
        inputField.setDisable(false);

        statusLabel.setText("Input changed. Press Start to run.");

        callStackBox.getChildren().removeIf(node -> node instanceof StackPane);
        returnStackBox.getChildren().removeIf(node -> node instanceof StackPane);
        arrowPane.getChildren().clear();

        callStack.clear();
        phase = Phase.DONE;
        animStep = 0;
    }

    private boolean readInput() {
        try {
            int val = Integer.parseInt(inputField.getText().trim());
            if (val < 0 || val > 10) {
                statusLabel.setText("Please enter an integer between 0 and 10.");
                return false;
            }
            n = val;
            return true;
        } catch (NumberFormatException ex) {
            statusLabel.setText("Invalid input. Enter an integer.");
            return false;
        }
    }

    private void buildCallStack(int num) {
        callStack.clear();
        for (int i = num; i >= 0; i--) {
            callStack.add(i);
        }
    }

    private void animateStep() {
        switch (phase) {
            case CALL -> animateCallPhase();
            case BASE -> animateBaseCase();
            case RETURN -> animateReturnPhase();
            case DONE -> {
                timeline.stop();
                statusLabel.setText("Animation complete.");
                startBtn.setDisable(false);
                pauseBtn.setDisable(true);
                stepBtn.setDisable(true);
                stopBtn.setDisable(true);
                inputField.setDisable(false);
            }
        }
    }

    private void animateCallPhase() {
        if (animStep < callStack.size()) {
            int current = callStack.get(animStep);

            String callText;
            if (current > 0) {
                callText = "factorial(" + current + ")\ncalls factorial(" + (current - 1) + ")";
                statusLabel.setText("factorial(" + current + ") calls factorial(" + (current - 1) + ")");
            } else {
                callText = "factorial(0)\n(base case)";
                statusLabel.setText("Reached base case factorial(0). Returning starts next.");
            }

            addCallStackNode(callText);
            drawArrowsBetweenBoxes(callStackBox);

            animStep++;

            if (animStep == callStack.size()) {
                phase = Phase.BASE;
                animStep = 0;
            }
        }
    }

    private void animateBaseCase() {
        statusLabel.setText("Base case factorial(0) = 1. Start returning...");
        addReturnStackNode("factorial(0) = 1");
        phase = Phase.RETURN;
        animStep = 1;
    }

    private void animateReturnPhase() {
        if (animStep <= n) {
            long valMinus1 = factorial(animStep - 1);
            long val = factorial(animStep);

            String retText;
            if (animStep == 0) {
                retText = "factorial(0) = 1";
                statusLabel.setText("Return factorial(0) = 1");
            } else if (animStep == 1) {
                retText = "factorial(1) = 1";
                statusLabel.setText("Return factorial(1) = 1");
            } else {
                retText = animStep + " * factorial(" + (animStep - 1) + ") = " + animStep + " * " + valMinus1 + " = " + val;
                statusLabel.setText(animStep + " * factorial(" + (animStep - 1) + ") = " + animStep + " * " + valMinus1 + " = " + val);
            }

            addReturnStackNode(retText);
            drawArrowsBetweenBoxes(returnStackBox);

            animStep++;
        } else {
            phase = Phase.DONE;
        }
    }

    private void addCallStackNode(String text) {
        Label label = new Label(text);
        label.setFont(Font.font(14));
        label.setAlignment(Pos.CENTER);
        label.setWrapText(true);

        Rectangle rect = new Rectangle(160, 50, Color.LIGHTBLUE);
        rect.setStroke(Color.BLACK);

        StackPane stack = new StackPane(rect, label);
        stack.setAlignment(Pos.CENTER);
        callStackBox.getChildren().add(stack);
    }

    private void addReturnStackNode(String text) {
        Label label = new Label(text);
        label.setFont(Font.font(14));
        label.setAlignment(Pos.CENTER);
        label.setWrapText(true);

        Rectangle rect = new Rectangle(220, 50, Color.LIGHTGREEN);
        rect.setStroke(Color.BLACK);

        StackPane stack = new StackPane(rect, label);
        stack.setAlignment(Pos.CENTER);
        returnStackBox.getChildren().add(stack);
    }

    private void drawArrowsBetweenBoxes(VBox box) {
        // Clear arrows only if this is the currently animated stack to avoid confusion
        arrowPane.getChildren().clear();

        int count = box.getChildren().size();

        // Draw arrows between adjacent nodes, skip the label title at index 0
        for (int i = 1; i < count - 1; i++) {
            StackPane fromNode = (StackPane) box.getChildren().get(i);
            StackPane toNode = (StackPane) box.getChildren().get(i + 1);

            Bounds fromBounds = fromNode.localToScene(fromNode.getBoundsInLocal());
            Bounds toBounds = toNode.localToScene(toNode.getBoundsInLocal());
            Bounds paneBounds = arrowPane.localToScene(arrowPane.getBoundsInLocal());

            double startX = 30; // x fixed for vertical arrow on left side
            double startY = fromBounds.getMinY() + fromBounds.getHeight() / 2 - paneBounds.getMinY();
            double endY = toBounds.getMinY() + toBounds.getHeight() / 2 - paneBounds.getMinY();

            Line line = new Line(startX, startY, startX, endY);
            line.setStrokeWidth(2);
            line.setStroke(Color.RED);

            Polygon arrowHead = new Polygon();
            arrowHead.getPoints().addAll(
                    startX - 6.0, endY - 8.0,
                    startX + 6.0, endY - 8.0,
                    startX, endY
            );
            arrowHead.setFill(Color.RED);

            arrowPane.getChildren().addAll(line, arrowHead);
        }
    }

    // Factorial for small numbers
    private long factorial(int x) {
        if (x <= 1) return 1;
        long res = 1;
        for (int i = 2; i <= x; i++) {
            res *= i;
        }
        return res;
    }

    private void resetAnimationState() {
        if (timeline != null) timeline.stop();

        callStackBox.getChildren().removeIf(node -> node instanceof StackPane);
        returnStackBox.getChildren().removeIf(node -> node instanceof StackPane);
        arrowPane.getChildren().clear();

        callStack.clear();

        phase = Phase.DONE;
        animStep = 0;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
