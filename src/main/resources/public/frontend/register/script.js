const username = $(".username"), password = $(".password"), who = $(".who")

$(".back").click(()=>{window.location = "../junction/index.html"})

var storedData = getSavedData()
storedData = JSON.parse(storedData)

if(storedData.privilege != "ADMIN"){
    document.location= "../junction/index.html"
}

$(".submit").click(()=>{
    var myData = {
        "username": username.val(),
        "password": password.val()
    }

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url+"admin/register/"+who.val(),
        data: JSON.stringify(myData),
        headers: {
            "Authorization": `Bearer ${storedData.token}`
        },
        success: function(results){
           if(results.message == "user exist"){
                alert("User exists")
           }else if(results.status =="fail"){
                clearStorage()
           }else if(results){
              alert("success");
                window.location.reload()
           }
        }

    })
})
$(".footer").load("../footer/index.html")