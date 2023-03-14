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

//Update Btn

$('.update_btn').on('click', function (e){

      let data = {
         id: $(this).parent().parent().children().eq(0).text(),
         price: $(this).parent().parent().children().eq(1).text(),
         stock: $(this).parent().parent().children().eq(2).text(),
         name: $(this).parent().parent().children().eq(3).text(),
         company: $(this).parent().parent().children().eq(4).text()
      }
      console.log(data);
});