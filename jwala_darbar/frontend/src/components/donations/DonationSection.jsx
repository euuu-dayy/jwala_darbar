import { useEffect, useRef, useState } from "react";
import {
  ArrowUpRight,
  Heart,
  IndianRupee,
  ShieldCheck,
  Sparkles,
} from "lucide-react";
import gsap from "gsap";
import { ScrollTrigger } from "gsap/ScrollTrigger";

import { useFestival } from "../../context/FestivalContext";
import { useTheme } from "../../context/ThemeContext";

import {
  getDonationCampaignsByFestival,
  createDonation,
} from "../../api/donationApi";

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

export default function DonationSection() {
  const { currentFestival } = useFestival();
  const { theme } = useTheme();

  const sectionRef = useRef(null);

  const [campaign, setCampaign] = useState(null);
  const [loading, setLoading] = useState(true);

  const [amount, setAmount] = useState("");
  const [donorName, setDonorName] = useState("");
  const [mobileNumber, setMobileNumber] = useState("");
  const [email, setEmail] = useState("");

  const [submitting, setSubmitting] = useState(false);
  const [message, setMessage] = useState("");

  useEffect(() => {
    if (!currentFestival?.id) {
      setCampaign(null);
      setLoading(false);
      return;
    }

    let cancelled = false;

    async function loadCampaign() {
      try {
        setLoading(true);

        const response =
          await getDonationCampaignsByFestival(
            currentFestival.id
          );

        if (cancelled) return;

        const campaigns = extractList(response);

        const activeCampaign =
          campaigns.find(
            (item) =>
              String(item.status).toUpperCase() ===
              "ACTIVE"
          ) ?? campaigns[0];

        setCampaign(activeCampaign ?? null);
      } catch (error) {
        if (!cancelled) {
          console.error(
            "Donation campaign loading failed:",
            error
          );

          setCampaign(null);
        }
      } finally {
        if (!cancelled) {
          setLoading(false);
        }
      }
    }

    loadCampaign();

    return () => {
      cancelled = true;
    };
  }, [currentFestival?.id]);

  useEffect(() => {
    if (loading) return;

    const ctx = gsap.context(() => {
      gsap.fromTo(
        ".donation-reveal",
        {
          opacity: 0,
          y: 40,
        },
        {
          opacity: 1,
          y: 0,
          duration: 0.8,
          stagger: 0.12,
          ease: "power3.out",
          scrollTrigger: {
            trigger: sectionRef.current,
            start: "top 78%",
          },
        }
      );
    }, sectionRef);

    return () => ctx.revert();
  }, [loading]);

  function handleAmountChange(event) {
    const value = event.target.value;

    if (!/^\d*$/.test(value)) {
      return;
    }

    setAmount(value);
  }

  function selectAmount(value) {
    setAmount(String(value));
  }

  
async function handleSubmit(event) {
  event.preventDefault();
  setMessage("");

  const numericAmount = amount.trim();

  if (!numericAmount || Number(numericAmount) < 1) {
    setMessage("Please enter a valid donation amount.");
    return;
  }

  if (!donorName.trim()) {
    setMessage("Please enter your name.");
    return;
  }

  if (!/^[6-9]\d{9}$/.test(mobileNumber)) {
    setMessage("Please enter a valid 10-digit mobile number.");
    return;
  }

  if (!campaign?.id) {
    setMessage("No valid donation campaign is available.");
    return;
  }

  let createdDonation = null;

  try {
    setSubmitting(true);

    // 1. Create the donation.
    const donation = await createDonation({
      campaignId: campaign.id,
      donorName: donorName.trim(),
      mobileNumber,
      email: email.trim() || null,
      amount: numericAmount,
      donationType: "ONLINE",
      note: "Navdurga Utsav donation",
    });

    createdDonation = donation;

    if (!donation?.id) {
      throw new Error(
        "Donation was created, but its ID was not returned."
      );
    }

    // 2. Initiate a development payment transaction.
    // This records the transaction; it does NOT collect payment.
    const transaction = await createPaymentTransaction({
      donationId: donation.id,
      gateway: "DEVELOPMENT",
      paymentMethod: "UPI",
    });

    if (!transaction?.id) {
      throw new Error(
        "Donation created, but the transaction ID was not returned."
      );
    }

    // 3. Report the real state; do not mark the donation successful.
    setMessage(
      `Donation #${donation.id} created. ` +
      `Payment transaction #${transaction.id} initiated. ` +
      "Real payment checkout is not connected yet."
    );

    setAmount("");
    setDonorName("");
    setMobileNumber("");
    setEmail("");
  } catch (error) {
    console.error("Donation/payment initiation failed:", error);

    if (createdDonation?.id) {
      setMessage(
        `Donation #${createdDonation.id} was created, but payment ` +
        "transaction initiation failed. It remains pending. " +
        "Please don't submit another donation; check the transaction first."
      );
    } else {
      setMessage(
        error.response?.data?.message ||
        error.message ||
        "Unable to create the donation. Please try again."
      );
    }
  } finally {
    setSubmitting(false);
  }
}

  const targetAmount = Number(campaign?.targetAmount ?? 0);

  return (
    <section
      id="donate"
      ref={sectionRef}
      className="donation-section"
      style={{
        "--donation-primary": theme.primary,
        "--donation-accent": theme.accent,
        "--donation-glow": theme.glow,
      }}
    >
      <div className="donation-glow donation-glow-one" />
      <div className="donation-glow donation-glow-two" />

      <div className="container">
        <div className="donation-layout">
          <div className="donation-copy donation-reveal">
            <div className="section-eyebrow donation-eyebrow">
              <span className="section-eyebrow-line" />
              <span>Support the Celebration</span>
            </div>

            <div className="donation-symbol">
              <Heart size={20} fill="currentColor" />
            </div>

            <h2>
              Your contribution
              <span> becomes a celebration.</span>
            </h2>

            <p>
              Every contribution helps us bring the Navdurga
              celebration to life and serve our community with
              devotion.
            </p>

            {campaign && (
              <div className="donation-campaign">
                <div>
                  <span>Current Campaign</span>
                  <strong>{campaign.name}</strong>
                </div>

                {targetAmount > 0 && (
                  <div className="donation-target">
                    <span>Campaign Target</span>
                    <strong>
                      ₹{targetAmount.toLocaleString("en-IN")}
                    </strong>
                  </div>
                )}
              </div>
            )}

            <div className="donation-trust">
              <div>
                <ShieldCheck size={19} />
                <span>Secure donation process</span>
              </div>

              <div>
                <Sparkles size={19} />
                <span>For the official mandal campaign</span>
              </div>
            </div>
          </div>

          <div className="donation-card donation-reveal">
            {loading ? (
              <div className="donation-loading">
                Loading donation campaign...
              </div>
            ) : !campaign ? (
              <div className="donation-loading">
                No active donation campaign is available.
              </div>
            ) : (
              <form
                className="donation-form"
                onSubmit={handleSubmit}
              >
                <div className="donation-form-heading">
                  <span>Make a Contribution</span>
                  <Heart size={18} />
                </div>

                <div className="donation-amount-options">
                  {[101, 501, 1001, 2100].map(
                    (value) => (
                      <button
                        type="button"
                        key={value}
                        className={
                          amount === String(value)
                            ? "active"
                            : ""
                        }
                        onClick={() =>
                          selectAmount(value)
                        }
                      >
                        ₹{value.toLocaleString("en-IN")}
                      </button>
                    )
                  )}
                </div>

                <label className="donation-field">
                  <span>Donation Amount</span>

                  <div className="donation-input-wrap">
                    <IndianRupee size={17} />

                    <input
                      type="text"
                      inputMode="numeric"
                      placeholder="Enter amount"
                      value={amount}
                      onChange={handleAmountChange}
                    />
                  </div>
                </label>

                <label className="donation-field">
                  <span>Your Name</span>

                  <input
                    type="text"
                    placeholder="Enter your name"
                    value={donorName}
                    onChange={(event) =>
                      setDonorName(event.target.value)
                    }
                  />
                </label>

                <label className="donation-field">
                  <span>Mobile Number</span>

                  <input
                    type="tel"
                    inputMode="numeric"
                    maxLength={10}
                    placeholder="10-digit mobile number"
                    value={mobileNumber}
                    onChange={(event) =>
                      setMobileNumber(
                        event.target.value.replace(/\D/g, "")
                      )
                    }
                  />
                </label>

                <label className="donation-field">
                  <span>
                    Email <small>Optional</small>
                  </span>

                  <input
                    type="email"
                    placeholder="you@example.com"
                    value={email}
                    onChange={(event) =>
                      setEmail(event.target.value)
                    }
                  />
                </label>

                {message && (
                  <div className="donation-message">
                    {message}
                  </div>
                )}

                <button
                  type="submit"
                  className="donation-submit"
                  disabled={submitting}
                >
                  <span>
                    {submitting
                      ? "Creating Donation..."
                      : "Continue to Donate"}
                  </span>

                  <ArrowUpRight size={19} />
                </button>

                <p className="donation-note">
                  You will proceed to the secure payment
                  process after creating your donation.
                </p>
              </form>
            )}
          </div>
        </div>
      </div>
    </section>
  );
}