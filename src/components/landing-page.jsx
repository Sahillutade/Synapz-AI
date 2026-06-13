import { SiteHeader } from "./site-header";



export function LandingPage() {

    return(
        <div className="flex min-h-screen flex-col">
            <SiteHeader />

            <section className="relative overflow-hidden">
                <div className="absolute inset-0 glow-grid opacity-40" aria-hidden="true"></div>
                <div className="absolute -top-24 left-1/2 size-[36rem] -translate-x-1/2 rounded-full bg-primary/20 blur-3xl animate-pulse-glow" aria-hidden="true"></div>
                
            </section>
        </div>
    )

}