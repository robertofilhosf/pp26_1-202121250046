package debate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class LogSistem {
    private static LogSistem instance;
    private final String file_path;

    private LogSistem(String file_path) {
        this.file_path = file_path;
    }

    public static LogSistem get_instance(String file) {
        if (instance == null) {
            instance = new LogSistem(file);
            instance.register_log("Init");
        }
        return instance;
    }

    public String get_file_path() {
        return file_path;
    }

    public void register_log(String msg) {
        Path path = Paths.get(file_path);
        try {
            Path parent = path.getParent();
            if (parent != null && Files.notExists(parent)) {
                Files.createDirectories(parent);
            }
            String linha = msg + System.lineSeparator();
            Files.write(path, linha.getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Erro ao registrar log: " + e.getMessage());
        }
    }

    public String get_logs_register() {
        Path path = Paths.get(file_path);
        if (!Files.exists(path)) {
            return "";
        }
        try {
            List<String> linhas = Files.readAllLines(path, StandardCharsets.UTF_8);
            StringBuilder sb = new StringBuilder();
            for (String linha : linhas) {
                sb.append(linha).append(System.lineSeparator());
                System.out.println(linha);
            }
            return sb.toString();
        } catch (IOException e) {
            System.err.println("Erro ao ler log: " + e.getMessage());
            return "";
        }
    }
}
