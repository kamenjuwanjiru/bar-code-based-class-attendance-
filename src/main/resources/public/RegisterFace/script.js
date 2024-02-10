const video = document.querySelector(".video")
const button = document.querySelector(".capture")



//start stream
async function videoStream(){
    try{
        const stream = await navigator.mediaDevices.getUserMedia({
            video: true,
            audio: false
        });

        video.srcObject = stream
        button.addEventListener('click', ()=>{
            
            const canvas = document.createElement('canvas')

            canvas.height = video.height
            canvas.width = video.width

            canvas.getContext('2d').drawImage(video, 0, 0, video.width,video.height)

            let img = canvas.toDataURL('image/png').replace('image/png', 1.0)

            const atag = document.createElement('a')
            atag.href = img
            atag.download = `./${document.querySelector(".name").value}.png`
            document.body.appendChild(atag)
            atag.click()
        })
        
    }catch(err){
        console.log(err);
    }
}

videoStream()