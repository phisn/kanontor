import {Component, useState} from "react";
import {MessageModel} from "../models/MessageModel";
import {Message} from "./Message";
import {ChatInput} from "./ChatInput";

type ChatState = {
    messages: MessageModel[]
}

export class Chat extends Component<{}, ChatState> {
    state: ChatState = {
        messages: [
            {
                username: "User 1",
                text: "Hello world"
            }
        ]
    }

    render() {
        return (
            <div className="
              flex flex-col justify-end
              border-8 border-white bg-gray-800 rounded-xl 2xl:w-2/6 xl:w-3/6 lg:w-4/6 w-5/6 h-2/6 p-10">
                {
                    this.state.messages.map(v => <Message message={v}/>)
                }
                <div className="h-6"/>
                <ChatInput onSend={this.onSend}/>
            </div>
        )
    }

    onSend(text: String) {
       this.setState({
           messages: [
               ...this.state.messages,
               {
                   username: "Test",
                   text: text
               }
           ]
       })
    }
}
