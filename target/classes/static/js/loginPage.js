let index ={
    intit: function(){
        $('.login_btn').on("click",()=>{
            this.login();
            console.log("****************Login btn click************");
        });
        $('.join_create_account').on("click",()=>{
            this.join();
            console.log("****************Join btn click************");
        });
    },
    login: function(){
        let data = {
            username: $('#login_username').val(),
            password: $('#login_password').val()
        }
        console.log(data);

        //connecting to rest controller
        $.ajax({
            type: "POST",
            url:"/api/users/loginProc",
            data: JSON.stringify(data),
            contentType:"application/json; charset=utf-8",
            dataType: "json"
        }).done(function(resp){
            alert("Log-in Success");
            alert(resp);
            location.href="/page/main";
        }).fail(function(error){
            alert("Failed to Login"+JSON.stringify(error));
            console.log(JSON.stringify(error));
        });
    },
    join: function(){

        let data = {
            username: $("#join_username").val(),
            password: $("#join_password").val(),
            email: $("#join_email").val()
        }
        console.log(data);

        $.ajax({
            type:"POST",
            url:"/api/users/joinProc",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function(resp){
            alert("Join success! Please Sign-in");
            alert(resp);
            window.location.reload();
            location.href="/page/login"
        }).fail(function(error){
            alert("join process failed. check credentials again");
            console.log(JSON.stringify(error));
            window.location.reload();
        });
    }



}

console.log("loginPage.js connection confirm");
index.intit();