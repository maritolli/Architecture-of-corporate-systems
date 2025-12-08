package com.example.library.jms;

public class ChangeEvent {

    private String entityName;
    private String operation;
    private Long entityId;
    private String oldValue;
    private String newValue;

    public ChangeEvent() {
    }

    public ChangeEvent(String entityName, String operation, Long entityId, String oldValue, String newValue) {
        this.entityName = entityName;
        this.operation = operation;
        this.entityId = entityId;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }
}
