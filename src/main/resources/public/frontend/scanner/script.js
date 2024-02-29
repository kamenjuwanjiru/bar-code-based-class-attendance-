// const { Html5QrcodeScanner } = require("html5-qrcode");
const camera = document.querySelector("#html5-qrcode-button-camera-start")
// , denied = document.querySelector(".denied")
// , granted = document.querySelector(".granted")
const scanner =new Html5QrcodeScanner('reader',{
    qrbox:{
        width: 500,
        height: 500
    }, 
     fps: 20,
})

var storedData = getSavedData()
storedData = JSON.parse(storedData)

scanner.render(success, error)

function success(result){
   scanner.clear()
    console.log(result)
    
    const data = {
        "idNo": result,
        "unitCode": $(".options").val()
    }
    $.ajax({
        type:"POST",
        contentType: "application/json",
        url:url+"any/getpersonel",
        data: JSON.stringify(data),
        success: function(results){
            console.log(results)
            var newData = {
                "idNo": results.idNo,
                "unitCode": $(".options").val()
            }
            if(results){
                //found the person being authenticated
                $.ajax({
                    type: "POST",
                    url: url+"rep/addhistory",
                    contentType: "application/json",
                    data: JSON.stringify(newData),
                    headers:{
                        "Authorization": `Bearer ${storedData.token}`
                    },
                    success: function(results){
                        console.log(results)
                    }
                })

                var image = document.querySelector(".image")

                image.src = `../pics/${results.email}-PRP.jpg`

                goodCode()
                
            }else{
               badCode()
                
            }
        }
    })
    }
    


function error(){
    // console.log("there was a problem")
}

 var results = document.querySelector(".results");
$(".back").click(()=>{history.back()})

 function badCode(){ 
    // denied.play()
    results.style.color = "red"
    results.innerHTML = "BAD CODE"
    scanner.render(success, error) 
    console.log("bad code provided")
  
}

function goodCode(){
    // granted.play()
   
    results.style.color = "green"
    results.innerHTML = "GOOD CODE"
    scanner.render(success, error)
    console.log("good code")
}
const options = document.querySelector(".options")

$(document).ready(()=>{
    $.ajax({
        type:"GET",
        url: url+"rep/getunits",
        contentType: "application/json",
        headers:{
            "Authorization": `Bearer ${storedData.token}`
        },
        success: function(results){
            for(var i = 0; i< results.length; i++){
                var option = `<option value='${results[i].unitCode}'>${results[i].unitCode}</option>`
                options.innerHTML += option
            }
        }
    })
})