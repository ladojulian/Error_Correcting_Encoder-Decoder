import java.io.BufferedReader;
import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        var builder = new StringBuilder();
        var ch = reader.read();
        while (ch != -1) {
            builder.append(Character.toString(ch));
            ch = reader.read();
        }
        reader.close();
        builder.reverse();
        System.out.println(builder.toString());
    }
}