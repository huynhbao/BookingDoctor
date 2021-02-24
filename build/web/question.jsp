<%-- 
    Document   : question
    Created on : Jan 26, 2021, 9:54:31 AM
    Author     : HuynhBao
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Question Page</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
              integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
              crossorigin="anonymous">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
        <link href="css/filter.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="menu.jsp"/>
        <div class="container py-5" style="margin-top: 140px;">
            <div class="filter">
                <div class="filter-left">
                    <a href="createQuestion" class="btn btn-outline-secondary">
                        <i class="fa fa-plus"></i>
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
                                    <c:forEach items="${requestScope.LIST}" var="subject">
                                        <option ${param.cbSubject == subject.subjectID ? 'selected' : ''} value="${subject.subjectID}">${subject.name}</option>
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
                                    <option ${param.cbSubject == subject.subjectID ? 'selected' : ''} value="active">Active</option>
                                    <option ${param.cbSubject == subject.subjectID ? 'selected' : ''} value="inactive">Inactive</option>

                                </select>
                            </div>
                        </div>
                        <div class="form-group mb-4 text-center">
                            <input type="hidden" name="txtCategory" value="${param.txtCategory}">
                            <p class="text-danger">${requestScope.ERROR_MISSING_SEARCH_PRICE}</p>
                            <p class="text-danger">${requestScope.ERROR_NUMBER_SEARCH_PRICE}</p>


                            <button type="submit" class="btn btn-primary btn-lg btn-radius" name="btnAction" value="Search">Search</button>
                        </div>


                    </form>
                    <!-- End -->

                </div>
            </div>
            <div class="row pb-5 mb-4">
                <c:if test="${requestScope.SUBJECT != null}">
                    <c:if test="${not empty requestScope.SUBJECT}">
                        <div class="table-responsive">
                            <table class="table table-hover">
                                <thead class="thead-light">
                                    <tr class="align-middle text-center">
                                        <th scope="col">No</th>
                                        <th scope="col">Subject ID</th>
                                        <th scope="col">Subject Name</th>
                                        <th scope="col">Time Limit</th>
                                        <th scope="col">Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${requestScope.SUBJECT.question}" var="question" varStatus="counter">
                                    <tr class="align-middle text-center">
                                        <td>
                                            ${counter.count}
                                        </td>
                                        <td>
                                            ${question.questionID}
                                        </td>
                                        <td>
                                            ${question.name}
                                        </td>
                                        <td>
                                            ${question.answerA}
                                        </td>
                                        <td>
                                            <a href="getQuestion?txtSubjectID=${subject.subjectID}" class="btn btn-primary">Show Question</a>
                                        </td>
                                    </tr>
                                </c:forEach>

                                </tbody>
                            </table>
                        </div>
                    </c:if>
                </c:if>


            </div>
        </div>
        <jsp:include page="footer.jsp"/>
    </body>
</html>
