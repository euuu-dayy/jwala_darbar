import { useEffect, useRef, useState } from "react";
import {
  ArrowRight,
  CalendarDays,
  Clock3,
  MapPin,
  Sparkles,
  Users,
} from "lucide-react";
import gsap from "gsap";
import { ScrollTrigger } from "gsap/ScrollTrigger";

import { getEventsByType } from "../../api/eventApi";

import { useFestival } from "../../context/FestivalContext";
import { useTheme } from "../../context/ThemeContext";

import { formatTime } from "../../utils/dateUtils";

gsap.registerPlugin(ScrollTrigger);

const EVENT_TABS = [
  {
    key: "DAY",
    label: "Day Events",
    icon: Sparkles,
  },
  {
    key: "NIGHT",
    label: "Night Events",
    icon: Clock3,
  },
  {
    key: "SPECIAL",
    label: "Special",
    icon: CalendarDays,
  },
];

function EventsSection() {
  const sectionRef = useRef(null);
  const cardsRef = useRef(null);

  const {
    currentDay,
    loading: festivalLoading,
  } = useFestival();

  const { theme } = useTheme();

  const [activeType, setActiveType] = useState("DAY");
  const [events, setEvents] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    if (!currentDay?.id) {
      setEvents([]);
      return;
    }

    let cancelled = false;

    async function loadEvents() {
      try {
        setLoading(true);
        setError(null);

        const response = await getEventsByType(
          currentDay.id,
          activeType
        );

        if (cancelled) return;

        if (Array.isArray(response)) {
          setEvents(response);
        } else if (Array.isArray(response?.data)) {
          setEvents(response.data);
        } else {
          setEvents([]);
        }
      } catch (requestError) {
        if (cancelled) return;

        console.error(
          "Events loading failed:",
          requestError
        );

        setError(requestError);
        setEvents([]);
      } finally {
        if (!cancelled) {
          setLoading(false);
        }
      }
    }

    loadEvents();

    return () => {
      cancelled = true;
    };
  }, [currentDay?.id, activeType]);

  useEffect(() => {
    const section = sectionRef.current;
    const cards = cardsRef.current;

    if (!section || !cards) return;

    const context = gsap.context(() => {
      gsap.fromTo(
        section.querySelectorAll(
          ".events-reveal"
        ),
        {
          opacity: 0,
          y: 35,
        },
        {
          opacity: 1,
          y: 0,
          duration: 0.8,
          stagger: 0.1,
          ease: "power3.out",
          scrollTrigger: {
            trigger: section,
            start: "top 75%",
            once: true,
          },
        }
      );
    }, section);

    return () => context.revert();
  }, []);

  useEffect(() => {
    if (!cardsRef.current) return;

    const cards =
      cardsRef.current.querySelectorAll(
        ".event-card"
      );

    gsap.fromTo(
      cards,
      {
        opacity: 0,
        y: 25,
      },
      {
        opacity: 1,
        y: 0,
        duration: 0.55,
        stagger: 0.08,
        ease: "power2.out",
      }
    );
  }, [events]);

  function handleTabChange(type) {
    if (type === activeType) return;

    setActiveType(type);
  }

  return (
    <section
      ref={sectionRef}
      id="events"
      className="events-section"
      style={{
        "--events-accent": theme.accent,
        "--events-glow": theme.glow,
      }}
    >
      <div className="events-background-glow" />

      <div className="container">
        {/* HEADER */}
        <div className="events-header">
          <div className="events-header-left">
            <div className="section-eyebrow events-reveal">
              <span className="section-eyebrow-line" />
              <Sparkles size={14} />
              <span>THE CELEBRATION</span>
            </div>

            <h2 className="events-title events-reveal">
              Events that bring
              <br />
              <span>everyone together.</span>
            </h2>
          </div>

          <div className="events-header-right events-reveal">
            {currentDay ? (
              <>
                <span className="events-today-label">
                  TODAY'S CELEBRATION
                </span>

                <strong>
                  DAY{" "}
                  {String(
                    currentDay.dayNumber
                  ).padStart(2, "0")}
                </strong>

                <span className="events-today-title">
                  {currentDay.title}
                </span>
              </>
            ) : (
              <span>
                Explore our festival events.
              </span>
            )}
          </div>
        </div>

        {/* EVENT TYPE TABS */}
        <div className="events-tabs events-reveal">
          {EVENT_TABS.map((tab) => {
            const Icon = tab.icon;
            const active =
              activeType === tab.key;

            return (
              <button
                key={tab.key}
                type="button"
                className={`events-tab ${
                  active
                    ? "events-tab-active"
                    : ""
                }`}
                onClick={() =>
                  handleTabChange(tab.key)
                }
                style={
                  active
                    ? {
                        "--tab-color":
                          theme.accent,
                        "--tab-glow":
                          theme.glow,
                      }
                    : undefined
                }
              >
                <Icon size={16} />

                <span>{tab.label}</span>

                {active && (
                  <span className="events-tab-dot" />
                )}
              </button>
            );
          })}
        </div>

        {/* EVENTS */}
        <div
          ref={cardsRef}
          className="events-list"
        >
          {festivalLoading || loading ? (
            <div className="events-state">
              <div
                className="events-loader"
                style={{
                  borderTopColor:
                    theme.accent,
                }}
              />

              <span>
                Loading celebration...
              </span>
            </div>
          ) : error ? (
            <div className="events-state events-state-error">
              <span>
                Unable to load events right now.
              </span>
            </div>
          ) : events.length === 0 ? (
            <div className="events-empty">
              <div className="events-empty-icon">
                <CalendarDays size={25} />
              </div>

              <h3>
                No{" "}
                {activeType.toLowerCase()} events
                today
              </h3>

              <p>
                Check another part of the
                celebration or come back soon.
              </p>
            </div>
          ) : (
            events.map((event, index) => (
              <EventCard
                key={event.id}
                event={event}
                index={index}
                theme={theme}
              />
            ))
          )}
        </div>
      </div>
    </section>
  );
}

function EventCard({
  event,
  index,
  theme,
}) {
  const registrationRequired =
    event.registrationRequired === true;

  return (
    <article
      className="event-card"
      style={{
        "--event-accent":
          theme.accent,
        "--event-glow":
          theme.glow,
      }}
    >
      <div className="event-card-number">
        {String(index + 1).padStart(2, "0")}
      </div>

      <div className="event-card-main">
        <div className="event-card-meta">
          <span className="event-type">
            {event.eventType}
          </span>

          {registrationRequired && (
            <span className="event-registration">
              Registration Required
            </span>
          )}
        </div>

        <h3>{event.title}</h3>

        {event.description && (
          <p>{event.description}</p>
        )}
      </div>

      <div className="event-card-details">
        {(event.startTime ||
          event.endTime) && (
          <div className="event-detail">
            <Clock3 size={15} />

            <span>
              {formatTime(event.startTime)}

              {event.endTime &&
                ` — ${formatTime(
                  event.endTime
                )}`}
            </span>
          </div>
        )}

        {event.location && (
          <div className="event-detail">
            <MapPin size={15} />

            <span>
              {event.location}
            </span>
          </div>
        )}

        {event.maxParticipants && (
          <div className="event-detail">
            <Users size={15} />

            <span>
              Max{" "}
              {event.maxParticipants}
              {" participants"}
            </span>
          </div>
        )}
      </div>

      <button
        type="button"
        className="event-card-arrow"
        aria-label={`View ${event.title}`}
      >
        <ArrowRight size={18} />
      </button>
    </article>
  );
}

export default EventsSection;