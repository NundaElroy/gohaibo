package com.gohaibo.gohaibo.repo;

import com.gohaibo.gohaibo.entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepo extends JpaRepository<Invitation,Long> {
    Invitation findByToken(String token);
    Invitation findByEmail(String email);

}
