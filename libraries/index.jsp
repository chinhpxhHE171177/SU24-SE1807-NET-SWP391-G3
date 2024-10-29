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
        /* Chart Section Styling */
         .chart-container .position-relative {
             padding: 20px;
             border-radius: 12px;
             background-color: white;
             box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
         }

         /* Dropdown Three-Dot Icon */
         .dropdown .btn-link {
             border: none;
             background: transparent;
             cursor: pointer;
         }

         .dropdown-menu {
             min-width: 150px;
             padding: 10px 0;
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

<!-- Modal -->
<div class="modal fade" id="chartModal" tabindex="-1" aria-labelledby="chartModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="chartModalLabel">Line Chart</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <canvas id="modalChart"></canvas>
      </div>
    </div>
  </div>
</div>



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

<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/4.2.1/chart.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.18.5/xlsx.full.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels@2.0.0"></script>

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

        document.getElementById('loader').style.display = 'block'; // Show loader

        const response = await fetch(url);
        if (!response.ok) throw new Error('Network response was not ok');

        const data = await response.json();
        document.getElementById('loader').style.display = 'none'; // Hide loader
        chartsContainer.innerHTML = ''; // Clear old charts

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

        // Fetch OEE (discountPercentage) from DummyJSON API
       const oeeResponse = await fetch('https://dummyjson.com/products');
       if (!oeeResponse.ok) throw new Error('Failed to fetch OEE data');
       const oeeData = await oeeResponse.json();
       console.log(oeeData); // Check the API response here
       

        // Map OEE values to data based on product ID or other logic
        const oeeMap = {};
        oeeData.products.forEach(product => {
            oeeMap[product.id] = product.price;
        });


        data.forEach(item => {
            const chartSection = document.createElement('div');
            chartSection.classList.add('position-relative', 'mb-5', 'p-3', 'bg-white', 'rounded', 'shadow');

            const lineHeading = document.createElement('h3');
            lineHeading.innerText = item.lineName;
            lineHeading.style.cursor = 'pointer';
            lineHeading.style.width = '30%';
            lineHeading.onclick = () => openChartModal(item); // Open modal on click
            chartSection.appendChild(lineHeading);

            // Create three-dot menu (dropdown trigger)
            const dropdown = document.createElement('div');
            dropdown.classList.add('dropdown', 'position-absolute', 'top-0', 'end-0');

            const dropdownToggle = document.createElement('button');
            dropdownToggle.classList.add('btn', 'btn-link', 'text-dark', 'fs-5');
            dropdownToggle.innerHTML = '☰'; 
            dropdownToggle.setAttribute('data-bs-toggle', 'dropdown');
            dropdown.appendChild(dropdownToggle);

            const dropdownMenu = document.createElement('ul');
            dropdownMenu.classList.add('dropdown-menu', 'dropdown-menu-end');

            const downloadItem = document.createElement('li');
            const downloadBtn = document.createElement('button');
            downloadBtn.classList.add('dropdown-item');
            downloadBtn.innerText = 'Download';
            downloadBtn.onclick = () => downloadChartAsImage(chartId, item.lineName);
            downloadItem.appendChild(downloadBtn);

            const exportItem = document.createElement('li');
            const excelBtn = document.createElement('button');
            excelBtn.classList.add('dropdown-item');
            excelBtn.innerText = 'Export';
            excelBtn.onclick = () => exportChartToExcel(item);
            exportItem.appendChild(excelBtn);

            dropdownMenu.appendChild(downloadItem);
            dropdownMenu.appendChild(exportItem);
            dropdown.appendChild(dropdownMenu);

            chartSection.appendChild(dropdown);

            const chartId = 'downtimeChart_' + item.lineName.replace(/\s+/g, '_');
            const canvas = document.createElement('canvas');
            canvas.id = chartId;
            chartSection.appendChild(canvas);

            chartsContainer.appendChild(chartSection);

            const ctx = canvas.getContext('2d');
            const datasets = createDatasets(item, oeeMap[item.id]); // Pass OEE value here

            new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: [item.lineName],
                    datasets: datasets,
                },
                options: chartOptions(),
            });
        });
    } catch (error) {
        console.error('Error fetching data:', error);
        document.getElementById('loader').style.display = 'none';
        alert('Failed to fetch data. Please try again later.');
    }
}

// Create datasets based on data and OEE
function createDatasets(item, oee) {
    const datasets = [
        {
            label: 'Short Stop Time (minutes)',
            data: [item.shortStop],
            backgroundColor: 'rgba(255, 99, 132, 0.6)',
            borderColor: 'rgba(255, 99, 132, 1)',
            barThickness: 30,
            yAxisID: 'yTime',
        },
        {
            label: 'Short Stop Count',
            data: [item.downtimeCount],
            backgroundColor: 'rgba(255, 206, 86, 0.6)',
            borderColor: 'rgba(255, 206, 86, 1)',
            type: 'line',
            yAxisID: 'yErrorCount',
        },
        {
            label: 'Short Stop Percentage',
            data: [item.shortStopPercentage],
            backgroundColor: 'rgba(139, 0, 139, 0.6)',
            borderColor: 'rgba(139, 0, 139, 1)',
            type: 'line',
            yAxisID: 'yPercentage',
        },
        {
            label: 'OEE (%)',
            data: [oee || 0], // Use OEE data or default to 0
            backgroundColor: 'rgba(54, 162, 235, 0.6)',
            borderColor: 'rgba(54, 162, 235, 1)',
            type: 'bar',
            yAxisID: 'yOEE',
        },
    ];

    if (item.shortStop !== item.totalDowntime) {
        datasets.push({
            label: 'Total Downtime (minutes)',
            data: [item.totalDowntime],
            backgroundColor: 'rgba(75, 192, 192, 0.6)',
            borderColor: 'rgba(75, 192, 192, 1)',
            barThickness: 30,
            yAxisID: 'yTime',
        });
    }
    return datasets;
}

// Update chart options to include the new y-axis for OEE
function chartOptions() {
    return {
        responsive: true,
        scales: {
            yTime: {
                beginAtZero: true,
                position: 'left',
                title: { text: 'Time (minutes)', display: true },
            },
            yPercentage: {
                beginAtZero: true,
                position: 'left',
                ticks: { callback: value => value + '%' },
            },
            yErrorCount: {
                beginAtZero: true,
                position: 'right',
                title: { text: 'Error Count', display: true },
            },
            yOEE: {
                beginAtZero: true,
                position: 'right',
                title: { text: 'OEE (%)', display: true },
                ticks: { callback: value => value + '%' },
            },
        },
    };
}

function openChartModal(item) {
    const modalChartCtx = document.getElementById('modalChart').getContext('2d');

    // Clear previous chart instance if any
    if (window.modalChartInstance) window.modalChartInstance.destroy();

    // Create new chart instance in the modal
    window.modalChartInstance = new Chart(modalChartCtx, {
        type: 'bar',
        data: {
            labels: ['Short Stop Time', 'Short Stop Count', 'Short Stop Percentage', 'Total Downtime'],
            datasets: [{
                label: item.lineName,
                data: [
                    item.shortStop,
                    item.downtimeCount,
                    item.shortStopPercentage,
                    item.totalDowntime,
                ],
                backgroundColor: [
                    'rgba(255, 99, 132, 0.6)',
                    'rgba(255, 206, 86, 0.6)',
                    'rgba(139, 0, 139, 0.6)',
                    'rgba(75, 192, 192, 0.6)'
                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(139, 0, 139, 1)',
                    'rgba(75, 192, 192, 1)'
                ],
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            plugins: {
                datalabels: {
                    anchor: 'end',       
                    align: 'top',         
                    formatter: (value) => value, 
                    color: '#000', 
                    font: {
                        weight: 'bold'
                    }
                }
            },
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        },
        plugins: [ChartDataLabels] // Khởi động plugin datalabels
    });

    // Show the modal
    const chartModal = new bootstrap.Modal(document.getElementById('chartModal'));
    chartModal.show();
}

// Download chart as an image
function downloadChartAsImage(chartId, lineName) {
    const canvas = document.getElementById(chartId);
    const link = document.createElement('a');
    link.href = canvas.toDataURL('image/png');
    link.download = `${lineName}_chart.png`;
    link.click();
}

// Export chart data to Excel
function exportChartToExcel(item) {
    const workbook = XLSX.utils.book_new();
    const data = [
        ['Line Name', item.lineName],
        ['Short Stop Time (minutes)', item.shortStop],
        ['Short Stop Count', item.downtimeCount],
        ['Short Stop Percentage', item.shortStopPercentage + '%'],
        ['Total Downtime (minutes)', item.totalDowntime],
    ];

    const worksheet = XLSX.utils.aoa_to_sheet(data);
    XLSX.utils.book_append_sheet(workbook, worksheet, 'Chart Data');
    XLSX.writeFile(workbook, `${item.lineName}_data.xlsx`);
}
// Initial fetch on page load
fetchData('day');
</script>
</body>
</html>
