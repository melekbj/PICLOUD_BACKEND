package club.esprit.backend.services.chat;

import club.esprit.backend.entities.User;
import club.esprit.backend.entities.chat.ChatEntity;
import club.esprit.backend.repository.UserRepository;
import club.esprit.backend.repository.chat.ChatRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ChatImpl implements IChat {

    private ChatRepository chatRepository;

    private UserRepository userRepository;


    @Override
    public ChatEntity addChat(ChatEntity chat) {
        return chatRepository.save(chat);
    }

    @Override
    public ChatEntity retrieveById(long id) {
        return chatRepository.findById(id).get();
    }

    @Override
    public ChatEntity updateChat(ChatEntity chat) {
        return chatRepository.save(chat);
    }

    @Override
    public List<ChatEntity> retrieveAll() {
        return chatRepository.findAll();
    }

    @Override
    public void deleteById(long id) {
        chatRepository.deleteById(id);
    }

//    @Override
//    public List<ChatEntity> findByPartecipantLogin(String login) {
//        return chatRepository.findByPartecipantEmail(login);
//    }

    @Override
    public ChatEntity findByName(String name) {
        return chatRepository.findByName(name);
    }

    @Override
    public Optional<User> findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
