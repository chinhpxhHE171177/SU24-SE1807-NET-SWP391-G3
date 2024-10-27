<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="java.util.List" %>
<%@page import="model.Permission" %>
<%@page import="model.Users" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý thiết bị</title>
    <link rel="stylesheet" href="styles.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css" integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
     <style>
    body {
        font-family: 'Arial', sans-serif;
        margin: 0;
        padding: 0;
        background-color: #f4f4f4;
    }

    header {
        background: #35424a;
        color: #fff;
        padding: 15px 20px;
        text-align: center;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    }
    .hidden {
       display: none;
    }

    h1 {
        margin: 0;
        font-size: 24px;
    }

    .search-bar {
        width: 96%;
        padding: 20px;
        background: #fff;
        border-radius: 8px;
        margin: 6px auto;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }

    .search-input {
        width: 350px;
        height: 46px;
        margin-right: 10px;
        margin-bottom: 6px;
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
    }

    .btn {
        padding: 10px 15px;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        transition: background 0.3s ease;
    }

    .btn-primary {
        background: #007bff;
        color: white;
    }

    .btn-primary:hover {
        background: #0056b3;
    }

    .btn-secondary {
        background: #6c757d;
        color: white;
    }

    .btn-secondary:hover {
        background: #5a6268;
    }

    .chart-container {
        width: 96%;
        margin: 6px auto;
        padding: 20px;
        background: #fff;
        border-radius: 8px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }

    .chart {
        height: 300px;
    }

    table {
        width: 100%;
        border-collapse: collapse;
        margin: 20px 0;
        background-color: #fff;
        border-radius: 8px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        overflow: hidden;
    }

    .user-table, .line-table, .stage-table, .line-stage-table, .shift-table {
        border-collapse: collapse;
        margin-bottom: 20px;
    }

    .user-table tr, .user-table th, .user-table td,
    .shift-table tr, .shift-table th, .shift-table td,
    .line-table th, .line-table tr, .line-table td,
    .stage-table th, .stage-table tr, .stage-table td
    .line-stage-table th, .line-stage-table td, .line-stage-table tr {
        border: 1px solid #ddd;
    }

    th, td {
        padding: 12px 15px;
        text-align: left;
    }

    th {
        background: #35424a;
        color: #fff;
        text-transform: uppercase;
    }

    td {
        background-color: #f9f9f9;
    }

    /* Highlighting rows on hover */
    tr:hover td {
        background-color: #f1f1f1;
    }

    td[style*="background-color: red"] {
        background-color: #ffcccc;
        color: #b30000;
        font-weight: bold;
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
        transition: background 0.3s ease, color 0.3s ease;
    }

    .pagination a:hover {
        background-color: #007bff;
        color: white;
    }

    .pagination .active a {
        background-color: #35424a;
        color: white;
    }

    .pagination .disabled a {
        color: #aaa;
    }

    /* Hide button */
    .btn-hide {
        float: right;
        margin-right: 20px;
        margin-bottom: 20px;
        background: #ff4757;
        color: white;
        border: none;
        padding: 8px 12px;
        border-radius: 5px;
        cursor: pointer;
    }

    .btn-hide:hover {
        background: #e84118;
    }

    footer {
        text-align: center;
        padding: 10px;
        background: #35424a;
        color: #fff;
        margin-top: 20px;
        box-shadow: 0 -4px 6px rgba(0, 0, 0, 0.1);
    }
</style>
</head>
<body>
<%
    // Check if the user is logged in
    Users user = (Users) session.getAttribute("users");
    List<Permission> permissions = (List<Permission>) session.getAttribute("permissions");

    if (user == null || permissions == null) {
        response.sendRedirect("login");
        return;
    }

    // Check if the user has access to this page
    boolean hasAccess = false;
    for (Permission p : permissions) {
        if ("ReportDetail".equals(p.getPageName()) && p.isCanAccess()) {
            hasAccess = true;
            break;
        }
    }

    if (!hasAccess) {
        response.sendRedirect("error.jsp");
        return;
    }
%>


<%@ include file="components/navigation.jsp" %>
<header>
    <h1>Quản lý thông tin chi tiết dừng ngắn</h1> 
</header>

<main>
<div class="search-bar">
    <form id="searchForm" action="ReportDetail" method="POST">
        <input type="hidden" name="action" value="search">
        <!-- Radio buttons for Selecting Time Unit -->
        <div>
            <label>
                <input type="radio" name="timeUnit" value="WorkingDay" 
                    ${timeUnit == null || timeUnit == 'WorkingDay' ? 'checked' : ''} 
                    onclick="toggleInputFields()"> Ngày làm việc
            </label>
            <label>
                <input type="radio" name="timeUnit" value="Shift" 
                    ${timeUnit == 'Shift' ? 'checked' : ''} 
                    onclick="toggleInputFields()"> Ca
            </label>
            <label>
                <input type="radio" name="timeUnit" value="Hours" 
                    ${timeUnit == 'Hours' ? 'checked' : ''} 
                    onclick="toggleInputFields()"> Giờ
            </label>
        </div>
        
        <div style="display: flex">
            <div style="width: 30%">
                <!-- Line and Stage Dropdowns -->
                <select id="lineId" name="lineId" class="search-input">
                    <option value="">-- Chọn Line --</option>
                    <c:forEach var="l" items="${listl}">
                        <option value="${l.id}" ${l.id == lineId ? 'selected' : ''}>${l.name}</option>
                    </c:forEach>
                </select>
                <select id="stageId" name="stageId" class="search-input">
                    <option value="">-- Chọn Công Đoạn --</option>
                    <c:forEach var="s" items="${listst}">
                        <option value="${s.id}" ${s.id == stageId ? 'selected' : ''}>${s.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div style="width: 50%">
                <!-- Input fields for Working Day -->
                <div id="workingDayInputs" class="time-inputs">
                    <label style="margin-right: 20px; padding: 10px;">From: </label>
                    <input type="date" name="startDate" value="${not empty startDate ? startDate : ''}" class="search-input"/>
                    <br/>
                    <label style="margin-right: 40px; padding: 10px;">To: </label>
                    <input type="date" name="endDate" value="${not empty endDate ? endDate : ''}" class="search-input"/>
                </div>
                <!-- Input fields for Shift -->
                <div id="shiftInputs" class="time-inputs" style="display: none;">
                    <label style="margin-right: 20px; padding: 10px;">From: </label>
                    <input type="date" name="startDateShift" value="${not empty startDateShift ? startDateShift : ''}" class="search-input"/>
                    <br/>
                    <label style="margin-right: 40px; padding: 10px;">To: </label>
                    <input type="date" name="endDateShift" value="${not empty endDateShift ? endDateShift : ''}" class="search-input"/>
                </div>
                <!-- Input fields for Hours -->
                <div id="hourInputs" class="time-inputs" style="display: none;">
                    <label style="margin-right: 15px; padding: 10px;">Target Date: </label>
                    <input type="date" name="specificDate" value="${not empty specificDate ? specificDate : ''}" class="search-input"/>
                </div>
            </div>
        </div>
        <!-- Submit and Reset buttons -->
        <div style="margin-top: 10px;">
            <button type="submit" class="btn btn-primary">Tìm Kiếm</button>
            <button type="reset" class="btn btn-secondary" onclick="resetSearch()">Reset</button>
        </div>
    </form>
</div>


  <div id="chartContainer" class="chart-container" style="${timeUnit == 'WorkingDay' || timeUnit == 'Shift' || timeUnit == 'Hours' ? '' : 'display:none;'}">
    <div style="display: flex; justify-content: space-between; align-items: center;">
        <h2 style="margin: 0;">Biểu Đồ Thống Kê</h2>
        <h6 style="margin: 0; color: red; font-size: 12px;">Lần dừng: ${errorMessage}</h6>
    </div>

    <canvas id="errorTimeChart" width="550" height="150"></canvas>
   
</div>

<script>

var ctx = document.getElementById('errorTimeChart').getContext('2d');

var sampleLabels = ['Label1', 'Label2', 'Label3']; 
var sampleData = [10, 3, 5]; 
var errorCounts = [2, 1, 4]; 
var longData = [];
var shortData = [];
var shortStopPercentTage = [];

var sampleColors = sampleData.map(duration => duration > 5 ? 'rgba(54, 162, 235, 0.2)' : 'rgba(54, 162, 235, 0.2)');

var chart = new Chart(ctx, {
    type: 'bar',
    data: {
        labels: sampleLabels, 
        datasets: [{
            label: 'Tổng thời gian (phút)',
            data: sampleData, 
            backgroundColor: sampleColors,
            borderColor: sampleColors.map(color => color.replace('0.2', '1')), 
            yAxisID: 'yLeft',
            borderWidth: 1,
            barThickness: 50,
            additionalInfo: [] 
        },
        {
            label: 'Dừng dài (phút)',
            data: longData,
            backgroundColor: 'rgba(255, 99, 132, 0.2)', 
            borderColor: 'rgba(255, 99, 132, 1)',
            borderWidth: 1,
            barThickness: 50
        },
        {
            label: 'Dừng ngắn (phút)',
            data: shortData,
            backgroundColor: 'rgb(60, 179, 113)', 
            borderColor: 'rgba(60, 179, 113, 1)',
            borderWidth: 1,
            barThickness: 50
        },
        {
            label: 'Số lần dừng ngắn',
            data: errorCounts, 
            backgroundColor: 'rgba(255, 206, 86, 0.2)', 
            borderColor: 'rgba(255, 206, 86, 1)', 
            borderWidth: 2,
            type: 'line', 
            fill: false, 
            yAxisID: 'yRight',
            pointRadius: 5
        },
        {
            label: 'Số tỷ lệ dừng ngắn',
            data: shortStopPercentTage, 
            backgroundColor: 'rgba(75, 0, 130, 0.2)', 
            borderColor: 'rgba(75, 0, 130, 1)', 
            borderWidth: 2,
            type: 'line', 
            fill: false, 
            yAxisID: 'yRight',
            pointRadius: 5
        }]
    },
    options: {
        responsive: true,
        scales: {
            yLeft: {
                type: 'linear',
                position: 'left',
                beginAtZero: true,
                title: {
                    display: true,
                    text: 'Thời gian (phút)'
                }
            },
            yRight: {
                type: 'linear',
                position: 'right',
                beginAtZero: true,
                grid: {
                    drawOnChartArea: false
                },
                title: {
                    display: true,
                    text: 'Số lần lỗi'
                }
            }
        },
        plugins: {
            tooltip: {
                callbacks: {
                    label: function(tooltipItem) {
                        let labels = []; // Khởi tạo mảng để chứa các nhãn
                        //const datasetIndex = tooltipItem.datasetIndex;

                        // Nhãn chính
                        let mainLabel = tooltipItem.dataset.label || '';
                        mainLabel += ': ' + tooltipItem.raw; 
                        labels.push(mainLabel); // Thêm nhãn chính vào mảng
                        
                     // Calculate percentage for short stop ratio
                        if (tooltipItem.datasetIndex === 4) { 
                            const percentage = 'Tỷ lệ dừng ngắn: ' +  tooltipItem.raw + ' %' ;
                            labels.push(percentage); 
                        }

                        // Thêm thông tin bổ sung từ dataset
                        if (tooltipItem.dataset.additionalInfo) {
                            const additionalInfo = tooltipItem.dataset.additionalInfo[tooltipItem.dataIndex] || '';
                            labels.push(additionalInfo); 
                        }
                        
                        return labels; 
                    }
                }
            }
        },
        grouped: true
    }
});
</script>

</main>
<div>
    <button id="toggleButton" class="btn btn-secondary btn-hide" style="${timeUnit == 'WorkingDay' || timeUnit == 'Shift' || timeUnit == 'Hours' ? '' : 'display:none;'}">
        <i class="fa-solid fa-eye"></i>
    </button>
</div>
<div id="tablesContainer" class="tables">
<!-- Working Day Table -->
    <table class="line-stage-table" id="lineStageWorkingDayTable" style="${timeUnit == 'WorkingDay' ? '' : 'display:none;'} border-collapse: collapse; width: 100%;">
        <thead>
            <tr>
                <th>#</th>
                <th>Day</th>
                <th>Long Stop</th>
                <th>Short Stop</th>
                <th>Total Time</th>
                <th>Short Count</th>
                <th>SSTP(%)</th>
                <th>Count</th>
                <th style="display: none">Date</th>
            </tr>
        </thead>
        <tbody id="lineStageWorkingDayBody" style="font-size: 14px">
            <c:forEach items="${listsl}" var="s" varStatus="loop">
                <tr>
                    <td>${(currentPage - 1) * pageSize + loop.index + 1}</td>
                    <td><fmt:formatDate value="${s.startDate}" pattern="dd-MM-yyyy"/></td>
                    <td>${s.longTime}</td>
                    <td>${s.shortTime}</td>
                    <td>${s.duration}</td>
                    <td>${s.shortCount}</td>
                    <td>${s.shortStopPercentage} %</td>
                    <td>${s.errorCount}</td>
                    <th style="display: none"></th>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    
    <!-- Shift Table -->
    <table class="line-stage-table" id="lineStageShiftTable" style="${timeUnit == 'Shift' ? '' : 'display:none;'} border-collapse: collapse; width: 100%;">
        <thead>
            <tr>
                <th>#</th>
                <!--<th>Day</th>-->
                <th>Shift</th>
                <th>Long Stop</th>
                <th>Short Stop</th>
                <th>Total Time</th>
                <th>Short Count</th>
                 <th>SSTP(%)</th>
                <th>Count</th>
                <th>Date</th>
            </tr>
        </thead>
        <tbody id="lineStageShiftBody" style="font-size: 14px">
            <c:forEach items="${listsl}" var="s" varStatus="loop">
                <tr>
                    <td>${(currentPage - 1) * pageSize + loop.index + 1}</td>
                    <!--<td><fmt:formatDate value="${s.startDate}" pattern="dd-MM-yyyy"/></td> -->
                    <td>${s.shiftName}</td>
                    <td>${s.longTime}</td>
                    <td>${s.shortTime}</td>
                    <td>${s.duration}</td>
                    <td>${s.shortCount}</td>
                    <td>${s.shortStopPercentage} %</td>
                    <td>${s.errorCount}</td>
                    <td><fmt:formatDate value="${s.startDate}" pattern="dd-MM-yyyy"/></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <!-- Hour Table -->
    <table class="line-stage-table" id="lineStageHourTable" style="${timeUnit == 'Hours' ? '' : 'display:none;'} border-collapse: collapse; width: 100%;">
        <thead>
            <tr>
                <th>#</th>
                <th>Time</th>
                <th>Long Stop</th>
                <th>Short Stop</th>
                <th>Total Time</th>
                <th>Short Count</th>
                 <th>SSTP(%)</th>
                <th>Count</th>
                <th style="display: none">Date</th>
            </tr>
        </thead>
        <tbody id="lineStageHourBody" style="font-size: 14px">
            <c:forEach items="${listsl}" var="s" varStatus="loop">
                <tr>
                    <td>${(currentPage - 1) * pageSize + loop.index + 1}</td>
                    <!--<td><fmt:formatDate value="${s.startDate}" pattern="HH:mm"/></td> -->
                    <td>${s.startTime.substring(11, 16)}</td>
                    <td>${s.longTime}</td>
                    <td>${s.shortTime}</td>
                    <td>${s.duration}</td>
                    <td>${s.shortCount}</td>
                    <td>${s.shortStopPercentage} %</td>
                    <td>${s.errorCount}</td>
                    <td style="display: none"></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

<table class="user-table" id="userTable" style="border-collapse: collapse; width: 100%; ${timeUnit == 'WorkingDay' || timeUnit == 'Hours' ? '' : 'display:none;'}">
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
            <th>Total Time (m)</th>
        </tr>
    </thead>
    <tbody id="deviceTableBody" style="font-size: 14px">
        <c:forEach items="${lists}" var="i" varStatus="loop">
            <tr data-duration="${i.duration}">
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
<table class="shift-table" id="shiftTable" style="border-collapse: collapse; width: 100%; ${timeUnit == 'Shift' ? '' : 'display:none;'}">
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
            <th>Shift</th>
            <th>Total Time (m)</th>
        </tr>
    </thead>
    <tbody id="shiftTableBody" style="font-size: 14px">
        <c:forEach items="${lists}" var="i" varStatus="loop">
            <tr data-duration="${i.duration}">
                <td>${(currentPage - 1) * pageSize + loop.index + 1}</td>
                <td>${i.equipmentCode}</td>
                <td>${i.equipmentName}</td>
                <td>${i.departmentName}</td>
                <td>${i.lineName}</td>
                <td>${i.stageName}</td>
                <td>${i.content}</td>
                <td><fmt:formatDate value="${i.startDate}" pattern="yyyy-MM-dd HH:mm"/></td>
                <td><fmt:formatDate value="${i.endDate}" pattern="yyyy-MM-dd HH:mm"/></td>
                <td>${i.shiftName}</td>
                <td style="text-align: center; background-color: ${i.duration > 5 ? 'red' : ''}">${i.duration}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>
</div>

<c:if test="${empty requestScope.lists && empty requestScope.listStage && empty requestScope.listLine}">
    <p style="text-align: center; color: #666666; margin: 10px 20px;">
        No short stop or pause information found that matches your criteria.
    </p>
</c:if>

<nav id="navigation" aria-label="Page navigation" style="${timeUnit == 'WorkingDay' || timeUnit == 'Shift' || timeUnit == 'Hours' ? '' : 'display:none;'}">
    <ul class="pagination">
        <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
            <a class="page-link" href="ReportDetail?action=search&pageIndex=${currentPage - 1}&timeUnit=${timeUnit}&deviceCode=${deviceCode}&depId=${depId}&lineId=${lineId}&stageId=${stageId}&startDate=${startDate}&endDate=${endDate}&specificDate=${specificDate}" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
            </a>
        </li>
        <c:forEach begin="1" end="${totalPage}" var="i">
            <li class="page-item ${i == currentPage ? 'active' : ''}">
                <a class="page-link" href="ReportDetail?action=search&pageIndex=${i}&timeUnit=${timeUnit}&deviceCode=${deviceCode}&depId=${depId}&lineId=${lineId}&stageId=${stageId}&startDate=${startDate}&endDate=${endDate}&specificDate=${specificDate}">${i}</a>
            </li>
        </c:forEach>
        <li class="page-item ${currentPage == totalPage ? 'disabled' : ''}">
            <a class="page-link" href="ReportDetail?action=search&pageIndex=${currentPage + 1}&timeUnit=${timeUnit}&deviceCode=${deviceCode}&depId=${depId}&lineId=${lineId}&stageId=${stageId}&startDate=${startDate}&endDate=${endDate}&specificDate=${specificDate}" aria-label="Next">
                <span aria-hidden="true">&raquo;</span>
            </a>
        </li>
    </ul>
</nav>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
let searchPerformed = false; // Flag to track if search has been performed

// Function to reset the search and UI elements
function resetSearch() {
    // Reset UI elements
    document.getElementById('chartContainer').style.display = 'none';
    document.getElementById('tablesContainer').style.display = 'none';
    document.getElementById('navigation').style.display = 'none';
    document.getElementById('toggleButton').style.display = 'none';

    // Clear values from sessionStorage
    sessionStorage.clear(); // Clear all session storage values

    // Reset the searchPerformed flag
    searchPerformed = false; 
}

// Check the state on page load
window.onload = function() {
    if (!sessionStorage.getItem('visited')) {
        resetSearch(); // Reset the page state as if it's the first visit
        sessionStorage.setItem('visited', 'true'); // Mark as visited
    } else {
        // If the page has been visited before, perform necessary actions
        const isHidden = localStorage.getItem('isHidden') === 'true';
        updateRowVisibility(isHidden);
        updateChart(isHidden);
    }
    toggleInputFields(); // Initialize input fields
};

// Update row visibility based on state
function updateRowVisibility(isHidden) {
    const rows = document.querySelectorAll('#lineStageWorkingDayBody tr');
    const rowsShift = document.querySelectorAll('#lineStageShiftBody tr');
    const rowsHour = document.querySelectorAll('#lineStageHourBody tr');

    rows.forEach(row => {
        const durationCell = row.cells[4];
        const duration = parseFloat(durationCell.innerText);

        if (isHidden) {
            row.style.display = (duration > 5) ? 'none' : '';
        } else {
            row.style.display = '';
        }
    });
}

// Update the chart
function updateChart(isHidden) {
    const selectedUnit = document.querySelector('input[name="timeUnit"]:checked').value;

    let labels = [];
    let longStopData = [];
    let shortStopData = [];
    let durations = [];
    let errorCounts = []; // shortStopCount
    let shortStopPercentTage = [];
    let additionalInfo = [];

    //const rows = selectedUnit === 'WorkingDay' 
        //? document.querySelectorAll('#lineStageWorkingDayBody tr') 
        //: document.querySelectorAll('#lineStageHourBody tr');
        
     const rows = document.querySelectorAll(selectedUnit === 'WorkingDay'
        ? '#lineStageWorkingDayBody tr'
        : selectedUnit === 'Shift'
        ? '#lineStageShiftBody tr'
        : '#lineStageHourBody tr'
    );

    rows.forEach(row => {
        const labelCell = row.cells[1];
        const longStopCell = row.cells[2];
        const shortStopCell = row.cells[3];
        const durationCell = row.cells[4];
        const errorCountCell = row.cells[5];
        const shortStopPerCell = row.cells[6];
        const additionalInfoCell = row.cells[8];

        labels.push(labelCell.innerText);
        durations.push(parseFloat(durationCell.innerText));
        longStopData.push(parseFloat(longStopCell.innerText));
        shortStopData.push(parseFloat(shortStopCell.innerText));
        errorCounts.push(parseInt(errorCountCell.innerText));
        shortStopPercentTage.push(parseFloat(shortStopPerCell.innerText));
        additionalInfo.push(additionalInfoCell.innerText);
    });

    chart.data.labels = labels;
    chart.data.datasets[0].data = durations;
    chart.data.datasets[1].data = longStopData;
    chart.data.datasets[2].data = shortStopData;
    chart.data.datasets[3].data = errorCounts;
    chart.data.datasets[4].data = shortStopPercentTage;
    chart.data.datasets[0].additionalInfo = additionalInfo;

    chart.update();
}

// Toggle input fields
//function toggleInputFields() {
//    const workingDayInputs = document.getElementById('workingDayInputs');
//    const hourInputs = document.getElementById('hourInputs');
//    const isWorkingDay = document.querySelector('input[name="timeUnit"]:checked').value === 'WorkingDay';

//    workingDayInputs.style.display = isWorkingDay ? 'block' : 'none';
//    hourInputs.style.display = isWorkingDay ? 'none' : 'block';
    
//    document.getElementById('lineStageWorkingDayTable').style.display = isWorkingDay ? '' : 'none';
//    document.getElementById('lineStageHourTable').style.display = isWorkingDay ? 'none' : '';
//   document.getElementById('userTable').style.display = '';
//}
function toggleInputFields() {
    const selectedUnit = document.querySelector('input[name="timeUnit"]:checked').value;

    document.getElementById('workingDayInputs').style.display = selectedUnit === 'WorkingDay' ? 'block' : 'none';
    document.getElementById('shiftInputs').style.display = selectedUnit === 'Shift' ? 'block' : 'none';
    document.getElementById('hourInputs').style.display = selectedUnit === 'Hours' ? 'block' : 'none';

    //document.getElementById('lineStageWorkingDayTable').style.display = selectedUnit === 'WorkingDay' ? '' : 'none';
    //document.getElementById('lineStageShiftTable').style.display = selectedUnit === 'Shift' ? '' : 'none';
    //document.getElementById('lineStageHourTable').style.display = selectedUnit === 'Hours' ? '' : 'none';
}


// Listen for changes to lineId
document.getElementById('lineId').addEventListener('change', function() {
    var lineId = this.value;
    var stageDropdown = document.getElementById('stageId');

    // Clear current options for stages
    stageDropdown.innerHTML = '<option value="">-- Select Stage --</option>';

    if (lineId) {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', 'ReportDetail?lineId=' + lineId, true);
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var stageList = JSON.parse(xhr.responseText);
                stageList.forEach(function(stage) {
                    var option = document.createElement('option');
                    option.value = stage.id;
                    option.textContent = stage.name;
                    stageDropdown.appendChild(option);
                });
            }
        };
        xhr.send();
    }
});

// Manage row visibility in device table
document.addEventListener('DOMContentLoaded', function() {
    const toggleButton = document.getElementById('toggleButton');
    const rows = document.querySelectorAll('#deviceTableBody tr');
    const rowShift = document.querySelectorAll('#shiftTableBody tr');
    const hiddenRows = JSON.parse(localStorage.getItem('hiddenRows')) || [];

    function initRows() {
        rows.forEach((row, index) => {
            const duration = parseInt(row.getAttribute('data-duration'));
            if (hiddenRows.includes(index) && duration > 5) {
                row.classList.add('hidden');
            }
        });
        rowShift.forEach((row, index) => {
            const duration = parseInt(row.getAttribute('data-duration'));
            if (hiddenRows.includes(index) && duration > 5) {
                row.classList.add('hidden');
            }
        });
    }

    function toggleRows() {
        let anyHidden = false;
        rows.forEach((row, index) => {
            const duration = parseInt(row.getAttribute('data-duration'));
            if (duration > 5) {
                if (row.classList.contains('hidden')) {
                    row.classList.remove('hidden');
                    hiddenRows.splice(hiddenRows.indexOf(index), 1);
                    anyHidden = true;
                } else {
                    row.classList.add('hidden');
                    hiddenRows.push(index);
                }
            }
        });

        localStorage.setItem('hiddenRows', JSON.stringify(hiddenRows));

        toggleButton.innerHTML = anyHidden 
            ? '<i class="fa-solid fa-eye"></i>' 
            : '<i class="fa-solid fa-eye-slash"></i>';
    }

    toggleButton.addEventListener('click', toggleRows);
    initRows();
});

//Logout function
function logout() {
    sessionStorage.clear(); 
    window.location.href = "http://localhost:8080/ssmqrcode/";
}

// Login function (optional)
function login() {
    sessionStorage.clear();
    window.location.href = "http://localhost:8080/ssmqrcode/"; 
}

// Function to handle search submission
function handleSearch(event) {
    event.preventDefault(); 
    searchPerformed = true;

    // Prepare form data
    const formData = new FormData(document.getElementById('searchForm'));

    // Make an AJAX request
    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'ReportDetail', true);
    xhr.onload = function() {
        if (xhr.status === 200) {
            const response = JSON.parse(xhr.responseText);
            updateTableAndChart(response);
        } else {
            console.error('Search request failed:', xhr.statusText);
        }
    };
    xhr.send(formData);
}

// Reset inactivity timer
let inactivityTimer;
const resetTime = 30000; // Set time (30 seconds) for inactivity

function resetInactivityTimer() {
    clearTimeout(inactivityTimer);
    inactivityTimer = setTimeout(resetSearch, resetTime);
}

// Event listeners to reset the inactivity timer
document.addEventListener('mousemove', resetInactivityTimer);
document.addEventListener('keypress', resetInactivityTimer);
document.addEventListener('click', resetInactivityTimer);

// Start the inactivity timer on page load
resetInactivityTimer();
</script>
</body>
</html>