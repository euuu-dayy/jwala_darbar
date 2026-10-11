import { useEffect, useRef } from "react";

import { useTheme } from "../../context/ThemeContext";

function ThemeParticles() {
  const canvasRef = useRef(null);

  const { theme } = useTheme();

  useEffect(() => {
    const canvas = canvasRef.current;

    if (!canvas) {
      return;
    }

    const context = canvas.getContext("2d");

    if (!context) {
      return;
    }

    let animationFrame;
    let particles = [];

    function resizeCanvas() {
      const pixelRatio = Math.min(
        window.devicePixelRatio || 1,
        2
      );

      canvas.width =
        window.innerWidth * pixelRatio;

      canvas.height =
        window.innerHeight * pixelRatio;

      canvas.style.width =
        `${window.innerWidth}px`;

      canvas.style.height =
        `${window.innerHeight}px`;

      context.setTransform(
        pixelRatio,
        0,
        0,
        pixelRatio,
        0,
        0
      );
    }

    function createParticles() {
      const count =
        window.innerWidth < 700
          ? 35
          : 65;

      particles = Array.from(
        { length: count },
        () => ({
          x: Math.random() * window.innerWidth,

          y:
            Math.random() *
            window.innerHeight,

          size:
            Math.random() * 1.8 + 0.4,

          speed:
            Math.random() * 0.25 + 0.08,

          opacity:
            Math.random() * 0.5 + 0.15,

          drift:
            (Math.random() - 0.5) * 0.25,
        })
      );
    }

    function draw() {
      context.clearRect(
        0,
        0,
        window.innerWidth,
        window.innerHeight
      );

      particles.forEach((particle) => {
        particle.y -= particle.speed;
        particle.x += particle.drift;

        if (particle.y < -10) {
          particle.y =
            window.innerHeight + 10;

          particle.x =
            Math.random() *
            window.innerWidth;
        }

        if (
          particle.x < -10 ||
          particle.x >
            window.innerWidth + 10
        ) {
          particle.x =
            Math.random() *
            window.innerWidth;
        }

        context.beginPath();

        context.arc(
          particle.x,
          particle.y,
          particle.size,
          0,
          Math.PI * 2
        );

        context.fillStyle = theme.accent;

        context.globalAlpha =
          particle.opacity;

        context.fill();
      });

      context.globalAlpha = 1;

      animationFrame =
        requestAnimationFrame(draw);
    }

    resizeCanvas();
    createParticles();
    draw();

    window.addEventListener(
      "resize",
      resizeCanvas
    );

    return () => {
      cancelAnimationFrame(
        animationFrame
      );

      window.removeEventListener(
        "resize",
        resizeCanvas
      );
    };
  }, [theme.accent]);

  return (
    <canvas
      ref={canvasRef}
      className="theme-particles"
      aria-hidden="true"
    />
  );
}

export default ThemeParticles;