// const { Html5QrcodeScanner } = require("html5-qrcode");
const camera = document.querySelector("#html5-qrcode-button-camera-start")
, denied = document.querySelector(".denied")
, granted = document.querySelector(".granted")
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
    document.querySelector(".results").innerHTML+=`${result}<br>`
    if(!result.includes("StoredData")){
        setTimeout(()=>{
            badCode();
        }, 100)
    }else{
        
         //getting uid from scan result
    result = result.split(",")[1].split("=")[1].split(")")[0]
        
    console.log(result)
    const data = {
        "uid": result,
        "unitCode": $(".options").val()
    }
    $.ajax({
        type:"POST",
        contentType: "application/json",
        url:url+"any/getpersonel",
        data: JSON.stringify(data),
        success: function(results){
            console.log(results)
            if(results){
                //found the person being authenticated
                $.ajax({
                    type: "POST",
                    url: url+"rep/addhistory",
                    contentType: "application/json",
                    data: JSON.stringify(data),
                    headers:{
                        "Authorization": `Bearer ${storedData.token}`
                    },
                    success: function(results){
                        console.log(results)
                    }
                })
                goodCode()
                
            }else{
               badCode()
                
            }
        }
    })
    }
    
}


function error(){
    // console.log("there was a problem")
}


$(".back").click(()=>{history.back()})

 function badCode(){ 
    denied.play()
    scanner.render(success, error) 
    console.log("bad code provided")
  
}

function goodCode(){
    granted.play()
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