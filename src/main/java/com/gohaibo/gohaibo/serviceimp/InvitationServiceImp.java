package com.gohaibo.gohaibo.serviceimp;

import com.gohaibo.gohaibo.entity.Invitation;
import com.gohaibo.gohaibo.repo.InvitationRepo;
import com.gohaibo.gohaibo.service.EmailService;
import com.gohaibo.gohaibo.service.InvitationService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvitationServiceImp implements InvitationService {

    private final EmailService emailService;
    private final InvitationRepo invitationRepo;

    public InvitationServiceImp(EmailService emailService, InvitationRepo invitationRepo) {
        this.emailService = emailService;

        this.invitationRepo = invitationRepo;
    }

    @Override
    public void sendInvitation(String email, Long projectID) {
        String invitationToken = UUID.randomUUID().toString();

        Invitation invitation = new Invitation();
        invitation.setEmail(email);
        invitation.setToken(invitationToken);
        invitation.setProjectID(projectID);

        invitationRepo.save(invitation);

        //Todo: to  be implemented from the frontend
        String invitationLink = "http://localhost:8080/invitation/accept?token=" + invitationToken;

        emailService.sendEmailWithToken(email, invitationLink);
    }

    @Override
    public Invitation acceptInvitation(String token, Long userID) throws Exception {

        Invitation invitation = invitationRepo.findByToken(token);

        if (invitation == null) {
             throw  new Exception("Invalid invitation token");
        }

        return invitation;

    }

    @Override
    public String getTokenByUserMail(String email) {
        Invitation invitation = invitationRepo.findByEmail(email);
        return invitation.getToken();
    }

    @Override
    public void deleteToken(String token) throws Exception {
        Invitation invitation = invitationRepo.findByToken(token);
        if (invitation == null) {
            throw  new Exception("Invalid invitation token");
        }
        invitationRepo.delete(invitation);
    }
}
