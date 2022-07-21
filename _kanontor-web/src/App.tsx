import { Component } from 'react';
import {Chat} from "./components/Chat";

class App extends Component {
  render() {
    return (
      <div className="
        flex items-center justify-center
        bg-gray-900 w-screen h-screen">
        <Chat/>
      </div>
    );
  }
}

export default App;
