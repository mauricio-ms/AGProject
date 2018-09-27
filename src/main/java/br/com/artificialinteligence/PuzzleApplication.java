package br.com.artificialinteligence;

import br.com.artificialinteligence.maze.factories.LocksFactory;
import br.com.artificialinteligence.maze.model.Coordinates;
import br.com.artificialinteligence.maze.model.Lock;
import br.com.artificialinteligence.maze.model.MazeConfig;
import br.com.artificialinteligence.maze.model.MazePath;
import br.com.artificialinteligence.model.ConfigAG;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PuzzleApplication extends Application {

    private static final Logger LOGGER = Logger.getLogger(PuzzleApplication.class.getCanonicalName());

    private static final Integer PIECE_SIZE_PIXELS = 50;

    private static final Integer PUZZLE_SIZE = 100;

    private static final String BACKGROUND_COLOR = "-fx-background-color: #6699cc;";

    private static final Font ARIAL = Font.font("Arial", FontWeight.BOLD, 12);

    private final Pane puzzle;

    private final List<Lock> locks;

    private Thread currentExecution;

    private Label generationsNumberLb;

    private TextField generationsNumberTf;

    private Label populationSizeLb;

    private TextField populationSizeTf;

    private Label crossoverRateLb;

    private TextField crossoverRateTf;

    private Label mutationRateLb;

    private TextField mutationRateTf;

    private Label elitismRateLb;

    private TextField elitismRateTf;

    private CheckBox elitismCb;

    private Button startBt;

    private Button stopBt;

    private Label currentGenerationLb;

    private Text currentGenerationT;

    private Label currentFitnessLb;

    private Text currentFitnessT;

    public PuzzleApplication() {
        puzzle = buildPuzzle();
        locks = LocksFactory.createDefault();
        applyBounds();
        applyLocks();
    }

    @Override
    public void start(final Stage primaryStage) {
        final BorderPane borderPane = new BorderPane();

        final Scene scene = new Scene(borderPane, PIECE_SIZE_PIXELS * 10 + 250, PIECE_SIZE_PIXELS * 10 + 150);
        primaryStage.getIcons().add(new Image(ClassLoader.getSystemResourceAsStream("icon.png")));

        primaryStage.setTitle("AG");
        primaryStage.setScene(scene);
        primaryStage.getScene().getStylesheets().add("puzzle.css");

        final HBox topBox = createHorizontalBox(100);
        final Text tittle = new Text("Algoritmo Genético");
        tittle.setFont(Font.font(40));
        tittle.setId("title");

        topBox.getChildren().addAll(tittle, createCurrentScoresBox());
        borderPane.setTop(topBox);

        final VBox leftBox = createVerticalBox(200);

        final ConfigAG defaultConfigAG = ConfigAG.createDefault();

        generationsNumberLb = new Label("Número de Gerações *:");
        generationsNumberLb.setFont(ARIAL);
        generationsNumberTf = new TextField(String.valueOf(defaultConfigAG.getMaxNumberGenerations()));

        populationSizeLb = new Label("Tamanho da População *:");
        populationSizeLb.setFont(ARIAL);
        populationSizeTf = new TextField(String.valueOf(defaultConfigAG.getPopulationSize()));

        crossoverRateLb = new Label("Taxa de Crossover *:");
        crossoverRateLb.setFont(ARIAL);
        crossoverRateTf = new TextField(String.valueOf(defaultConfigAG.getCrossoverRate()));

        mutationRateLb = new Label("Taxa de Mutação *:");
        mutationRateLb.setFont(ARIAL);
        mutationRateTf = new TextField(String.valueOf(defaultConfigAG.getMutationRate()));

        elitismCb = new CheckBox("Elitismo");
        elitismCb.setFont(ARIAL);
        elitismCb.setSelected(defaultConfigAG.isElitism());
        elitismCb.setOnAction(cb -> elitismRateTf.setDisable(!((CheckBox) cb.getSource()).isSelected()));

        elitismRateLb = new Label("Taxa de Elitismo *:");
        elitismRateLb.setFont(ARIAL);
        elitismRateTf = new TextField(String.valueOf(defaultConfigAG.getElitismRate()));
        elitismRateTf.setDisable(!defaultConfigAG.isElitism());

        startBt = new Button("Iniciar");
        startBt.setPrefWidth(180);
        startBt.setFont(ARIAL);
        startBt.setStyle("-fx-background-color: #1aff1a");
        startBt.setBorder(createBorder(Paint.valueOf("black")));
        startBt.setOnAction(click -> {
            startBt.setDisable(true);
            startAg();
        });

        stopBt = new Button("Parar");
        stopBt.setPrefWidth(180);
        stopBt.setFont(ARIAL);
        stopBt.setStyle("-fx-background-color: #ff4d4d");
        stopBt.setBorder(createBorder(Paint.valueOf("black")));
        stopBt.setOnAction(click -> stopAg());

        leftBox.getChildren().addAll(
                generationsNumberLb, generationsNumberTf,
                populationSizeLb, populationSizeTf,
                crossoverRateLb, crossoverRateTf,
                mutationRateLb, mutationRateTf,
                elitismCb,
                elitismRateLb, elitismRateTf,
                startBt, stopBt
        );

        borderPane.setLeft(leftBox);
        final VBox rightBox = createVerticalBox(50);
        borderPane.setRight(rightBox);
        final HBox bottomBox = createHorizontalBox(50);
        bottomBox.setStyle(BACKGROUND_COLOR);
        borderPane.setBottom(bottomBox);

        borderPane.setCenter(puzzle);

        primaryStage.show();
    }

    private HBox createCurrentScoresBox() {
        final HBox currentScores = new HBox();
        currentScores.setPadding(new Insets(15, 30, 15, 30));
        currentScores.setSpacing(10);

        final VBox currentGeneration = new VBox();
        currentGenerationLb = new Label("Geração :");
        currentGenerationLb.setFont(ARIAL);
        currentGenerationT = new Text();
        currentGeneration.getChildren().addAll(currentGenerationLb, currentGenerationT);

        final VBox currentFitness = new VBox();
        currentFitnessLb = new Label("Aptidão :");
        currentFitnessLb.setFont(ARIAL);
        currentFitnessT = new Text();
        currentFitness.getChildren().addAll(currentFitnessLb, currentFitnessT);

        currentScores.getChildren().addAll(currentGeneration, currentFitness);
        return currentScores;
    }

    private VBox createVerticalBox(final Integer width) {
        final VBox vBox = new VBox();
        vBox.setStyle(BACKGROUND_COLOR);
        vBox.setPadding(new Insets(15, 10, 15, 10));
        vBox.setSpacing(10);
        vBox.setPrefWidth(width);
        return vBox;
    }

    private HBox createHorizontalBox(final Integer height) {
        final HBox hBox = new HBox();
        hBox.setPadding(new Insets(15, 10, 15, 10));
        hBox.setPrefHeight(height);
        return hBox;
    }

    private Pane buildPuzzle() {
        final Paint defaultColorBorder = Paint.valueOf("white");
        final Border border = new Border(
                createBorderStroke(
                        defaultColorBorder,
                        defaultColorBorder,
                        defaultColorBorder,
                        defaultColorBorder
                )
        );

        final Pane gamePane = new Pane();
        for (int i = 0; i < PUZZLE_SIZE; i++) {
            final Integer x = i % 10;
            final Integer y = i / 10;

            final Region piece = new Region();
            piece.setLayoutX(PIECE_SIZE_PIXELS * x);
            piece.setLayoutY(PIECE_SIZE_PIXELS * y);
            piece.setPrefWidth(PIECE_SIZE_PIXELS);
            piece.setPrefHeight(PIECE_SIZE_PIXELS);
            piece.setMaxWidth(PIECE_SIZE_PIXELS);
            piece.setMaxHeight(PIECE_SIZE_PIXELS);
            piece.getStyleClass().add("estado-objetivo");
            piece.setBorder(border);

            final Text text = new Text();
            text.setText(String.valueOf(i));
            text.getStyleClass().add("conteudo-estado");

            final StackPane stackPane = new StackPane();
            final Integer layoutX = PIECE_SIZE_PIXELS * x;
            final Integer layoutY = PIECE_SIZE_PIXELS * (9 - y);
            stackPane.setLayoutX(layoutX);
            stackPane.setLayoutY(layoutY);
            stackPane.getChildren().addAll(piece, text);

            gamePane.getChildren().add(stackPane);
        }
        return gamePane;
    }

    private void applyBounds() {
        for (int i = 0; i < PUZZLE_SIZE; i++) {
            final Region piece = getPiece(i);
            if (i > 0 && i % 10 == 0) {
                final BorderStroke borderStroke = getBorderStroke(piece);
                piece.setBorder(getBorderWithNewLeft(borderStroke));
            }
            if (i % 10 == 9 && i != 99) {
                final BorderStroke borderStroke = getBorderStroke(piece);
                piece.setBorder(getBorderWithNewRight(borderStroke));
            }

            if (i >= 0 && i < 10) {
                final BorderStroke borderStroke = getBorderStroke(piece);
                piece.setBorder(getBorderWithNewBottom(borderStroke));
            } else if (i >= 90 && i < 100) {
                final BorderStroke borderStroke = getBorderStroke(piece);
                piece.setBorder(getBorderWithNewTop(borderStroke));
            }
        }
    }

    private Border createBorder(final Paint color) {
        return new Border(
                new BorderStroke(
                        color,
                        color,
                        color,
                        color,
                        BorderStrokeStyle.SOLID,
                        BorderStrokeStyle.SOLID,
                        BorderStrokeStyle.SOLID,
                        BorderStrokeStyle.SOLID,
                        new CornerRadii(2),
                        new BorderWidths(2),
                        Insets.EMPTY
                )
        );
    }

    private BorderStroke getBorderStroke(final Region piece) {
        final Border border = piece.getBorder();
        return border.getStrokes().get(0);
    }

    private Border getBorderWithNewTop(final BorderStroke borderStroke) {
        return new Border(
                createBorderStroke(Paint.valueOf("blue"), borderStroke.getRightStroke(),
                        borderStroke.getBottomStroke(), borderStroke.getLeftStroke())
        );
    }

    private BorderStroke createBorderStroke(final Paint topPaint,
                                            final Paint rightPaint,
                                            final Paint bottomPaint,
                                            final Paint leftPaint) {
        return new BorderStroke(
                topPaint,
                rightPaint,
                bottomPaint,
                leftPaint,
                BorderStrokeStyle.SOLID,
                BorderStrokeStyle.SOLID,
                BorderStrokeStyle.SOLID,
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                BorderStroke.MEDIUM,
                Insets.EMPTY
        );
    }

    private Border getBorderWithNewBottom(final BorderStroke borderStroke) {
        return new Border(
                createBorderStroke(
                        borderStroke.getTopStroke(),
                        borderStroke.getRightStroke(),
                        Paint.valueOf("blue"),
                        borderStroke.getLeftStroke()
                )
        );
    }

    private Border getBorderWithNewRight(final BorderStroke borderStroke) {
        return new Border(
                createBorderStroke(
                        borderStroke.getTopStroke(),
                        Paint.valueOf("blue"),
                        borderStroke.getBottomStroke(),
                        borderStroke.getLeftStroke()
                )
        );
    }

    private Border getBorderWithNewLeft(final BorderStroke borderStroke) {
        return new Border(
                createBorderStroke(
                        borderStroke.getTopStroke(),
                        borderStroke.getRightStroke(),
                        borderStroke.getBottomStroke(),
                        Paint.valueOf("blue")
                )
        );
    }

    private void applyLocks() {
        locks.forEach(lock -> {
            if (lock.isHorizontal()) {
                IntStream.range(lock.getxStart(), lock.getxEnd())
                        .forEach(index -> {
                            final Region piece = getPiece(index + lock.getyStart() * 10);
                            final BorderStroke borderStroke = getBorderStroke(piece);
                            piece.setBorder(getBorderWithNewTop(borderStroke));
                        });
            } else if (lock.isVertical()) {
                IntStream.range(lock.getyStart(), lock.getyEnd())
                        .forEach(index -> {
                            final Region piece = getPiece(index * 10 + lock.getxStart());
                            final BorderStroke borderStroke = getBorderStroke(piece);
                            piece.setBorder(getBorderWithNewRight(borderStroke));
                        });
            }
        });
    }

    private void startAg() {
        currentExecution = new Thread(() -> {
            try {

//                final ConfigAG configAG = ConfigAG.of(
//                        IntegerParser.parse(generationsNumberTf.getText()),
//                        IntegerParser.parse(populationSizeTf.getText()),
//                        DoubleParser.parse(crossoverRateTf.getText()),
//                        DoubleParser.parse(mutationRateTf.getText()),
//                        DoubleParser.parse(elitismRateTf.getText()),
//                        elitismCb.isSelected()
//                );
//                final AG ag = AGFactory.from(ProblemType.MAZE, configAG);
//
//                while( !ag.reachToTheMaxNumberOfGenerations() ) {
//                    currentGenerationT.setText(String.valueOf(ag.getCurrentGeneration()));
//                    currentFitnessT.setText(String.valueOf(ag.getBetter().getFitness()));
//
//                    final LocalDateTime start = LocalDateTime.now();
//
//                    final Subject better = ag.getBetter();
//                    System.out.println("==============================");
//                    System.out.println(better);
//                    System.out.println("==============================");
//                    final String genes = better.getGenes();
//                    System.out.println("Genes: " + genes);
//                    final MazePath mazePath = MazePath.of(genes);
//                    while (mazePath.hasMove()) {
//                        moverPeca(mazePath.move());
//                    }
//                    ag.stage();
//                    clearPuzzle();
//                    System.out.println("Resultados:");
//                    System.out.println("Tempo Decorrido >> " +
//                            start.until(LocalDateTime.now(), ChronoUnit.SECONDS) + " segundos.");
//                }
//
//                stopAg();
                Stream.of("100101011011101110010110011010100110010000011001100110", "101001010001011010101010111010010100010101101110010110", "101001010110101001010100010001100110101110101110010110", "100101101010010101010000000110101010100101101010111001", "100110100101101110010101000101101011101001011011100101")
                        .forEach(genes -> {
                            final MazePath mazePath = MazePath.of(genes);
                            while (mazePath.hasMove()) {
                                moverPeca(mazePath.move());
                            }
                            clearPuzzle();
                        });
            } catch (IllegalArgumentException e) {
                LOGGER.severe(e.getMessage());
                Platform.runLater(() -> showAlertError(e.getMessage()));
                stopAg();
            }
        });
        currentExecution.start();
    }

    private void showAlertError(final String errorMessage) {
        final Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setContentText(errorMessage);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(ClassLoader.getSystemResourceAsStream("icon.png")));

        alert.showAndWait();
    }

    private void stopAg() {
        new Thread(
                () -> {
                    clearPuzzle();
                    currentExecution.stop();
                    startBt.setDisable(false);
                }
        ).start();
    }

    private void clearPuzzle() {
        IntStream.range(0, PUZZLE_SIZE)
                .forEach(i -> {
                    final Region piece = getPiece(i);
                    piece.getStyleClass().removeAll("ativo", "visitado");
                });
    }

    private void moverPeca(final Coordinates coordinates) {
        if (coordinates.getX() < 0 || coordinates.getY() < 0 ||
                coordinates.getX() > 9 || coordinates.getY() > 9) {
            return;
        }

        final Region piece = getPiece(coordinates.getX() + (coordinates.getY() * MazeConfig.SIZE));
        piece.getStyleClass().add("ativo");
        delay(50);
        piece.getStyleClass().add("visitado");
    }

    private Region getPiece(final Integer position) {
        final StackPane node = (StackPane) puzzle.getChildren().get(position);
        return (Region) node.getChildren().get(0);
    }

    private void delay(final Integer delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}