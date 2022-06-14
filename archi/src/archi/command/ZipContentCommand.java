package archi.command;

import archi.ConsoleHelper;
import archi.FileProperties;
import archi.ZipFileManager;

import java.util.List;

/**
 * Класс, отвечающий за команду ПРОСМОТРА содержимого архива (это упакованные файлы и папки(их имена, размер до и после сжатия, степень сжатия и метод.)
 */
public class ZipContentCommand extends ZipCommand{
    @Override
    public void execute() throws Exception {
        ConsoleHelper.writeMessage("Просмотр содержимого архива");
        ZipFileManager zipFileManager = getZipFileManager();

        ConsoleHelper.writeMessage("Содержимое архива: ");

        List<FileProperties> filesList = zipFileManager.getFilesList(); //Получение списка файлов
        for(FileProperties file : filesList){                           //Вывод свойств каждого файла в консоль
            ConsoleHelper.writeMessage(file.toString());
        }
        ConsoleHelper.writeMessage("Содержимое архива прочитано");
    }
}
