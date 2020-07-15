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
    Collection<TimeRange> availibility;
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {

    //no attendees -- first test
    if (request.getAttendees() == null){
        availibility = Arrays.asList(TimeRange.WHOLE_DAY);
    }

    //no options too long of a request (how do I access time range here? THis is an error) -- second test
    else if (request.getDuration() > TimeRange.WHOLE_DAY.duration()){
        availibility = Arrays.asList();
    }

    //event splits day into two options (before and after) -- third test
    for (Event event : events){
        availibility =
        Arrays.asList(TimeRange.fromStartEnd(TimeRange.START_OF_DAY, event.getWhen()[0], false),
            TimeRange.fromStartEnd(event.getWhen()[1], TimeRange.END_OF_DAY, true));
        
    }

    //return the collection
    return availibility;
  }
}
