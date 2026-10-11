import {
  createContext,
  useContext,
  useEffect,
  useState,
} from "react";

import {
  getFestivals,
  getFestivalDays,
} from "../api/festivalApi";

import { getIndiaDateString } from "../utils/dateUtils";
import { useTheme } from "./ThemeContext";

const FestivalContext = createContext(null);

function extractList(response) {
  if (Array.isArray(response)) {
    return response;
  }

  if (Array.isArray(response?.data)) {
    return response.data;
  }

  return [];
}

function getThemeFromDay(day) {
  if (!day?.theme) {
    return null;
  }

  return {
    primary: day.theme.primaryColor,
    secondary: day.theme.secondaryColor,
    accent: day.theme.accentColor,
    accentLight: day.theme.accentLightColor,
    accentDark: day.theme.accentDarkColor,
    glow: day.theme.glowColor,
  };
}

export function FestivalProvider({ children }) {
  const { setTheme, resetTheme } = useTheme();

  const [festivals, setFestivals] = useState([]);
  const [currentFestival, setCurrentFestival] = useState(null);
  const [festivalDays, setFestivalDays] = useState([]);
  const [currentDay, setCurrentDay] = useState(null);

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // ---------------------------------------------------------
  // LOAD FESTIVAL
  // ---------------------------------------------------------

  useEffect(() => {
    let cancelled = false;

    async function loadFestivalData() {
      try {
        setLoading(true);
        setError(null);

        const festivalResponse = await getFestivals();

        if (cancelled) {
          return;
        }

        const festivalList = extractList(festivalResponse);

        setFestivals(festivalList);

        if (festivalList.length === 0) {
          setCurrentFestival(null);
          setFestivalDays([]);
          return;
        }

        const sortedFestivals = [...festivalList].sort(
          (a, b) => (b.year ?? 0) - (a.year ?? 0)
        );

        const festival = sortedFestivals[0];

        setCurrentFestival(festival);

        if (!festival?.id) {
          setFestivalDays([]);
          return;
        }

        const daysResponse = await getFestivalDays(
          festival.id
        );

        if (cancelled) {
          return;
        }

        const dayList = extractList(daysResponse);

        setFestivalDays(dayList);
      } catch (requestError) {
        if (cancelled) {
          return;
        }

        console.error(
          "Festival data loading failed:",
          requestError
        );

        setError(requestError);

        setCurrentFestival(null);
        setFestivalDays([]);
        setCurrentDay(null);

        resetTheme();
      } finally {
        if (!cancelled) {
          setLoading(false);
        }
      }
    }

    loadFestivalData();

    return () => {
      cancelled = true;
    };
  }, []);

  // ---------------------------------------------------------
  // FIND TODAY'S FESTIVAL DAY
  // ---------------------------------------------------------

  useEffect(() => {
    if (!festivalDays.length) {
      setCurrentDay(null);
      resetTheme();

      return;
    }

    const today = getIndiaDateString();

    const matchingDay = festivalDays.find((day) => {
      if (!day?.festivalDate) {
        return false;
      }

      return (
        String(day.festivalDate).slice(0, 10) === today
      );
    });

    setCurrentDay(matchingDay ?? null);

    const dayTheme = getThemeFromDay(matchingDay);

    if (dayTheme) {
      setTheme(dayTheme);
    } else {
      resetTheme();
    }
  }, [festivalDays]);

  // ---------------------------------------------------------
  // CONTEXT VALUE
  // ---------------------------------------------------------

  return (
    <FestivalContext.Provider
      value={{
        festivals,
        currentFestival,
        festivalDays,
        currentDay,
        loading,
        error,
      }}
    >
      {children}
    </FestivalContext.Provider>
  );
}

export function useFestival() {
  const context = useContext(FestivalContext);

  if (!context) {
    throw new Error(
      "useFestival must be used inside FestivalProvider"
    );
  }

  return context;
}