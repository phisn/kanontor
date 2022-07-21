import {ChangeEvent, useState} from "react";

type ChatInputProps = {
    onSend: (text: String) => void
}

export function ChatInput({ onSend }: ChatInputProps) {
    const [text, setText] = useState("")

    function handleSubmit(e: any) {
        e.preventDefault()
        onSend(e.target.value)
        setText("")
    }

    return (
        <div className="flex items-center space-x-1">
            <div className="text-gray-300 w-2 h-2 mb-1">
                <svg className="fill-gray-400" id="Layer_1" data-name="Layer 1" xmlns="http://www.w3.org/2000/svg"
                     viewBox="0 0 103.28 122.88">
                    <polygon
                        points="0 122.88 103.28 76.93 103.28 45.95 0 0 0 33.94 75.46 61.34 0 88.74 0 122.88 0 122.88"/>
                </svg>
            </div>
            <form className="w-full" onChange={handleSubmit}>
                {/* TODO: define input */}
                <input
                    className="underline-offset-8 underline bg-gray-800 leading-tight text-gray-400 focus:outline-none w-full p-1"
                    value={text}
                    onChange={e => setText(e.target.value)}/>
                <input className="hidden" type="submit"/>
                <div className="bg-gray-600 h-0.5 w-full"/>
            </form>
        </div>
    )
}