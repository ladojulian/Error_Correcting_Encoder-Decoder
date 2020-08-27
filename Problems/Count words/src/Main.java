import java.io.BufferedReader;
import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        var ch = reader.read();
        var previousCh = ch;
        var count = 0;
        while (ch != -1) {
            if (ch == ' ' && previousCh != ' ') {
                count++;
            }
            previousCh = ch;
            ch = reader.read();
        }
        if (previousCh != ' ') {
            count++;
        }

        reader.close();
        System.out.println(count);
    }
}