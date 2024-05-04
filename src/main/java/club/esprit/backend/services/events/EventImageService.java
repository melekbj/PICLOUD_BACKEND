package club.esprit.backend.services.events;


import club.esprit.backend.entities.EventImage;
import club.esprit.backend.repository.EventImageRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EventImageService {

    @Autowired
    EventImageRepository eventImageRepository;

    public List<EventImage> list() {
        return eventImageRepository.findByOrderById();
    }

    public Optional<EventImage> getOne(int id) {
        return eventImageRepository.findById(id);
    }
    public void save(EventImage eventImage) {
        eventImageRepository.save(eventImage);
    }
    public void delete(int id) {
        eventImageRepository.deleteById(id);
    }
    public boolean exists(int id) {
        return eventImageRepository.existsById(id);
    }
    public String getImageUrlById(int id) {
        Optional<EventImage> eventImageOptional = eventImageRepository.findById(id);
        if (eventImageOptional.isPresent()) {
            return eventImageOptional.get().getImageUrl();
        }
        return null;
    }
}

