package com.gohaibo.gohaibo.service;

import com.gohaibo.gohaibo.entity.Invitation;

public interface InvitationService {
    public void sendInvitation(String email, Long projectID);
    public Invitation acceptInvitation(String token, Long userID) throws Exception;
    public String getTokenByUserMail(String email);
    public void deleteToken(String token) throws Exception;
}
