import { User, MessageCircle, X, Heart } from "lucide-react";
import React, { useState, useEffect } from "react";
import "./App.css";

const fetchRandomProfile = async () => {
  const response = await fetch("http://localhost:8080/api/profiles/random");
  if (!response.ok) {
    throw new Error("Unable to fetch profile");
  }
  return response.json();
};

const saveSwipe = async (profileId) => {
  const response = await fetch("http://localhost:8080/api/matches", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ profileId }),
  });
  if (!response.ok) {
    throw new Error("Failed to save swipe");
  }
};
const fetchMatches = async () => {
  const response = await fetch("http://localhost:8080/api/matches");
  if (!response.ok) {
    throw new Error("Failed to save swipe");
  }
  return response.json();
};

const fetchConversation = async (conversationId) => {
  console.log(`fetching conversation ${conversationId}`);
  const response = await fetch(
    `http://localhost:8080/api/conversations/${conversationId}`
  );
  if (!response.ok) {
    throw new Error("Failed to fetch conversation");
  }
  return response.json();
};

const sendMessage = async (conversationId, message) => {
  const response = await fetch(
    `http://localhost:8080/api/conversations/${conversationId}`,
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ messageText: message, authorId: "user" }),
    }
  );
  if (!response.ok) {
    throw new Error("Failed to send message");
  }
  return response.json();
};

const ProfileSelector = ({ profile, onSwipe }) =>
  profile ? (
    <div className="rounded-lg overflow-hidden bg-white shadow-lg">
      <div className="relative">
        <img src={"http://localhost:8080/api/images/" + profile.imageUrl} />
        <div className="absolute bottom-0 left-0 right-0 text-white p-4 bg-gradient-to-t from-black  ">
          <h2 className="text-3x1 font-bold">
            {profile.firstName} {profile.lastName} {profile.age}
          </h2>
        </div>
      </div>
      <div className="p-4">
        <p>{profile.bio}</p>
      </div>

      <div className="p-4 flex justify-center space-x-4">
        <button
          onClick={() => onSwipe(profile.id, "left")}
          className="bg-red-500 rounded-full p-4 text-white hover:bg-red-700"
        >
          <X size={24} />
        </button>
        <button
          onClick={() => onSwipe(profile.id, "right")}
          className="bg-green-500 rounded-full p-4 text-white hover:bg-green-700"
        >
          <Heart size={24} />
        </button>
      </div>
    </div>
  ) : (
    <div>Loading profile</div>
  );

const MatchesList = ({ matches, onSelectMatch }) => {
  return (
    <div className="rounded-lg shadow-lg p-4">
      <h2 className="text-2xl font-bold mb-4">Matches</h2>
      <ul>
        {matches.map((match, index) => (
          <li key={index} className="mb-2">
            <button className="w-full hover:bg-gray-100 rounded flex item-center">
              <img
                src={
                  "http://localhost:8080/api/images/" + match.profile.imageUrl
                }
                alt={`${match.profile.firstName} ${match.profile.lastName}`}
                className="w-16 h-16 rounded-full mr-3 object-cover"
                onClick={() =>
                  onSelectMatch(match.profile, match.conversationId)
                }
              />

              <span>
                <h3 className="font-bold">
                  {match.profile.firstName} {match.profile.lastName}
                </h3>
              </span>
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
};

const ChatScreen = ({ currentMatch, conversation, refreshState }) => {
  const [input, setInput] = useState("");

  const handleSend = async (input, conversation) => {
    if (input.trim()) {
      await sendMessage(conversation.id, input);
      setInput("");
    }
    refreshState();
  };

  return currentMatch ? (
    <div className="rounded-lg shadow-lg p-4">
      <h2 className="text-2xl font-bold mb-4">
        Chat with {currentMatch.firstName} {currentMatch.lastName}
      </h2>
      <div className="h-[50vh] border rounded overflow-y-auto mb-4 p-2">
        {conversation.messages.map((message, index) => (
          <div key={index}>
            <div className="mb-4 p-2 rounded bg-gray-100">
              {message.messageText}
            </div>
          </div>
        ))}
      </div>
      <div className="flex">
        <input
          type="text"
          value={input}
          onChange={(e) => setInput(e.target.value)}
          className="border flex-1 rounded p-2 mr-2"
          placeholder="Type a message..."
        />
        <button
          className="bg-blue-500 text-white rounded p-2"
          onClick={() => handleSend(input, conversation)}
        >
          Send
        </button>
      </div>
    </div>
  ) : (
    <div>Loading chat...</div>
  );
};
function App() {
  const loadRandomProfile = async () => {
    try {
      const profile = await fetchRandomProfile();
      setCurrentProfile(profile);
    } catch (error) {
      console.error(error);
    }
  };

  const loadMatches = async () => {
    try {
      const matches = await fetchMatches();
      setMatches(matches);
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    loadRandomProfile();
    loadMatches();
  }, {});

  const [currentScreen, setCurrentScreen] = useState("profile");
  const [currentProfile, setCurrentProfile] = useState(null);
  const [matches, setMatches] = useState([]);
  const [currentMatchAndConversation, setCurrentMatchAndConversation] =
    useState({
      match: {},
      conversation: [],
    });

  const onSwipe = async (profileId, direction) => {
    if (direction === "right") {
      await saveSwipe(profileId);
      await loadMatches();
    }
    loadRandomProfile();
  };

  const onSelectMatch = async (profile, conversationId) => {
    const conversation = await fetchConversation(conversationId);
    setCurrentMatchAndConversation({
      match: profile,
      conversation: conversation,
    });
    setCurrentScreen("chat");
    console.log(profile);
  };

  const refreshChatState = async () => {
    const conversation = await fetchConversation(
      currentMatchAndConversation.conversation.id
    );
    setCurrentMatchAndConversation({
      match: currentMatchAndConversation.match,
      conversation: conversation,
    });
  };

  const renderScreen = () => {
    switch (currentScreen) {
      case "profile":
        return <ProfileSelector profile={currentProfile} onSwipe={onSwipe} />;
      case "matches":
        return <MatchesList matches={matches} onSelectMatch={onSelectMatch} />;
      case "chat":
        return (
          <ChatScreen
            currentMatch={currentMatchAndConversation.match}
            conversation={currentMatchAndConversation.conversation}
            refreshState={refreshChatState}
          />
        );
    }
  };
  return (
    <>
      <div className="max-w-md mx-auto p-4">
        <nav className="flex justify-between mb-4">
          <User
            onClick={() => {
              setCurrentScreen("profile");
            }}
          />
          <MessageCircle
            onClick={() => {
              setCurrentScreen("matches");
            }}
          />
        </nav>
        {renderScreen()}
      </div>
    </>
  );
}

export default App;
