import { useEffect, useRef } from "react";
import gsap from "gsap";

import { useTheme } from "../../context/ThemeContext";

import deviImage from "../../assets/images/devi.png";

function DeviImage() {
  const wrapperRef = useRef(null);
  const imageRef = useRef(null);
  const glowRef = useRef(null);

  const { theme } = useTheme();

  useEffect(() => {
    const wrapper = wrapperRef.current;
    const image = imageRef.current;
    const glow = glowRef.current;

    if (!wrapper || !image || !glow) {
      return;
    }

    const context = gsap.context(() => {
      gsap.fromTo(
        wrapper,
        {
          opacity: 0,
          y: 40,
          scale: 0.94,
        },
        {
          opacity: 1,
          y: 0,
          scale: 1,
          duration: 1.5,
          ease: "power3.out",
        }
      );

      gsap.to(image, {
        y: -8,
        duration: 3.8,
        repeat: -1,
        yoyo: true,
        ease: "sine.inOut",
      });

      gsap.to(glow, {
        scale: 1.08,
        opacity: 0.4,
        duration: 3.2,
        repeat: -1,
        yoyo: true,
        ease: "sine.inOut",
      });
    }, wrapper);

    return () => {
      context.revert();
    };
  }, []);

  return (
    <div
      ref={wrapperRef}
      className="devi-visual"
      style={{
        "--devi-glow": theme.glow,
      }}
    >
      <div
        ref={glowRef}
        className="devi-glow"
      />

      <div className="devi-frame">
        <img
          ref={imageRef}
          src={deviImage}
          alt="Jwala Navdurga Devi"
          className="devi-image"
        />
      </div>

      <div className="devi-ring devi-ring-one" />
      <div className="devi-ring devi-ring-two" />
    </div>
  );
}

export default DeviImage;