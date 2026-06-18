import { useEffect, useRef, useState } from "react";
import { SiteHeader } from "./site-header";
import { Link, useNavigate, useSearchParams } from "react-router-dom";
import axios from "axios";
import { useCookies } from "react-cookie";


export function RegisterStep1(){

    const [preview, setPreview] = useState(null);
    const [image, setImage] = useState(null);
    const [formData, setFormData] = useState({
        username: "",
        email: "",
        phone: ""
    });
    
    const fileRef = useRef(null);

    let navigate = useNavigate();

    const [, setCookie, ] = useCookies(['user']);
    const [params] = useSearchParams();

    const handleImageChange = (e) => {
        const file = e.target.files?.[0];

        if(file) {
            const imageUrl = URL.createObjectURL(file);
            setPreview(imageUrl);
            setImage(file);
        }
    };

    const handleChange = (e) => {
        const { name, value } = e.target;

        setFormData((prev)  => ({
            ...prev,
            [name]: value
        }));
    };

    const removeImage = () => {
        setImage(null);
        setPreview("");

        if(fileRef.current) {
            fileRef.current.value = "";
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const multipartData = new FormData();

            multipartData.append(
                "data",
                new Blob(
                    [
                        JSON.stringify({
                            username: formData.username,
                            email: formData.email,
                            phone: formData.phone
                        })
                    ],
                    {
                        type: "application/json"
                    }
                )
            );

            if(image){
                multipartData.append("image", image);
            }

            const response = await axios.post(
                "http://localhost:8080/user/register/step1",
                multipartData
            );

            const result = await response.data();

            console.log(result);
            navigate("/registerotp");
            
        }
        catch(error){
            console.error(error);
        }
    };

    const handleGoogleSignIn = () => {
        window.location.href = "http://localhost:8080/oauth2/authorization/google";
    };

    useEffect(() => {
        const token = params.get("token");

        if(token) {
            setCookie("user", token, { path: "/", });
            navigate("/", { replace: true });
        }
    },[params, navigate, setCookie])

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
                        <form className="space-y-4" onSubmit={handleSubmit}>
                            <div className="flex flex-col items-center gap-3">
                                <div className="relative">
                                    <span className="relative flex shrink-0 overflow-hidden rounded-full size-20 ring-2 ring-edge">
                                        {preview ? (
                                            <>
                                                <img src={preview} alt="Profile Preview" className="aspect-square size-full"></img>
                                            </>
                                        ) : (
                                            <>
                                                <div className="bg-second aspect-square size-full flex flex-column items-center justify-center">
                                                    <span className="bi bi-camera size-6 text-muted-foreground"></span>
                                                </div>
                                            </>
                                        )}
                                    </span>
                                    {preview && (
                                        <button style={{ borderRadius: '100%' }} type="button" onClick={removeImage} className="absolute -right-1 -top-1 flex flex-column size-6 items-center justify-center rounded-full bg-destructive text-destructive-foreground">
                                            <div className="flex flex-column items-center justify-center">
                                                <span className="bi bi-x size-3.5 -top-1"></span>
                                            </div>
                                        </button>
                                    )}
                                </div>
                                <input type="file" ref={fileRef} onChange={handleImageChange} accept="image/*" className="hidden"></input>
                                <button onClick={() => fileRef.current?.click()} className="inline-flex items-center justify-center whitespace-nowrap text-sm font-medium transition-all shrink-0 outline-none border shadow-xs hover:bg-accent hover:text-accent-foreground dark:bg-input/30 dark:border-input dark:hover:bg-input/50 h-8 rounded-3 gap-1.5 px-3 bg-second/40" style={{ color: 'var(--foreground)' }} type="button">
                                    Upload profile photo
                                </button>
                            </div>
                            <div className="space-y-2">
                                <label className="flex items-center gap-2 text-sm leading-none font-medium select-none" htmlFor="username" style={{ color: 'var(--foreground)' }}>Username</label>
                                <input type="text" id="username" name="username" value={formData.username} onChange={handleChange} placeholder="ada" required className="placeholder:text-muted-foreground selection:bg-first selection:text-first-foreground dark:bg-input/30 border-input h-9 w-full min-w-0 rounded-3 bg-transparent px-3 py-1 text-base shadow-xs transition-[color,box-shadow] outline-none file:inline-flex file:h-7 file:border-0 file:bg-transparent file:text-sm file:font-medium border" style={{ color: 'var(--foreground)' }}></input>
                            </div>
                            <div className="space-y-2">
                                <label className="flex items-center gap-2 text-sm leading-none font-medium select-none" htmlFor="email" style={{ color: 'var(--foreground)' }}>Email</label>
                                <input type="email" id="email" name="email" value={formData.email} onChange={handleChange} placeholder="you@example.com" required className="placeholder:text-muted-foreground selection:bg-first selection:text-first-foreground dark:bg-input/30 border-input h-9 w-full min-w-0 rounded-3 bg-transparent px-3 py-1 text-base shadow-xs transition-[color,box-shadow] outline-none file:inline-flex file:h-7 file:border-0 file:bg-transparent file:text-sm file:font-medium border" style={{ color: 'var(--foreground)' }}></input>
                            </div>
                            <div className="space-y-2">
                                <label className="flex items-center gap-2 text-sm leading-none font-medium select-none" htmlFor="phone" style={{ color: 'var(--foreground)' }}>Phone number</label>
                                <input type="text" id="phone" name="phone" value={formData.phone} onChange={handleChange} placeholder="+91 7896541235" required className="placeholder:text-muted-foreground selection:bg-first selection:text-first-foreground dark:bg-input/30 border-input h-9 w-full min-w-0 rounded-3 bg-transparent px-3 py-1 text-base shadow-xs transition-[color,box-shadow] outline-none file:inline-flex file:h-7 file:border-0 file:bg-transparent file:text-sm file:font-medium border" style={{ color: 'var(--foreground)' }}></input>
                            </div>
                            <button type="submit" className="inline-flex items-center justify-center gap-2 whitespace-nowrap rounded-3 text-sm font-medium transition-all shrink-0 outline-none bg-first text-first-foreground hover:bg-first/90 h-9 px-4 py-2 w-full">
                                Generate OTP
                            </button>
                        </form>
                        <div className="my-5 flex items-center gap-3 text-xs text-muted-foreground">
                            <span className="h-px flex-1 bg-edge"></span>
                            OR 
                            <span className="h-px flex-1 bg-edge"></span>
                        </div>
                        <button type="button" onClick={handleGoogleSignIn} style={{ color: 'var(--foreground)' }} className="inline-flex items-center justify-center whitespace-nowrap rounded-3 text-sm font-medium transition-all shrink-0 outline-none border shadow-xs hover:bg-accent hover:text-accent-foreground dark:bg-input/30 dark:border-input dark:hover:bg-input/50 h-9 px-4 py-2 w-full gap-2.5 bg-second/40">
                            <span className="bi bi-google size-4 mb-2" aria-hidden="true"></span>
                            Sign up with Google
                        </button>
                        <p style={{ marginTop: '30px' }} className="text-center text-sm text-muted-foreground">
                            Already have an account?{'  '}
                            <Link className="text-first text-decoration-none hover:underline">
                                Log in
                            </Link>
                        </p>
                    </div>
                </div>
            </main>
        </div>
    )

}