package com.driver;

import java.util.ArrayList;
import java.util.Iterator;

public class Workspace extends Gmail{

    private final ArrayList<Meeting> calendar = new ArrayList<>(); // Stores all the meetings

    public Workspace(String emailId) {
        // The inboxCapacity is equal to the maximum value an integer can store.
        super(emailId,Integer.MAX_VALUE);
    }

    public void addMeeting(Meeting meeting){
        //add the meeting to calendar
        this.calendar.add(meeting);

    }

    public int findMaxMeetings(){
        // find the maximum number of meetings you can attend
        // 1. At a particular time, you can be present in at most one meeting
        // 2. If you want to attend a meeting, you must join it at its start time and leave at end time.
        // Example: If a meeting ends at 10:00 am, you cannot attend another meeting starting at 10:00 am
        int maxMeetings = 0;

        int overlappingMeetings;
        for(Iterator<Meeting> var2 = this.calendar.iterator(); var2.hasNext(); maxMeetings = Math.max(maxMeetings, overlappingMeetings)) {
            Meeting currentMeeting = var2.next();
            overlappingMeetings = 1;

            for (Meeting otherMeeting : this.calendar) {
                if (currentMeeting != otherMeeting && currentMeeting.getEndTime().isAfter(otherMeeting.getStartTime()) && currentMeeting.getStartTime().isBefore(otherMeeting.getEndTime())) {
                    ++overlappingMeetings;
                }
            }
        }

        return maxMeetings;
    }
}
