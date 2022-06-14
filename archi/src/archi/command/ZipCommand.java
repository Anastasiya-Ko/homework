package archi.command;

import archi.ConsoleHelper;
import archi.ZipFileManager;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Абстрактный класс - чтобы не создавать объекты класса (но методы в абстрактном классе должны быть реализованными)
 */
public abstract class ZipCommand implements Command {

    /**
     * метод для получения пути к файлу
     * @return путь к файлу
     * @throws Exception
     */
    public ZipFileManager getZipFileManager() throws Exception {

        ConsoleHelper.writeMessage("Введите полный путь файла архива");

        Path zipPath = Paths.get(ConsoleHelper.readString());  // преобразует строку в путь

        return new ZipFileManager(zipPath);
    }
}
