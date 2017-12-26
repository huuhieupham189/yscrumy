(function ($) {
    $.fn.raccordion = function (options) {
        var settings = $.extend({
            speed: 700,
            sliderWidth: 960,
            sliderHeight: 300,
            autoCollapse: true,
            activeSlide: 0
        }, options);

        return this.each(function () {
            var accordionWrapper = $(this);
            var width = accordionWrapper.find('.slide img:eq(0)').width();
            accordionWrapper.addClass('accordion-wrapper');
            var totalSlides = accordionWrapper.find('.slide').size();
            var w = width;
            initiliaze();
            if (settings.activeSlide < totalSlides - 1) {
                setTimeout(function () {
                    accordionWrapper.find('.slide:eq(' + settings.activeSlide + ')').click()
                }, settings.speed);
            }

            function initiliaze() {
                if (settings.sliderWidth > $(window).width()) {
                    width = w * (($(window).width() / settings.sliderWidth));
                    accordionWrapper.css("width", settings.sliderWidth * ($(window).width() / settings.sliderWidth));
                    accordionWrapper.find('.slide').each(function (index) {
                        $(this).animate({ left: (index * (accordionWrapper.width()) / totalSlides) }, { queue: false, speed: settings.speed, easing: 'quadEaseOut' });
                    });
                } else {
                    width = w;
                    accordionWrapper.css("width", settings.sliderWidth);
                    accordionWrapper.find('.slide').each(function (index) {
                        $(this).animate({ left: (index * (accordionWrapper.width()) / totalSlides) }, { queue: false, speed: settings.speed, easing: 'quadEaseOut' });
                    });
                }
                if (settings.sliderHeight > $(window).height()) {

                    accordionWrapper.animate({ height: settings.sliderHeight * ($(window).height() / settings.sliderHeight) }, { queue: false, speed: settings.speed, easing: 'quadEaseOut' });

                    accordionWrapper.find('.slide').animate({ height: settings.sliderHeight * ($(window).height() / settings.sliderHeight) }, { queue: false, speed: settings.speed, easing: 'quadEaseOut' });
                }
                else {
                    accordionWrapper.animate({ height: settings.sliderHeight }, { queue: false, speed: settings.speed, easing: 'quadEaseOut' });
                    accordionWrapper.find('.slide').animate({ height: settings.sliderHeight }, { queue: false, speed: settings.speed, easing: 'quadEaseOut' });
                }
                accordionWrapper.find('.caption').css({ opacity: 0 });
            }

            $(window).resize(function () {
                accordionWrapper.find('.slide').each(function (index) {
                    $(this).stop().animate({ left: (index * (accordionWrapper.width()) / totalSlides) }, { queue: false, speed: settings.speed, easing: 'quadEaseOut' });
                });
                animateCaption();
                initiliaze();
            });


            function animateCaption() {
                accordionWrapper.find('.caption').stop().animate({ opacity: 0, bottom: 0 }, { queue: false, speed: settings.speed, easing: 'quadEaseOut' });
                accordionWrapper.find('.active').find('.caption').stop().animate({ opacity: 1 }, { queue: false, speed: settings.speed, easing: 'quadEaseOut' });
            }


            accordionWrapper.find('.slide').click(function () {
                var ratio = (((accordionWrapper.width()) - width)) / (totalSlides - 1);
                if (($(this).width() == $('.slide').width()) || ($(this).width() == ratio)) {
                    accordionWrapper.find('.slide').removeClass('active');
                    $(this).addClass('active');
                    var currentIndex = accordionWrapper.find('.slide').index(this);
                    accordionWrapper.find('.slide').each(function (index) {
                        if (index == 0) {
                            $(this).animate({ left: 0 }, { queue: false, speed: settings.speed, easing: 'quadEaseOut' });
                        }
                        else if (index == currentIndex) {
                            $(this).animate({ left: (index) * ratio }, { queue: false, speed: settings.speed, easing: 'quadEaseOut' });
                        }
                        else if (index < currentIndex) {
                            $(this).animate({ left: (index) * ratio }, { queue: false, speed: settings.speed, easing: 'quadEaseOut' });
                        }
                        else if (index > currentIndex) {
                            $(this).animate({ left: width + (index - 1) * ratio }, { queue: false, speed: settings.speed, easing: 'quadEaseOut' });
                        }

                    });
                    animateCaption();
                }
            });


            if (settings.autoCollapse) {
                accordionWrapper.mouseleave(function () {
                    accordionWrapper.find('.slide').each(function (index) {
                        $(this).stop().animate({ left: (index * (accordionWrapper.width()) / totalSlides) }, { queue: false, speed: settings.speed, easing: 'quadEaseOut' });
                    });
                    accordionWrapper.find('.caption').css({ opacity: 0, bottom: 0 });
                });
            }

        });
    }
})(jQuery);
