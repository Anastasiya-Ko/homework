package archi.command;


import archi.ConsoleHelper;
import archi.ZipFileManager;
import archi.exception.PathlsNotFoundException;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Класс, отвечающий за команду УДАЛЕНИЯ файла из архива
 */
public class ZipRemoveCommand extends ZipCommand{
    @Override
    public void execute() throws Exception {

            ConsoleHelper.writeMessage("Удаление файла из архива.");  // в консоли информируем об идущей операции

            ZipFileManager zipFileManager = getZipFileManager();

            ConsoleHelper.writeMessage("Введите путь к файлу в архиве, который нужно удалить");

            Path sourcePath = Paths.get(ConsoleHelper.readString()); //Получаем путь из строки

            zipFileManager.removeFile(sourcePath);

            ConsoleHelper.writeMessage("Удаление из архива завершено");

    }
}
