// const { Html5QrcodeScanner } = require("html5-qrcode");
const camera = document.querySelector("#html5-qrcode-button-camera-start")
const scanner =new Html5QrcodeScanner('reader',{
    qrbox:{
        width: 300,
        height: 300
    }, 
     fps: 20,
})

scanner.render(success, error)

function success(result){
    document.querySelector("#result").innerHTML += `${result}<br>`
    scanner.clear()
    setTimeout(()=>{
        scanner.render(success, error)
    },1000)
}
function error(){
    console.log("there was a problem")
}
// navigator.mediaDevices.enumerateDevices()
//   .then(devices => {
//     const cameras = devices.filter(device => device.kind === 'videoinput');
//     cameras.forEach(camera => {
//       console.log('Camera ID:', camera.deviceId, 'Label:', camera.label);
//     });
//   })
//   .catch(err => {
//     console.error('Error accessing media devices:', err);
//   });