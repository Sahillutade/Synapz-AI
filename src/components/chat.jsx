import { SiteHeader } from "./site-header";


export function ChatPage() {

    return(
        <div className="flex h-screen flex-col">
            <SiteHeader />

            <div className="flex min-h-0 flex-1">
                <aside className="hidden w-72 shrink-0 border-r border-sidebar-border lg:block">
                    
                </aside>
            </div>
        </div>
    )

}