function getSavedData(){
    var data = localStorage.getItem("mydata")

    if(!data){
        goHome()
    }else{
        console.log(data)
        return data
    }
}

function goHome(){
    alertsession()
    window.location = "../main/index.html"
}

function clearStorage(){
    localStorage.removeItem("mydata")
    alertsession()
    window.location = "../main/index.html"
}

function alertsession(){
    alert("Your session expired, you need to login")
}