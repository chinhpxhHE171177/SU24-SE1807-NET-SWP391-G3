<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Top 5 Downtime Charts</title>

    <!-- Bootstrap CSS for styling -->
    <link 
      rel="stylesheet" 
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
    >

    <!-- Chart.js for charts -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

    <style>
        body {
            background-color: #f8f9fa;
            font-family: 'Arial', sans-serif;
        }

        h2 {
            text-align: center;
            margin-top: 20px;
            margin-bottom: 15px;
            color: #343a40;
        }

        .chart-container {
            margin: 20px auto;
            padding: 15px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        canvas {
            max-height: 400px;
        }

        footer {
            text-align: center;
            margin-top: 30px;
            padding: 10px;
            background-color: #343a40;
            color: white;
        }
    </style>
</head>

<body>
<div class="container">
    <button style="margin-top: 10px; margin-bottom: 10px" class="btn btn-secondary" onclick="toggleDarkMode()">Dark</button>

    <!-- Compact filter for Lines -->
    <div class="form-group d-flex align-items-center" style="margin-bottom: 10px;">
        <label for="lineStartDate" class="mr-2">Start Date:</label>
        <input type="date" id="lineStartDate" class="form-control mr-2" style="width: auto;"/>
        
        <label for="lineEndDate" class="mr-2">End Date:</label>
        <input type="date" id="lineEndDate" class="form-control mr-2" style="width: auto;"/>

        <button id="lineFilterButton" class="btn btn-primary" onclick="fetchLineData()">Filter</button>
    </div>

    <div class="card" style="margin-bottom: 10px">
        <div class="card-header">Top 5 Production Lines</div>
        <div class="card-body">
            <canvas id="top5LinesChart"></canvas>
        </div>
    </div>

    <!-- Compact filter for Stages -->
    <div class="form-group d-flex align-items-center" style="margin-bottom: 10px;">
        <label for="stageStartDate" class="mr-2">Start Date:</label>
        <input type="date" id="stageStartDate" class="form-control mr-2" style="width: auto;"/>

        <label for="stageEndDate" class="mr-2">End Date:</label>
        <input type="date" id="stageEndDate" class="form-control mr-2" style="width: auto;"/>

        <button id="stageFilterButton" class="btn btn-primary" onclick="fetchStageData()">Filter</button>
    </div>

    <div class="card">
        <div class="card-header">Top 5 Stages</div>
        <div class="card-body">
            <canvas id="top5StagesChart"></canvas>
        </div>
    </div>
</div>

<script>
async function fetchLineData() {
    const lineStartDate = document.getElementById('lineStartDate').value;
    const lineEndDate = document.getElementById('lineEndDate').value;

    try {
        const url = new URL('http://localhost:8080/ssmqrcode/ReportTop');
        if (lineStartDate && lineEndDate) {
            url.searchParams.append('lineStartDate', lineStartDate);
            url.searchParams.append('lineEndDate', lineEndDate);
        }

        const response = await fetch(url);
        if (!response.ok) throw new Error('Failed to fetch line data.');

        const data = await response.json();
        updateLineChart(data.top5Lines);
    } catch (error) {
        console.error('Error fetching line data:', error);
        alert('Unable to fetch line data. Please try again.');
    }
}

async function fetchStageData() {
    const stageStartDate = document.getElementById('stageStartDate').value;
    const stageEndDate = document.getElementById('stageEndDate').value;

    try {
        const url = new URL('http://localhost:8080/ssmqrcode/ReportTop');
        if (stageStartDate && stageEndDate) {
            url.searchParams.append('stageStartDate', stageStartDate);
            url.searchParams.append('stageEndDate', stageEndDate);
        }

        const response = await fetch(url);
        if (!response.ok) throw new Error('Failed to fetch stage data.');

        const data = await response.json();
        updateStageChart(data.top5Stages);
    } catch (error) {
        console.error('Error fetching stage data:', error);
        alert('Unable to fetch stage data. Please try again.');
    }
}


function updateLineChart(top5Lines) {
    const lineLabels = top5Lines.map(record => record.lineName);
    const lineDurations = top5Lines.map(record => record.totalDowntime);
    const lineShortStops = top5Lines.map(record => record.shortTime);
    const lineOccurrences = top5Lines.map(record => record.errorCount);

    const lineCtx = document.getElementById('top5LinesChart').getContext('2d');

    // Destroy the previous chart if it exists
    if (window.lineChart) {
        window.lineChart.destroy();
    }

    // Create a new chart
    window.lineChart = new Chart(lineCtx, {
        type: 'bar',
        data: {
            labels: lineLabels,
            datasets: [
                {
                    label: 'Total Downtime (minutes)',
                    data: lineDurations,
                    backgroundColor: 'rgba(75, 192, 192, 0.6)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1,
                    yAxisID: 'y'
                },
                {
                    label: 'Short Stops (minutes)',
                    data: lineShortStops,
                    backgroundColor: 'rgba(255, 99, 132, 1)',
                    borderColor: 'rgba(255, 99, 132, 1)',
                    borderWidth: 1,
                    yAxisID: 'y'
                },
                {
                    label: 'Downtime Occurrences',
                    data: lineOccurrences,
                    type: 'line',
                    borderColor: 'rgba(255, 159, 64, 1)',
                    fill: false,
                    tension: 0.1,
                    yAxisID: 'y1'
                }
            ]
        },
        options: {
            responsive: true,
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
                        text: 'Downtime Occurrences'
                    },
                    grid: {
                        drawOnChartArea: false
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
}

function updateStageChart(top5Stages) {
    const stageLabels = top5Stages.map(record => record.stageName);
    const stageDurations = top5Stages.map(record => record.totalDowntime);
    const stageShortStops = top5Stages.map(record => record.shortTime);
    const stageOccurrences = top5Stages.map(record => record.errorCount);

    const stageCtx = document.getElementById('top5StagesChart').getContext('2d');

    // Destroy the previous chart if it exists
    if (window.stageChart) {
        window.stageChart.destroy();
    }

    // Create a new chart
    window.stageChart = new Chart(stageCtx, {
        type: 'bar',
        data: {
            labels: stageLabels,
            datasets: [
                {
                    label: 'Total Downtime (minutes)',
                    data: stageDurations,
                    backgroundColor: 'rgba(153, 102, 255, 0.6)',
                    borderColor: 'rgba(153, 102, 255, 1)',
                    borderWidth: 1,
                    yAxisID: 'y'
                },
                {
                    label: 'Short Stops (minutes)',
                    data: stageShortStops,
                    backgroundColor: 'rgba(255, 99, 132, 1)',
                    borderColor: 'rgba(255, 99, 132, 1)',
                    borderWidth: 1,
                    yAxisID: 'y'
                },
                {
                    label: 'Downtime Occurrences',
                    data: stageOccurrences,
                    type: 'line',
                    borderColor: 'rgba(255, 159, 64, 1)',
                    fill: false,
                    tension: 0.1,
                    yAxisID: 'y1'
                }
            ]
        },
        options: {
            responsive: true,
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
                        text: 'Downtime Occurrences'
                    },
                    grid: {
                        drawOnChartArea: false
                    }
                },
                x: {
                    title: {
                        display: true,
                        text: 'Stages'
                    }
                }
            }
        }
    });
}


// Call fetchData initially to load default data
fetchLineData();
fetchStageData();

        
        function toggleDarkMode() {
            document.body.classList.toggle('bg-dark');
            document.body.classList.toggle('text-white');
        }

    </script>
</body>
</html>
