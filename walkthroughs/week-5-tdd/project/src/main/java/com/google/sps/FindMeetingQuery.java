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

package com.google.sps;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public final class FindMeetingQuery {

  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    Collection<TimeRange> availibility = new ArrayList();
    Collection<TimeRange> temp_availibility = new ArrayList();


     //no conflict
      if (events.isEmpty()){
          availibility = Arrays.asList(TimeRange.WHOLE_DAY);
      }

    //no attendees -- first test
    if (request.getAttendees().isEmpty()){
        availibility = Arrays.asList(TimeRange.WHOLE_DAY);
       
        //return the collection
        return availibility;
    }

    //no options too long of a request (how do I access time range here? THis is an error) -- second test
    else if (request.getDuration() > TimeRange.WHOLE_DAY.duration()){
        availibility = Arrays.asList();
        
        //return the collection
        return availibility;
    }

    //event splits day into two options (before and after) -- third test
    for (Event event : events){
        availibility =
        Arrays.asList(TimeRange.fromStartEnd(TimeRange.START_OF_DAY, event.getWhen().start(), false),
            TimeRange.fromStartEnd(event.getWhen().end(), TimeRange.END_OF_DAY, true));
    }

    //fourth, fifth, sixth, seventh (automatically covers double booking) tests
    for (Event event1 : events){
        
        //ignores people not attending
        for (String attendee : request.getAttendees()){
            if(!event1.getAttendees().contains(attendee)){
                availibility = Arrays.asList(TimeRange.WHOLE_DAY);
            }
        }


        for (Event event2 : events){

            //optional attendee in every attendee considered 
            for (Event event3 : events){//new
                if (event1 != event2 && event2!=event3){//new
                    if (!event1.getWhen().overlaps(event2.getWhen())){
                       if(event2.getWhen().start()-event1.getWhen().end() >= request.getDuration()){//and there is enough time between events
                          temp_availibility = 
                          Arrays.asList(TimeRange.fromStartEnd(TimeRange.START_OF_DAY, event1.getWhen().start(), false),
                            TimeRange.fromStartEnd(event1.getWhen().end(), event2.getWhen().start(), false),
                            TimeRange.fromStartEnd(event2.getWhen().end(), TimeRange.END_OF_DAY, true));
                          if (temp_availibility.overlaps(event3.getWhen()) && event3.getDuration() >=request.getDuration()){
                              availibility = 
                              Arrays.asList(TimeRange.fromStartEnd(event1.getWhen().end(), event2.getWhen().start(), false),
                                TimeRange.fromStartEnd(event2.getWhen().end(), TimeRange.END_OF_DAY, true));
                           }
                        }
                    }
                }

                }//new
            
           
           
            if (event1 != event2){

                //every attendee considered -- fourth test
                if (!event1.getWhen().overlaps(event2.getWhen())){

                    //just enough room
                    for (String attendee : request.getAttendees()){ //for each attendee
                        if (event1.getAttendees().contains(attendee) && event2.getAttendees().contains(attendee)){ //if the attendee attends both events
                            if(event2.getWhen().start()-event1.getWhen().end() >= request.getDuration()){//and there is enough time between events
                                availibility =
                                Arrays.asList(TimeRange.fromStartDuration(event1.getWhen().end(), (int)request.getDuration()));//add this time to availibility
                                return availibility;
                            }
                            if(event2.getWhen().start()-event1.getWhen().end() < request.getDuration()){
                                availibility = Arrays.asList();
                                return availibility;
                            }
                        }
                    }
                    
                     availibility =
                     Arrays.asList(TimeRange.fromStartEnd(TimeRange.START_OF_DAY, event1.getWhen().start(), false),
                        TimeRange.fromStartEnd(event1.getWhen().end(), event2.getWhen().start(), false),
                        TimeRange.fromStartEnd(event2.getWhen().end(), TimeRange.END_OF_DAY, true));
                     return availibility;
                }


                //overlapping events -- fifth test
                else if (event1.getWhen().overlaps(event2.getWhen())){
                    if (event1.getWhen().contains(event2.getWhen())){
                        availibility =
                        Arrays.asList(TimeRange.fromStartEnd(TimeRange.START_OF_DAY, event1.getWhen().start(), false),
                            TimeRange.fromStartEnd(event1.getWhen().end(), TimeRange.END_OF_DAY, true));
                        return availibility;
                    }

                    //nested events -- sixth test
                    else{
                        availibility =
                        Arrays.asList(TimeRange.fromStartEnd(TimeRange.START_OF_DAY, event1.getWhen().start(), false),
                            TimeRange.fromStartEnd(event2.getWhen().end(), TimeRange.END_OF_DAY, true));
                        return availibility;
                    }
                }
            }
        }
    }
         

    



    //return the collection
    return availibility;
  }
}
