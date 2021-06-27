/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

 function toggleMenu(){
                    let toggle = document.querySelector('.toggle');
                    let navigation = document.querySelector('.navigation');
                    let main = document.querySelector('.main');
                    toggle.classList.toggle('active');
                    navigation.classList.toggle('active');
                    main.classList.toggle('active');
}
