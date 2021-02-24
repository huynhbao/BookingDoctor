<%-- 
    Document   : createSubject
    Created on : Jan 30, 2021, 8:41:56 PM
    Author     : HuynhBao
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Subject Page</title>
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
            <form action="createSubject" method="POST" id="action-form">
                <div class="form-group">
                    <label for="txtSubjectID">Subject ID</label>

                    <input type="text" class="form-control" name="txtSubjectID" placeholder="Enter Subject ID" value="<c:out value="${param.txtSubjectID}" />">
                    <small class="text-danger">
                        ${requestScope.ERROR_SUBJECT_ID}
                    </small>
                </div>
                <div class="form-group">
                    <label for="txtName">Subject Name</label>

                    <input type="text" class="form-control" name="txtName" placeholder="Enter Subject Name" value="<c:out value="${param.txtName}" />">
                    <small class="text-danger">
                        ${requestScope.ERROR_SUBJECT_NAME}
                    </small>
                </div>
                <div class="form-group">
                    <label for="txtNumOfQuestion">Number of question</label>

                    <input type="text" class="form-control" name="txtNumOfQuestion" placeholder="Enter Number of question" value="<c:out value="${param.txtNumOfQuestion}" />">
                    <small class="text-danger">
                        ${requestScope.ERROR_NUM_OF_QUESTION}
                    </small>
                </div>
                <div class="form-group">
                    <label for="txtTimeLimit">Time Limit</label>

                    <input type="text" class="form-control" name="txtTimeLimit" placeholder="Enter Time Limit" value="<c:out value="${param.txtTimeLimit}" />">
                    <small class="text-danger">
                        ${requestScope.ERROR_TIME_LIMIT}
                    </small>
                </div>
                <div class="form-group">
                    <div class="input-group mb-3">
                        <div class="input-group-prepend">
                            <label class="input-group-text" for="cbStatus">Status</label>
                        </div>
                        <select class="form-control border-primary" name="cbStatus">
                            <option ${param.cbStatus == 'active' ? 'selected' : ''} value="active">Active</option>
                            <option ${param.cbStatus == 'inactive' ? 'selected' : ''} value="inactive">Inactive</option>

                        </select>
                    </div>
                </div>

                <div class="form-group text-center">
                    ${ERROR_ANSWER}
                    <br>
                    <button type="submit" class="btn btn-primary btn-lg btn-radius">Create</button>
                </div>

            </form>

        </div>
        <jsp:include page="footer.jsp"/>
    </body>
</html>
