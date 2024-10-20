<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Top 5 Downtime Charts</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<%@include file="components/navigation.jsp"%>
<body>
<h2>Top 5 Production Lines with Most Downtime</h2>
<canvas id="top5LinesChart"></canvas>

<h2>Top 5 Stages with Most Downtime</h2>
<canvas id="top5StagesChart"></canvas>

<script>
    async function fetchData() {
        try {
            // Update the URL to point to the servlet directly
            const response = await fetch('http://localhost:8080/ssmqrcode/ReportTopDetail');
            if (!response.ok) throw new Error('Network response was not ok');

            const data = await response.json();

            // Data for the top 5 production lines
            const top5Lines = data.top5Lines;
            const lineLabels = top5Lines.map(record => record.lineName);
            const lineDurations = top5Lines.map(record => record.totalDowntime); // Make sure to use totalDowntime
            const lineOccurrences = top5Lines.map(record => record.errorCount); // Use errorCount

            // Data for the top 5 stages
            const top5Stages = data.top5Stages;
            const stageLabels = top5Stages.map(record => record.stageName);
            const stageDurations = top5Stages.map(record => record.totalDowntime); // Make sure to use totalDowntime
            const stageOccurrences = top5Stages.map(record => record.errorCount); // Use errorCount

            // Production Lines chart setup
            const lineCtx = document.getElementById('top5LinesChart').getContext('2d');
            new Chart(lineCtx, {
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
                            label: 'Downtime Occurrences',
                            data: lineOccurrences,
                            type: 'line',
                            borderColor: 'rgba(255, 99, 132, 1)',
                            fill: false,
                            tension: 0.1,
                            yAxisID: 'y1'
                        }
                    ]
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

            // Stages chart setup
            const stageCtx = document.getElementById('top5StagesChart').getContext('2d');
            new Chart(stageCtx, {
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

        } catch (error) {
            console.error('Error fetching data:', error);
        }
    }

    fetchData();
</script>

</body>
</html>
