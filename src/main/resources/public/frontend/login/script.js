const username = $("#username")
const password = $("#password")

$("#submit").click(()=>{
        // alert(username.val()+" "+password.val());
    const data = {
        "username": username.val(),
        "password": password.val()
    }
$("#submit").css({
    "display": "none"
})
$(".loader").css({
    "display": "block"
})
    $.ajax({
        type: "POST",
        url:"http://localhost:8080/any/login",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function(results){
            $(".loader").css({
                "display": "none"
            })
            $("#submit").css({
                "display": "block"
            })
           console.log(results)
           if(results.result != null){
                alert("bad credentials")
           }else{
                localStorage.setItem("mydata", JSON.stringify(results))
                window.location= "../junction/index.html"
           }
        }
    })
})