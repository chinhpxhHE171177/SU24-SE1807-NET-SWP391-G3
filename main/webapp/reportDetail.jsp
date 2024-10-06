<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 9/29/2024
  Time: 12:41 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="model.ErrorHistory" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý thiết bị</title>
    <link rel="stylesheet" href="styles.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }

        header {
            background: #333;
            color: #fff;
            padding: 15px 20px;
            text-align: center;
        }

        h1 {
            margin: 0;
        }

        .search-bar {
            padding: 20px;
            background: #fff;
            border-radius: 5px;
            margin: 20px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        .search-input {
            margin-right: 10px;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 3px;
        }

        .btn {
            padding: 10px 15px;
            border: none;
            border-radius: 3px;
            cursor: pointer;
        }

        .btn-primary {
            background: #007bff;
            color: white;
        }

        .btn-secondary {
            background: #6c757d;
            color: white;
        }

        .chart-container {
            margin: 20px;
            padding: 20px;
            background: #fff;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        .chart {
            height: 300px; /* Kích thước biểu đồ */
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }

        th, td {
            padding: 12px;
            border: 1px solid #ddd;
            text-align: left;
        }

        th {
            background: #f2f2f2;
        }
        /* Pagination Styles */
        .pagination {
            display: flex;
            justify-content: center;
            list-style-type: none;
            padding: 20px 0;
        }

        .pagination li {
            margin: 0 5px;
        }

        .pagination a {
            text-decoration: none;
            padding: 8px 12px;
            border: 1px solid #ccc;
            border-radius: 5px;
            color: #333;
        }

        .pagination .active a {
            background-color: #35424a;
            color: white;
        }

        .pagination .disabled a {
            color: #aaa;
        }

        footer {
            text-align: center;
            padding: 10px;
            background: #333;
            color: #fff;
        }

    </style>
</head>
<body>
<header>
    <h1>Quản Lý Thiết Bị</h1>
</header>

<main>
    <div class="search-bar">
        <form id="searchForm" action="ReportDetail" method="POST">
            <input type="hidden" name="action" value="search">
            <input type="text" name="deviceCode" ${not empty deviceCode ? deviceCode : ''} placeholder="Mã thiết bị" class="search-input">
            <select name="depId" class="search-input">
                <option value="">-- Chọn Phòng --</option>
                <c:forEach var="d" items="${listd}">
                    <option value="${d.id}" ${d.id == depId ? 'selected' : ''}>${d.name}</option>
                </c:forEach>
            </select>

            <select name="lineId" class="search-input">
                <option value="">-- Chọn Line --</option>
                <c:forEach var="l" items="${listl}">
                    <option value="${l.id}" ${l.id == lineId ? 'selected' : ''}>${l.name}</option>
                </c:forEach>
            </select>

            <select name="stageId" class="search-input">
                <option value="">-- Chọn Công Đoạn --</option>
                <c:forEach var="s" items="${listst}">
                    <option value="${s.id}" ${s.id == stageId ? 'selected' : ''}>${s.name}</option>
                </c:forEach>
            </select>
            <button type="submit" class="btn btn-primary">Tìm Kiếm</button>
            <button type="button" class="btn btn-secondary">Đặt Lại</button>
        </form>
    </div>

<%--    <%--%>
<%--        java.util.List<ErrorHistory> errorDataList = (java.util.List<ErrorHistory>) request.getAttribute("deviceList");--%>
<%--    %>--%>

    <div class="chart-container">
        <h2>Biểu Đồ Thống Kê</h2>
        <canvas id="errorTimeChart" width="550" height="150"></canvas>
        <div class="legend">
            <div style="display: flex; align-items: center;">
                <div style="width: 20px; height: 20px; background-color: rgba(255, 99, 132, 0.2); border: 1px solid rgba(255, 99, 132, 1); margin-right: 5px;"></div>
                <span style="margin-right: 20px;">Duration > 5 minutes</span>
                <div style="width: 20px; height: 20px; background-color: rgba(54, 162, 235, 0.2); border: 1px solid rgba(54, 162, 235, 1); margin-right: 5px;"></div>
                <span>Duration < 5 minutes</span>
            </div>
        </div>
    </div>

    <script>
        var ctx = document.getElementById('errorTimeChart').getContext('2d');

        // Extract data from Java
        var labels = [];
        var downtimeData = [];
        var barColors = []; // Array for storing bar colors

        <c:forEach var="data" items="${deviceList}">
        labels.push('${data.startDate}');
        downtimeData.push(${data.duration});

        // Check duration and push appropriate color (red if duration > 5)
        if (${data.duration} > 5) {
            barColors.push('rgba(255, 99, 132, 0.2)'); // red for > 5
        } else {
            barColors.push('rgba(54, 162, 235, 0.2)'); // default blue for <= 5
        }
        </c:forEach>

        var chart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Downtime (minutes)',
                    data: downtimeData,
                    backgroundColor: barColors, // Use dynamic colors
                    borderColor: barColors.map(function(color) {
                        return color.replace('0.2', '1'); // Make border solid
                    }),
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    </script>


    <table>
        <thead>
        <tr>
            <th>#</th>
            <th>Device Code</th>
            <th>Device Name</th>
            <th>Department</th>
            <th>Line</th>
            <th>Stage</th>
            <th>Error Description</th>
            <th>Start Date</th>
            <th>End Date</th>
            <th>TotalTime(m)</th>
        </tr>
        </thead>
        <tbody id="deviceTableBody" style="font-size: 14px">
        <c:forEach items="${lists}" var="i" varStatus="loop">
            <tr>
                <td>${(currentPage - 1) * pageSize + loop.index + 1}</td>
                <td>${i.equipmentCode}</td>
                <td>${i.equipmentName}</td>
                <td>${i.departmentName}</td>
                <td>${i.lineName}</td>
                <td>${i.stageName}</td>
                <td>${i.content}</td>
                <td><fmt:formatDate value="${i.startDate}" pattern="yyyy-MM-dd HH:mm"/></td>
                <td><fmt:formatDate value="${i.endDate}" pattern="yyyy-MM-dd HH:mm"/></td>
                <td style="text-align: center; background-color: ${i.duration > 5 ? 'red' : ''}">${i.duration}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <c:if test="${empty requestScope.lists}">
        <p style="text-align: center; color: #666666; margin: 10px 20px">No short stop pause info found matching your criteria.</p>
    </c:if>
</main>

<nav aria-label="Page navigation">
    <ul class="pagination">
        <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
            <a class="page-link" href="ReportDetail?action=search&pageIndex=${currentPage - 1}&deviceCode=${deviceCode}&depId=${depId}&lineId=${lineId}&stageId=${stageId}" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
            </a>
        </li>
        <c:forEach begin="1" end="${totalPage}" var="i">
            <li class="page-item ${i == currentPage ? 'active' : ''}">
                <a class="page-link" href="ReportDetail?action=search&pageIndex=${i}&deviceCode=${deviceCode}&depId=${depId}&lineId=${lineId}&stageId=${stageId}">${i}</a>
            </li>
        </c:forEach>
        <li class="page-item ${currentPage == totalPage ? 'disabled' : ''}">
            <a class="page-link" href="ReportDetail?action=search&pageIndex=${currentPage + 1}&deviceCode=${deviceCode}&depId=${depId}&lineId=${lineId}&stageId=${stageId}" aria-label="Next">
                <span aria-hidden="true">&raquo;</span>
            </a>
        </li>
    </ul>
</nav>

<footer>
    <p>Bản quyền © 2024</p>
</footer>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</body>
</html>

