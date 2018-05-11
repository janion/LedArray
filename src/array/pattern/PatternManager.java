package array.pattern;

import array.led.writer.PixelWriter;
import array.led.PixelWriterFactory;
import array.led.builtin.BuiltinFunctionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PatternManager {

    private static final String DEFAULT_PATTERN_NAME = "None";
    private static final String DEFAULT_PATTERN_FILE_NAME = "patterns.csv";

    private PixelWriterFactory writerFactory;
    private String patternFileName;
    private PatternReader fileReader;
    private PatternWriter fileWriter;

    private List<Pattern> patterns;
    private String currentPatternName;
    private PixelWriter currentWriter;

    private BuiltinFunctionManager builtins;

    public PatternManager(PixelWriterFactory writerFactory) {
        this(writerFactory, DEFAULT_PATTERN_FILE_NAME);
    }

    public PatternManager(PixelWriterFactory writerFactory, String patternFileName) {
        this.writerFactory = writerFactory;
        this.patternFileName = patternFileName;
        fileReader = new PatternReader();
        fileWriter = new PatternWriter();
        patterns = fileReader.readPatterns(patternFileName);
        builtins = new BuiltinFunctionManager();
    }

    public String getCurrentPatternName() {
        return currentPatternName;
    }

    public List<Pattern> getPatterns() {
        return new ArrayList<>(patterns);
    }

    public BuiltinFunctionManager getBuiltinPatternsManager() {
        return builtins;
    }

    public void setPattern(String name) {
        // Check builtins
        for (String builtinName : builtins.getPatternNames()) {
            if (builtinName.equals(name)) {
                currentWriter = builtins.getWriter(name);
                return;
            }
        }

        // Check custom patterns
        for (Pattern pattern : patterns) {
            if (pattern.getName().equals(name)) {
                currentWriter = writerFactory.createPixelWriter(pattern);
                break;
            }
        }
    }

    public boolean addPattern(String name, String redFunction, String greenFunction, String blueFunction) {
        Pattern pattern = new Pattern(name, redFunction, greenFunction, blueFunction);
        if (pattern.isValid()) {
            patterns.add(pattern);
            fileWriter.writePatterns(patternFileName, patterns);
            if (patterns.size() == 1) {
                setPattern(name);
            }
            return true;
        }
        return false;
    }

    public void removePattern(String name) {
        for (int i = 0; i < patterns.size(); i++) {
            Pattern pattern = patterns.get(i);
            if (pattern.getName().equals(name)) {
                patterns.remove(pattern);

                if (name.equals(currentPatternName)) {
                    if (patterns.size() > 0) {
                        setPattern(patterns.get(0).getName());
                    } else if (builtins.getWriters().size() > 0) {
                        setPattern(builtins.getWriters().get(0).getName());
                    } else {
                        currentPatternName = DEFAULT_PATTERN_NAME;
                        currentWriter = null;
                    }
                }
                fileWriter.writePatterns(patternFileName, patterns);
                break;
            }
        }
    }

    public PixelWriter getCurrentWriter() {
        return currentWriter;
    }

    public PixelWriter getWriter(String name) {
        // Check builtins
        for (String builtinName : builtins.getPatternNames()) {
            if (builtinName.equals(name)) {
                return builtins.getWriter(name);
            }
        }

        // Check custom patterns
        for (Pattern pattern : patterns) {
            if (pattern.getName().equals(name)) {
                return writerFactory.createPixelWriter(pattern);
            }
        }

        return null;
    }

    public boolean isUniqueName(String name) {
        // Check builtins
        for (String builtinName : builtins.getPatternNames()) {
            if (builtinName.equals(name)) {
                return false;
            }
        }

        // Check custom patterns
        for (Pattern pattern : patterns) {
            if (pattern.getName().equals(name)) {
                return false;
            }
        }

        return true;
    }

    public List<String> getAllPatternNames() {
        final List<String> patternNames = patterns.stream().map(Pattern::getName).collect(Collectors.toList());
        builtins.getPatternNames().forEach(patternNames::add);

        return patternNames;
    }

}
