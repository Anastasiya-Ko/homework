package archi.command;


import archi.ConsoleHelper;
import archi.ZipFileManager;
import archi.exception.PathlsNotFoundException;
import archi.exception.WrongZipFileException;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Класс, отвечающий за команду РАСПАКОВКИ архива
 */
public class ZipExtractCommand extends ZipCommand{
    @Override
    public void execute() throws Exception {
        try {
            ConsoleHelper.writeMessage("Распаковка архива.");  // в консоли информируем об идущей операции

            ZipFileManager zipFileManager = getZipFileManager();

            ConsoleHelper.writeMessage("Введите путь для распаковки: ");

            Path destinationPath = Paths.get(ConsoleHelper.readString()); //Получаем путь из строки

            zipFileManager.extractAll(destinationPath);

            ConsoleHelper.writeMessage("Архив распакован");

        } catch (WrongZipFileException e){
            ConsoleHelper.writeMessage("Архив не существует");
        }
    }
}
