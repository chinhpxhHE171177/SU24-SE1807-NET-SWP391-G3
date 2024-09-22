<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Slideshow with Autoplay and Manual Controls</title>
    <style>
        html, body {
            width: 100%;
            height: 100%;
            margin: 0;
            padding: 0;
        }

        .slider-container {
            height: 100vh;
            width: 100%;
            position: relative;
            overflow: hidden;
            text-align: center;
        }

        .slide {
            width: 100%;
            height: 100%;
            position: absolute;
            top: 0;
            left: 100%;
            opacity: 0;
            background-size: cover;
            background-position: 50% 50%;
            transition: opacity 1s ease-in-out, left 1s ease-in-out;
        }

        [id^="slide"]:checked + .slide {
            left: 0;
            opacity: 1;
            z-index: 100;
        }

        .slide-1 {
            background-image: url("https://static.ybox.vn/2023/11/3/1699411140521-306z.jpg");
        }

        .slide-2 {
            background-image: url("https://fat.haui.edu.vn/media/76/t76899.jpg");
        }

        /* Navigation dots */
        .menu {
            position: absolute;
            left: 0;
            z-index: 900;
            width: 100%;
            bottom: 20px;
            display: flex;
            justify-content: center;
        }

        .menu label {
            cursor: pointer;
            display: inline-block;
            width: 16px;
            height: 16px;
            background: #fff;
            border-radius: 50px;
            margin: 0 .5em;
            transition: all .3s ease;
        }

        .menu label:hover {
            background: red;
        }

        /* Next and Previous buttons */
        .prev, .next {
            cursor: pointer;
            position: absolute;
            top: 50%;
            padding: 16px;
            color: white;
            font-weight: bold;
            font-size: 18px;
            background-color: rgba(0, 0, 0, 0.5);
            border: none;
            border-radius: 50%;
            transition: background 0.3s ease;
            z-index: 1000;
        }

        .prev:hover, .next:hover {
            background-color: rgba(0, 0, 0, 0.8);
        }

        .prev {
            left: 20px;
            transform: translateY(-50%);
        }

        .next {
            right: 20px;
            transform: translateY(-50%);
        }

        /* Hide radio buttons */
        input[type="radio"] {
            display: none;
        }
    </style>
</head>
<%@include file="components/navigation.jsp"%>
<body>

<div class="slider-container">
    <!-- Slides controlled by radio buttons -->
    <input id="slide-1" type="radio" name="slides" checked>
    <div class="slide slide-1"></div>

    <input id="slide-2" type="radio" name="slides">
    <div class="slide slide-2"></div>

    <!-- Previous and Next buttons -->
    <button class="prev" onclick="prevSlide()">&#10094;</button>
    <button class="next" onclick="nextSlide()">&#10095;</button>

    <!-- Navigation dots -->
    <div class="menu">
        <label for="slide-1"></label>
        <label for="slide-2"></label>
    </div>
</div>

<script>
    let currentSlideIndex = 1;
    const totalSlides = 2;

    function showSlide(index) {
        document.getElementById('slide-' + index).checked = true;
    }

    function nextSlide() {
        currentSlideIndex++;
        if (currentSlideIndex > totalSlides) {
            currentSlideIndex = 1;
        }
        showSlide(currentSlideIndex);
    }

    function prevSlide() {
        currentSlideIndex--;
        if (currentSlideIndex < 1) {
            currentSlideIndex = totalSlides;
        }
        showSlide(currentSlideIndex);
    }

    // Autoplay function to automatically move to the next slide every 3 seconds
    function autoPlaySlides() {
        setInterval(() => {
            nextSlide();
        }, 10000); // Change slide every 3 seconds
    }

    // Start autoplay when page loads
    window.onload = autoPlaySlides;
</script>

</body>
</html>
