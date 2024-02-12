// const { Html5QrcodeScanner } = require("html5-qrcode");
const camera = document.querySelector("#html5-qrcode-button-camera-start")
, denied = document.querySelector(".denied")
, granted = document.querySelector(".granted")
const scanner =new Html5QrcodeScanner('reader',{
    qrbox:{
        width: 400,
        height: 400
    }, 
     fps: 20,
})

scanner.render(success, error)

function success(result){
   scanner.clear()
    console.log(result)
    if(!result.includes("StoredData")){
        setTimeout(()=>{
            badCode();
        }, 100)
    }else{
        
         //getting uid from scan result
    result = result.split(",")[1].split("=")[1].split(")")[0]
        
    console.log(result)
    const data = {
        "uid": result
    }
    $.ajax({
        type:"POST",
        contentType: "application/json",
        url:"http://localhost:8080/any/getpersonel",
        data: JSON.stringify(data),
        success: function(results){
            console.log(results)
            if(results){
                //found the person being authenticated
                $.ajax({
                    type: "POST",
                    url: "http://localhost:8080/any/addhistory",
                    contentType: "application/json",
                    data: JSON.stringify(data),
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