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

    public Report( Instruction instruction, UserInfo initiator, UserInfo receiver) {
        this.comment=instruction.getComment();
        this.completionTypeId=1500;
        this.documentId = 0;
        this.executionDate = LocalDateTime.now();
        //this.fileIds
        this.initiatorID=initiator.getId();
        //this.operationDocumentID
        this.operationTypeId=instruction.getOperationTypeId();
        this.receiverId=receiver.getId();
        //this.reportId
        this.subject=instruction.getSubject();
        this.text=instruction.getText();
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

    
}
