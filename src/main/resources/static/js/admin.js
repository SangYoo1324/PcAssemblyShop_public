console.log("adminPage");

//class variable
let upload_image = false;


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

//Update modal trigger Btn(just showing former data already inside db)
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

// generates formdata for the 2 update functions (with_image && without_image)
function updateFormDataGenerator(){
   let form_data = new FormData();
//putting info into formdata
   form_data.append("category",$('.update_category').val());
   console.log($('.update_category').val());
   form_data.append("id", $('#update_id').val());
   form_data.append("price",  $('#update_price').val());
   form_data.append("stock",$('#update_stock').val());
   form_data.append("name",$('#update_name').val() );
   form_data.append("company",$('#update_company').val());
   form_data.append("featured_env",$('#update_featured_env').val());
   return form_data;
}





//save update btn
$('.save_update').on('click',function(){
   console.log("save update button click");

   //generating form_data
   let form_data_with_pic= updateFormDataGenerator();

   // Getting image info & put it into form data
var file = $('#update_file')[0].files[0];
   form_data_with_pic.append("file",file);
console.log(file);

// confirming all data inputs valid
   form_data_with_pic.forEach((a)=>console.log(a));

if(upload_image){
   $.ajax({
      data: form_data_with_pic,
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
         alert("error occured. Please check if you uploaded the pic or pushed no image button");
      }
   });
}else{
   let form_data_without_pic = updateFormDataGenerator();

   $.ajax({
      data: form_data_without_pic,
      type: "PATCH",
      url: "/api/itemUpdate/noImage",
      cache: false,
      contentType: false,
      enctype: 'multipart/form-data',
      processData: false,
      success: function (url) {
         alert("Success updated file without Image :::::" );
         window.location.reload();
      },
      error: function (err) {
         alert(err);
      }
   });
}



});

//save update without changing picture
// $('.keep_image').on('click',function(){
//    console.log("keep image click");
//
//    no_image= !no_image;
//    if(no_image){
//       alert("You don't need to add Image. Keeping former Image");
//
//    }else{
//       alert("Image update enabled. You need to upload Image to process update.");
//    }


// });
$('#switch').change(function(){
   if($(this).prop("checked")){
      upload_image=true;
      alert("upload Image enabled.");
   }else{
      upload_image= false;
      alert(`uploadImage=${upload_image}`);
   }
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
   form_data.append("category", $('.update_category').val());
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


//email sender
// CKEditor
let editor;
ClassicEditor
    .create( document.querySelector( '#writeEditor' ) , {
       mediaEmbed: {
          previewsInData:true
       },

       fontSize: {
          options: [
             'default',
             9,
             11,
             13,
             '17px',
             19,
             21
          ]
       },
       // toolbar: {
       //     items:[
       //         'undo', 'redo',
       //         '|', 'heading',
       //         '|', 'fontFamily', 'fontSize', 'fontColor', 'fontBackgroundColor',
       //         '|', 'bold', 'italic', 'strikethrough', 'subscript', 'superscript', 'code',
       //         '|', 'link', 'uploadImage', 'blockQuote', 'codeBlock', 'mediaEmbed',
       //         '|', 'bulletedList', 'numberedList', 'todoList', 'outdent', 'indent'
       //     ]
       // },




       ckfinder: {
          uploadUrl: '/api/dashboard/image'
       }

    } )
    .then( newEditor => {
       editor = newEditor;
    } )

    .then( editor => {
       window.editor = editor;

    } )
    .catch( error => {
       console.error( 'Oops, something went wrong!' );
       console.warn( 'Build id: g64ljk55ssvc-goqlohse75uw' );
       console.error( error );
    } );

// sending data to eamil controller

$('.email_submit-btn').on("click",function(){

   console.log($('.post_title').val());
   console.log(editor.getData());

   var file = $('#attachment')[0]  // html DOM id=file 자체
       .files[0];  // input DOM에 저장된 file들 중 첫번째꺼( 어차피 한개밖에 선택 못함)
   console.log(file);
   let form_data = new FormData();
   form_data.append("file", file);
   form_data.append("subject",$('.post_title').val());
   form_data.append("message",editor.getData());

   console.log(form_data.get("file").toString());

   $.ajax({
      data: form_data,
      type: "POST",
      url: "/api/send",
      cache: false,
      contentType: false,
      enctype: 'multipart/form-data',
      processData: false,
   }).done(function(resp){
      alert(`${form_data.get("subject")} successfully sent`);
      window.location.reload();
   }).fail(function(err){
      alert("oops something went wrong");
      window.location.reload();
   });

});