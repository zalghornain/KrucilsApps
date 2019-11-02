package com.example.krucils;

import com.google.type.Date;

public class GroupChat {
        private String name;
        private String message;
        private String uid;
        private Date mTimestamp;
        private String email;

        public GroupChat() { } // Needed for Firebase

        public GroupChat(String name, String message, String uid, String email) {
            this.name = name;
            this.message = message;
            this.uid = uid;
            this.email = email;
            //mTimestamp = timestamp;
        }

        public String getName() { return name; }

        public void setName(String name) { this.name = name; }

        public String getMessage() { return message; }

        public void setMessage(String message) { this.message = message; }

        public String getUid() { return uid; }

        public void setUid(String uid) { this.uid = uid; }

       // public Date getTimestamp() { return mTimestamp; }

       // public void setTimestamp(Date timestamp) { mTimestamp = timestamp; }
        public String getEmail(){ return email; }

        public void setEmail(String email) { this.email = email;}
}
