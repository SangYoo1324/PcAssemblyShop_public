console.log("adminPage");


//Delete Btn
$('.delete_btn').on("click",function(e){
   console.log(e.target);

   let data = {
      name : $(this).attr("data-name"),
      company: $(this).attr("data-company")
   };

   $.ajax({
      type:"DELETE",
      url:"/api/item",
      data: JSON.stringify(data),
      contentType:"application/json; charset=utf-8",
      dataType: "json"

   }).done(function(resp){
      alert(`${data.name} successfully deleted from DB`);
      window.location.reload();
   }).fail(function (err){
      alert(`${data.name} successfully deleted from DB`);
      window.location.reload();
   });

});

//Update modal trigger Btn
$('.update_btn').on('click', function (e){

      let data = {
         id: $(this).parent().parent().children().eq(0).text(),
         price: $(this).parent().parent().children().eq(1).text(),
         stock: $(this).parent().parent().children().eq(2).text(),
         name: $(this).parent().parent().children().eq(3).text(),
         company: $(this).parent().parent().children().eq(4).text(),
         category: $(this).attr("data-category")
      }
      console.log(data);


      //category automatically set to former assigned value
      if(data.category==1){
         $('.update_category').val('1');
      }
   if(data.category==2){
      $('.update_category').val('2');
   }
   if(data.category==3){
      $('.update_category').val('3');
   }
   //update the former info to the input screen
   $('#update_id').val(data.id);
   $('#update_name').val(data.name);
   $('#update_company').val(data.company);
   $('#update_price').val(data.price);
   $('#update_stock').val(data.stock);





});


//save update btn
$('.save_update').on('click',function(){
   console.log("save update button click");
   // Getting image info
var file = $('#update_file')[0].files[0];
console.log(file);
let form_data = new FormData();

//putting info into formdata
form_data.append("file",file);
form_data.append("category",$('.update_category').val());
console.log($('.update_category').val());
form_data.append("id", $('#update_id').val());
form_data.append("price",  $('#update_price').val());
form_data.append("stock",$('#update_stock').val());
form_data.append("name",$('#update_name').val() );
form_data.append("company",$('#update_company').val());
form_data.append("featured_env",$('#update_featured_env').val());
//just confirming all data are on the formdata
form_data.forEach((a)=>console.log(a));


   $.ajax({
      data: form_data,
      type: "POST",
      url: "/api/itemUpdate",
      cache: false,
      contentType: false,
      enctype: 'multipart/form-data',
      processData: false,
      success: function (url) {
         alert("Success updated file :::::" );
         window.location.reload();
      },
      error: function (err) {
         alert(err);
      }
   });


});


//Creating Item btn
$('.submit-btn').on('click',function() {
   console.log("submit btn click");
   imgSender("itemInfo","POST");
});



//image sender
function imgSender(target,method){
   var file = $('#file')[0]  // html DOM id=file 자체
       .files[0];  // input DOM에 저장된 file들 중 첫번째꺼( 어차피 한개밖에 선택 못함)
   console.log(file);
   let form_data = new FormData();
   form_data.append("file", file);
   form_data.append("category", $('.category').val());
   form_data.append("name",$('#name').val());
   form_data.append("company",$('#company').val());
   form_data.append("price",$('#price').val());
   form_data.append("stock",$('#stock').val());
   form_data.append("featured_env",$('#featured_env').val());
   $.ajax({
      data: form_data,
      type: method,
      url: "/api/"+target,
      cache: false,
      contentType: false,
      enctype: 'multipart/form-data',
      processData: false,
      success: function (url) {
         alert("Success sending file to :::::" + url);
         window.location.reload();
      },
      error: function (err) {
         alert(err);
      }
   });
}