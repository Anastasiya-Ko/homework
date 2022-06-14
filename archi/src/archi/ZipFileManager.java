package archi;

import archi.exception.PathlsNotFoundException;
import archi.exception.WrongZipFileException;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Класс, совершающий операции над файлом архива (файл, хранящийся на диске и имеющий расширение .zip)
 */
public class ZipFileManager {

    /**
     * zipFile - в ней хранится полный путь к архиву, с которым будем работать.
     */
    private Path zipFile;

    public ZipFileManager(Path zipFile) {
        this.zipFile = zipFile;
    }

    /**
     * Метод, в котором архивируется файл, заданный переменной source
     *
     * @param source это путь к чему-то, что мы будем архивировать
     * @throws Exception Если переданный source не папка и не файл
     */
    public void createZip(Path source) throws Exception {

        Path zipDirectory = zipFile.getParent();
        if (Files.notExists(zipDirectory)) {       //Проверяем, существует ли папка, где будет создаваться архив.
            Files.createDirectories(zipDirectory); //При необходимости создаём её.
        }

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(zipFile))) { //Создаём zip поток

            if (Files.isDirectory(source)) {   // Проверяем, является ли файл каталогом
                FileManager fileManager = new FileManager(source);  //Если архивируем папку, то нужно получить список файлов в ней
                List<Path> fileNames = fileManager.getFileList();

                for (Path fileName : fileNames)
                    addNewZipEntry(zipOutputStream, source, fileName); //Добавляем каждый файл в архив

            } else if (Files.isRegularFile(source)) {
                addNewZipEntry(zipOutputStream, source.getParent(), source.getFileName()); //Если архивируем отдельный файл, то нужно получить его папку и имя.

            } else {
                throw new PathlsNotFoundException();  //Если переданный source не папка и не файл, то бросаем исключение
            }
        }
    }

    /**
     * Метод, для создания нового потока
     *
     * @param zipOutputStream
     * @param filePath        путь к папке с файлом fileName
     * @param fileName        имя файла
     * @throws Exception
     */
    private void addNewZipEntry(ZipOutputStream zipOutputStream, Path filePath, Path fileName) throws Exception {

        Path fullPath = filePath.resolve(fileName);

        try (InputStream fileInputStream = Files.newInputStream(fullPath)) {

            ZipEntry zipEntry = new ZipEntry(fileName.toString());//новый элемент архива, нужен для того, чтобы несколько файлов, сжимаемые в архив, не слиплись вместе. В zipOutputStream можно записать только энтри

            zipOutputStream.putNextEntry(zipEntry); //добавление в поток архива созданный элемент архива.

            copyData(fileInputStream, zipOutputStream); //копируем данные из входящего потока в исходящий поток.

            zipOutputStream.closeEntry(); //Закрывает текущую запись ZIP и позиционирует поток для записи следующей записи.
        }
    }

    /**
     * Метод для копирования данных
     * @param in  входящий поток
     * @param out исходящий поток
     * @throws Exception
     */
    private void copyData(InputStream in, OutputStream out) throws Exception {
        byte[] buffer = new byte[8 * 1024];
        int len;
        while ((len = in.read(buffer)) > 0) {   //цикл, который: читает данные из InputStream, пока они там есть
            out.write(buffer, 0, len);       //и записывает их в zipOutputStream. write - Пишет набор байт в текущий ZipEntry.
        }
    }

    /**
     * Метод для возвращения списка файлов в архиве (вернее, списка свойств файлов(FileProperties))
     * @return список с элементами типа FileProperties
     * @throws Exception если содержимое zipFile - не файл
     */
    public List<FileProperties> getFilesList() throws Exception {

        if (!Files.isRegularFile(zipFile)) { //является ли содержимое zipFile обычным файлом.
            throw new WrongZipFileException(); //если содержимое zipFile - не файл
    }
        List<FileProperties> filesList = new ArrayList<>(); //список с элементами типа FileProperties, в него мы будем складывать свойства файлов.

        try (ZipInputStream zipInputStream =new ZipInputStream(Files.newInputStream(zipFile))) { //создание входящего потока

            ZipEntry zipEntry = zipInputStream.getNextEntry(); //Проход по всем элементам ZipEntry потока zipInputStream.

            while (zipEntry != null){
                //Поля "размер" и "сжатый размер" не известны, пока элемент не будет прочитан
                //Вычитаем его в какой-то выходной поток
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); //Создание временного буфера, для
                copyData(zipInputStream, byteArrayOutputStream); //для каждого элемента zipEntry вычитаем его содержимое, иначе у нас не будет информации о его размере

                FileProperties fileProperties = new FileProperties(zipEntry.getName(), zipEntry.getSize(), zipEntry.getCompressedSize(), zipEntry.getMethod()); //получаем имя, размер, размер сжатия, метод
                filesList.add(fileProperties); //добавляем созданный объект Свойств (fileProperties) в список с элементами типа FileProperties
                zipEntry = zipInputStream.getNextEntry(); //Начинает запись новой записи ZIP-файла во входящий поток
            }
        }
        return filesList;
    }

    /**
     * Метод для распаковки
     * @param outputFolder путь, куда мы будем распаковывать наш архив
     * @throws Exception WrongZipFileException (архива не существует)
     */
    public void extractAll(Path outputFolder) throws Exception{

        if(!Files.isRegularFile(zipFile)){     //Проверяем, существует ли zip файл
            throw new WrongZipFileException(); //если архив не существует
        }

        try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(zipFile))) { //создаём новый входящий поток

            if (Files.notExists(outputFolder));    //Проверяем, существует ли папка для архивации
            Files.createDirectories(outputFolder); //Если outputFolder не существует, то её нужно создать

            ZipEntry zipEntry = zipInputStream.getNextEntry();  //Проходимся по содержимому zip потока(файла)

            while (zipEntry != null) { //пока zipEntry не нулл
                String fileName = zipEntry.getName();
                Path fileFullName = outputFolder.resolve(fileName); //Преобразует заданную строку пути в Path

                //Создаём необходимые папки
                Path parent = fileFullName.getParent(); //возвращает родительский путь
                if (Files.notExists(parent)) //проверяем, существует ли папка
                    Files.createDirectories(parent); //если нет, то создаём

                try (OutputStream outputStream = Files.newOutputStream(fileFullName)) { //создаём новый исходящий поток
                    copyData(zipInputStream, outputStream); //копируем данные из входящего потока в исходящий поток.
                }
                zipEntry = zipInputStream.getNextEntry(); //Начинает запись новой записи ZIP-файла во входящий поток
            }
        }
    }

    /**
     * Метод для удаления файла из архива.
     * Чтобы что-то удалить из архива, нужно создать новый архив -> переписать в него всё, кроме удаляемых файлов ->
     * заменить старый архив вновь созданным).
     * @param pathList в него будет передаваться список относительных путей ( - путь, относительно какой-то папки) на файлы внутри архива.
     * @throws Exception WrongZipFileException - если файл архива не существует
     */
    public void removeFiles(List<Path> pathList) throws Exception {
        if (!Files.isRegularFile(zipFile)) { //проверяем, существует ли zip файл
            throw new WrongZipFileException();
        }
        Path tempZipFile = Files.createTempFile(null, null); //создаём временный файл архива в папке

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(tempZipFile))) { //Создаём исходящий zip поток для временного файла
            try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(zipFile))) { //создание входящего потока

                ZipEntry zipEntry = zipInputStream.getNextEntry(); //Проходимся по всем файлам оригинального архива

                while (zipEntry !=null) {   //пока новый элемент архива не нулл
                    Path archivedFile = Paths.get(zipEntry.getName()); //получаем путь к файлу

                    //в случае, если файла нет в списке на удаление, то переносим его в новый архив.
                    if (!pathList.contains(archivedFile)) {
                        String fileName = zipEntry.getName();
                        zipOutputStream.putNextEntry(new ZipEntry((fileName))); //Начинает запись новой записи ZIP-файла в исходящий поток

                        copyData(zipInputStream, zipOutputStream); //копируем данные из входящего потока в исходящий поток.

                        zipOutputStream.closeEntry(); //Закрывает текущую запись ZIP и позиционирует поток для записи следующей записи.
                        zipInputStream.closeEntry(); //Закрывает текущую запись ZIP и позиционирует поток для записи следующей записи.

                        //в случае, если файл есть в списке на удаление
                    } else {
                        ConsoleHelper.writeMessage(String.format("Файл '%s' удалён из архива", archivedFile.toString()));
                    }
                    zipEntry = zipInputStream.getNextEntry();
                }
            }
        }
        Files.move(tempZipFile, zipFile, StandardCopyOption.REPLACE_EXISTING); //Перемещаем временный файл на место оригинального.
    }

    /**
     * Метод вызывает removeFiles(), создавая список из одного элемента.
     * @param path
     * @throws Exception
     */
    public void removeFile(Path path) throws Exception{
        removeFiles(Collections.singletonList(path)); //singletonList() используется для возврата неизменяемого списка, содержащего только указанный объект

    }

    /**
     * Метод для добавления файла в архив (создаём временный архив -> переписываем в него всё содержимое старого архива ->
     * добавляем новые файлы -> заменяем старый архив на новый)
     * @param absolutePathList список абсолютный путей, добавляемых файлов.
     * @throws Exception
     */
    public void addFiles(List<Path> absolutePathList) throws Exception{

        if (!Files.isRegularFile(zipFile)) { //проверяем, существует ли zip файл
            throw new WrongZipFileException();
        }

        Path tempZipFile = Files.createTempFile(null, null); //создаём временный файл архива в папке
        List<Path> archiveFiles = new ArrayList<>(); //какой-нибудь локальный список

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(tempZipFile))) { //Создаём исходящий zip поток для временного файла
            try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(zipFile))) { //создание входящего потока

                ZipEntry zipEntry = zipInputStream.getNextEntry(); //Проходимся по всем файлам оригинального архива

                while (zipEntry !=null) {   //пока новый элемент архива не нулл
                    String fileName = zipEntry.getName();
                    archiveFiles.add(Paths.get(fileName));

                    zipOutputStream.putNextEntry(new ZipEntry((fileName))); //Начинает запись новой записи ZIP-файла в исходящий поток
                    copyData(zipInputStream, zipOutputStream); //копируем данные из входящего потока в исходящий поток.

                    zipInputStream.closeEntry(); //Закрывает текущую запись ZIP и позиционирует поток для записи следующей записи.
                    zipOutputStream.closeEntry(); //Закрывает текущую запись ZIP и позиционирует поток для записи следующей записи.

                    zipEntry = zipInputStream.getNextEntry();
                }
            }
            //Архивируем новые файлы
            for (Path file : absolutePathList) { //Проходимся по списку добавляемых файлов
                if (Files.isRegularFile(file)) { //Для каждого файла проверяем, есть ли он на диске и является ли он обычным файлом
                    if (archiveFiles.contains(file.getFileName())) //Проверяем есть ли добавляемый файл уже в архиве
                        ConsoleHelper.writeMessage(String.format("Файл '%s' уже существует в архиве", file.toString())); //Если есть -> выводим нужное сообщение
                    else {
                        addNewZipEntry(zipOutputStream, file.getParent(), file.getFileName()); //если добавляемого файла нет в архиве, то добавляем его.
                        ConsoleHelper.writeMessage(String.format("Файл '%s' добавлен в архив", file.toString()));
                    }
                } else
                    throw new PathlsNotFoundException();
            }
        }

        Files.move(tempZipFile, zipFile, StandardCopyOption.REPLACE_EXISTING); //Перемещаем временный файл на место оригинального.
    }

    /**
     * Метод вызывает  addFile(), создавая список из одного элемента.
     * @param absolutePath абсолютный путь
     * @throws Exception
     */
    public void addFile(Path absolutePath) throws Exception{
        addFiles(Collections.singletonList(absolutePath)); //singletonList() используется для возврата неизменяемого списка, содержащего только указанный объект
    }

}

