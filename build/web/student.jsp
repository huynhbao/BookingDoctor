<%-- 
    Document   : student
    Created on : Jan 24, 2021, 12:06:49 PM
    Author     : HuynhBao
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Student Page</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
              integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
              crossorigin="anonymous">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
        <link href="css/filter.css" rel="stylesheet">
        <style>
            body {
                background-color: #b8c6db;
                background-image: linear-gradient(315deg, #b8c6db 0%, #f5f7fa 100%);
                display: flex;
                height: 100vh;
                overflow: hidden;
                margin: 0;
            }

            .my-round {
                border-radius: 15px;
            }

            .title-bg {
                width: 100%;
                height: 170px;
                background-color: #b8ffce;
                display: flex;
                justify-content: center;
                align-items: center;
                text-transform: uppercase;
                font-weight: bold;
                font-size: 25px;
                border-radius: 30px;
                box-shadow: 0 0.125rem 0.25rem rgb(0 0 0 / 24%);
                color: #fff;
            }
        </style>
    </head>
    <body>
        <jsp:include page="menu.jsp"/>
        <div class="container" style="margin-top: 140px;">
            <div class="row pb-5 mb-4">
                <c:forEach items="${requestScope.LIST_SUBJECT}" var="subject">
                    <div class="col-4 mt-4">
                        <!-- Card-->
                        <div class="card shadow-sm border-0 my-round">
                            <div class="card-body p-4">
                                <div class="card-img-top embed-responsive-item text-center align-middle title-bg">
                                    <p>${subject.subjectID}</p>
                                </div>
                                <h6 class="mt-3"> ${subject.name}</h6>
                                <p class="small text-muted font-italic"></p>
                                <ul class="list-inline small">
                                    <li class="list-inline-item m-0">Number of question: <strong>${subject.numOfQuestion}</strong></li><br>
                                    <li class="list-inline-item m-0">Time Limit: <strong>${subject.time_limit} minutes</strong></li><br>
                                </ul>
                                <c:url value = "quiz" var = "quizLink">
                                    <c:param name="txtSubjectID" value="${subject.subjectID}" />
                                </c:url>
                                <a class="btn btn-outline-primary w-100" href="${quizLink}">
                                    <i class="fa fa-sign-in"></i> Take Quiz
                                </a>
                            </div>
                        </div>
                    </div>
                </c:forEach>

            </div>
        </div>
        <jsp:include page="footer.jsp"/>
        <script>
            $('.title-bg').each(function () {
                const randomColor = Math.floor(Math.random() * 16777215).toString(16);
                $(this).css('background-color', "#" + randomColor);
            });
        </script>
    </body>
</html>
