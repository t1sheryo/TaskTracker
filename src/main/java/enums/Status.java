package enums;

//status: The status of the task (todo, in-progress, done)

import lombok.Getter;

@Getter
public enum Status {
    TODO("TODO"),
    IN_PROGRESS("IN PROGRESS"),
    DONE("DONE");

    private final String stringValue;

    private Status(String stringValue) {
        this.stringValue = stringValue;
    }
}
