package archi;

/**
 * Класс, отвечаеющий за свойства каждого файла в архиве: имя файла, размер до и после сжатия, метод сжатия.
 */
public class FileProperties {
    /**
     * имя файла
     */
    private String name;

    /**
     * размер файла, в байтах
     */
    private long size;

    /**
     * размер файла после сжатия, в байтах
     */
    private long compressedSize;

    /**
     * метод компрессии
     */
    private int compressionMethod;

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    public long getCompressedSize() {
        return compressedSize;
    }

    public int getCompressionMethod() {
        return compressionMethod;
    }

    public FileProperties(String name, long size, long compressedSize, int compressionMethod) {
        this.name = name;
        this.size = size;
        this.compressedSize = compressedSize;
        this.compressionMethod = compressionMethod;
    }

    /**
     * Метод, вычисляющий степень сжатия
     * @return степень сжатия
     */
    public long getCompressionRatio(){
        return 100-((compressedSize * 100) / size);
    }

    @Override
    public String toString() {

        //Строим красивую строку из свойств
        StringBuilder builder = new StringBuilder();
        builder.append(name);
        if (size > 0) {
            builder.append("\t");
            builder.append(size / 1024);
            builder.append(" Kb (");
            builder.append(compressedSize / 1024);
            builder.append(" Kb) сжатие: ");
            builder.append(getCompressionMethod());
            builder.append("%");
        }
        return builder.toString();
    }
}
