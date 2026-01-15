import { User, MessageCircle, Home, X, Heart, Send } from "lucide-react";
import React, { useState, useEffect } from "react";
import "./App.css";

const fetchRandomProfile = async () => {
  const response = await fetch("http://localhost:8080/api/profiles/random");
  if (!response.ok) {
    throw new Error("Unable to fetch profile");
  }
  return response.json();
};
const fetchUserProfile = async () => {
  const res = await fetch("http://localhost:8080/api/profiles/user");
  if (!res.ok) {
    throw new Error("Unable to fetch user profile");
  }
  return res.json();
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

const UserProfile = ({ userProfile }) => (
  <div>
    <div className="px-4 sm:px-0">
      <h3 className="text-base/7 font-semibold text-gray-900">Profile</h3>
    </div>
    <div className="mt-6 border-t border-gray-100">
      <dl className="divide-y divide-gray-100">
        <div className="px-4 py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
          <dt className="text-sm/6 font-medium text-gray-900">Full name</dt>
          <dd className="mt-1 text-sm/6 text-gray-700 sm:col-span-2 sm:mt-0">
            {userProfile.firstName} {userProfile.lastName}
          </dd>
        </div>
        <div className="px-4 py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
          <dt className="text-sm/6 font-medium text-gray-900">
            Application for
          </dt>
          <dd className="mt-1 text-sm/6 text-gray-700 sm:col-span-2 sm:mt-0">
            Backend Developer
          </dd>
        </div>
        <div className="px-4 py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
          <dt className="text-sm/6 font-medium text-gray-900">Email address</dt>
          <dd className="mt-1 text-sm/6 text-gray-700 sm:col-span-2 sm:mt-0">
            margotfoster@example.com
          </dd>
        </div>
        <div className="px-4 py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
          <dt className="text-sm/6 font-medium text-gray-900">
            Salary expectation
          </dt>
          <dd className="mt-1 text-sm/6 text-gray-700 sm:col-span-2 sm:mt-0">
            $120,000
          </dd>
        </div>
        <div className="px-4 py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
          <dt className="text-sm/6 font-medium text-gray-900">About</dt>
          <dd className="mt-1 text-sm/6 text-gray-700 sm:col-span-2 sm:mt-0">
            Fugiat ipsum ipsum deserunt culpa aute sint do nostrud anim
            incididunt cillum culpa consequat. Excepteur qui ipsum aliquip
            consequat sint. Sit id mollit nulla mollit nostrud in ea officia
            proident. Irure nostrud pariatur mollit ad adipisicing reprehenderit
            deserunt qui eu.
          </dd>
        </div>
        <div className="px-4 py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
          <dt className="text-sm/6 font-medium text-gray-900">Attachments</dt>
          <dd className="mt-2 text-sm text-gray-900 sm:col-span-2 sm:mt-0">
            <ul
              role="list"
              className="divide-y divide-gray-100 rounded-md border border-gray-200"
            >
              <li className="flex items-center justify-between py-4 pr-5 pl-4 text-sm/6">
                <div className="flex w-0 flex-1 items-center">
                  <div className="ml-4 flex min-w-0 flex-1 gap-2">
                    <span className="truncate font-medium text-gray-900">
                      resume_back_end_developer.pdf
                    </span>
                    <span className="shrink-0 text-gray-400">2.4mb</span>
                  </div>
                </div>
                <div className="ml-4 shrink-0">
                  <a
                    href="#"
                    className="font-medium text-indigo-600 hover:text-indigo-500"
                  >
                    Download
                  </a>
                </div>
              </li>
              <li className="flex items-center justify-between py-4 pr-5 pl-4 text-sm/6">
                <div className="flex w-0 flex-1 items-center">
                  <div className="ml-4 flex min-w-0 flex-1 gap-2">
                    <span className="truncate font-medium text-gray-900">
                      coverletter_back_end_developer.pdf
                    </span>
                    <span className="shrink-0 text-gray-400">4.5mb</span>
                  </div>
                </div>
                <div className="ml-4 shrink-0">
                  <a
                    href="#"
                    className="font-medium text-indigo-600 hover:text-indigo-500"
                  >
                    Download
                  </a>
                </div>
              </li>
            </ul>
          </dd>
        </div>
      </dl>
    </div>
  </div>
);

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

  const handleKeyPress = (e) => {
    if (e.key === "Enter" && !e.shiftKey) {
      e.preventDefault();
      handleSend(input, conversation);
    }
  };

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
        Chat with {currentMatch.firstName} {currentMatch.lastName}{" "}
      </h2>
      <div className="h-[50vh] border rounded-lg overflow-y-auto mb-6 p-4 bg-gray-50">
        {conversation.messages.map((message, index) => (
          <div
            key={index}
            className={`flex ${
              message.authorId === "user" ? "justify-end" : "justify-start"
            } mb-4`}
          >
            <div
              className={`flex items-end ${
                message.authorId === "user" ? "flex-row-reverse" : "flex-row"
              }`}
            >
              {message.authorId === "user" ? (
                <User size={15} />
              ) : (
                <img
                  src={`http://localhost:8080/api/images/${currentMatch.imageUrl}`}
                  className="w-11 h-11 rounded-full"
                />
              )}
              <div
                className={`max-w-xs px-4 py-2 rounded-2xl ${
                  message.authorId === "user"
                    ? "bg-blue-500 text-white ml-2"
                    : "bg-gray-200 text-gray-800 mr-2"
                }`}
              >
                {message.messageText}
              </div>
            </div>
          </div>
        ))}
      </div>
      <div className="flex items-center">
        <input
          type="text"
          value={input}
          onChange={(e) => setInput(e.target.value)}
          onKeyDown={handleKeyPress}
          className="flex-1 border-2 border-gray-300 rounded-full py-2 px-4 mr-2 focus:outline-none focus:border-blue-500"
          placeholder="Type a message..."
        />
        <button
          className="bg-blue-500 text-white rounded-full p-2 hover:bg-blue-600 transition-colors duration-200"
          onClick={() => handleSend(input, conversation)}
        >
          <Send size={24} />
        </button>
      </div>
    </div>
  ) : (
    <div>Loading...</div>
  );
};
function App() {
  const loadUserProfile = async () => {
    try {
      const userProfile = await fetchUserProfile();
      setUserProfile(userProfile);
    } catch (error) {
      console.error(error);
    }
  };
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
    loadUserProfile();
  }, []);

  const [currentScreen, setCurrentScreen] = useState("profile");
  const [currentProfile, setCurrentProfile] = useState(null);
  const [userProfile, setUserProfile] = useState(null);
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
      case "userProfile":
        return <UserProfile userProfile={userProfile} />;
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
          <Home
            onClick={() => {
              setCurrentScreen("userProfile");
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
