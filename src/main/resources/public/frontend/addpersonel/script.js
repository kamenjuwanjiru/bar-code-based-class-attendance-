var storedData = getSavedData();
storedData = JSON.parse(storedData)
$(".submit").click(()=>{
    var data = {
        "fullName": $(".fullname").val(),
        "uid": $(".uid").val(),
        "email": $(".email").val()
    }
    $(".submit").css({
        "display": "none"
    })
    $(".loader").css({
        "display": "block"
    })
    var formData = new FormData()

    var image = document.querySelector(".image").files[0]
    if(image == null){
        alert("image required")
        return
    }
    formData.append("data", JSON.stringify(data))
    formData.append("image", image)

    $.ajax({
        type: "POST",
        url: url+"addpersonel",
        processData: false,
        contentType: false,
        data: formData,
        headers: {
            "Authorization": `Bearer ${storedData.token}`
        },
        success: function(results){
            console.log(results)
            if(results){
                if(results.message == "fail authorization"){
                    goHome()
                }else if(results.email == $(".email").val()){
                    alert("Personel added")
                    window.location.reload()
                }else{
                    $(".submit").css({
                        "display": "block"
                    })
                    $(".loader").css({
                        "display": "none"
                    })
                    alert("personel Exists")
                }
            }
        }
    })
})

$(".back").click(()=>{
    window.location = "../junction/index.html"
})
$(".footer").load("../footer/index.html")