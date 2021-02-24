<%-- 
    Document   : result
    Created on : Jan 25, 2021, 10:35:38 AM
    Author     : HuynhBao
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Result Page</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
              integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
              crossorigin="anonymous">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
        <style>
            @import url("https://fonts.googleapis.com/css2?family=Poppins:wght@200;400&display=swap");

            * {
                box-sizing: border-box;
            }

            body {
                background-color: #b8c6db;
                background-image: linear-gradient(315deg, #b8c6db 0%, #f5f7fa 100%);
                font-family: "Poppins", sans-serif;
                display: flex;
                align-items: center;
                justify-content: center;
                margin: 0;
            }

            .quiz-header {
                padding: 0px 50px;
            }

            .quiz-container {
                background-color: #fff;
                border-radius: 10px;
                box-shadow: 0 0 10px 2px rgba(100, 100, 100, 0.1);
                max-width: 95vw;
                overflow: hidden;
                margin-top: 140px;
            }

            .body-content {
                padding: 2rem;
            }

            h2 {
                padding: 1rem;
                text-align: center;
                margin: 0;
            }

            ul {
                list-style-type: none;
                padding: 0;
            }

            ul li {
                font-size: 1.2rem;
                margin: 1rem 0;
            }

            ul li label {
                cursor: pointer;
            }

            .footer {
                background-color: #bdb1ff;
                color: #fff;
                border: none;
                width: 100%;
                font-size: 1.1rem;
                font-family: inherit;
            }

            button:hover {
                background-color: #732d91;
            }

            button:focus {
                outline: none;
                background-color: #5e3370;
            }

            .quiz-panel {
                position: fixed;
                top: 150px;
                right: 39px;
                z-index: 98;
                padding: 21px;
                background-color: #8881ff;
                color: #fff;
                display: block;
                border-radius: 10px;
                box-shadow: 0 0 10px 2px rgba(100, 100, 100, 0.1);
                width: 180px;
            }

            .btn-finish {
                background-color: #ff3057;
                color: #fff;
            }

            .btn-finish:hover {
                background-color: #ff0b39;
                border: 1px solid #fff;
                color: #fff;
            }

            .list{
                justify-content: center;
            }
            .list ol{
                list-style: none;
                counter-reset: my-counter;
                margin: 0rem;
                padding:  0rem 0rem 0rem 1.5rem;
            }
            .list li{
                counter-increment: my-counter;
                border-left: solid 2px #cdcdcd;
                /*  border-bottom: solid 2px #cdcdcd; */
                padding-top: 2rem;
                padding-bottom: 1rem;
                /*display: grid;*/
            }
            .list li:before {
                content: counter(my-counter);
                font-weight: bold;
                background-color: #ff9e9e;
                border: 3px solid #ff788b;
                border-radius: 50px;
                font-size: 20px;
                padding: 0.8rem 1.4rem;
                color: #fff;
                margin-left: -2.0rem;
                margin-right: auto;
            }

            li.correct:before {
                background-color: #76ff93;
                border: 3px solid #baffb2;
            }

            .card{
                margin-left: 4rem;
                border-radius: 0px;
                box-shadow: 0 0 5px 1px #ddd;
                margin-top: -2.5rem;
            }
            .card-detail .card-header:after {
                right: 100%;
                border: solid transparent;
                content: " ";
                height: 0;
                width: 0;
                position: absolute;
                pointer-events: none;
                border-right-color: #bf9eff;
                border-width: 10px;
                top: 10px;
            }

            .card-bootstrap .card-header:after {
                right: 100%;
                border: solid transparent;
                content: " ";
                height: 0;
                width: 0;
                position: absolute;
                pointer-events: none;
                border-right-color: #7952b3;
                border-width: 10px;
                top: 10px;
            }
            .card .card-header{
                background-color: #8abe3e;
                color: #fff;
                font-size: 17px;
                border-top-left-radius: 0px;
                border-top-right-radius: 0px;
            }
            .btn-success{
                background-color: #8abe3e;
                color: #fff;
                border-color: #8abe3e;
                border-radius: 0px;
            }
            .card-detail .card-header, .card-detail .btn-success{
                background-color: #bf9eff;
                border-color: #bf9eff;
            }

            .correct-icon {
                color: #00ff66;
                font-size: 20px;
                margin-left: 5px;
            }
        </style>
    </head>
    <body>
        <jsp:include page="menu.jsp"/>
        <div class="container py-5">
            <div class="quiz-container">
                <div class="row">
                    <div class="col-md-12 body-content bg-white">
                        <h2 id="question">Your Result</h2>
                        <hr>
                        <div class="quiz-header d-flex justify-content-between">
                            <div>
                                <p><strong>Subject ID:</strong> ${fn:toUpperCase(requestScope.QUIZ.subject.subjectID)}</p>
                                <p><strong>Subject Name:</strong> ${requestScope.QUIZ.subject.name}</p>
                                <p><strong>Time:</strong> ${requestScope.QUIZ.subject.time_limit} minutes</p>
                                <fmt:formatDate var="startTime" pattern="dd-MM-yyyy HH:mm:ss" value="${requestScope.QUIZ.startTime}" />
                                <fmt:formatDate var="endTime" pattern="dd-MM-yyyy HH:mm:ss" value="${requestScope.QUIZ.endTime}" />
                                <p><strong>Start time:</strong> ${startTime}</p>
                                <p><strong>Submit time:</strong> ${endTime}</p>
                            </div>
                            <div>
                                <p><strong>Number of correct question:</strong> ${requestScope.QUIZ.numOfCorrect}/${requestScope.QUIZ.subject.numOfQuestion}</p>
                                <p><strong>Points:</strong> ${requestScope.QUIZ.points}/10</p>
                            </div>
                        </div>
                        <hr>
                    </div>
                </div>
                <div class="row justify-content-center">
                    <div class="col-10">
                        <h2 class="text-center">Quiz Detail</h2>
                        <div class="list">
                            <ol>
                                <c:forEach var="question" items="${requestScope.QUIZ.subject.question}" varStatus="counter">
                                    <li class="${question.userAnswer == question.correctAnswer ? 'correct' : ''}">
                                        <div class="card card-detail">
                                            <div class="card-header">
                                                ${question.name}
                                            </div>
                                            <div class="card-body">
                                                <p>A. ${question.answerA} <c:if test="${question.correctAnswer == 1}"><i class="fa fa-check correct-icon"></i></c:if></p>
                                                <p>B. ${question.answerB} <c:if test="${question.correctAnswer == 2}"><i class="fa fa-check correct-icon"></i></c:if></p>
                                                <p>C. ${question.answerC} <c:if test="${question.correctAnswer == 3}"><i class="fa fa-check correct-icon"></i></c:if></p>
                                                <p>D. ${question.answerD} <c:if test="${question.correctAnswer == 4}"><i class="fa fa-check correct-icon"></i></c:if></p>
                                                </div>
                                                <div class="card-footer">
                                                    <div class="clearfix">
                                                        <div class="float-left">
                                                            Your answer: 
                                                        <c:if test="${question.userAnswer == 0}">
                                                            Did not answer
                                                        </c:if>
                                                        <c:if test="${question.userAnswer == 1}">
                                                            A
                                                        </c:if>
                                                        <c:if test="${question.userAnswer == 2}">
                                                            B
                                                        </c:if>
                                                        <c:if test="${question.userAnswer == 3}">
                                                            C
                                                        </c:if>
                                                        <c:if test="${question.userAnswer == 4}">
                                                            D
                                                        </c:if>
                                                    </div>
                                                    <div class="float-right">

                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </li>
                                </c:forEach>
                            </ol>
                        </div>
                    </div>
                </div>
            </div>
    </body>
    <jsp:include page="footer.jsp"/>
</html>
