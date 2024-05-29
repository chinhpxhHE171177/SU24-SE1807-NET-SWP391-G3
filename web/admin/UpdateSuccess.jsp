<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang Web</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: 'Source Sans Pro', sans-serif;
            display: flex;
            flex-direction: column;
            min-height: 100vh;
        }

        main {
            flex: 1;
        }

        header {
            padding: 2em;
            background-color: #8BC34A;
            color: #fff;
            text-align: center;
        }

        #card {
            position: relative;
            width: 320px;
            margin: 40px auto;
            text-align: center;
        }

        #upper-side {
            padding: 2em;
            background-color: #8BC34A;
            color: #fff;
            border-top-right-radius: 8px;
            border-top-left-radius: 8px;
        }

        #checkmark {
            font-weight: lighter;
            fill: #fff;
            margin: -3.5em auto auto 20px;
        }

        #status {
            font-weight: lighter;
            text-transform: uppercase;
            letter-spacing: 2px;
            font-size: 1em;
            margin-top: -.2em;
            margin-bottom: 0;
        }

        #lower-side {
            padding: 2em 2em 5em 2em;
            background: #fff;
            border-bottom-right-radius: 8px;
            border-bottom-left-radius: 8px;
        }

        #message {
            margin-top: -.5em;
            color: #757575;
            letter-spacing: 1px;
        }

        #contBtn {
            position: relative;
            top: 1.5em;
            text-decoration: none;
            background: #8bc34a;
            color: #fff;
            margin: auto;
            padding: .8em 3em;
            box-shadow: 0px 15px 30px rgba(50, 50, 50, 0.21);
            border-radius: 25px;
            transition: all .4s ease;
        }

        #contBtn:hover {
            box-shadow: 0px 15px 30px rgba(50, 50, 50, 0.41);
            transition: all .4s ease;
        }

        .footer {
            padding: 2em;
            background-color: #8BC34A;
            text-align: center;
            color: #fff;
            border-top-right-radius: 8px;
            border-top-left-radius: 8px;
        }
    </style>
</head>
<body>
    <header>
        
    </header>
    <main>
        <div id='card' class="animated fadeIn">
            <div id='upper-side'>
                <?xml version="1.0" encoding="utf-8"?>
                <!-- Generator: Adobe Illustrator 17.1.0, SVG Export Plug-In . SVG Version: 6.00 Build 0)  -->
                <!DOCTYPE svg PUBLIC "-//W3C//DTD SVG 1.1//EN" "http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd">
                <svg version="1.1" id="checkmark" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px" xml:space="preserve">
                    <path d="M131.583,92.152l-0.026-0.041c-0.713-1.118-2.197-1.447-3.316-0.734l-31.782,20.257l-4.74-12.65
                          c-0.483-1.29-1.882-1.958-3.124-1.493l-0.045,0.017c-1.242,0.465-1.857,1.888-1.374,3.178l5.763,15.382
                          c0.131,0.351,0.334,0.65,0.579,0.898c0.028,0.029,0.06,0.052,0.089,0.08c0.08,0.073,0.159,0.147,0.246,0.209
                          c0.071,0.051,0.147,0.091,0.222,0.133c0.058,0.033,0.115,0.069,0.175,0.097c0.081,0.037,0.165,0.063,0.249,0.091
                          c0.065,0.022,0.128,0.047,0.195,0.063c0.079,0.019,0.159,0.026,0.239,0.037c0.074,0.01,0.147,0.024,0.221,0.027
                          c0.097,0.004,0.194-0.006,0.292-0.014c0.055-0.005,0.109-0.003,0.163-0.012c0.323-0.048,0.641-0.16,0.933-0.346l34.305-21.865
                          C131.967,94.755,132.296,93.271,131.583,92.152z" />
                    <circle fill="none" stroke="#ffffff" stroke-width="5" stroke-miterlimit="10" cx="109.486" cy="104.353" r="32.53" />
                </svg>
                <h3 id='status'>Success</h3>
            </div>
            <div id='lower-side'>
                <p id='message'>Congratulations, Registrations has been successfully updated.</p>
                <p><a href="UpdateRegis" id="contBtn">Update Another</a></p>
                <p><a href="index.html" id="contBtn" style="margin-top: auto;top: 60px;">Back to Home</a></p>
            </div>
        </div>
    </main>
    <footer class="footer">
        
    </footer>
</body>
</html>
  
    
