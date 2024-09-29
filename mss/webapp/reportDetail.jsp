<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 9/29/2024
  Time: 12:41 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý thiết bị</title>
    <link rel="stylesheet" href="styles.css">
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
        <form id="searchForm">
            <input type="text" name="deviceCode" placeholder="Mã thiết bị" class="search-input">
            <select name="room" class="search-input">
                <option value="">-- Chọn Phòng --</option>
                <c:forEach var="d" items="${listd}">
                    <option value="${d.id}">${d.name}</option>
                </c:forEach>
            </select>

            <select name="line" class="search-input">
                <option value="">-- Chọn Line --</option>
                <c:forEach var="l" items="${listl}">
                    <option value="${l.id}">${l.name}</option>
                </c:forEach>
            </select>

            <select name="stage" class="search-input">
                <option value="">-- Chọn Công Đoạn --</option>
                <c:forEach var="s" items="${listst}">
                    <option value="${s.stageId}">${stage.stageName}</option>
                </c:forEach>
            </select>
            <button type="button" class="btn btn-primary" onclick="search()">Tìm Kiếm</button>
            <button type="button" class="btn btn-secondary" onclick="resetSearch()">Đặt Lại</button>
        </form>
    </div>

    <div class="chart-container">
        <h2>Biểu Đồ Thống Kê</h2>
        <canvas id="chart" class="chart"></canvas>
        <p class="summary-text" id="summaryText">Tổng thời gian dừng: 0 phút (0%)</p>
    </div>

    <table>
        <thead>
        <tr>
            <th>#</th>
            <th>Device Code</th>
            <th>Device Name</th>
            <th>Line</th>
            <th>Stage</th>
            <th>Error Description</th>
            <th>Start Date</th>
            <th>End Date</th>
            <th>TotalTime(m)</th>
        </tr>
        </thead>
        <tbody id="deviceTableBody">

        </tbody>
    </table>
</main>

<footer>
    <p>Bản quyền © 2024</p>
</footer>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
    function search() {
        // Lấy dữ liệu từ form
        const form = document.getElementById('searchForm');
        const formData = new FormData(form);

        updateChartAndTable(formData);
    }

    let myChart; // Khai báo biến biểu đồ

    function updateChartAndTable(data) {
        const summaryText = document.getElementById('summaryText');
        const tableBody = document.getElementById('deviceTableBody');

        // Simulated data for devices with occurrences and total downtime
        const devices = [
            { id: 1, code: 'D001', name: 'Thiết bị A', occurrences: 3, totalDuration: 90 }, // Simulated total downtime for 3 occurrences
            { id: 2, code: 'D002', name: 'Thiết bị B', occurrences: 1, totalDuration: 15 },
            // Add more simulated devices if needed
        ];

        // Calculate total occurrences and total downtime
        const totalOccurrences = devices.reduce((total, device) => total + device.occurrences, 0);
        const totalDuration = devices.reduce((total, device) => total + device.totalDuration, 0);

        // Update summary text
        <%--summaryText.textContent = `Tổng thời gian dừng: ${totalDuration} phút (${((totalDuration / 100) * 100).toFixed(0)}%)`; // Assuming 100 is max time for calculation--%>

        // Clear table before adding new data
        tableBody.innerHTML = '';

        // Populate the table
        devices.forEach(device => {
            const row = document.createElement('tr');
            row.innerHTML = `
            <td>${device.id}</td>
            <td>${device.code}</td>
            <td>${device.name}</td>
            <td>${device.occurrences}</td> <!-- Add occurrences -->
            <td>${device.totalDuration}</td> <!-- Add total downtime duration -->
        `;
            tableBody.appendChild(row);
        });

        // Prepare data for the chart
        const labels = devices.map(device => device.name);
        const durations = devices.map(device => device.totalDuration);
        const percentages = devices.map(device => ((device.totalDuration / totalDuration) * 100).toFixed(2)); // Tính tỷ lệ phần trăm
        const occurrences = devices.map(device => device.occurrences);

        // Clear old chart if exists
        if (myChart) {
            myChart.destroy();
        }

        // Create new chart
        const ctx = document.getElementById('chart').getContext('2d');
        myChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [
                    {
                        label: 'Tỷ lệ phần trăm (%)',
                        data: percentages,
                        backgroundColor: 'rgba(75, 192, 192, 0.2)',
                        borderColor: 'rgba(75, 192, 192, 1)',
                        borderWidth: 1,
                        yAxisID: 'y', // Use the primary y-axis for percentages
                    },
                    {
                        label: 'Thời gian dừng (phút)',
                        data: durations,
                        backgroundColor: 'rgba(255, 99, 132, 0.2)',
                        borderColor: 'rgba(255, 99, 132, 1)',
                        borderWidth: 1,
                        yAxisID: 'y', // Use the same primary y-axis for downtime
                    },
                    {
                        label: 'Số lần dừng',
                        data: occurrences,
                        backgroundColor: 'rgba(153, 102, 255, 0.2)',
                        borderColor: 'rgba(153, 102, 255, 1)',
                        borderWidth: 2,
                        type: 'line', // Use line type for occurrences
                        fill: false,
                        yAxisID: 'y1', // Use a secondary y-axis for occurrences
                    }
                ]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true,
                        title: {
                            display: true,
                            text: 'Tỷ lệ phần trăm (%) / Thời gian dừng (phút)',
                        }
                    },
                    y1: {
                        beginAtZero: true,
                        position: 'right',
                        title: {
                            display: true,
                            text: 'Số lần dừng',
                        },
                        grid: {
                            drawOnChartArea: false // Disable grid for the secondary y-axis
                        }
                    }
                }
            }
        });
    }


    function resetSearch() {
        // Đặt lại form tìm kiếm
        document.getElementById('searchForm').reset();
        document.getElementById('deviceTableBody').innerHTML = '';
        document.getElementById('summaryText').textContent = 'Tổng thời gian dừng: 0 phút (0%)';
    }

</script>
</body>
</html>

