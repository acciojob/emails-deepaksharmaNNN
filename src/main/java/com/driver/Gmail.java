package com.driver;

import java.util.*;

public class Gmail extends Email {

    final int inboxCapacity; //maximum number of mails inbox can store
    //Inbox: Stores mails. Each mail has date (Date), sender (String), message (String). It is guaranteed that message is distinct for all mails.
    //Trash: Stores mails. Each mail has date (Date), sender (String), message (String)
    private static class Mail{
        Date date;
        String senderId;
        String message;
        public Mail(Date date, String senderId, String message){
            this.date = date;
            this.senderId = senderId;
            this.message = message;
        }
    }
    private final Deque<Mail> inbox;
    private final Deque<Mail> trash;
    public Gmail(String emailId, int inboxCapacity) {
        super(emailId);
        this.inboxCapacity = inboxCapacity;
        this.inbox = new LinkedList<>();
        this.trash = new LinkedList<>();
    }

    public void receiveMail(Date date, String sender, String message){
        // If the inbox is full, move the oldest mail in the inbox to trash and add the new mail to inbox.
        // It is guaranteed that:
        // 1. Each mail in the inbox is distinct.
        // 2. The mails are received in non-decreasing order. This means that the date of a new mail is greater than equal to the dates of mails received already.
        if (this.inbox.size() == this.inboxCapacity) {
            this.trash.add(this.inbox.poll());
        }

        this.inbox.add(new Mail(date, sender, message));
    }

    public void deleteMail(String message){
        // Each message is distinct
        // If the given message is found in any mail in the inbox, move the mail to trash, else do nothing
        Iterator<Mail> iterator = this.inbox.iterator();

        while(iterator.hasNext()) {
            Mail mail = iterator.next();
            if (mail.message.equals(message)) {
                this.trash.add(mail);
                iterator.remove();
                break;
            }
        }
    }

    public String findLatestMessage(){
        // If the inbox is empty, return null
        // Else, return the message of the latest mail present in the inbox
        return this.inbox.isEmpty() ? null : this.inbox.peek().message;
    }

    public String findOldestMessage(){
        // If the inbox is empty, return null
        // Else, return the message of the oldest mail present in the inbox
        return this.inbox.isEmpty() ? null : ((Mail)((LinkedList<?>)this.inbox).getLast()).message;

    }

    public int findMailsBetweenDates(Date start, Date end){
        //find number of mails in the inbox which are received between given dates
        //It is guaranteed that start date <= end date
        int count = 0;

        for (Mail mail : this.inbox) {
            if (this.isDateBetween(mail.date, start, end)) {
                ++count;
            }
        }
        return count;
    }
    private boolean isDateBetween(Date date, Date start, Date end){
        return date.compareTo(start) >= 0 && date.compareTo(end) <= 0;
    }

    public int getInboxSize(){
        // Return number of mails in inbox
        return this.inbox.size();

    }

    public int getTrashSize(){
        // Return number of mails in Trash
        return this.trash.size();

    }

    public void emptyTrash(){
        // clear all mails in the trash
        this.trash.clear();

    }

    public int getInboxCapacity() {
        // Return the maximum number of mails that can be stored in the inbox
        return this.inboxCapacity;
    }
}
