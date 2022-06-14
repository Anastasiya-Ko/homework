package archi;

import archi.exception.WrongZipFileException;

import java.io.*;

/**
 * Главный класс
 */
public class Archiver {

    public static void main(String[] args) throws Exception {

        Operation operation = null;
        //В цикле запрашивается новое значение для operation и вызывается выполнение операции
        do {
            try {
                operation = askOperation();             //В цикле запрашивается новое значение для operation
                CommandExecutor.execute(operation);     // и вызывается выполнение операции
            } catch (WrongZipFileException e) {
                ConsoleHelper.writeMessage("Вы не выбрали файл архива или выбрали неверный файл");
            } catch (Exception e) {
                ConsoleHelper.writeMessage("Произошла ошибка. Проверьте введённые данные");
            }
        } while (operation != Operation.EXIT);
    }

    /**
     * Метод, выводящий в консоль список доступных команд и просящий выбрать одну из них
     * @return одну из команд
     * @throws IOException
     */
    public static Operation askOperation() throws IOException {
        ConsoleHelper.writeMessage("Выберете опцию");
        ConsoleHelper.writeMessage(String.format("\t %d - упаковать файлы в архив", Operation.CREATE.ordinal())); //%d - сюда подставляется целое число. Метод ordinal() возвращает порядковый номер константы. Вызывать его нужно не у класса enum, а у значения enum.
        ConsoleHelper.writeMessage(String.format("\t %d - добавить файл в архив", Operation.ADD.ordinal()));
        ConsoleHelper.writeMessage(String.format("\t %d - удалить файл из архива", Operation.REMOVE.ordinal()));
        ConsoleHelper.writeMessage(String.format("\t %d - извлечь содержимое архива", Operation.EXTRACT.ordinal()));
        ConsoleHelper.writeMessage(String.format("\t %d - посмотреть содержимое архива", Operation.CONTENT.ordinal()));
        ConsoleHelper.writeMessage(String.format("\t %d - выйти из программы", Operation.EXIT.ordinal()));

        return Operation.values()[ConsoleHelper.readInt()]; //Статический метод values() возвращает массив всех значений типа enum
    }
}
