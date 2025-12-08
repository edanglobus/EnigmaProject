package history;

public class ProcessedString {
    private final String source;
    private final String result;
    private final long timeTaken;

    public ProcessedString(String source, String result, long timeTaken) {
        this.source = source;
        this.result = result;
        this.timeTaken = timeTaken;
    }

    @Override
    public String toString() {
        return String.format("<%s> --> <%s> (%d nano-seconds)", source, result, timeTaken);
    }
}
