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

package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gson.Gson;
import com.google.sps.data.Comment;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

    
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Query query = new Query("Comment").addSort("timestamp", SortDirection.DESCENDING);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    int userChoice = 1; //getUserChoice(request);


    List<Comment> UserComments = new ArrayList<>();
    int commentNum = 0;
    for (Entity entity : results.asIterable()) {
        if(commentNum < userChoice){
          long id = entity.getKey().getId();
          String text = (String) entity.getProperty("text");
          long timestamp = (long) entity.getProperty("timestamp");

          Comment comment = new Comment(id, text, timestamp);
          UserComments.add(comment);
          commentNum++;
        } 
    }

    Gson gson = new Gson();

    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(UserComments));
  }
  

@Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String text = request.getParameter("text");
    long timestamp = System.currentTimeMillis();

    Entity commentEntity = new Entity("Comment");
    commentEntity.setProperty("text", text);
    commentEntity.setProperty("timestamp", timestamp);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(commentEntity);

    response.sendRedirect("/index.html");
  }


   
   
    /** Returns the choice entered by the user, or 3 if the choice was invalid. */
  private int getChoice(HttpServletRequest request) {
    // Get the input from the form.
    String ChoiceString = request.getParameter("choice");

    // Convert the input to an int.
    int userChoice;
    try {
      userChoice = Integer.parseInt(ChoiceString);
    } catch (NumberFormatException e) {
      System.err.println("Could not convert to int: " + ChoiceString);
      return 3;
    }

    // Check that the input is between 1 and 15.
    if (userChoice < 1 || userChoice > 15) {
      System.err.println("Choice is out of range: " + ChoiceString);
      return 3;
    }

    return userChoice;
  }
}
