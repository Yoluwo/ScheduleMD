let noti = document.querySelector('.noti');
let social = document.querySelector('.social');
let height = document.querySelector('.settings');
noti.addEventListener('click', () =>{
      if(notifications.checked){
           const email = document.createElement('input');
           const number = document.createElement('input');
           email.value= "test@gmail.com";
         number.value = "587-999-3211";
           social.appendChild(email);
          social.appendChild(number);
          
          if(height.offsetHeight < 800){
              console.log(height)
              setTheme.style.height = "600px";
          }
          else if(height.offsetHeight === 800){
              console.log(height)
              setTheme.style.height = "900px";
          }
          
       }
       else{
           social.innerHTML = "";
           if(height.offsetHeight === 900){
               setTheme.style.height = "900px";
           }
       
         
       }
})