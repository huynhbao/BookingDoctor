/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.util.List;

/**
 *
 * @author HuynhBao
 */
public class SubjectDTO {
    private String subjectID;
    private String name;
    private int numOfQuestion;
    private int time_limit;
    private boolean status;
    private List<QuestionDTO> question;

    public SubjectDTO() {
    }

    public SubjectDTO(String subjectID, String name, int numOfQuestion, int time_limit, boolean status, List<QuestionDTO> question) {
        this.subjectID = subjectID;
        this.name = name;
        this.numOfQuestion = numOfQuestion;
        this.time_limit = time_limit;
        this.status = status;
        this.question = question;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTime_limit() {
        return time_limit;
    }

    public void setTime_limit(int time_limit) {
        this.time_limit = time_limit;
    }

    public List<QuestionDTO> getQuestion() {
        return question;
    }

    public void setQuestion(List<QuestionDTO> question) {
        this.question = question;
    }

    public int getNumOfQuestion() {
        return numOfQuestion;
    }

    public void setNumOfQuestion(int numOfQuestion) {
        this.numOfQuestion = numOfQuestion;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
