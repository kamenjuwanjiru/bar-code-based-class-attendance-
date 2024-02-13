$(".footer").load("../footer/index.html")

var storedData = getSavedData()
storedData = JSON.parse(storedData)

const newpassword = $(".newpassword"), oldpassword = $(".oldpassword")

$(".submit").click(()=>{
    if(!newpassword.val() || !oldpassword.val()){
        alert("fill out the fields")
        return;
    }
    var data = {
        "newPassword": newpassword.val(),
        "oldPassword": oldpassword.val()
    }
    $.ajax({
        type: "POST",
        url: url+"updatepass",
        contentType: "application/json",
        data: JSON.stringify(data),
        headers:{
            "Authorization": `Bearer ${storedData.token}`
        },
        success: function(results){
            if(results == true){
                alert("Updated password successfully")
                window.location.reload()
            }else if(results.status){
                goHome()
            }else if(results == false){
                alert("Old password is incorrect")
            }
        }
    })
})

$(".back").click(()=>{
    window.location = "../junction/index.html"
})