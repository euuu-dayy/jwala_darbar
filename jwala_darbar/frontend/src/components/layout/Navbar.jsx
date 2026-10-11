import { useEffect, useState } from "react";
import {
  ArrowUpRight,
  Menu,
  X,
} from "lucide-react";

import { useFestival } from "../../context/FestivalContext";
import { useTheme } from "../../context/ThemeContext";

const NAV_ITEMS = [
  {
    label: "About",
    href: "#about",
  },
  {
    label: "Events",
    href: "#events",
  },
  {
    label: "Gallery",
    href: "#gallery",
  },
  {
    label: "Updates",
    href: "#updates",
  },
  {
    label: "Contact",
    href: "#contact",
  },
];

function Navbar() {
  const [scrolled, setScrolled] = useState(false);
  const [mobileOpen, setMobileOpen] = useState(false);

  const { currentDay } = useFestival();
  const { theme } = useTheme();

  useEffect(() => {
    function handleScroll() {
      setScrolled(window.scrollY > 30);
    }

    handleScroll();

    window.addEventListener("scroll", handleScroll);

    return () => {
      window.removeEventListener("scroll", handleScroll);
    };
  }, []);

  useEffect(() => {
    function handleResize() {
      if (window.innerWidth > 900) {
        setMobileOpen(false);
      }
    }

    window.addEventListener("resize", handleResize);

    return () => {
      window.removeEventListener("resize", handleResize);
    };
  }, []);

  useEffect(() => {
    if (mobileOpen) {
      document.body.style.overflow = "hidden";
    } else {
      document.body.style.overflow = "";
    }

    return () => {
      document.body.style.overflow = "";
    };
  }, [mobileOpen]);

  function closeMobileMenu() {
    setMobileOpen(false);
  }

  function handleNavClick(event, href) {
    event.preventDefault();

    closeMobileMenu();

    const target = document.querySelector(href);

    if (!target) {
      return;
    }

    target.scrollIntoView({
      behavior: "smooth",
      block: "start",
    });
  }

  return (
    <>
      <header
        className={`navbar ${
          scrolled ? "navbar-scrolled" : ""
        }`}
        style={{
          "--navbar-accent": theme.accent,
          "--navbar-glow": theme.glow,
        }}
      >
        <div className="navbar-inner">

          {/* BRAND */}

          <a
            href="#top"
            className="navbar-brand"
            onClick={(event) =>
              handleNavClick(event, "#top")
            }
            aria-label="Jwala Navdurga Utsav Mandal"
          >
            <div className="navbar-symbol">
              <span>ॐ</span>
            </div>

            <div className="navbar-brand-text">
              <span className="navbar-brand-main">
                ज्वाला नवदुर्गा
              </span>

              <span className="navbar-brand-sub">
                उत्सव मंडळ
              </span>
            </div>
          </a>


          {/* DESKTOP NAVIGATION */}

          <nav
            className="navbar-desktop-nav"
            aria-label="Main navigation"
          >
            {NAV_ITEMS.map((item) => (
              <a
                key={item.label}
                href={item.href}
                className="navbar-link"
                onClick={(event) =>
                  handleNavClick(event, item.href)
                }
              >
                {item.label}
              </a>
            ))}

            <a
              href="#donate"
              className="navbar-donate"
              onClick={(event) =>
                handleNavClick(event, "#donate")
              }
            >
              <span>Donate</span>
              <ArrowUpRight size={16} />
            </a>
          </nav>


          {/* MOBILE BUTTON */}

          <button
            type="button"
            className="navbar-mobile-button"
            onClick={() =>
              setMobileOpen((previous) => !previous)
            }
            aria-label={
              mobileOpen
                ? "Close navigation"
                : "Open navigation"
            }
            aria-expanded={mobileOpen}
          >
            {mobileOpen ? (
              <X size={23} />
            ) : (
              <Menu size={23} />
            )}
          </button>
        </div>
      </header>


      {/* MOBILE MENU */}

      <div
        className={`navbar-mobile ${
          mobileOpen ? "navbar-mobile-open" : ""
        }`}
      >
        <div className="navbar-mobile-inner">

          {currentDay && (
            <div className="navbar-mobile-day">
              <span
                className="navbar-mobile-day-dot"
                style={{
                  background: theme.accent,
                  boxShadow: `0 0 18px ${theme.glow}`,
                }}
              />

              <span>
                DAY{" "}
                {String(currentDay.dayNumber).padStart(
                  2,
                  "0"
                )}
              </span>

              <span className="navbar-mobile-divider">
                —
              </span>

              <span>
                {currentDay.title}
              </span>
            </div>
          )}


          <nav
            className="navbar-mobile-nav"
            aria-label="Mobile navigation"
          >
            {NAV_ITEMS.map((item, index) => (
              <a
                key={item.label}
                href={item.href}
                className="navbar-mobile-link"
                onClick={(event) =>
                  handleNavClick(event, item.href)
                }
              >
                <span className="navbar-mobile-number">
                  {String(index + 1).padStart(2, "0")}
                </span>

                <span>{item.label}</span>

                <ArrowUpRight size={18} />
              </a>
            ))}
          </nav>


          <a
            href="#donate"
            className="navbar-mobile-donate"
            onClick={(event) =>
              handleNavClick(event, "#donate")
            }
          >
            <span>Support the Celebration</span>

            <ArrowUpRight size={19} />
          </a>


          <div className="navbar-mobile-footer">
            <span>
              ज्वाला नवदुर्गा उत्सव मंडळ
            </span>

            <span>
              जय माता दी
            </span>
          </div>
        </div>
      </div>


      {/* MOBILE BACKDROP */}

      <button
        type="button"
        className={`navbar-backdrop ${
          mobileOpen
            ? "navbar-backdrop-visible"
            : ""
        }`}
        onClick={closeMobileMenu}
        aria-label="Close navigation"
      />
    </>
  );
}

export default Navbar;