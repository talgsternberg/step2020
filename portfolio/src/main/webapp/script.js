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


function getRandomGreeting() {
  console.log('Fetching a random greeting.');

  // The fetch() function returns a Promise because the request is asynchronous.
  const responsePromise = fetch('/data');

  // When the request is complete, pass the response into handleResponse().
  responsePromise.then(handleResponse);
}

/**
 * Handles response by converting it to text and passing the result to
 * addQuoteToDom().
 */
function handleResponse(response) {
  console.log('Handling the response.');

  // response.text() returns a Promise, because the response is a stream of
  // content and not a simple variable.
  const textPromise = response.text();

  // When the response is converted to text, pass the result into the
  // addQuoteToDom() function.
  textPromise.then(addQuoteToDom);
}

/** Adds a random quote to the DOM. */
function addQuoteToDom(quote) {
  console.log('Adding quote to dom: ' + quote);

  const quoteContainer = document.getElementById('quote-container');
  quoteContainer.innerText = quote;
}
