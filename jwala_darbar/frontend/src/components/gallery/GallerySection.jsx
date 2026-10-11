import { useEffect, useRef, useState } from "react";
import { ArrowUpRight, Image as ImageIcon } from "lucide-react";
import gsap from "gsap";
import { ScrollTrigger } from "gsap/ScrollTrigger";

import { useFestival } from "../../context/FestivalContext";
import { useTheme } from "../../context/ThemeContext";
import { getGalleryAlbumsByFestival } from "../../api/galleryApi";

gsap.registerPlugin(ScrollTrigger);

function extractList(response) {
  if (Array.isArray(response)) {
    return response;
  }

  if (Array.isArray(response?.data)) {
    return response.data;
  }

  return [];
}

function getDrivePreviewUrl(url) {
  if (!url) return null;

  /*
   * Google Drive links can have different formats.
   * We keep the original URL as fallback.
   */

  const fileMatch = url.match(/\/file\/d\/([^/]+)/);

  if (fileMatch?.[1]) {
    return `https://drive.google.com/thumbnail?id=${fileMatch[1]}&sz=w1200`;
  }

  return url;
}

export default function GallerySection() {
  const { currentFestival } = useFestival();
  const { theme } = useTheme();

  const sectionRef = useRef(null);

  const [albums, setAlbums] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    if (!currentFestival?.id) {
      setAlbums([]);
      setLoading(false);
      return;
    }

    let cancelled = false;

    async function loadGallery() {
      try {
        setLoading(true);
        setError(null);

        const response = await getGalleryAlbumsByFestival(
          currentFestival.id
        );

        if (cancelled) return;

        setAlbums(extractList(response));
      } catch (requestError) {
        if (cancelled) return;

        console.error("Gallery loading failed:", requestError);

        setError(requestError);
        setAlbums([]);
      } finally {
        if (!cancelled) {
          setLoading(false);
        }
      }
    }

    loadGallery();

    return () => {
      cancelled = true;
    };
  }, [currentFestival?.id]);

  useEffect(() => {
    if (loading) return;

    const ctx = gsap.context(() => {
      gsap.fromTo(
        ".gallery-heading-reveal",
        {
          opacity: 0,
          y: 35,
        },
        {
          opacity: 1,
          y: 0,
          duration: 0.8,
          ease: "power3.out",
          scrollTrigger: {
            trigger: sectionRef.current,
            start: "top 80%",
          },
        }
      );

      gsap.fromTo(
        ".gallery-card",
        {
          opacity: 0,
          y: 45,
        },
        {
          opacity: 1,
          y: 0,
          duration: 0.7,
          stagger: 0.12,
          ease: "power3.out",
          scrollTrigger: {
            trigger: ".gallery-grid",
            start: "top 82%",
          },
        }
      );
    }, sectionRef);

    return () => ctx.revert();
  }, [loading, albums]);

  return (
    <section
      id="gallery"
      className="gallery-section"
      ref={sectionRef}
      style={{
        "--gallery-accent": theme.accent,
        "--gallery-glow": theme.glow,
      }}
    >
      <div className="container">
        <div className="gallery-heading gallery-heading-reveal">
          <div className="section-eyebrow">
            <span className="section-eyebrow-line" />
            <span>Moments of Devotion</span>
          </div>

          <div className="gallery-heading-row">
            <div>
              <h2>
                Memories from
                <span> Jwala Navdurga</span>
              </h2>

              <p>
                A glimpse into the celebrations, devotion and
                beautiful moments of our Navdurga Utsav.
              </p>
            </div>

            <div className="gallery-heading-icon">
              <ImageIcon size={24} strokeWidth={1.5} />
            </div>
          </div>
        </div>

        {loading && (
          <div className="gallery-state">
            <div className="gallery-loader" />
            <span>Loading memories...</span>
          </div>
        )}

        {!loading && error && (
          <div className="gallery-state gallery-error">
            Unable to load gallery moments.
          </div>
        )}

        {!loading && !error && albums.length === 0 && (
          <div className="gallery-state">
            Gallery moments will appear here soon.
          </div>
        )}

        {!loading && !error && albums.length > 0 && (
          <div className="gallery-grid">
            {albums.slice(0, 6).map((album, index) => {
              const imageUrl = getDrivePreviewUrl(album.driveUrl);

              return (
                <a
                  key={album.id ?? index}
                  href={album.driveUrl || "#"}
                  target="_blank"
                  rel="noreferrer"
                  className={`gallery-card gallery-card-${index + 1}`}
                >
                  <div className="gallery-card-image">
                    {imageUrl ? (
                      <img
                        src={imageUrl}
                        alt={album.title || "Festival memory"}
                        loading="lazy"
                      />
                    ) : (
                      <div className="gallery-placeholder">
                        <ImageIcon size={34} />
                      </div>
                    )}

                    <div className="gallery-card-overlay" />

                    <div className="gallery-card-arrow">
                      <ArrowUpRight size={20} />
                    </div>
                  </div>

                  <div className="gallery-card-content">
                    <span className="gallery-card-number">
                      {String(index + 1).padStart(2, "0")}
                    </span>

                    <div>
                      <h3>{album.title}</h3>

                      {album.description && (
                        <p>{album.description}</p>
                      )}
                    </div>
                  </div>
                </a>
              );
            })}
          </div>
        )}

        {!loading && albums.length > 6 && (
          <div className="gallery-footer">
            <a
              href="#gallery"
              className="gallery-view-button"
            >
              <span>Explore All Memories</span>
              <ArrowUpRight size={18} />
            </a>
          </div>
        )}
      </div>
    </section>
  );
}   