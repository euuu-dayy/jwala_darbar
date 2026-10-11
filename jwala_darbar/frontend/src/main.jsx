import React from "react";
import ReactDOM from "react-dom/client";

import App from "./App";

import { ThemeProvider } from "./context/ThemeContext";
import { FestivalProvider } from "./context/FestivalContext";

import "./styles/variables.css";
import "./styles/globals.css";
import "./styles/animations.css";

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <ThemeProvider>
      <FestivalProvider>
        <App />
      </FestivalProvider>
    </ThemeProvider>
  </React.StrictMode>
);