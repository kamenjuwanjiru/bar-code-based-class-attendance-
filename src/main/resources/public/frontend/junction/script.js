var storedData = getSavedData()
storedData = JSON.parse(storedData)

if(storedData.privilege != "ADMIN"){
    document.querySelector(".register").style.display = "none"
}
$(".logout").click(()=>{clearStorage()})

$(".footer").load("../footer/index.html")