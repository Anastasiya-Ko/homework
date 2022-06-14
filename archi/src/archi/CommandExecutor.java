package archi;

import archi.command.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс, для поиска и вызова команд.
 */
public class CommandExecutor {
    private static Map<Operation, Command> ALL_KNOWN_COMMANDS_MAP = new HashMap<>(); //хранилище команд

    //т.к хранилище создаётся один раз, то добавление значений лучше сделать в статическом блоке инициализации.
    static {
        ALL_KNOWN_COMMANDS_MAP.put(Operation.CREATE, new ZipCreateCommand());
        ALL_KNOWN_COMMANDS_MAP.put(Operation.ADD, new ZipAddCommand());
        ALL_KNOWN_COMMANDS_MAP.put(Operation.REMOVE, new ZipRemoveCommand());
        ALL_KNOWN_COMMANDS_MAP.put(Operation.EXTRACT, new ZipExtractCommand());
        ALL_KNOWN_COMMANDS_MAP.put(Operation.CONTENT, new ZipContentCommand());
        ALL_KNOWN_COMMANDS_MAP.put(Operation.EXIT, new ExitCommand());
    }
    private CommandExecutor() {} //запрет на явный вызова конструктора

    /**
     * Метод, берущий нужную команду из ALL_KNOWN_COMMANDS_MAP и вызывающий у неё метод execute()
     * @param operation энамка команд
     * @throws Exception
     */
    public static void execute(Operation operation) throws Exception{
        ALL_KNOWN_COMMANDS_MAP.get(operation).execute();
    }
}
