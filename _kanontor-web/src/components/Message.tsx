import {MessageModel} from "../models/MessageModel";

type MessageProps = {
    message: MessageModel
}

export const Message = ({ message}: MessageProps) =>
    <div className="text-gray-100 text-lg">
        {message.username} | {message.text}
    </div>
