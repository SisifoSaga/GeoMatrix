import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    private final String input;
    private int position;
    private int line;
    private int linePosition;
    private final ErrorStack errorStack;

    // Definición de patrones para números e identificadores
    private static final Pattern numberPattern = Pattern.compile("\\d+");
    private static final Pattern identifierPattern = Pattern.compile("[a-zA-Z][a-zA-Z0-9]*");

    public Lexer(String input, ErrorStack errorStack) {
        this.input = input;
        this.position = 0;
        this.line = 1;
        this.linePosition = 1;
        this.errorStack = errorStack;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        while (position < input.length()) {
            char currentChar = input.charAt(position);

            try {
                switch (currentChar) {
                    case ' ':
                    case '\t':
                        position++;
                        linePosition++;
                        break;
                    case '\n':
                        position++;
                        line++;
                        linePosition = 1;
                        break;
                    case '(':
                        tokens.add(new Token(TokenType.PARENTESIS_IZQUIERDO, "("));
                        position++;
                        linePosition++;
                        break;
                    case ')':
                        tokens.add(new Token(TokenType.PARENTESIS_DERECHO, ")"));
                        position++;
                        linePosition++;
                        break;
                    case ',':
                        tokens.add(new Token(TokenType.COMA, ","));
                        position++;
                        linePosition++;
                        break;
                    case ';':
                        tokens.add(new Token(TokenType.PUNTO_Y_COMA, ";"));
                        position++;
                        linePosition++;
                        break;
                    default:
                        if (Character.isDigit(currentChar)) {
                            tokens.add(new Token(TokenType.NUMERO, readNumber()));
                        } else if (Character.isLetter(currentChar)) {
                            String identifier = readIdentifier();
                            tokens.add(new Token(getTokenType(identifier), identifier));
                        } else {
                            // Agregar el error con línea y posición
                            errorStack.addError("Error léxico: carácter inesperado '" + currentChar + "' en línea " + line + ", posición " + linePosition);
                            position++;
                            linePosition++;
                        }
                }
            } catch (RuntimeException e) {
                errorStack.addError("Error en línea " + line + ": " + e.getMessage());
                break;  // Detiene el análisis en cuanto encuentra un error
            }
        }
        tokens.add(new Token(TokenType.FIN, ""));
        return tokens;
    }

    private String readNumber() {
        Matcher matcher = numberPattern.matcher(input.substring(position));
        if (matcher.find()) {
            position += matcher.group().length();
            linePosition += matcher.group().length();
            return matcher.group();
        }
        return "";
    }

    private String readIdentifier() {
        Matcher matcher = identifierPattern.matcher(input.substring(position));
        if (matcher.find()) {
            position += matcher.group().length();
            linePosition += matcher.group().length();
            return matcher.group();
        }
        return "";
    }

    private TokenType getTokenType(String identifier) {
        return switch (identifier) {
            case "crear" -> TokenType.CREAR;
            case "calcular" -> TokenType.CALCULAR;
            case "transformar" -> TokenType.TRANSFORMAR;
            case "cuadrado" -> TokenType.CUADRADO;
            case "circulo" -> TokenType.CIRCULO;
            case "rectangulo" -> TokenType.RECTANGULO;
            case "triangulo" -> TokenType.TRIANGULO;
            case "area" -> TokenType.AREA;
            case "perimetro" -> TokenType.PERIMETRO;
            case "escalar" -> TokenType.ESCALAR;
            case "rotar" -> TokenType.ROTAR;
            default -> TokenType.IDENTIFICADOR;
        };
    }
}
