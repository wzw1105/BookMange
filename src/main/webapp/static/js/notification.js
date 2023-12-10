function notification(ok, text){
    let toasts = document.querySelector('.toasts')

    toasts.innerText = text
    toasts.classList.add('notification')
    toasts.classList.add(ok ? 'normal_notification' : 'error_notification')
    setTimeout(()=>{
        toasts.remove()
    }, 2000)

}