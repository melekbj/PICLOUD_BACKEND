package club.esprit.backend.services;

import club.esprit.backend.entities.Club;
import club.esprit.backend.repository.ClubRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClubServiceImpl implements IClubService {

    @Autowired
    private ClubRepository clubRepository;

    @Override
    public Club addClub(Club club) {


        return clubRepository.save(club);
    }

    @Override
    public Club updateClub(Club club) {
        return clubRepository.save(club);
    }

    @Override

    public void deleteClub(Long id) {
        clubRepository.deleteById(id);
    }

    @Override
    public Club getClub(Long id) {
        return clubRepository.findById(id).orElse(null);
    }

    @Override
    public List<Club> getAllClubs() {
        return clubRepository.findAll();
    }

    @Override
    public List<Club> getClubByUser(Long id) {return clubRepository.findClubsByUserId(id);}
}
