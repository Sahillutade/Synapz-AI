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
        <header className="sticky top-0 z-40 border-b  border-border/60 bg-background/80 backdrop-blur-xl">
            <div className="mx-auto flex h-16 w-full max-w-6xl items-center justify-between px-4 sm:px-6">
                <div className="gap-2 items-center flex">
                    <span className="relative inline-flex size-8 items-center justify-center rounded-lg bg-primary/15 ring-1 ring-primary/30">
                        <img src="./public/synapzai.png" className="size-5"></img>
                    </span>
                    <span className="text-lg font-semibold tracking-tight">
                        Synapz <span className="text-primary"> AI</span>
                    </span>
                </div>

                <div className="flex items-center gap-2">
                    {user ? (
                        <>
                            <Link className="hidden items-center justify-center whitespace-nowrap text-sm font-medium transition-all disabled:pointer-events-none disabled:opacity-50 outline-none focus-visible:border-ring focus-visible:ring-ring/50 focus-visible:ring-[3px] hover:bg-accent hover:text-accent-foreground rounded-md px-3 gap-1.5 sm:inline-flex"> <span className="bi bi-stars size-4 text-primary"></span> Upgrade</Link>

                            <div className="dropdown">
                                <button className="flex items-center gap-2 rounded-full p-0.5 outline-none ring-offset-background transition focus-visible:ring-2 focus-visible:ring-ring" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                                    <div className="relative flex shrink-0 overflow-hidden rounded-full size-9 ring-1 ring-border">
                                        {userInfo?.profileImageUrl ? (
                                            <img src={userInfo.profileImageUrl} alt={userInfo.username} className="aspect-square size-full"></img>
                                        ) :(
                                            <div className="w-9 h-9 rounded-full border flex items-center justify-center bg-gray-200 font-semibold">
                                                {userInfo?.username?.slice(0, 2).toUpperCase()}
                                            </div>
                                        )}
                                    </div>
                                </button>
                                <ul className="dropdown-menu bg-popover text-popover-foreground z-50 min-w-[8rem] overflow-x-hidden overflow-y-auto rounded-md border p-1 shadow-md w-56">
                                    <li>
                                        <a className="dropdown-item px-2 py-1.5 text-sm font-medium flex flex-col data-[inset]:pl-8" href="#">
                                            <span className="text-sm font-medium"> {userInfo?.username} </span>
                                            <span className="text-xs font-normal text-muted-foreground"> {userInfo?.subscriptionPackage} </span>
                                        </a>
                                    </li>
                                    <li><hr className="dropdown-divider bg-border -mx-1 my-1 h-px"></hr></li>
                                    <li> 
                                        <a className="dropdown-item focus:bg-accent focus:text-accent-foreground relative flex cursor-default items-center gap-2 rounded-sm px-2 py-1.5 text-sm outline-hidden select-none">
                                            <span className="bi bi-person size-4"></span>
                                            Profile Settings
                                        </a> 
                                    </li>
                                    <li> 
                                        <a className="dropdown-item focus:bg-accent focus:text-accent-foreground relative flex cursor-default items-center gap-2 rounded-sm px-2 py-1.5 text-sm outline-hidden select-none">
                                            <span className="bi bi-credit-card size-4"></span>
                                            Subscription
                                        </a> 
                                    </li>
                                    <li><hr className="dropdown-divider bg-border -mx-1 my-1 h-px"></hr></li>
                                    <li> 
                                        <a className="dropdown-item focus:bg-accent focus:text-accent-foreground relative flex cursor-default items-center gap-2 rounded-sm px-2 py-1.5 text-sm outline-hidden select-none text-destructive">
                                            <span className="bi bi-box-arrow-right size-4"></span>
                                            Logout
                                        </a> 
                                    </li>
                                </ul>
                            </div>
                        </>
                    ) : (
                        <>
                            <div className="flex items-center gap-2">
                                <Link className="inline-flex items-center justify-center whitespace-nowrap text-sm font-medium transition-all disabled:pointer-events-none disabled:opacity-50 shrink-0 outline-none focus-visible:border-ring focus-visible:ring-ring/50 focus-visible:ring-[3px] aria-invalid:destructive/20 hover:bg-accent hover:text-accent-foreground dark:hover:bg-accent/50 h-8 rounded-md gap-1.5 px-3 text-decoration-none">Log in</Link>
                                <Link className="inline-flex items-center justify-center whitespace-nowrap text-sm font-medium transition-all disabled:pointer-events-none disabled:opacity-50 shrink-0 outline-none focus-visible:border-ring focus-visible:ring-ring/50 focus-visible:ring-[3px] aria-invalid:ring-destructive/20 bg-primary text-primary-foreground aria-invalid:border-destructive hover:bg-primary/90 hover:text-accent-foreground h-8 rounded-md gap-1.5 px-3 text-decoration-none dark:aria-invalid:ring-destructive/40">Get Started</Link>
                            </div>
                        </>
                    )}
                </div>
            </div>
        </header>
    )

}