$('.add_cart_btn').on('click',function(e) {
    // button click이 발생한 곳 자체
    console.log(e.target);
    console.log(e.target.getAttribute("data-item-id"));
    let data = {
        item_id: e.target.getAttribute("data-item-id")
    }
    let form_data = new FormData();
    form_data.append("item_id",e.target.getAttribute("data-item-id"));
    $.ajax({
        data: form_data,
        type: 'POST',
        cache: false,
        processData: false,
        contentType: false,
        url: "/api/addToCart"
    }).done(function(resp){
        alert(JSON.stringify(resp)+"added to cart");
    }).fail(function(err){
        alert(JSON.stringify(err));
    });
});