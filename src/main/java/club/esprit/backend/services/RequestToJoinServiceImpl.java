package club.esprit.backend.services;

import club.esprit.backend.entities.Club;
import club.esprit.backend.entities.RequestToJoin;
import club.esprit.backend.repository.ClubRepository;
import club.esprit.backend.repository.RequestToJoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestToJoinServiceImpl implements RequestToJoinService {

    @Autowired
    private RequestToJoinRepository requestToJoinRepository;
    @Autowired
    private ClubRepository clubrespository;
    @Override
    public RequestToJoin saveRequestToJoin(RequestToJoin requestToJoin) {
        return requestToJoinRepository.save(requestToJoin);
    }

    @Override
    public Optional<RequestToJoin> getRequestToJoinById(Long id) {
        return requestToJoinRepository.findById(id);
    }

    @Override
    public List<RequestToJoin> getAllRequestsToJoin() {
        return requestToJoinRepository.findAll();
    }

    @Override
    public List<RequestToJoin> getRequestsToJoinByUserId(Long userId) {
        return requestToJoinRepository.findByClub_Id(userId);
    }

    @Override
    public List<RequestToJoin> getRequestsToJoinByClubId(Long clubId) {
       List<RequestToJoin> requests = requestToJoinRepository.findByClub_Id(clubId);
       for (RequestToJoin request : requests) {
           request.setUserName(request.getUser().getName());
              request.setUserEmail(request.getUser().getEmail());
         request.setUserid(request.getUser().getId());
       }



        return requests;
    }

    @Override
    public void deleteRequestToJoin(Long id) {
        requestToJoinRepository.deleteById(id);
    }

    @Override
    public RequestToJoin updateRequestToJoin(RequestToJoin newRequestToJoin) {
        if (newRequestToJoin.getId() == null) {
            return requestToJoinRepository.save(newRequestToJoin);
        }

        RequestToJoin existingRequestToJoin = null;
        try {
            existingRequestToJoin = requestToJoinRepository.findById(newRequestToJoin.getId())
                    .orElseThrow(() -> new RuntimeException("RequestToJoin not found"));
        } catch (RuntimeException e) {
            // Handle exception here, for example, log the error and return null
            System.err.println("Error: " + e.getMessage());
            return null;
        }

        if (newRequestToJoin.getUser() != null) {
            existingRequestToJoin.setUser(newRequestToJoin.getUser());
        }
        if (newRequestToJoin.getClub() != null) {
            existingRequestToJoin.setClub(newRequestToJoin.getClub());
        }
        if (newRequestToJoin.getStatus() != null) {
            existingRequestToJoin.setStatus(newRequestToJoin.getStatus());
        }

        return requestToJoinRepository.save(existingRequestToJoin);
    }


}