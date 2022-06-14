package archi;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для получения всех файлов в какой-то папке
 */
public class FileManager {

    /**
     * Корневой путь папки, файлы которой нас интересуют
     */
    private Path rootPath;

    /**
     * Список относительных путей ( - путь, относительно какой-то папки) файлов внутри rootPath(папки, файлы которой нас интересуют), включая файлы в подпапках.
     */
    private List<Path> fileList;

    /**
     *Конструктор. Здесь единождый задаётся папка, в которой мы будем искать.
     * Здесь же формируем список файлов(их относительных путей),
     * сохранив в переменную класса List<Path> fileList.
     * @param rootPath корневой путь папки, файлы которой нас интересуют.
     */
    public FileManager(Path rootPath) throws IOException {
        this.rootPath = rootPath;
        this.fileList = new ArrayList<>();
        collectFileList(rootPath);
    }

    public List<Path> getFileList() {
        return fileList;
    }

    /**
     * Метод, проверяющий переданный путь(обычный файл/папка)
     * @param path переданный путь.
     * @throws IOException
     */
    private void collectFileList(Path path) throws IOException {
        //Добавляем только файлы
        if (Files.isRegularFile(path)) {  //если путь является обычным файлом
            Path relativePath = rootPath.relativize(path);  //то получить его относительный путь относительно rootPath
            fileList.add(relativePath); // и добавить его в список fileList
        }
        //Добавляем содержимое папки
        if (Files.isDirectory(path)) {
            //Рекрусивно проходимся по всему содержимому папки
            //Чтобы не писать код по вызову Close для DirectoryStream, обернём вызов newDirectoryStream в try-with-resources
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path)) { //если переданный путь является папкой
                for (Path file : directoryStream) { //то пройтись по всему содержимому папки
                    collectFileList(file); //и вызвать collectFileList, передав в него полученные элементы
                }
            }
        }
    }
}
