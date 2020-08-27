import java.io.CharArrayWriter;
import java.io.IOException;

class Converter {
    public static char[] convert(String[] words) throws IOException {
        var writer = new CharArrayWriter();
        for (var word : words) {
            writer.write(word, 0, word.length());
        }

        return writer.toCharArray();
    }
}