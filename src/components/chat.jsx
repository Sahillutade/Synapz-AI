import { useState } from "react";
import { SiteHeader } from "./site-header";
import { useEffect } from "react";
import axios from "axios";


export function ChatPage() {

    const [chats, setChats] = useState([]);
    const [openMenuId, setOpenMenuId] = useState(null);

    useEffect(() => {

        const loadChats = async () => {
            try{
                const response = await axios.get(
                    "http://localhost:8080/chat/my-chats"
                );

                setChats(response.data);
            }
            catch (error) {
                console.log(error);
            }
        }

        loadChats();
    },[])

    return(
        <div className="flex h-screen flex-col">
            <SiteHeader />

            <aside className="hidden w-72 border-r border-sidebar-border lg:flex lg:flex-col">
                <div className="p-3">
                    <button className="w-full justify-start gap-2 inline-flex items-center whitespace-nowrap rounded-3 text-sm font-medium transition-all shrink-0 outline-none focus:border-ring focus:ring-ring/50 focus:ring-[3px] bg-first text-first-foreground hover:bg-first/90 h-9 px-4 py-2">
                        <span className="bi bi-plus size-4 mb-1.5" />
                        New Chat
                    </button>
                </div>

                <div className="relative flex-1 overflow-y-auto px-3">
                    <div style={{ minWidth: '100%', display: "table" }}>
                        <div className="mb-4">
                            <p className="px-2 pb-1.5 text-xs font-medium text-muted-foreground">Pinned</p>
                            <div className="space-y-0.5">
                                {chats.map(chat => (
                                    (chat.pinned === true && (

                                        <div key={chat.id} className="group/item relative flex items-center rounded-4 text-sm transition-colors bg-second">
                                            <button className="flex min-w-0 flex-1 items-center gap-2 px-3 py-2 text-left">
                                                <span className="bi bi-pin size-3 shrink-0 text-first" />
                                                <span className="truncate"> {chat.title} </span>
                                            </button>
                                            <button onClick={() => setOpenMenuId(openMenuId === chat.id ? null : chat.id)} className="mr-1 flex size-7 shrink-0 items-center justify-center rounded-3 text-muted-foreground opacity-0 transition hover:bg-background hover:text-foreground focus:opacity-100 group-hover/item:opacity-100">
                                                <span className="bi bi-three-dots-vertical size-4" />
                                            </button>

                                            {openMenuId === chat.id && (
                                                <div style={{ position: 'fixed', left: '0px', top: '0px', transform: 'translate(95.2px, 182.4px)', minWidth: 'max-content', zIndex: '50' }}>
                                                    <div className="bg-popover text-foreground z-50 min-w-[8rem] overflow-x-hidden overflow-y-auto rounded-3 border p-1 shadow-md w-44" tabIndex="-1">
                                                        <button className="focus:bg-accent focus:text-accent-foreground relative flex cursor-default items-center gap-2 rounded-sm px-2 py-1.5 text-sm outline-hidden select-none" tabIndex="-1">
                                                            <span className="bi bi-pin size-4" />
                                                            Unpin
                                                        </button>
                                                    </div>
                                                </div>
                                            )}
                                        </div>

                                    ))
                                ))}
                            </div>
                        </div>
                    </div>
                </div>
            </aside>
        </div>
    )

}