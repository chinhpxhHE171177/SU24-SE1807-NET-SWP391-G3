'use strict';

$(window).on('load', function () {
    /*------------------
     Preloader
     --------------------*/
    setTimeout(function () {
        $(".loader").fadeOut();
        $("#preloder").fadeOut("slow");
    }, 1000); // 1000 milliseconds = 5 seconds
});

(function ($) {
    /*------------------
     Navigation
     --------------------*/
    $('.primary-menu').slicknav({
        appendTo: '.header-warp',
        closedSymbol: '<i class="fa fa-angle-down"></i>',
        openedSymbol: '<i class="fa fa-angle-up"></i>'
    });

    /*------------------
     Background Set
     --------------------*/
    $('.set-bg').each(function () {
        var bg = $(this).data('setbg');
        $(this).css('background-image', 'url(' + bg + ')');
    });

    /*------------------
     Hero Slider
     --------------------*/
    $('.hero-slider').owlCarousel({
        loop: true,
        nav: true,
        dots: true,
        navText: ['', '<img src="img/icons/solid-right-arrow.png">'],
        mouseDrag: false,
        animateOut: 'fadeOut',
        animateIn: 'fadeIn',
        items: 1,
        //autoplay: true,
        autoplayTimeout: 10000,
    });

    var dot = $('.hero-slider .owl-dot');
    dot.each(function () {
        var index = $(this).index() + 1;
        if (index < 10) {
            $(this).html('0').append(index + '.');
        } else {
            $(this).html(index + '.');
        }
    });

    /*------------------
     Video Popup
     --------------------*/
    $('.video-popup').magnificPopup({
        type: 'iframe'
    });

    $('#stickySidebar').stickySidebar({
        topSpacing: 60,
        bottomSpacing: 60
    });

})(jQuery);

/*------------------
 Read more
 --------------------*/
$(document).ready(function () {
    $('.read-more').click(function (e) {
        e.preventDefault();
    });
});
