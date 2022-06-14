package archi.exception;

/**
 * Исключение будет выкидываться, если:
 * 1)не можем найти путь, в который нужно распаковать архив;
 * 2)или путь к файлу, который хотим запаковать;
 * 3)или любой другой путь.
 */
public class PathlsNotFoundException extends Exception{
}
