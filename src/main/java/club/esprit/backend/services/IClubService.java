package club.esprit.backend.services;

import club.esprit.backend.entities.Club;


import java.util.List;

public interface IClubService {
    Club addClub(Club club);
    Club updateClub(Club club);
    void deleteClub(Long id);
    Club getClub(Long id);
    List<Club> getAllClubs();

    List<Club> getClubByUser(Long id);

}