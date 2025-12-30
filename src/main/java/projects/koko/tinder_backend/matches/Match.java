package projects.koko.tinder_backend.matches;

import projects.koko.tinder_backend.profiles.Profile;

public record Match (String id, Profile profile, String conversationId){ }
