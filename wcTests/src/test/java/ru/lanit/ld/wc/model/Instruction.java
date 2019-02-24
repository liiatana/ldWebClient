package ru.lanit.ld.wc.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ru.lanit.ld.wc.appmanager.ApplicationManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class Instruction {

    private int[] receiverID;//": 40808836,
    private String text;//"text": "Прошу согласовать документ.",
    private String subject;       // "subject": "На согласование",
    private String comment;      //  "comment": null,
    private int documentId;      // "fileIds": null,
    private int execAuditorID;//    "execAuditorID": null,
    private int initiatorID;//      "initiatorID": 1000,
    private int reportReceiverID;  //     "reportReceiverID":1000,
    private LocalDateTime startDate;//       "startDate": "2018-11-22T18:50:39.000Z",
    private LocalDateTime executionDate;//      "executionDate": null,
    private int execInterval;//      "execInterval": null
    private int execIntervalType;//      "execInterval": null
    private int sendType;// 1/0 = последовательная/параллельная
    //private String reportflag;
    private boolean control;
    private boolean withExecutive;
    private boolean reportToExecutive;
    private int[] fileIds;      // "fileIds": null,

    private int instructionTypeId;
    private int operationTypeId;

    //private boolean hasPlugin; //
    //public String signServiceUrl;

    private int InstructionId;

    public Instruction(JsonElement parsed, int InstructionNum) {
            //тип сообщения
       // this.instructionTypeId=
        //        parsed.getAsJsonObject().getAsJsonArray("items").get(0).getAsJsonObject().get("instructionType").getAsJsonObject().get("id").getAsInt();
            //Id сообщения
        this.InstructionId=
                parsed.getAsJsonObject().getAsJsonArray("items").get(InstructionNum).getAsJsonObject().get("instruction").getAsJsonObject().get("id").getAsInt();

    }

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
        this.subject = String.format("%s", type.getName());
        this.comment = String.format("%s", type.getName());
        // "fileIds": null,
        //"execAuditorID": null,

        //this.reportflag = type.getReportFlag();
        // "initiatorID": 1000,

        // "reportReceiverID": 1000,
        //this.reportReceiverID=

        //"startDate": "2018-12-02T18:50:39.000Z",
        this.startDate = LocalDateTime.now();//.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));

        //"executionDate": null,
        //"execInterval": null
        this.operationTypeId=type.getOperationID();



    }


    public int[] getReceiverID() {
        return receiverID;
    }

    public Instruction withReceiverID(int[] receiverID) {
        this.receiverID = receiverID;
        return this;
    }

    public int getOperationTypeId() {
        return operationTypeId;
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

    public Instruction withExecAuditorID(int[] execAuditorID) {
        this.control=true;
        this.execAuditorID = execAuditorID[0];
        return this;
    }

    public int getInitiatorID() {
        return initiatorID;
    }

    public Instruction withInitiatorID(int[] initiatorID) {
        this.initiatorID = initiatorID[0];
        if (this.reportReceiverID  == 0 ) {this.reportReceiverID=this.initiatorID;}
        return this;
    }

    public int getReportReceiverID() {
        return reportReceiverID;
    }

    public Instruction withReportReceiverID(int[] reportReceiverID) {
        this.reportReceiverID = reportReceiverID[0];
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

    public Instruction withExecutionDate(LocalDateTime executionDate, int execIntervalType) {
        this.executionDate = executionDate;
        //this.execIntervalType = execIntervalType;
        return this;
    }

    public boolean isWithExecutive() {
        return withExecutive;
    }

    public Instruction setWithExecutive(boolean withExecutive) {
        this.withExecutive = withExecutive;
        return this;
    }

    public boolean isReportToExecutive() {
        return reportToExecutive;
    }

    public Instruction setReportToExecutive(boolean reportToExecutive) {
        this.reportToExecutive = reportToExecutive;
        return this;
    }

    public int getSendType() {
        return sendType;
    }

    public Instruction withSendType(int sendType) {
        this.sendType = sendType;
        return this;
    }


    public int getInstructionId() {
        return InstructionId;
    }

    public Instruction withInstructionId(int instructionId) {
        InstructionId = instructionId;
        return this;
    }

    public JsonObject toJson(boolean send) {
        JsonObject mainInstruction = new JsonObject();
        JsonObject jsonInstruction = new JsonObject();

        mainInstruction.addProperty("receiverID", this.receiverID[0]);
        mainInstruction.addProperty("subject", this.subject);
        mainInstruction.addProperty("text", this.text+String.format("_%sZ", this.startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.000"))));

        mainInstruction.addProperty("comment", this.comment);

        if (this.fileIds == null) {
            mainInstruction.add("fileIds", null);
        } else {
            mainInstruction.addProperty("fileIds", this.fileIds[0]);
        }

        mainInstruction.addProperty("execAuditorID", this.execAuditorID);
        mainInstruction.addProperty("initiatorID", this.initiatorID);

        if (!this.control) {
            mainInstruction.addProperty("reportReceiverID", 0);
        }else if (this.reportReceiverID != 0) {
            mainInstruction.addProperty("reportReceiverID", this.reportReceiverID);
        } else {
            mainInstruction.addProperty("reportReceiverID", this.initiatorID);
        }



        if (this.startDate == null) {
            mainInstruction.add("startDate", null);
        } else {
            mainInstruction.addProperty("startDate", String.format("%sZ", this.startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.000"))));
        }


        if (this.executionDate == null) {
            mainInstruction.add("executionDate", null);
        } else {
            mainInstruction.addProperty("executionDate", String.format("%sZ", this.executionDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.000"))));
            mainInstruction.addProperty("execIntervalType", this.execIntervalType);
        }


        mainInstruction.add("execInterval", null);

        if (this.execAuditorID == 0) {
            mainInstruction.add("execAuditorID", null);
        } else {
            mainInstruction.addProperty("execAuditorID", this.execAuditorID);
        }


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

        if (send){
            jsonInstruction.addProperty("doEdit", false);
            jsonInstruction.add("instructionId", null);
            jsonInstruction.addProperty("operationTypeId", this.operationTypeId);
            jsonInstruction.add("scrInstructionId", null);
            jsonInstruction.add("reportId", null);
        }

        jsonInstruction.add("mainInstruction", mainInstruction);

        if(this.receiverID.length>1){

            JsonArray coExecutorInstruction=new JsonArray();

            JsonObject coExecFirst= mainInstruction.deepCopy();
            coExecFirst.remove("receiverID");
            coExecFirst.addProperty("receiverID", this.receiverID[1]);
            coExecutorInstruction.add(coExecFirst);


            JsonObject coExecOthers= mainInstruction.deepCopy();
            //coExecFirst.remove("receiverID");

            if(this.withExecutive && this.reportToExecutive){
                coExecOthers.remove("reportReceiverID");
                coExecOthers.addProperty("reportReceiverID", this.receiverID[1]);
            }

            for (int i = 2; i < receiverID.length; i++) {
                coExecOthers.remove("receiverID");
                coExecOthers.addProperty("receiverID", this.receiverID[i]);
                coExecutorInstruction.add(coExecOthers);
            }

            jsonInstruction.add("coExecutorInstruction", coExecutorInstruction);
        }

        JsonObject request = new JsonObject();
        request.add("request", jsonInstruction);
        return request;
    }


    public String toString() {
        return "Instruction: {" +
                "receiverID=" + Arrays.toString(receiverID) +
                ", text='" + text + '\'' +
                ", subject='" + subject + '\'' +
                ", comment='" + comment + '\'' +
                ", documentId=" + documentId +
                ", execAuditorID=" + execAuditorID +
                ", initiatorID=" + initiatorID +
                ", reportReceiverID=" + reportReceiverID +
                ", startDate=" + startDate +
                ", executionDate=" + executionDate +
                ", execInterval=" + execInterval +
                ", sendType=" + sendType +
                ", control=" + control +
                ", withExecutive=" + withExecutive +
                ", reportToExecutive=" + reportToExecutive +
                ", fileIds=" + Arrays.toString(fileIds) +
                ", instructionTypeId=" + instructionTypeId +
                ", operationTypeId=" + operationTypeId +
                '}';
    }


}
