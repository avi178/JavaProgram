import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MatrixMultiplicationFX extends Application {

    private static final int SIZE = 3;

    int[][] matrixA = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
    };

    int[][] matrixB = {
            {9, 8, 7},
            {6, 5, 4},
            {3, 2, 1}
    };

    int[][] resultMatrix = new int[SIZE][SIZE];
    Label[][] resultLabels = new Label[SIZE][SIZE];
    Label[][] labelA = new Label[SIZE][SIZE];
    Label[][] labelB = new Label[SIZE][SIZE];
    TextFlow statusFlow = new TextFlow();
    ScrollPane statusScroll;
    Timeline timeline;

    private int row = 0, col = 0, k = 0;
    private int prevRow = -1, prevCol = -1, prevK = -1;
    private int lastRowInStatus = -1;
    private int lastColInStatus = -1;
    private double delay = 2.0;

    TextFlow loopInfoFlow = new TextFlow();

    @Override
    public void start(Stage primaryStage) {
        VBox rootVBox = new VBox();

        GridPane matrixPane = new GridPane();
        matrixPane.setHgap(30);
        matrixPane.setVgap(10);
        matrixPane.setPadding(new Insets(10));

        GridPane gridA = createMatrixGrid(matrixA, "Matrix A", labelA, true);
        GridPane gridB = createMatrixGrid(matrixB, "Matrix B", labelB, false);
        GridPane gridResult = createResultMatrixGrid("Result");

        VBox matricesVBox = new VBox(20, gridA, gridB);

        updateLoopInfo(loopInfoFlow, 0, 0, 0, -1, -1, -1);
        VBox loopValues = new VBox(loopInfoFlow);
        loopValues.setPadding(new Insets(0, 0, 0, 20));

        HBox matrixAndLoopBox = new HBox(matricesVBox, loopValues);
        matrixPane.add(matrixAndLoopBox, 0, 0);
        matrixPane.add(gridResult, 1, 0);

        Button startButton = new Button("Start");
        Button pauseButton = new Button("Pause");
        Button resetButton = new Button("Reset");
        Button incDelay = new Button("+");
        Button decDelay = new Button("-");

        Label delayLabel = new Label(" Delay: " + delay + "s");

        incDelay.setOnAction(e -> {
            delay += 0.5;
            delayLabel.setText(" Delay: " + delay + "s");
            updateTimeline();
        });
        decDelay.setOnAction(e -> {
            if (delay > 0.5) delay -= 0.5;
            delayLabel.setText(" Delay: " + delay + "s");
            updateTimeline();
        });

        HBox controls = new HBox(10, startButton, pauseButton, resetButton, incDelay, decDelay, delayLabel);
        controls.setPadding(new Insets(10));

        statusFlow.setPrefWidth(1600);
        statusScroll = new ScrollPane(statusFlow);
        statusScroll.setFitToHeight(true);
        statusScroll.setFitToWidth(false);
        statusScroll.setPrefViewportHeight(400);
        statusScroll.setPrefViewportWidth(1300);
        statusScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        statusScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        VBox displaySection = new VBox(10, controls, statusScroll);
        displaySection.setPadding(new Insets(10));

        rootVBox.getChildren().addAll(matrixPane, displaySection);

        ScrollPane mainScroll = new ScrollPane(rootVBox);
        mainScroll.setFitToWidth(true);

        Scene scene = new Scene(mainScroll, 1400, 700);
        primaryStage.setTitle("Matrix Multiplication Animation");
        primaryStage.setScene(scene);
        primaryStage.show();

        timeline = new Timeline(new KeyFrame(Duration.seconds(delay), e -> animateStep()));
        timeline.setCycleCount(Timeline.INDEFINITE);

        startButton.setOnAction(e -> timeline.play());
        pauseButton.setOnAction(e -> timeline.pause());
        resetButton.setOnAction(e -> reset());
    }

    private void updateLoopInfo(TextFlow flow, int row, int col, int k, int prevRow, int prevCol, int prevK) {
        flow.getChildren().clear();

        Text tRow = new Text("row=" + row + "  ");
        Text tCol = new Text("col=" + col + "  ");
        Text tK = new Text("k=" + k);

        tRow.setFont(Font.font("Consolas", FontWeight.BOLD, 14));
        tCol.setFont(Font.font("Consolas", FontWeight.BOLD, 14));
        tK.setFont(Font.font("Consolas", FontWeight.BOLD, 14));

        tRow.setFill(row != prevRow ? Color.GREEN : Color.BLACK);
        tCol.setFill(col != prevCol ? Color.GREEN : Color.BLACK);
        tK.setFill(k != prevK ? Color.GREEN : Color.BLACK);

        flow.getChildren().addAll(tRow, tCol, tK);
    }

    private void updateTimeline() {
        timeline.stop();
        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(delay), e -> animateStep()));
    }

    private void animateStep() {
        if (row >= SIZE) {
            Text complete = new Text("\nMultiplication complete.\n");
            complete.setFill(Color.DARKGREEN);
            complete.setFont(Font.font("Consolas", FontWeight.BOLD, 16));
            statusFlow.getChildren().add(complete);
            timeline.stop();
            return;
        }

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                labelA[i][j].setStyle("-fx-background-color: transparent;");
                labelB[i][j].setStyle("-fx-background-color: transparent;");
            }
        }

        for (int j = 0; j < SIZE; j++) {
            labelA[row][j].setStyle("-fx-background-color: lightyellow;");
        }

        labelA[row][k].setStyle("-fx-background-color: orange;");
        labelB[k][col].setStyle("-fx-background-color: orange;");

        int currentC = resultMatrix[row][col];
        int a = matrixA[row][k];
        int b = matrixB[k][col];
        int product = a * b;
        resultMatrix[row][col] += product;
        resultLabels[row][col].setText(String.valueOf(resultMatrix[row][col]));
        resultLabels[row][col].setTextFill(Color.BLUE);

        if (row != lastRowInStatus || col != lastColInStatus) {
            statusFlow.getChildren().add(new Text(System.lineSeparator()));
            lastRowInStatus = row;
            lastColInStatus = col;
        }

        Text part1 = new Text(String.format("C[%d][%d] = %d + %d * %d --> ", row, col, currentC, a, b));
        part1.setFont(Font.font("Consolas", FontWeight.BOLD, 14));
        part1.setFill(Color.PURPLE);

        Text indexA1 = new Text("A[" + row);
        indexA1.setFont(Font.font("Consolas", FontWeight.BOLD, 14));
        indexA1.setFill(Color.DARKBLUE);

        Text indexAK = new Text("][" + k + "]");
        indexAK.setFont(Font.font("Consolas", FontWeight.BOLD, 14));
        indexAK.setFill(Color.ORANGE);

        Text indexB1 = new Text(" * B[" + k);
        indexB1.setFont(Font.font("Consolas", FontWeight.BOLD, 14));
        indexB1.setFill(Color.DARKBLUE);

        Text indexBK = new Text("][" + col + "]");
        indexBK.setFont(Font.font("Consolas", FontWeight.BOLD, 14));
        indexBK.setFill(Color.ORANGE);

        Text equals = new Text(" = " + resultMatrix[row][col] + System.lineSeparator());
        equals.setFont(Font.font("Consolas", FontWeight.BOLD, 14));
        equals.setFill(Color.GREEN);

        statusFlow.getChildren().addAll(part1, indexA1, indexAK, indexB1, indexBK, equals);

        updateLoopInfo(loopInfoFlow, row, col, k, prevRow, prevCol, prevK);
        prevRow = row;
        prevCol = col;
        prevK = k;

        statusScroll.setVvalue(1.0);

        k++;
        if (k == SIZE) {
            k = 0;
            col++;
            if (col == SIZE) {
                col = 0;
                row++;
            }
        }
    }

    private void reset() {
        row = 0;
        col = 0;
        k = 0;
        prevRow = -1;
        prevCol = -1;
        prevK = -1;
        lastRowInStatus = -1;
        lastColInStatus = -1;
        resultMatrix = new int[SIZE][SIZE];
        statusFlow.getChildren().clear();
        updateLoopInfo(loopInfoFlow, 0, 0, 0, -1, -1, -1);
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                resultLabels[i][j].setText("0");
                resultLabels[i][j].setTextFill(Color.BLACK);
                labelA[i][j].setStyle("-fx-background-color: transparent;");
                labelB[i][j].setStyle("-fx-background-color: transparent;");
            }
        }
        timeline.stop();
    }

    private GridPane createMatrixGrid(int[][] matrix, String title, Label[][] labelGrid, boolean isA) {
        GridPane grid = new GridPane();
        Label titleLabel = new Label(title);
        titleLabel.setFont(new Font("Arial", 16));
        grid.add(titleLabel, 0, 0, SIZE * 2, 1);

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                VBox cell = new VBox(2);
                Label label = new Label(String.valueOf(matrix[i][j]));
                label.setFont(Font.font("Consolas", 18));
                label.setMinWidth(40);
                label.setPadding(new Insets(5));
                labelGrid[i][j] = label;
                Label index = new Label((isA ? "A" : "B") + "[" + i + "][" + j + "]");
                index.setFont(Font.font("Arial", 10));
                cell.getChildren().addAll(label, index);
                grid.add(cell, j, i + 1);
            }
        }
        return grid;
    }

    private GridPane createResultMatrixGrid(String title) {
        GridPane grid = new GridPane();
        Label titleLabel = new Label(title);
        titleLabel.setFont(new Font("Arial", 16));
        grid.add(titleLabel, 0, 0, SIZE * 2, 1);

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                VBox cell = new VBox(2);
                Label label = new Label("0");
                label.setFont(Font.font("Consolas", 18));
                label.setMinWidth(40);
                label.setPadding(new Insets(5));
                resultLabels[i][j] = label;
                Label index = new Label("C[" + i + "][" + j + "]");
                index.setFont(Font.font("Arial", 10));
                cell.getChildren().addAll(label, index);
                grid.add(cell, j, i + 1);
            }
        }
        return grid;
    }

    public static void main(String[] args) {
        launch(args);
    }
}