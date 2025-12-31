import { User, MessageCircle, X, Heart } from "lucide-react";
import React, { useState } from "react";
import "./App.css";

const ProfileSelector = () => {
  return (
    <div className="rounded-lg overflow-hidden bg-white shadow-lg">
      <div className="relative">
        <img src="http://localhost:8080/images/ffa9903a-9001-4c1d-a43c-eeee33433648.jpg" />
        <div className="absolute bottom-0 left-0 right-0 text-white p-4 bg-gradient-to-t from-black  ">
          <h2 className="text-3x1 font-bold">Foo Bar, 30</h2>
        </div>
      </div>
      <div className="p-4">
        <p>
          I am a software engineer looking for a job here, because LinkedIn
          isn't being of any help
        </p>
      </div>

      <div className="p-4 flex justify-center space-x-4">
        <button
          onClick={() => {
            console.log("Swipe left");
          }}
          className="bg-red-500 rounded-full p-4 text-white hover:bg-red-700"
        >
          <X size={24} />
        </button>
        <button
          onClick={() => {
            console.log("Swipe right");
          }}
          className="bg-green-500 rounded-full p-4 text-white hover:bg-green-700"
        >
          <Heart size={24} />
        </button>
      </div>
    </div>
  );
};

const MatchesList = () => (
  <div className="rounded-lg shadow-lg p-4">
    <h2 className="text-2xl font-bold mb-4">Matches</h2>
    <ul>
      {[
        {
          id: 1,
          firstName: "Foo",
          lastName: "Bar",
          imageUrl:
            "http://localhost:8080/images/ffa9903a-9001-4c1d-a43c-eeee33433648.jpg",
        },
        {
          id: 2,
          firstName: "Baz",
          lastName: "Qux",
          imageUrl:
            "http://localhost:8080/images/4c887672-9d40-4bfb-82ab-c049325a02e9.jpg",
        },
      ].map((match) => (
        <li key={match.id} className="mb-2">
          <button className="w-full hover:bg-gray-100 rounded flex item-center">
            <img
              src={match.imageUrl}
              alt={`${match.firstName} ${match.lastName}`}
              className="w-16 h-16 rounded-full mr-3 object-cover"
            />

            <span>
              <h3 className="font-bold">
                {match.firstName} {match.lastName}
              </h3>
            </span>
          </button>
        </li>
      ))}
    </ul>
  </div>
);

function App() {
  const [currentScreen, setCurrentScreen] = useState("profile");
  const renderScreen = () => {
    switch (currentScreen) {
      case "profile":
        return <ProfileSelector />;
      case "matches":
        return <MatchesList />;
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
