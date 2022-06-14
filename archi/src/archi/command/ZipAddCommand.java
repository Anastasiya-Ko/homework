package archi.command;

import archi.ConsoleHelper;
import archi.ZipFileManager;
import archi.exception.PathlsNotFoundException;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Класс, отвечающий за команду ДОБАВЛЕНИЯ файла в архив
 */
public class ZipAddCommand extends ZipCommand{
    @Override
    public void execute() throws Exception {
        try {
            ConsoleHelper.writeMessage("Добавление нового файла в архив");  // в консоли информируем об идущей операции

            ZipFileManager zipFileManager = getZipFileManager();

            ConsoleHelper.writeMessage("Введите полное имя файла, который нужно добавить");

            Path sourcePath = Paths.get(ConsoleHelper.readString()); //Получаем путь из строки

            zipFileManager.addFile(sourcePath);

            ConsoleHelper.writeMessage("Файл успешно добавлен");

        } catch (PathlsNotFoundException e){
            ConsoleHelper.writeMessage("Файл не был найден");
        }

    }
}
