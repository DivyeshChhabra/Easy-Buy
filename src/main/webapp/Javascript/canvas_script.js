window.addEventListener("load", () => {
    const canvas = document.querySelector("#canvas");
    const ctx = canvas.getContext("2d");
    
    canvas.style.width ='100%';
    canvas.style.height='100%';
    
    let drawing = false;
    //    let signature = document.getElementsByName('signature')[0];

    function start() {
        drawing = true;
    }

    function stop() {
        drawing = false;
    }

    function draw(e) {
        if (!drawing) {
            return;
        }

        ctx.lineWidth = 2;
        
        ctx.lineTo(e.clientX - 558, e.clientY - 109);
        ctx.stroke();
        
        console.log("x,y",e.clientX - 558, e.clientY - 109);
        console.log("mx,my",e.clientX,e.clientY);
    }

    canvas.addEventListener("mousedown", start);
    canvas.addEventListener("mouseup", stop);
    canvas.addEventListener("mousemove", draw);
});