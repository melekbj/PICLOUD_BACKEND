package club.esprit.backend.controllers;

import club.esprit.backend.entities.Event;
import club.esprit.backend.entities.User;
import club.esprit.backend.repository.UserRepository;
import club.esprit.backend.services.AuthService;
import club.esprit.backend.services.events.IEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/event")
public class EventRestController {
    @Autowired
    private IEvent iEvent;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/addEvent")
    public ResponseEntity<Event> addEvent(@RequestBody Event e){
        Event savedEvent = iEvent.addEvent(e);
        return new ResponseEntity<>(savedEvent, HttpStatus.CREATED);
    }
    @GetMapping("/findByEmail/{email}")
    public Optional<User> getUserByEmail(@PathVariable String email){
        return iEvent.findUserByEmail(email) ;
    }




    // @PostMapping("/addEvent")
    //public Event addEvent(@RequestBody Event e){
       // return iEvent.addEvent(e);
  //  }
    @DeleteMapping("/deleteEvent/{id}")
    public void deleteEvent(@PathVariable Long id){

        iEvent.deleteEvent(id);
    }
    @GetMapping("/getEvent/{id}")
    public Event getEvent(@PathVariable Long id){
        return iEvent.getEvent(id);
    }
    @PutMapping("/updateEvent")
    public Event updateEvent(@RequestBody Event e){

        return iEvent.updateEvent(e);
    }
    @GetMapping("/getAllEvents")
    public List<Event> retrieveAllEvents(){
        return iEvent.retrieveAllEvents();
    }

}
