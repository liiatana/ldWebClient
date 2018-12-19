package ru.lanit.ld.wc.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Instruction {

    public int[] receiverID;//": 40808836,
    public String text;//"text": "Прошу согласовать документ.",
    public String subject;       // "subject": "На согласование",
    public String comment;      //  "comment": null,
    public int documentId;      // "fileIds": null,
    public int execAuditorID;//    "execAuditorID": null,
    public int initiatorID;//      "initiatorID": 1000,
    public int reportReceiverID;  //     "reportReceiverID":1000,
    public LocalDateTime startDate;//       "startDate": "2018-11-22T18:50:39.000Z",
    public LocalDateTime executionDate;//      "executionDate": null,
    public int execInterval;//      "execInterval": null
    public int sendType;//
    public String reportflag;
    public boolean control;
    public boolean withExecutive;
    public boolean reportToExecutive;
    public int[] fileIds;      // "fileIds": null,

    public int instructionTypeId;

    //private boolean hasPlugin; //
    //public String signServiceUrl;

    public Instruction(instructionType type) {

        // this.hasPlugin=false;
        // this.signServiceUrl=app.properties.getProperty("web.securityUrl");

        this.instructionTypeId = type.getId();
        //this.documentId = 0;
        this.sendType = 0;
        this.control = type.getUseControl();
        this.withExecutive = false;
        this.reportToExecutive = false;
        //"receiverID": 40808836,
        this.text = type.getName();
        this.subject = String.format("Тема: %s", type.getName());
        this.comment = String.format("Комментарий: %s", type.getName());
        // "fileIds": null,
        //"execAuditorID": null,

        this.reportflag = type.getReportFlag();
        // "initiatorID": 1000,

        // "reportReceiverID": 1000,

        //"startDate": "2018-12-02T18:50:39.000Z",
        this.startDate = LocalDateTime.now();//.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));

        //"executionDate": null,
        //"execInterval": null


    }


    public int[] getReceiverID() {
        return receiverID;
    }

    public Instruction withReceiverID(int[] receiverID) {
        this.receiverID = receiverID;
        return this;
    }

    public String getText() {
        return text;
    }

    public Instruction withText(String text) {
        this.text = text;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public Instruction withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public Instruction withComment(String comment) {
        this.comment = comment;
        return this;
    }

    public int getFileIds() {
        return documentId;
    }

    public Instruction withFileIds(int fileIds) {
        this.documentId = fileIds;
        return this;
    }

    public int getExecAuditorID() {
        return execAuditorID;
    }

    public Instruction withExecAuditorID(int execAuditorID) {
        this.execAuditorID = execAuditorID;
        return this;
    }

    public int getInitiatorID() {
        return initiatorID;
    }

    public Instruction withInitiatorID(int initiatorID) {
        this.initiatorID = initiatorID;
        return this;
    }

    public int getReportReceiverID() {
        return reportReceiverID;
    }

    public Instruction withReportReceiverID(int reportReceiverID) {
        this.reportReceiverID = reportReceiverID;
        return this;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public Instruction withStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public LocalDateTime getExecutionDate() {
        return executionDate;
    }

    public Instruction withExecutionDate(LocalDateTime executionDate) {
        this.executionDate = executionDate;
        return this;
    }

    public int getExecInterval() {
        return execInterval;
    }

    public Instruction withExecInterval(int execInterval) {
        this.execInterval = execInterval;
        return this;
    }

    public JsonObject toJson() {
        JsonObject mainInstruction = new JsonObject();

        mainInstruction.addProperty("receiverID", this.receiverID[0]);

        mainInstruction.addProperty("text", this.text+String.format("_%sZ", this.startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.000"))));

        mainInstruction.addProperty("comment", this.comment);

        if (this.fileIds == null) {
            mainInstruction.add("fileIds", null);
        } else {
            mainInstruction.addProperty("fileIds", this.fileIds[0]);
        }

        mainInstruction.addProperty("execAuditorID", this.execAuditorID);
        mainInstruction.addProperty("initiatorID", this.initiatorID);
        mainInstruction.addProperty("reportReceiverID", this.reportReceiverID);
        if (this.startDate == null) {
            mainInstruction.add("startDate", null);
        } else {
            mainInstruction.addProperty("startDate", String.format("%sZ", this.startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.000"))));
        }


        if (this.executionDate == null) {
            mainInstruction.add("executionDate", null);
        } else {
            mainInstruction.addProperty("executionDate", String.format("%sZ", this.executionDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.000"))));
        }

        if (this.execInterval == 0) {
            mainInstruction.add("execInterval", null);
        } else {
            mainInstruction.addProperty("execInterval", this.execInterval);
        }
        if (this.execAuditorID == 0) {
            mainInstruction.add("execAuditorID", null);
        } else {
            mainInstruction.addProperty("execAuditorID", this.execAuditorID);
        }
        if (this.reportReceiverID == 0) {
            mainInstruction.add("reportReceiverID", null);
        } else {
            mainInstruction.addProperty("reportReceiverID", this.reportReceiverID);
        }


        JsonObject jsonInstruction = new JsonObject();
        jsonInstruction.addProperty("instructionTypeId", this.instructionTypeId);

        if (this.documentId == 0) {
            jsonInstruction.add("documentId", null);
        } else {
            jsonInstruction.addProperty("documentId", this.documentId);
        }


        jsonInstruction.addProperty("sendType", this.sendType);
        jsonInstruction.addProperty("reportToExecutive", this.reportToExecutive);
        jsonInstruction.addProperty("withExecutive", this.withExecutive);
        jsonInstruction.addProperty("control", this.control);

        jsonInstruction.add("mainInstruction", mainInstruction);

        if(this.receiverID.length>1){
            JsonObject coExecutorInstruction = new JsonObject();
            coExecutorInstruction=mainInstruction;
            coExecutorInstruction.addProperty("receiverID", this.receiverID[1]);
            jsonInstruction.add("coExecutorInstruction", coExecutorInstruction);
        }

        JsonObject request = new JsonObject();
        request.add("request", jsonInstruction);
        return request;
    }

}
