import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.FileASTRequestor;

public class TestCase {
    public static void main(String[] args) throws IOException {
        ASTParser parser = ASTParser.newParser(AST.JLS_Latest);
        Map<String, String> options = new HashMap<>();
        options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_15);
        options.put(JavaCore.CORE_ENCODING, "UTF-8");
        options.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_15);
        parser.setCompilerOptions(options);
        parser.setResolveBindings(true);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        String src = "input";
        parser.setEnvironment(
                new String[0],
                new String[]{ src },
                new String[]{ "UTF-8" },
                false
        );
        List<Path> files = Files.walk(Paths.get(src))
                .filter(p -> p.toString().endsWith(".java") && !Files.isDirectory(p))
                .sorted()
                .collect(Collectors.toList());
        parser.createASTs(
                files.stream().map(Path::toString).toArray(String[]::new),
                files.stream().map(f -> "UTF-8").toArray(String[]::new),
                new String[0],
                new FileASTRequestor() {
                },
                null
        );
    }
}
