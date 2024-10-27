<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Equipment Downtime Dashboard</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="styles.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }

        .container {
            margin: auto;
            overflow: hidden;
            max-width: 1200px;
            padding: 20px;
        }

        header {
            background: #333;
            color: #fff;
            padding: 20px 0;
            text-align: center;
            border-radius: 8px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        }

        h1 {
            margin: 0;
        }

        .chart-container {
            margin: 20px 0;
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        }

        /* Responsive chart styling */
        canvas {
            width: 100% !important;
            height: auto !important;
        }

        /* Styling for data labels */
        .chartjs-tooltip {
            background: rgba(0, 0, 0, 0.8);
            color: #fff;
            border-radius: 5px;
            padding: 5px;
        }

        .loader {
             display: none; /* Initially hidden */
             border: 16px solid #f3f3f3; /* Light grey */
             border-top: 16px solid #3498db; /* Blue */
             border-radius: 50%;
             width: 60px;
             height: 60px;
             animation: spin 1s linear infinite; /* Spin animation */
             margin: 20px auto; /* Center the loader */
         }

         @keyframes spin {
             0% { transform: rotate(0deg); }
             100% { transform: rotate(360deg); }
         }
         

        /* Styling for buttons (if needed) */
        .button {
            background-color: #5cb85c; /* Bootstrap primary button color */
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .button:hover {
            background-color: #4cae4c; /* Darker shade on hover */
        }

        .filter-section {
            display: flex;
            align-items: center;
            gap: 12px;
            margin: 20px 0;
        }

        .filter-section label {
            font-weight: bold;
            font-size: 1.1rem;
        }

        .filter-section select {
            padding: 8px;
            border-radius: 6px;
            border: 1px solid #ced4da;
            font-size: 1rem;
            min-width: 150px;
        }

        .filter-section select:focus {
            outline: none;
            border-color: #80bdff;
            box-shadow: 0 0 0 3px rgba(0, 123, 255, 0.25);
        }
    </style>
</head>
<%@include file="components/navigation.jsp"%>
<body>
<div class="container">
    <header>
        <h1>Equipment Downtime Dashboard</h1>
    </header>

    <div class="filter-section">
        <label for="filterType">Select Filter:</label>
        <select id="filterType">
            <option value="day" selected>Day</option>
            <option value="month">Month</option>
            <option value="year">Year</option>
        </select>
    </div>

    <!-- Loader Spinner -->
    <div id="loader" class="loader"></div>

    <div id="chartsContainer" class="chart-container"></div>
</div>

<script>
const filterTypeSelect = document.getElementById('filterType');
const chartsContainer = document.getElementById('chartsContainer');

// Event listener for dropdown change
filterTypeSelect.addEventListener('change', () => {
    const filterType = filterTypeSelect.value;
    fetchData(filterType);
});

async function fetchData(filterType) {
    try {
        const url = new URL('http://localhost:8080/ssmqrcode/home');
        url.searchParams.append('filterType', filterType);
        
        // Show loading spinner
        document.getElementById('loader').style.display = 'block';
        
        const response = await fetch(url);
        if (!response.ok) throw new Error('Network response was not ok');
        
        const data = await response.json();
        // Hide loading spinner
        document.getElementById('loader').style.display = 'none';

        // Clear previous charts and messages
        chartsContainer.innerHTML = '';

        // Check if data is empty
        if (data.length === 0) {
            const noDataMessage = document.createElement('div');
            noDataMessage.innerText = 'No data available for the selected period.';
            noDataMessage.style.color = 'red';
            noDataMessage.style.textAlign = 'center';
            noDataMessage.style.margin = '20px 0';
            chartsContainer.appendChild(noDataMessage);
            return; // Exit the function if there's no data
        }

        data.forEach(item => {
            const lineHeading = document.createElement('h3');
            lineHeading.innerText = item.lineName; 
            chartsContainer.appendChild(lineHeading); 

            const chartId = 'downtimeChart_' + item.lineName.replace(/\s+/g, '_'); 
            const canvas = document.createElement('canvas');
            canvas.id = chartId;
            chartsContainer.appendChild(canvas);

            const ctx = canvas.getContext('2d');

            // Create dataset conditionally based on shortStop and totalDowntime values
            const datasets = [
                {
                    label: 'Short Stop Time (minutes)',
                    data: [item.shortStop],
                    backgroundColor: 'rgba(255, 99, 132, 0.6)',
                    borderColor: 'rgba(255, 99, 132, 1)',
                    borderWidth: 1,
                    barThickness: 30,
                    yAxisID: 'yTime',
                },
                {
                    label: 'Short Stop Count',
                    data: [item.downtimeCount],
                    backgroundColor: 'rgba(255, 206, 86, 0.6)',
                    borderColor: 'rgba(255, 206, 86, 1)',
                    borderWidth: 3,
                    type: 'line',
                    yAxisID: 'yErrorCount',
                },
                {
                    label: 'Short Stop Percentage',
                    data: [item.shortStopPercentage],
                    backgroundColor: 'rgba(139, 0, 139, 0.6)',
                    borderColor: 'rgba(139, 0, 139, 0.6)',
                    borderWidth: 3,
                    type: 'line',
                    yAxisID: 'yPercentage',
                },
            ];

            // Only add the totalDowntime dataset if it's different from shortStop
            if (item.shortStop !== item.totalDowntime) {
                datasets.push({
                    label: 'Total Downtime (minutes)',
                    data: [item.totalDowntime],
                    backgroundColor: 'rgba(75, 192, 192, 0.6)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1,
                    barThickness: 30,
                    yAxisID: 'yTime',
                });
            }

            const downtimeChart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: [item.lineName],
                    datasets: datasets,
                },
                options: {
                    responsive: true,
                    plugins: {
                        tooltip: {
                            callbacks: {
                                label: function(tooltipItem) {
                                    const datasetIndex = tooltipItem.datasetIndex;
                                    if (datasetIndex === 2) {
                                        return 'Short Stop Percentage: ' + tooltipItem.raw + '%';
                                    } else if (datasetIndex === 1) {
                                        return 'Short Stop Count: ' + tooltipItem.raw;
                                    } else if (datasetIndex === 3) {
                                        return 'Total Downtime: ' + tooltipItem.raw + ' minutes';
                                    } else {
                                        return 'Short Stop: ' + tooltipItem.raw + ' minutes';
                                    }
                                },
                            },
                        },
                    },
                    scales: {
                        yTime: {
                            beginAtZero: true,
                            position: 'left',
                            title: {
                                display: true,
                                text: 'Time (minutes)',
                            },
                        },
                        yPercentage: {
                            beginAtZero: true,
                            position: 'left',
                            offset: true,
                            title: {
                                display: true,
                                text: 'Short Stop Percentage (%)',
                            },
                            grid: {
                                drawOnChartArea: false,
                            },
                            ticks: {
                                callback: function(value) {
                                    return value + '%';
                                },
                            },
                        },
                        yErrorCount: {
                            beginAtZero: true,
                            position: 'right',
                            title: {
                                display: true,
                                text: 'Error Count',
                            },
                            grid: {
                                drawOnChartArea: false,
                            },
                        },
                        x: {
                            title: {
                                display: true,
                                text: '',
                            },
                        },
                    },
                },
            });
        });
    } catch (error) {
        console.error('Error fetching data:', error);
        document.getElementById('loader').style.display = 'none';
        alert('Failed to fetch data. Please try again later.');
    }
}

// Initial fetch on page load
fetchData('day');

</script>
</body>
</html>
