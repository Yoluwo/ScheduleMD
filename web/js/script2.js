let setTheme = document.querySelector('.settings');
let themeButton = document.getElementById('theme');
let accountQuality = document.querySelector(".quality");


themeButton.addEventListener('click', function () {
    if (setTheme.classList.contains('dark')){
        setTheme.classList.remove('dark');
        setTheme.classList.add("light");
        themeButton.textContent = "LIGHT";

    }
    else if (setTheme.classList.contains("light")) {
        setTheme.classList.remove("light");
        setTheme.classList.add("dark");
        themeButton.textContent = "DARK";
        } 
});

accountQuality.addEventListener('click', function (){
    if(accountQuality.textContent === "HIGH"){
        accountQuality.textContent = "LOW";
    } else {
        accountQuality.textContent = 'HIGH';
    }
});