
import { useEffect, useRef } from "react";
import {
  ArrowUpRight,
  CalendarDays,
  Sparkles,
} from "lucide-react";
import gsap from "gsap";
import { ScrollTrigger } from "gsap/ScrollTrigger";

import { useFestival } from "../../context/FestivalContext";
import { useTheme } from "../../context/ThemeContext";
import { formatDate } from "../../utils/dateUtils";

gsap.registerPlugin(ScrollTrigger);

const NIGHT_JOURNEY = {
  1: {
    title: "Mahaarti",
    description: "An evening of devotion and divine blessings.",
  },
  2: {
    title: "Jagrata",
    description: "A sacred night filled with prayers and devotion.",
  },
  3: {
    title: "Magical Show",
    description: "An entertaining evening of magic and wonder.",
  },
  4: {
    title: "Eating Stalls",
    description: "Explore delicious food and festive flavours.",
  },
  5: {
    title: "Garba",
    description: "Celebrate the spirit of Navdurga through dance.",
  },
  6: {
    title: "Dance",
    description: "Enjoy an energetic evening of dance performances.",
  },
  7: {
    title: "Mimicry",
    description: "An entertaining night of mimicry and laughter.",
  },
  8: {
    title: "Mahaprasad",
    description: "Share the blessings of Devi through Mahaprasad.",
  },
  9: {
    title: "Miravnuk",
    description: "Conclude the festival with the grand procession.",
  },
};

function AboutJourney() {
  const sectionRef = useRef(null);
  const introRef = useRef(null);
  const journeyRef = useRef(null);

  const {
    currentFestival,
    festivalDays,
    currentDay,
  } = useFestival();

  const { theme } = useTheme();

  useEffect(() => {
    const section = sectionRef.current;
    const intro = introRef.current;
    const journey = journeyRef.current;

    if (!section || !intro || !journey) return;

    const context = gsap.context(() => {
      gsap.fromTo(
        intro.querySelectorAll(".about-reveal"),
        { opacity: 0, y: 45 },
        {
          opacity: 1,
          y: 0,
          duration: 0.9,
          stagger: 0.12,
          ease: "power3.out",
          scrollTrigger: {
            trigger: intro,
            start: "top 78%",
            once: true,
          },
        }
      );

      gsap.fromTo(
        journey.querySelectorAll(".journey-card"),
        { opacity: 0, y: 50 },
        {
          opacity: 1,
          y: 0,
          duration: 0.8,
          stagger: 0.1,
          ease: "power3.out",
          scrollTrigger: {
            trigger: journey,
            start: "top 75%",
            once: true,
          },
        }
      );
    }, section);

    return () => context.revert();
  }, [festivalDays]);

  return (
    <section
      ref={sectionRef}
      id="about"
      className="about-journey-section"
      style={{
        "--about-accent": theme.accent,
        "--about-glow": theme.glow,
      }}
    >
      {/* ABOUT */}
      <div ref={introRef} className="container about-intro">
        <div className="about-intro-left">
          <div className="section-eyebrow about-reveal">
            <span className="section-eyebrow-line" />
            <Sparkles size={14} />
            <span>ABOUT THE MANDAL</span>
          </div>

          <h2 className="about-title about-reveal">
            Where devotion
            <br />
            becomes <span>celebration.</span>
          </h2>
        </div>

        <div className="about-intro-right">
          <p className="about-description about-reveal">
            {currentFestival?.description ||
              "Jwala Navdurga Utsav Mandal brings together devotion, tradition and community through nine sacred days dedicated to Devi."}
          </p>

          <div className="about-meta about-reveal">
            <div className="about-meta-item">
              <span className="about-meta-icon">
                <CalendarDays size={17} />
              </span>

              <div>
                <span className="about-meta-label">FESTIVAL</span>
                <strong>
                  {currentFestival?.year || "NAVDURGA"}
                </strong>
              </div>
            </div>

            <div className="about-meta-item">
              <span className="about-meta-icon">
                <Sparkles size={17} />
              </span>

              <div>
                <span className="about-meta-label">SACRED DAYS</span>
                <strong>09 DAYS</strong>
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* DIVIDER */}
      <div className="container about-divider">
        <span
          style={{
            background: theme.accent,
            boxShadow: `0 0 18px ${theme.glow}`,
          }}
        />
      </div>

      {/* 9 DAY JOURNEY */}
      <div
        ref={journeyRef}
        className="container journey-wrapper"
      >
        <div className="journey-heading">
          <div>
            <div className="section-eyebrow">
              <span className="section-eyebrow-line" />
              <span>NINE NIGHTS</span>
            </div>

            <h3 className="journey-title">
              Nights of celebration
            </h3>
          </div>

          <p className="journey-heading-description">
            Every night brings a unique experience,
            tradition and celebration.
          </p>
        </div>

        <div className="journey-grid">
          {festivalDays.map((day) => {
            const isCurrent = currentDay?.id === day.id;
            const dayTheme = day.theme;
            const nightEvent = NIGHT_JOURNEY[day.dayNumber];

            const accent =
              dayTheme?.accentColor || theme.accent;

            const glow =
              dayTheme?.glowColor || theme.glow;

            return (
              <article
                key={day.id}
                className={`journey-card ${
                  isCurrent ? "journey-card-active" : ""
                }`}
                style={{
                  "--day-accent": accent,
                  "--day-glow": glow,
                }}
              >
                <div className="journey-card-top">
                  <span className="journey-day-number">
                    {String(day.dayNumber).padStart(2, "0")}
                  </span>

                  {isCurrent && (
                    <span className="journey-live">
                      <span />
                      TODAY
                    </span>
                  )}
                </div>

                <div className="journey-card-middle">
                  <span className="journey-date">
                    {formatDate(day.festivalDate)}
                  </span>

                  <h4>
                    {nightEvent?.title || day.title}
                  </h4>

                  <p>
                    {nightEvent?.description ||
                      day.description ||
                      "A special night of Navdurga celebrations."}
                  </p>
                </div>

                <div className="journey-card-bottom">
                  <span
                    className="journey-color-dot"
                    style={{
                      background: accent,
                      boxShadow: `0 0 16px ${glow}`,
                    }}
                  />

                  <span>
                    NIGHT {String(day.dayNumber).padStart(2, "0")}
                  </span>

                  <ArrowUpRight size={16} />
                </div>
              </article>
            );
          })}
        </div>
      </div>
    </section>
  );
}

export default AboutJourney;
