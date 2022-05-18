package main.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// Вспомогательный класс, для чтения или записи в консоль


public class ConsoleHelper {

    private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    //Метод, выводящий сообщение message в консоль
    public static void writeMessage(String message) {
        System.out.println(message);
    }

    //Метод, считывающий строку с консоли
    public static String readString() {
        while (true) {
            try {
                String textInConsole = bufferedReader.readLine();
                if (textInConsole != null)
                    return textInConsole;
            } catch (IOException e) {
                writeMessage("Произошла ошибка при попытке ввода текста. Попробуйте ещё раз");
            }
        }
    }

    //Метод, возвращающий введённое число
    public static int readInt() {
        while (true) {
            try {
                return Integer.parseInt(readString().trim());
            } catch (NumberFormatException e) {
                writeMessage("Произошла ошибка при попытке ввода. Попробуйте ещё раз");
            }
        }

    }

}
