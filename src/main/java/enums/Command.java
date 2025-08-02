package enums;

public enum Command {
    ADD("add"),
    UPDATE("update"),
    DELETE("delete"),
    MARK_IN_PROGRESS("mark-in-progress"),
    MARK_DONE("mark-done"),
    LIST_ALL("list"),
    LIST_DONE("list done"),
    LIST_TODO("list todo"),
    LIST_IN_PROGRESS("list in-progress"),
    QUIT("quit"),
    HELP("help");

    private final String stringValue;

    private Command(String command){
        stringValue = command;
    }

    public static Command fromString(String input) {
        for (Command cmd : Command.values()) {
            if (cmd.stringValue.equalsIgnoreCase(input)) {
                return cmd;
            }
        }
        throw new IllegalArgumentException("Unknown command: " + input);
    }

    public String getStringValue() {
        return stringValue;
    }
}
