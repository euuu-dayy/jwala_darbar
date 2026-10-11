
import { useState } from "react";
import { MapPin, Mail, Phone, ArrowUpRight, Send, LoaderCircle } from "lucide-react";
import { submitContactMessage } from "../../api/contactApi";

const initialForm = {
  name: "",
  mobileNumber: "",
  email: "",
  subject: "",
  message: "",
};

export default function ContactSection() {
  const [form, setForm] = useState(initialForm);
  const [loading, setLoading] = useState(false);
  const [feedback, setFeedback] = useState({ type: "", text: "" });

  function handleChange(event) {
    const { name, value } = event.target;

    setForm((previous) => ({
      ...previous,
      [name]: value,
    }));

    if (feedback.text) {
      setFeedback({ type: "", text: "" });
    }
  }

  async function handleSubmit(event) {
    event.preventDefault();
    setFeedback({ type: "", text: "" });

    const mobile = form.mobileNumber.trim();

    if (mobile && !/^[6-9]\d{9}$/.test(mobile)) {
      setFeedback({
        type: "error",
        text: "Enter a valid 10-digit Indian mobile number.",
      });
      return;
    }

    try {
      setLoading(true);

      await submitContactMessage({
        name: form.name.trim(),
        mobileNumber: mobile || null,
        email: form.email.trim() || null,
        subject: form.subject.trim(),
        message: form.message.trim(),
      });

      setForm(initialForm);
      setFeedback({
        type: "success",
        text: "Thank you for contacting us! Your message has been submitted successfully.",
      });
    } catch (error) {
      console.error("Contact form submission failed:", error);

      setFeedback({
        type: "error",
        text:
          error.response?.data?.message ||
          "We couldn't submit your message. Please try again.",
      });
    } finally {
      setLoading(false);
    }
  }

  return (
    <section className="contact-section" id="contact">
      <div className="contact-container">
        <div className="contact-heading">
          <span className="contact-eyebrow">
            <span />
            WE WOULD LOVE TO HEAR FROM YOU
            <span />
          </span>

          <h2>
            Let's Connect
            <br />
            <span>With Our Mandal</span>
          </h2>

          <p>
            Have a question, suggestion, or want to know more about
            our celebrations? Send us a message. We would be happy to
            hear from you.
          </p>
        </div>

        <div className="contact-grid">
          <div className="contact-information">
            <span className="contact-card-label">GET IN TOUCH</span>

            <h3>We're here for you.</h3>

            <p className="contact-info-description">
              Whether it is about our festival, events, or community
              activities, feel free to reach out.
            </p>

            <div className="contact-info-list">
              <div className="contact-info-item">
                <div className="contact-info-icon">
                  <MapPin size={21} />
                </div>
                <div>
                  <h4>Our Location</h4>
                  <p>Jwala Navdurga Utsav Mandal</p>
                  <span>Address to be added</span>
                </div>
              </div>

              <div className="contact-info-item">
                <div className="contact-info-icon">
                  <Phone size={21} />
                </div>
                <div>
                  <h4>Call Us</h4>
                  <p>Contact number to be added</p>
                </div>
              </div>

              <div className="contact-info-item">
                <div className="contact-info-icon">
                  <Mail size={21} />
                </div>
                <div>
                  <h4>Email Us</h4>
                  <p>Email address to be added</p>
                </div>
              </div>
            </div>

            <div className="contact-decoration" aria-hidden="true">
              <span>॥ जय माता दी ॥</span>
            </div>
          </div>

          <div className="contact-form-card">
            <div className="contact-form-heading">
              <span>YOUR MESSAGE MATTERS</span>
              <h3>Send us a message</h3>
              <p>Fill in the details below and we'll receive your message.</p>
            </div>

            <form onSubmit={handleSubmit}>
              <div className="contact-form-row">
                <div className="contact-field">
                  <label htmlFor="contact-name">Full Name *</label>
                  <input
                    id="contact-name"
                    name="name"
                    type="text"
                    placeholder="Enter your full name"
                    value={form.name}
                    onChange={handleChange}
                    maxLength={150}
                    autoComplete="name"
                    required
                  />
                </div>

                <div className="contact-field">
                  <label htmlFor="contact-mobile">Mobile Number</label>
                  <input
                    id="contact-mobile"
                    name="mobileNumber"
                    type="tel"
                    placeholder="10-digit mobile number"
                    value={form.mobileNumber}
                    onChange={handleChange}
                    inputMode="numeric"
                    maxLength={10}
                    autoComplete="tel"
                  />
                </div>
              </div>

              <div className="contact-field">
                <label htmlFor="contact-email">Email Address</label>
                <input
                  id="contact-email"
                  name="email"
                  type="email"
                  placeholder="you@example.com"
                  value={form.email}
                  onChange={handleChange}
                  maxLength={255}
                  autoComplete="email"
                />
              </div>

              <div className="contact-field">
                <label htmlFor="contact-subject">Subject *</label>
                <input
                  id="contact-subject"
                  name="subject"
                  type="text"
                  placeholder="What would you like to discuss?"
                  value={form.subject}
                  onChange={handleChange}
                  maxLength={200}
                  required
                />
              </div>

              <div className="contact-field">
                <label htmlFor="contact-message">Your Message *</label>
                <textarea
                  id="contact-message"
                  name="message"
                  placeholder="Write your message here..."
                  value={form.message}
                  onChange={handleChange}
                  rows={5}
                  maxLength={3000}
                  required
                />
                <span className="contact-character-count">
                  {form.message.length}/3000
                </span>
              </div>

              {feedback.text && (
                <div
                  className={`contact-feedback contact-feedback-${feedback.type}`}
                  role={feedback.type === "error" ? "alert" : "status"}
                  aria-live="polite"
                >
                  {feedback.text}
                </div>
              )}

              <button
                className="contact-submit-button"
                type="submit"
                disabled={loading}
              >
                {loading ? (
                  <>
                    <LoaderCircle size={18} className="contact-spinner" />
                    Sending Message...
                  </>
                ) : (
                  <>
                    Send Message
                    <Send size={17} />
                  </>
                )}
              </button>

              <p className="contact-form-note">
                Fields marked with * are required.
              </p>
            </form>
          </div>
        </div>
      </div>
    </section>
  );
}
