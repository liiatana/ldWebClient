package ru.lanit.ld.wc.model;

import java.time.LocalDateTime;

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


}
