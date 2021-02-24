<%-- 
    Document   : paging
    Created on : Jan 11, 2021, 6:18:54 PM
    Author     : HuynhBao
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Paging Page</title>
        <style>
            a {
                color: #000;
            }
            a:hover{
                text-decoration: none;
            }

            .pagination{
                /*padding: 30px 0;*/
            }

            .pagination ul{
                margin: 0;
                padding: 0;
                list-style-type: none;
            }

            .pagination li{
                display: inline-block;
                padding: 10px 18px;
                color: #222;
            }

            .p1 li{
                width: 40px;
                height: 40px;
                line-height: 40px;
                padding: 0;
                text-align: center;
            }

            .p1 li.active{
                background-color: #7208ff87;
                border-radius: 100%;
                color: #fff;
            }
        </style>
    </head>
    <body>
        <ul class="pagination p1 mx-auto">
            <c:if test="${param.currentPage != 1}">
                <li class="page-item">
                    <a href="${param.hrefLink}&page=${param.currentPage-1}"><</a>
                </li>
            </c:if>

            <c:if test="${param.noOfPages != 1}">
                <c:forEach begin="1" end="${param.noOfPages}" var="i">
                    <c:choose>
                        <c:when test="${param.currentPage eq i}">
                            <li class="page-item active">
                                <a class=""> ${i} <span class="sr-only">(current)</span></a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item">
                                <a href="${param.hrefLink}&page=${i}">${i}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </c:if>

            <c:if test="${param.currentPage lt param.noOfPages}">
                <li class="page-item">
                    <a href="${param.hrefLink}&page=${param.currentPage+1}">></a>
                </li>
            </c:if>
        </ul>
    </body>
</html>
