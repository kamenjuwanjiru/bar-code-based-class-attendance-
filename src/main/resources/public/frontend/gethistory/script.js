const date = document.querySelector(".date"), tbody = document.querySelector(".tbody")
const email = $(".email"), uid = $(".uid")

$(".back").click(()=>{window.location = "../junction/index.html"})

var storedData = getSavedData()
storedData = JSON.parse(storedData)

$(".submit").click(()=>{
    var newdate = formatDate(date.value)
    tbody.innerHTML = ""
    var data = {
        "email": email.val(),
        "uid": uid.val(),
        "date": newdate
    }

    $.ajax({
        type: "POST",
        url: url+"gethistory",
        contentType: "application/json",
        data: JSON.stringify(data),
        headers: {
            "Authorization": `Bearer ${storedData.token}`
        },
        success: function(results){
            if(results.status == "fail"){
                goHome()
            }else{
                $.each(results, (index)=>{
                    var trow = document.createElement('tr')
                    
                    var tdata = `
                    <td>${index+1}</td>
                    <td>${results[index].fullName}</td>
                    <td>${results[index].email}</td>
                    <td>${results[index].uid}</td>
                    <td>${results[index].date}</td>
                    <td>${results[index].timeStamp}</td>`
                    if(index%2 == 0){
                        trow.style.backgroundColor = "grey"
                    }

                    trow.innerHTML = tdata
                    tbody.append(trow)
                })
            }
        }
    })
})

function formatDate(date){
    date = date.split("-")
    date = `${date[2]}-${date[1]}-${date[0]}`
    return date
}
$(".footer").load("../footer/index.html")