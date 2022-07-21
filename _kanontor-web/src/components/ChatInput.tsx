
type ChatInputProps = {
    onSend: (text: String) => void
}

export const ChatInput = ({ onSend }: ChatInputProps) =>
    <div className="flex items-center space-x-1">
        <div className="text-gray-300">
            <svg id="Layer_1" data-name="Layer 1" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 103.28 122.88">
                <polygon points="0 122.88 103.28 76.93 103.28 45.95 0 0 0 33.94 75.46 61.34 0 88.74 0 122.88 0 122.88"/>
            </svg>
        </div>
        <div>
            <input className="underline-offset-8 underline bg-gray-800 leading-tight text-gray-500 focus:outline-none w-full p-1"
                   onInput={onSend}/>
            <div className="bg-white h-1 w-full">
            </div>
        </div>
    </div>