//img sending post to db
$('.submit-btn').on("click",function(){

  console.log($('.post_title').val());
   console.log(editor.getData());

        let data = {
            title: $('.post_title').val(),
            body: editor.getData()
        }

        $.ajax({
            type: "POST",
            url:"/api/dashboard/body",
            data: JSON.stringify(data),
            contentType:"application/json; charset=utf-8",
            dataType: "json"
        }).done(function(resp){
            alert(`${data.title} successfully uploaded`);
            window.location.reload();
        }).fail(function(err){
            alert("Oops something went wrong")
        });

});
//
// CKEditor
let editor;
ClassicEditor
    .create( document.querySelector( '#writeEditor' ) , {
        mediaEmbed: {
            previewsInData:true
        },

        fontsize: {
            options: [
            10,
                11,
                12
            ]
        }
        // toolbar: {
        //     items:[
        //         'undo', 'redo',
        //         '|', 'heading',
        //         '|', 'fontfamily', 'fontsize', 'fontColor', 'fontBackgroundColor',
        //         '|', 'bold', 'italic', 'strikethrough', 'subscript', 'superscript', 'code',
        //         '|', 'link', 'uploadImage', 'blockQuote', 'codeBlock',
        //         '|', 'bulletedList', 'numberedList', 'todoList', 'outdent', 'indent'
        //     ]
        // }


        ,

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

