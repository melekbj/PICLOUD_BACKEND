package club.esprit.backend.controllers.chat;

import club.esprit.backend.dto.ChatMessage;
import club.esprit.backend.entities.User;
import club.esprit.backend.entities.chat.ChatEntity;
import club.esprit.backend.entities.chat.MessageEntity;
import club.esprit.backend.services.chat.IChat;
import club.esprit.backend.services.chat.IMessage;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class WebSocketController {
    private IChat chatDAO;
    private IMessage messageDAO;

    @MessageMapping("/chat/{to}")
    @SendTo("/topic/{to}")
    public ChatMessage chat(@DestinationVariable String to, MessageEntity message) {
        if (message.getWhoMakeDelete() != null){
            this.messageDAO.deleteFor(message);
            return new ChatMessage();
        }else if (message.getMs_id() != 0){
            this.messageDAO.msgReact(message);
            return new ChatMessage();
        }

        message.setChatId(createAndOrGetChat(to));
        message.setT_stamp(generateTimeStamp());
        message = messageDAO.addMessage(message);
        return new ChatMessage(message.getContent(), message.getSender());
    }
    //returns an empty list if the chat doesn't exist
    @PostMapping("/getMessages")
    public List<MessageEntity> getMessages(@RequestBody String chat) {
        ChatEntity ce = chatDAO.findByName(chat);

        if(ce != null) {
            return messageDAO.findAllByChat(ce.getChat_id());
        }
        else{
            return new ArrayList<MessageEntity>();
        }
    }

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers() {
        return chatDAO.findAllUsers();
    }
    @GetMapping("/findByEmail/{email}")
    public Optional<User> getUserByEmail(@PathVariable String email){
        return chatDAO.findUserByEmail(email) ;
    }

    @DeleteMapping("/deletemsg/{id}")
    public void deletemsg(@PathVariable int id){
        messageDAO.deleteById(id); ;
    }
    private Long createAndOrGetChat(String name) {
        ChatEntity ce = chatDAO.findByName(name);

        if (ce != null) {
            return ce.getChat_id();
        }
        else {
            ChatEntity newChat = new ChatEntity(name);
            return chatDAO.addChat(newChat).getChat_id();
        }
    }

    private String generateTimeStamp() {
        Instant i = Instant.now();
        String date = i.toString();
        int endRange = date.indexOf('T');
        date = date.substring(0, endRange);
        date = date.replace('-', '/');
        String time = Integer.toString(i.atZone(ZoneOffset.UTC).getHour() + 1);
        time += ":";

        int minutes = i.atZone(ZoneOffset.UTC).getMinute();
        if (minutes > 9) {
            time += Integer.toString(minutes);
        } else {
            time += "0" + Integer.toString(minutes);
        }

        String timeStamp = date + "-" + time;
        return timeStamp;
    }
}
