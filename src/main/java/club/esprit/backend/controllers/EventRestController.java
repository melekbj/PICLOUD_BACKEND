package club.esprit.backend.controllers;

import club.esprit.backend.entities.Event;
import club.esprit.backend.services.events.IEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/event")
public class EventRestController {
    @Autowired
    private IEvent iEvent;
    @PostMapping("/addEvent")
    public Event addEvent(@RequestBody Event e){
        return iEvent.addEvent(e);
    }
    @DeleteMapping("/deleteEvent/{id}")
    public void deleteEvent(@PathVariable Long id){

        iEvent.deleteEvent(id);
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
