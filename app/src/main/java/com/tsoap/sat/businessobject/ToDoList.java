package com.tsoap.sat.businessobject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by nisheeth on 01/11/15.
 */
public class ToDoList {

    private UserProfile user;
    private String taskId;
    private String taskTitle;
    private String taskDesc;
    private String hasSubTasks;
    private String parentTaskId;
    private String isTaskComplete;
    private Date taskCreatedOn;
    private Date taskScheduledOn;
    private Byte[] Attachment;


    public Byte[] getAttachment() {
        return Attachment;
    }

    public void setAttachment(Byte[] attachment) {
        Attachment = attachment;
    }

    public UserProfile getUser() {
        return user;
    }

    public void setUser(UserProfile user) {
        this.user = user;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public String getHasSubTasks() {
        return hasSubTasks;
    }

    public void setHasSubTasks(String hasSubTasks) {
        this.hasSubTasks = hasSubTasks;
    }

    public String getParentTaskId() {
        return parentTaskId;
    }

    public void setParentTaskId(String parentTaskId) {
        this.parentTaskId = parentTaskId;
    }

    public String getIsTaskComplete() {
        return isTaskComplete;
    }

    public void setIsTaskComplete(String isTaskComplete) {
        this.isTaskComplete = isTaskComplete;
    }

    public Date getTaskCreatedOn() {
        return taskCreatedOn;
    }

    public void setTaskCreatedOn(Date taskCreatedOn) {
        this.taskCreatedOn = taskCreatedOn;
    }

    public Date getTaskScheduledOn() {
        return taskScheduledOn;
    }

    public void setTaskScheduledOn(Date taskScheduledOn) {
        this.taskScheduledOn = taskScheduledOn;
    }

    public JSONObject getJsonObject( ) throws JSONException {

        JSONObject jsonTODOLIST = new JSONObject();
        jsonTODOLIST.put("USER", this.getUser());
        jsonTODOLIST.put("TASKID", this.getTaskId());
        jsonTODOLIST.put("TASKTITLE", this.getTaskTitle());
        jsonTODOLIST.put("TASKDESC", this.getTaskDesc());
        jsonTODOLIST.put("HASSUBTASK", this.getHasSubTasks());
        jsonTODOLIST.put("PARENTTASKID", this.getParentTaskId());
        jsonTODOLIST.put("ISTASKCOMPLETE", this.getIsTaskComplete());
        jsonTODOLIST.put("CREATEDON", this.getTaskCreatedOn());
        jsonTODOLIST.put("TASKSCHEDULEDON", this.getTaskScheduledOn());
        jsonTODOLIST.put("ATTACHMENT", this.getAttachment());
        return jsonTODOLIST;
    }
}
