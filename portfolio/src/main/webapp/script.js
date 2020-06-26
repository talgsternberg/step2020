// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Adds a random fact to the page.
 */

 const facts =
      ['If I was stuck on a desert island, I would bring a good book', 
      'My favorite animal is a lizard', 'I grew up in Boston, MA', 
      'The food I eat most is Cheerios', 'When I graduate, I plan on moving to Australia', 
      'I learned to surf when I was eight', 'I was the cross country captain for my high school team', 
      'My favorite color is yellow', 'I am super interested in the ocean and marine life'];
function addRandomFact() {
  var i = facts.length-1;
  if(0 <= i){
    // Pick a random fact
    var factIndex = Math.floor(Math.random() * facts.length)
    const fact = facts[factIndex];
    
    // Add it to the page.
    const factContainer = document.getElementById('fact-container');
    factContainer.innerText = fact;
    
    //remove this fact and the null from the array
    facts.splice(factIndex,1);

    //update i 
    i = facts.length-1;
  }
}



// function to calculate the result of the survey
function tabulateAnswers() {
  // initialize variables for each choice's score
  // If you add more choices and outcomes, you must add another variable here.
  var c1score = 0;
  var c2score = 0;
  var c3score = 0;
  var c4score = 0;
  
  // get a list of the radio inputs on the page
  var choices = document.getElementsByTagName('input');
  // loop through all the radio inputs
  for (i=0; i<choices.length; i++) {
    // if the radio is checked..
    if (choices[i].checked) {
      // add 1 to that choice's score
      if (choices[i].value == 'c1') {
        c1score = c1score + 1;
      }
      if (choices[i].value == 'c2') {
        c2score = c2score + 1;
      }
      if (choices[i].value == 'c3') {
        c3score = c3score + 1;
      }
      if (choices[i].value == 'c4') {
        c4score = c4score + 1;
      }
    }
  }
  // Find out which choice got the highest score.
  var maxscore = Math.max(c1score,c2score,c3score,c4score);
  
  // Display answer corresponding to that choice
  var answerbox = document.getElementById('answer');
  if (c1score == maxscore) { 
    answerbox.innerHTML= "You should go surfing! You like to take risks and enjoy fast paced and challenging activities!";
  }
  if (c2score == maxscore) { 
    answerbox.innerHTML = "You should go canoeing/kayaking! If you want a bit more thrill be sure to head down the rapids. Remember to wear a lifejacket!";
  }
  if (c3score == maxscore) { 
    answerbox.innerHTML = "You should go hiking! Grab some sneakers and get ready to climb the mountains for that perfect view. Don't forget a camera!";
  }
  if (c4score == maxscore) { 
    answerbox.innerHTML = "You should have a picnic in the park/on a field. Maybe make a special treat like cupcakes and bring a kite or frisbee!";
  }
}

// program the reset button
function resetAnswer() {
  var answerbox = document.getElementById('answer');
  answerbox.innerHTML = "Your result will show up here!";
}