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
    
          /* Container with horizontal scroll */
      .table-container {
          overflow-x: auto;
          margin: 0 auto;
          padding: 10px;
      }

      /* Header grouping */
      .line-stage-table thead tr:nth-child(1) th {
          background-color: #2c3e50;
          color: #fff;
          font-weight: bold;
          text-align: center;
          padding: 12px;
      }

      .line-stage-table thead tr:nth-child(2) th {
          background-color: #34495e;
          color: #fff;
          font-weight: normal;
      }

      .line-stage-table td, .line-stage-table th {
          padding: 8px;
          border: 1px solid #ddd;
      }

      /* Row hover effect */
      .line-stage-table tbody tr:hover td {
          background-color: #f5f5f5;
      }

      /* Responsive width adjustment */
      th, td {
          white-space: nowrap;
          text-align: center;
      }

      /* Hide Date Column */
      th[style*="display: none"], td[style*="display: none"] {
          display: none;
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
    
    .line-stage-table {
       table-layout: fixed;
       width: 100%;
    }
    
    .line-stage-table th {
       font-size: 8px;
    }
    
    #lineStageWorkingDayBody {
       font-size: 12px;
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
    #dropdownMenu {
         display: none;
         position: fixed;
         background-color: white;
         min-width: 150px;
         box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
         z-index: 1;
     }

     #dropdownMenu button {
         color: black;
         padding: 10px;
         text-decoration: none;
         display: block;
         border: none;
         background: none;
         cursor: pointer;
         width: 100%;
     }

     #dropdownMenu button:hover {
         background-color: #ddd;
     }
     #lineStageWorkingDayTable {
        width: 200px;
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
    <form id="searchForm" action="ReportDetailNew" method="POST">
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

<div style="position: relative; display: inline-block;">
    <button id="menuButton" style="background: none; border: none; cursor: pointer; font-size: 24px;">
        &#9776; <!-- Biểu tượng ba gạch -->
    </button>
    <div id="dropdownMenu" style="display: none; position: absolute; left: 0; background-color: white; border: 1px solid #ccc; z-index: 1;">
        <button id="downloadBtn">Tải ảnh (PNG)</button>
        <button id="exportExcelBtn">Xuất sang Excel</button>
    </div>
</div>
<canvas id="errorTimeChart" width="550" height="150"></canvas>

   
</div>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.17.0/xlsx.full.min.js"></script>

<script>
var ctx = document.getElementById('errorTimeChart').getContext('2d');

var sampleLabels = ['Label1', 'Label2', 'Label3']; 
var shortPercentage = [10, 3, 5]; 
var longPercentage = [2, 1, 4]; 
var ppdPercentage = [1, 2, 3];
var dccPercentage = [5, 1, 2]; 
var dmtPercentage = [20, 30, 10]; 
var nrPercentage = [4, 5, 10]; 
//var sampleColors = sampleData.map(duration => duration > 5 ? 'rgba(54, 162, 235, 0.2)' : 'rgba(54, 162, 235, 0.2)');


var chart = new Chart(ctx, {
    type: 'bar',
    data: {
        labels: sampleLabels, 
        datasets: [{
            label: 'Dừng ngắn',
            data: shortPercentage, 
            backgroundColor: 'rgba(119, 164, 255, 0.2)',
            borderColor: 'rgba(119, 164, 255, 1)', 
            yAxisID: 'yLeft',
            borderWidth: 1,
            barThickness: 20,
            yAxisID: 'efficiency'
        },
        {
            label: 'Dừng dài',
            data: longPercentage,
            backgroundColor: 'rgba(255, 99, 132, 0.2)', 
            borderColor: 'rgba(255, 99, 132, 1)',
            borderWidth: 1,
            barThickness: 20,
            yAxisID: 'efficiency'
        },
        {
            label: 'Phế phẩm',
            data: ppdPercentage,
            backgroundColor: 'rgb(60, 179, 113)', 
            borderColor: 'rgba(60, 179, 113, 1)',
            borderWidth: 1,
            barThickness: 20,
            yAxisID: 'efficiency'
        },
        {
            label: 'Đầu cuối ca',
            data: dccPercentage, 
            backgroundColor: 'rgba(255, 206, 86, 0.2)', 
            borderColor: 'rgba(255, 206, 86, 1)', 
            borderWidth: 1,
            barThickness: 20,
            yAxisID: 'efficiency'
        },
        {
            label: 'Đổi mã thay dao',
            data: dmtPercentage, 
            backgroundColor: 'rgba(75, 0, 130, 0.2)', 
            borderColor: 'rgba(75, 0, 130, 1)', 
            borderWidth: 1,
            barThickness: 20,
            yAxisID: 'efficiency'
        },
        {
            label: 'Ngoài ra',
            data: nrPercentage, 
            backgroundColor: 'rgba(121, 114, 117, 0.8)', 
            borderColor: 'rgba(121, 114, 117, 1)', 
            borderWidth: 1,
            barThickness: 20,
            borderRadius: 8,
            yAxisID: 'efficiency'
        },
        {
            type: 'line',
            label: 'OEE',
            data: shortPercentage,
            borderColor: 'rgba(75, 192, 192, 1)',
            backgroundColor: 'rgba(75, 192, 192, 0.1)',
            fill: false,
            pointBackgroundColor: 'rgba(75, 192, 192, 1)',
            pointRadius: 5,
            tension: 0.3,
            yAxisID: 'efficiency'
        },
        {
            type: 'line',
            label: 'Tỷ lệ mất mát',
            data: shortPercentage,
            borderColor: 'rgba(255, 99, 132, 1)',
            backgroundColor: 'rgba(255, 99, 132, 0.1)',
            fill: false,
            pointBackgroundColor: 'rgba(255, 99, 132, 1)',
            pointRadius: 5,
            tension: 0.3,
            yAxisID: 'efficiency' 
        } ]
    },
    options: {
        responsive: true,
        scales: {
            x: { stacked: true },
            y: {
                beginAtZero: true,
                max: 10,
                stacked: true,
            },
            efficiency: {
                type: "linear",
                position: "left",
                beginAtZero: true,
                max: 10,
                title: {
                    display: true,
                    text: "Efficiency (%)",
                },
                grid: {
                    drawOnChartArea: false,
                },
            },
        },
        plugins: {
            tooltip: {
                callbacks: {
                    title: function(tooltipItem) {
                        return tooltipItem[0].label;
                    },
                    label: function(tooltipItem) {
                        var datasetLabel = tooltipItem.dataset.label || '';
                        var value = tooltipItem.raw;
                        var time = tooltipItem.label; 
                        var count = tooltipItem.dataset.data[tooltipItem.index]; 

                        return datasetLabel + ': ' + value + '% (Time: ' + time + ', Count: ' + count + ')';
                    }
                }
            },
            datalabels: {
                display: function (context) {
                    return context.dataset.type === 'line'; 
                },
                formatter: function (value) {
                    return `${value}%`;
                },
                color: 'black',
                align: 'top',
                anchor: 'end'
            }
        }
    }
});
</script>


</main>
<div>
    <button id="toggleButton" class="btn btn-secondary btn-hide" style="${timeUnit == 'WorkingDay' || timeUnit == 'Shift' || timeUnit == 'Hours' ? '' : 'display:none;'}">
        <i class="fa-solid fa-eye"></i>
    </button>
</div>
<div id="tablesContainer" class="class="table-container"">
<!-- Working Day Table -->
    <table class="line-stage-table" id="lineStageWorkingDayTable" style="${timeUnit == 'WorkingDay' ? '' : 'display:none;'} border-collapse: collapse; width: 100%;">
        <thead>
            <tr>
                <th style="width: 50px;">#</th>
                <th>Day</th>
                <th>Short Stop</th>
                <th>SSTP(%)</th>
                <th>Short Count</th>
                <th>Long Stop</th>
                <th>LSTP(%)</th>
                <th>Long Count</th>
                <th>PP</th>
                <th>PPTP(%)</th>
                <th>PP Count</th>
                <th>DCC</th>
                <th>DCCTP(%)</th>
                <th>DCC Count</th>
                <th>DMTD</th>
                <th>DMTDTP(%)</th>
                <th>DMTD Count</th>
                <th>NR</th>
                <th>NRTP(%)</th>
                <th>NR Count</th>
                <th style="display: none">Date</th>
            </tr>
        </thead>
        <tbody id="lineStageWorkingDayBody">
            <c:forEach items="${listsl}" var="s" varStatus="loop">
                <tr>
                    <td>${(currentPage - 1) * pageSize + loop.index + 1}</td>
                    <td>${s.day}</td>
                    <td>${s.shortStop}</td>
                    <td>${s.shortPercentage} %</td>
                    <td>${s.shortCount}</td>
                    <td>${s.longStop}</td>
                    <td>${s.longPercentage} %</td>
                    <td>${s.longCount}</td>
                    <td>${s.ppStop}</td>
                    <td>${s.ppPercentage} %</td>
                    <td>${s.ppCount}</td>
                    <td>${s.dccStop}</td>
                    <td>${s.dccPercentage} %</td>
                    <td>${s.dccCount}</td>
                    <td>${s.dmtdStop}</td>
                    <td>${s.dmtdPercentage} %</td>
                    <td>${s.dmtdCount}</td>
                    <td>${s.nrStop}</td>
                    <td>${s.nrPercentage} %</td>
                    <td>${s.nrCount}</td>
                    <th style="display: none"></th>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    
    <!-- Shift Table -->
    <table class="line-stage-table" id="lineStageShiftTable" style="${timeUnit == 'Shift' ? '' : 'display:none;'} border-collapse: collapse; width: 100%;">
        <thead>
            <tr>
                <th style="width: 50px;">#</th>
                <!--<th>Day</th>-->
                <th>Shift</th>
                <th>Short Stop</th>
                <th>SSTP(%)</th>
                <th>Short Count</th>
                <th>Long Stop</th>
                <th>LSTP(%)</th>
                <th>Long Count</th>
                <th>PP</th>
                <th>PPTP(%)</th>
                <th>PP Count</th>
                <th>DCC</th>
                <th>DCCTP(%)</th>
                <th>DCC Count</th>
                <th>DMTD</th>
                <th>DMTDTP(%)</th>
                <th>DMTD Count</th>
                <th>NR</th>
                <th>NRTP(%)</th>
                <th>NR Count</th>
                <th style="width: 100px;">Day</th>
            </tr>
        </thead>
        <tbody id="lineStageShiftBody" style="font-size: 14px">
            <c:forEach items="${listsl}" var="s" varStatus="loop">
                <tr>
                    <td>${(currentPage - 1) * pageSize + loop.index + 1}</td>
                    <!--<td><fmt:formatDate value="${s.startDate}" pattern="dd-MM-yyyy"/></td> -->
                    <td>${s.shiftName}</td>
                    <td>${s.shortStop}</td>
                    <td>${s.shortPercentage} %</td>
                    <td>${s.shortCount}</td>
                    <td>${s.longStop}</td>
                    <td>${s.longPercentage} %</td>
                    <td>${s.longCount}</td>
                    <td>${s.ppStop}</td>
                    <td>${s.ppPercentage} %</td>
                    <td>${s.ppCount}</td>
                    <td>${s.dccStop}</td>
                    <td>${s.dccPercentage} %</td>
                    <td>${s.dccCount}</td>
                    <td>${s.dmtdStop}</td>
                    <td>${s.dmtdPercentage} %</td>
                    <td>${s.dmtdCount}</td>
                    <td>${s.nrStop}</td>
                    <td>${s.nrPercentage} %</td>
                    <td>${s.nrCount}</td>
                    <td>${s.day}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <!-- Hour Table -->
    <table class="line-stage-table" id="lineStageHourTable" style="${timeUnit == 'Hours' ? '' : 'display:none;'} border-collapse: collapse; width: 100%;">
        <thead>
            <tr>
                <th style="width: 50px">#</th>
                <th>Time</th>
                <th>Short Stop</th>
                <th>SSTP(%)</th>
                <th>Short Count</th>
                <th>Long Stop</th>
                <th>LSTP(%)</th>
                <th>Long Count</th>
                <th>PP</th>
                <th>PPTP(%)</th>
                <th>PP Count</th>
                <th>DCC</th>
                <th>DCCTP(%)</th>
                <th>DCC Count</th>
                <th>DMTD</th>
                <th>DMTDTP(%)</th>
                <th>DMTD Count</th>
                <th>NR</th>
                <th>NRTP(%)</th>
                <th>NR Count</th>
                <th style="display: none">Date</th>
            </tr>
        </thead>
        <tbody id="lineStageHourBody" style="font-size: 14px">
            <c:forEach items="${listsl}" var="s" varStatus="loop">
                <tr>
                    <td>${(currentPage - 1) * pageSize + loop.index + 1}</td>
                    <!--<td><fmt:formatDate value="${s.startDate}" pattern="HH:mm"/></td> -->
                    <td>${s.startTime.substring(11, 16)}</td>
                    <td>${s.shortStop}</td>
                    <td>${s.shortPercentage} %</td>
                    <td>${s.shortCount}</td>
                    <td>${s.longStop}</td>
                    <td>${s.longPercentage} %</td>
                    <td>${s.longCount}</td>
                    <td>${s.ppStop}</td>
                    <td>${s.ppPercentage} %</td>
                    <td>${s.ppCount}</td>
                    <td>${s.dccStop}</td>
                    <td>${s.dccPercentage} %</td>
                    <td>${s.dccCount}</td>
                    <td>${s.dmtdStop}</td>
                    <td>${s.dmtdPercentage} %</td>
                    <td>${s.dmtdCount}</td>
                    <td>${s.nrStop}</td>
                    <td>${s.nrPercentage} %</td>
                    <td>${s.nrCount}</td>
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
            <th>Line</th>
            <th>Stage</th>
            <th>Error Description</th>
            <th>Type</th>
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
                <td>${i.departmentName}</td>
                <td>${i.lineName}</td>
                <td>${i.stageName}</td>
                <td>${i.content}</td>
                <td>${i.typeName}</td>
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
            <th>Line</th>
            <th>Stage</th>
            <th>Error Description</th>
            <th>Type</th>
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
                <td>${i.lineName}</td>
                <td>${i.stageName}</td>
                <td>${i.content}</td>
                <td>${i.typeName}</td>
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
            <a class="page-link" href="ReportDetailNew?action=search&pageIndex=${currentPage - 1}&timeUnit=${timeUnit}&deviceCode=${deviceCode}&depId=${depId}&lineId=${lineId}&stageId=${stageId}&startDate=${startDate}&endDate=${endDate}&specificDate=${specificDate}" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
            </a>
        </li>
        <c:forEach begin="1" end="${totalPage}" var="i">
            <li class="page-item ${i == currentPage ? 'active' : ''}">
                <a class="page-link" href="ReportDetailNew?action=search&pageIndex=${i}&timeUnit=${timeUnit}&deviceCode=${deviceCode}&depId=${depId}&lineId=${lineId}&stageId=${stageId}&startDate=${startDate}&endDate=${endDate}&specificDate=${specificDate}">${i}</a>
            </li>
        </c:forEach>
        <li class="page-item ${currentPage == totalPage ? 'disabled' : ''}">
            <a class="page-link" href="ReportDetailNew?action=search&pageIndex=${currentPage + 1}&timeUnit=${timeUnit}&deviceCode=${deviceCode}&depId=${depId}&lineId=${lineId}&stageId=${stageId}&startDate=${startDate}&endDate=${endDate}&specificDate=${specificDate}" aria-label="Next">
                <span aria-hidden="true">&raquo;</span>
            </a>
        </li>
    </ul>
</nav>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.17.0/xlsx.full.min.js"></script>

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
    let shortStopData = [];
    let longStopData = [];
    let ppStopData = [];
    let dccStopData = [];
    let dmtdStopData = [];
    let nrStopData = [];
    let additionalInfo = [];
        
     const rows = document.querySelectorAll(selectedUnit === 'WorkingDay'
        ? '#lineStageWorkingDayBody tr'
        : selectedUnit === 'Shift'
        ? '#lineStageShiftBody tr'
        : '#lineStageHourBody tr'
    );

    rows.forEach(row => {
        const labelCell = row.cells[1];
        const shortStopCell = row.cells[3];
        const longStopCell = row.cells[6];
        const ppCell = row.cells[9];
        const dccCell = row.cells[12];
        const dmtdCell = row.cells[15];
        const nrCell = row.cells[18];
        const additionalInfoCell = row.cells[20];

        labels.push(labelCell.innerText);
        shortStopData.push(parseFloat(shortStopCell.innerText));
        longStopData.push(parseFloat(longStopCell.innerText));
        ppStopData.push(parseFloat(ppCell.innerText));
        dccStopData.push(parseInt(dccCell.innerText));
        dmtdStopData.push(parseFloat(dmtdCell.innerText));
        nrStopData.push(parseFloat(nrCell.innerText));
        additionalInfo.push(additionalInfoCell.innerText);
    });

    chart.data.labels = labels;
    chart.data.datasets[0].data = shortStopData;
    chart.data.datasets[1].data = longStopData;
    chart.data.datasets[2].data = ppStopData;
    chart.data.datasets[3].data = dccStopData;
    chart.data.datasets[4].data = dmtdStopData;
    chart.data.datasets[5].data = nrStopData;
    chart.data.datasets[0].additionalInfo = additionalInfo;

    chart.update();
}

function toggleInputFields() {
    const selectedUnit = document.querySelector('input[name="timeUnit"]:checked').value;

    document.getElementById('workingDayInputs').style.display = selectedUnit === 'WorkingDay' ? 'block' : 'none';
    document.getElementById('shiftInputs').style.display = selectedUnit === 'Shift' ? 'block' : 'none';
    document.getElementById('hourInputs').style.display = selectedUnit === 'Hours' ? 'block' : 'none';

}


// Listen for changes to lineId
document.getElementById('lineId').addEventListener('change', function() {
    var lineId = this.value;
    var stageDropdown = document.getElementById('stageId');

    // Clear current options for stages
    stageDropdown.innerHTML = '<option value="">-- Select Stage --</option>';

    if (lineId) {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', 'ReportDetailNew?lineId=' + lineId, true);
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

//Define these variables at a higher scope if they're updated in the handleSearch function
let exportLabels = [];
let exportSampleData = [];
let exportLongData = [];
let exportShortData = [];
let exportErrorCounts = [];

// Update your search handling function
function handleSearch(event) {
    event.preventDefault();
    searchPerformed = true;

    const formData = new FormData(document.getElementById('searchForm'));

    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'ReportDetailNew', true);
    xhr.onload = function() {
        if (xhr.status === 200) {
            const response = JSON.parse(xhr.responseText);
            updateTableAndChart(response);

            // Capture export data
            exportLabels = response.labels;
            exportSampleData = response.sampleData;
            exportLongData = response.longData;
            exportShortData = response.shortData;
            exportErrorCounts = response.errorCounts;

            // Debug logs
            console.log('Export Data:', {exportLabels, exportSampleData, exportLongData, exportShortData, exportErrorCounts});
        } else {
            console.error('Search request failed:', xhr.statusText);
        }
    };
    xhr.send(formData);
}

document.getElementById('exportExcelBtn').addEventListener('click', function() {
    const workbook = XLSX.utils.book_new();
    const worksheet = XLSX.utils.aoa_to_sheet([
        ['Labels', 'Total Time (min)', 'Long Stops (min)', 'Short Stops (min)', 'Error Counts'], 
        ...exportLabels.map((label, index) => [
            label,
            exportSampleData[index] || 0,
            exportLongData[index] || 0,
            exportShortData[index] || 0,
            exportErrorCounts[index] || 0
        ])
    ]);
    
    XLSX.utils.book_append_sheet(workbook, worksheet, 'Chart Data');
    XLSX.writeFile(workbook, 'chart_data.xlsx');
});

// Reset inactivity timer
//let inactivityTimer;
//const resetTime = 30000; // Set time (30 seconds) for inactivity

//function resetInactivityTimer() {
    //clearTimeout(inactivityTimer);
    //inactivityTimer = setTimeout(resetSearch, resetTime);
//}

// Event listeners to reset the inactivity timer
//document.addEventListener('mousemove', resetInactivityTimer);
//document.addEventListener('keypress', resetInactivityTimer);
//document.addEventListener('click', resetInactivityTimer);

// Start the inactivity timer on page load
resetInactivityTimer();
</script>
<script>
document.getElementById('menuButton').addEventListener('click', function() {
    const menu = document.getElementById('dropdownMenu');
    menu.style.display = menu.style.display === 'block' ? 'none' : 'block';
});

// Đóng menu nếu người dùng nhấp ra ngoài
window.addEventListener('click', function(event) {
    if (!event.target.matches('#menuButton')) {
        const dropdowns = document.getElementById('dropdownMenu');
        if (dropdowns.style.display === 'block') {
            dropdowns.style.display = 'none';
        }
    }
});

// Tải ảnh (PNG) của biểu đồ
document.getElementById('downloadBtn').addEventListener('click', function() {
    const link = document.createElement('a');
    link.href = document.getElementById('errorTimeChart').toDataURL('image/png');
    link.download = 'chart.png';
    link.click();
});
</script>

</body>
</html>