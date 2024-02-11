const uid = $(".lookuid")

var storedData = getSavedData()

storedData = JSON.parse(storedData)

$('.back').click(()=>{window.location = "../junction/index.html"})

$(".look").click(()=>{

    $(".nothingfound").css({
        "display": "none"
    })
    $(".found").css({
        "display": "none"
    })

    
    console.log(uid.val())

    const data = {
        "uid": uid.val()
    }
    $.ajax({
        type:"POST",
        contentType: "application/json",
        url:"http://localhost:8080/any/getpersonel",
        data: JSON.stringify(data),
        success: function(results){
            console.log(results)
            if(results){
                $(".found").css({
                    "display": "block"
                })
                //displaying details
                document.querySelector(".profilepic").src = `../pics/${results.email}-PRP.jpg`
                document.querySelector(".fullname").textContent = results.fullname
                document.querySelector(".uid").textContent = results.uid
                document.querySelector(".email").textContent = results.email
                document.querySelector(".barcode").src = `../pics/${results.email}-BAR.jpg`
                document.querySelector(".qrcode").src = `../pics/${results.email}-QR.jpg`
            }else{
                $(".nothingfound").css({
                    "display": "block"
                })
            }
        }
    
    })
})

//delete personel
$(".delete").click(()=>{
    console.log(storedData.token)
    if(confirm("Do you want to delete this personel?")){
        $.ajax({
            type: "POST",
            url: "http://localhost:8080/deletepersonel",
            contentType: "application/json",
            data: JSON.stringify({
                "uid": uid.val()
            }),
            headers: {
                "Authorization": `Bearer ${storedData.token}`
            },
            success: function(results){
         
                console.log(results)
                if(results.status == "fail"){
                    clearStorage()
                }else{
                    alert("Success")
                    window.location.reload()
                }
            }
        })
    }
})