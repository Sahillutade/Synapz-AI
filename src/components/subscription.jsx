import { useEffect, useState } from "react";
import { SiteHeader } from "./site-header";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { useCookies } from "react-cookie";


export function Subscription() {

    const [billing, setBilling] = useState("MONTHLY");
    const [packages, setPackages] = useState([]);

    const [cookies] = useCookies(["user"]);

    let navigate = useNavigate();

    useEffect(() => {
        const fetchPackages = async () => {

            try {
                const response = await axios.get(
                    "http://localhost:8080/subscription/packages"
                );

                setPackages(response.data);
            }
            catch(error) {
                console.error("Error fetching packages: ", error);
            }

        };

        fetchPackages();
    },[])

    const filterPackages = packages.filter((pkg) => pkg.billingCycle === billing);

    const handleSubscribe = async (pkg) => {

        try{

            if(!cookies?.user){
                alert("Login to subscribe.");
                navigate('/login');
            }

            if(pkg.price === 0){

                const responseFree = await axios.post("http://localhost:8080/subscription/free",
                    {},
                    {
                        headers: {
                            Authorization: `Bearer ${cookies?.user}`
                        }
                    }
                );

                alert(responseFree.data);
                return;

            }

            const orderResponse = await axios.post(
                "http://localhost:8080/subscription/create-order",
                {
                    packageId: pkg.id
                },
                {
                    headers: {
                        Authorization: `Bearer ${cookies?.user}`
                    }
                }
            );

            const {orderId, amount, key} = orderResponse.data;

            const options = {
                key,
                amount,
                currency: "INR",
                name: "Synapz AI",
                description: pkg.packagename,
                order_id: orderId,

                handler: async function (response) {
                    try {
                        const verifyResponse = await axios.post(
                            "http://localhost:8080/subscription/verify",
                            {
                                packageId: pkg.id,
                                razorpayOrderId: response.razorpay_order_id,
                                razorpayPaymentId: response.razorpay_payment_id,
                                razorpaySignature: response.razorpay_signature
                            },
                            {
                                headers:{
                                    Authorization: `Bearer ${cookies?.user}`
                                }
                            }
                        );

                        alert(verifyResponse.data);

                        navigate();
                    }
                    catch (error) {
                        console.error("Verification error: ", error);
                        alert("Payment verification failed");
                    }
                },

                modal: {
                    ondismiss: () => {
                        console.log("Checkout closed");
                    }
                },

                theme: {
                    color: "oklch(0.78, 0.14, 195)"
                }
            };

            const razorpay = new window.Razorpay(options);

            razorpay.open();

        }
        catch (error) {
            console.error("Subscription error: ", error);
            alert("Unable to process payment");
        }

    }

    return(
        <div className="flex min-h-screen flex-col">
            <SiteHeader />

            <main className="relative mx-auto w-full max-w-6xl flex-1 px-4 py-8 sm:px-6">
                <div className="absolute inset-x-0 top-0 h-64 glow-grid opacity-30" aria-hidden="true"></div>
                <div className="relative">
                    <button onClick={() => navigate('/')} className="inline-flex items-center justify-center whitespace-nowrap text-sm font-medium transition-all shrink-0 outline-none focus:border-ring focus:ring-ring/50 focus:ring-[3px] hover:bg-accent dark:hover:bg-accent/50 h-8 rounded-3 px-3 gap-1.5 text-muted-foreground hover:text-foreground mb-4">
                        <span className="bi bi-arrow-left size-4 mb-2" />
                        Back
                    </button>

                    <div className="mx-auto max-w-2xl text-center">
                        <span className="inline-flex items-center justify-center rounded-3 px-2 py-0.5 text-xs font-medium w-fit whitespace-nowrap shrink-0 focus:border-ring focus:ring-ring/50 focus:ring-[3px] transition-[color,box-shadow] overflow-hidden border-transparent bg-second text-second-foreground mb-4 gap-1.5">
                            <span className="bi bi-stars size-3.5 text-first"></span>
                            Plans & pricing
                        </span>
                        <h1 className="text-balance text-3xl font-semibold tracking-tight sm:text-4xl" style={{ color: 'var(--foreground)' }}>
                            Choose the plan that fits you
                        </h1>
                        <p className="mt-3 text-pretty text-muted-foreground">
                            Switch or cancel anytime. Annual billing saves upto 25%.
                        </p>
                    </div>

                    <div className="flex flex-col gap-2 mt-8 items-center">
                        <div className="bg-muted text-muted-foreground inline-flex h-9 w-fit items-center justify-center rounded-lg p-[3px]">
                            <button onClick={() => setBilling("MONTHLY")} type="button" className="focus:border-ring focus:ring-ring/50 focus:ring-[3px] focus:outline-ring text-foreground dark:tex-muted-foreground inline-flex h-[calc(100%-1px)] flex-1 items-center justify-center gap-1.5 rounded-3 border-transparent px-2 py-1 text-sm font-medium whitespace-nowrap transition-[color,box-shadow] focus:outline-1">Monthly</button>
                            <button onClick={() => setBilling("ANNUAL")} type="button" className="focus:border-ring focus:ring-ring/50 focus:ring-[3px] focus:outline-ring text-foreground dark:tex-muted-foreground inline-flex h-[calc(100%-1px)] flex-1 items-center justify-center gap-1.5 rounded-3 border-transparent px-2 py-1 text-sm font-medium whitespace-nowrap transition-[color,box-shadow] focus:outline-1">
                                Annual
                                <span className="inline-flex items-center justify-center rounded-3 px-2 py-0.5 text-xs font-medium w-fit whitespace-nowrap shrink-0 gap-1 focus:border-ring focus:ring-ring/50 focus:ring-[3px] transition-[color,box-shadow] overflow-hidden border-transparent ml-2 bg-first/15 text-first">Save 25%</span>
                            </button>
                        </div>
                    </div>
                </div>

                <div className="mt-8 grid gap-5 lg:grid-cols-4">
                    {filterPackages.map((pkg) => (
                        <div key={pkg.id} className="relative flex flex-col border rounded-2xl bg-card/60 p-6 transition-all border-edge/70 w-[100%]">
                            <h3 className="text-lg font-semibold text-foreground" style={{ color: 'var(--foreground)' }}> {pkg.packagename} </h3>
                            <p className="mt-1 text-sm text-muted-foreground"> {pkg.description} </p>

                            <div className="mt-5 flex items-end gap-1">
                                <span className="text-4xl font-semibold tracking-tight text-foreground">
                                    ₹{pkg.price}
                                </span>
                                <span className="mb-1 text-sm text-muted-foreground">
                                    {(billing === "ANNUAL") ? "/yr" : "/mo"}
                                </span>
                            </div>
                            
                            <button onClick={() => handleSubscribe(pkg)} className="inline-flex items-center justify-center gap-2 whitespace-nowrap rounded-3 text-sm font-medium transition-all shrink-0 outline-none focus:border-ring focus:ring-ring/50 focus:ring-[3px] bg-background shadow-xs hover:bg-accent hover:text-accent-foreground dark:bg-input/30 h-9 px-4 py-2 mt-5 w-full text-foreground">
                                { (pkg.price === 0) ? "Get Started" : "Choose plan" }
                            </button>

                            <ul style={{ marginTop: '24px' }} className="mt-6 space-y-3 text-sm">
                                { pkg.features.map((feature) => (
                                    <li key={feature.id} className="flex items-start gap-2.5">
                                        <i className="bi bi-check text-first"></i>
                                        <span className="text-muted-foreground">
                                            {feature.featureName}
                                        </span>
                                    </li>
                                ))}
                            </ul>
                        </div>
                    ))}
                </div>
            </main>
        </div>
    )

}