<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
            width: 100%;
            padding: 20px;
            background: #fff;
            border-radius: 5px;
            margin: 20px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        .search-input {
            width: 350px;
            height: 46px;
            margin-right: 10px;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 3px;
            margin-bottom: 5px;
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
        
        .btn-hide {
            float: right;
            margin-right: 20px;
            margin-bottom: 20px;
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
<%@ include file="components/navigation.jsp" %>
<header>
    <h1>Quản Lý Thiết Bị</h1>
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
                <input type="radio" name="timeUnit" value="Hours" 
                    ${timeUnit == 'Hours' ? 'checked' : ''} 
                    onclick="toggleInputFields()"> Giờ
            </label>
        </div>

        <div style="display: flex">
            <div style="width: 50%">
                <input type="text" name="deviceCode" 
                    value="${not empty deviceCode ? deviceCode : ''}" 
                    placeholder="Mã thiết bị" class="search-input">
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
            </div>

            <div style="width: 50%">
                <!-- Input fields for Working Day -->
                <div id="workingDayInputs" class="time-inputs">
                    <label style="margin-right: 20px; padding: 10px;">From: </label>
                    <input type="date" name="startDate"
                        value="${not empty startDate ? startDate : ''}" class="search-input" />
                    <br/>
                    <label style="margin-right: 40px; padding: 10px;">To: </label>
                    <input type="date" name="endDate"
                        value="${not empty endDate ? endDate : ''}" class="search-input" />
                </div>

                <!-- Input fields for Hours -->
                <div id="hourInputs" class="time-inputs" style="display: none;">
                    <label style="margin-right: 15px; padding: 10px;">Target Date: </label>
                    <input type="date" name="specificDate"
                        value="${not empty specificDate ? specificDate : ''}" class="search-input" />
                </div>
            </div>
        </div>

        <button type="submit" class="btn btn-primary">Tìm Kiếm</button>
        <button type="button" class="btn btn-secondary">Đặt Lại</button>
    </form>
</div> 


<%--    <%--%>
<%--        java.util.List<ErrorHistory> errorDataList = (java.util.List<ErrorHistory>) request.getAttribute("deviceList");--%>
<%--    %>--%>

   <div class="chart-container">
        <div style="display: flex; justify-content: space-between; align-items: center;">
            <h2 style="margin: 0;">Biểu Đồ Thống Kê</h2>
            <h6 style="margin: 0; color: red; font-size: 12px;">ErrorCount: ${errorMessage}</h6>
        </div>
        <canvas id="errorTimeChart" width="550" height="150"></canvas>

        <div class="legend">
            <div style="display: flex; align-items: center;">
                <div style="width: 20px; height: 20px; background-color: rgba(255, 99, 132, 0.2); border: 1px solid rgba(255, 99, 132, 1); margin-right: 5px;"></div>
                <span style="margin-right: 20px;">Duration > 5 minutes</span>
                <div style="width: 20px; height: 20px; background-color: rgba(54, 162, 235, 0.2); border: 1px solid rgba(54, 162, 235, 1); margin-right: 5px;"></div>
                <span>Duration <= 5 minutes</span>
            </div>
        </div>
    </div>
    
    <script>
        function toggleInputFields() {
            const workingDayInputs = document.getElementById('workingDayInputs');
            const hourInputs = document.getElementById('hourInputs');
            const isWorkingDay = document.querySelector('input[name="timeUnit"]:checked').value === 'WorkingDay';

            // Show or hide inputs based on selected radio button
            if (isWorkingDay) {
                workingDayInputs.style.display = 'block';
                hourInputs.style.display = 'none';
            } else {
                workingDayInputs.style.display = 'none';
                hourInputs.style.display = 'block';
            }
        }

        var ctx = document.getElementById('errorTimeChart').getContext('2d');

        // Initialize the chart
        var chart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: [], // To be filled based on selection
                datasets: [{
                    label: 'Downtime (minutes)',
                    data: [],
                    backgroundColor: [],
                    borderColor: [],
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
</main>

<button id="toggleButton" class="btn btn-secondary btn-hide"><i class="fa-solid fa-eye"></i></button>

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

<nav aria-label="Page navigation">
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

<footer>
    <p>Bản quyền © 2024</p>
</footer>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
//Toggle functionality
const toggleButton = document.getElementById('toggleButton');

// Load visibility state from localStorage
const isHidden = localStorage.getItem('isHidden') === 'true';
updateRowVisibility(isHidden);
updateChart(isHidden);

// Event listener for toggle button
toggleButton.onclick = function() {
    const currentState = localStorage.getItem('isHidden') === 'true';
    const newState = !currentState;

    localStorage.setItem('isHidden', newState); // Save the new state
    updateRowVisibility(newState); // Update the row visibility
    updateChart(newState); // Update chart based on the new state

    // Toggle button text
    toggleButton.innerText = newState ? 'Show' : 'Hide';
};

// Update row visibility based on state
function updateRowVisibility(isHidden) {
    const rows = document.querySelectorAll('#deviceTableBody tr');

    rows.forEach(row => {
        const durationCell = row.cells[9]; // Adjust index based on your table structure
        const duration = parseFloat(durationCell.innerText);

        if (isHidden) {
            // Hide rows with duration > 5
            row.style.display = (duration > 5) ? 'none' : '';
        } else {
            row.style.display = ''; // Show all rows
        }
    });
}

// Update the chart based on visible rows
function updateChart(isHidden) {
    const rows = document.querySelectorAll('#deviceTableBody tr');
    const filteredData = [];
    const filteredLabels = [];
    const filteredColors = [];

    rows.forEach(row => {
        const durationCell = row.cells[9]; // Adjust index based on your table structure
        const duration = parseFloat(durationCell.innerText);

        // Only consider durations that are <= 5 or showing the chart normally
        if (!isHidden || (isHidden && duration <= 5)) {
            filteredData.push(duration);
            const labelCell = row.cells[7]; // Adjust index based on your table structure
            filteredLabels.push(labelCell.innerText); // Push corresponding label

            // Assign color based on the duration for the filtered data
            if (duration > 5) {
                filteredColors.push('rgba(255, 99, 132, 0.2)'); // Red
            } else {
                filteredColors.push('rgba(54, 162, 235, 0.2)'); // Blue
            }
        }
    });

    // Update chart with filtered data and colors
    chart.data.labels = filteredLabels; // Update the labels in the chart
    chart.data.datasets[0].data = filteredData; // Update the data in the chart
    chart.data.datasets[0].backgroundColor = filteredColors; // Update the colors
    chart.data.datasets[0].borderColor = filteredColors.map(color => color.replace('0.2', '1')); // Update border colors
    chart.update(); // Refresh the chart
}


function toggleInputFields() {
    const workingDayInputs = document.getElementById('workingDayInputs');
    const hourInputs = document.getElementById('hourInputs');
    const isWorkingDay = document.querySelector('input[name="timeUnit"]:checked').value === 'WorkingDay';

    // Show or hide inputs based on selected radio button
    if (isWorkingDay) {
        workingDayInputs.style.display = 'block';
        hourInputs.style.display = 'none';
    } else {
        workingDayInputs.style.display = 'none';
        hourInputs.style.display = 'block';
    }
}

// Ensure the right inputs are displayed on initial page load
window.onload = function() {
    toggleInputFields();
};

</script>

</body>
</html>