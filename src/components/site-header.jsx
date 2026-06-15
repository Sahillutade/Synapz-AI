import axios from "axios";
import { useEffect, useState } from "react";
import { useCookies } from "react-cookie"
import { Link, useNavigate } from "react-router-dom";



export function SiteHeader() {

    const [cookies, , removeCookies] = useCookies(["user"]);
    const navigate = useNavigate();

    const user = cookies.user;

    const [userInfo, setUserInfo] = useState(null);

    useEffect(() => {
        const fetchUserInfo = async () => {
            const info = await axios.get(`http://localhost:8080/user/details`);

            setUserInfo(info.data);
            return;
        }

        fetchUserInfo();
    },[])

    return(
        <header className="sticky top-0 z-40 border-b border-edge/60 bg-background/80 backdrop-blur-xl">
            <div className="mx-auto flex h-16 w-full max-w-6xl items-center justify-between px-4 sm:px-6">
                <div className="transition-opacity hover:opacity-80">
                    <span className="flex items-center gap-2">
                        <span className="relative inline-flex items-center size-8 justify-center">
                            <img src="/synapzai.png" className="size-5"></img>
                        </span>
                        <span className="text-lg font-semibold tracking-tight text-foreground">
                            Synapz <span className="text-first"> AI</span>
                        </span>
                    </span>
                </div>
                <div className="flex items-center gap-2">
                    {user ? (
                        <>
                           <Link className="items-center justify-center whitespace-nowrap text-sm font-medium transition-all outline-none focus-visible:border-ring focus-visible:ring-ring/50 focus-visible:ring-[3px] hover:bg-accent hover:text-accent-foreground dark:hover:bg-accent/50 h-8 rounded-md px-3 hidden gap-1.5 sm:inline-flex">
                                <span className="bi bi-stars size-4 text-first" />
                                Upgrade
                            </Link>
                            <div className="dropdown">
                                <button className="flex items-center gap-2 rounded-full p-0.5 outline-none ring-offset-background transition focus-visible:ring-2 focus-visible:ring-ring" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                                    <span className="relative flex shrink-0 overflow-hidden rounded-full size-9 ring-1 ring-edge">
                                        <img src={userInfo.profileImageUrl} className="aspect-square size-full" alt={userInfo.username} />
                                    </span>
                                </button>
                                <ul className="dropdown-menu bg-popover text-popover-foreground z-50 min-w-[8rem] overflow-x-hidden overflow-y-auto rounded-md border p-1 shadow-md w-56">
                                    <li> 
                                        <a className="dropdown-item px-2 py-1.5 text-sm font-medium flex flex-col">
                                            <span className="text-sm font-medium"> {userInfo.username} </span>
                                            <span className="text-xs font-normal text-muted-foreground"> {userInfo.subscriptionPackage} </span>
                                        </a> 
                                    </li>
                                    <li> <hr className="dropdown-divider bg-edge -mx-1 my-1 h-px"></hr> </li>
                                    <li> <Link className="dropdown-item relative flex cursor-default items-center gap-2 rounded-sm px-2 py-1.5 text-sm outline-hidden select-none text-muted-foreground">
                                        <span className="bi bi-person size-4" />
                                        Profile Settings
                                    </Link> </li>
                                    <li> <Link className="dropdown-item text-muted-foreground relative flex cursor-default items-center gap-2 rounded-sm px-2 py-1.5 text-sm outline-hidden select-none">
                                        <span className="bi bi-credit-card size-4" />
                                        Subscription
                                    </Link> </li>
                                    <li> <hr className="dropdown-divider bg-border -mx-1 my-1 h-px"></hr> </li>
                                    <li> <button className="text-destructive relative flex cursor-default items-center gap-2 rounded-sm px-2 py-1.5 text-sm outline-hidden select-none bg-none">
                                            <span className="bi bi-box-arrow-right size-4" />
                                            Log out
                                        </button> </li>
                                </ul>
                            </div>
                        </>
                    ) : (
                        <>
                            <Link className="inline-flex items-center justify-center whitespace-nowrap text-sm font-medium transition-all shrink-0 outline-none hover:bg-accent dark:hover:bg-accent/50 h-8 rounded-md gap-1.5 px-3 text-decoration-none text-foreground" style={{ color: 'var(--foreground)' }}>Log in</Link>
                            <Link className="inline-flex items-center justify-center whitespace-nowrap text-sm font-medium transition-all shrink-0 outline-none focus-visible:border-ring focus-visible:ring-ring/50 focus-visible:ring-[3px] bg-first text-first-foreground hover:bg-first/90 h-8 rounded-md gap-1.5 px-3 text-decoration-none" style={{ color: 'var(--first-foreground)' }}>Get Started</Link>
                        </>
                    )}
                </div>
            </div>
        </header>
    )

}