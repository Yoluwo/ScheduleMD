/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* Toggle button minimizes navigation */
function toggleMenu() {
     let toggle = document.querySelector('.toggle');
     let navigation = document.querySelector('.navigation');
     let main = document.querySelector('.main');
     toggle.classList.toggle('active');
     navigation.classList.toggle('active');
     main.classList.toggle('active');
}

/* Create a new date object */
const date = new Date();

const loadCalendar = () => {

     date.setDate(1);

     /* Variable to store the days of the month */
     const monthDays = document.querySelector('.days');

     /* Variable to store the last day of the current month */
     const lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0).getDate();

     /* Variable to store the last day of the previous month */
     const prevLastDay = new Date(date.getFullYear(), date.getMonth(), 0).getDate();

     /* Variable to store the index number of the first day of the month */
     const firstDayIndex = date.getDay();

     /* Variable to store the index number of the last day of the current month */
     const lastDayIndex = new Date(date.getFullYear(), date.getMonth() + 1, 0).getDay();

     /* Variable to store the number of days from the next month to display */
     const nextDays = 7 - lastDayIndex - 1;

     /* Array to store the months of the year */
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

     /* Select the h1 heading elements to display current month*/
     document.querySelector('.date h1').innerHTML = months[date.getMonth()];

     /* Display the current date */
     document.querySelector('.date p').innerHTML = new Date().toDateString();

     /* Display the dates in the month of the calendar*/
     let days = "";

     /* Display the last dates of the previous month */
     for (let x = firstDayIndex; x > 0; x--) {
          days += `<div class="prev-date">${prevLastDay - x + 1}</div>`;
     }

     /* Display the dates of the month */
     for (let i = 1; i <= lastDay; i++) {
          /* Highlight the current date in the month */
          if (i === new Date().getDate() && date.getMonth() === new Date().getMonth()) {
               days += `<div class="today">${i}</div>`;
          } else {
               days += `<div>${i}</div>`;
          }
     }

     /* Display some dates from the next month */
     for (let j = 1; j <= nextDays; j++) {
          days += `<div class="next-date">${j}</div>`;
     }
     monthDays.innerHTML = days;
};

/* Making the arrows functional */
document.querySelector('.prev').addEventListener('click', () => {
     date.setMonth(date.getMonth() - 1);
     loadCalendar();
});

document.querySelector('.next').addEventListener('click', () => {
     date.setMonth(date.getMonth() + 1);
     loadCalendar();
});

loadCalendar();