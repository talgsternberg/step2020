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


/** Fetches tasks from the server and adds them to the DOM. */
function loadComments() {
  fetch('/data?choice=' + document.getElementById('numInput').value).then(response => response.json()).then((UserComments) => {
  const CommentListElement = document.getElementById('comment-list');
  CommentListElement.innerHTML = "";
  UserComments.forEach((comment) => {
    CommentListElement.appendChild(createCommentElement(comment));
    })
  });
}

/** Creates an element that represents a task, including its delete button. */
function createCommentElement(comment) {
  const commentElement = document.createElement('li');
  commentElement.className = 'comment';

  const textElement = document.createElement('span');
  textElement.innerText = comment.text;

  const deleteButtonElement = document.createElement('button');
  deleteButtonElement.innerText = 'Delete';
  deleteButtonElement.addEventListener('click', () => {
    deleteComment(comment);

    // Remove the comment from the DOM.
    commentElement.remove();
  });
  commentElement.appendChild(deleteButtonElement);
  commentElement.appendChild(textElement);
  return commentElement;
}

/** Tells the server to delete the comment. */
function deleteComment(comment) {
  const params = new URLSearchParams();
  params.append('id', comment.id);
  fetch('/delete-comment', {method: 'POST', body: params});
}


function UserLogin(){
    fetch('/user?Logs').then(response => response.json()).then((Logs) => {
        loggedInStatus = Logs[0]; // since we have an array
        console.log(loggedInStatus["status"]);
        console.log(loggedInStatus["loginUrl"]);
        console.log(loggedInStatus["logoutUrl"]);

        if(loggedInStatus["status"] == "Out"){
          document.getElementById("userOnly").style.display = "none";
          document.getElementById("notLoggedIn").style.display = "block";
          document.getElementById("logOut").style.display = "none";
          document.getElementById("logInButton").href = loggedInStatus["logoutUrl"];//shouldn't this redirect to login?

        }
        else{
          document.getElementById("userOnly").style.display = "block";
          document.getElementById("notLoggedIn").style.display = "none";
          document.getElementById("logOut").style.display = "block";
          document.getElementById("logOutButton").href = loggedInStatus["logoutUrl"];//shouldn't this redirct to logout?

        }
    });
}









