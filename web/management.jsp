<%-- 
    Document   : management
    Created on : Jan 23, 2021, 3:56:09 PM
    Author     : HuynhBao
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Management Page</title>
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
                background-attachment: fixed;
                background-size: cover;
                height: auto;
            }

            .quuestion-container {
                padding: 20px;
                border-radius: 20px;
                box-shadow: 0 0 10px 2px rgba(100, 100, 100, 0.1);
            }

            .question-body {
                padding: 10px 0px 0px 15px;
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

            h2 {
                padding: 1rem;
                text-align: center;
                margin: 0;
            }

            ul {
                list-style-type: none;
                padding: 0;
            }

            .question-body ul li {
                font-size: 1.0rem;
                margin: 0.9rem 0;
                background-color: #e2e2e263;
                border-radius: 10px;
                padding: 10px 0px 10px 10px;
            }

            ul li label {
                cursor: pointer;
            }

            .footer {
                width: 100%;
            }
        </style>
    </head>
    <body>
        <jsp:include page="menu.jsp"/>
        <div class="container py-5" style="margin-top: 140px;">
            <div class="filter">
                <div class="filter-left">
                    <a href="createSubject" class="btn btn-outline-secondary">
                        <i class="fa fa-plus"> Create Subject</i>
                    </a>
                    <a href="createQuestion" class="btn btn-outline-secondary">
                        <i class="fa fa-plus"> Create Question</i>
                    </a>
                </div>
                <div class="filter-right">
                    <button class="btn btn-outline-secondary" id="btn-search-panel" type="button">
                        <i class="fa fa-search"></i>
                    </button>
                </div>
            </div>
            <c:set var="searching" value="${not empty param.chkSubject or not empty param.chkName or not empty param.chkStatus}" />
            <div class="search-container mb-5 ${searching == true ? '' : 'hide'}">
                <div class="bg-white p-3 rounded shadow">
                    <form action="management">
                        <div class="form-group mb-4">
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <div class="input-group-text">
                                        <input type="checkbox" name="chkSubject" ${param.chkSubject == 'on' ? 'checked' : ''}>
                                    </div>
                                </div>
                                <select class="form-control border-primary" name="cbSubject">
                                    <c:forEach items="${requestScope.LIST_SUBJECT}" var="subject">
                                        <option ${param.cbSubject == subject.subjectID ? 'selected' : ''} value="${subject.subjectID}">${subject.subjectID} - ${subject.name}</option>
                                    </c:forEach>
                                </select>
                            </div>

                        </div>
                        <div class="form-group mb-4">
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <div class="input-group-text">
                                        <input type="checkbox" name="chkName" ${param.chkName == 'on' ? 'checked' : ''}>
                                    </div>
                                </div>
                                <input class="form-control border-primary" type="text" placeholder="Search Name" name="txtSearchName" value="${param.txtSearchName}">
                            </div>
                        </div>
                        <div class="form-group mb-4">
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <div class="input-group-text">
                                        <input type="checkbox" name="chkStatus" ${param.chkStatus == 'on' ? 'checked' : ''}>
                                    </div>
                                </div>
                                <select class="form-control border-primary" name="cbStatus">
                                    <option ${param.cbStatus == 'active' ? 'selected' : ''} value="active">Active</option>
                                    <option ${param.cbStatus == 'inactive' ? 'selected' : ''} value="inactive">Inactive</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group mb-4 text-center">
                            <button type="submit" class="btn btn-primary btn-lg btn-radius">Search</button>
                        </div>


                    </form>
                    <!-- End -->

                </div>
            </div>
            <c:if test="${not searching}">
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
                                        <li class="list-inline-item m-0">Number of question for quiz: <strong>${subject.numOfQuestion}</strong></li><br>
                                        <li class="list-inline-item m-0">Time Limit: <strong>${subject.time_limit} minutes</strong></li><br>
                                        <li class="list-inline-item m-0">Status: <strong>${subject.status == true ? 'Active' : 'Inactive'}</strong></li><br>
                                    </ul>
                                    <c:url value = "updateSubject" var = "updateLink">
                                        <c:param name="txtSubjectID" value="${subject.subjectID}" />
                                    </c:url>

                                    <c:url value = "deleteSubject" var = "deleteLink">
                                        <c:param name="txtSubjectID" value="${subject.subjectID}" />
                                    </c:url>
                                    <a class="btn btn-outline-primary" href="${updateLink}">
                                        <i class="fa fa-edit"></i> Update
                                    </a>
                                    <a class="btn btn-outline-primary" href="${deleteLink}">
                                        <i class="fa fa-remove"></i> Delete
                                    </a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>

                </div>
            </c:if>


            <c:if test="${requestScope.LIST != null}">
                <c:forEach items="${requestScope.LIST}" var="subject">
                    <c:forEach items="${subject.question}" var="question">
                        <div class="row mb-4 quuestion-container bg-white">
                            <div class="col-md-12 body-content">

                                <div class="d-flex justify-content-between">
                                    <div>
                                        <p><strong>Subject ID:</strong> <span class="text-uppercase"> ${subject.subjectID}</p>
                                        <p><strong>Subject Name:</strong> ${subject.name}</p>
                                        <fmt:formatDate var="date" pattern="dd-MM-yyyy HH:mm:ss" value="${question.createDate}" />
                                        <p><strong>Create Date: </strong> ${date}</p>
                                        <p><strong>Status:</strong> ${question.status == true ? 'Active' : 'Inactive'}</p>
                                    </div>
                                    <div>
                                        <a href="updateQuestion?txtQuestionID=${question.questionID}" class="btn btn-primary">
                                            <i class="fa fa-edit"></i>
                                            Update
                                        </a>
                                        <c:if test="${question.status == true}">
                                            <a href="deleteQuestion?txtQuestionID=${question.questionID}" class="btn btn-primary">
                                                <i class="fa fa-remove"></i>
                                                Delete
                                            </a>
                                        </c:if>
                                    </div>
                                </div>
                                <hr>
                                <h4 id="question">${question.name}</h4>
                                <div class="question-body">
                                    <ul>
                                        <input type="hidden" name="txtSubjectID" value="${param.txtSubjectID}"/>
                                        <input type="hidden" name="page" value="${param.page}"/>
                                        <input type="hidden" name="saveAnswer" value="true"/>
                                        <li>
                                            <label class="${question.correctAnswer == 1 ? 'text-danger font-weight-bold' : ''}"><strong>A.</strong> ${question.answerA}</label>
                                        </li>
                                        <li>
                                            <label class="${question.correctAnswer == 2 ? 'text-danger font-weight-bold' : ''}"><strong>B.</strong> ${question.answerB}</label>
                                        </li>
                                        <li>
                                            <label class="${question.correctAnswer == 3 ? 'text-danger font-weight-bold' : ''}"><strong>C.</strong> ${question.answerC}</label>
                                        </li>
                                        <li>
                                            <label class="${question.correctAnswer == 4 ? 'text-danger font-weight-bold' : ''}"><strong>D.</strong> ${question.answerD}</label>
                                        </li>
                                    </ul>
                                </div>

                            </div>
                        </div>
                    </c:forEach>
                </c:forEach>
                <div class="row">
                    <div class="d-flex footer">
                        <jsp:include page="paging.jsp" >
                            <jsp:param name="currentPage" value="${requestScope.currentPage}" />
                            <jsp:param name="noOfPages" value="${requestScope.noOfPages}" />
                            <jsp:param name="hrefLink" value="management?chkSubject=${param.chkSubject}&cbSubject=${param.cbSubject}&chkName=${param.chkName}&txtSearchName=${param.txtSearchName}&chkStatus=${param.chkStatus}&cbStatus=${param.cbStatus}" />
                        </jsp:include>
                    </div>
                </div>
            </c:if>
        </div>
        <jsp:include page="footer.jsp"/>
        <script>
            $('#btn-search-panel').click(function () {
                $('.search-container').slideToggle('slow');
            });

            $('.title-bg').each(function () {
                const randomColor = Math.floor(Math.random() * 16777215).toString(16);
                $(this).css('background-color', "#" + randomColor);
            });
        </script>
    </body>
</html>
