<%-- 
    Document   : certificate
    Created on : Jun 24, 2024, 2:56:34 PM
    Author     : Admin
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="en_US" />
<fmt:setTimeZone value="GMT" />
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Certificate</title>
        <!-- Include Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <!-- Include Font Awesome CSS -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"
              integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />
        <!-- Google Fonts -->
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Open+Sans|Pinyon+Script">
        <!-- Internal Styles -->
    </head>
    <style>
        @import url('https://fonts.googleapis.com/css?family=Open+Sans|Pinyon+Script|Rochester');

        .cursive {
            font-family: 'Pinyon Script', cursive;
        }

        .sans {
            font-family: 'Open Sans', sans-serif;
        }

        .bold {
            font-weight: bold;
        }

        .block {
            display: block;
        }

        .underline {
            border-bottom: 1px solid #777;
            padding: 5px;
            margin-bottom: 15px;
        }

        .margin-0 {
            margin: 0;
        }

        .padding-0 {
            padding: 0;
        }

        .pm-empty-space {
            height: 40px;
            width: 100%;
        }

        body {
            padding: 20px 0;
            background: #ccc;
        }

        .signature {
            font-family: 'Pinyon Script', cursive;
            font-size: 24px;
            color: #2c3e50;
            text-align: center;
            margin-top: 20px;
        }

        .completedDate {
            font-family: 'Pacifico', cursive;
            /* font-size: 24px; */
            color: #2c3e50;
            text-align: center;
            margin-top: 20px;
        }

        .button-down {
            /*width: 100%;*/
            padding: 1rem;
            border: 1px solid #0056b3;
            border-radius: 5px;
            text-decoration: none;
            color: #0056d2;
            font-family: cursive;
            font-weight: 500;
        }
        .button-down:hover {
            background-color: #283060;
            color: #E1E5F0;
        }

        .pm-certificate-container {
            position: relative;
            width: 800px;
            height: 600px;
            background-color: #283060;
            padding: 30px;
            color: #333;
            font-family: 'Open Sans', sans-serif;
            box-shadow: 0 0 5px rgba(0, 0, 0, .5);
            /*background: -webkit-repeating-linear-gradient(
        45deg,
        #618597,
        #618597 1px,
        #b2cad6 1px,
        #b2cad6 2px
      );
      background: repeating-linear-gradient(
        90deg,
        #618597,
        #618597 1px,
        #b2cad6 1px,
        #b2cad6 2px
      );*/

            .outer-border {
                width: 794px;
                height: 594px;
                position: absolute;
                left: 50%;
                margin-left: -397px;
                top: 50%;
                margin-top: -297px;
                border: 2px solid #E8B974;
            }

            .inner-border {
                width: 730px;
                height: 530px;
                position: absolute;
                left: 50%;
                margin-left: -365px;
                top: 50%;
                margin-top: -265px;
                border: 2px solid #E8B974;
            }

            .pm-certificate-border {
                position: relative;
                width: 720px;
                height: 520px;
                padding: 0;
                border: 1px solid #E1E5F0;
                background-color: rgba(255, 255, 255, 1);
                background-image: none;
                left: 50%;
                margin-left: -360px;
                top: 50%;
                margin-top: -260px;

                .pm-certificate-block {
                    width: 650px;
                    height: 200px;
                    position: relative;
                    left: 50%;
                    margin-left: -325px;
                    top: 70px;
                    margin-top: 0;
                }

                .pm-certificate-header {
                    margin-bottom: 10px;
                }

                .pm-certificate-title {
                    position: relative;
                    top: 40px;

                    h2 {
                        font-size: 34px !important;
                    }
                }

                .pm-certificate-body {
                    padding: 20px;

                    .pm-name-text {
                        font-size: 20px;
                        color: #283060;
                    }
                }

                .pm-earned {
                    margin: 15px 0 20px;

                    .pm-earned-text {
                        font-size: 20px;

                    }

                    .pm-credits-text {
                        font-size: 15px;
                        color: #283060;
                    }
                }

                .pm-course-title {
                    .pm-earned-text {
                        font-size: 20px;
                    }

                    .pm-credits-text {
                        font-size: 15px;
                        color: #283060;
                    }
                }

                .pm-certified {
                    font-size: 12px;
                    color: #283060;

                    .underline {
                        margin-bottom: 5px;
                    }
                }

                .pm-certificate-footer {
                    width: 650px;
                    height: 100px;
                    position: relative;
                    display: flex;
                    left: 50%;
                    margin-left: -325px;
                    bottom: -105px;
                }
            }
        }
    </style>

    <body>
        <div id="invoice">
            <div class="container pm-certificate-container">
                <div class="outer-border"></div>
                <div class="inner-border"></div>
                <div class="pm-certificate-border col-xs-12">
                    <div class="row pm-certificate-header">
                        <div class="pm-certificate-title cursive col-xs-12 text-center">
                            <h2>Buffalo Public Schools Certificate of Completion</h2>
                        </div>
                    </div>

                    <div class="row pm-certificate-body" id="invoice">

                        <div class="pm-certificate-block">
                            <div class="col-xs-12">
                                <div class="row">
                                    <div class="col-xs-2"><!-- LEAVE EMPTY --></div>
                                    <div class="pm-certificate-name underline margin-0 col-xs-8 text-center">
                                        <span class="pm-name-text bold">${sessionScope.user.fullname}</span>
                                    </div>
                                    <div class="col-xs-2"><!-- LEAVE EMPTY --></div>
                                </div>
                            </div>

                            <div class="col-xs-12">
                                <div class="row">
                                    <div class="col-xs-2"><!-- LEAVE EMPTY --></div>
                                    <div class="pm-earned col-xs-8 text-center">
                                        <span class="pm-earned-text padding-0 block cursive">has successful completed</span>
                                        <span class="pm-credits-text block bold sans">${subject.name}</span>

                                    </div>
                                    <div class="col-xs-2"><!-- LEAVE EMPTY --></div>
                                    <div class="col-xs-12"></div>
                                </div>
                            </div>

                            <div class="col-xs-12">
                                <div class="row">
                                    <div class="col-xs-2"><!-- LEAVE EMPTY --></div>
                                    <div class="pm-course-title col-xs-8 text-center">
                                        <span class="pm-earned-text block cursive">an online non-credit course authorized by
                                            CertNexus and offered through Course</span>
                                    </div>
                                    <div class="col-xs-2"><!-- LEAVE EMPTY --></div>
                                </div>
                            </div>

                            <div class="col-xs-12">
                                <div class="row">
                                    <div class="col-xs-2"><!-- LEAVE EMPTY --></div>
                                    <div class="pm-course-title underline col-xs-8 text-center">
                                        <!-- <span class="pm-credits-text block bold sans">BPS PGS Initial PLO for Principals at
                                            Cluster Meetings</span> -->
                                    </div>
                                    <div class="col-xs-2"><!-- LEAVE EMPTY --></div>
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-12">
                            <div class="row">
                                <div class="pm-certificate-footer">
                                    <div class="col-md-4 pm-certified col-xs-4 text-center">
                                        <span class="pm-credits-text block sans">FPT UNIVERSITY</span>
                                        <span class="pm-empty-space block underline signature">${subject.fullName}</span>
                                        <span class="bold block">Crystal Benton Instructional Specialist II, Staff
                                            Development</span>
                                    </div>
                                    <div class="col-md-4  pm-certified col-xs-4 text-center">
                                        <img src="https://t4.ftcdn.net/jpg/00/08/58/49/360_F_8584973_Uu6DqhY8m8f5EIRr619d29VGLBBHCUCW.jpg"
                                             alt="" width="85" height="120">
                                    </div>
                                    <div class="col-md-4 pm-certified col-xs-4 text-center">
                                        <span class="pm-credits-text block sans">Date Completed</span>
                                        <span class="pm-empty-space block underline completedDate"> <fmt:formatDate value="${uchoice.endTime}" pattern="MMM dd, yyyy, h:mm:ss a"/></span>
                                        <span class="bold block">DOB: </span>
                                        <span class="bold block">Social Security # (last 4 digits)</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Download button -->
        <div class="container mt-3">
            <div class="text-center">
                <button id="downloadForm" type="button" class="button-down"><i class="fas fa-download"></i> Download Certificate</button>
            </div>
        </div>

        <!-- Scripts -->
        <!-- Bootstrap Bundle -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
        <!-- html2pdf -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/html2pdf.js/0.10.1/html2pdf.bundle.js"></script>
        <!-- PDF generation script -->
        <script>
            window.onload = function () {
                document.getElementById("downloadForm").addEventListener("click", function () {
                    const invoice = document.getElementById("invoice");
                    var opt = {
                        margin: 0.05,
                        filename: 'certificate.pdf',
                        image: {type: 'jpeg', quality: 0.98},
                        html2canvas: {scale: 2},
                        jsPDF: {unit: 'in', format: 'letter', orientation: 'portrait'}
                    };
                    html2pdf().from(invoice).set(opt).save();
                });
            };
        </script>
    </body>
</html>
