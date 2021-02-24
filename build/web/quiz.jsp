<%-- 
    Document   : quiz
    Created on : Jan 23, 2021, 3:55:42 PM
    Author     : HuynhBao
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quiz Page</title>
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
                height: 100vh;
                overflow: hidden;
                margin: 0;
            }

            .quiz-container {
                background-color: #fff;
                border-radius: 10px;
                box-shadow: 0 0 10px 2px rgba(100, 100, 100, 0.1);
                max-width: 95vw;
                overflow: hidden;
            }

            .body-content {
                padding: 4rem;
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
                height: 90px;
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

            .question-panel {
                position: fixed;
                top: 150px;
                left: 39px;
                z-index: 98;
                padding: 14px;
                background-color: #8881ff;
                color: #fff;
                border-radius: 10px;
                box-shadow: 0 0 10px 2px rgba(100, 100, 100, 0.1);
                width: 220px;
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

            .btn-paging {
                border-radius: 10px;
                width: 125px;
                color: #7359ff;
                background-color: #fff;
                margin: 24px 80px 0px 100px
            }

            .btn-paging-question {
                background-color: #fff;
                border: 1px solid #000;
                margin: 5px;
                width: 38px;
            }

            .btn-paging-question-current {
                background-color: #85bbff;
                pointer-events: none;
                cursor: default;
                text-decoration: none;
            }

            [type="radio"]:checked,
            [type="radio"]:not(:checked) {
                position: absolute;
                left: -9999px;
            }
            [type="radio"]:checked + label,
            [type="radio"]:not(:checked) + label
            {
                position: relative;
                padding-left: 28px;
                cursor: pointer;
                line-height: 20px;
                display: inline-block;
                color: #666;
            }
            [type="radio"]:checked + label:before,
            [type="radio"]:not(:checked) + label:before {
                content: '';
                position: absolute;
                left: 0;
                top: 0;
                width: 18px;
                height: 18px;
                border: 1px solid #ddd;
                border-radius: 100%;
                background: #fff;
            }
            [type="radio"]:checked + label:after,
            [type="radio"]:not(:checked) + label:after {
                content: '';
                width: 12px;
                height: 12px;
                background: #b6b1ff;
                position: absolute;
                top: 3px;
                left: 3px;
                border-radius: 100%;
                -webkit-transition: all 0.2s ease;
                transition: all 0.2s ease;
            }
            [type="radio"]:not(:checked) + label:after {
                opacity: 0;
                -webkit-transform: scale(0);
                transform: scale(0);
            }
            [type="radio"]:checked + label:after {
                opacity: 1;
                -webkit-transform: scale(1);
                transform: scale(1);
            }
        </style>

    </head>
    <body>
        <jsp:include page="menu.jsp"/>
        <div class="container py-5">
            <div class="row quiz-panel text-center">
                <div>
                    Time left: <span id="timer"></span>
                </div>
                <br>
                <div>
                    <form action="submit" method="POST" id="submit-form">
                        <input type="hidden" name="txtSubjectID" value="${param.txtSubjectID}"/>
                        <button type="submit" class="btn btn-finish btn-radius">Finish</button>
                    </form>
                </div>
            </div>

            <div class="row question-panel text-center">
                <c:forEach begin="1" end="${requestScope.noOfPages}" var="i">
                    <div>
                        <a class="btn btn-paging-question ${requestScope.currentPage eq i ? 'btn-paging-question-current' : ''}" href="quiz?txtSubjectID=${param.txtSubjectID}&page=${i}">${i}</a>
                    </div>
                </c:forEach>
            </div>

            <div class="quiz-container">
                <div class="row">
                    <div class="col-md-12 body-content bg-white">
                        <h2 id="question">${requestScope.currentPage}. ${requestScope.QUIZ_QUESTION.name}</h2>
                        <form action="quiz" class="form-group" id="answer-form">
                            <div class="quiz-header">
                                <ul>
                                    <input type="hidden" name="txtSubjectID" value="${param.txtSubjectID}"/>
                                    <input type="hidden" name="page" value="${param.page}"/>
                                    <input type="hidden" name="saveAnswer" value="true"/>
                                    <li>
                                        <input type="radio" name="answer" value="1" id="a" class="answer mr-3" ${requestScope.QUIZ_QUESTION.userAnswer == 1 ? 'checked' : ''}/>
                                        <label for="a" id="a_text">A. ${requestScope.QUIZ_QUESTION.answerA}</label>
                                    </li>
                                    <li>
                                        <input type="radio" name="answer" value="2" id="b" class="answer mr-3" ${requestScope.QUIZ_QUESTION.userAnswer == 2 ? 'checked' : ''}/>
                                        <label for="b" id="b_text">B. ${requestScope.QUIZ_QUESTION.answerB}</label>
                                    </li>
                                    <li>
                                        <input type="radio" name="answer" value="3" id="c" class="answer mr-3" ${requestScope.QUIZ_QUESTION.userAnswer == 3 ? 'checked' : ''}/>
                                        <label for="c" id="c_text">C. ${requestScope.QUIZ_QUESTION.answerC}</label>
                                    </li>
                                    <li>
                                        <input type="radio" name="answer" value="4" id="d" class="answer mr-3" ${requestScope.QUIZ_QUESTION.userAnswer == 4 ? 'checked' : ''}/>
                                        <label for="d" id="d_text">D. ${requestScope.QUIZ_QUESTION.answerD}</label>
                                    </li>
                                </ul>
                            </div>
                        </form>

                    </div>
                </div>
                <div class="row">
                    <div class="d-flex justify-content-between footer">
                        <div>
                            <c:if test="${requestScope.currentPage != 1}">
                                <a class="btn btn-paging" href="quiz?txtSubjectID=${param.txtSubjectID}&page=${requestScope.currentPage-1}">&larr; Previous</a>
                            </c:if>
                        </div>
                        <div>
                            <c:if test="${requestScope.currentPage lt requestScope.noOfPages}">
                                <a class="btn btn-paging" href="quiz?txtSubjectID=${param.txtSubjectID}&page=${requestScope.currentPage+1}">Next &rarr;</a>
                                </li>
                            </c:if>
                        </div>
                    </div>
                </div>

            </div>
        </div>
        <script>

            function submitQuiz() {
                $.ajax({
                    url: 'quiz',
                    type: 'POST',
                    data: $("#answer-form").serialize(),
                    success: function () {
                    },
                    error: function () {
                        alert("Cannot save your answer!");
                    }
                });
            }

            function cal(timer) {
                var minutes = parseInt(timer / 60, 10);
                var seconds = parseInt(timer % 60, 10);

                minutes = minutes < 10 ? "0" + minutes : minutes;
                seconds = seconds < 10 ? "0" + seconds : seconds;

                document.querySelector('#timer').textContent = minutes + ":" + seconds;

            }

            function startTimer(duration) {
                var timer = duration;
                cal(timer);
                var intervalCount = setInterval(function () {
                    cal(timer);

                    if (--timer < 0) {
                        document.getElementById("submit-form").submit();
                        clearInterval(intervalCount);
                    }

                }, 1000);
            }

            window.onload = function () {
                var endTime = ${sessionScope.QUIZ.endTime.time};
                var curTime = new Date().getTime();
                var diff = Math.round((endTime - curTime) / 1000);
                startTimer(diff);
            }

            $("input[type=radio]").click(function () {
                if ($(this).prop("checked")) {
                    submitQuiz()
                }
            });


        </script>
        <jsp:include page="footer.jsp"/>
    </body>
</html>
