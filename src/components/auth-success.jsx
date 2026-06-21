import { useEffect } from "react";
import { useCookies } from "react-cookie";
import { useLocation, useNavigate } from "react-router-dom";


export function AuthSuccess() {

    const [, setCookie, ] = useCookies(["user"]);
    const navigate = useNavigate();
    const location = useLocation();

    useEffect(() => {
        const token = new URLSearchParams(location.search).get("token");

        if(token) {
            setCookie("user", token, {
                path: "/"
            });

            navigate("/subscription");
        }
    },[location, navigate, setCookie])

}