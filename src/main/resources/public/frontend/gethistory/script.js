const date = document.querySelector(".date"), tbody = document.querySelector(".tbody")
const unitcode = $(".unitcode"), uid = $(".uid"), staffno = $(".staffno")

$(".back").click(()=>{window.location = "../junction/index.html"})

var storedData = getSavedData()
storedData = JSON.parse(storedData)

$(".submit").click(()=>{
    var newdate = null
    var newuid = null
    var newstaffno = null
    var newunitcode = null
    if(date.value){
        newdate = formatDate(date.value)
    }
    if(unitcode.val()){
        newunitcode = unitcode.val()
    }
    if(uid.val()){
        newuid = uid.val()
    }
    if(staffno.val()){
        newstaffno = staffno.val()
    }
    
    tbody.innerHTML = ""
    var data = {
        "unitCode": newunitcode,
        "uid": newuid,
        "date": newdate,
        "staffNo": newstaffno 
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
                    <td>${results[index].timeStamp}</td>
                    <td>${results[index].unitCode}</td>
                    <td>${results[index].staffNo}</td>`
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