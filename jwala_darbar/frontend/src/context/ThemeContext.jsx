import {
  createContext,
  useContext,
  useEffect,
  useMemo,
  useState,
} from "react";

const ThemeContext = createContext(null);

const DEFAULT_THEME = {
  primary: "#F4C542",
  secondary: "#A87900",
  accent: "#FFD966",
  accentLight: "#FFF1B8",
  accentDark: "#765500",
  glow: "#FFE48A",
};

function normalizeTheme(theme) {
  return {
    primary: theme?.primary ?? DEFAULT_THEME.primary,
    secondary: theme?.secondary ?? DEFAULT_THEME.secondary,
    accent: theme?.accent ?? DEFAULT_THEME.accent,
    accentLight: theme?.accentLight ?? DEFAULT_THEME.accentLight,
    accentDark: theme?.accentDark ?? DEFAULT_THEME.accentDark,
    glow: theme?.glow ?? DEFAULT_THEME.glow,
  };
}

export function ThemeProvider({ children }) {
  const [theme, setThemeState] = useState(DEFAULT_THEME);

  useEffect(() => {
    const root = document.documentElement;

    root.style.setProperty("--theme-primary", theme.primary);
    root.style.setProperty("--theme-secondary", theme.secondary);
    root.style.setProperty("--theme-accent", theme.accent);
    root.style.setProperty("--theme-accent-light", theme.accentLight);
    root.style.setProperty("--theme-accent-dark", theme.accentDark);
    root.style.setProperty("--theme-glow", theme.glow);
    root.style.setProperty("--theme-particle", theme.accent);
    root.style.setProperty(
      "--theme-particle-soft",
      theme.accentLight
    );
  }, [theme]);

  const value = useMemo(
    () => ({
      theme,

      setTheme: (newTheme) => {
        setThemeState(normalizeTheme(newTheme));
      },

      resetTheme: () => {
        setThemeState(DEFAULT_THEME);
      },
    }),
    [theme]
  );

  return (
    <ThemeContext.Provider value={value}>
      {children}
    </ThemeContext.Provider>
  );
}

export function useTheme() {
  const context = useContext(ThemeContext);

  if (!context) {
    throw new Error(
      "useTheme must be used inside ThemeProvider"
    );
  }

  return context;
}