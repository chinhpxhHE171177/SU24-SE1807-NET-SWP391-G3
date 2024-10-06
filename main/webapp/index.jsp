
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Equipment Downtime Dashboard</title>
    <link rel="stylesheet" href="styles.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }

        .container {
            margin: auto;
            overflow: hidden;
        }

        header {
            background: #333;
            color: #fff;
            padding: 20px 0;
            text-align: center;
        }

        .chart-container {
            margin: 20px 0;
        }

        /*canvas {*/
        /*    width: 100% !important;*/
        /*    height: auto !important;*/
        /*}*/
    </style>
</head>

<%@include file="components/navigation.jsp"%>
<body>
<div class="container" style="width: 96%">
    <header>
        <h1>Equipment Downtime Dashboard</h1>
    </header>
    <main>
        <section class="chart-container">
            <canvas id="downtimeChart"></canvas>
        </section>
    </main>
</div>

<script>
    const ctx = document.getElementById('downtimeChart').getContext('2d');
    const downtimeChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: [], // This will be populated by fetchData()
            datasets: [{
                label: 'Total Downtime (minutes)',
                data: [], // This will be populated by fetchData()
                backgroundColor: 'rgba(75, 192, 192, 0.6)',
                borderColor: 'rgba(75, 192, 192, 1)',
                borderWidth: 1,
                yAxisID: 'y'
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true,
                    title: {
                        display: true,
                        text: 'Total Downtime (minutes)'
                    }
                },
                y1: {
                    beginAtZero: true,
                    position: 'right',
                    title: {
                        display: true,
                        text: 'Downtime Count'
                    },
                    grid: {
                        drawOnChartArea: false // Only grid lines for primary y-axis
                    }
                },
                x: {
                    title: {
                        display: true,
                        text: 'Production Lines'
                    }
                }
            }
        }
    });

    async function fetchData() {
        try {
            const response = await fetch('http://localhost:8080/mssqrcode/home');
            if (!response.ok) throw new Error('Network response was not ok');

            const data = await response.json();
            const labels = [];
            const downtimeValues = [];
            const countValues = [];

            data.forEach(item => {
                const parts = item.split(": ");
                labels.push(parts[0]); // Production Line Name
                countValues.push(parseFloat(parts[1])); // Total Downtime Count
                downtimeValues.push(parseFloat(parts[2])); // Total Downtime in minutes
            });

            // Populate chart with data
            downtimeChart.data.labels = labels;
            downtimeChart.data.datasets[0].data = downtimeValues;

            // Adding trend (Downtime Count) dataset
            downtimeChart.data.datasets.push({
                label: 'Downtime Occurrences',
                data: countValues,
                type: 'line',
                borderColor: 'rgba(255, 99, 132, 1)',
                fill: false,
                tension: 0.1,
                yAxisID: 'y1'
            });

            downtimeChart.update(); // Refresh chart
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    }

    fetchData();
</script>
</body>
</html>