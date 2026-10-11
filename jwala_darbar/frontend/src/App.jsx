import Navbar from "./components/layout/Navbar";
import Hero from "./components/hero/Hero";
import AboutJourney from "./components/festival/AboutJourney";
import EventsSection from "./components/events/EventsSection";
import GallerySection from "./components/gallery/GallerySection";
import DonationSection from "./components/donations/DonationSection";
import ContactSection from "./components/contact/ContactSection";

function App() {
  return (
    <div className="app">
      <div id="top" />

      <Navbar />

      <main>
        <Hero />

        <AboutJourney />

        <EventsSection />
        <GallerySection />
        <DonationSection />
        <ContactSection />
      </main>
    </div>
  );
}

export default App;