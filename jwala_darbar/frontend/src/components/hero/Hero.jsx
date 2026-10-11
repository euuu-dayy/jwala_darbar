import {
  ArrowDown,
  ArrowRight,
  Sparkles,
} from "lucide-react";

import { useFestival } from "../../context/FestivalContext";
import { useTheme } from "../../context/ThemeContext";

import DeviImage from "./DeviImage";
import ThemeParticles from "./ThemeParticles";

function Hero() {
  const {
    currentFestival,
    currentDay,
    loading,
  } = useFestival();

  const { theme } = useTheme();

  function scrollToEvents(event) {
    event.preventDefault();

    const target =
      document.querySelector("#events");

    if (!target) {
      return;
    }

    target.scrollIntoView({
      behavior: "smooth",
    });
  }

  function scrollToAbout(event) {
    event.preventDefault();

    const target =
      document.querySelector("#about");

    if (!target) {
      return;
    }

    target.scrollIntoView({
      behavior: "smooth",
    });
  }

  return (
    <section
      id="hero"
      className="hero"
      style={{
        "--hero-accent": theme.accent,
        "--hero-glow": theme.glow,
      }}
    >
      <ThemeParticles />

      <div className="hero-glow" />

      <div className="hero-grid" />

      <div className="hero-noise" />

      <div className="hero-content">
        <div className="hero-eyebrow">
          <span className="hero-eyebrow-line" />

          <Sparkles size={14} />

          <span>
            {loading
              ? "THE CELEBRATION IS LOADING"
              : currentDay
                ? `DAY ${String(
                    currentDay.dayNumber
                  ).padStart(2, "0")} • ${
                    currentDay.title
                  }`
                : currentFestival
                  ? `${currentFestival.year} • NAVDURGA UTSAV`
                  : "NAVDURGA UTSAV"}
          </span>
        </div>

        <h1 className="hero-title">
          <span className="hero-title-line">
            ज्वाला
          </span>

          <span
            className="hero-title-accent"
            style={{
              color: theme.accent,
            }}
          >
            नवदुर्गा
          </span>

          <span className="hero-title-sub">
            उत्सव मंडळ
          </span>
        </h1>

        {currentDay && (
          <div className="hero-current-day">
            <span
              className="hero-current-day-dot"
              style={{
                background: theme.accent,
                boxShadow: `0 0 18px ${theme.glow}`,
              }}
            />

            <span>
              DAY{" "}
              {String(
                currentDay.dayNumber
              ).padStart(2, "0")}
            </span>

            <span className="hero-current-day-divider">
              —
            </span>

            <span>
              {currentDay.title}
            </span>
          </div>
        )}

        <p className="hero-description">
          Nine sacred days.
          <br />
          Nine expressions of Devi.
          <br />
          One celebration that brings
          everyone together.
        </p>

        <div className="hero-actions">
          <a
            href="#events"
            className="hero-primary-button"
            onClick={scrollToEvents}
          >
            <span>
              Explore Celebration
            </span>

            <ArrowRight size={17} />
          </a>

          <a
            href="#about"
            className="hero-secondary-button"
            onClick={scrollToAbout}
          >
            Discover Our Mandal
          </a>
        </div>
      </div>

      <DeviImage />

      <div className="hero-scroll">
        <span>
          SCROLL TO EXPERIENCE
        </span>

        <div className="hero-scroll-line">
          <span />
        </div>

        <ArrowDown size={14} />
      </div>

      <div className="hero-corner hero-corner-left">
        <span>ॐ</span>
      </div>

      <div className="hero-corner hero-corner-right">
        <span>जय माता दी</span>
      </div>
    </section>
  );
}

export default Hero;