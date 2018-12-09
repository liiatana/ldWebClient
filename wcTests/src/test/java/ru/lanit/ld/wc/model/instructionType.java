package ru.lanit.ld.wc.model;

public class instructionType {

    private int id;
    private String name;
    private int operationId;
    private String operation;
    private int interval;
    private String reportFlag;
    private boolean redirectAsControl;
    private boolean useControl;
    private boolean active;
    private int checkReportTypeNegative;
    private int checkReportTypePositive;

    public int[] receiverTypes;

    public instructionType() {
    }

    public instructionType(int id, String name, int operationId, String operation, int interval, String reportFlag, boolean redirectAsControl, boolean useControl, boolean active, int checkReportTypeNegative, int checkReportTypePositive, int[] receiverTypes) {
        this.id = id;
        this.name = name;
        this.operationId = operationId;
        this.operation = operation;
        this.interval = interval;
        this.reportFlag = reportFlag;
        this.redirectAsControl = redirectAsControl;
        this.useControl = useControl;
        this.active = active;
        this.checkReportTypeNegative = checkReportTypeNegative;
        this.checkReportTypePositive = checkReportTypePositive;
        this.receiverTypes = receiverTypes;
    }

    public int getId() {
        return id;

    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOperationId() {
        return operationId;
    }

    public void setOperationId(int operationId) {
        this.operationId = operationId;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String getReportFlag() {
        return reportFlag;
    }

    public void setReportFlag(String reportFlag) {
        this.reportFlag = reportFlag;
    }

    public boolean isRedirectAsControl() {
        return redirectAsControl;
    }

    public void setRedirectAsControl(boolean redirectAsControl) {
        this.redirectAsControl = redirectAsControl;
    }

    public boolean getUseControl() {
        return useControl;
    }

    public void setUseControl(boolean useControl) {
        this.useControl = useControl;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getCheckReportTypeNegative() {
        return checkReportTypeNegative;
    }

    public void setCheckReportTypeNegative(int checkReportTypeNegative) {
        this.checkReportTypeNegative = checkReportTypeNegative;
    }

    public int getCheckReportTypePositive() {
        return checkReportTypePositive;
    }

    public void setCheckReportTypePositive(int checkReportTypePositive) {
        this.checkReportTypePositive = checkReportTypePositive;
    }

    public int[] getReceiverTypes() {
        return receiverTypes;
    }

    public void setReceiverTypes(int[] receiverTypes) {
        this.receiverTypes = receiverTypes;
    }
}
