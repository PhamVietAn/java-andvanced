package Ex03;

import java.util.HashMap;
import java.util.Map;

public class RemoteControl {

    private Map<Integer, Command> buttons = new HashMap<>();
    private Command lastCommand;

    public void setCommand(int slot, Command command) {
        buttons.put(slot, command);
        System.out.println("Da gan command cho nut " + slot);
    }

    public void pressButton(int slot) {

        Command command = buttons.get(slot);

        if (command != null) {
            command.execute();
            lastCommand = command;
        } else {
            System.out.println("Nut chua duoc gan.");
        }
    }

    public void undo() {

        if (lastCommand != null) {
            lastCommand.undo();
        } else {
            System.out.println("Khong co lenh de undo.");
        }

    }

}