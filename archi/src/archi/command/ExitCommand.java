package archi.command;

import archi.ConsoleHelper;

/**
 * Класс, отвечающий за команду ВЫХОД
 */
public class ExitCommand implements Command{
    @Override
    public void execute() throws Exception {
        ConsoleHelper.writeMessage("До встречи!");
    }
}
