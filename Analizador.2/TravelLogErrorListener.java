import org.antlr.v4.runtime.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TravelLogErrorListener extends BaseErrorListener {

    private PrintWriter logWriter;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public TravelLogErrorListener(String logFileName) {
        try {
            logWriter = new PrintWriter(new FileWriter(logFileName, true)); // true = append
        } catch (IOException e) {
            System.err.println("No se pudo abrir travel_log.txt para escribir errores.");
        }
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer,
                            Object offendingSymbol,
                            int line, int charPositionInLine,
                            String msg,
                            RecognitionException e)
    {
        String now = LocalDateTime.now().format(dtf);
        String error = "[" + now + "] Error sintáctico en línea " + line + ":" + charPositionInLine + " - " + msg;
        System.err.println(error);
        if (logWriter != null) {
            logWriter.println(error);
            logWriter.flush();
        }
    }

    public void close() {
        if (logWriter != null) {
            logWriter.close();
        }
    }
}
