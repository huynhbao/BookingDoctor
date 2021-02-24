<%-- 
    Document   : history
    Created on : Jan 25, 2021, 8:59:39 PM
    Author     : HuynhBao
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>History Page</title>
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
            <div class="filter">
                <div class="filter-left">
                    <h2>My History</h2>
                </div>
                <div class="filter-right">
                    <button class="btn btn-outline-secondary" id="btn-search-panel" type="button">
                        <i class="fa fa-search"></i>
                    </button>
                </div>
            </div>
            <c:set var="searching" value="${not empty param.txtSearchName}" />
            <div class="search-container mb-5 ${searching == true ? '' : 'hide'}">
                <div class="bg-white p-3 rounded shadow">
                    <form action="history">
                        <div class="form-group mb-4">
                            <div class="input-group mb-3">
                                <input class="form-control border-primary" type="text" placeholder="Search Name" name="txtSearchName" value="${param.txtSearchName}">
                            </div>
                        </div>
                        <div class="form-group mb-4 text-center">
                            <button type="submit" class="btn btn-primary btn-lg btn-radius">Search</button>
                        </div>
                    </form>
                    <!-- End -->

                </div>
            </div>
            <div class="row pb-5 mb-4">
                <c:forEach items="${requestScope.LIST}" var="quiz">
                    <div class="col-4 mt-4">
                        <!-- Card-->
                        <div class="card shadow-sm border-0 my-round">
                            <div class="card-body p-4">
                                <div class="card-img-top embed-responsive-item text-center align-middle title-bg">
                                    <p>${quiz.subject.subjectID}</p>
                                </div>
                                <h6 class="mt-3"> ${quiz.subject.name}</h6>
                                <p class="small text-muted font-italic"></p>
                                <ul class="list-inline small">
                                    <fmt:formatDate var="date" pattern="dd-MM-yyyy HH:mm:ss" value="${quiz.startTime}" />
                                    <li class="list-inline-item m-0">Date: <strong>${date}</strong></li><br>
                                    <li class="list-inline-item m-0">Number of correct answers: <strong>${quiz.numOfCorrect}</strong></li><br>
                                    <li class="list-inline-item m-0">Points: <strong>${quiz.points}</strong></li><br>
                                </ul>
                                <c:url value="review" var="reviewLink">
                                    <c:param name="txtQuizID" value="${quiz.quizID}" />
                                </c:url>
                                <a class="btn btn-outline-primary w-100" href="${reviewLink}">
                                    <i class="fa fa-sign-in"></i> Detail
                                </a>
                            </div>
                        </div>
                    </div>
                </c:forEach>

            </div>
            <div class="row">
                <div class="d-flex footer">
                    <jsp:include page="paging.jsp" >
                        <jsp:param name="currentPage" value="${requestScope.currentPage}" />
                        <jsp:param name="noOfPages" value="${requestScope.noOfPages}" />
                        <jsp:param name="hrefLink" value="history?" />
                    </jsp:include>
                </div>
            </div>
        </div>
        <jsp:include page="footer.jsp"/>
        <script>
            $('.title-bg').each(function () {
                const randomColor = Math.floor(Math.random() * 16777215).toString(16);
                $(this).css('background-color', "#" + randomColor);
            });
            $('#btn-search-panel').click(function () {
                $('.search-container').slideToggle('slow');
            });
        </script>
    </body>
</html>
