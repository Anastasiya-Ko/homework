package archi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Помощник консоли
 */
public class ConsoleHelper {

    private static BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Метод для вывода сообщения в консоль.
     * @param message введённое сообщение.
     */
    public static void writeMessage(String message){
        System.out.println(message);
    }

    /**
     * Метод для чтения строки с консоли
     * @return строка
     * @throws IOException
     */
    public static String readString() throws IOException {
        String text = bis.readLine();
        return text;
    }

    /**
     * Метод, для чтения числа с консоли
     * @return int
     * @throws IOException
     */
    public static int readInt() throws IOException{
        String text = readString();
        return Integer.parseInt(text.trim()); //trim()-удаление всех пробелов в начале и конце строки
    }

}
