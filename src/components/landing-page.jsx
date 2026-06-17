import { Link } from "react-router-dom";
import { SiteHeader } from "./site-header";
import { useCookies } from "react-cookie";

export function LandingPage() {

    const features = [
        {
            icon: 'bi bi-chat-left',
            title: 'Neural conversation',
            desc: 'Chat like you would with a teammate. Synapz keeps context across the whole thread.',
        },
        {
            icon: 'bi bi-image',
            title: 'See & create images',
            desc: 'Upload a photo, capture from your camera, or generate fresh visuals on the fly.',
        },
        {
            icon: 'bi bi-paperclip',
            title: 'Work with your files',
            desc: 'Drop in documents and let Synapz summarize, extract, and reason over them.'
        },
        {
            icon: 'bi bi-lightning-charge',
            title: 'Lightning fast',
            desc: 'Streaming responses that keep up with your train of thought.',
        },
        {
            icon: 'bi bi-shield-check',
            title: 'Private by design',
            desc: 'Your conversations stay yours. Manage everything from one place.',
        },
        {
            icon: 'bi bi-stars',
            title: 'Plans that scale',
            desc: 'Start free, upgrade to Plus, Pro, or Ultra as your needs grow.',
        },
    ];

    const [cookies] = useCookies(['user']);

    return(
        <div className="flex min-h-screen flex-col">
            <SiteHeader />

            <section className="relative overflow-hidden">
                <div className="absolute inset-0 glow-grid opacity-40" aria-hidden="true"></div>
                <div className="absolute -top-24 left-1/2 size-[36rem] -translate-x-1/2 rounded-full bg-first/20 blur-3xl animate-pulse-glow" aria-hidden="true"></div>
                <div className="relative mx-auto flex w-full max-w-6xl flex-col items-center justify-center px-4 pb-20 pt-20 text-center sm:px-6 sm:pt-28">
                    <div className="mb-6 inline-flex items-center gap-2 rounded-full border-edge/70 bg-second/40 px-4 py-1.5 text-sm text-muted-foreground animate-fade-in">
                        <span className="bi bi-stars size-3.5 text-first" />
                        Meet your new thinking partner
                    </div>
                    <h1 className="max-w-3xl text-balance text-4xl font-semibold tracking-tight sm:text-6xl animate-fade-in-up" style={{ color: 'var(--foreground)' }}>
                        Think faster with {'  '}
                        <span className="text-first">Synapz AI</span>
                    </h1>
                    <p className="mt-6 max-w-xl text-pretty text-lg leading-relaxed text-muted-foreground animate-fade-in-up" style={{ animationDelay: '80ms' }}>
                        A next-generation AI chat assistant that helps you write, learn, create, and solve problems - with images, files, and conversation that actually understands you.
                    </p>
                    <div className="mt-9 flex flex-col items-center gap-3 sm:flex-row animate-fade-in-up" style={{ animationDelay: '160ms' }}>
                        <button className="inline-flex items-center justify-center whitespace-nowrap text-sm font-medium transition-all disabled:pointer-events-none disabled:opacity-50 shrink-0 outline-none focus-visible:border-ring focus-visible:ring-ring/50 focus-visible:ring-[3px] aria-invalid:ring-destructive/20 bg-first text-first-foreground hover:bg-first/90 h-10 rounded-3 px-6 gap-2">
                            <Link to={'/registerstep1'} className="text-decoration-none" style={{ color: 'var(--first-foreground)' }}>
                                Get started free
                                <span className="bi bi-arrow-right size-4 pl-2"></span>
                            </Link>
                        </button>
                        <button className="inline-flex items-center justify-center whitespace-nowrap text-sm font-medium transition-all disabled:pointer-events-none disabled:opacity-50 shrink-0 outline-none focus-visible:border-ring focus-visible:ring-ring/50 focus-visible:ring-[3px] aria-invalid:ring-destructive/20 border shadow-xs hover:bg-accent text-accent-foreground bg-input/30 border-input dark:hover:bg-input/50 h-10 rounded-3 px-6 bg-secondary/30">
                            Login
                        </button>
                    </div>

                    <div className="relative mt-16 w-full max-w-3xl animate-fade-in-up" style={{ animationDelay: '240ms' }}>
                        <div className="rounded-2xl border border-edge/70 bg-card/70 p-3 shadow-2xl shadow-first/5 backdrop-blur">
                            <div className="rounded-xl border border-edge/60 bg-background/60 p-5 text-left">
                                <div className="flex items-start gap-3">
                                    <div className="flex size-8 shrink-0 items-center justify-center rounded-full bg-second text-sm" style={{ color: 'var(--foreground)' }}>
                                        You
                                    </div>
                                    <p className="rounded-2xl rounded-tl-sm bg-second/60 px-4 py-2.5 text-sm" style={{ color: 'var(--foreground)' }}>
                                        Explain ho neural networks learn, like I&apos;m five.
                                    </p>
                                </div>
                                <div className="mt-4 flex items-start gap-3">
                                    <div className="flex size-8 shrink-0 items-center justify-center rounded-full bg-first/20 text-first">
                                        <span className="bi bi-stars size-4"></span>
                                    </div>
                                    <p className="rounded-2xl rounded-tl-sm bg-first/10 px-4 py-2.5 text-sm leading-relaxed text-foreground/90">
                                        imagine a kid learning to catch a ball. Every miss teaches a lesson, so next time the hands move a little better. Neural networkslearn the same way - lots of small corrections until they get really good.
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <section className="mx-auto w-full max-w-6xl px-4 py-16 sm:px-6">
                <div className="mx-auto max-w-2xl text-center">
                    <h2 className="text-balance text-3xl font-semibold tracking-tight sm:text-4xl" style={{ color: 'var(--foreground)' }}>
                        Everything you need in one place
                    </h2>
                    <p className="mt-3 text-pretty text-muted-foreground">
                        Powerful tools wrapped in a clean, focused chat experience.
                    </p>
                </div>
                <div className="mt-12 grid gap-4 sm:grid-cols-2 lg:grid-cols-3">
                    {features.map((f) => (
                        <div key={f.title} className="group rounded-2xl border border-edge/70 bg-card/50 p-6 transition-all hover:-translate-y-1 hover:border-first/40 hover:bg-card">
                            <div className="flex size-11 items-center justify-center rounded-xl bg-first/15 text-first ring-1 ring-first/20 transition-transform group-hover:scale-110">
                                <span className={`${f.icon} size-5`}></span>
                            </div>
                            <h3 className="mt-4 font-medium" style={{ color: 'var(--foreground)' }}>{f.title}</h3>
                            <p className="mt-1.5 text-sm leading-relaxed text-muted-foreground">{f.desc}</p>
                        </div>
                    ))}
                </div>
            </section>

            <section className="mx-auto w-full max-w-6xl px-4 pb-24 sm:px-6">
                <div className="relative overflow-hidden rounded-3xl border border-edge/70 bg-gradient-to-br from-first/15 via-card to-card p-10 text-center sm:p-16">
                    <div className="absolute -right-10 -top-10 size-48 rounded-full bg-first/20 blur-3xl" aria-hidden="true"></div>
                    <h2 className="relative text-balance text-3xl font-semibold tracking-tight sm:text-4xl" style={{ color: 'var(--foreground)' }}>
                        Ready to start thinking with Synapz?
                    </h2>
                    <p className="relative mx-auto mt-3 max-w-md text-muted-foreground">
                        Create a free account in seconds. No credit card required.
                    </p>
                    <div className="relative mt-8 flex flex-col items-center justify-center gap-3 sm:flex-row">
                        <button className="inline-flex items-center justify-center whitespace-nowrap text-sm font-medium transition-all shrink-0 outline-none bg-first text-first-foreground hover:bg-first/90 h-10 rounded-3 px-6 gap-2">
                            <Link className="text-decoration-none" style={{ color: 'var(--first-foreground)' }}>Create your account
                                <span className="bi bi-arrow-right size-4 pl-3"></span>
                            </Link>
                        </button>
                        <button className="inline-flex items-center justify-center gap-2 whitespace-nowrap text-sm font-medium transition-all shrink-0 outline-none border shadow-xs hover:bg-accent hover:text-accent-foreground dark:bg-input/30 dark:border-input dark:hover:bg-input/50 h-10 rounded-3 px-6 bg-background/40">
                            <Link className="text-decoration-none" style={{ color: 'var(--foreground)' }}>
                                I already have an account
                            </Link>
                        </button>
                    </div>
                </div>
            </section>

            <footer className="border-t border-edge/60 py-8">
                <div className="mx-auto flex w-full max-w-6xl flex-col items-center justify-between gap-3 px-4 text-sm text-muted-foreground sm:flex-row sm:px-6">
                    <p>© {new Date().getFullYear()} Synapz AI. A demo experience.</p>
                    <div className="flex gap-5">
                        <Link className="hover:text-foreground text-decoration-none" style={{ color: 'var(--muted-foreground)' }}>Log in</Link>
                        <Link className="hover:text-foreground text-decoration-none" style={{ color: 'var(--muted-foreground)' }}>Register</Link>
                        <Link className="hover:text-foreground text-decoration-none" style={{ color: 'var(--muted-foreground)' }}>Pricing</Link>
                    </div>
                </div>
            </footer>
        </div>
    )

}