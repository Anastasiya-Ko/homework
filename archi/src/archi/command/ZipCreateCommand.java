package archi.command;

import archi.ConsoleHelper;
import archi.ZipFileManager;
import archi.exception.PathlsNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Класс, отвечающий за СОЗДАНИЕ архива (упаковки файлов в архив)
 */
public class ZipCreateCommand extends ZipCommand{

   @Override
    public void execute() throws Exception {
        try {
            ConsoleHelper.writeMessage("Создание архива.");  // в консоли информируем об идущей операции

            ZipFileManager zipFileManager = getZipFileManager();

            ConsoleHelper.writeMessage("Введите полное имя файла или папки для архивации");

            Path sourcePath = Paths.get(ConsoleHelper.readString()); //Получаем путь из строки

            zipFileManager.createZip(sourcePath);

            ConsoleHelper.writeMessage("Архив создан");

        } catch (PathlsNotFoundException e){
            ConsoleHelper.writeMessage("Вы ошибочно указали имя файла или папки");
        }
    }
}
