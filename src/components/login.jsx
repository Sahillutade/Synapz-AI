import { useEffect, useState } from "react";
import { SiteHeader } from "./site-header";
import { Link, useNavigate, useSearchParams } from "react-router-dom";
import { useCookies } from "react-cookie";
import axios from "axios";


export function Login() {

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    let navigate = useNavigate();
    const [ params ] = useSearchParams();

    const [cookies, setCookie, ] = useCookies(['user']);

    const handleBack = () => {
        navigate('/');
    }

    const handleGoogleSignIn = () => {
        window.location.href = "http://localhost:8080/oauth2/authorization/google";
    }

    const handleLogIn = async (e) => {
        e.preventDefault();

        try {
            const response = await axios.post(
                "http://localhost:8080/user/login",
                {
                    email,
                    password
                }
            );

            const result = await response.data;

            alert(result);
            navigate('/chat');
            setCookie("user", result?.token, {
                path: "/"
            });

        }
        catch(error){
            console.error(error);
        }
    };

    return(
        <div className="flex min-h-screen flex-col">
            <SiteHeader />

            <main className="relative flex flex-1 items-center justify-center overflow-hidden px-4 py-10">
                <div className="absolute -top-20 left-1/2 size-[30rem] -translate-x-1/2 rounded-full bg-first/15 blur-3xl" aria-hidden="true"></div>
                <div className="relative w-full max-w-md animate-fade-in-up">
                    <div className="mb-3">
                        <button onClick={handleBack} className="inline-flex items-center justify-center whitespace-nowrap text-sm font-medium transition-all shrink-0 outline-none hover:bg-accent dark:hover:bg-accent/50 h-8 rounded-3 px-3 gap-1.5 text-muted-foreground hover:text-foreground">
                            <span className="bi bi-arrow-left size-4 mb-2"></span>
                            Back
                        </button>
                    </div>
                    <div className="rounded-2xl border-edge/70 bg-card/70 p-6 shadow-2xl shadow-first/5 backdrop-blur sm:p-8">
                        <div className="mb-6">
                            <h1 className="text-2xl font-semibold tracking-tight text-balance" style={{ color: 'var(--foreground)' }}>
                                Welcome back
                            </h1>
                            <p className="mt-1.5 text-sm text-muted-foreground text-pretty">
                                Log in to continue your conversations.
                            </p>
                        </div>
                    
                        <form onSubmit={handleLogIn} className="space-y-4">
                            <div className="space-y-2">
                                <label htmlFor="email" className="flex items-center gap-2 text-sm leading-none font-medium select-none text-foreground">Email</label>
                                <input id="email" type="email" value={email} onChange={(e) => setEmail(e.target.value)} placeholder="ada@synapz.ai" required className="file:text-foreground placeholder:text-muted-foreground selection:bg-first selection:text-first-foreground dark:bg-input/30 border-input h-9 w-full min-w-0 rounded-3 border bg-transparent px-3 py-1 text-base shadow-xs transition-[color, box-shadow] outline-none file:inline-flex file:h-7 file:border-0 file:bg-transparent file:text-sm file:font-medium md:text-sm focus:border-ring focus:ring-ring/50 focus:ring-[3px] text-foreground" />
                            </div>
                            <div className="space-y-2">
                                <div className="flex items-center justify-between">
                                    <label htmlFor="password" className="flex items-center gap-2 text-sm leading-none font-medium select-none text-foreground">Password</label>
                                    <Link className="text-xs text-first text-decoration-none hover:underline">Forgot password?</Link>
                                </div>
                                <div className="relative">
                                    <input id="password" type="password" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="••••••••" required className="file:text-foreground placeholder:text-muted-foreground selection:bg-first selection:text-first-foreground dark:bg-input/30 border-input h-9 w-full min-w-0 rounded-3 border bg-transparent px-3 py-1 text-base shadow-xs transition-[color, box-shadow] outline-none file:inline-flex file:h-7 file:border-0 file:bg-transparent file:text-sm file:font-medium md:text-sm focus:border-ring focus:ring-ring/50 focus:ring-[3px] text-foreground"></input>
                                </div>
                            </div>
                            <button type="submit" className="inline-flex items-center justify-center gap-2 whitespace-nowrap rounded-3 text-sm font-medium transition-all shrink-0 outline-none focus:border-ring focus:ring-ring/50 focus:ring-[3px] text-first-foreground bg-first hover:bg-first/90 h-9 px-4 py-2 w-full">Log in</button>
                        </form>

                        <div className="my-5 flex items-center gap-3 text-xs text-muted-foreground">
                            <span className="h-px flex-1 bg-edge"></span>
                            OR 
                            <span className="h-px flex-1 bg-edge" />
                        </div>

                        <button type="button" onClick={handleGoogleSignIn} style={{ color: 'var(--foreground)' }} className="inline-flex items-center justify-center whitespace-nowrap rounded-3 text-sm font-medium transition-all shrink-0 outline-none border shadow-xs hover:bg-accent hover:text-accent-foreground dark:bg-input/30 dark:border-input dark:hover:bg-input/50 h-9 px-4 py-2 w-full gap-2.5 bg-second/40">
                            <span className="bi bi-google size-4 mb-2" aria-hidden="true"></span>
                            Continue with Google
                        </button>

                        <p style={{ marginTop: '24px' }} className="text-center text-sm text-muted-foreground">
                            Don&apos;t have an account?{'  '}
                            <Link to={'/registerstep1'}  className="text-first text-decoration-none hover:underline">
                                Sign up
                            </Link>
                        </p>
                    </div>
                </div>
            </main>
        </div>
    )

}