package code;

public class ExceptionHandler {
    /**
     * Метод, выводящий в консоль краткое описание проблемы
     * @param e переданное исключение
     */
    public static void log(Exception e){
        System.out.println(e.toString());
    }

}
