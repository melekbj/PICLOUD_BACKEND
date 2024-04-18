package club.esprit.backend.services;

import club.esprit.backend.entities.Club;
import club.esprit.backend.entities.Membership;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface MembershipService {
    Membership addMember(Membership membership);
    Membership updateMember(Membership membership);
    void deleteMember(Long memberId);
    Membership getMemberById(Long memberId);
    List<Membership> getAllMembers();
     List<Membership> getMembershipByclub(Long id );
    List<Membership> getMembershipByDepartment(Long id );
    List<Membership> assignUsersToDepartment(List<Long> userIds, Long departmentId,Long clubId) throws ChangeSetPersister.NotFoundException;

}
