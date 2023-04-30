package com.employeedirectory.rest.email;


public class EmailRequest {
	
    private String fullName;
    private String subject;
    private String email;
    private String content;


    public EmailRequest(String fullName, String subject, String email, String content) {
        this.fullName = fullName;
        this.subject = subject;
        this.email = email;
        this.content = content;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
