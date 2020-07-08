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
import java.util.*;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gson.Gson;
import com.google.sps.data.Log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;


@WebServlet("/user")
public class UserServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    UserService userService = UserServiceFactory.getUserService();

    
    
    List<Log> Logs = new ArrayList<>();
    String status;
    String userEmail;
    String urlToRedirectToAfterUserLogsIn;
    String urlToRedirectToAfterUserLogsOut;
    String loginUrl;
    String logoutUrl;

    //get all the properties
    if (userService.isUserLoggedIn()) {
      status = "In";
      userEmail = userService.getCurrentUser().getEmail();
      urlToRedirectToAfterUserLogsIn = "/";
      urlToRedirectToAfterUserLogsOut = "/";
      loginUrl = "";
      logoutUrl = userService.createLogoutURL(urlToRedirectToAfterUserLogsOut);
    } 
        
    else {
      status = "Out";
      urlToRedirectToAfterUserLogsIn = "/";
      urlToRedirectToAfterUserLogsOut = "/";
      userEmail = "";
      loginUrl = userService.createLoginURL(urlToRedirectToAfterUserLogsIn);
      logoutUrl = "";
    }

    //create new log object
    Log log = new Log(status, loginUrl, logoutUrl, userEmail);
    //add to Logs
    Logs.add(log);
         
    Gson gson = new Gson();
    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(Logs));

  }


  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String status = request.getParameter("status");
    String loginUrl = request.getParameter("loginUrl");
    String logoutUrl = request.getParameter("logoutUrl");
    String userEmail = request.getParameter("userEmail");

    Entity logEntity = new Entity("Log");
   
    logEntity.setProperty("status", status);
    logEntity.setProperty("loginUrl", loginUrl);
    logEntity.setProperty("logoutUrl", logoutUrl);
    logEntity.setProperty("userEmail", userEmail);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(logEntity);

    //response.sendRedirect("/index.html");
  }
}
