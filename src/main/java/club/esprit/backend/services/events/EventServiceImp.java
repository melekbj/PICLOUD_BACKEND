package club.esprit.backend.services.events;

import club.esprit.backend.entities.Event;
import club.esprit.backend.entities.Participant;
import club.esprit.backend.repository.EventRepository;
import club.esprit.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EventServiceImp  implements IEvent{
    @Autowired
    private EventRepository eventRepository;
    @Override
    public Event addEvent(Event e) {

        return eventRepository.save(e);
    }

    @Override
    public List<Event> retrieveAllEvents() {

        return eventRepository.findAll();
    }

    @Override
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);

    }

    @Override
    public Event updateEvent(Event e) {

        return eventRepository.save(e);
    }

}
