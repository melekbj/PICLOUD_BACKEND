package club.esprit.backend.controllers;

import club.esprit.backend.entities.Club;
import club.esprit.backend.entities.User;
import club.esprit.backend.services.IClubService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/club")
public class ClubRestController {
    private IClubService clubService;

    @Autowired
    public ClubRestController(IClubService clubService) {
        this.clubService = clubService;
    }
    /*@PostMapping("/add/file")
    public ResponseEntity<Club> addClubfile(
                                        @RequestPart("file") MultipartFile file) {

        try {
            if (file != null) {
                System.out.println(file.getOriginalFilename());
               // club.setLogo("http://localhost:8080/img/"+file.getOriginalFilename()+ club.getId());
                //file.transferTo(new File("C:/Users/abdes/OneDrive - ESPRIT/Bureau/selfworkpicloud/SPRING_2/src/main/resources/static/img"));
                file.transferTo(new File("C:/xampp/htdocs/img"));




                //Club savedClub = clubService.addClub(club);
                return ResponseEntity.status(HttpStatus.CREATED).body(new Club());
            } else {

                System.out.println("ok");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }*/
    @PostMapping("/add")
    public ResponseEntity<Club> addClub(@RequestBody Club club) {


                Club savedClub = clubService.addClub(club);
                return ResponseEntity.status(HttpStatus.CREATED).body(savedClub);


    }

    @PutMapping("/update/{clubId}")
    public ResponseEntity<Club> updateClub(@PathVariable("clubId") Long clubId, @RequestBody Club club) {
        club.setId(clubId);
        Club updatedClub = clubService.updateClub(club);
        if (updatedClub != null) {
            return ResponseEntity.ok(updatedClub);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteClub(@PathVariable Long id) {
        clubService.deleteClub(id);
    }

    @GetMapping("/get/{id}")
    public Club getClub(@PathVariable Long id) {
        return clubService.getClub(id);
    }

    @GetMapping("/clubs")
    public List<Club> getAllClubs() {
        return clubService.getAllClubs();
    }
    @GetMapping("/getbyuser/{id}")
    public List<Club> getAllClubs(@PathVariable Long id) {
        return clubService.getClubByUser(id);
    }

}