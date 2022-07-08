package code;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Класс - фильтр для Открытия или Сохранения файла (используем библиотеку JFileChooser)
 */
public class HTMLFileFilter extends FileFilter {

    /**
     * Метод, проверяющий, чтобы окно выбора файла отображало только htm и html файлы или папки
     * @param file переданный файл
     * @return true-переданный файл/директория содержит в конце имени .htm/.html(без учёта регистра)
     */
    @Override
    public boolean accept(File file) {
       return ((file.isDirectory())) //Проверяет, что файл — это директория, а не файл
               || (file.getName().toLowerCase().endsWith(".htm")) //возьми имя файла, преобразовав в строку->преобрази в нижний регистр->
               || (file.getName().toLowerCase().endsWith(".html")); //->проверь в конце строки указанный суффикс
    }

    @Override
    public String getDescription() {
        return "HTML и HTM файлы";
    }
}
