/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.util.Date;

/**
 *
 * @author HuynhBao
 */
public class QuizDTO {
    private int quizID;
    private SubjectDTO subject;
    private Date startTime;
    private Date endTime;
    private int numOfCorrect;
    private float points;
    private String userID;

    public QuizDTO() {
    }

    public QuizDTO(int quizID, SubjectDTO subject, Date startTime, Date endTime, int numOfCorrect, float points, String userID) {
        this.quizID = quizID;
        this.subject = subject;
        this.startTime = startTime;
        this.endTime = endTime;
        this.numOfCorrect = numOfCorrect;
        this.points = points;
        this.userID = userID;
    }
    
    
    /*public void add(SubjectDTO subject) {
        if (this.quiz == null) {
            this.quiz = new HashMap<>();
        }
        this.quiz.remove(subject.getSubjectID());
        this.quiz.put(subject.getSubjectID(), subject);
    }*/

    public int getQuizID() {
        return quizID;
    }

    public void setQuizID(int quizID) {
        this.quizID = quizID;
    }

    public SubjectDTO getSubject() {
        return subject;
    }

    public void setSubject(SubjectDTO subject) {
        this.subject = subject;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getNumOfCorrect() {
        return numOfCorrect;
    }

    public void setNumOfCorrect(int numOfCorrect) {
        this.numOfCorrect = numOfCorrect;
    }

    public float getPoints() {
        return points;
    }

    public void setPoints(float points) {
        this.points = points;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    
    
}
