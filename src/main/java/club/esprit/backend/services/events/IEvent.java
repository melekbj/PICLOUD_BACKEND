package club.esprit.backend.services.events;

import club.esprit.backend.entities.Event;

import java.util.List;

public interface IEvent {
    public Event addEvent(Event e);
    public List<Event> retrieveAllEvents();
    public void deleteEvent(Long id);
    public Event updateEvent(Event e);
}
