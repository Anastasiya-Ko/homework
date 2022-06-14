package archi.command;


public interface Command {
    /**
     * Метод, для выполнения команды(execute-выполнить)
     * @throws Exception
     */
    void execute() throws Exception;
}
