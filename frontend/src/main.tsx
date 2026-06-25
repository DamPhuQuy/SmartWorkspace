import { Agentation } from "agentation";
import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import App from "./App.tsx";
import "./index.css";

const ENV = import.meta.env.VITE_ENV;

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <App />
    {ENV === "development" && <Agentation />}
  </StrictMode>,
);
