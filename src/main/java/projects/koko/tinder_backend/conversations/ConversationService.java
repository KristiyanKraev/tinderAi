package projects.koko.tinder_backend.conversations;

import org.springframework.ai.chat.messages.*;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projects.koko.tinder_backend.profiles.Profile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConversationService {

    @Autowired
    private OllamaChatModel chatClient;

    public Conversation generateProfileResponse(Conversation conversation, Profile profile, Profile user) {
        String systemMessageStr = """
                You are a %d year old %s %s called %s %s
                matched with a %d year old %s %s called %s %s on Tinder.
                This is an in-app text conversation between you two.
                Pretend to be the provided person and respond to the conversation as if writing on Tinder.
                Your bio is %s and your Myers Briggs personality is %s.
                Respond in the role of this person only.
                Only respond to the user's text. Do not use hashtags. Keep the responses brief.
                """.formatted(profile.age(), profile.ethnicity(), profile.gender(), profile.firstName(), profile.lastName(),
                user.age(), user.ethnicity(), user.gender(), user.firstName(), user.lastName(),
                profile.bio(), profile.myersBriggsPersonalityType());
        SystemMessage systemMessage = new SystemMessage(systemMessageStr);

        List<? extends AbstractMessage> conversationMessages = conversation.messages().stream().map(message -> {
            if (message.authorId().equals(profile.id())) {
                return new AssistantMessage(message.messageText());
            } else {
                return new UserMessage(message.messageText());
            }
        }).toList();

        List<Message> allMessages = new ArrayList<>();
        allMessages.add(systemMessage);
        allMessages.addAll(conversationMessages);
        Prompt prompt = new Prompt(allMessages);
        ChatResponse response = chatClient.call(prompt);

        conversation.messages().add(new ChatMessage(
                response.getResult().getOutput().getText(),
                profile.id(),
                LocalDateTime.now()
        ));
        System.out.println("This is the response from Ollama: " + response.getResult().getOutput().getText());
        return conversation;

    }
}
