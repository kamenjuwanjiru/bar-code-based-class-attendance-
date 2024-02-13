$(".footer").load("../footer/index.html")

var storedData= getSavedData()
storedData = JSON.parse(storedData)

const unitcode = $(".unitcode"), unitname = $(".unitname")

$(".submit").click(()=>{
    if(!unitcode.val() || !unitname.val()){
        alert("fill out the fields")
        return
    }
    var data = {
        "unitCode": unitcode.val(),
        "unitName": unitname.val()
    }

    $.ajax({
        type: "POST",
        url: url+"rep/addupdateunit",
        contentType: "application/json",
        data: JSON.stringify(data),
        headers:{
            "Authorization":`Bearer ${storedData.token}`
        },
        success: function(results){
            if(results.status){
                goHome()
            }else{
                alert("Success, added unit")
                window.location.reload()
            }
        }
    })
})
$(".back").click(()=>{window.location = "../junction/index.html"})