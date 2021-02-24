<%-- 
    Document   : update
    Created on : Jan 24, 2021, 10:49:18 AM
    Author     : HuynhBao
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Question Page</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
              integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
              crossorigin="anonymous">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="menu.jsp"/>
        <div class="container py-5" style="margin-top: 140px;">
            <form action="updateQuestion" method="POST" id="action-form">
                <input type="hidden" name="txtQuestionID" value="${requestScope.QUESTION.questionID}" />
                <div class="form-group">
                    <label for="txtName">Subject</label>
                    <div class="input-group mb-3">
                        <div class="input-group-prepend">
                            <label class="input-group-text" for="cbSubject">Subject</label>
                        </div>
                        <select class="custom-select" id="cbSubject" name="cbSubject">
                            <c:forEach items="${requestScope.LIST_SUBJECT}" var="subject">
                                <option ${requestScope.QUESTION.subjectID == subject.subjectID ? 'selected' : ''} value="${subject.subjectID}">${subject.subjectID} - ${subject.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="txtName">Question Name</label>
                    <input type="text" class="form-control" name="txtName" placeholder="Enter Question Name" value="<c:out value="${requestScope.QUESTION.name}" />">
                    <small class="text-danger">
                        ${requestScope.ERROR_QUESTION_NAME}
                    </small>
                </div>
                <div class="form-group">
                    <label for="txtAnswer1">Answer A</label>
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <div class="input-group-text">
                                <input type="radio" name="radioCorrect" value="1" ${requestScope.QUESTION.correctAnswer == 1 ? 'checked' : ''}>
                            </div>
                        </div>
                        <input type="text" class="form-control" name="txtAnswerA" placeholder="Enter Answer A" value="<c:out value="${requestScope.QUESTION.answerA}" />">
                        <br>
                        <small class="text-danger">
                            ${requestScope.ERROR_ANSWER_A}
                        </small>
                    </div>
                </div>
                <div class="form-group">
                    <label for="txtAnswerB">Answer B</label>
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <div class="input-group-text">
                                <input type="radio" name="radioCorrect" value="2" ${requestScope.QUESTION.correctAnswer == 2 ? 'checked' : ''}>
                            </div>
                        </div>
                        <input type="text" class="form-control" name="txtAnswerB" placeholder="Enter Answer B" value="<c:out value="${requestScope.QUESTION.answerB}" />">
                        <br>
                        <small class="text-danger">
                            ${requestScope.ERROR_ANSWER_B}
                        </small>
                    </div>
                </div>
                <div class="form-group">
                    <label for="txtAnswerC">Answer C</label>
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <div class="input-group-text">
                                <input type="radio" name="radioCorrect" value="3" ${requestScope.QUESTION.correctAnswer == 3 ? 'checked' : ''}>
                            </div>
                        </div>
                        <input type="text" class="form-control" name="txtAnswerC" placeholder="Enter Answer C" value="<c:out value="${requestScope.QUESTION.answerC}" />">
                        <br>
                        <small class="text-danger">
                            ${requestScope.ERROR_ANSWER_C}
                        </small>
                    </div>
                </div>
                <div class="form-group">
                    <label for="txtAnswerD">Answer D</label>
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <div class="input-group-text">
                                <input type="radio" name="radioCorrect" value="4" ${requestScope.QUESTION.correctAnswer == 4 ? 'checked' : ''}>
                            </div>
                        </div>
                        <input type="text" class="form-control" name="txtAnswerD" placeholder="Enter Answer D" value="<c:out value="${requestScope.QUESTION.answerD}" />">
                        <br>
                        <small class="text-danger">
                            ${requestScope.ERROR_ANSWER_D}
                        </small>
                    </div>
                </div>
                <div class="form-group">
                    <label for="txtName">Subject</label>
                    <div class="input-group mb-3">
                        <div class="input-group-prepend">
                            <label class="input-group-text" for="cbStatus">Status</label>
                        </div>
                        <select class="form-control border-primary" name="cbStatus">
                            <option ${requestScope.QUESTION.status == true ? 'selected' : ''} value="active">Active</option>
                            <option ${requestScope.QUESTION.status == false ? 'selected' : ''} value="inactive">Inactive</option>

                        </select>
                    </div>
                </div>

                <div class="form-group text-center">
                    ${ERROR_ANSWER}
                    <br>
                    <button type="submit" class="btn btn-primary btn-lg btn-radius">Update</button>
                </div>

            </form>

        </div>
        <jsp:include page="footer.jsp"/>
    </body>
</html>
