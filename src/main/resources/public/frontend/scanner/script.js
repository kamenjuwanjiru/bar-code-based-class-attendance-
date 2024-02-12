// const { Html5QrcodeScanner } = require("html5-qrcode");
const camera = document.querySelector("#html5-qrcode-button-camera-start")
const scanner =new Html5QrcodeScanner('reader',{
    qrbox:{
        width: 400,
        height: 400
    }, 
     fps: 20,
})

scanner.render(success, error)

function success(result){
    document.querySelector("#result").innerHTML += `${result}<br>`
    scanner.clear()
    //getting uid from scan result
    try{
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

                $.ajax({
                    type: "POST",
                    url: "http://localhost:8080/any/addhistory",
                    contentType: "application/json",
                    data: JSON.stringify(data),
                    success: function(results){
                        console.log(results)
                    }
                })

                scanner.render(success, error)
            }else{
                badCode()
            }
        }
    })
    }catch(error){
        badCode()
    }
    
}
function error(){
    // console.log("there was a problem")
}
$(".back").click(()=>{
    history.back()
})

function badCode(){
    console.log("bad code provided")
    scanner.render(success, error)
}