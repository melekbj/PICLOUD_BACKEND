package club.esprit.backend.services.chat;

import club.esprit.backend.entities.chat.MessageEntity;
import club.esprit.backend.repository.chat.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MessageImpl implements IMessage{

    private MessageRepository messageRepository;
    @Override
    public MessageEntity addMessage(MessageEntity message) {
        return messageRepository.save(message);
    }

    @Override
    public MessageEntity retrieveById(long id) {
        return messageRepository.findById(id).get();
    }

    @Override
    public MessageEntity updateMessage(MessageEntity message) {
        return messageRepository.save(message);
    }

    @Override
    public List<MessageEntity> retrieveAll() {
        return messageRepository.findAll();
    }

    @Override
    public void deleteById(long id) {
        messageRepository.deleteById(id);
    }

    @Override
    public List<MessageEntity> findAllByChat(long chat_id) {
        return messageRepository.findByChatId(chat_id);
    }
}
