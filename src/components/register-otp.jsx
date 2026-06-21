import { useState } from "react";
import { SiteHeader } from "./site-header";
import axios from "axios";
import { useNavigate, useParams } from "react-router-dom";


export function RegisterOtp() {

    const [otp, setOtp] = useState(Array(6).fill(""));
    const [password, setPassword] = useState("");
    const [confirm, setConfirm] = useState("");

    const { registrationToken } = useParams();

    let navigate = useNavigate();

    const handleOtpChange = (value, index) => {

        if(!/^\d?$/.test(value)) return;

        const newOtp = [...otp];
        newOtp[index] = value;
        setOtp(newOtp);

        if(value && index < 5) {
            document.getElementById(`otp-${index + 1}`)?.focus();
        }

    };

    const handleKeyDown = (e, index) => {
        if(e.key === "Backspace" && !otp[index] && index > 0){
            document.getElementById(`otp-${index - 1}`)?.focus();
        }
    };

    const handleConfirm = async (e) => {
        e.preventDefault();

        try{
            if(otp.length < 6){
                alert("Enter the 6 digit code. Sent on Email-ID.");
                return;
            }
            if(password.length < 6){
                alert("Password must be at least 6 characters.");
                return;
            }
            if(password!=confirm){
                alert("Passwords do not match.");
                return;
            }

            const otpValue = otp.join("");

            const response = await axios.post(
                "http://localhost:8080/user/register/verify",
                {
                    registrationToken,
                    otp: otpValue,
                    password
                }
            );

            const result = await response.data();

            alert(result);
            navigate("/login");

        }
        catch(error){
            console.error(error);
        }
    };

    const handleBack = () => {
        navigate("/registerstep1");
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
                                Verify & set your password
                            </h1>
                            <p className="mt-1.5 text-sm text-muted-foreground text-pretty">
                                We sent a code. Enter it below to finish.
                            </p>
                        </div>
                        <form onSubmit={handleConfirm} className="space-y-5">
                            <div className="space-y-2">
                                <label className="flex items-center gap-2 text-sm leading-none font-medium select-none" style={{ color: 'var(--foreground)' }}>
                                    One-time passcode
                                </label>
                                <div className="flex justify-center rounded-lg border border-edge/60 bg-second/20 py-4">
                                    <div className="flex items-center gap-2">
                                        <div className="flex items-center">
                                            {otp.map((digit, index) => (
                                                <input key={index} id={`otp-${index}`} type="text" inputMode="numeric" maxLength={1} value={digit} onChange={(e) => handleOtpChange(e.target.value, index)} onKeyDown={(e) => handleKeyDown(e, index)} style={{ color: 'var(--foreground)' }} className="dark:bg-input/30 border-input relative flex h-9 w-9 items-center justify-center border-y border-r text-center text-sm shadow-xs transition-all outline-none first:rounded-l-md first:border-l last:rounded-r-md focus:z-10 focus:border-ring focus:ring-[3px] focus:ring-ring/50 aria-invalid:border-destructive"></input>
                                            ))}
                                        </div>
                                    </div>
                                </div>
                                <p className="flex items-center justify-center gap-1.5 text-xs text-muted-foreground">
                                    <span className="bi bi-envelope-check size-3.5 text-first"></span>
                                    Enter 6 digit OTP sent on email ID
                                </p>
                            </div>
                            <div className="space-y-2">
                                <label htmlFor="password" className="flex items-center gap-2 text-sm leading-none font-medium select-none text-foreground">Create Password</label>
                                <input id="password" type="password" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="At least 6 characters" required className="file:text-foreground placeholder:text-muted-foreground selection:bg-first selection:text-first-foreground dark:bg-input/30 border-input h-9 w-full min-w-0 rounded-3 border bg-transparent px-3 py-1 text-base shadow-xs transition-[color, box-shadow] outline-none file:inline-flex file:h-7 file:border-0 file:bg-transparent file:text-sm file:font-medium md:text-sm focus:border-ring focus:ring-ring/50 focus:ring-[3px] text-foreground"></input>
                            </div>
                            <div className="space-y-2">
                                <label htmlFor="confirm" className="flex items-center gap-2 text-sm leading-none font-medium select-none text-foreground">Confirm Password</label>
                                <input id="confirm" type="password" value={confirm} onChange={(e) => setConfirm(e.target.value)} placeholder="Re-enter your password" required className="file:text-foreground placeholder:text-muted-foreground selection:bg-first selection:text-first-foreground dark:bg-input/30 border-input h-9 w-full min-w-0 rounded-3 border bg-transparent px-3 py-1 text-base shadow-xs transition-[color, box-shadow] outline-none file:inline-flex file:h-7 file:border-0 file:bg-transparent file:text-sm file:font-medium md:text-sm focus:border-ring focus:ring-ring/50 focus:ring-[3px] text-foreground"></input>
                            </div>
                            <button type="submit" className="inline-flex items-center justify-center gap-2 whitespace-nowrap rounded-3 text-sm font-medium transition-all shrink-0 outline-none focus:border-ring focus:ring-ring/50 focus:ring-[3px] bg-first text-first-foreground hover:bg-first/90 border bg-background h-9 px-4 py-2 w-full">Confirm</button>
                        </form>
                    </div>
                </div>
            </main>
        </div>
    )

}