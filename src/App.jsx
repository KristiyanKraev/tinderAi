import { User, MessageCircle } from "lucide-react";
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
        <p>Text</p>
      </div>
    </div>
  );
};

function App() {
  return (
    <>
      <div className="max-w-md mx-auto">
        <nav className="flex justify-between ">
          <User />
          <MessageCircle />
        </nav>
        <ProfileSelector />
      </div>
    </>
  );
}

export default App;
