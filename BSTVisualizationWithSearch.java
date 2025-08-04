import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;

public class BSTVisualizationWithSearch extends Application {

    private static final double NODE_RADIUS = 20;
    private static final double LEVEL_HEIGHT = 80;

    private BSTNode root;

    private Pane treePane;
    private VBox callStackBox;
    private VBox visualStackBox;
    private TextField insertField;
    private TextField searchField;
    private Button insertButton;
    private Button searchButton;

    private Timeline timeline;
    private List<SearchStep> searchSteps;
    private int currentStepIndex;

    // To draw the "highlighted edge" during traversal
    private Line highlightedEdge;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("BST Recursive Search Visualizer with Stack and Traversal");

        BorderPane rootPane = new BorderPane();

        // Top input controls
        HBox inputBox = new HBox(10);
        inputBox.setPadding(new Insets(10));
        inputBox.setAlignment(Pos.CENTER_LEFT);

        insertField = new TextField();
        insertField.setPromptText("Value to Insert");
        insertButton = new Button("Insert");
        insertButton.setOnAction(e -> insertValue());

        searchField = new TextField();
        searchField.setPromptText("Value to Search");
        searchButton = new Button("Search");
        searchButton.setOnAction(e -> startSearch());

        inputBox.getChildren().addAll(new Label("Insert:"), insertField, insertButton,
                new Label("Search:"), searchField, searchButton);

        rootPane.setTop(inputBox);

        // Center pane for tree drawing
        treePane = new Pane();
        treePane.setPrefSize(800, 500);
        treePane.setStyle("-fx-background-color: white; -fx-border-color: black;");
        rootPane.setCenter(treePane);

        // Right pane with call stack + visual stack
        VBox rightPane = new VBox(20);
        rightPane.setPadding(new Insets(10));

        Label callStackLabel = new Label("Recursive Call Stack");
        callStackBox = new VBox(5);
        callStackBox.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: black;");
        callStackBox.setPrefWidth(250);
        callStackBox.setPrefHeight(250);

        Label visualStackLabel = new Label("Visual Stack (Nodes pushed/popped)");
        visualStackBox = new VBox(5);
        visualStackBox.setStyle("-fx-background-color: #f9f9f9; -fx-border-color: black;");
        visualStackBox.setPrefWidth(250);
        visualStackBox.setPrefHeight(220);

        rightPane.getChildren().addAll(callStackLabel, callStackBox, visualStackLabel, visualStackBox);

        rootPane.setRight(rightPane);

        Scene scene = new Scene(rootPane, 1100, 600);
        stage.setScene(scene);
        stage.show();
    }

    private void insertValue() {
        String text = insertField.getText().trim();
        if (text.isEmpty()) return;
        try {
            int val = Integer.parseInt(text);
            root = insertNode(root, val);
            insertField.clear();
            redrawTree();
        } catch (NumberFormatException e) {
            showAlert("Invalid input", "Please enter an integer value.");
        }
    }

    // BST Insert recursive
    private BSTNode insertNode(BSTNode node, int val) {
        if (node == null) return new BSTNode(val);
        if (val < node.val) node.left = insertNode(node.left, val);
        else if (val > node.val) node.right = insertNode(node.right, val);
        return node;
    }

    private void redrawTree() {
        treePane.getChildren().clear();
        if (root == null) return;
        computeNodePositions();
        drawEdges(root);
        drawNodes(root);
        if (highlightedEdge != null) {
            treePane.getChildren().add(highlightedEdge);
        }
    }

    // Position nodes and center horizontally
    private void computeNodePositions() {
        Map<Integer, Integer> levelPos = new HashMap<>();
        computePositionsRecursive(root, 0, levelPos);

        List<BSTNode> allNodes = new ArrayList<>();
        collectNodes(root, allNodes);

        double minX = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        for (BSTNode node : allNodes) {
            if (node.x < minX) minX = node.x;
            if (node.x > maxX) maxX = node.x;
        }
        double treeWidth = maxX - minX;
        double paneWidth = treePane.getWidth() > 0 ? treePane.getWidth() : 800;
        double offsetX = (paneWidth - treeWidth) / 2 - minX;

        for (BSTNode node : allNodes) {
            node.x += offsetX;
        }
    }

    private void computePositionsRecursive(BSTNode node, int depth, Map<Integer, Integer> levelPos) {
        if (node == null) return;
        computePositionsRecursive(node.left, depth + 1, levelPos);
        int pos = levelPos.getOrDefault(depth, 0);
        node.x = 50 + pos * 70 + depth * 20;
        node.y = 50 + depth * LEVEL_HEIGHT;
        levelPos.put(depth, pos + 1);
        computePositionsRecursive(node.right, depth + 1, levelPos);
    }

    private void collectNodes(BSTNode node, List<BSTNode> list) {
        if (node == null) return;
        list.add(node);
        collectNodes(node.left, list);
        collectNodes(node.right, list);
    }

    // Draw edges with black lines
    private void drawEdges(BSTNode node) {
        if (node == null) return;
        if (node.left != null) {
            Line line = new Line(node.x, node.y, node.left.x, node.left.y);
            treePane.getChildren().add(line);
        }
        if (node.right != null) {
            Line line = new Line(node.x, node.y, node.right.x, node.right.y);
            treePane.getChildren().add(line);
        }
        drawEdges(node.left);
        drawEdges(node.right);
    }

    // Draw nodes with fill color and value text
    private void drawNodes(BSTNode node) {
        if (node == null) return;

        Circle circle = new Circle(node.x, node.y, NODE_RADIUS);
        circle.setStroke(Color.BLACK);
        circle.setFill(node.getColor());
        treePane.getChildren().add(circle);

        Text text = new Text(String.valueOf(node.val));
        text.setFont(Font.font(14));
        text.setX(node.x - 7);
        text.setY(node.y + 5);
        treePane.getChildren().add(text);

        node.circle = circle; // Save reference for color updates

        drawNodes(node.left);
        drawNodes(node.right);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.setTitle(title);
        alert.showAndWait();
    }

    // SEARCH animation and visualization -------------------

    private void startSearch() {
        if (root == null) {
            showAlert("Empty Tree", "Please insert values first.");
            return;
        }
        String text = searchField.getText().trim();
        if (text.isEmpty()) return;
        try {
            int target = Integer.parseInt(text);
            resetNodeColors(root);
            callStackBox.getChildren().clear();
            visualStackBox.getChildren().clear();
            searchSteps = new ArrayList<>();
            currentStepIndex = 0;
            highlightedEdge = null;

            // Generate all search steps
            generateSearchSteps(root, target, new LinkedList<>(), new Stack<>());

            if (searchSteps.isEmpty()) {
                showAlert("Search Error", "Search steps not generated.");
                return;
            }

            if (timeline != null) timeline.stop();
            timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> playNextStep()));
            timeline.setCycleCount(searchSteps.size());
            timeline.play();

        } catch (NumberFormatException e) {
            showAlert("Invalid input", "Please enter an integer value.");
        }
    }

    /*
     SearchStep now includes:
      - current node being processed
      - copy of call stack (Strings)
      - current visual stack content (list of Integers)
      - next traversal direction and node (to highlight edge)
      - action: PUSH or POP or NONE on visual stack
    */
    private boolean generateSearchSteps(BSTNode node, int target, Deque<String> callStack, Stack<Integer> visualStack) {
        if (node == null) {
            // null call
            callStack.addLast("search(null, " + target + ")");
            searchSteps.add(new SearchStep(null, new ArrayList<>(callStack), new ArrayList<>(visualStack), null, null, VisualStackAction.NONE));
            callStack.removeLast();
            return false;
        }

        // PUSH node on visual stack & add call stack entry
        callStack.addLast("search(" + node.val + ", " + target + ")");
        visualStack.push(node.val);
        searchSteps.add(new SearchStep(node, new ArrayList<>(callStack), new ArrayList<>(visualStack), null, null, VisualStackAction.PUSH));

        if (node.val == target) {
            // Found target
            searchSteps.add(new SearchStep(node, new ArrayList<>(callStack), new ArrayList<>(visualStack), null, null, VisualStackAction.NONE));

            // POP from visual stack before returning
            visualStack.pop();
            searchSteps.add(new SearchStep(node, new ArrayList<>(callStack), new ArrayList<>(visualStack), null, null, VisualStackAction.POP));
            callStack.removeLast();
            return true;
        } else if (target < node.val) {
            // next traversal left
            searchSteps.add(new SearchStep(node, new ArrayList<>(callStack), new ArrayList<>(visualStack), "left", node.left, VisualStackAction.NONE));
            boolean found = generateSearchSteps(node.left, target, callStack, visualStack);
            // POP after returning from recursive call
            visualStack.pop();
            searchSteps.add(new SearchStep(node, new ArrayList<>(callStack), new ArrayList<>(visualStack), null, null, VisualStackAction.POP));
            callStack.removeLast();
            return found;
        } else {
            // next traversal right
            searchSteps.add(new SearchStep(node, new ArrayList<>(callStack), new ArrayList<>(visualStack), "right", node.right, VisualStackAction.NONE));
            boolean found = generateSearchSteps(node.right, target, callStack, visualStack);
            // POP after returning from recursive call
            visualStack.pop();
            searchSteps.add(new SearchStep(node, new ArrayList<>(callStack), new ArrayList<>(visualStack), null, null, VisualStackAction.POP));
            callStack.removeLast();
            return found;
        }
    }

    private void playNextStep() {
        if (currentStepIndex >= searchSteps.size()) {
            timeline.stop();
            highlightedEdge = null;
            redrawTree();
            return;
        }
        SearchStep step = searchSteps.get(currentStepIndex);

        resetNodeColors(root);
        highlightedEdge = null;

        // Highlight current node yellow
        if (step.currentNode != null) {
            step.currentNode.setColor(Color.YELLOW);
        }

        // Highlight the next traversal edge blue
        if (step.nextNode != null && step.currentNode != null) {
            highlightedEdge = new Line(step.currentNode.x, step.currentNode.y, step.nextNode.x, step.nextNode.y);
            highlightedEdge.setStroke(Color.BLUE);
            highlightedEdge.setStrokeWidth(3);
            highlightedEdge.getStrokeDashArray().addAll(10.0, 5.0);
        }

        redrawTree();

        // Update call stack UI
        callStackBox.getChildren().clear();
        for (int i = 0; i < step.callStack.size(); i++) {
            String call = step.callStack.get(i);
            Label label = new Label(call);
            label.setFont(Font.font("Consolas", FontWeight.NORMAL, 14));
            if (i == step.callStack.size() - 1) {
                label.setStyle("-fx-background-color: yellow;");
            }
            callStackBox.getChildren().add(label);
        }

        // Update visual stack UI
        visualStackBox.getChildren().clear();
        for (int val : step.visualStack) {
            Label lbl = new Label(String.valueOf(val));
            lbl.setFont(Font.font("Consolas", FontWeight.BOLD, 16));
            lbl.setTextFill(Color.DARKGREEN);
            lbl.setStyle("-fx-border-color: black; -fx-padding: 3 8 3 8; -fx-background-color: #ccffcc;");
            visualStackBox.getChildren().add(lbl);
        }

        // Show action label for push/pop
        if (step.stackAction != VisualStackAction.NONE) {
            Label actionLabel = new Label(step.stackAction == VisualStackAction.PUSH ? "Push node " + (step.currentNode != null ? step.currentNode.val : "") :
                    "Pop node " + (step.currentNode != null ? step.currentNode.val : ""));
            actionLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            actionLabel.setTextFill(step.stackAction == VisualStackAction.PUSH ? Color.BLUE : Color.RED);
            if (!visualStackBox.getChildren().isEmpty()) {
                if (step.stackAction == VisualStackAction.PUSH) {
                    visualStackBox.getChildren().get(0).setStyle("-fx-border-color: black; -fx-padding: 3 8 3 8; -fx-background-color: #99ff99;"); // Highlight top pushed
                }
            }
            if (!callStackBox.getChildren().isEmpty()) {
                // also highlight the top call in callStack if pushing/popping
                callStackBox.getChildren().get(callStackBox.getChildren().size()-1).setStyle("-fx-background-color: #ffff99;");
            }
            visualStackBox.getChildren().add(actionLabel);
        }

        currentStepIndex++;
    }

    private void resetNodeColors(BSTNode node) {
        if (node == null) return;
        node.setColor(Color.LIGHTGRAY);
        resetNodeColors(node.left);
        resetNodeColors(node.right);
    }

    // Classes and data structures ---------------------------------

    static class BSTNode {
        int val;
        BSTNode left, right;
        double x, y;
        Circle circle;
        private Color color = Color.LIGHTGRAY;

        public BSTNode(int val) {
            this.val = val;
        }

        public void setColor(Color c) {
            color = c;
            if (circle != null) {
                circle.setFill(color);
            }
        }

        public Color getColor() {
            return color;
        }
    }

    // Stack action for visualization
    enum VisualStackAction {
        PUSH, POP, NONE
    }

    static class SearchStep {
        BSTNode currentNode;
        List<String> callStack;
        List<Integer> visualStack;
        String nextDirection; // "left" or "right" or null
        BSTNode nextNode; // Node of next traversal (left or right)
        VisualStackAction stackAction;

        public SearchStep(BSTNode currentNode, List<String> callStack, List<Integer> visualStack,
                          String nextDirection, BSTNode nextNode, VisualStackAction stackAction) {
            this.currentNode = currentNode;
            this.callStack = callStack;
            this.visualStack = visualStack;
            this.nextDirection = nextDirection;
            this.nextNode = nextNode;
            this.stackAction = stackAction;
        }
    }
}
