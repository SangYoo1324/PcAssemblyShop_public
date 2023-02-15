console.log("init");

pc_index = {
    init: function(){
        this.second_nav_bar_show();
        this. nav_bar_line_animation();
        this. main_page_slideshow_animation();
        this.PC_Laptops_animation();
        this.testimonial_slideShow_animation();
        this.event_carousel_slide_animation();
    },

    second_nav_bar_show: function(){
        $('.header__menus>ul>li').click(function(){
            let target =$(this).children().eq(1);
            if(!target.hasClass('active')){
                target.addClass('active');
                //remove all other active class from siblings
                target.parent().siblings().children().eq(1).removeClass('active');
            }else
                target.removeClass('active');

        })

    },

    nav_bar_line_animation: function(){


    },
    main_page_slideshow_animation: function(){
        let slides = $('.main-page-slideshow>.con>*:not(:last-child)');
        let dots = $('.main-page__slide_dot >*');
        slide_looping(slides,dots);

        setInterval(function(){
            slide_looping(slides,dots);
        },16000);

    },
    PC_Laptops_animation: function(){

        // let slides = document.querySelectorAll('.PC_Laptops>.con > *');
        let slides = $('.PC_Laptops>.con > *').get();
        console.log(slides);
        const Observer =  new IntersectionObserver((entries,observer)=>{
            entries.forEach(entry=>{
                if(entry.isIntersecting){
                    $(entry.target).css('opacity','1');
                    console.log(entry+"이 intersecting 했다");
                }else
                    $(entry.target).css('opacity','0');

            });





        });
        // observing all elements on slides(line1,line2)
        slides.forEach(obj =>{
            Observer.observe(obj);
        })
    },

    testimonial_slideShow_animation: function(){
        const slides  = $('section.testimonials>*:not(:last-child)');
        const dots = $('.testimonials__slide_dot>*');
        console.log(slides);

        slide_looping(slides,dots);

        setInterval(function(){
            slide_looping(slides,dots);

            console.log("testimonial 돌아가고 있다");
        },16000);


    },
    event_carousel_slide_animation: function(){
        let slide_count = 0;

        const left_btn = $('.arrow_l');
        const right_btn = $('.arrow_r');
        const carousel_slides= $('.event_carousel>.con> *:not(:last-child)');

        //initialize 1st slide
        carousel_slides.eq(slide_count).addClass('active');
        carousel_slides.eq(slide_count).siblings().removeClass('active');


        left_btn.click(function(){
            if(slide_count==0){
                slide_count=carousel_slides.length-1;
                carousel_slides.eq(slide_count).addClass('active');
                carousel_slides.eq(slide_count).siblings().removeClass('active');
                console.log("slide가 이동합니다"+slide_count);
            }else{
                slide_count--;
                carousel_slides.eq(slide_count).addClass('active');
                carousel_slides.eq(slide_count).siblings().removeClass('active');
                console.log("slide가 이동합니다"+slide_count);

            }
        });
        right_btn.click(function(){
            if(slide_count==carousel_slides.length-1){
                slide_count=0;
                carousel_slides.eq(slide_count).addClass('active');
                carousel_slides.eq(slide_count).siblings().removeClass('active');
                console.log("slide가 이동합니다"+slide_count);
            }else{
                slide_count++;
                carousel_slides.eq(slide_count).addClass('active');
                carousel_slides.eq(slide_count).siblings().removeClass('active');
                console.log("slide가 이동합니다"+slide_count);
            }

        });
    }
}

pc_index.init();


// slideshow_animation(sub-method)   dots can be null
function slide_looping(slides,dots){
    for(let i = 0; i<slides.length; i++){
        setTimeout(function(){
            if(!$(slides.eq(i)).hasClass('active')){
                $(slides.eq(i)).addClass('active');
                console.log(`slide-${i}activated`);
                // for dots
                console.log(dots!=null);
                if(dots!=null){
                    console.log(i+"th slide for testimonial");
                    dots.eq(i).addClass('active');
                    dots.eq(i).siblings().removeClass('active');
                }

            }
            $(slides.eq(i)).siblings().removeClass('active');
            // console.log(`slide-${i}removed`);
        },4000*i);

    }

}

//testimonial slideshow Dot(sub-method)
function testimonial_dot_chng(slides,dots){
    for(let i=0; i<slides.length; i++)
        if(slides.eq(i).hasClass('active')){
            console.log(i+"th slide for testimonial");
            dots.eq(i).css('background-color','black');
            dots.eq(i).siblings().removeClass('active');
        }

}


//mobile index

mobile_index = {
    init: function(){
        this.mobileTopbar_init();
        this.mobileSidebar_close();
        this.mobileSidebar_menus_2nd();
    },
    mobileTopbar_init: function(){  // burger icon triggers side-bar
        $('.menu-expansion-burger').click(function(){
            console.log("clicked");
            $('.mobile-side-bar').addClass('active');
        });
    },
    mobileSidebar_close: function(){ // x btn + transparent grey screen
        $('.mobile-side-bar, .mobile-side-bar__btn-close').click(
            function(){
                console.log("sidebar closing triggered");
                $('.mobile-side-bar').removeClass('active');
                $('.mobile-side-bar__body__menu-box-1 > ul > li').removeClass('active');
            }
        );

        $('.mobile.side-bar__contents, .mobile-side-bar__btn-close').click(
            function(e){
                e.stopPropagation();
            });
        //.mobile.side-bar__contents-컨텐츠부분 클릭 시 클릭 이벤트가 부모클래스에까지 전파되어 창이 닫히는거 방지
        // .mobile-side-bar__btn-close- 버튼부분 클릭시 메뉴 외 부분까지
        //연결되서 두번 클릭되는 문제 해결
    },

    mobileSidebar_menus_2nd: function(){

        $('.mobile-side-bar__body__menu-box-1 > ul > li').click(function(e){
            e.stopPropagation();
            if($(this).hasClass('active')){
                $(this).removeClass('active');
                console.log("sidebar 2nd ul activated");
            }else{
                $(this).addClass('active');
                $(this).siblings().removeClass('active');
            }

        });
    }


};

mobile_index.init();