/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*Toggle button minimizes navigation */
function toggleMenu() {
     let toggle = document.querySelector('.toggle');
     let navigation = document.querySelector('.navigation');
     let main = document.querySelector('.main');
     toggle.classList.toggle('active');
     navigation.classList.toggle('active');
     main.classList.toggle('active');
}

/*Create a new date object */
const date = new Date();

/*Get current month*/
const month = date.getMonth();

/*Array to store the months of the year */
const months = [
     "January",
     "February",
     "March",
     "April",
     "May",
     "June",
     "July",
     "August",
     "September",
     "October",
     "November",
     "December"
];

/*Select the h1 heading elements to display current month*/
document.querySelector('.date h1').innerHTML = months[date.getMonth()];

/*Display the current date */
document.querySelector('.date p').innerHTML = date.toDateString();