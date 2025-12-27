package projects.koko.tinder_backend.conversations;

import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

@Service
public class ConversationService {

    private OllamaChatModel ollamaChatModel;

    public ConversationService(OllamaChatModel ollamaChatModel){
        this.ollamaChatModel = ollamaChatModel;
    }
}
