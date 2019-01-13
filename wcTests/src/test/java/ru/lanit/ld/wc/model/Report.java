package ru.lanit.ld.wc.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class Report {

    private String comment;
    private int completionTypeId;
    private int documentId;
    private LocalDateTime executionDate;
    private int[] fileIds;
    private int initiatorID;
    private int operationDocumentID;
    private int operationTypeId;
    private int receiverId;
    private int reportId;
    private String subject;
    private String text;

    private int InstructionId;

    public Report( Instruction instruction) {//UserInfo initiator, UserInfo receiver
        this.comment=instruction.getComment();
        this.completionTypeId=1500;
        this.documentId = 0;
        this.executionDate = LocalDateTime.now();
        //this.fileIds
        //this.initiatorID=initiator.getId();
        //this.operationDocumentID
        this.operationTypeId=instruction.getOperationTypeId();
        //this.receiverId=receiver.getId();
        //this.reportId
        this.subject=instruction.getSubject();
        this.text=instruction.getText();
        this.InstructionId=instruction.getInstructionId();
    }

    public Report( Report report) { //копирование объекта
        this.comment=report.getComment();
        this.completionTypeId=report.getCompletionTypeId();
        this.documentId = report.getDocumentId();
        this.executionDate = report.getExecutionDate();
        this.fileIds=report.getFileIds();
        //this.initiatorID=initiator.getId();
        this.operationDocumentID=report.getDocumentId();
        this.operationTypeId=report.getOperationTypeId();
        //this.receiverId=receiver.getId();
        this.reportId=report.getReportId();
        this.subject=report.getSubject();
        this.text=report.getText();
        this.InstructionId=0;

    }

    public Report withInitiatorID(int initiatorID) {
        this.initiatorID = initiatorID;
        return this;
    }

    public Report withReceiverId(int receiverId) {
        this.receiverId = receiverId;
        return this;
    }

    public int getInstructionId() {
        return InstructionId;
    }

    public Report withInstructionId(int instructionId) {
        InstructionId = instructionId;
        return this;
    }

    @Override
    public String toString() {
        return "Report{" +
                "comment='" + comment + '\'' +
                ", completionTypeId=" + completionTypeId +
                ", documentId=" + documentId +
                ", executionDate=" + executionDate +
                ", fileIds=" + Arrays.toString(fileIds) +
                ", initiatorID=" + initiatorID +
                ", operationDocumentID=" + operationDocumentID +
                ", operationTypeId=" + operationTypeId +
                ", receiverId=" + receiverId +
                ", reportId=" + reportId +
                ", subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    public JsonObject toJson() {

        JsonObject request = new JsonObject();

        request.addProperty("comment", this.comment);
        request.addProperty("completionTypeId", this.completionTypeId);

        if (this.documentId == 0) {
            request.add("documentId", null);
        } else {
            request.addProperty("documentId", this.documentId);
        }

        request.addProperty("executionDate", String.format("%sZ", this.executionDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.000"))));

        if (this.fileIds == null) {
            request.add("fileIds", null);
        } else {
            request.addProperty("fileIds", this.fileIds[0]);
        }

        request.addProperty("initiatorID", this.initiatorID);

        if (this.operationDocumentID == 0) {
            request.add("operationDocumentID", null);
        } else {
            request.addProperty("documentId", this.operationDocumentID);
        }

        request.addProperty("operationTypeId", this.operationTypeId);
        request.addProperty("receiverId", this.receiverId);
        request.add("reportId", null);


        request.addProperty("subject", this.subject);
        request.addProperty("text", this.text);

        return request;
    }


    public String getComment() {
        return comment;
    }

    public int getCompletionTypeId() {
        return completionTypeId;
    }

    public int getDocumentId() {
        return documentId;
    }

    public LocalDateTime getExecutionDate() {
        return executionDate;
    }

    public int[] getFileIds() {
        return fileIds;
    }

    public int getInitiatorID() {
        return initiatorID;
    }

    public int getOperationDocumentID() {
        return operationDocumentID;
    }

    public int getOperationTypeId() {
        return operationTypeId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public int getReportId() {
        return reportId;
    }

    public String getSubject() {
        return subject;
    }

    public String getText() {
        return text;
    }
}
