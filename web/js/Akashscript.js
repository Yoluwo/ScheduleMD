let setTheme = document.querySelector('.settings');
let themeButton = document.getElementById('theme');
let accountQuality = document.querySelector(".quality");
let doNotDisturb = document.getElementById('offline');
let daysOfTheWeek = ["Monday", "Tuesday","Wednesday", "Thursday", "Friday", "Saturday", "Sunday"];
let mylist = document.querySelector('.mylist');
let timeout = document.querySelector('#timeout');



const removeElements = (element) =>{
      element.innerHTML = '';
}


doNotDisturb.addEventListener('click', function(){
    
    if(doNotDisturb.checked){
       
            for(let days of daysOfTheWeek){
       const newListItem = document.createElement("li");
   let cb = document.createElement( "input" );
   cb.type = "checkbox";
      newListItem.appendChild(cb);
    let text = document.createTextNode(days);
     newListItem.appendChild = cb;
     mylist.appendChild(newListItem);
     mylist.appendChild(text);  
     
     setTheme.style.height ="800px";
   }
   }
   else{
       removeElements(mylist);
       setTheme.style.height = "500px";
    }
 
 } )

timeout.value = 0;
console.log(timeout.value);
timeout.addEventListener('input', () =>{
   console.log(timeout.value)
   if(parseInt(timeout.value) === 3){
    setTimeout(() =>{
        window.location.href="http://localhost:8084/Schedule-MD/login"
    },180000)
}

})






 

//toggle = false;
themeButton.addEventListener('click', function () {
     if (setTheme.classList.contains('dark')) {
          setTheme.classList.remove('dark');
          setTheme.classList.add("light");
          themeButton.textContent = "LIGHT";

     } else if (setTheme.classList.contains("light")) {
          setTheme.classList.remove("light");
          setTheme.classList.add("dark");
          themeButton.textContent = "DARK";
     }
});

//accountQuality.addEventListener('click', function () {
//     if (accountQuality.textContent === "HIGH") {
//          accountQuality.textContent = "LOW";
//     } else {
//          accountQuality.textContent = 'HIGH';
//     }
//});