import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class GeoMatrixApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("GeoMatrix Interpreter");

        // Área de texto para el código
        Label codeLabel = new Label("Código:");
        TextArea codeArea = new TextArea();
        codeArea.setPromptText("Escribe el código aquí...");

        // Botón de ejecución con funcionalidad de análisis léxico
        Button executeButton = new Button("Ejecutar");
        executeButton.setOnAction(event -> {
            // Obtener el texto ingresado en el área de código
            String code = codeArea.getText();

            // Crear una instancia de ErrorStack para capturar errores
            ErrorStack errorStack = new ErrorStack();

            // Ejecutar el análisis léxico
            Lexer lexer = new Lexer(code, errorStack);
            List<Token> tokens = lexer.tokenize();

            // Verificar si hay errores
            if (errorStack.hasErrors()) {
                // Mostrar errores en un cuadro de diálogo
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errores Léxicos Encontrados");
                alert.setHeaderText("Se encontraron errores en el código:");
                alert.setContentText(String.join("\n", errorStack.getErrors()));
                alert.showAndWait();
            } else {
                // Si no hay errores, mostrar los tokens generados
                StringBuilder tokenOutput = new StringBuilder("Tokens generados:\n");
                for (Token token : tokens) {
                    tokenOutput.append(token).append("\n");
                }

                // Mostrar los tokens en un cuadro de diálogo informativo
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Análisis Léxico Exitoso");
                alert.setHeaderText("El análisis léxico se completó sin errores.");
                alert.setContentText(tokenOutput.toString());
                alert.showAndWait();
            }
        });

        // Sección del panel para el código
        VBox codePanel = new VBox(10, codeLabel, codeArea, executeButton);
        codePanel.setPadding(new Insets(10));
        codePanel.setPrefWidth(300);

        // Panel de visualización (aún vacío, se usará en la segunda etapa)
        Label displayLabel = new Label("Visualización:");
        StackPane displayPane = new StackPane();
        displayPane.setStyle("-fx-border-color: black; -fx-border-width: 2;");
        displayPane.setPrefSize(400, 400);

        VBox displayPanel = new VBox(10, displayLabel, displayPane);
        displayPanel.setPadding(new Insets(10));

        // Layout principal
        HBox mainLayout = new HBox(10, codePanel, displayPanel);
        Scene scene = new Scene(mainLayout, 750, 450);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
