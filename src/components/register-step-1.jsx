import { useRef, useState } from "react";
import { SiteHeader } from "./site-header";


export function RegisterStep1(){

    const [preview, setPreview] = useState(null);
    
    const fileRef = useRef(null);

    const handleImageChange = (e) => {
        const file = e.target.files?.[0];

        if(file) {
            const imageUrl = URL.createObjectURL(file);
            setPreview(imageUrl);
        }
    };

    return(
        <div className="flex min-h-screen flex-col">
            <SiteHeader />

            <main className="relative flex flex-1 items-center justify-center overflow-hidden px-4 py-10">
                <div className="absolute -top-20 left-1/2 size-[30rem] -translate-x-1/2 rounded-full bg-first/15 blur-3xl" aria-hidden="true"></div>
                <div className="relative w-full max-w-md animate-fade-in-up">
                    <div className="mb-3">
                        <button className="inline-flex items-center justify-center whitespace-nowrap text-sm font-medium transition-all shrink-0 outline-none hover:bg-accent dark:hover:bg-accent/50 h-8 rounded-3 px-3 gap-1.5 text-muted-foreground hover:text-foreground">
                            <span className="bi bi-arrow-left size-4 mb-2"></span>
                            Back
                        </button>
                    </div>
                    <div className="rounded-2xl border-edge/70 bg-card/70 p-6 shadow-2xl shadow-first/5 backdrop-blur sm:p-8">
                        <div className="mb-6">
                            <h1 className="text-2xl font-semibold tracking-tight text-balance" style={{ color: 'var(--foreground)' }}>
                                Create your account
                            </h1>
                            <p className="mt-1.5 text-sm text-muted-foreground text-pretty">
                                Join Synapz AI and start chatting in minutes.
                            </p>
                        </div>
                        <form className="space-y-4">
                            <div className="flex flex-col items-center gap-3">
                                <div className="relative">
                                    <span className="relative flex shrink-0 overflow-hidden rounded-full size-20 ring-2 ring-edge">
                                        {preview ? (
                                            <>
                                                <img src={preview} alt="Profile Preview" className="aspect-square size-full"></img>
                                            </>
                                        ) : (
                                            <>
                                                <div className="bg-second">
                                                    <span className="bi bi-camera size-6 text-muted-foreground items-center justify-center"></span>
                                                </div>
                                            </>
                                        )}
                                    </span>
                                    {preview && (
                                        <button type="button" onClick={() => setPreview('')} className="absolute -right-1 -top-1 flex size-6 items-center justify-center rounded-full bg-destructive text-destructive-foreground">
                                            <span className="bi bi-x size-3.5 bottom-1"></span>
                                        </button>
                                    )}
                                </div>
                                <input type="file" ref={fileRef} onChange={handleImageChange} accept="image/*" className="hidden"></input>
                                <button onClick={() => fileRef.current?.click()} className="inline-flex items-center justify-center whitespace-nowrap text-sm font-medium transition-all shrink-0 outline-none border shadow-xs hover:bg-accent hover:text-accent-foreground dark:bg-input/30 dark:border-input dark:hover:bg-input/50 h-8 rounded-3 gap-1.5 px-3 bg-second/40" style={{ color: 'var(--foreground)' }} type="button">
                                    Upload profile photo
                                </button>
                            </div>
                            <div className="space-y-2">
                                <label className="flex items-center gap-2 text-sm leading-none font-medium select-none" for="username" style={{ color: 'var(--foreground)' }}>Username</label>
                                <input type="text" id="username" placeholder="ada" required className="placeholder:text-muted-foreground selection:bg-first selection:text-first-foreground dark:bg-input/30 border-input h-9 w-full min-w-0 rounded-3 bg-transparent px-3 py-1 text-base shadow-xs transition-[color,box-shadow] outline-none file:inline-flex file:h-7 file:border-0 file:bg-transparent file:text-sm file:font-medium border" style={{ color: 'var(--foreground)' }}></input>
                            </div>
                            
                        </form>
                    </div>
                </div>
            </main>
        </div>
    )

}