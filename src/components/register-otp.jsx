import { SiteHeader } from "./site-header";


export function RegisterOtp() {

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
                                Verify & set your password
                            </h1>
                            <p className="mt-1.5 text-sm text-muted-foreground text-pretty">
                                We sent a code. Enter it below to finish.
                            </p>
                        </div>
                        <form className="space-y-5">
                            <div className="space-y-2">
                                <label className="flex items-center gap-2 text-sm leading-none font-medium select-none" style={{ color: 'var(--foreground)' }}>
                                    One-time passcode
                                </label>
                                <div className="flex justify-center rounded-lg border border-edge/60 bg-second/20 py-4">
                                    
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </main>
        </div>
    )

}