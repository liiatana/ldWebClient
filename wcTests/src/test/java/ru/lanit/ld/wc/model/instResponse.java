package ru.lanit.ld.wc.model;

public class instResponse {

    public int instructionId;
    public String message;

    public instResponse(int instructionId, String message) {
        this.instructionId = instructionId;
        this.message = message;
    }

    public instResponse() {
    }

    public int getInstructionId() {
        return instructionId;
    }

    public String getMessage() {
        return message;
    }

}
