import {MessageModel} from "../models/MessageModel";

type MessageProps = {
    message: MessageModel
}

export const Message = ({ message}: MessageProps) =>
    <div className="flex text-gray-100 text-lg">
        {message.username}: <div className="w-5"/> {message.text}
    </div>
